package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;

import static florademon.FloraDemonMod.makeID;

public class ParalyticPoisonPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID(ParalyticPoisonPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ParalyticPoisonPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (power.ID == PoisonPower.POWER_ID && source == this.owner && target != this.owner) {
            this.flash();
            if (!target.hasPower(ArtifactPower.POWER_ID)){
                addToTop(new ApplyPowerAction(target, source, new GainStrengthPower(target, amount), amount, true, AbstractGameAction.AttackEffect.NONE));
            }
            addToTop(new ApplyPowerAction(target, source, new StrengthPower(target, -amount), -amount, true, AbstractGameAction.AttackEffect.NONE));

        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new ParalyticPoisonPower(owner, amount);
    }
}
