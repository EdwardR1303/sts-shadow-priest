package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class Catharsis extends PriestCard {
 public static final String ID="shadowpriest:Catharsis";
 public Catharsis(){super(ID,2,CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY);exhaust=true;}
 @Override public void applyPowers(){baseDamage=AbstractDungeon.player==null?0:Math.max(0,AbstractDungeon.player.maxHealth-AbstractDungeon.player.currentHealth);super.applyPowers();}
 @Override public void calculateCardDamage(AbstractMonster m){baseDamage=Math.max(0,AbstractDungeon.player.maxHealth-AbstractDungeon.player.currentHealth);super.calculateCardDamage(m);}
 @Override public void use(AbstractPlayer p,AbstractMonster m){final Catharsis hit=(Catharsis)makeStatEquivalentCopy();addToBot(new AbstractGameAction(){public void update(){if(VoidBolt.alive(m)){hit.calculateCardDamage(m);addToTop(new DamageAction(m,new DamageInfo(p,hit.damage,hit.damageTypeForTurn),AttackEffect.FIRE));}isDone=true;}});}
 @Override public void upgrade(){if(!upgraded){upgradeName();selfRetain=true;upgradeText();}}
}
