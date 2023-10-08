package florademon.orbs;

import basemod.abstracts.CustomOrb;

public  abstract class PlantOrb extends CustomOrb {


    public PlantOrb(String ID, String NAME, int basePassiveAmount, int baseEvokeAmount, String passiveDescription, String evokeDescription, String imgPath) {
        super(ID, NAME, basePassiveAmount, baseEvokeAmount, passiveDescription, evokeDescription, imgPath);
    }

    /**
     * To be used when the plant is activated.
     */
    public abstract void onActivate();




}
