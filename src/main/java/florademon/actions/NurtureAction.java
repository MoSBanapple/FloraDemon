package florademon.actions;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import florademon.modifiers.NurtureModifier;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

import static florademon.FloraDemonMod.makeID;

public class NurtureAction extends AbstractGameAction {
    AbstractCard thisCard;
    int numNurtures;

    public NurtureAction(AbstractCard inputCard, int howManyNurtures){
        thisCard = inputCard;
        numNurtures = howManyNurtures;
        actionType = ActionType.SPECIAL;
    }

    @Override
    public void update() {
        String nurtureID = makeID(NurtureModifier.class.getSimpleName());
        ArrayList<AbstractCardModifier> nurtureModifiers = CardModifierManager.getModifiers(thisCard,nurtureID);
        AtomicInteger totalNurtures = new AtomicInteger();
        if (!nurtureModifiers.isEmpty()){
            nurtureModifiers.forEach((modifier) -> {
                if (modifier instanceof NurtureModifier) {
                    totalNurtures.set(totalNurtures.get() + ((NurtureModifier) modifier).getNumNurtures());
                }
            });
        }
        CardModifierManager.removeModifiersById(thisCard,nurtureID,true);
        CardModifierManager.addModifier(thisCard, new NurtureModifier(totalNurtures.get() + numNurtures));
        this.isDone = true;
    }
}
