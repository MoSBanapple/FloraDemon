package florademon.orbs;

import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import florademon.FloraDemonMod;
import florademon.actions.BladedLilyAction;
import florademon.powers.FertilityPower;

import static florademon.FloraDemonMod.makeID;

public class BladedLily extends PlantOrb{

    public static final String ID = makeID(BladedLily.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbString.NAME;
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final String IMG_PATH = FloraDemonMod.orbPath("BladedLily.png");
    private static final int DAMAGE = 4;
    private static final int FERT_DAMAGE = 1;

    public BladedLily() {
        super(ID, NAME, DAMAGE, DAMAGE, "", "", IMG_PATH);
    }

    /**
     * To be used when the plant is activated.
     */
    @Override
    public void onActivate() {
        applyFocus();
        AbstractDungeon.actionManager.addToTop(new BladedLilyAction(this));
        super.onActivate();
    }
    public void applyFocus(){
        AbstractPower power = AbstractDungeon.player.getPower(FertilityPower.POWER_ID);
        if (power != null) {
            passiveAmount = Math.max(0, basePassiveAmount + (power.amount*FERT_DAMAGE));
            evokeAmount = Math.max(0, baseEvokeAmount + (power.amount*FERT_DAMAGE));
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
        return new BladedLily();
    }

    @Override
    public void playChannelSFX() {

    }
}
