package florademon.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.orbs.Lightning;
import florademon.actions.DrawNurtureCardAction;
import florademon.character.FloraDemonCharacter;

import static florademon.FloraDemonMod.makeID;

public class Rototiller extends BaseRelic{
    private static final String NAME = Rototiller.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.SHOP; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private static final int numSlots = 3;



    public Rototiller() {
        super(ID, NAME, FloraDemonCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void atBattleStartPreDraw() {
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        this.addToBot(new IncreaseMaxOrbAction(numSlots));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + numSlots + DESCRIPTIONS[1];
    }

}
