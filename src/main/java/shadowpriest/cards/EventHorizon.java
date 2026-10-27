package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;

public class EventHorizon extends PriestCard {
    public static final String ID="shadowpriest:EventHorizon";
    public EventHorizon() { super(ID,2,CardType.POWER,CardRarity.RARE,CardTarget.SELF); baseMagicNumber=magicNumber=2; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p,p,new EventHorizonPower(p,magicNumber),magicNumber)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName(); upgradeMagicNumber(1); } }
    
}
