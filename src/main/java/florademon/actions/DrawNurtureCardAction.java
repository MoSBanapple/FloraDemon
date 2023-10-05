package florademon.actions;


import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;

public class DrawNurtureCardAction extends AbstractGameAction {
    private int numDraws;
    private int numNurtures;
    public DrawNurtureCardAction(int amount, int howManyNurtures){
        numDraws = amount;
        numNurtures = howManyNurtures;
    }

    public DrawNurtureCardAction(int amount){
        this(amount, 1);
    }

    @Override
    public void update() {
        DrawCardAction drawAction = new DrawCardAction(numDraws, new AbstractGameAction() {
            @Override
            public void update() {
                DrawCardAction.drawnCards.forEach((c) -> {
                    addToTop(new NurtureAction(c,numNurtures));
                });
                this.isDone = true;
            }
        });
        addToTop(drawAction);
        this.isDone = true;
    }
}
