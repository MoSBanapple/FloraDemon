package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import florademon.orbs.PlantOrb;

public class BlossomAction extends AbstractGameAction {

    private int energyOnUse;
    private boolean isUpgraded;
    private boolean freeToPlayOnce;

    public BlossomAction(int energyOnUse, boolean isUpgraded, boolean freeToPlayOnce) {
        this.energyOnUse = energyOnUse;
        this.isUpgraded = isUpgraded;
        this.freeToPlayOnce = freeToPlayOnce;

    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int numActivations = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            numActivations = this.energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            numActivations += 2;
            p.getRelic("Chemical X").flash();
        }
        if (this.isUpgraded){
            numActivations += 1;
        }
        if (numActivations >= 2) {

            addToTop(new BloomAction(2));

        }
        if (numActivations > 0){
            for (int i = 0; i < numActivations; i++){
                addToTop(new ChannelAction(PlantOrb.getRandomPlant(true)));
            }

            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;


    }


}
