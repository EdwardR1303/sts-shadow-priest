package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;
public class MindSoothe extends PriestCard {
 public static final String ID="shadowpriest:MindSoothe";
 public MindSoothe(){super(ID,0,CardType.SKILL,CardRarity.COMMON,CardTarget.ALL_ENEMY);}
 public void use(AbstractPlayer p,AbstractMonster ignored){for(AbstractMonster m:com.megacrit.cardcrawl.dungeons.AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(m)){int value=reduction(m,upgraded);addToBot(new ApplyPowerAction(m,p,new MindSoothePower(m,value),value));}}
 public static int reduction(AbstractMonster m,boolean upgraded){return (upgraded?3:2)+(upgraded?2:1)*ShadowDotPower.count(m);}
 public void upgrade(){if(!upgraded){upgradeName();upgradeText();}}
}
