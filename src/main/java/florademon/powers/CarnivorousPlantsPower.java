package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.powers.AbstractPower;
import florademon.orbs.PlantOrb;

import static florademon.FloraDemonMod.makeID;

public class CarnivorousPlantsPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(CarnivorousPlantsPower.class.getSimpleName());
    public static final String ID = POWER_ID;
    private static final AbstractPower.PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = false;

    public CarnivorousPlantsPower(AbstractCreature owner, int amount) {
        super(POWER_ID, TYPE, TURN_BASED, owner, amount);
    }

    public void updateDescription() {
        this.description = DESCRIPTIONS[0] + amount + DESCRIPTIONS[1];
    }

    public void atEndOfTurnPreEndTurnCards(boolean isPlayer){
        flash();
        if (!(owner instanceof AbstractPlayer)){
            return;
        }
        AbstractPlayer p = (AbstractPlayer) owner;
        p.orbs.forEach((currentOrb) -> {
            if (currentOrb instanceof PlantOrb){
                //addToTop(new GainBlockAction(p, p, amount,true));
                addToTop(new DamageRandomEnemyAction(new DamageInfo(p, amount, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.BLUNT_LIGHT));

            }


        });
        CardCrawlGame.sound.play("EVENT_VAMP_BITE", 0.05F);
    }
    @Override
    public AbstractPower makeCopy() {
        return new CarnivorousPlantsPower(owner, amount);
    }
}
