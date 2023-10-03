package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.orbs.PlantOrb;

public class ActivatePlantAction extends AbstractGameAction {

    private int numActivations;
    private PlantOrb plant;

    public ActivatePlantAction(PlantOrb plantToActivate){
        this(plantToActivate, 1);
    }

    public ActivatePlantAction(PlantOrb plantToActivate, int howManyActivations){
        plant = plantToActivate;
        numActivations = howManyActivations;
    }

    @Override
    public void update() {
        for (int i = 0; i < numActivations; i++){
            plant.onActivate();
        }
        this.isDone = true;
    }
}
