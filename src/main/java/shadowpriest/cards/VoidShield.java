package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
/** Displayed as Void Shift; retain the old ID for existing saves. */
public class VoidShield extends PriestCard {
 public static final String ID="shadowpriest:VoidShield";
 public VoidShield(){super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY);}
 @Override public void use(AbstractPlayer p,AbstractMonster m){addToBot(new AbstractGameAction(){public void update(){
  if(VoidBolt.alive(m)){int before=m.currentBlock;m.loseBlock();p.addBlock(Math.max(0,before-m.currentBlock));}isDone=true;
 }});}
 @Override public void upgrade(){if(!upgraded){upgradeName();selfRetain=true;upgradeText();}}
}
