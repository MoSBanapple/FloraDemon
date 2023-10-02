package florademon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.actions.RegenerativeAcidAction;
import florademon.character.FloraDemonCharacter;
import florademon.powers.FertilityPower;
import florademon.util.CardStats;

public class DemonicStrength extends BaseCard {
    public static final String ID = makeID(DemonicStrength.class.getSimpleName());

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 1;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DemonicStrength() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new FertilityPower(p, magicNumber*-1)));

        addToBot(new ApplyPowerAction(p, p, new StrengthPower(p, magicNumber)));
        addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));



    }
}
