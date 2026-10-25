package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
public class InsidiousIrePower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:InsidiousIre";
 public InsidiousIrePower(AbstractCreature owner,int amount){this.owner=owner;this.amount=amount;ID=POWER_ID;name="Insidious Ire";type=PowerType.BUFF;AbilityPowerArt.apply(this,"insidious_ire.jpg");updateDescription();}
 @Override public void atStartOfTurn(){for(AbstractMonster m:AbstractDungeon.getMonsters().monsters)if(!m.isDeadOrEscaped()&&ShadowDotPower.count(m)==3)addToBot(new ApplyPowerAction(m,owner,new VulnerablePower(m,amount,false),amount));}
 @Override public void updateDescription(){description="At the start of your turn, apply #b"+amount+" Vulnerable to each enemy with Touch, Pain, and Madness.";}
}
