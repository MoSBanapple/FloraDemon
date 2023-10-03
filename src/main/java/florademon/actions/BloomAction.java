package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import florademon.orbs.BladedLily;
import florademon.orbs.PlantOrb;

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
        for (int i = 0; i < numBlooms; i++) {
            player.orbs.forEach((currentOrb) -> {
                if (currentOrb instanceof PlantOrb) {
                    addToTop(new ActivatePlantAction((PlantOrb) currentOrb));
                    addToTop(new WaitAction(0.1F));
                }
            });
            addToTop(new WaitAction(0.2F));
        }
        this.isDone = true;
    }
}
