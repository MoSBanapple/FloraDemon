package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import florademon.orbs.BladedLily;

public class BladedLilyAction extends AbstractGameAction {
    private BladedLily bladedLily;
    public BladedLilyAction(BladedLily inputLily){
        bladedLily = inputLily;
        amount = inputLily.passiveAmount;
        actionType = ActionType.DAMAGE;

    }

    @Override
    public void update() {
        MonsterGroup allMonsters = AbstractDungeon.getMonsters();
        target = AbstractDungeon.getRandomMonster();
        if (target == null){
            this.isDone = true;
            return;
        }
        allMonsters.monsters.forEach((currentMonster) -> {
            if (!currentMonster.isDeadOrEscaped() && currentMonster.currentHealth < target.currentHealth){
                target = currentMonster;
            }
        });
        DamageInfo info = new DamageInfo(AbstractDungeon.player, amount, DamageInfo.DamageType.THORNS);
        addToTop(new DamageAction(target,info,AttackEffect.SLASH_HORIZONTAL,true));
        this.isDone = true;
    }
}
