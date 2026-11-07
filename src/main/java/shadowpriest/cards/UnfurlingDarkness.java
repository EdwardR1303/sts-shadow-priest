package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class UnfurlingDarkness extends PriestCard {
 public static final String ID="shadowpriest:UnfurlingDarkness";
 public UnfurlingDarkness(){super(ID,1,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);baseDamage=7;baseMagicNumber=magicNumber=3;}
 @Override public float getTitleFontSize(){return 20f;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){
  final int stacks=magicNumber;
  addToBot(new shadowpriest.actions.DevourAction(devoured->{
   java.util.List<AbstractGameAction> effects=new java.util.ArrayList<>();
   for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(enemy)&&(devoured||enemy==m)){
    // Recalculate for each target's Vulnerable/Intangible, preserving the played card's stats.
    UnfurlingDarkness copy=(UnfurlingDarkness)makeStatEquivalentCopy();copy.calculateCardDamage(enemy);
    effects.add(new DamageAction(enemy,new DamageInfo(p,copy.damage,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));
    effects.add(ShadowDotPower.apply(enemy,p,ShadowDotPower.TOUCH,stacks));
   }
   effects.add(new shadowpriest.actions.GainInsanityAction(10));
   return effects;
  }));
 }
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeBaseCost(0);}}
}
