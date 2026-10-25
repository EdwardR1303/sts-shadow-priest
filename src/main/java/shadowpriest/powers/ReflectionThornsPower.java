package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.actions.common.*;
public class ReflectionThornsPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:ReflectionThorns";
 public ReflectionThornsPower(AbstractCreature owner,int amount){this.owner=owner;this.amount=amount;ID=POWER_ID;name="Temporary Thorns";type=PowerType.BUFF;loadRegion("thorns");updateDescription();}
 @Override public int onAttacked(DamageInfo info,int damageAmount){if(info.type==DamageInfo.DamageType.NORMAL&&info.owner!=null&&info.owner!=owner)addToTop(new DamageAction(info.owner,new DamageInfo(owner,amount,DamageInfo.DamageType.THORNS)));return damageAmount;}
 @Override public void atStartOfTurn(){addToBot(new RemoveSpecificPowerAction(owner,owner,this));}
 @Override public void updateDescription(){description="When an enemy Attack hits you, deal #b"+amount+" damage back. Expires at the start of your next turn.";}
}
