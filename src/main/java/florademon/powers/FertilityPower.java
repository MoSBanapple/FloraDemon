package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static florademon.FloraDemonMod.makeID;


public class FertilityPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(FertilityPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public FertilityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + (amount*2) + DESCRIPTIONS[1] + amount + DESCRIPTIONS[2];
    }



    @Override
    public AbstractPower makeCopy() {
        return new FertilityPower(owner, amount);
    }
}
