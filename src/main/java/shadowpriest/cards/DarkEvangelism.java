package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class DarkEvangelism extends PriestCard {
 public static final String ID="shadowpriest:DarkEvangelism";
 public DarkEvangelism(){super(ID,-1,CardType.POWER,CardRarity.RARE,CardTarget.SELF);}
 @Override public void use(AbstractPlayer p,AbstractMonster m){int x=spendX(p)+(upgraded?1:0);if(x>0)addToBot(new ApplyPowerAction(p,p,new MasteryPower(p,x),x));}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeText();}}
}
