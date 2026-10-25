package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
public class InsightLockPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:InsightUsed";
 public InsightLockPower(AbstractCreature owner){this.owner=owner;ID=POWER_ID;name="Shadowy Insight Used";amount=-1;type=PowerType.BUFF;loadRegion("draw");description="No more Shadowy Insight cards can be played this turn.";}
 @Override public void atStartOfTurn(){addToBot(new RemoveSpecificPowerAction(owner,owner,this));}
}
