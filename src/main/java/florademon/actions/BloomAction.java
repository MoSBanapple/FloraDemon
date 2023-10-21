package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import florademon.orbs.BladedLily;
import florademon.orbs.PlantOrb;
import florademon.powers.CrossPollinationPower;
import florademon.powers.FertilityPower;
import florademon.powers.LoseFertilityPower;
import florademon.relics.SunflowerSeeds;

import java.util.ArrayList;
import java.util.Collections;

public class BloomAction extends AbstractGameAction {

    private int numBlooms;

    public BloomAction(){
        this(1);
    }

    public BloomAction(int howManyBlooms){
        actionType = ActionType.SPECIAL;
        numBlooms = howManyBlooms;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null){
            this.isDone = true;
            return;
        }
        int numFertility = 0;
        if (player.hasPower(CrossPollinationPower.POWER_ID)){
            numFertility = player.getPower(CrossPollinationPower.POWER_ID).amount;
        }

        if (player.hasRelic(SunflowerSeeds.ID)){
            SunflowerSeeds seeds = (SunflowerSeeds) player.getRelic(SunflowerSeeds.ID);
            if (!seeds.activatedThisTurn){
                seeds.activatedThisTurn = true;
                addToTop(new GainEnergyAction(1));
                this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, seeds));
                seeds.flash();
            }
        }

        for (int i = 0; i < numBlooms; i++) {
            if (numFertility > 0){
                addToTop(new ApplyPowerAction(player, player, new LoseFertilityPower(player, numFertility)));
                addToTop(new ApplyPowerAction(player, player, new FertilityPower(player, numFertility)));
            }
            ArrayList<AbstractOrb> reverseOrbs = new ArrayList<AbstractOrb>(player.orbs);
            Collections.reverse(reverseOrbs);
            reverseOrbs.forEach((currentOrb) -> {
                if (currentOrb instanceof PlantOrb) {
                    addToTop(new ActivatePlantAction((PlantOrb) currentOrb));
                }
            });
            addToTop(new WaitAction(0.1F));
        }
        this.isDone = true;
    }
}
