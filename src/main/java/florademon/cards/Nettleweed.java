package florademon.cards;

import basemod.abstracts.AbstractCardModifier;
import basemod.helpers.CardModifierManager;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.character.FloraDemonCharacter;
import florademon.powers.NurtureNextDrawPower;
import florademon.util.CardStats;

import java.util.ArrayList;

public class Nettleweed extends BaseCard {
    public static final String ID = makeID(Nettleweed.class.getSimpleName());

    private static final int DAMAGE = 4;
    private static final int UPG_DAMAGE = 2;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    public boolean secondPlay;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.SKILL, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.UNCOMMON, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            1 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public Nettleweed() {
        super(ID, info); //Pass the required information to the BaseCard constructor.

        setBlock(BLOCK,UPG_BLOCK); //Sets the card's damage and how much it changes when upgraded.
        secondPlay = false;
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new GainBlockAction(p, p, block));
        if (!secondPlay && p.hasPower(ThornsPower.POWER_ID)) {
            Nettleweed tmp = (Nettleweed) this.makeSameInstanceOf();
            tmp.secondPlay = true;
            tmp.purgeOnUse = true;
            AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(tmp, m, tmp.energyOnUse, true, true));
        }
    }
}
