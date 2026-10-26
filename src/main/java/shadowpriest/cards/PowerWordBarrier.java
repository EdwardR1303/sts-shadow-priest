package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.BarrierPower;

public class PowerWordBarrier extends PriestCard {
 public static final String ID="shadowpriest:PowerWordBarrier";
 public PowerWordBarrier() { super(ID,3,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF); baseBlock=30; }
 @Override public float getTitleFontSize() { return 16f; }
 @Override public boolean canUse(AbstractPlayer p, AbstractMonster m) {
  if(BarrierPower.active(p)){cantUseMessage="Your Barrier has not broken yet.";return false;}
  return super.canUse(p,m);
 }
 @Override public void use(AbstractPlayer p, AbstractMonster m) {
  if(BarrierPower.active(p))return;
  addToBot(new GainBlockAction(p,p,block));
  addToBot(new AbstractGameAction(){public void update(){
   if(p.currentBlock>0 && !BarrierPower.active(p)) addToTop(new ApplyPowerAction(p,p,new BarrierPower(p),-1));
   isDone=true;
  }});
 }
 @Override public void upgrade() { if(!upgraded){upgradeName();upgradeBlock(10);} }
}
