package florademon.orbs;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.OrbStrings;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import florademon.FloraDemonMod;
import florademon.actions.RemoveSpecificOrbAction;
import florademon.powers.FertilityPower;

import static florademon.FloraDemonMod.makeID;

public class ProteaOfPlenty extends PlantOrb{

    public static final String ID = makeID(ProteaOfPlenty.class.getSimpleName());
    private static final OrbStrings orbString = CardCrawlGame.languagePack.getOrbString(ID);
    public static final String NAME = orbString.NAME;
    public static final String[] DESCRIPTIONS = orbString.DESCRIPTION;

    private static final String IMG_PATH = FloraDemonMod.orbPath("ProteaOfPlenty.png");
    private static final int DRAW = 1;

    private static final int STARTINGCOUNT = 5;

    public ProteaOfPlenty() {
        super(ID, NAME, STARTINGCOUNT, STARTINGCOUNT, "", "", IMG_PATH);
    }

    /**
     * To be used when the plant is activated.
     */
    @Override
    public void onActivate() {
        applyFocus();
        AbstractPlayer p = AbstractDungeon.player;
        basePassiveAmount--;
        if (basePassiveAmount <= 0){
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificOrbAction(this));
        }
        AbstractDungeon.actionManager.addToTop(new DrawCardAction(DRAW));
        applyFocus();
        updateDescription();

    }
    public void applyFocus(){
        passiveAmount = Math.max(0, basePassiveAmount );
        evokeAmount = Math.max(0, baseEvokeAmount);
    }

    @Override
    public void updateDescription() {
        applyFocus();
        description = DESCRIPTIONS[0] + DRAW + DESCRIPTIONS[1] + passiveAmount;
        if (passiveAmount > 1){
            description += DESCRIPTIONS[2];
        } else {
            description += DESCRIPTIONS[3];
        }
    }

    @Override
    public void onEvoke() {

    }

    @Override
    public AbstractOrb makeCopy() {
        return new ProteaOfPlenty();
    }

    @Override
    public void playChannelSFX() {

    }
}
