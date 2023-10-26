package florademon.orbs;

import basemod.abstracts.CustomOrb;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.cards.status.Burn;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import florademon.patches.OrbFlareColorPatch;
import florademon.vfx.PlantActivateEffect;

public  abstract class PlantOrb extends CustomOrb {


    public PlantOrb(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription, String evokeDescription, String imgPath) {
        super(ID, NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription, imgPath);
    }

    /**
     * To be used when the plant is activated.
     */
    public void onActivate(){
        /*
        float speedTime = 0.2F / (float)AbstractDungeon.player.orbs.size();
        if (Settings.FAST_MODE) {
            speedTime = 0.0F;
        }
        //AbstractDungeon.actionManager.addToTop(new VFXAction(new OrbFlareEffect(this, OrbFlareEffect.OrbFlareColor.LIGHTNING), speedTime));

         */
        AbstractDungeon.actionManager.addToTop(new VFXAction(new PlantActivateEffect(this)));

    };


    public static PlantOrb getRandomPlant(boolean common){
        int chosenPlant = AbstractDungeon.cardRandomRng.random(7);
        if (common){
            chosenPlant = AbstractDungeon.cardRandomRng.random(3);
        }
        switch(chosenPlant){
            case 0:
                return new BladedLily();
            case 1:
                return new HardyDandelion();
            case 2:
                return new SpikyThistle();
            case 3:
                return new PoisonIvy();
            case 4:
                return new BurningSnapdragon();
            case 5:
                return new BloodyRose();
            case 6:
                return new ShiningSunflower();
            case 7:
                return new ProteaOfPlenty();
        }
        return new BladedLily();
    }


    public static int getNumPlants(AbstractPlayer p){
        int output = 0;
        for (AbstractOrb currentOrb : p.orbs){
            if (currentOrb instanceof PlantOrb){
                output++;
            }
        }
        return output;
    }


}
