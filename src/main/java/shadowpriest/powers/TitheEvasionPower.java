package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class TitheEvasionPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:TitheEvasion";
 public TitheEvasionPower(AbstractCreature p,int value){owner=p;amount=value;ID=POWER_ID;name="Tithe Evasion";type=PowerType.BUFF;AbilityPowerArt.apply(this,"tithe_evasion.jpg");updateDescription();}
 @Override public void stackPower(int value){amount=Math.max(amount,value);updateDescription();}
 @Override public void updateDescription(){description="Lose #b"+amount+" less HP from Blood Magic, Shadow Word: Death, and Shadow Mend only. Copies do not stack.";}
}
