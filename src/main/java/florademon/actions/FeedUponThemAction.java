package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import florademon.orbs.PlantOrb;
import florademon.powers.FertilityPower;

public class FeedUponThemAction extends AbstractGameAction {
    private DamageInfo info;
    private int fertilizeAmount;
    public FeedUponThemAction(AbstractCreature target, DamageInfo info, int howMuchFertility) {
        this.info = info;
        this.setValues(target, info);
        this.fertilizeAmount = howMuchFertility;
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
    }

    public void update() {
        if (this.target != null) {
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.SLASH_HEAVY));
            this.target.damage(this.info);
            AbstractPlayer p = AbstractDungeon.player;
            if (((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) {
                this.addToBot(new ApplyPowerAction(p, p, new FertilityPower(p,fertilizeAmount)));
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }

        this.isDone = true;
    }
}
