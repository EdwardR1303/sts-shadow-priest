package shadowpriest.cards;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.actions.DevourAction;
import shadowpriest.powers.*;
public class Insight extends PriestCard {
 public static final String ID="shadowpriest:Insight";
 private UseCardAction cleanup;
 public Insight(){super(ID,0,CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY);exhaust=true;}
 @Override public boolean canUse(AbstractPlayer p,AbstractMonster m){
  if(p.hasPower(InsightLockPower.POWER_ID)){cantUseMessage="Shadowy Insight has already been played this turn.";return false;}
  return super.canUse(p,m);
 }
 @Override public void use(AbstractPlayer p,AbstractMonster m){
  if(p.hasPower(InsightLockPower.POWER_ID))return;
  p.powers.add(new InsightLockPower(p));java.util.Collections.sort(p.powers);
  final int count=ShadowDotPower.count(m)+(upgraded?1:0);
  addToBot(new DevourAction(devoured->{
   if(!devoured&&cleanup!=null)cleanup.exhaustCard=true;
   return java.util.Collections.singletonList(new DrawCardAction(p,count));
  }));
 }
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeText();}}
 /** Defer only printed Exhaust; external exhaust hooks still run normally. */
 @SpirePatch(clz=UseCardAction.class,method=SpirePatch.CONSTRUCTOR,paramtypez={AbstractCard.class,AbstractCreature.class})
 public static class ConditionalExhaust {
  @SpirePrefixPatch public static void before(UseCardAction __instance,AbstractCard card,AbstractCreature target){if(card instanceof Insight)card.exhaust=false;}
  @SpirePostfixPatch public static void after(UseCardAction __instance,AbstractCard card,AbstractCreature target){
   if(card instanceof Insight){((Insight)card).cleanup=__instance;card.exhaust=true;}
  }
 }
}
