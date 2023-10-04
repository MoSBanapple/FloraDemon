package florademon.powers;

import basemod.helpers.CardModifierManager;
import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;
import florademon.actions.NurtureAction;
import florademon.modifiers.NurtureModifier;

import static florademon.FloraDemonMod.makeID;

public class RampantGrowthPower extends BasePower implements CloneablePowerInterface {

    public static final String POWER_ID = makeID(RampantGrowthPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public RampantGrowthPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }
    public void updateDescription() {
        if (amount == 1){
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + this.amount + DESCRIPTIONS[2];
        }

    }

    public void onUseCard(AbstractCard card, UseCardAction action){
        if (!card.purgeOnUse && this.amount > 0){
            String nurtureID = makeID(NurtureModifier.class.getSimpleName());
            if (CardModifierManager.hasModifier(card, nurtureID)){
                this.flash();
                addToTop(new NurtureAction(card, amount));
            }
        }
    }



    @Override
    public AbstractPower makeCopy() {
        return new RampantGrowthPower(owner, amount);
    }
}
