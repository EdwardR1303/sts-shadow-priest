package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.actions.PriestHpLossAction;
public class ShadowMendDebtPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:ShadowMendDebt";
 public ShadowMendDebtPower(AbstractCreature p,int value){owner=p;amount=value;ID=POWER_ID;name="Shadow Mend Debt";type=PowerType.DEBUFF;AbilityPowerArt.apply(this,"shadow_mend.jpg");updateDescription();}
 @Override public void atStartOfTurn(){flash();addToBot(new PriestHpLossAction(amount));}
 @Override public void updateDescription(){description="At the start of your turn, lose #b"+amount+" HP. Debt stacks and lasts for combat.";}
}
