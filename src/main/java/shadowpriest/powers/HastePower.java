package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
public class HastePower extends AbstractPower {
 private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion small, large;
 private int combatAmount;
 public static final String POWER_ID="shadowpriest:Haste";
 public HastePower(AbstractCreature owner,int amount){this.owner=owner;this.amount=amount;ID=POWER_ID;name="Haste";type=PowerType.BUFF;isTurnBased=true;loadRegion("dexterity");
  if(small==null){small=PowerIconTint.tint(region48,1f,0.88f,0.1f);large=PowerIconTint.tint(region128,1f,0.88f,0.1f);}
  region48=small;region128=large;updateDescription();}
 // Use normal application hooks, then mark only the points actually gained as combat-long.
 public static ApplyPowerAction grantForCombat(AbstractCreature owner,int value){
  return new ApplyPowerAction(owner,owner,new HastePower(owner,value),value){
   private int before=-1;private boolean marked;
   @Override public void update(){
    if(before<0){AbstractPower current=owner.getPower(POWER_ID);before=current==null?0:current.amount;}
    super.update();
    if(isDone&&!marked){marked=true;AbstractPower current=owner.getPower(POWER_ID);
     if(current instanceof HastePower){HastePower haste=(HastePower)current;haste.combatAmount+=Math.max(0,haste.amount-before);haste.updateDescription();}
    }
   }
  };
 }
 @Override public void atEndOfTurn(boolean isPlayer){
  int temporary=Math.max(0,amount-combatAmount);
  if(isPlayer&&temporary>0)addToBot(new ReducePowerAction(owner,owner,this,temporary));
 }
 @Override public void updateDescription(){
  combatAmount=Math.max(0,Math.min(amount,combatAmount));int temporary=amount-combatAmount;isTurnBased=temporary>0;
  description="X-cost cards have #b"+amount+" additional effective X, including at zero energy. Stacks with Chemical X.";
  if(combatAmount>0)description+=" NL #b"+combatAmount+" lasts for combat.";
  if(temporary>0)description+=" NL #b"+temporary+" expires at the end of this turn.";
 }
}
