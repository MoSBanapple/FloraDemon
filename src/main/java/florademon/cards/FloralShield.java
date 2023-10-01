package florademon.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import florademon.character.FloraDemonCharacter;
import florademon.orbs.PlantOrb;
import florademon.util.CardStats;

import java.util.concurrent.atomic.AtomicInteger;

public class FloralShield extends BaseCard {
    public static final String ID = makeID(FloralShield.class.getSimpleName());

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int BLOCK = 4;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.COMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public FloralShield() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setBlock(BLOCK,UPG_BLOCK); //Sets the card's damage and how much it changes when upgraded.
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {

        AtomicInteger plantCount = new AtomicInteger();
        p.orbs.forEach((currentOrb) -> {
            if (currentOrb instanceof PlantOrb){
                plantCount.getAndIncrement();
            }
        });

        for (int i = 0; i < plantCount.get(); i++){
            addToBot(new GainBlockAction(p, p, block));
            addToBot(new WaitAction(0.1F));
        }

    }
}
