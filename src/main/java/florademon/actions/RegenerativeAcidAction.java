package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

public class RegenerativeAcidAction extends AbstractGameAction {
    private AbstractCreature source;
    private AbstractCreature target;

    public RegenerativeAcidAction(AbstractCreature source, AbstractCreature target){
        this.source = source;
        this.target = target;
    }
    @Override
    public void update() {
        if (source == null || target == null){
            this.isDone = true;
            return;
        }

        int numPoison = 0;
        if (target.hasPower(PoisonPower.POWER_ID)){
            numPoison = Math.max(0, target.getPower(PoisonPower.POWER_ID).amount);
        }
        if (numPoison == 0){
            isDone = true;
            return;
        }
        addToTop(new GainBlockAction(source, source, numPoison));
        AbstractDungeon.effectList.add(new FlashAtkImgEffect(source.hb.cX, source.hb.cY, AbstractGameAction.AttackEffect.SHIELD));
        this.isDone = true;
    }
}
