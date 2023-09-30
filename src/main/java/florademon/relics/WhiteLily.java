package florademon.relics;

import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.actions.BloomAction;
import florademon.character.FloraDemonCharacter;
import florademon.orbs.BladedLily;

import static florademon.FloraDemonMod.makeID;

public class WhiteLily  extends BaseRelic{
    private static final String NAME = "WhiteLily"; //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.STARTER; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final int LILY_NUM = 1;
    private static final int BLOOM_NUM = 2;

    public WhiteLily() {
        super(ID, NAME, FloraDemonCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void atBattleStartPreDraw(){
        this.flash();
        addToBot(new ChannelAction(new BladedLily()));
        addToBot(new BloomAction());
        addToBot(new BloomAction());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + LILY_NUM + DESCRIPTIONS[1] + BLOOM_NUM + DESCRIPTIONS[2];
    }
}
