package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.unique.LoseEnergyAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static florademon.FloraDemonMod.makeID;


public class LoseFertilityPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(LoseFertilityPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    public LoseFertilityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }


    /**
     * Ripped from Downfall
     */
    public void atStartOfTurn() {
        flash();
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        addToBot(new ApplyPowerAction(this.owner, this.owner, new FertilityPower(this.owner, -1*amount)));
    }

    @Override
    public AbstractPower makeCopy() {
        return new LoseFertilityPower(owner, amount);
    }
}
