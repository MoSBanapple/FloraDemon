package florademon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.character.FloraDemonCharacter;
import florademon.powers.LoseThornsPower;
import florademon.util.CardStats;

public class ScatterRoses extends BaseCard {
    public static final String ID = makeID(ScatterRoses.class.getSimpleName());

    private static final int DAMAGE = 7;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 0;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public ScatterRoses() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.isMultiDamage = true;
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(MAGIC,UPG_MAGIC);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAllEnemiesAction(p, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        int numEnemies = 0;
        for (AbstractMonster currentMonster : AbstractDungeon.getMonsters().monsters){
            if (!currentMonster.isDeadOrEscaped()){
                numEnemies++;
            }
        }
        addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, magicNumber*numEnemies)));
        addToBot(new ApplyPowerAction(p, p, new LoseThornsPower(p, magicNumber*numEnemies)));
    }
}
