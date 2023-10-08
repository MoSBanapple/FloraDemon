package florademon.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.actions.BloomAction;
import florademon.character.FloraDemonCharacter;
import florademon.orbs.ShiningSunflower;

import static florademon.FloraDemonMod.makeID;

public class SunflowerSeeds extends BaseRelic{
    private static final String NAME = SunflowerSeeds.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.


    public SunflowerSeeds() {
        super(ID, NAME, FloraDemonCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void atBattleStartPreDraw(){
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new ChannelAction(new ShiningSunflower()));
        addToBot(new BloomAction());
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
