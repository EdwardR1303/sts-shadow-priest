package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.MasteryPower;
public class Xalatath extends LegendaryCard {
 public static final String ID="shadowpriest:Xalatath";
 public Xalatath(){super(ID);baseMagicNumber=magicNumber=4;}
 public void equip(AbstractPlayer p){final int gain=upgraded?3:2;for(int i=0;i<magicNumber;i++) addToBot(new AbstractGameAction(){ public void update(){
 int choice=AbstractDungeon.miscRng.random(2);
  AbstractPower power=choice==0?new StrengthPower(p,gain):choice==1?new MasteryPower(p,gain):new DexterityPower(p,gain);
 addToTop(new ApplyPowerAction(p,p,power,gain));isDone=true;
 }});}
 public void upgrade(){if(!upgraded){upgradeName();upgradeText();}}
}
