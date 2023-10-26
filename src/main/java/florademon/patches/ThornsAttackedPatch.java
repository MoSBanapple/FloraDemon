package florademon.patches;


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
import florademon.powers.PrickledPower;
import florademon.relics.CrownOfThorns;

@SpirePatch(
        clz = ThornsPower.class,
        method = "onAttacked"
)
public class ThornsAttackedPatch {
    public static void Prefix(ThornsPower __instance, DamageInfo info, int damageAmount){
        ThornsClassPatch.amountHolder.set(__instance, __instance.amount);



        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != __instance.owner) {

            /*
            if (__instance.owner.hasPower(SteelspikeAdaptationPower.POWER_ID)) {
                SteelspikeAdaptationPower pow = (SteelspikeAdaptationPower) __instance.owner.getPower(SteelspikeAdaptationPower.POWER_ID);
                pow.flash();
                int numAbsorbing = pow.amount;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(__instance.owner, __instance.owner, new NextTurnBlockPower(__instance.owner, __instance.amount * numAbsorbing)));
            }
             */
            if (info.owner.hasPower(PrickledPower.POWER_ID)) {
                __instance.amount = (int) ((float) __instance.amount * 1.5f);
            }


            if (__instance.owner.hasPower(LokiThornsPower.POWER_ID)) {
                AbstractPower pow = __instance.owner.getPower(LokiThornsPower.POWER_ID);
                pow.flash();
                int numLoki = pow.amount;
                AbstractDungeon.actionManager.addToTop(new ApplyPowerAction(info.owner, __instance.owner, new PoisonPower(info.owner, __instance.owner, __instance.amount * numLoki), __instance.amount * numLoki, true, AbstractGameAction.AttackEffect.POISON));
            }
            AbstractPlayer p = AbstractDungeon.player;
            if (p == __instance.owner && p.hasRelic(CrownOfThorns.ID) && __instance.amount > 0){
                CrownOfThorns crown = (CrownOfThorns) p.getRelic(CrownOfThorns.ID);
                if (!crown.usedThisTurn){
                    crown.usedThisTurn = true;
                    crown.flash();
                    AbstractDungeon.actionManager.addToTop(new RelicAboveCreatureAction(info.owner, crown));
                    AbstractDungeon.actionManager.addToTop(new ActivateThornsOnEnemyAction(p, info.owner));
                }
            }
        }
    }

    public static void Postfix(ThornsPower __instance, DamageInfo info, int damageAmount){
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != __instance.owner) {
            if (info.owner.hasPower(PrickledPower.POWER_ID)) {
                __instance.amount = ThornsClassPatch.amountHolder.get(__instance);
            }
        }
    }
}
