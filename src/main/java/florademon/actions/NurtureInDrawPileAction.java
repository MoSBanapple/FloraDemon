package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;

public class NurtureInDrawPileAction extends AbstractGameAction {

    private int numToNurture;
    private boolean random;

    public NurtureInDrawPileAction(int numToNurture, boolean nurtureRandomly) {
        this.numToNurture = numToNurture;
        this.random = nurtureRandomly;
    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.drawPile.isEmpty()){
            this.isDone = true;
            return;
        }
        ArrayList<AbstractCard> cardsToNurture = new ArrayList<AbstractCard>();
        if (random){
            ArrayList<AbstractCard> eligibleCards = new ArrayList<AbstractCard>(p.drawPile.getAttacks().group);
            eligibleCards.addAll(p.drawPile.getSkills().group);
            eligibleCards.addAll(p.drawPile.getPowers().group);
            for (int i = 0; i < numToNurture; i++){
                if (eligibleCards.isEmpty()){
                    break;
                }
                AbstractCard targetCard = eligibleCards.get(AbstractDungeon.cardRandomRng.random(eligibleCards.size() - 1));
                cardsToNurture.add(targetCard);
                eligibleCards.remove(targetCard);
            }
        } else {
            int cardsToCheck = Math.min(numToNurture,p.drawPile.size());
            cardsToNurture = new ArrayList<AbstractCard>(p.drawPile.group.subList(0,cardsToCheck));

        }
        cardsToNurture.forEach((c)-> {
            if (c.type == AbstractCard.CardType.ATTACK || c.type == AbstractCard.CardType.SKILL || c.type == AbstractCard.CardType.POWER){
                addToBot(new NurtureAction(c,1));
            }
        });

        this.isDone = true;
    }
}
