package florademon.patches;


import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.ending.CorruptHeart;
import florademon.character.FloraDemonCharacter;
import florademon.powers.WhitePhantomPower;

@SpirePatch(
        clz = CorruptHeart.class,
        method = "usePreBattleAction"
)
public class HeartPreBattlePatch {


    public static void Postfix(CorruptHeart __instance){
        if (AbstractDungeon.player instanceof FloraDemonCharacter){
            AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(__instance, __instance, new WhitePhantomPower(__instance,9)));
        }
    }
}
