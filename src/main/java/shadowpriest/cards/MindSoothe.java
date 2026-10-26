package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;
public class MindSoothe extends PriestCard {
 public static final String ID="shadowpriest:MindSoothe";
 public MindSoothe(){super(ID,0,CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY);}
 public void use(AbstractPlayer p,AbstractMonster m){int value=ShadowDotPower.count(m)+(upgraded?2:1);if(value>0)addToBot(new ApplyPowerAction(m,p,new MindSoothePower(m,value),value));}
 public void upgrade(){if(!upgraded){upgradeName();upgradeText();}}
}
