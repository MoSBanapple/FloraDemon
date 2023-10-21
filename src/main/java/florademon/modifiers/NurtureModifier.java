package florademon.modifiers;

import basemod.abstracts.AbstractCardModifier;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.mod.stslib.util.extraicons.ExtraIcons;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import florademon.FloraDemonMod;
import florademon.util.TextureLoader;

import static florademon.FloraDemonMod.*;

public class NurtureModifier extends AbstractCardModifier {

    private static final int DAMAGE = 3;
    private static final int BLOCK = 3;
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
        if (card.cost != -1){
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
        int multiplier = (getCostMultiplier(card)*numNurtures);
        if (card.type == AbstractCard.CardType.ATTACK) {
            multiplier = multiplier * DAMAGE;
        } else if (card.type == AbstractCard.CardType.SKILL){
            multiplier = multiplier * BLOCK;
        } else if (card.type == AbstractCard.CardType.POWER) {
            multiplier = multiplier * DRAW;
        } else {
            return rawDescription;
        }
        String multString = String.valueOf(multiplier);
        if (card.cost == -1){
            multString += "X";
        }
        String toAppend = " NL " + FloraDemonMod.modID.toLowerCase() +  ":Nurtured";
        if (numNurtures > 1){
            toAppend += " (" + numNurtures + ")";
        }
        if (card.type == AbstractCard.CardType.ATTACK){
            toAppend += ": Deals " + multString + " more damage.";
        }
        else if (card.type == AbstractCard.CardType.SKILL) {
            toAppend += ": Gain " + multString + " Block.";
        } else {
            if (numNurtures > 1) {
                toAppend += ": Draw " + multString + " cards.";
            } else {
                toAppend += ": Draw a card.";
            }
        }
        return rawDescription + toAppend;
    }

    @Override
    public void onRender(AbstractCard card, SpriteBatch sb) {
        if (card.type == AbstractCard.CardType.ATTACK || card.type == AbstractCard.CardType.SKILL || card.type == AbstractCard.CardType.POWER) {
            Texture modIcon = TextureLoader.getTexture(modifierPath("NurtureModifier.png"));
            if (numNurtures > 1) {
                ExtraIcons.icon(modIcon).text(String.valueOf(numNurtures)).textOffsetX(3).drawColor(new Color(1, 1, 1, card.transparency)).render(card);
            } else {
                ExtraIcons.icon(modIcon).render(card);
            }
        }
    }

    public void onApplyPowers(AbstractCard card){
        card.initializeDescription();
    }

    @Override
    public void onSingleCardViewRender(AbstractCard card, SpriteBatch sb) {
        Texture modIcon = TextureLoader.getTexture(modifierPath("NurtureModifier.png"));
        if (numNurtures > 1) {
            ExtraIcons.icon(modIcon).text(String.valueOf(numNurtures)).textOffsetX(3).drawColor(new Color(1, 1, 1, card.transparency)).render(card);
        } else {
            ExtraIcons.icon(modIcon).render(card);
        }
    }

    @Override
    public AbstractCardModifier makeCopy() {
        return new NurtureModifier(numNurtures);
    }
}
