package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
public class MadnessPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:Madness";
 public MadnessPower(AbstractCreature owner,int amount){this.owner=owner;this.amount=amount;ID=POWER_ID;name="Madness";type=PowerType.DEBUFF;AbilityPowerArt.apply(this,"shadow_word_madness.jpg");updateDescription();}
 public static int stacks(AbstractCreature target){return target!=null&&target.hasPower(POWER_ID)?Math.max(0,target.getPower(POWER_ID).amount):0;}
 public int tickDamage(){return Math.max(0,amount/2+amount%2+MasteryPower.current());}
 public void naturalTick(){if(amount<=0||owner.isDeadOrEscaped())return;int hit=tickDamage();
  if(owner.hasPower("Intangible")||owner.hasPower("IntangiblePlayer"))hit=Math.min(1,hit);
  addToBot(new shadowpriest.actions.DotTickAction(owner,hit,false));
  addToBot(new ReducePowerAction(owner,AbstractDungeon.player,this,1));
 }
 @Override public void updateDescription(){description="At the end of your turn, deal #b"+tickDamage()+" damage ignoring Block, then lose #b1 stack. Damage is half the stacks rounded up, plus Mastery.";}
}
