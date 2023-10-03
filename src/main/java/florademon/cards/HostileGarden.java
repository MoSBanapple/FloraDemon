package florademon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.actions.RegenerativeAcidAction;
import florademon.character.FloraDemonCharacter;
import florademon.util.CardStats;

import java.util.ArrayList;

public class HostileGarden extends BaseCard {
    public static final String ID = makeID(HostileGarden.class.getSimpleName());

    private static final int DAMAGE = 3;
    private static final int UPG_DAMAGE = 1;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;
    private static final int THORNS = 2;
    private static final int UPG_THORNS = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public HostileGarden() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setMagic(MAGIC,UPG_MAGIC);
        setCustomVar("THORNS",THORNS,UPG_THORNS);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        ArrayList<AbstractMonster> monsters = AbstractDungeon.getMonsters().monsters;
        int numAttacking = 0;
        for (int i = 0; i < monsters.size(); i++){
            AbstractMonster targetMonster = monsters.get(i);
            if (targetMonster == null || targetMonster.isDeadOrEscaped()){
                continue;
            }
            if (targetMonster.getIntentBaseDmg() >= 0){
                addToBot(new ApplyPowerAction(p, p, new DexterityPower(p, magicNumber)));
                addToBot(new ApplyPowerAction(p, p, new ThornsPower(p, customVar("THORNS"))));
                addToBot(new WaitAction(0.1F));
            }
        }




    }
}
