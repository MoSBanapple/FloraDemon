package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static florademon.FloraDemonMod.makeID;


public class ScoutingStrikePower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(ScoutingStrikePower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    public ScoutingStrikePower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }


    /**
     * Ripped from Downfall
     */
    public void atStartOfTurn() {
        flash();
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.owner.hb.cX, this.owner.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        addToBot(new GainBlockAction(owner, owner, amount));
        addToBot(new ApplyPowerAction(owner, owner, new ThornsPower(owner,amount)));
        addToBot(new ApplyPowerAction(owner, owner, new LoseThornsPower(owner,amount)));
    }

    @Override
    public AbstractPower makeCopy() {
        return new ScoutingStrikePower(owner, amount);
    }
}
