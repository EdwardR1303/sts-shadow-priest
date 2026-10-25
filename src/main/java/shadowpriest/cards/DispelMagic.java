package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.powers.DexterityPower;
import shadowpriest.powers.MasteryPower;
public class DispelMagic extends PriestCard {
 public static final String ID="shadowpriest:DispelMagic";
 public DispelMagic(){super(ID,0,CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY);}
 @Override public void use(AbstractPlayer p,AbstractMonster m){addToBot(new AbstractGameAction(){public void update(){
  if(m!=null){boolean block=m.currentBlock>0,artifact=upgraded&&m.hasPower("Artifact");
   if(block){m.loseBlock();addToTop(new ApplyPowerAction(p,p,new DexterityPower(p,1),1));}
   if(artifact){addToTop(new ApplyPowerAction(p,p,new MasteryPower(p,1),1));addToTop(new RemoveSpecificPowerAction(m,p,"Artifact"));}
  }isDone=true;
 }});}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeText();}}
}
