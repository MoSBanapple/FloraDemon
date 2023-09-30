package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static florademon.FloraDemonMod.makeID;


public class LoseThornsPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(LoseThornsPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final AbstractPower.PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;
    public LoseThornsPower(AbstractCreature owner, int amount) {
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
        if (this.owner.hasPower("Thorns"))
            if (!this.owner.hasPower("Artifact")) {
                if ((this.owner.getPower("Thorns")).amount <= this.amount) {
                    AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, "Thorns"));
                } else {
                    AbstractDungeon.player.getPower("Thorns").stackPower(this.amount * -1);
                }
            } else {
                this.owner.getPower("Artifact").onSpecificTrigger();
            }
    }

    @Override
    public AbstractPower makeCopy() {
        return new LoseThornsPower(owner, amount);
    }
}
