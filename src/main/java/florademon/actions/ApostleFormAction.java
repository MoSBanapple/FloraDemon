package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.cards.DemonsRoots;
import florademon.util.TextureLoader;

import java.util.ArrayList;

import static florademon.FloraDemonMod.characterPath;

public class ApostleFormAction extends AbstractGameAction {

    public ApostleFormAction(){

    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> allCards = new ArrayList<AbstractCard>(p.drawPile.group);
        allCards.addAll(p.hand.group);
        allCards.addAll(p.discardPile.group);
        allCards.addAll(p.exhaustPile.group);
        allCards.forEach((c) -> {
            if (c instanceof DemonsRoots){
                ((DemonsRoots) c).turnIntoOriginRoots();
            }
        });
        p.img = TextureLoader.getTexture(characterPath("deathpolca_apostle.png"));
        this.isDone = true;
    }
}
