package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class Serendipity extends PriestCard {
 public static final String ID="shadowpriest:Serendipity";
 public Serendipity() { super(ID,0,CardType.SKILL,CardRarity.RARE,CardTarget.SELF); exhaust=true;selfRetain=true; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new shadowpriest.actions.SerendipityAction()); }
 @Override public void upgrade() { if (!upgraded) { upgradeName(); exhaust=false; upgradeText(); } }

}
