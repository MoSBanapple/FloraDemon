package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DiscardAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

public class ReseedAction extends AbstractGameAction {

    private int numNurtures;

    public ReseedAction(int howManyNurtures){
        numNurtures = howManyNurtures;
    }

    public ReseedAction(){
        this(1);
    }
    @Override
    public void update() {
        int theSize = AbstractDungeon.player.hand.size();
        this.addToTop(new DrawNurtureCardAction(theSize));
        this.addToTop(new DiscardAction(AbstractDungeon.player, AbstractDungeon.player, theSize, false));


        this.isDone = true;
    }
}
