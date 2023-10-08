package florademon.cards;

import basemod.patches.com.megacrit.cardcrawl.cards.AbstractCard.MultiCardPreview;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.actions.watcher.ChooseOneAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import florademon.actions.BloomAction;
import florademon.actions.FlexibleDiscoveryAction;
import florademon.cards.special.*;
import florademon.character.FloraDemonCharacter;
import florademon.util.CardStats;

import java.util.ArrayList;
import java.util.function.Consumer;

public class SeedsOfTheFuture extends BaseCard {
    public static final String ID = makeID(SeedsOfTheFuture.class.getSimpleName());

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public SeedsOfTheFuture() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.exhaust = true;
        MultiCardPreview.add(this, new KnightsSeed(), new GladiatorsSeed(), new StudentsSeed(), new SoldiersSeed(), new PrincesSeed());
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        ArrayList<AbstractCard> seedChoices = getSeeds(this);
        Consumer<AbstractCard> callBack = (c) -> {
            ArrayList<AbstractCard> seedsToShuffle = getSeeds(c);
            seedsToShuffle.forEach((thisSeed) -> {
                this.addToBot(new MakeTempCardInDrawPileAction(thisSeed, 1, true, true));
            });
        };
        addToBot(new FlexibleDiscoveryAction(seedChoices,callBack, false));
    }

    public ArrayList<AbstractCard> getSeeds(AbstractCard seedToExclude){
        ArrayList<AbstractCard> seeds = new ArrayList<AbstractCard>();
        if (!(seedToExclude instanceof KnightsSeed)) {
            seeds.add(new KnightsSeed());
        }
        if (!(seedToExclude instanceof GladiatorsSeed)) {
            seeds.add(new GladiatorsSeed());
        }
        if (!(seedToExclude instanceof StudentsSeed)) {
            seeds.add(new StudentsSeed());
        }
        if (!(seedToExclude instanceof SoldiersSeed)) {
            seeds.add(new SoldiersSeed());
        }
        if (!(seedToExclude instanceof PrincesSeed)) {
            seeds.add(new PrincesSeed());
        }
        if (this.upgraded){
            seeds.forEach(AbstractCard::upgrade);
        }
        return seeds;
    }
}
