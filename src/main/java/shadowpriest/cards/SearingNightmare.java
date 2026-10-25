package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class SearingNightmare extends PriestCard {
 public static final String ID="shadowpriest:SearingNightmare";
 public SearingNightmare(){super(ID,-1,CardType.ATTACK,CardRarity.RARE,CardTarget.ALL_ENEMY);baseDamage=5;baseMagicNumber=magicNumber=5;isMultiDamage=true;}
 @Override public float getTitleFontSize(){return 20f;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){int x=spendX(p)+(upgraded?1:0);final SearingNightmare hit=(SearingNightmare)makeStatEquivalentCopy();
 for(int i=0;i<x;i++)addToBot(new AbstractGameAction(){public void update(){
  java.util.ArrayList<AbstractMonster> targets=new java.util.ArrayList<>(AbstractDungeon.getMonsters().monsters);
  for(int n=targets.size()-1;n>=0;n--){AbstractMonster enemy=targets.get(n);if(VoidBolt.alive(enemy))addToTop(new ApplyPowerAction(enemy,p,new MadnessPower(enemy,magicNumber),magicNumber));}
  for(int n=targets.size()-1;n>=0;n--){AbstractMonster enemy=targets.get(n);if(VoidBolt.alive(enemy)){hit.calculateCardDamage(enemy);addToTop(new DamageAction(enemy,new DamageInfo(p,hit.damage,hit.damageTypeForTurn),AttackEffect.FIRE));}}
  isDone=true;
 }});}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeText();}}
}
