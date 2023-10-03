package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import com.megacrit.cardcrawl.vfx.combat.FlashAtkImgEffect;
import florademon.cards.Uproot;
import florademon.orbs.PlantOrb;
import florademon.powers.FertilityPower;

import static florademon.FloraDemonMod.makeID;

public class UprootAction extends AbstractGameAction {
    public static final String ID = makeID(Uproot.class.getSimpleName());
    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private DamageInfo info;
    private int fertilizeAmount;
    private boolean activatePlant;
    public UprootAction(AbstractCreature target, DamageInfo info, boolean activatePlant) {
        this.info = info;
        this.setValues(target, info);
        this.actionType = ActionType.DAMAGE;
        this.duration = Settings.ACTION_DUR_FASTER;
        this.activatePlant = activatePlant;
    }

    public void update() {
        if (this.target == null){
            this.isDone = true;
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        PlantOrb targetPlant = null;
        for (int i = 0; i < p.orbs.size(); i++){
            AbstractOrb thisOrb = p.orbs.get(i);
            if (thisOrb instanceof PlantOrb){
                targetPlant = (PlantOrb) thisOrb;
                break;
            }
        }
        if (targetPlant == null){
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
            this.isDone = true;
            return;
        }
        if (activatePlant){
            addToBot(new ActivatePlantAction(targetPlant));
        }
        addToBot(new RemoveSpecificOrbAction(targetPlant));
        addToBot(new DamageAction(this.target, this.info, AbstractGameAction.AttackEffect.BLUNT_LIGHT));

        this.isDone = true;
    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("UprootAction"));
        TEXT = uiStrings.TEXT;
    }
}
