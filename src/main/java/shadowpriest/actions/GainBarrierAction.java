package shadowpriest.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import shadowpriest.powers.BarrierPower;

/** Refresh only the missing reserve. Existing ordinary Block is never converted to Barrier. */
public final class GainBarrierAction extends AbstractGameAction {
    public GainBarrierAction(AbstractCreature owner,int block){target=owner;amount=block;actionType=ActionType.BLOCK;}
    @Override public void update(){
        int reserve=BarrierPower.remaining(target),missing=Math.max(0,amount-reserve);
        if(missing>0){
            int before=target.currentBlock;
            target.addBlock(missing);
            int gained=Math.max(0,target.currentBlock-before);
            if(gained>0){
                BarrierPower barrier=new BarrierPower(target,reserve+gained);
                addToTop(new ApplyPowerAction(target,target,barrier,barrier.amount));
            }
        }
        isDone=true;
    }
}
