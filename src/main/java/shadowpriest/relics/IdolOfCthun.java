package shadowpriest.relics;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowpriest.cards.TentacleSlam;
public class IdolOfCthun extends PriestRelic {
    public static final String ID="shadowpriest:IdolOfCthun";
    public IdolOfCthun(){super(ID,RelicTier.UNCOMMON,"shadowpriest/images/relics/icons/cthun.jpg");}
    public static AbstractCard freeSlam(){AbstractCard c=new TentacleSlam();shadowpriest.mechanics.RelicCardHooks.FreeCard.mark(c);return c;}
    @Override public void atBattleStart(){flash();addToBot(new MakeTempCardInHandAction(freeSlam(),1));}
    @Override public AbstractRelic makeCopy(){return new IdolOfCthun();}
}
