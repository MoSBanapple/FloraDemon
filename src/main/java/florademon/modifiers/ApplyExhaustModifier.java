package florademon.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.cards.AbstractCard;

import static florademon.FloraDemonMod.makeID;

public class ApplyExhaustModifier extends AbstractCardModifier {


    public ApplyExhaustModifier(){
    }

    public String identifier(AbstractCard card){
        return makeID(ApplyExhaustModifier.class.getSimpleName());
    }


    public void onInitialApplication(AbstractCard card){
        card.exhaust = true;
    }


    public String modifyDescription(String rawDescription, AbstractCard card){
        return rawDescription + " NL Exhaust.";
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyExhaustModifier();
    }
}
