package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import com.megacrit.cardcrawl.powers.PoisonPower;
import florademon.orbs.PoisonIvy;

public class PoisonIvyAction extends AbstractGameAction {
    private PoisonIvy thisPlant;
    public PoisonIvyAction(PoisonIvy inputPlant){
        thisPlant = inputPlant;
        amount = thisPlant.passiveAmount;
        actionType = ActionType.DEBUFF;

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
            if (!currentMonster.isDeadOrEscaped() && currentMonster.currentHealth > target.currentHealth){
                target = currentMonster;
            }
        });
        PoisonPower poison = new PoisonPower(target,AbstractDungeon.player,amount);
        addToTop(new ApplyPowerAction(target, AbstractDungeon.player, poison, amount, true,AttackEffect.POISON));
        this.isDone = true;
    }
}
