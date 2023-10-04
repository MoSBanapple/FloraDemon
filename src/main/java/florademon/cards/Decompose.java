package florademon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import florademon.actions.BloomAction;
import florademon.actions.DecomposeAction;
import florademon.character.FloraDemonCharacter;
import florademon.util.CardStats;

public class Decompose extends BaseCard {
    public static final String ID = makeID(Decompose.class.getSimpleName());

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 4;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 0;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Decompose() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        this.exhaust = true;
        this.tags.add(CardTags.HEALING);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DecomposeAction(m, new DamageInfo(p, damage, DamageInfo.DamageType.NORMAL)));
    }
}
