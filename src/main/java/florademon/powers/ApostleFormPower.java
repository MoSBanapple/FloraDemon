package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import florademon.actions.NurtureAction;
import florademon.util.TextureLoader;

import static florademon.FloraDemonMod.characterPath;
import static florademon.FloraDemonMod.makeID;


public class ApostleFormPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(ApostleFormPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;
    public ApostleFormPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void onCardDraw(AbstractCard card) {
        if (this.amount <= 0){
            return;
        }
        this.flash();
        addToTop(new NurtureAction(card,amount));
    }

    public void onRemove(){
        AbstractDungeon.player.img = TextureLoader.getTexture(characterPath("deathpolca.png"));
    }

    public void onVictory(){
        AbstractDungeon.player.img = TextureLoader.getTexture(characterPath("deathpolca.png"));
    }

    public void updateDescription() {
        if (amount == 1){
            this.description = DESCRIPTIONS[0];
        } else {
            this.description = DESCRIPTIONS[1] + (amount) + DESCRIPTIONS[2];
        }
    }



    @Override
    public AbstractPower makeCopy() {
        return new ApostleFormPower(owner, amount);
    }
}
