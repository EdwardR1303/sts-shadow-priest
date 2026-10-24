package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class PowerWordBarrier extends PriestCard {
 public static final String ID="shadowpriest:PowerWordBarrier";
 public PowerWordBarrier() { super(ID,3,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF); baseBlock=5; }
 @Override public float getTitleFontSize() { return 16f; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p,p,new EquilibriumPower(p,1),1));
 addToBot(new AbstractGameAction(){ public void update(){
 PowerWordBarrier.this.applyPowers();
 int count=0; for(AbstractCard c:p.hand.group) if(c!=PowerWordBarrier.this) count++;
 addToTop(new GainBlockAction(p,p,block*count)); isDone=true;
 }}); }
 @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeBlock(2); } }

}
