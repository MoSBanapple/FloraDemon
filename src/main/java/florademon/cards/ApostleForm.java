package florademon.cards;

import basemod.helpers.BaseModCardTags;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.FloraDemonMod;
import florademon.actions.ApostleFormAction;
import florademon.character.FloraDemonCharacter;
import florademon.powers.ApostleFormPower;
import florademon.util.CardStats;
import florademon.util.TextureLoader;

import static florademon.FloraDemonMod.cardPath;
import static florademon.FloraDemonMod.enableSpoilers;

public class ApostleForm extends BaseCard {
    public static final String ID = makeID(ApostleForm.class.getSimpleName());

    private static final int DAMAGE = 8;
    private static final int UPG_DAMAGE = 3;
    private static final int BLOCK = 5;
    private static final int UPG_BLOCK = 2;
    private static final int MAGIC = 1;
    private static final int UPG_MAGIC = 1;

    private static final CardStrings cardStrings;

    private static final CardStats info = new CardStats(
            FloraDemonCharacter.Enums.CARD_COLOR, //The card color. If you're making your own character, it'll look something like this. Otherwise, it'll be CardColor.RED or something similar for a basegame character color.
            CardType.POWER, //The type. ATTACK/SKILL/POWER/CURSE/STATUS
            CardRarity.RARE, //Rarity. BASIC is for starting cards, then there's COMMON/UNCOMMON/RARE, and then SPECIAL and CURSE. SPECIAL is for cards you only get from events. Curse is for curses, except for special curses like Curse of the Bell and Necronomicurse.
            CardTarget.SELF, //The target. Single target is ENEMY, all enemies is ALL_ENEMY. Look at cards similar to what you want to see what to use.
            3 //The card's base cost. -1 is X cost, -2 is no cost for unplayable cards like curses, or Reflex.
    );

    public ApostleForm() {
        super(ID, info);
        setMagic(MAGIC, UPG_MAGIC);
        tags.add(BaseModCardTags.FORM);
        if (!enableSpoilers){
            censorSpoilers();
        }
    }

    public void censorSpoilers(){
        if (!enableSpoilers){
            this.name = cardStrings.EXTENDED_DESCRIPTION[0];
            this.loadCardImage(cardPath("power/DemonLordsBlessing.png"));
        } else {
            this.name = cardStrings.NAME;
            this.loadCardImage(cardPath("power/ApostleForm.png"));
        }
    }

    public Texture getPortraitImage(){
        if (enableSpoilers){
            return super.getPortraitImage();
        } else {
            return new Texture(cardPath("power/DemonLordsBlessing_p.png"));
        }
    }


    public float getTitleFontSize() {
        if (enableSpoilers){
            return super.getTitleFontSize();
        } else {
            return 18F;
        }
    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if (enableSpoilers) {
            addToBot(new ApostleFormAction());
        }
        addToBot(new ApplyPowerAction(p, p, new ApostleFormPower(p, magicNumber)));

    }

    static {
        cardStrings = CardCrawlGame.languagePack.getCardStrings(ID);
    }
}
