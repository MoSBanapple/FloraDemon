package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import florademon.actions.BloomAction;

import static florademon.FloraDemonMod.makeID;

public class FloweringSeasonPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(FloweringSeasonPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;

    private static final int numCardsForBloom = 5;
    private static final boolean TURN_BASED = false;


    private int numBlooms;
    public FloweringSeasonPower(AbstractCreature owner, int howManyBlooms){
        super(POWER_ID, TYPE, TURN_BASED, owner, numCardsForBloom);
        numBlooms = howManyBlooms;
        this.updateDescription();
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + this.amount;
        if (this.amount == 1) {
            this.description += DESCRIPTIONS[2];
        } else {
            this.description += DESCRIPTIONS[1];
        }

        if (this.numBlooms == 1){
            this.description += DESCRIPTIONS[4];
        } else {
            this.description += DESCRIPTIONS[3];
        }

    }

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.numBlooms += stackAmount;
        this.updateDescription();
    }

    public void onUseCard(AbstractCard card, UseCardAction action) {
        --this.amount;
        if (this.amount == 0) {
            this.flash();
            this.amount = numCardsForBloom;
            this.addToBot(new BloomAction(this.numBlooms));
        }

        this.updateDescription();
    }

    public void atStartOfTurn() {
        this.amount = numCardsForBloom;
        this.updateDescription();
    }

    @Override
    public AbstractPower makeCopy() {
        return new FloweringSeasonPower(owner, this.amount);
    }
}
