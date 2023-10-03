package florademon.powers;

import basemod.interfaces.CloneablePowerInterface;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import static florademon.FloraDemonMod.makeID;


public class SeliLeavesPower extends BasePower implements CloneablePowerInterface {
    public static final String POWER_ID = makeID(SeliLeavesPower.class.getSimpleName());
    private static final PowerType TYPE = PowerType.BUFF;
    private static final boolean TURN_BASED = true;
    private static int powerIdOffset;
    private AbstractCard cardToPlay;
    public SeliLeavesPower(AbstractCreature owner, AbstractCard cardToPlay) {
        super(POWER_ID, TYPE, TURN_BASED, owner, 1);
        this.ID = POWER_ID + powerIdOffset;
        powerIdOffset++;
        this.cardToPlay = cardToPlay;
    }




    public void atStartOfTurn() {
        flash();
        AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(cardToPlay, AbstractDungeon.getRandomMonster(), cardToPlay.energyOnUse, true, true));
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
    }

    public void updateDescription() {
        if (this.cardToPlay == null){
            this.description = DESCRIPTIONS[2];
            return;
        }
        this.description = DESCRIPTIONS[0] + cardToPlay.name + DESCRIPTIONS[1];
    }



    @Override
    public AbstractPower makeCopy() {
        return new SeliLeavesPower(owner, cardToPlay);
    }
}
