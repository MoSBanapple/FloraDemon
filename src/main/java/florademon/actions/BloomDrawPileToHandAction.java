package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.cards.BaseCard;

import java.util.ArrayList;

public class BloomDrawPileToHandAction extends AbstractGameAction {
    private int numCard;

    public BloomDrawPileToHandAction(int numToDraw){
        numCard = numToDraw;
    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.drawPile.isEmpty()){
            this.isDone = true;
            return;
        }
        ArrayList<AbstractCard> bloomCards = getBloomCardsInDrawPile();
        int numCardsToDraw = Math.min(bloomCards.size(), numCard);
        for (int i = 0; i < numCardsToDraw; i++){
            AbstractCard targetCard = bloomCards.get(i);
            if (p.hand.size() >= 10){
                p.drawPile.moveToDiscardPile(targetCard);
                p.createHandIsFullDialog();
            }else {
                p.drawPile.removeCard(targetCard);
                p.hand.addToTop(targetCard);
            }
            p.hand.refreshHandLayout();
            p.hand.applyPowers();
        }

        this.isDone = true;
    }


    public static ArrayList<AbstractCard> getBloomCardsInDrawPile(){
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> bloomCards = new ArrayList<AbstractCard>();
        if (p.drawPile.isEmpty()){
            return bloomCards;
        }
        p.drawPile.group.forEach((c) -> {
            if (c instanceof BaseCard){
                if (((BaseCard) c).customVar("BLOOM") == 1){
                    bloomCards.add(c);
                }
            }
        });
        return bloomCards;
    }
}
