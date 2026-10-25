package shadowpriest.actions;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import java.util.function.IntConsumer;
/** Captures only this hit's HP loss, excluding queued procs and overkill. */
public class HpDamageAction extends DamageAction {
 private final AbstractCreature victim; private final IntConsumer result; private boolean resolved;
 public HpDamageAction(AbstractCreature victim,DamageInfo info,IntConsumer result){super(victim,info,AttackEffect.FIRE);this.victim=victim;this.result=result;}
 @Override public void update(){int before=Math.max(0,victim.currentHealth);super.update();
  if(!resolved&&(isDone||victim.currentHealth<before)){resolved=true;result.accept(Math.max(0,before-Math.max(0,victim.currentHealth)));}
 }
}
