package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class Piety extends PriestCard {
 public static final String ID="shadowpriest:Piety";
 public Piety() { super(ID,0,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF); exhaust=true; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new AbstractGameAction(){ public void update(){
 boolean attacks=false,skills=false;
 for(AbstractCard c:p.hand.group){ if(c.type==CardType.ATTACK) attacks=true; if(c.type==CardType.SKILL && !(c instanceof Piety)) skills=true; }
 if(!skills) addToTop(new shadowpriest.actions.TypedDrawAction(CardType.SKILL,2));
 if(!attacks) addToTop(new shadowpriest.actions.TypedDrawAction(CardType.ATTACK,2));
 isDone=true;
 }}); }
 @Override public void upgrade() { if (!upgraded) { upgradeName(); exhaust=false; upgradeText(); } }

}
