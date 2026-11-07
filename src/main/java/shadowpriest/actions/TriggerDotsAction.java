package shadowpriest.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.powers.ShadowDotPower;

/** Mind Flay: Insanity bonus Touch/Pain ticks. Never tick Madness or consume/extend stacks. */
public final class TriggerDotsAction extends AbstractGameAction {
    private final int repeats;
    public TriggerDotsAction(AbstractCreature target,int repeats){this.target=target;this.repeats=repeats;}
    @Override public void update(){
        if(repeats>0&&!target.isDeadOrEscaped()&&!target.isDying){
            // Insert the tick actions ahead of the remaining card cleanup and next round.
            java.util.List<AbstractGameAction> ticks=new java.util.ArrayList<>();
            for(AbstractPower power:target.powers){
                if(power.amount<=0 || !(ShadowDotPower.TOUCH.equals(power.ID)||ShadowDotPower.PAIN.equals(power.ID)))continue;
                int damage;
                if(power instanceof ShadowDotPower)damage=((ShadowDotPower)power).tickDamage();
                else continue;
                if(target.hasPower("Intangible")||target.hasPower("IntangiblePlayer"))damage=Math.min(1,damage);
                ticks.add(shadowpriest.details.DetailsTracker.dot(new DotTickAction(target,damage,ShadowDotPower.TOUCH.equals(power.ID)),power));
            }
            if(repeats>1)addToTop(new TriggerDotsAction(target,repeats-1));
            for(int i=ticks.size()-1;i>=0;i--)addToTop(ticks.get(i));
        }
        isDone=true;
    }
}
