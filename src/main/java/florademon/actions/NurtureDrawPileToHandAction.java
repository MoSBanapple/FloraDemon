package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

import java.util.ArrayList;
import java.util.Iterator;

import static florademon.FloraDemonMod.makeID;

public class NurtureDrawPileToHandAction extends AbstractGameAction {
    public static final String[] TEXT;
    private AbstractPlayer player;
    private int numberOfCards;
    private boolean optional;

    private int numNurtures;

    public NurtureDrawPileToHandAction(int numberOfCards, int numNurtures, boolean optional) {
        this.actionType = ActionType.CARD_MANIPULATION;
        this.duration = this.startDuration = Settings.ACTION_DUR_FAST;
        this.player = AbstractDungeon.player;
        this.numberOfCards = numberOfCards;
        this.optional = optional;
        this.numNurtures = numNurtures;
    }

    public NurtureDrawPileToHandAction(int numberOfCards, int numNurtures) {
        this(numberOfCards,numNurtures, false);
    }

    public void update() {
        if (this.duration == this.startDuration) {
            if (!this.player.drawPile.isEmpty() && this.numberOfCards > 0) {
                AbstractCard c;
                Iterator var6;
                if (this.player.drawPile.size() <= this.numberOfCards && !this.optional) {
                    ArrayList<AbstractCard> cardsToMove = new ArrayList();
                    var6 = this.player.drawPile.group.iterator();

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        cardsToMove.add(c);
                    }

                    var6 = cardsToMove.iterator();

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        if (this.player.hand.size() == 10) {
                            this.player.drawPile.moveToDiscardPile(c);
                            this.player.createHandIsFullDialog();
                        } else {
                            this.player.drawPile.moveToHand(c, this.player.drawPile);
                        }
                        addToBot(new NurtureAction(c, 1));
                    }


                    this.isDone = true;
                } else {
                    CardGroup temp = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
                    var6 = this.player.drawPile.group.iterator();

                    while(var6.hasNext()) {
                        c = (AbstractCard)var6.next();
                        temp.addToTop(c);
                    }

                    temp.sortAlphabetically(true);
                    temp.sortByRarityPlusStatusCardType(false);
                    if (this.numberOfCards == 1) {
                        if (this.optional) {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[0]);
                        } else {
                            AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[0], false);
                        }
                    } else if (this.optional) {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, true, TEXT[1] + this.numberOfCards + TEXT[2]);
                    } else {
                        AbstractDungeon.gridSelectScreen.open(temp, this.numberOfCards, TEXT[1] + this.numberOfCards + TEXT[2], false);
                    }

                    this.tickDuration();
                }
            } else {
                this.isDone = true;
            }
        } else {
            if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
                Iterator var1 = AbstractDungeon.gridSelectScreen.selectedCards.iterator();

                while(var1.hasNext()) {
                    AbstractCard c = (AbstractCard)var1.next();
                    addToTop(new NurtureAction(c, numNurtures));
                    if (this.player.hand.size() == 10) {
                        this.player.drawPile.moveToDiscardPile(c);
                        this.player.createHandIsFullDialog();
                    } else {
                        this.player.drawPile.moveToHand(c, this.player.drawPile);
                    }
                }

                AbstractDungeon.gridSelectScreen.selectedCards.clear();
                AbstractDungeon.player.hand.refreshHandLayout();
            }

            this.tickDuration();
        }
    }

    static {
        TEXT = CardCrawlGame.languagePack.getUIString(makeID(NurtureDrawPileToHandAction.class.getSimpleName())).TEXT;
    }
}
