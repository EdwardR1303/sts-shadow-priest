package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.BodyAndSoulPower;
public class BodyAndSoul extends PriestCard {
 public static final String ID="shadowpriest:BodyAndSoul";
 public BodyAndSoul(){super(ID,2,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);baseMagicNumber=magicNumber=20;}
 public static void resetBenefit(){ /* Benefits now live in the combat power. */ }
 @Override public void use(AbstractPlayer p,AbstractMonster m){addToBot(new ApplyPowerAction(p,p,new BodyAndSoulPower(p,magicNumber),magicNumber));}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeMagicNumber(10);}}
}
