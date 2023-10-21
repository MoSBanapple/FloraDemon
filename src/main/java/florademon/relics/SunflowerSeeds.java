package florademon.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.actions.BloomAction;
import florademon.actions.DrawNurtureCardAction;
import florademon.character.FloraDemonCharacter;
import florademon.orbs.ShiningSunflower;

import static florademon.FloraDemonMod.makeID;

public class SunflowerSeeds extends BaseRelic{
    private static final String NAME = SunflowerSeeds.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public boolean activatedThisTurn;

    public SunflowerSeeds() {
        super(ID, NAME, FloraDemonCharacter.Enums.CARD_COLOR, RARITY, SOUND);
        activatedThisTurn = false;
    }

    public void atTurnStart(){
        activatedThisTurn = false;
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
