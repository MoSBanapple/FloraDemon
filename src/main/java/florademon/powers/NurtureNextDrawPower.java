package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import florademon.actions.NurtureAction;

import static florademon.FloraDemonMod.makeID;


public class NurtureNextDrawPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(NurtureNextDrawPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public NurtureNextDrawPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onCardDraw(AbstractCard card) {
        if (this.amount <= 0 || card.type == AbstractCard.CardType.CURSE || card.type == AbstractCard.CardType.STATUS){
            return;
        }
        this.flash();
        addToTop(new NurtureAction(card,1));
        this.amount--;
        if (this.amount <= 0){
            addToTop(new RemoveSpecificPowerAction(owner,owner,POWER_ID));
        }
    }

    public void updateDescription() {
        if (amount == 1){
            this.description = DESCRIPTIONS[2];
        } else {
            this.description = DESCRIPTIONS[0] + (amount) + DESCRIPTIONS[1];
        }
    }



    @Override
    public AbstractPower makeCopy() {
        return new NurtureNextDrawPower(owner, amount);
    }
}
