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
 public UnfurlingDarkness(){super(ID,1,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);baseDamage=5;baseMagicNumber=magicNumber=3;}
 @Override public float getTitleFontSize(){return 20f;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){
  final int hit=damage,stacks=magicNumber;
  addToBot(new shadowpriest.actions.DevourAction(devoured->{
   java.util.List<AbstractGameAction> effects=new java.util.ArrayList<>();
   effects.add(new DamageAction(m,new DamageInfo(p,hit,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));
   effects.add(ShadowDotPower.apply(m,p,ShadowDotPower.TOUCH,stacks));
   effects.add(new shadowpriest.actions.GainInsanityAction(10));
   if(devoured)for(AbstractMonster other:AbstractDungeon.getMonsters().monsters)
    if(other!=m&&VoidBolt.alive(other))effects.add(ShadowDotPower.apply(other,p,ShadowDotPower.TOUCH,stacks));
   return effects;
  }));
 }
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeBaseCost(0);}}
}
