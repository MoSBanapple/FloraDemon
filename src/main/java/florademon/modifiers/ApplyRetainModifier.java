package florademon.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

import static florademon.FloraDemonMod.makeID;

public class ApplyRetainModifier extends AbstractCardModifier {


    public ApplyRetainModifier(){
    }

    public String identifier(AbstractCard card){
        return makeID(ApplyRetainModifier.class.getSimpleName());
    }


    public void onInitialApplication(AbstractCard card){
        card.selfRetain = true;
    }


    public String modifyDescription(String rawDescription, AbstractCard card){
        return rawDescription + " NL Retain";
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new ApplyRetainModifier();
    }
}
