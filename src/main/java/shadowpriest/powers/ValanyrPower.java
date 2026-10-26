package shadowpriest.powers;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
public class ValanyrPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:Valanyr";
 public boolean ward=true;
 public ValanyrPower(AbstractCreature p){owner=p;amount=-1;ID=POWER_ID;name="Barricade (Val'anyr)";type=PowerType.BUFF;loadRegion("barricade");updateDescription();}
 public void breakWard(){if(ward){ward=false;flash();addToTop(new RemoveSpecificPowerAction(owner,owner,this));}}
 @Override public void updateDescription(){description="Retain Block until your Block is depleted. NL This retention ends for the combat when Block reaches #b0. Guardian Angel is independent.";}
 @SpirePatch(clz=AbstractCreature.class,method="hasPower")
 public static class Retention {
  @SpirePostfixPatch public static boolean after(boolean __result,AbstractCreature __instance,String targetID){
   if(__result || !"Barricade".equals(targetID)) return __result;
   AbstractPower power=__instance.getPower(POWER_ID);return power instanceof ValanyrPower && ((ValanyrPower)power).ward;
  }
 }
 @SpirePatch(clz=AbstractCreature.class,method="loseBlock",paramtypez={int.class,boolean.class})
 public static class Break {
  @SpirePostfixPatch public static void after(AbstractCreature __instance,int amount,boolean noAnimation){
   AbstractPower power=__instance.getPower(POWER_ID);
   if(amount>0 && __instance.currentBlock==0 && power instanceof ValanyrPower) ((ValanyrPower)power).breakWard();
  }
 }
}
