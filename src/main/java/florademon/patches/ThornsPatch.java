package florademon.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.NextTurnBlockPower;
import com.megacrit.cardcrawl.powers.PoisonPower;
import com.megacrit.cardcrawl.powers.ThornsPower;
import florademon.powers.AbsorbingThornsPower;
import florademon.powers.LokiThornsPower;

@SpirePatch(
        clz = ThornsPower.class,
        method = "onAttacked"
)
public class ThornsPatch {
    public static void Prefix(ThornsPower __instance, DamageInfo info, int damageAmount){
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != __instance.owner) {
            if (__instance.owner.hasPower(AbsorbingThornsPower.POWER_ID)) {
                AbsorbingThornsPower pow = (AbsorbingThornsPower) __instance.owner.getPower(AbsorbingThornsPower.POWER_ID);
                pow.flash();
                int numAbsorbing = pow.amount;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__instance.owner, __instance.owner, new NextTurnBlockPower(__instance.owner, __instance.amount * numAbsorbing)));
            }
            if (__instance.owner.hasPower(LokiThornsPower.POWER_ID)) {
                AbstractPower pow = __instance.owner.getPower(LokiThornsPower.POWER_ID);
                pow.flash();
                int numLoki = pow.amount;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(info.owner, __instance.owner, new PoisonPower(info.owner, __instance.owner, __instance.amount * numLoki), __instance.amount * numLoki, true, AbstractGameAction.AttackEffect.POISON));
            }
        }
    }
}
