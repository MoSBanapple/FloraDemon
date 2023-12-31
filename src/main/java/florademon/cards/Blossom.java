package florademon.cards;

import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import florademon.actions.BloomAction;
import florademon.actions.BlossomAction;
import florademon.cards.special.GrowDandelion;
import florademon.cards.special.GrowIvy;
import florademon.cards.special.GrowLily;
import florademon.cards.special.GrowThistle;
import florademon.character.FloraDemonCharacter;
import florademon.util.CardStats;

import java.util.ArrayList;

public class Blossom extends BaseCard {
    public static final String ID = makeID(Blossom.class.getSimpleName());

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Blossom() {
        super(ID, info);
        this.setCustomVar("BLOOM",1);
        setMagic(MAGIC,UPG_MAGIC);
        this.exhaust = true;
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        //addToBot(new BlossomAction(energyOnUse,upgraded,this.freeToPlayOnce));
        ArrayList<AbstractCard> plantChoices;
        for (int i = 0; i < 4; i++){
            plantChoices = new ArrayList<AbstractCard>();
            plantChoices.add(new GrowLily());
            plantChoices.add(new GrowDandelion());
            plantChoices.add(new GrowIvy());
            plantChoices.add(new GrowThistle());
            addToBot(new ChooseOneAction(plantChoices));
        }
        addToBot(new BloomAction(magicNumber));
    }
}
