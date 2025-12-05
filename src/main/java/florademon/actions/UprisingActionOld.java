package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import florademon.powers.EnergizedPower;
import florademon.powers.NurtureNextDrawPower;

public class UprisingActionOld extends AbstractGameAction {

    private int energyOnUse;
    private boolean isUpgraded;
    private boolean freeToPlayOnce;

    public UprisingActionOld(int energyOnUse, boolean isUpgraded, boolean freeToPlayOnce) {
        this.energyOnUse = energyOnUse;
        this.isUpgraded = isUpgraded;
        this.freeToPlayOnce = freeToPlayOnce;

    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int numNurtures = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            numNurtures = this.energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            numNurtures += 2;
            p.getRelic("Chemical X").flash();
        }
        if (this.isUpgraded){
            numNurtures += 1;
        }
        if (numNurtures > 0) {
            addToTop(new ApplyPowerAction(p, p, new EnergizedPower(p, numNurtures)));
            addToTop(new ApplyPowerAction(p, p, new NurtureNextDrawPower(p, numNurtures)));

            if (!this.freeToPlayOnce) {

                p.energy.use(EnergyPanel.totalCount);


            }
        }
        this.isDone = true;


    }
}
