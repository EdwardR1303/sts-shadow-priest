package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
public class ShadowCrashPower extends AbstractPower {
 private static int serial;
 public ShadowCrashPower(AbstractCreature owner,int amount){this.owner=owner;this.amount=amount;ID="shadowpriest:ShadowCrash:"+serial++;name="Shadow Crash";type=PowerType.BUFF;loadRegion("nightmare");description="At the start of your next turn, apply #b"+amount+" Touch to ALL enemies.";}
 @Override public void atStartOfTurn(){for(AbstractMonster m:AbstractDungeon.getMonsters().monsters)if(!m.isDeadOrEscaped())addToBot(ShadowDotPower.apply(m,owner,ShadowDotPower.TOUCH,amount));addToBot(new RemoveSpecificPowerAction(owner,owner,this));}
}
