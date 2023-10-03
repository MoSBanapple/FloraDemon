package florademon.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.defect.ChannelAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import florademon.actions.ActivatePlantAction;
import florademon.actions.BloomAction;
import florademon.character.FloraDemonCharacter;
import florademon.orbs.HardyDandelion;
import florademon.util.CardStats;

public class NagiRoots extends BaseCard {
    public static final String ID = makeID(NagiRoots.class.getSimpleName());

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 11;
    private static final int UPG_BLOCK = 3;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 1;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            2 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public NagiRoots() {
        super(ID, info);
        setCustomVar("BLOOM", 1);
        setBlock(BLOCK,UPG_BLOCK);
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        addToBot(new BloomAction());
    }
}
