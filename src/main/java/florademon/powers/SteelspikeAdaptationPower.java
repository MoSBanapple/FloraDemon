package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static florademon.FloraDemonMod.makeID;

public class SteelspikeAdaptationPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID(SteelspikeAdaptationPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public SteelspikeAdaptationPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount + DESCRIPTIONS[1];
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        if (target != owner || !(power instanceof ThornsPower) || power.amount <= 0 || this.amount <= 0){
            return;
        }
        flash();
        addToTop(new GainBlockAction(owner, owner, this.amount*power.amount));

    }


    @Override
    public AbstractPower makeCopy() {
        return new SteelspikeAdaptationPower(owner, amount);
    }
}
