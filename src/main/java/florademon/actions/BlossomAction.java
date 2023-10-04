package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

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
        int numBlooms = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            numBlooms = this.energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            numBlooms += 2;
            p.getRelic("Chemical X").flash();
        }
        if (this.isUpgraded){
            numBlooms += 1;
        }
        if (numBlooms > 0) {

            addToTop(new BloomAction(numBlooms));

            if (!this.freeToPlayOnce) {
                p.energy.use(EnergyPanel.totalCount);
            }
        }
        this.isDone = true;


    }
}
