package shadowpriest.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.powers.TitheEvasionPower;
/** Only the three explicitly approved self-damage sources call this helper. */
public class PriestHpLossAction extends AbstractGameAction {
 public PriestHpLossAction(int loss){amount=loss;}
 public static void damage(AbstractPlayer p,int loss){
  if(p.isDead) return;
  AbstractPower tithe=p.getPower(TitheEvasionPower.POWER_ID);
  int actual=Math.max(0,loss-(tithe==null?0:tithe.amount));
  if(tithe!=null && actual<loss) tithe.flash();
  p.damage(new DamageInfo(p,actual,DamageInfo.DamageType.HP_LOSS));
 }
 public void update(){damage(AbstractDungeon.player,amount);isDone=true;}
}
