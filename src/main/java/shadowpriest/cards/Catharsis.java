package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.evacipated.cardcrawl.modthespire.lib.*;
import javassist.CannotCompileException;
import javassist.expr.*;
import java.util.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class Catharsis extends PriestCard {
 public static final String ID="shadowpriest:Catharsis";
 public int storedDamage;
 public Catharsis(){super(ID,2,CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);exhaust=true;baseDamage=0;}
 @Override public void applyPowers(){baseDamage=storedDamage;super.applyPowers();FormulaPreview.set(this,damage+" damage");}
 @Override public void calculateCardDamage(AbstractMonster m){baseDamage=storedDamage;super.calculateCardDamage(m);FormulaPreview.set(this,damage+" damage");}
 @Override public void resetAttributes(){super.resetAttributes();FormulaPreview.set(this,null);}
 @Override public void use(AbstractPlayer p,AbstractMonster m){addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));}
 @Override public AbstractCard makeStatEquivalentCopy(){Catharsis c=(Catharsis)super.makeStatEquivalentCopy();c.storedDamage=storedDamage;return c;}
 public static void record(AbstractPlayer p,int before,int after){
  int lost=Math.min(Math.max(0,before),Math.max(0,before-after));
  if(lost==0 || !FormulaPreview.inCombat())return;
  Set<AbstractCard> seen=Collections.newSetFromMap(new IdentityHashMap<AbstractCard,Boolean>());
  for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})
   for(AbstractCard c:g.group)if(c instanceof Catharsis && seen.add(c))((Catharsis)c).storedDamage+=lost*(p.hand.contains(c)?5:3);
 }
 @SpirePatch(clz=AbstractPlayer.class,method="damage")
 public static class Track {
  @SpireInstrumentPatch public static ExprEditor instrument(){return new ExprEditor(){
   @Override public void edit(FieldAccess f)throws CannotCompileException {
    if(f.isWriter() && f.getFieldName().equals("currentHealth"))f.replace("{ shadowpriest.cards.Catharsis.record(this, $0.currentHealth, $1); $proceed($$); }");
   }
  };}
 }
 @Override public void upgrade(){if(!upgraded){upgradeName();selfRetain=true;upgradeText();}}
}
