package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static florademon.FloraDemonMod.makeID;

public class WhitePhantomPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID(WhitePhantomPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public int maxAmount;

    public WhitePhantomPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        maxAmount = amount;
    }
    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
        } else {
            this.description = DESCRIPTIONS[2];
        }

    }


    public void atStartOfTurn() {
        this.amount = this.maxAmount;
        this.updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new WhitePhantomPower(owner, amount);
    }
}
