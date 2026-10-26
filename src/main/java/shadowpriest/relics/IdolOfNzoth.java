package shadowpriest.relics;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowpriest.powers.MasteryPower;
public class IdolOfNzoth extends PriestRelic {
    public static final String ID="shadowpriest:IdolOfNzoth";
    private boolean equipped;
    public IdolOfNzoth(){super(ID,RelicTier.RARE,"shadowpriest/images/relics/icons/nzoth.jpg");}
    @Override public void atPreBattle(){equipped=false;}
    public void legendaryEquipped(){if(!equipped){equipped=true;gain();}}
    private void gain(){flash();addToBot(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new MasteryPower(AbstractDungeon.player,1),1));}
    @Override public void onUseCard(AbstractCard c,UseCardAction action){if(c.type==AbstractCard.CardType.POWER)gain();}
    @Override public AbstractRelic makeCopy(){return new IdolOfNzoth();}
}
