package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import florademon.FloraDemonMod;
import florademon.actions.NurtureAction;
import florademon.cards.DemonsRoots;
import florademon.character.FloraDemonCharacter;
import florademon.modifiers.NurtureModifier;
import florademon.util.TextureLoader;

import java.util.ArrayList;

import static florademon.FloraDemonMod.*;


public class ApostleFormPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(ApostleFormPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public ApostleFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onCardDraw(AbstractCard card) {
        if (this.amount <= 0 || !NurtureModifier.isNurturable(card)){
            return;
        }
        this.flash();
        addToTop(new NurtureAction(card,amount));
    }

    public void onRemove(){
        if (!enableSpoilers){
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> allCards = new ArrayList<AbstractCard>(p.drawPile.group);
        allCards.addAll(p.hand.group);
        allCards.addAll(p.discardPile.group);
        allCards.addAll(p.exhaustPile.group);
        allCards.forEach((c) -> {
            if (c instanceof DemonsRoots){
                ((DemonsRoots) c).revertToDemonsRoots();
            }
        });
        if (p instanceof FloraDemonCharacter){
            FloraDemonCharacter f = (FloraDemonCharacter) p;
            if (f.isApostleFormActive){
                f.toggleApostleForm();
            }
        }
    }

    public void onVictory() {
        AbstractPlayer p = AbstractDungeon.player;
        if (enableSpoilers){
            p.masterDeck.group.forEach((c) -> {
                if (c instanceof DemonsRoots) {
                    ((DemonsRoots) c).revertToDemonsRoots();
                }
            });
        }

    }

    public void updateDescription() {
        if (amount == 1){
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + (amount) + DESCRIPTIONS[2];
        }
        if (!FloraDemonMod.enableSpoilers){
            this.name = DESCRIPTIONS[3];
        }
    }



    @Override
    public AbstractPower makeCopy() {
        return new ApostleFormPower(owner, amount);
    }
}
