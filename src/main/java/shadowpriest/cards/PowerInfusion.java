package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class PowerInfusion extends PriestCard {
 public static final String ID="shadowpriest:PowerInfusion";
 public PowerInfusion(){super(ID,1,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);baseMagicNumber=magicNumber=2;exhaust=true;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){addToBot(new ApplyPowerAction(p,p,new HastePower(p,magicNumber),magicNumber));addToBot(new ApplyPowerAction(p,p,new PowerInfusionPower(p),2));}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeBaseCost(0);}}
}
