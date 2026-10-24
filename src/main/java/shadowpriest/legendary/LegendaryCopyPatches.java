package shadowpriest.legendary;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.relics.DollysMirror;
import com.megacrit.cardcrawl.events.shrines.Duplicator;
import javassist.expr.*;
public class LegendaryCopyPatches {
 @SpirePatch(clz=DollysMirror.class,method="onEquip")
 @SpirePatch(clz=Duplicator.class,method="use")
 public static class Filter {
  @SpireInstrumentPatch public static ExprEditor patch(){return new ExprEditor(){
   public void edit(FieldAccess field) throws javassist.CannotCompileException {
    if(field.isReader() && field.getFieldName().equals("masterDeck")) field.replace("{ $_ = shadowpriest.legendary.LegendaryEquipment.copyableDeck(); }");
   }
  };}
  @SpirePrefixPatch public static SpireReturn<Void> before(){return LegendaryEquipment.copyableDeck().isEmpty()?SpireReturn.Return(null):SpireReturn.Continue();}
 }
}
