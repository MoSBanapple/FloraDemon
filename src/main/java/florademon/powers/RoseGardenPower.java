package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;

import static florademon.FloraDemonMod.makeID;

public class RoseGardenPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(RoseGardenPower.class.getSimpleName());
    private static int powerIdOffset;
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    private int minusThorns;


    public RoseGardenPower(AbstractPlayer player, int turns, int thornsToRemove) {
        super(POWER_ID, TYPE, TURN_BASED, player, turns);
        this.ID = POWER_ID + powerIdOffset;
        powerIdOffset++;
        this.minusThorns = thornsToRemove;
        this.updateDescription();
    }

    public void atStartOfTurn() {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            if (this.owner.hasPower("Thorns")) {

                    if ((this.owner.getPower("Thorns")).amount <= this.minusThorns) {
                        AbstractDungeon.actionManager.addToBottom((AbstractGameAction) new RemoveSpecificPowerAction(this.owner, this.owner, "Thorns"));
                    } else {
                        this.addToBot(new ApplyPowerAction(this.owner, this.owner, new ThornsPower(this.owner, -1*minusThorns)));
                    }

            }

        }

    }

    public void updateDescription() {
        String turnsString = DESCRIPTIONS[2];
        if (this.amount == 1){
            turnsString = DESCRIPTIONS[3];
        }
        this.description = DESCRIPTIONS[0] + this.minusThorns + DESCRIPTIONS[1] + this.amount + turnsString;

    }



    @Override
    public AbstractPower makeCopy() {
        return new RoseGardenPower((AbstractPlayer) this.owner, this.amount, this.minusThorns);
    }
}
