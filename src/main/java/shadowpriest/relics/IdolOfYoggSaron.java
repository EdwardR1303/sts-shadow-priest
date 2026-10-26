package shadowpriest.relics;
import com.megacrit.cardcrawl.actions.common.GainEnergyAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
public class IdolOfYoggSaron extends PriestRelic {
    public static final String ID="shadowpriest:IdolOfYoggSaron";
    public IdolOfYoggSaron(){super(ID,RelicTier.BOSS,"shadowpriest/images/relics/icons/yogg.jpg");}
    public static void enteredVoidform(){
        AbstractRelic r=AbstractDungeon.player.getRelic(ID);
        if(r!=null){r.flash();AbstractDungeon.actionManager.addToBottom(new GainEnergyAction(3));}
    }
    @Override public AbstractRelic makeCopy(){return new IdolOfYoggSaron();}
}
