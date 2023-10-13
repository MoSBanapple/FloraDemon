package florademon.character;

import basemod.abstracts.CustomEnergyOrb;
import basemod.abstracts.CustomPlayer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.MathUtils;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Strike_Red;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.cutscenes.CutscenePanel;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.ScreenShake;
import com.megacrit.cardcrawl.localization.CharacterStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.screens.CharSelectInfo;
import com.brashmonkey.spriter.Player;
import florademon.animation.CustomSpriterAnimation;
import florademon.animation.MyAnimationListener;
import florademon.cards.*;
import florademon.relics.WhiteLily;

import java.util.ArrayList;
import java.util.List;

import static florademon.FloraDemonMod.characterPath;
import static florademon.FloraDemonMod.makeID;

public class FloraDemonCharacter extends CustomPlayer {
    //Stats
    public static final int ENERGY_PER_TURN = 3;
    public static final int MAX_HP = 75;
    public static final int STARTING_GOLD = 99;
    public static final int CARD_DRAW = 5;
    public static final int ORB_SLOTS = 4;

    //Strings
    private static final String ID = makeID("FloraDemon"); //This should match whatever you have in the CharacterStrings.json file
    private static final CharacterStrings characterStrings = CardCrawlGame.languagePack.getCharacterString(ID);
    private static final String[] NAMES = characterStrings.NAMES;
    private static final String[] TEXT = characterStrings.TEXT;

    //Image file paths
    private static final String SHOULDER_1 = characterPath("polcashoulder.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String SHOULDER_2 = characterPath("polcashoulder2.png");
    private static final String APOSTLE_SHOULDER_1 = characterPath("apolcashoulder.png"); //Shoulder 1 and 2 are used at rest sites.
    private static final String APOSTLE_SHOULDER_2 = characterPath("apolcashoulder2.png");
    private static final String CORPSE = characterPath("corpse.png"); //Corpse is when you die.

    private static final String ENERGY_ORB = characterPath("cardback/energy_orb.png");
    private static final String ENERGY_ORB_P = characterPath("cardback/energy_orb_p.png");
    private static final String SMALL_ORB = characterPath("cardback/energy_orb.png");
    public boolean isApostleFormActive;

    private Player.PlayerListener currentListener;

    public static final String[] orbTextures = {
            characterPath("orb/layer1.png"),
                    characterPath("orb/layer2.png"),
                            characterPath("orb/layer3.png"),
                                    characterPath("orb/layer4.png"),
                    characterPath("orb/layer5.png"),
                    characterPath("orb/layer6.png"),
            characterPath("orb/layer1d.png"),
            characterPath("orb/layer2d.png"),
            characterPath("orb/layer3d.png"),
            characterPath("orb/layer4d.png"),
            characterPath("orb/layer5d.png")
    };
    public static class Enums {
        //These are used to identify your character, as well as your character's card color.
        //Library color is basically the same as card color, but you need both because that's how the game was made.
        @SpireEnum
        public static AbstractPlayer.PlayerClass FLORADEMON;
        @SpireEnum(name = "CHARACTER_DARKGREEN_COLOR") // These two MUST match. Change it to something unique for your character.
        public static AbstractCard.CardColor CARD_COLOR;
        @SpireEnum(name = "CHARACTER_DARKGREEN_COLOR") @SuppressWarnings("unused")
        public static CardLibrary.LibraryType LIBRARY_COLOR;
    }

    public FloraDemonCharacter() {
        super(NAMES[0], Enums.FLORADEMON,
                new CustomEnergyOrb(orbTextures, characterPath("orb/vfx.png"), null), //Energy Orb
                new CustomSpriterAnimation(characterPath("animation/DefaultAnimations.scml"))); //Animation
        currentListener = new MyAnimationListener(this);
        ((CustomSpriterAnimation)this.animation).myPlayer.addListener(currentListener);
        initializeClass(null,
                SHOULDER_2,
                SHOULDER_1,
                CORPSE,
                getLoadout(),
                20.0F, -20.0F, 200.0F, 250.0F, //Character hitbox. x y position, then width and height.
                new EnergyManager(ENERGY_PER_TURN));

        //Location for text bubbles. You can adjust it as necessary later. For most characters, these values are fine.
        dialogX = (drawX + 0.0F * Settings.scale);
        dialogY = (drawY + 220.0F * Settings.scale);
        isApostleFormActive = false;
        playAnimation("Idle");
    }

    public void toggleApostleForm(){

        if (isApostleFormActive){
            this.animation = new CustomSpriterAnimation(characterPath("animation/DefaultAnimations.scml"));
            ((CustomSpriterAnimation)this.animation).myPlayer.addListener(currentListener);
            this.shoulderImg = new Texture(SHOULDER_1);
            this.shoulder2Img = new Texture(SHOULDER_2);
        } else {
            this.animation = new CustomSpriterAnimation(characterPath("animation/ApostleAnimations.scml"));
            ((CustomSpriterAnimation)this.animation).myPlayer.addListener(currentListener);
            this.shoulderImg = new Texture(APOSTLE_SHOULDER_1);
            this.shoulder2Img = new Texture(APOSTLE_SHOULDER_2);
            playAnimation("Transform");
        }

        isApostleFormActive = !isApostleFormActive;
    }

    @Override
    public ArrayList<String> getStartingDeck() {
        ArrayList<String> retVal = new ArrayList<>();
        //List of IDs of cards for your starting deck.
        //If you want multiple of the same card, you have to add it multiple times.
        retVal.add(Strike_Flora.ID);
        retVal.add(Strike_Flora.ID);
        retVal.add(Strike_Flora.ID);
        retVal.add(Strike_Flora.ID);
        retVal.add(Defend_Flora.ID);
        retVal.add(Defend_Flora.ID);
        retVal.add(Defend_Flora.ID);
        retVal.add(Defend_Flora.ID);
        retVal.add(ThornedWhip.ID);
        retVal.add(Gardenwork.ID);

        return retVal;
    }

    @Override
    public ArrayList<String> getStartingRelics() {
        ArrayList<String> retVal = new ArrayList<>();
        //IDs of starting relics. You can have multiple, but one is recommended.
        retVal.add(WhiteLily.ID);

        return retVal;
    }

    @Override
    public AbstractCard getStartCardForEvent() {
        //This card is used for the Gremlin card matching game.
        //It should be a non-strike non-defend starter card, but it doesn't have to be.
        return new Strike_Red();
    }

    /*- Below this is methods that you should *probably* adjust, but don't have to. -*/

    @Override
    public int getAscensionMaxHPLoss() {
        return 4; //Max hp reduction at ascension 14+
    }

    @Override
    public AbstractGameAction.AttackEffect[] getSpireHeartSlashEffect() {
        //These attack effects will be used when you attack the heart.
        return new AbstractGameAction.AttackEffect[] {
                AbstractGameAction.AttackEffect.SLASH_HORIZONTAL,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_DIAGONAL,
                AbstractGameAction.AttackEffect.SLASH_VERTICAL,
                AbstractGameAction.AttackEffect.SLASH_HEAVY,
                AbstractGameAction.AttackEffect.SLASH_HEAVY
        };
    }

    private final Color cardRenderColor = Color.LIGHT_GRAY.cpy(); //Used for some vfx on moving cards (sometimes) (maybe)
    private final Color cardTrailColor = Color.LIGHT_GRAY.cpy(); //Used for card trail vfx during gameplay.
    private final Color slashAttackColor = Color.LIGHT_GRAY.cpy(); //Used for a screen tint effect when you attack the heart.
    @Override
    public Color getCardRenderColor() {
        return cardRenderColor;
    }

    @Override
    public Color getCardTrailColor() {
        return cardTrailColor;
    }

    @Override
    public Color getSlashAttackColor() {
        return slashAttackColor;
    }

    @Override
    public BitmapFont getEnergyNumFont() {
        //Font used to display your current energy.
        //energyNumFontRed, Blue, Green, and Purple are used by the basegame characters.
        //It is possible to make your own, but not convenient.
        return FontHelper.energyNumFontRed;
    }

    @Override
    public void doCharSelectScreenSelectEffect() {
        //This occurs when you click the character's button in the character select screen.
        //See SoundMaster for a full list of existing sound effects, or look at BaseMod's wiki for adding custom audio.
        CardCrawlGame.sound.playA("ATTACK_DAGGER_2", MathUtils.random(-0.2F, 0.2F));
        CardCrawlGame.screenShake.shake(ScreenShake.ShakeIntensity.MED, ScreenShake.ShakeDur.SHORT, false);
    }
    @Override
    public String getCustomModeCharacterButtonSoundKey() {
        //Similar to doCharSelectScreenSelectEffect, but used for the Custom mode screen. No shaking.
        return "ATTACK_DAGGER_2";
    }

    //Don't adjust these four directly, adjust the contents of the CharacterStrings.json file.
    @Override
    public String getLocalizedCharacterName() {
        return NAMES[0];
    }
    @Override
    public String getTitle(PlayerClass playerClass) {
        return NAMES[1];
    }
    @Override
    public String getSpireHeartText() {
        return TEXT[1];
    }
    @Override
    public String getVampireText() {
        return TEXT[2]; //Generally, the only difference in this text is how the vampires refer to the player.
    }

    /*- You shouldn't need to edit any of the following methods. -*/

    //This is used to display the character's information on the character selection screen.
    @Override
    public CharSelectInfo getLoadout() {
        return new CharSelectInfo(NAMES[0], TEXT[0],
                MAX_HP, MAX_HP, ORB_SLOTS, STARTING_GOLD, CARD_DRAW, this,
                getStartingRelics(), getStartingDeck(), false);
    }

    @Override
    public AbstractCard.CardColor getCardColor() {
        return Enums.CARD_COLOR;
    }

    @Override
    public AbstractPlayer newInstance() {
        //Makes a new instance of your character class.
        return new FloraDemonCharacter();
    }


    public void playAnimation(String name) {
        ((CustomSpriterAnimation)this.animation).myPlayer.setAnimation(name);
    }

    @Override
    public void onVictory() {
        super.onVictory();
        playAnimation("Skill");
    }

    @Override
    public void preBattlePrep() {
        if (isApostleFormActive){
            toggleApostleForm();
        }

        playAnimation("Idle");
        super.preBattlePrep();
    }

    @Override
    public void useCard(AbstractCard c, AbstractMonster monster, int energyOnUse) {
        super.useCard(c, monster, energyOnUse);
        switch (c.type) {
            case ATTACK:

                playAnimation("Attack");

                break;
            case POWER:
                playAnimation("Power");
                break;
            default:
                playAnimation("Skill");
                break;
        }
    }

    public void damage(DamageInfo info) {
        super.damage(info);
        if (this.lastDamageTaken > 0){
            playAnimation("Damaged");
        }
    }

    public Texture getCutsceneBg() {
        return ImageMaster.loadImage("images/scenes/greenBg.jpg");
    }


    @Override
    public List<CutscenePanel> getCutscenePanels() {
        List<CutscenePanel> panels = new ArrayList<>();
        if (isApostleFormActive){
            panels.add(new CutscenePanel(characterPath("ending/ending1a.png"), "ATTACK_DAGGER_2"));
            panels.add(new CutscenePanel(characterPath("ending/ending2.png")));
            panels.add(new CutscenePanel(characterPath("ending/ending3a.png")));
        } else {
            panels.add(new CutscenePanel(characterPath("ending/ending1.png"), "ATTACK_DAGGER_2"));
            panels.add(new CutscenePanel(characterPath("ending/ending2.png")));
            panels.add(new CutscenePanel(characterPath("ending/ending3.png")));
        }
        return panels;
    }
}
