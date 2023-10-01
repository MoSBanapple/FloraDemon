package florademon.orbs;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import florademon.FloraDemonMod;
import florademon.powers.FertilityPower;

import static florademon.FloraDemonMod.makeID;

public class HardyDandelion extends PlantOrb{

    public static final String ID = makeID(HardyDandelion.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbString.NAME;
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final String IMG_PATH = FloraDemonMod.orbPath("HardyDandelion.png");
    private static final int BLOCK = 5;
    private static final int FERT_BLOCK = 2;

    public HardyDandelion() {
        super(ID, NAME, BLOCK, BLOCK, "", "", IMG_PATH);
    }

    /**
     * To be used when the plant is activated.
     */
    @Override
    public void onActivate() {
        applyFocus();
        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player, this.passiveAmount));
    }
    public void applyFocus(){
        AbstractPower power = AbstractDungeon.player.getPower(FertilityPower.POWER_ID);
        if (power != null) {
            passiveAmount = Math.max(0, basePassiveAmount + (power.amount*FERT_BLOCK));
            evokeAmount = Math.max(0, baseEvokeAmount + (power.amount*FERT_BLOCK));
        } else {
            passiveAmount = basePassiveAmount;
            evokeAmount = baseEvokeAmount;
        }
        if (passiveAmount < 0)
            passiveAmount = 0;
        if (evokeAmount < 0)
            evokeAmount = 0;
    }

    public void apply(){

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
        return new HardyDandelion();
    }

    @Override
    public void playChannelSFX() {

    }
}
