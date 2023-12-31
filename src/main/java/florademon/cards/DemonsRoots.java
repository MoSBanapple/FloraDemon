package florademon.cards;

import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import florademon.actions.DemonsRootsAction;
import florademon.character.FloraDemonCharacter;
import florademon.powers.ApostleFormPower;
import florademon.powers.LoseThornsPower;
import florademon.util.CardStats;
import florademon.util.TextureLoader;
import florademon.vfx.DemonsRootsEffect;

import static florademon.FloraDemonMod.cardPath;
import static florademon.FloraDemonMod.enableSpoilers;

public class DemonsRoots extends BaseCard {
    public static final String ID = makeID(DemonsRoots.class.getSimpleName());

    private static final int DAMAGE = 9;
    private static final int UPG_DAMAGE = 4;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 3;
    private static final int UPG_MAGIC = 0;

    private static final CardStrings cardStrings;

    public boolean originRoots;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.ATTACK, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.ALL_ENEMY, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public DemonsRoots() {
        super(ID, info); //Pass the required information to the BaseCard constructor.
        this.isMultiDamage = true;
        setDamage(DAMAGE, UPG_DAMAGE); //Sets the card's damage and how much it changes when upgraded.
        setMagic(MAGIC,UPG_MAGIC);
        originRoots = false;

        if (CardCrawlGame.isInARun() && AbstractDungeon.player.hasPower(ApostleFormPower.POWER_ID) && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT){
            this.turnIntoOriginRoots();
        }
        DemonsRootsEffect preload = new DemonsRootsEffect(0f, 0f, false);
    }

    public void turnIntoOriginRoots(){
        if (originRoots){
            return;
        }
        this.baseMagicNumber++;
        this.name = cardStrings.EXTENDED_DESCRIPTION[0];
        if (this.upgraded){
            this.name += "+";
        }
        if (enableSpoilers) {
            this.loadCardImage(cardPath("attack/OriginRoots.png"));
        }

        originRoots = true;
    }

    public void revertToDemonsRoots(){
        if (!originRoots){
            return;
        }
        this.baseMagicNumber = MAGIC;
        this.name = cardStrings.NAME;
        if (this.upgraded){
            this.name += "+";
        }
        this.loadCardImage(cardPath("attack/DemonsRoots.png"));
        originRoots = false;
    }

    public Texture getPortraitImage(){
        if (originRoots && enableSpoilers){
            return new Texture(cardPath("attack/OriginRoots_p.png"));
        } else {
            return super.getPortraitImage();
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        addToTop(new DemonsRootsAction(this, p, m));
    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
