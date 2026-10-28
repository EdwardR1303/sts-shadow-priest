package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class TitheEvasionPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:TitheEvasion";
 public TitheEvasionPower(AbstractCreature p,int value){owner=p;amount=value;ID=POWER_ID;name="Tithe Evasion";type=PowerType.BUFF;AbilityPowerArt.apply(this,"tithe_evasion.jpg");updateDescription();}
 @Override public int onLoseHp(int damage){return shadowpriest.mechanics.PlayerTurnWindow.active?Math.max(0,damage-amount):damage;}
 @Override public void updateDescription(){description="During your turn, lose #b"+amount+" less HP from each damage source, after Block. Stacks.";}
}
