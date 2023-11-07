package florademon.actions;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import florademon.modifiers.ApplyRetainModifier;
import florademon.modifiers.NurtureModifier;

import java.util.ArrayList;

import static florademon.FloraDemonMod.makeID;

public class NurtureInHandAction extends AbstractGameAction {
    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    int numCardsToNurture;
    int nurturesPerCard;
    boolean isRandom;
    boolean shouldRetain;
    public NurtureInHandAction(int numCards, int nurturesPerCard, boolean isRandom, boolean applyRetain){
        this.numCardsToNurture = numCards;
        this.nurturesPerCard = nurturesPerCard;
        this.isRandom = isRandom;
        this.shouldRetain = applyRetain;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hand.isEmpty() || AbstractDungeon.getCurrRoom().isBattleEnding() || !NurtureModifier.isAnyNurturable(p.hand.group)) {
            this.isDone = true;
            return;
        }

        String applyRetainID = makeID(ApplyRetainModifier.class.getSimpleName());
        if (!this.isRandom){
            SelectCardsInHandAction selectCards = new SelectCardsInHandAction(numCardsToNurture, TEXT[0], NurtureModifier::isNurturable, (cardList) -> {
                cardList.forEach((currentCard) -> {
                    addToTop(new NurtureAction(currentCard, nurturesPerCard));
                    if (shouldRetain && CardModifierManager.getModifiers(currentCard,applyRetainID).isEmpty()){
                        CardModifierManager.addModifier(currentCard, new ApplyRetainModifier());
                    }
                });
            });
            addToTop(selectCards);
        } else {
            ArrayList<AbstractCard> nurturableCards = new ArrayList<AbstractCard>();
            for (AbstractCard card : p.hand.group){
                if (NurtureModifier.isNurturable(card)) {
                    nurturableCards.add(card);
                }
            }
            for (int i = 0; i < numCardsToNurture; i++){
                if (nurturableCards.isEmpty()){
                    break;
                }
                AbstractCard targetCard = nurturableCards.get(AbstractDungeon.cardRandomRng.random(0,nurturableCards.size()-1));
                addToTop(new NurtureAction(targetCard, nurturesPerCard));
                if (shouldRetain && CardModifierManager.getModifiers(targetCard,applyRetainID).isEmpty()){
                    CardModifierManager.addModifier(targetCard, new ApplyRetainModifier());
                }
                nurturableCards.remove(targetCard);
            }
        }
        p.hand.refreshHandLayout();
        this.isDone = true;

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID(NurtureInHandAction.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
    }
}
