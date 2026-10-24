package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import shadowpriest.powers.DragonwrathPower;
public class Dragonwrath extends LegendaryCard {
 public static final String ID="shadowpriest:Dragonwrath";
 public Dragonwrath(){super(ID);baseMagicNumber=magicNumber=20;}
 public void equip(AbstractPlayer p){addToBot(new ApplyPowerAction(p,p,new DragonwrathPower(p,magicNumber),magicNumber));}
 public void upgrade(){if(!upgraded){upgradeName();upgradeMagicNumber(5);}}
}
