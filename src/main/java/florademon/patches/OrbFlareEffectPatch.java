package florademon.patches;

import com.badlogic.gdx.graphics.Color;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.powers.ThornsPower;
import com.megacrit.cardcrawl.vfx.combat.OrbFlareEffect;
import florademon.powers.PrickledPower;

@SpirePatch(
        clz = OrbFlareEffect.class,
        method = "setColor"
)
public class OrbFlareEffectPatch {
    public static void Postfix(OrbFlareEffect __instance, OrbFlareEffect.OrbFlareColor ___flareColor, Color ___color, Color ___color2){
        if (___flareColor == OrbFlareColorPatch.PLANT){
            ___color = Color.FOREST.cpy();
            ___color2 = Color.GREEN.cpy();
        }
    }
}
