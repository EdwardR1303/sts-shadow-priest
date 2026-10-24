package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
/** Unlike vanilla Draw Reduction, stacks reduce next-turn draw rather than extend duration. */
public class InsightDebtPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:InsightDebt";
 public InsightDebtPower(AbstractCreature p,int value){owner=p;amount=value;ID=POWER_ID;name="Insight Debt";type=PowerType.DEBUFF;AbilityPowerArt.apply(this,"insight.jpg");updateDescription();}
 @Override public void onInitialApplication(){AbstractDungeon.player.gameHandSize-=amount;}
 @Override public void stackPower(int value){super.stackPower(value);AbstractDungeon.player.gameHandSize-=value;updateDescription();}
 @Override public void atStartOfTurnPostDraw(){addToBot(new RemoveSpecificPowerAction(owner,owner,POWER_ID));}
 @Override public void onRemove(){AbstractDungeon.player.gameHandSize+=amount;}
 @Override public void updateDescription(){description="Draw #b"+amount+" fewer cards at the start of your next turn.";}
}
