package florademon.actions;

import basemod.helpers.CardModifierManager;
import com.evacipated.cardcrawl.mod.stslib.actions.common.SelectCardsInHandAction;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import florademon.modifiers.ApplyRetainModifier;

import java.util.Iterator;

import static florademon.FloraDemonMod.makeID;

public class SelectPutOnDeckAction extends AbstractGameAction {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;

    private int numCards;
    private boolean random;
    private boolean refundEnergy;

    public SelectPutOnDeckAction(int howManyCards, boolean isRandom, boolean shouldRefundEnergy){
        numCards = howManyCards;
        random = isRandom;
        refundEnergy = shouldRefundEnergy;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        numCards = Math.min(p.hand.size(), numCards);

        if (this.random) {
            for(int i = 0; i < this.amount; ++i) {
                p.hand.moveToDeck(p.hand.getRandomCard(AbstractDungeon.cardRandomRng), false);
                if (refundEnergy) {
                    addToTop(new GainEnergyAction(1));
                }
            }
            this.isDone = true;
            return;
        }
        SelectCardsInHandAction selectCards = new SelectCardsInHandAction(numCards, TEXT[0],true, true, (c) -> {
            return true;
            },
                (cardList) -> {
                cardList.forEach((currentCard) -> {
                    p.hand.moveToDeck(currentCard, false);
                    p.hand.refreshHandLayout();
                    if (refundEnergy) {
                        addToTop(new GainEnergyAction(1));
                    }
                });
                cardList.clear();
            });
        addToBot(selectCards);
        this.isDone = true;


    }


    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID(SelectPutOnDeckAction.class.getSimpleName()));
        TEXT = uiStrings.TEXT;
    }
}
