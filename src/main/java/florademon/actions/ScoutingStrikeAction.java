package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.UIStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.ThoughtBubble;
import florademon.powers.ScoutingStrikePower;

import static florademon.FloraDemonMod.makeID;

public class ScoutingStrikeAction extends AbstractGameAction {

    private static final UIStrings uiStrings;
    public static final String[] TEXT;
    private int potency;
    private int thorns;
    private AbstractMonster targetMonster;

    public ScoutingStrikeAction(int potency, AbstractMonster m) {
        this.potency = potency;
        targetMonster = m;
    }

    @Override
    public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (this.targetMonster != null && this.targetMonster.getIntentBaseDmg() <= 0) {
            this.addToBot(new ApplyPowerAction(p, p, new ScoutingStrikePower(p, potency)));
        } else {
            AbstractDungeon.effectList.add(new ThoughtBubble(AbstractDungeon.player.dialogX, AbstractDungeon.player.dialogY, 3.0F, TEXT[0], true));
        }


        this.isDone = true;

    }

    static {
        uiStrings = CardCrawlGame.languagePack.getUIString(makeID("ScoutingStrikeAction"));
        TEXT = uiStrings.TEXT;
    }


}
