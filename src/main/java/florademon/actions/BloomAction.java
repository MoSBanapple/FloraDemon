package florademon.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.MonsterGroup;
import florademon.orbs.BladedLily;
import florademon.orbs.PlantOrb;

public class BloomAction extends AbstractGameAction {

    public BloomAction(){
        actionType = ActionType.DAMAGE;
    }

    @Override
    public void update() {
        AbstractPlayer player = AbstractDungeon.player;
        if (player == null){
            this.isDone = true;
            return;
        }
        player.orbs.forEach((currentOrb) -> {
            if (currentOrb instanceof PlantOrb){
                ((PlantOrb) currentOrb).onActivate();
            }
        });
        this.isDone = true;
    }
}
