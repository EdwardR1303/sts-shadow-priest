package shadowpriest.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.powers.TitheEvasionPower;
/** Self HP loss; prevention is centralized in the normal player damage pipeline. */
public class PriestHpLossAction extends AbstractGameAction {
 public PriestHpLossAction(int loss){amount=loss;}
 public static void damage(AbstractPlayer p,int loss){
  if(p.isDead) return;
  p.damage(new DamageInfo(p,Math.max(0,loss),DamageInfo.DamageType.HP_LOSS));
 }
 public void update(){damage(AbstractDungeon.player,amount);isDone=true;}
}
