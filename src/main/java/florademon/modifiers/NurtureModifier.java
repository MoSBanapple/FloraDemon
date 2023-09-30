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

public class NurtureModifier extends AbstractCardModifier {

    private static final int DAMAGE = 4;
    private static final int BLOCK = 4;
    private static final int DRAW = 1;
    private int numNurtures;
    public NurtureModifier(int howManyNurtures){
        this.numNurtures = Math.max(0,howManyNurtures);
    }

    public int getNumNurtures(){
        return numNurtures;
    }

    public void setNumNurtures(int howManyNurtures){
        this.numNurtures = Math.max(0,howManyNurtures);
    }

    public String identifier(AbstractCard card){
        return makeID(NurtureModifier.class.getSimpleName());
    }

    private int getCostMultiplier(AbstractCard card){
        int multiplier = 1;
        if (card.cost == -1){
            multiplier = Math.max(1,card.energyOnUse);
        } else {
            multiplier = Math.max(1,card.costForTurn);
        }
        return multiplier;
    }

    @Override
    public float modifyDamage(float damage, DamageInfo.DamageType type, AbstractCard card, AbstractMonster target) {
        int multiplier = getCostMultiplier(card);
        return damage + DAMAGE*multiplier*numNurtures;
    }

    public void onUse(AbstractCard card, AbstractCreature target, UseCardAction action) {
        int multiplier = getCostMultiplier(card)*numNurtures;
        if (card.type == AbstractCard.CardType.SKILL) {
            AbstractDungeon.actionManager.addToBottom(new GainBlockAction(AbstractDungeon.player, AbstractDungeon.player,multiplier*BLOCK));
        } else if (card.type == AbstractCard.CardType.POWER){
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(multiplier*DRAW));
        }
    }

    public String modifyDescription(String rawDescription, AbstractCard card){
        int multiplier = getCostMultiplier(card)*numNurtures;
        String toAppend = " NL Nurtured";
        if (numNurtures > 1){
            toAppend += "(" + numNurtures + ")";
        }
        if (card.type == AbstractCard.CardType.SKILL) {
            toAppend += ": Gain " + multiplier*BLOCK + " Block.";
        } else if (card.type == AbstractCard.CardType.POWER){
            toAppend += ": Draw " + multiplier*DRAW + " cards.";
        }
        return rawDescription + toAppend;
    }
    @Override
    public AbstractCardModifier makeCopy() {
        return new NurtureModifier(numNurtures);
    }
}
