package florademon.relics;

import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import florademon.actions.DrawNurtureCardAction;
import florademon.actions.NurtureInDrawPileAction;
import florademon.character.FloraDemonCharacter;

import static florademon.FloraDemonMod.makeID;

public class DarkSoil extends BaseRelic{
    private static final String NAME = DarkSoil.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.UNCOMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.

    public DarkSoil() {
        super(ID, NAME, FloraDemonCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void atTurnStart(){
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        addToBot(new NurtureInDrawPileAction(1, true));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0];
    }

}
