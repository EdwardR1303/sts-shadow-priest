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
 @Override public void use(AbstractPlayer p,AbstractMonster m){addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));addToBot(ShadowDotPower.apply(m,p,ShadowDotPower.TOUCH,magicNumber));}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeBaseCost(0);}}
}
