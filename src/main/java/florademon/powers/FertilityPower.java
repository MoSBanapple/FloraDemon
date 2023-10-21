package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import florademon.orbs.PlantOrb;

import static florademon.FloraDemonMod.makeID;


public class FertilityPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(FertilityPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public FertilityPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
        this.canGoNegative = true;
    }

    public void playApplyPowerSfx() {
        //CardCrawlGame.sound.play("POWER_FOCUS", 0.05F);
        updatePlantNumbers();
    }

    public void updateDescription() {
        if (this.amount > 0) {
            this.description = DESCRIPTIONS[2] + DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
            this.type = PowerType.BUFF;
        } else {
            int tmp = -this.amount;
            this.description = DESCRIPTIONS[3] + DESCRIPTIONS[0] + (tmp) + DESCRIPTIONS[1];
            this.type = PowerType.DEBUFF;
        }

    }

    private void updatePlantNumbers() {
        AbstractDungeon.player.orbs.forEach((currentOrb) -> {
            if (currentOrb instanceof PlantOrb){
                currentOrb.applyFocus();
            }
        });
    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }


        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }
        updatePlantNumbers();

    }

    public void reducePower(int reduceAmount) {
        this.fontScale = 8.0F;
        this.amount -= reduceAmount;
        if (this.amount == 0) {
            this.addToTop(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

        if (this.amount >= 999) {
            this.amount = 999;
        }

        if (this.amount <= -999) {
            this.amount = -999;
        }
        updatePlantNumbers();

    }





    @Override
    public AbstractPower makeCopy() {
        return new FertilityPower(owner, amount);
    }
}
