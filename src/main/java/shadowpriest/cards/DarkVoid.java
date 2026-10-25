package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class DarkVoid extends PriestCard {
 public static final String ID="shadowpriest:DarkVoid";
 public DarkVoid(){super(ID,1,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ALL_ENEMY);baseDamage=3;baseMagicNumber=magicNumber=3;isMultiDamage=true;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(enemy)){
  calculateCardDamage(enemy);addToBot(new shadowpriest.actions.HpDamageAction(enemy,new DamageInfo(p,damage,damageTypeForTurn),lost->{if(lost>0&&VoidBolt.alive(enemy))addToTop(ShadowDotPower.apply(enemy,p,ShadowDotPower.PAIN,magicNumber));}));
 }}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeDamage(2);upgradeMagicNumber(2);}}
}
