package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.orbs.PlantOrb;
import florademon.powers.FloralReflectionPower;

import java.util.concurrent.atomic.AtomicInteger;

public class FloralReflectionAction extends AbstractGameAction {

    public FloralReflectionAction(){

    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        int numPlants = PlantOrb.getNumPlants(p);
        if (numPlants == 0){
            this.isDone = true;
            return;
        }
        addToTop(new ApplyPowerAction(p, p, new FloralReflectionPower(p, numPlants), numPlants));
        p.orbs.forEach((thisOrb) -> {
            if (thisOrb instanceof PlantOrb){
                addToTop(new RemoveSpecificOrbAction(thisOrb));
            }
        });
        this.isDone = true;
        return;
    }
}
