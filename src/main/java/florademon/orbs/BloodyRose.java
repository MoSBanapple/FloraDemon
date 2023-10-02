package florademon.orbs;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.FloraDemonMod;
import florademon.powers.FertilityPower;
import florademon.powers.LoseThornsPower;

import static florademon.FloraDemonMod.makeID;

public class BloodyRose extends PlantOrb{

    public static final String ID = makeID(BloodyRose.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbString.NAME;
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final String IMG_PATH = FloraDemonMod.orbPath("BloodyRose.png");
    private static final int THORNS = 2;
    private static final int FERT_THORNS = 0;

    public BloodyRose() {
        super(ID, NAME, THORNS, THORNS, "", "", IMG_PATH);
    }

    /**
     * To be used when the plant is activated.
     */
    @Override
    public void onActivate() {
        applyFocus();
        AbstractPlayer p = AbstractDungeon.player;
        AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p, p, new ThornsPower(p,basePassiveAmount)));
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
        return new BloodyRose();
    }

    @Override
    public void playChannelSFX() {

    }
}
