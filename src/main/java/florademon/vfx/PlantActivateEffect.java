package florademon.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

import java.util.ArrayList;
import java.util.Collections;

import static florademon.FloraDemonMod.*;

public class PlantActivateEffect extends AbstractGameEffect {
    private static final float FRAME_INTERVAL = 0.05F;
    private static final int FRAMES = 18;

    public static final float DURATION = FRAME_INTERVAL*FRAMES;

    public static ArrayList<Texture> sprites;

    private float x;
    private float y;
    private int framesRemaining;
    private Texture currentFrame;

    private AbstractOrb orb;




    static {
        sprites = getSprites();
    }

    public PlantActivateEffect(AbstractOrb orb, float duration){
        this.orb = orb;
        this.duration = duration;
        this.startingDuration = duration;
        framesRemaining = FRAMES-1;
        currentFrame = sprites.get(framesRemaining);




    }
    public PlantActivateEffect(AbstractOrb orb){
        this(orb, DURATION);

    }

    public void update(){
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            this.isDone = true;
        }
        if (this.duration < framesRemaining*FRAME_INTERVAL && framesRemaining > 0){
            framesRemaining -= 1;
            currentFrame = sprites.get(framesRemaining);
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setColor(Color.WHITE);
        sb.draw(this.currentFrame, this.orb.cX - (this.currentFrame.getWidth()/1.8f), this.orb.cY - (this.currentFrame.getHeight()/2f), this.currentFrame.getWidth(), this.currentFrame.getHeight());
    }

    @Override
    public void dispose() {

    }



    public static ArrayList<Texture> getSprites(){
        ArrayList<Texture> output = new ArrayList<Texture>();
        for (int i = 0; i < FRAMES; i++){
            String fileName = "PlantEffect/tile0";
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
