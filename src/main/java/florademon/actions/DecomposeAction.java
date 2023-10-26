package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;

import java.util.ArrayList;
import java.util.Iterator;

public class DecomposeAction extends AbstractGameAction {

    private DamageInfo info;
    private AbstractCard theCard;


    public DecomposeAction(AbstractCreature target, DamageInfo info){
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_MED;
    }
    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;

        if (this.target != null){
            AbstractDungeon.effectList.add(new FlashAtkImgEffect(this.target.hb.cX, this.target.hb.cY, AttackEffect.NONE));
            this.target.damage(this.info);
            if ((((AbstractMonster)this.target).isDying || this.target.currentHealth <= 0) && !this.target.halfDead && !this.target.hasPower("Minion")) {
                ArrayList<AbstractCard> possibleCards = new ArrayList<AbstractCard>(p.masterDeck.getCardsOfType(AbstractCard.CardType.ATTACK).group);
                possibleCards.addAll(p.masterDeck.getCardsOfType(AbstractCard.CardType.SKILL).group);
                possibleCards.addAll(p.masterDeck.getCardsOfType(AbstractCard.CardType.POWER).group);




                if (!possibleCards.isEmpty()) {
                    this.theCard = (AbstractCard)possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
                    NurtureAction nurture = new NurtureAction(this.theCard,1);
                    nurture.update();
                    AbstractDungeon.player.bottledCardUpgradeCheck(this.theCard);
                    AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
                    AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(this.theCard.makeStatEquivalentCopy()));
                }
            }

            if (AbstractDungeon.getCurrRoom().monsters.areMonstersBasicallyDead()) {
                AbstractDungeon.actionManager.clearPostCombatActions();
            }
        }
        this.isDone = true;

    }
}
