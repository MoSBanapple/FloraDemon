package florademon.relics;

import com.evacipated.cardcrawl.mod.stslib.relics.OnApplyPowerRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChampionsBelt;
import florademon.character.FloraDemonCharacter;

import java.util.ArrayList;
import java.util.Objects;

import static florademon.FloraDemonMod.makeID;

public class DemonBlade extends BaseRelic implements OnApplyPowerRelic {
    private static final String NAME = DemonBlade.class.getSimpleName(); //The name will be used for determining the image file as well as the ID.
    public static final String ID = makeID(NAME); //This adds the mod's prefix to the relic ID, resulting in modID:MyRelic
    private static final RelicTier RARITY = RelicTier.RARE; //The relic's rarity.
    private static final LandingSound SOUND = LandingSound.CLINK; //The sound played when the relic is clicked.

    private static final int VULNERABLE_PER_WEAK = 1;

    private ArrayList<AbstractCreature> beltChecker;



    public DemonBlade() {
        super(ID, NAME,FloraDemonCharacter.Enums.CARD_COLOR, RARITY, SOUND);
        beltChecker = new ArrayList<AbstractCreature>();
    }

    @Override
    public String getUpdatedDescription() {
        return DESCRIPTIONS[0] + VULNERABLE_PER_WEAK + DESCRIPTIONS[1];
    }

    public void atBattleStartPreDraw(){
        beltChecker.clear();
    }


    @Override
    public boolean onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        AbstractPlayer p = AbstractDungeon.player;
        if (!Objects.equals(power.ID, WeakPower.POWER_ID) || target == p){
            return true;
        }


        if (power.amount > 0) {
            if (beltChecker.contains(target)){
                beltChecker.remove(target);
                return true;
            }
            addToBot(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            addToBot(new ApplyPowerAction(target, source, new VulnerablePower(target, VULNERABLE_PER_WEAK, false)));
            if (p.hasRelic(ChampionsBelt.ID) && !target.hasPower(ArtifactPower.POWER_ID)){
                beltChecker.add(target);
            }
        }

        return true;
    }

}
