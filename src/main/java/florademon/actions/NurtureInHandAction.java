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
        if (p.hand.isEmpty() || AbstractDungeon.getCurrRoom().isBattleEnding()) {
            this.isDone = true;
            return;
        }

        String applyRetainID = makeID(ApplyRetainModifier.class.getSimpleName());

        if (!this.isRandom){
            SelectCardsInHandAction selectCards = new SelectCardsInHandAction(numCardsToNurture, TEXT[0],(cardList) -> {
                cardList.forEach((currentCard) -> {
                    addToBot(new NurtureAction(currentCard, nurturesPerCard));
                    if (shouldRetain && CardModifierManager.getModifiers(currentCard,applyRetainID).isEmpty()){
                        CardModifierManager.addModifier(currentCard, new ApplyRetainModifier());
                    }
                });
            });
            addToBot(selectCards);
        } else {
            ArrayList<AbstractCard> badCards = new ArrayList<AbstractCard>(p.hand.getCardsOfType(AbstractCard.CardType.CURSE).group);
            badCards.addAll(p.hand.getCardsOfType(AbstractCard.CardType.STATUS).group);
            for (int i = 0; i < numCardsToNurture; i++){
                AbstractCard targetCard = p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                while (badCards.contains(targetCard)){
                    targetCard = p.hand.getRandomCard(AbstractDungeon.cardRandomRng);
                }
                addToBot(new NurtureAction(targetCard, nurturesPerCard));
                if (shouldRetain && CardModifierManager.getModifiers(targetCard,applyRetainID).isEmpty()){
                    CardModifierManager.addModifier(targetCard, new ApplyRetainModifier());
                }
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
