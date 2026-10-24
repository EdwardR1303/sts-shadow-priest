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
 public void equip(AbstractPlayer p){for(int i=0;i<magicNumber;i++) addToBot(new AbstractGameAction(){ public void update(){
 int choice=AbstractDungeon.miscRng.random(2);
 AbstractPower power=choice==0?new StrengthPower(p,2):choice==1?new MasteryPower(p,2):new DexterityPower(p,2);
 addToTop(new ApplyPowerAction(p,p,power,2));isDone=true;
 }});}
 public void upgrade(){if(!upgraded){upgradeName();upgradeMagicNumber(1);}}
}
