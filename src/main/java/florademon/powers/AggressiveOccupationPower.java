package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.defect.DecreaseMaxOrbAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

import static florademon.FloraDemonMod.makeID;

public class AggressiveOccupationPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(AggressiveOccupationPower.class.getSimpleName());
    private static int powerIdOffset;
    private static final PowerType TYPE = PowerType.DEBUFF;
    private static final boolean TURN_BASED = true;

    private int slots;


    public AggressiveOccupationPower(AbstractPlayer player, int turns, int slotsToRemove) {
        super(POWER_ID, TYPE, TURN_BASED, player, turns);
        this.ID = POWER_ID + powerIdOffset;
        powerIdOffset++;
        this.slots = slotsToRemove;
        this.updateDescription();
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this, 1));
            if (this.amount == 1) {
                addToBot(new DecreaseMaxOrbAction(slots));
            }
        }

    }

    public void updateDescription() {
        String turnsString = DESCRIPTIONS[1];
        if (this.amount != 1){
            turnsString = DESCRIPTIONS[2];
        }
        String orbsString = DESCRIPTIONS[4];
        if (this.slots != 1){
            orbsString = DESCRIPTIONS[5];
        }
        this.description = DESCRIPTIONS[0] + this.amount + turnsString + DESCRIPTIONS[3] + this.slots + orbsString;

    }



    @Override
    public AbstractPower makeCopy() {
        return new AggressiveOccupationPower((AbstractPlayer) this.owner, this.amount, this.slots);
    }
}
