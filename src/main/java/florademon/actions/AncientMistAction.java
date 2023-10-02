package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.BouncingFlaskAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.vfx.combat.PotionBounceEffect;

public class AncientMistAction extends AbstractGameAction {
    private AbstractCreature target;
    private int poison;
    private int weak;
    private int numTimes;

    public AncientMistAction(AbstractCreature target, int amountToPoison, int amountToWeak, int numTimes){
        this.target = target;
        this.poison = amountToPoison;
        this.weak = amountToWeak;
        this.numTimes = numTimes;
    }
    @Override
    public void update() {
        if (this.target == null) {
            this.isDone = true;
        } else if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
            AbstractDungeon.actionManager.clearPostCombatActions();
            this.isDone = true;
        } else {
            if (this.numTimes > 1 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
                --this.numTimes;
                AbstractMonster randomMonster = AbstractDungeon.getMonsters().getRandomMonster((AbstractMonster)null, true, AbstractDungeon.cardRandomRng);
                this.addToTop(new AncientMistAction(randomMonster, this.poison, this.weak, this.numTimes));
                this.addToTop(new VFXAction(new PotionBounceEffect(this.target.hb.cX, this.target.hb.cY, randomMonster.hb.cX, randomMonster.hb.cY), 0.4F));
            }

            if (this.target.currentHealth > 0) {
                this.addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new PoisonPower(this.target, AbstractDungeon.player, this.poison), this.poison, true, AttackEffect.POISON));
                this.addToTop(new ApplyPowerAction(this.target, AbstractDungeon.player, new WeakPower(this.target, this.weak, false), this.weak, AttackEffect.NONE));
                this.addToTop(new WaitAction(0.1F));
            }

            this.isDone = true;
        }
    }
}
