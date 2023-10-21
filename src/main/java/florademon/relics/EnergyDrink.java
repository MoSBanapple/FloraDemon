package florademon.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.UpgradeShineEffect;
import com.megacrit.cardcrawl.vfx.cardManip.ShowCardBrieflyEffect;
import florademon.actions.NurtureAction;
import florademon.character.FloraDemonCharacter;
import florademon.powers.FertilityPower;

import java.util.ArrayList;

import static florademon.FloraDemonMod.makeID;

public class EnergyDrink extends BaseRelic{
    private static final String NAME = EnergyDrink.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.COMMON; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.
    private static final int NUMNURTURES = 2;



    public EnergyDrink() {
        super(ID, NAME,  FloraDemonCharacter.Enums.CARD_COLOR,RARITY, SOUND);
    }

    public void onEquip(){
        AbstractPlayer p = AbstractDungeon.player;
        ArrayList<AbstractCard> possibleCards = new ArrayList<AbstractCard>(p.masterDeck.getCardsOfType(AbstractCard.CardType.ATTACK).group);
        possibleCards.addAll(p.masterDeck.getCardsOfType(AbstractCard.CardType.SKILL).group);
        possibleCards.addAll(p.masterDeck.getCardsOfType(AbstractCard.CardType.POWER).group);
        ArrayList<AbstractCard> nurturedCards = new ArrayList<AbstractCard>();

        for (int i = 0; i < NUMNURTURES; i++) {
            if (!possibleCards.isEmpty()) {
                AbstractCard theCard = (AbstractCard) possibleCards.get(AbstractDungeon.miscRng.random(0, possibleCards.size() - 1));
                NurtureAction nurture = new NurtureAction(theCard, 1);
                nurture.update();
                nurturedCards.add(theCard);
                /*
                AbstractDungeon.player.bottledCardUpgradeCheck(theCard);
                AbstractDungeon.effectsQueue.add(new UpgradeShineEffect((float) Settings.WIDTH / 2.0F, (float) Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffectsQueue.add(new ShowCardBrieflyEffect(theCard.makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                 */
                possibleCards.remove(theCard);
            }
        }

        if (!nurturedCards.isEmpty()) {
            if (nurturedCards.size() == 1) {
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)nurturedCards.get(0));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)nurturedCards.get(0)).makeStatEquivalentCopy()));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            } else {
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)nurturedCards.get(0));
                AbstractDungeon.player.bottledCardUpgradeCheck((AbstractCard)nurturedCards.get(1));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)nurturedCards.get(0)).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F - AbstractCard.IMG_WIDTH / 2.0F - 20.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new ShowCardBrieflyEffect(((AbstractCard)nurturedCards.get(1)).makeStatEquivalentCopy(), (float)Settings.WIDTH / 2.0F + AbstractCard.IMG_WIDTH / 2.0F + 20.0F * Settings.scale, (float)Settings.HEIGHT / 2.0F));
                AbstractDungeon.topLevelEffects.add(new UpgradeShineEffect((float)Settings.WIDTH / 2.0F, (float)Settings.HEIGHT / 2.0F));
            }
        }
    }
    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + NUMNURTURES + DESCRIPTIONS[1];
    }

}
