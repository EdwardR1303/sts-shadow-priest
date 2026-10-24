package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class ShadowMend extends PriestCard {
 public static final String ID="shadowpriest:ShadowMend";
 public ShadowMend() { super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF); baseMagicNumber=magicNumber=2; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new HealAction(p,p,10));
 addToBot(new ApplyPowerAction(p,p,new ShadowMendDebtPower(p,magicNumber),magicNumber)); }
 @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeMagicNumber(-1); } }

}
