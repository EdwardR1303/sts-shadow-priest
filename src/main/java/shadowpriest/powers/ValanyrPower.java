package shadowpriest.powers;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class ValanyrPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:Valanyr";
 public boolean ward=true,rescue=true;
 public ValanyrPower(AbstractCreature p){owner=p;amount=-1;ID=POWER_ID;name="Val'anyr";priority=10000;type=PowerType.BUFF;AbilityPowerArt.apply(this,"valanyr.jpg");updateDescription();}
 @Override public int onLoseHp(int loss){
 if(rescue && owner.currentHealth>0 && loss>=owner.currentHealth){rescue=false;flash();shadowpriest.legendary.LegendaryEquipment.pulse=1f;updateDescription();return owner.currentHealth-1;}return loss;
 }
 public void breakWard(){if(ward){ward=false;flash();updateDescription();}}
 @Override public void updateDescription(){description=(ward?"Retain Block until your Block is depleted.":"Barrier shattered for this combat.")+" NL "+(rescue?"The next lethal damage leaves you at #b1 HP.":"Lethal protection spent.");}
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
