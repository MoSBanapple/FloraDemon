package florademon.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import florademon.actions.BloomAction;
import florademon.character.FloraDemonCharacter;
import florademon.powers.LoseThornsPower;

import static florademon.FloraDemonMod.makeID;

public class ReversedRoot extends BaseRelic{
    private static final String NAME = ReversedRoot.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.SOLID; //The sound played when the relic is clicked.
    private static final int thornsPerDamage = 1;


    public ReversedRoot() {
        super(ID, NAME,FloraDemonCharacter.Enums.CARD_COLOR, RARITY, SOUND);
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT){
            return;
        }
        AbstractPlayer p = AbstractDungeon.player;
        if (info.owner != null && info.type != DamageInfo.DamageType.HP_LOSS && info.type != DamageInfo.DamageType.THORNS) {
            this.flash();
            //this.addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToTop(new ApplyPowerAction(p, p, new LoseThornsPower(p, thornsPerDamage), thornsPerDamage, true));
            addToTop(new ApplyPowerAction(p, p, new ThornsPower(p, thornsPerDamage), thornsPerDamage, true));

        }
    }
/*
    public int onAttackToChangeDamage(DamageInfo info, int damageAmount) {

        return damageAmount;
    }
*/

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + thornsPerDamage + DESCRIPTIONS[1];
    }

}
