package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class TopOfDiscardPileToHandAction extends AbstractGameAction {
    private int numCards;
    public TopOfDiscardPileToHandAction(int howManyCards){
        numCards = howManyCards;
    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = 0; i < numCards; i++){
            if (p.discardPile.isEmpty()){
                this.isDone = true;
                return;
            }
            AbstractCard targetCard = p.discardPile.getTopCard();
            p.discardPile.removeCard(targetCard);
            p.hand.addToHand(targetCard);
            p.hand.refreshHandLayout();
        }
        this.isDone = true;
    }
}
