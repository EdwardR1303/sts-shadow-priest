package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class VoidShield extends PriestCard {
 public static final String ID="shadowpriest:VoidShield";
 public VoidShield() { super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF); baseBlock=0; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new AbstractGameAction(){ public void update(){
 shadowpriest.relics.VoidformRelic r=shadowpriest.relics.VoidformRelic.current();
 if(upgraded && r!=null) r.gainInsanity(2);
 VoidShield.this.applyPowers();
 addToTop(new GainBlockAction(p,p,block)); isDone=true;
 }}); }
 @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeText(); } }
 @Override public void applyPowers(){
 shadowpriest.relics.VoidformRelic r=shadowpriest.relics.VoidformRelic.current();
 baseBlock=2*(shadowpriest.relics.VoidformRelic.isActive()?10:r==null?0:r.state.amount());
 super.applyPowers();
 }
}
