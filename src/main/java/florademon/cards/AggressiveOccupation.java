package florademon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.actions.defect.IncreaseMaxOrbAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.actions.BloomDrawPileToHandAction;
import florademon.actions.NurtureAction;
import florademon.character.FloraDemonCharacter;
import florademon.orbs.PlantOrb;
import florademon.powers.AggressiveOccupationPower;
import florademon.util.CardStats;

public class AggressiveOccupation extends BaseCard {
    public static final String ID = makeID(AggressiveOccupation.class.getSimpleName());

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public AggressiveOccupation() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public float getTitleFontSize() {
        return 19F;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        int wastedOrbs = Math.max(0, p.maxOrbs + magicNumber - 10);
        int orbsToMake = magicNumber - wastedOrbs;
        if (orbsToMake > 0){
            addToBot(new IncreaseMaxOrbAction(orbsToMake));
            addToBot(new ApplyPowerAction(p, p, new AggressiveOccupationPower(p, 3, orbsToMake)));
        }
        for (int i = 0; i < orbsToMake; i++){
            addToBot(new ChannelAction(PlantOrb.getRandomPlant(true)));
        }
        addToBot(new BloomDrawPileToHandAction(1));

    }
}
