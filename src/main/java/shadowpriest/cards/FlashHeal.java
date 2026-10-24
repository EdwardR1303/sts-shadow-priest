package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class FlashHeal extends PriestCard {
 public static final String ID="shadowpriest:FlashHeal";
 public FlashHeal() { super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF); baseMagicNumber=magicNumber=6; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new HealAction(p,p,magicNumber));
 addToBot(new AbstractGameAction(){ public void update(){ updateCost(1); isDone=true; }}); }
 @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeMagicNumber(3); } }

}
