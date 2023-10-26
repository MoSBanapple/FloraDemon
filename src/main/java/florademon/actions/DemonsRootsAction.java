package florademon.actions;

import basemod.ReflectionHacks;
import basemod.helpers.VfxBuilder;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import florademon.cards.DemonsRoots;
import florademon.vfx.DemonsRootsEffect;

import static florademon.FloraDemonMod.vfxPath;

import java.util.ArrayList;
import java.util.Collections;

public class DemonsRootsAction extends AbstractGameAction {
    AbstractPlayer p;
    AbstractMonster m;
    DemonsRoots thisCard;

    public static ArrayList<Texture> attackSprites;

    public static ArrayList<Texture> originSprites;

    public DemonsRootsAction(DemonsRoots card, AbstractPlayer p, AbstractMonster m){
        thisCard = card;
        this.p = p;
        this.m = m;
    }
    @Override
    public void update() {
        /*
        addToBot(new DamageAllEnemiesAction(p, thisCard.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HORIZONTAL));
        addToBot(new DamageAllEnemiesAction(p, thisCard.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        addToBot(new DamageAllEnemiesAction(p, thisCard.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        if (thisCard.originRoots){
            addToBot(new DamageAllEnemiesAction(p, thisCard.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.SLASH_HEAVY));
        }
        */

        float[] coordinates = getAverageMonsterCoordinates();//TODO: Fix the jank as hell implementation here and make this an actual VFX
        DemonsRootsEffect visuals = new DemonsRootsEffect(coordinates[0], -coordinates[1], thisCard.originRoots);
        AbstractDungeon.effectsQueue.add(visuals);
        for (int i = 0; i < 3; i++) {
            DamageAllEnemiesAction damage = new DamageAllEnemiesAction(p, thisCard.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE, false);
            addToTop(damage);
        }
        if (thisCard.originRoots){
            DamageAllEnemiesAction damage = new DamageAllEnemiesAction(p, thisCard.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE, false);
            addToTop(damage);
        }
        /*
        ArrayList<Texture> spritesToUse = attackSprites;
        int attackInterval = 8;
        if (thisCard.originRoots){
            spritesToUse = originSprites;
            attackInterval = 9;
        }
        for (int i = 0; i < spritesToUse.size(); i++){
            Texture currentTexture = spritesToUse.get(i);
            AbstractGameEffect currentEffect;
            if (i%attackInterval == attackInterval-3) {
                currentEffect = new VfxBuilder(currentTexture, coordinates[0], coordinates[1] + 100, 0.05f).playSoundAt(0F, "ATTACK_HEAVY").build();
                DamageAllEnemiesAction damage = new DamageAllEnemiesAction(p, thisCard.multiDamage, DamageInfo.DamageType.NORMAL, AbstractGameAction.AttackEffect.NONE, true);
                ReflectionHacks.setPrivate(damage, AbstractGameAction.class, "duration", 0);
                addToTop(damage);
            } else {
                currentEffect = new VfxBuilder(currentTexture, coordinates[0], coordinates[1] + 100, 0.05f).build();
            }
            this.addToTop(new VFXAction(this.p, currentEffect, 0.0F));
        }
        */

        this.isDone = true;
    }

    /*
    public static ArrayList<Texture> getAttackSprites(){
        ArrayList<Texture> output = new ArrayList<Texture>();
        for (int i = 0; i <= 22; i++){
            String fileName = "DemonsRoots/tile0";
            if (i < 10){
                fileName += "0";
            }
            fileName += i + ".png";
            output.add(new Texture(vfxPath(fileName)));
        }
        Collections.reverse(output);

        return output;
    }

    public static ArrayList<Texture> getOriginSprites(){
        ArrayList<Texture> output = new ArrayList<Texture>();
        for (int i = 0; i <= 33; i++){
            String fileName = "OriginRoots/tile0";
            if (i < 10){
                fileName += "0";
            }
            fileName += i + ".png";
            output.add(new Texture(vfxPath(fileName)));
        }
        Collections.reverse(output);

        return output;
    }
    */


    public float[] getAverageMonsterCoordinates(){
        float[] output = {0, 0};
        int numMonsters = 0;
        for (AbstractMonster currentMonster : AbstractDungeon.getMonsters().monsters){
            if (currentMonster.isDeadOrEscaped()){
                continue;
            }
            output[0] += currentMonster.hb.cX;
            output[1] += currentMonster.hb.cY;
            numMonsters++;
        }
        if (numMonsters == 0){
            return output;
        }
        output[0] /= numMonsters;
        output[1] /= numMonsters;
        return output;
    }

    /*
    static {
        attackSprites = getAttackSprites();
        originSprites = getOriginSprites();
    }

     */
}
