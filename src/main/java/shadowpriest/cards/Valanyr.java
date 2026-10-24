package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.actions.common.*;
import shadowpriest.powers.ValanyrPower;
public class Valanyr extends LegendaryCard {
 public static final String ID="shadowpriest:Valanyr";
 public Valanyr(){super(ID);baseMagicNumber=magicNumber=5;}
 public void equip(AbstractPlayer p){addToBot(new GainBlockAction(p,p,magicNumber));addToBot(new ApplyPowerAction(p,p,new ValanyrPower(p)));}
 public void upgrade(){if(!upgraded){upgradeName();upgradeMagicNumber(5);}}
}
