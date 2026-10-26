package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class ShadowWeaving extends PriestCard {
    public static final String ID="shadowpriest:ShadowWeaving";
    public ShadowWeaving() { super(ID,1,CardType.POWER,CardRarity.COMMON,CardTarget.SELF);baseMagicNumber=magicNumber=2; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) { addToBot(new ApplyPowerAction(p,p,new ShadowWeavingPower(p,magicNumber),magicNumber)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeMagicNumber(1); } }

}
