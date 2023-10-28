package florademon.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Collections;

import static florademon.FloraDemonMod.*;

public class DemonsRootsEffect extends AbstractGameEffect {
    private static final float FRAME_INTERVAL = 0.05F;
    private static final int DEFAULT_FRAMES = 23;
    private static final int ORIGIN_FRAMES = 34;
    private static final int DEFAULT_SOUND_INTERVAL = 6;
    private static final int APOSTLE_SOUND_INTERVAL = 7;
    public static final float DEFAULT_DURATION = FRAME_INTERVAL*DEFAULT_FRAMES;
    public static final float ORIGIN_DURATION = FRAME_INTERVAL*ORIGIN_FRAMES;

    public static ArrayList<Texture> attackSprites;
    public static ArrayList<Texture> originSprites;
    public static ArrayList<String> soundClips;
    private float x;
    private float y;
    private boolean isOrigin;
    private int framesRemaining;
    private Texture currentFrame;
    private int soundInterval;
    private int currentSound;
    private int maxAttacks;




    static {
        attackSprites = getAttackSprites();
        originSprites = getOriginSprites();
        soundClips = new ArrayList<String>();
        soundClips.add(SLASH_1_KEY);
        soundClips.add(SLASH_2_KEY);
        soundClips.add(SLASH_3_KEY);
        soundClips.add(MAGIC_KEY);

    }

    public DemonsRootsEffect(float x, float y, boolean isOrigin, float duration){
        this.x = x;
        this.y = y;
        this.isOrigin = isOrigin;
        this.duration = duration;
        this.startingDuration = duration;
        if (isOrigin){
            framesRemaining = ORIGIN_FRAMES-1;
            currentFrame = originSprites.get(framesRemaining);
            soundInterval = APOSTLE_SOUND_INTERVAL;
            maxAttacks = 4;
        } else {
            framesRemaining = DEFAULT_FRAMES-1;
            currentFrame = attackSprites.get(framesRemaining);
            soundInterval = DEFAULT_SOUND_INTERVAL;
            maxAttacks = 3;
        }
        currentSound = 0;



    }
    public DemonsRootsEffect(float x, float y, boolean isOrigin){
        this(x, y, isOrigin, DEFAULT_DURATION);
        if (isOrigin) {
            this.duration = ORIGIN_DURATION;
            this.startingDuration = ORIGIN_DURATION;
        }

    }
    public DemonsRootsEffect(float x, float y){
        this(x, y, false, DEFAULT_DURATION);
    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        if (this.duration < framesRemaining*FRAME_INTERVAL && framesRemaining > 0){
            framesRemaining -= 1;
            if (isOrigin){
                currentFrame = originSprites.get(framesRemaining);
            } else {
                currentFrame = attackSprites.get(framesRemaining);
            }
            if (framesRemaining%soundInterval == 4){
                if (currentSound < maxAttacks) {
                    CardCrawlGame.sound.playA(soundClips.get(currentSound), 0);
                    currentSound++;
                }

            }

        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        int test = currentFrame.getHeight();
        float test2 = AbstractDungeon.floorY;
        sb.draw(this.currentFrame, this.x - ((this.currentFrame.getWidth()/2f)*Settings.scale), 0,this.currentFrame.getWidth()*0.9f*Settings.scale, this.currentFrame.getHeight()*0.9f*Settings.scale);
    }

    @Override
    public void dispose() {

    }


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
}
