package florademon.animation;
import com.brashmonkey.spriter.Animation;
import com.brashmonkey.spriter.Mainline;
import com.brashmonkey.spriter.Player;
import florademon.character.FloraDemonCharacter;

public class MyAnimationListener implements Player.PlayerListener {

    private final FloraDemonCharacter character;

    public MyAnimationListener(FloraDemonCharacter character){
        this.character = character;
    }
    @Override
    public void animationFinished(Animation animation) {
        character.playAnimation("Idle");

    }

    @Override
    public void animationChanged(Animation animation, Animation animation1) {

    }

    @Override
    public void preProcess(Player player) {

    }

    @Override
    public void postProcess(Player player) {

    }

    @Override
    public void mainlineKeyChanged(Mainline.Key key, Mainline.Key key1) {

    }
}
