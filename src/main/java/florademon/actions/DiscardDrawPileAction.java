package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class DiscardDrawPileAction extends AbstractGameAction {
    private int numCards;
    private boolean discardBottom;
    public DiscardDrawPileAction(int howManyCards, boolean fromBottom){
        numCards = howManyCards;
        discardBottom = fromBottom;
    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        for (int i = 0; i < numCards; i++){
            if (p.drawPile.isEmpty()){
                break;
            }

            AbstractCard targetCard;
            if (discardBottom){
                targetCard = p.drawPile.getBottomCard();
            } else {
                targetCard = p.drawPile.getTopCard();
            }
            p.drawPile.moveToDiscardPile(targetCard);
        }
        this.isDone = true;
    }
}
