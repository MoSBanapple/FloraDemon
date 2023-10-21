package florademon.actions;

import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.modifiers.NurtureModifier;

import java.util.ArrayList;

import static florademon.FloraDemonMod.makeID;

public class DiscardDrawPileAction extends AbstractGameAction {
    private int numCards;
    private boolean excludeNurtured;

    public DiscardDrawPileAction(int howManyCards, boolean excludeNurtured){
        numCards = howManyCards;
        this.excludeNurtured = excludeNurtured;
    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> discardTargets = new ArrayList<AbstractCard>();
        for (AbstractCard currentCard : p.drawPile.group){
            if (excludeNurtured && CardModifierManager.hasModifier(currentCard, makeID(florademon.modifiers.NurtureModifier.class.getSimpleName()))){
                continue;
            }
            discardTargets.add(currentCard);
            if (discardTargets.size() >= numCards){
                break;
            }
        }
        for (AbstractCard cardToDiscard : discardTargets){
            p.drawPile.moveToDiscardPile(cardToDiscard);
        }
        this.isDone = true;
    }
}
