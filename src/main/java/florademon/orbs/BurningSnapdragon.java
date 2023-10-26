package florademon.orbs;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.FloraDemonMod;
import florademon.powers.FertilityPower;

import static florademon.FloraDemonMod.makeID;

public class BurningSnapdragon extends PlantOrb{

    public static final String ID = makeID(BurningSnapdragon.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbString.NAME;
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final String IMG_PATH = FloraDemonMod.orbPath("BurningSnapdragon.png");
    private static final int STRENGTH = 1;

    public BurningSnapdragon() {
        super(ID, NAME, STRENGTH, STRENGTH, "", "", IMG_PATH);
    }

    /**
     * To be used when the plant is activated.
     */
    @Override
    public void onActivate() {
        applyFocus();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(p, p, new StrengthPower(p,basePassiveAmount)));
        super.onActivate();
    }
    public void applyFocus(){
        AbstractPower power = AbstractDungeon.player.getPower(FertilityPower.POWER_ID);
        if (power != null) {
            passiveAmount = Math.max(0, basePassiveAmount );
            evokeAmount = Math.max(0, baseEvokeAmount);
        } else {
            passiveAmount = basePassiveAmount;
            evokeAmount = baseEvokeAmount;
        }
        if (passiveAmount < 0)
            passiveAmount = 0;
        if (evokeAmount < 0)
            evokeAmount = 0;
    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESCRIPTIONS[0] + passiveAmount + DESCRIPTIONS[1];
    }

    @Override
    public void onEvoke() {

    }

    @Override
    public AbstractOrb makeCopy() {
        return new BurningSnapdragon();
    }

    @Override
    public void playChannelSFX() {

    }
}
