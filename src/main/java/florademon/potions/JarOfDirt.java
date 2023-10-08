package florademon.potions;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardHelper;
import com.megacrit.cardcrawl.helpers.GameDictionary;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import florademon.FloraDemonMod;
import florademon.powers.FertilityPower;
import florademon.powers.LoseThornsPower;

import static florademon.FloraDemonMod.makeID;

public class JarOfDirt extends BasePotion {

    public static final String ID = makeID(JarOfDirt.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(80, 52, 42);
    private static final Color HYBRID_COLOR = CardHelper.getColor(90, 62, 52);
    private static final Color SPOTS_COLOR = CardHelper.getColor(70, 42, 32);

    private static final int FERT = 2;
    public JarOfDirt() {
        super(ID, FERT, PotionRarity.COMMON, PotionSize.JAR, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new FertilityPower(AbstractDungeon.player, potency), potency));
        }

    }

    @Override
    public void addAdditionalTips() {
        //Adding a tooltip for Strength
        this.tips.add(new PowerTip(FloraDemonMod.keywords.get("FERTILITY").PROPER_NAME, FloraDemonMod.keywords.get("FERTILITY").DESCRIPTION));
    }
}
