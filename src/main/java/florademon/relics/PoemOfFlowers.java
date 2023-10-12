package florademon.relics;

import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import florademon.actions.DrawNurtureCardAction;
import florademon.cards.Gardenwork;
import florademon.character.FloraDemonCharacter;

import static florademon.FloraDemonMod.makeID;

public class PoemOfFlowers extends BaseRelic{
    private static final String NAME = PoemOfFlowers.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.BOSS; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.FLAT; //The sound played when the relic is clicked.
    private static final int numTurnsToActivate = 3;
    private static final String starterReplaceID = WhiteLily.ID;

    public PoemOfFlowers() {
        super(ID, NAME, FloraDemonCharacter.Enums.CARD_COLOR, RARITY, SOUND);
        this.counter = numTurnsToActivate;
    }

    public void atTurnStartPostDraw(){
        if (this.counter <= 0){
            return;
        }
        this.flash();
        this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        Gardenwork c = new Gardenwork();
        c.setCostForTurn(0);
        UnlockTracker.markCardAsSeen(c.cardID);
        this.addToBot(new MakeTempCardInHandAction(c));
        this.counter--;
    }

    public void atPreBattle(){
        this.counter = numTurnsToActivate;
    }

    @Override
    public void obtain() {
        // Replace the starter relic, or just give the relic if starter isn't found
        if (AbstractDungeon.player.hasRelic(starterReplaceID)) {
            for (int i=0; i<AbstractDungeon.player.relics.size(); ++i) {
                if (AbstractDungeon.player.relics.get(i).relicId.equals(starterReplaceID)) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }

    @Override
    public boolean canSpawn()
    {
        // Only spawn if player has the starter relic
        return AbstractDungeon.player.hasRelic(starterReplaceID);
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + numTurnsToActivate + DESCRIPTIONS[1];
    }

}
