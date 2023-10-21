package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.GainStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class ApplyTempStrengthDownAction extends AbstractGameAction {
    private AbstractCreature source;
    private AbstractCreature target;
    private int amount;
    public ApplyTempStrengthDownAction(AbstractCreature target, AbstractCreature source, int amount){
        this.source = source;
        this.target = target;
        this.amount = amount;

    }


    @Override
    public void update() {
        if (amount == 0){
            this.isDone = true;
            return;
        }
        if (!target.hasPower(ArtifactPower.POWER_ID)){
            addToTop(new ApplyPowerAction(target, source, new GainStrengthPower(target, amount), amount, true, AbstractGameAction.AttackEffect.NONE));
        }
        addToTop(new ApplyPowerAction(target, source, new StrengthPower(target, -amount), -amount, true, AbstractGameAction.AttackEffect.NONE));
        this.isDone = true;
    }
}
