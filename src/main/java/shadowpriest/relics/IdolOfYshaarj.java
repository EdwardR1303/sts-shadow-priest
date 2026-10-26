package shadowpriest.relics;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
public class IdolOfYshaarj extends PriestRelic {
    public static final String ID="shadowpriest:IdolOfYshaarj";
    public IdolOfYshaarj(){super(ID,RelicTier.RARE,"shadowpriest/images/relics/icons/yshaarj.jpg");}
    public void paidCard(){flash();addToBot(new DrawCardAction(AbstractDungeon.player,1));}
    @Override public AbstractRelic makeCopy(){return new IdolOfYshaarj();}
}
