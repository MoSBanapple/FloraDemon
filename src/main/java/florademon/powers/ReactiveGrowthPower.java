package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;

import static florademon.FloraDemonMod.makeID;

public class ReactiveGrowthPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID(ReactiveGrowthPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public ReactiveGrowthPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void wasHPLost(DamageInfo info, int damageAmount) {
        if (damageAmount > 0) {
            this.flash();
            this.addToTop(new ApplyPowerAction(this.owner, this.owner, new ThornsPower(this.owner, this.amount), this.amount));
        }

    }

    @Override
    public AbstractPower makeCopy() {
        return new ReactiveGrowthPower(owner, amount);
    }
}
