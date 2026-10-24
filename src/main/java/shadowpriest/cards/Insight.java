package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class Insight extends PriestCard {
 public static final String ID="shadowpriest:Insight";
 public Insight() { super(ID,0,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF); baseMagicNumber=magicNumber=2; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new DrawCardAction(p,magicNumber));
 addToBot(new ApplyPowerAction(p,p,new InsightDebtPower(p,1),1)); }
 @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeMagicNumber(1); } }

}
