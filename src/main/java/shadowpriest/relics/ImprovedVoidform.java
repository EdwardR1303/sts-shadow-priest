package shadowpriest.relics;
import com.megacrit.cardcrawl.relics.AbstractRelic;
public class ImprovedVoidform extends PriestRelic {
    public static final String ID="shadowpriest:ImprovedVoidform";
    public ImprovedVoidform(){super(ID,RelicTier.BOSS,"shadowpriest/images/relics/voidform_source.jpg");}
    @Override public boolean canSpawn(){return super.canSpawn()&&VoidformRelic.current()!=null;}
    @Override public void atPreBattle(){counter=-1;}
    @Override public void onVictory(){counter=-1;}
    @Override public AbstractRelic makeCopy(){return new ImprovedVoidform();}
}
