package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.cards.DamageInfo;
import shadowpriest.actions.HpDamageAction;
import shadowpriest.powers.MadnessPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
public class ShadowWordMadness extends PriestCard {
 public static final String ID="shadowpriest:ShadowWordMadness";
 public ShadowWordMadness(){super(ID,1,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);baseDamage=6;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){addToBot(new HpDamageAction(m,new DamageInfo(p,damage,damageTypeForTurn),lost->{if(lost>0&&VoidBolt.alive(m))addToTop(new ApplyPowerAction(m,p,new MadnessPower(m,lost),lost));}));}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeDamage(4);}}
}
