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
import florademon.powers.LoseThornsPower;

import static florademon.FloraDemonMod.makeID;

public class ThistleTea extends BasePotion {

    public static final String ID = makeID(ThistleTea.class.getSimpleName());

    private static final Color LIQUID_COLOR = CardHelper.getColor(20, 150, 20);
    private static final Color HYBRID_COLOR = CardHelper.getColor(20, 200, 20);
    private static final Color SPOTS_COLOR = null;
    private static final int THORNS = 10;

    public ThistleTea() {
        super(ID, THORNS, PotionRarity.COMMON, PotionSize.SPIKY, LIQUID_COLOR, HYBRID_COLOR, SPOTS_COLOR);
    }

    @Override
    public String getDescription() {
        return potionStrings.DESCRIPTIONS[0] + potency + potionStrings.DESCRIPTIONS[1];
    }

    @Override
    public void use(AbstractCreature abstractCreature) {
        if (AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new ThornsPower(AbstractDungeon.player, potency), potency));
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new LoseThornsPower(AbstractDungeon.player, potency), potency));
        }

    }

    @Override
    public void addAdditionalTips() {
        //Adding a tooltip for Strength
        this.tips.add(new PowerTip(TipHelper.capitalize(GameDictionary.THORNS.NAMES[0]), GameDictionary.keywords.get(GameDictionary.THORNS.NAMES[0])));
    }
}
