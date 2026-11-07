package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.actions.*;
import shadowpriest.powers.BarrierPower;
public class FocusedWill extends PriestCard {
 public static final String ID="shadowpriest:FocusedWill";
 public FocusedWill(){super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);baseBlock=8;baseMagicNumber=magicNumber=10;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){final int grant=block,insanity=magicNumber;
  addToBot(new DevourAction(devoured->{java.util.List<AbstractGameAction> effects=new java.util.ArrayList<>();
   effects.add(new GainBlockAction(p,p,grant));effects.add(new GainInsanityAction(insanity));
   if(devoured)effects.add(new AbstractGameAction(){public void update(){
    if(p.currentBlock>0)addToTop(new ApplyPowerAction(p,p,new BarrierPower(p,p.currentBlock),p.currentBlock));isDone=true;
   }});return effects;
  }));
 }
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeBlock(3);upgradeMagicNumber(5);}}
}
