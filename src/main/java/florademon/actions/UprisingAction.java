package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.EmptyDeckShuffleAction;
import com.megacrit.cardcrawl.actions.common.PlayTopCardAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

public class UprisingAction extends AbstractGameAction {

    private int energyOnUse;
    private boolean isUpgraded;
    private boolean freeToPlayOnce;

    public UprisingAction(int energyOnUse, boolean isUpgraded, boolean freeToPlayOnce) {
        this.energyOnUse = energyOnUse;
        this.isUpgraded = isUpgraded;
        this.freeToPlayOnce = freeToPlayOnce;

    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        int numPlays = EnergyPanel.totalCount;
        if (this.energyOnUse != -1) {
            numPlays = this.energyOnUse;
        }
        if (p.hasRelic("Chemical X")) {
            numPlays += 2;
            p.getRelic("Chemical X").flash();
        }
        if (this.isUpgraded){
            numPlays += 1;
        }
        numPlays = Math.min(numPlays, p.drawPile.size() + p.discardPile.size());
        if (numPlays > 0) {
            int howManyPlayed = 0;

            for (int i = 0; i < numPlays; i++){
                addToTop(new WaitAction(0.1F));
                addToTop(new PlayTopCardAction(AbstractDungeon.getCurrRoom().monsters.getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng), false));
                addToTop(new NurtureInDrawPileAction(1, false));
            }
            if (numPlays > p.drawPile.size()){
                addToTop(new EmptyDeckShuffleAction());
            }

            if (!this.freeToPlayOnce) {
                if (numPlays < EnergyPanel.totalCount) {
                    p.energy.use(numPlays);
                } else {
                    p.energy.use(EnergyPanel.totalCount);
                }

            }
        }
        this.isDone = true;


    }
}
