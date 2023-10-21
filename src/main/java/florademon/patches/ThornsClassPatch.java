package florademon.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpireField;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.actions.ActivateThornsOnEnemyAction;
import florademon.powers.LokiThornsPower;
import florademon.relics.CrownOfThorns;

@SpirePatch(
        clz = ThornsPower.class,
        method=SpirePatch.CLASS
)
public class ThornsClassPatch {
    public static SpireField<Integer> amountHolder = new SpireField<Integer>(() -> 0);
}
