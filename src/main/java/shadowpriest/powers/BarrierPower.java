package shadowpriest.powers;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;

/** The amount is a persistent portion of currentBlock, consumed BEFORE ordinary Block. */
public class BarrierPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:Barrier";
    private boolean expiringOrdinary;
    public BarrierPower(AbstractCreature owner, int reserve) {
        this.owner=owner;ID=POWER_ID;name="Barrier";
        amount=Math.max(0,Math.min(reserve,owner.currentBlock));type=PowerType.BUFF;
        AbilityPowerArt.apply(this,"power_word_barrier.jpg");updateDescription();
    }
    public static int remaining(AbstractCreature owner) {
        AbstractPower power=owner.getPower(POWER_ID);
        return power instanceof BarrierPower ? Math.max(0,Math.min(power.amount,owner.currentBlock)):0;
    }
    public static boolean active(AbstractCreature owner) { return remaining(owner)>0; }
    @Override public void stackPower(int reserve) {
        amount=Math.min(owner.currentBlock,Math.max(amount,reserve));updateDescription();
    }
    @Override public void updateDescription() {
        description="#b"+amount+" of your Block is Barrier. NL It absorbs damage before other Block and persists between turns. NL Other Block gains do not increase Barrier. Recasts refresh instead of stacking.";
    }
    private void consume(int loss) {
        if(expiringOrdinary || loss<=0 || amount<=0)return;
        amount=Math.max(0,Math.min(owner.currentBlock,amount-loss));updateDescription();
        if(amount==0) AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(owner,owner,this){
            @Override public void update(){
                // A queued removal must not remove a newly refreshed reserve.
                if(owner.getPower(POWER_ID)!=BarrierPower.this || BarrierPower.this.amount>0){isDone=true;return;}
                super.update();
            }
        });
    }
    public static void expireOrdinary(AbstractCreature owner,int requestedLoss) {
        AbstractPower current=owner.getPower(POWER_ID);
        if(!(current instanceof BarrierPower)){owner.loseBlock(requestedLoss);return;}
        BarrierPower barrier=(BarrierPower)current;
        int loss=Math.min(Math.max(0,requestedLoss),Math.max(0,owner.currentBlock-remaining(owner)));
        barrier.expiringOrdinary=true;
        try { owner.loseBlock(loss); } finally { barrier.expiringOrdinary=false; }
    }
    @SpirePatch(clz=AbstractCreature.class,method="loseBlock",paramtypez={int.class,boolean.class})
    public static class Spend {
        @SpirePostfixPatch public static void after(AbstractCreature __instance,int amount,boolean noAnimation){
            AbstractPower power=__instance.getPower(POWER_ID);
            if(power instanceof BarrierPower)((BarrierPower)power).consume(amount);
        }
    }
    @SpirePatch(clz=GameActionManager.class,method="getNextAction")
    public static class TurnBoundary {
        @SpireInstrumentPatch public static ExprEditor instrument(){return new ExprEditor(){
            @Override public void edit(MethodCall call)throws CannotCompileException{
                // Only turn-start expiration is exempt; damage and explicit removal consume Barrier.
                if(call.getMethodName().equals("loseBlock")){
                    String loss=call.getSignature().equals("()V")?"$0.currentBlock":"$1";
                    call.replace("{ shadowpriest.powers.BarrierPower.expireOrdinary($0, "+loss+"); }");
                }
            }
        };}
    }
}
