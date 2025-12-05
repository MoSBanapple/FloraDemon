package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.modifiers.NurtureModifier;

public class UprisingRestoreEnergyAction extends AbstractGameAction {
    private int baseEnergy = 0;
    private int maxRestored;
    public UprisingRestoreEnergyAction(int additionalEnergy,int maxRestored){
        this.baseEnergy += additionalEnergy;
        this.maxRestored = maxRestored;
    }

    public UprisingRestoreEnergyAction(int additionalEnergy){
        this(additionalEnergy,-1);
    }
    public UprisingRestoreEnergyAction(){
        this(0,-1);
    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int energyToRestore = this.baseEnergy + NurtureModifier.howManyNurturable(p.hand.group);
        if (this.maxRestored >=0){
            energyToRestore = Math.min(energyToRestore,maxRestored);
        }
        addToTop(new GainEnergyAction(energyToRestore));
        this.isDone = true;
    }
}
