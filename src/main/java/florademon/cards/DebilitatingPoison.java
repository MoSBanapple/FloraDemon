package florademon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import florademon.character.FloraDemonCharacter;
import florademon.util.CardStats;

public class DebilitatingPoison extends BaseCard {
    public static final String ID = makeID(DebilitatingPoison.class.getSimpleName());

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 1;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;
    private static final int WEAK = 1;
    private static final int UPG_WEAK = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DebilitatingPoison() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setMagic(MAGIC,UPG_MAGIC);
        setCustomVar("WEAK",WEAK,UPG_WEAK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        addToBot(new ApplyPowerAction(m, p, new PoisonPower(m, p, magicNumber), magicNumber, AbstractGameAction.AttackEffect.POISON));
        addToBot(new ApplyPowerAction(m, p, new WeakPower(m, customVar("WEAK"), false), customVar("WEAK")));



    }
}
