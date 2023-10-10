package florademon.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.unique.LimitBreakAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import florademon.actions.NurtureInHandAction;
import florademon.character.FloraDemonCharacter;
import florademon.util.CardStats;

import java.util.ArrayList;

public class PathOfEvil extends BaseCard {
    public static final String ID = makeID(PathOfEvil.class.getSimpleName());

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int BLOCK = 6;
    private static final int UPG_BLOCK = 1;
    private static final int MAGIC = 0;
    private static final int UPG_MAGIC = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            0 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public PathOfEvil() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.exhaust = true;
        setMagic(MAGIC,UPG_MAGIC);

    }

    @Override
    public void upgrade() {
        super.upgrade();
        this.selfRetain = true;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        this.addToBot(new LimitBreakAction());
        AbstractDungeon.getMonsters().monsters.forEach((thisCreature) -> {
            if (thisCreature.hasPower(StrengthPower.POWER_ID)){
                int strengthAmount = thisCreature.getPower(StrengthPower.POWER_ID).amount;
                addToBot(new ApplyPowerAction(thisCreature, p, new StrengthPower(thisCreature, strengthAmount), strengthAmount));
            }
        });
    }
}
