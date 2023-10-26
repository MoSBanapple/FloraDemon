package florademon.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.FocusPower;
import florademon.character.FloraDemonCharacter;
import florademon.powers.FertilityPower;

import static florademon.FloraDemonMod.makeID;

public class MeiDew extends BaseRelic{
    private static final String NAME = MeiDew.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.MAGICAL; //The sound played when the relic is clicked.
    private static final int FERT = 1;



    public MeiDew() {
        super(ID, NAME, FloraDemonCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void atPreBattle() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FertilityPower(AbstractDungeon.player, FERT), FERT));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + FERT + DESCRIPTIONS[1];
    }

}
