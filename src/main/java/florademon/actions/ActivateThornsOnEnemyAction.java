package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.ThornsPower;

public class ActivateThornsOnEnemyAction extends AbstractGameAction {

    private AbstractCreature thornsBearer;
    private AbstractCreature target;
    public ActivateThornsOnEnemyAction(AbstractCreature source, AbstractCreature target){
        thornsBearer = source;
        this.target = target;
    }
    @Override
    public void update() {
        if (target.isDeadOrEscaped() || !thornsBearer.hasPower(ThornsPower.POWER_ID)){
            this.isDone = true;
            return;
        }

        ThornsPower sourceThorns = (ThornsPower) thornsBearer.getPower(ThornsPower.POWER_ID);

        sourceThorns.onAttacked(new DamageInfo(target, 0), 0);
        this.isDone = true;
    }
}
