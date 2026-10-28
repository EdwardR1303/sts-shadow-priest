package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.evacipated.cardcrawl.mod.stslib.cards.targeting.SelfOrEnemyTargeting;
public class Penance extends PriestCard {
 public static final String ID="shadowpriest:Penance";
 public Penance(){super(ID,1,CardType.ATTACK,CardRarity.UNCOMMON,SelfOrEnemyTargeting.SELF_OR_ENEMY);baseDamage=3;baseMagicNumber=magicNumber=3;exhaust=true;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){AbstractCreature chosen=SelfOrEnemyTargeting.getTarget(this);if(chosen==null)chosen=m==null?p:m;
  if(chosen==p){for(int i=0;i<shadowpriest.mechanics.Repetitions.add(magicNumber);i++)addToBot(new HealAction(p,p,3));}
  else if(chosen instanceof AbstractMonster){AbstractMonster enemy=(AbstractMonster)chosen;calculateCardDamage(enemy);
   for(int i=0;i<shadowpriest.mechanics.Repetitions.add(magicNumber);i++)addToBot(new DamageAction(enemy,new DamageInfo(p,damage,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));}
 }
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeMagicNumber(1);}}
}
