package florademon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.actions.ActivateThornsOnEnemyAction;
import florademon.character.FloraDemonCharacter;
import florademon.powers.LoseThornsPower;
import florademon.util.CardStats;

public class BriarStorm extends BaseCard {
    public static final String ID = makeID(BriarStorm.class.getSimpleName());

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 11;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);;


    // Define the base description and placeholders for dynamic values
    private static final String BASE_DESCRIPTION = cardStrings.DESCRIPTION;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public BriarStorm() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        this.isMultiDamage = true;
    }

    /*
    public void updateDamageWithThorns(){
        AbstractPlayer p = AbstractDungeon.player;
        int upgradeDamage = 0;
        if (this.upgraded){
            upgradeDamage = UPG_DAMAGE;
        }
        this.baseDamage = DAMAGE + upgradeDamage;
        if (p.hasPower(ThornsPower.POWER_ID)){
            this.baseDamage += p.getPower(ThornsPower.POWER_ID).amount;
        }
    }*/

    @Override
    public void use(AbstractPlayer player, AbstractMonster monster) {


        addToBot(new DamageAllEnemiesAction(player, this.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        AbstractDungeon.getMonsters().monsters.forEach((currentMonster) -> {
            addToBot(new ActivateThornsOnEnemyAction(player, currentMonster));
        });

    }


}
