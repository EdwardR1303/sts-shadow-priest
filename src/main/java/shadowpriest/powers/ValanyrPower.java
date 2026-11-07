package shadowpriest.powers;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class ValanyrPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:Valanyr";
 public ValanyrPower(AbstractCreature p){owner=p;amount=-1;ID=POWER_ID;name="Barricade (Val'anyr)";type=PowerType.BUFF;loadRegion("barricade");updateDescription();}
 @Override public void updateDescription(){description="Your Block is not removed at the start of your turn. NL Lasts for combat, even after Block reaches #b0. Guardian Angel is independent.";}
 @SpirePatch(clz=AbstractCreature.class,method="hasPower")
 public static class Retention {
  @SpirePostfixPatch public static boolean after(boolean __result,AbstractCreature __instance,String targetID){
   if(__result || !"Barricade".equals(targetID)) return __result;
   AbstractPower power=__instance.getPower(POWER_ID);return power instanceof ValanyrPower;
  }
 }
}
