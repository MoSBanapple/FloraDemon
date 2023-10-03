package florademon.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Insight;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import florademon.character.FloraDemonCharacter;
import florademon.util.CardStats;

public class SetSoil extends BaseCard {
    public static final String ID = makeID(SetSoil.class.getSimpleName());

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 2;
    private static final int UPG_MAGIC = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public SetSoil() {
        super(ID, info);
        setBlock(BLOCK,UPG_BLOCK);
        setMagic(MAGIC,UPG_MAGIC);
        this.cardsToPreview = new SuddenGrowth();
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        for (int i = 0; i < magicNumber; i++){
            AbstractCard card = new SuddenGrowth();
            this.addToBot(new MakeTempCardInDrawPileAction(card, 1, true, true, false));
        }
    }
}
