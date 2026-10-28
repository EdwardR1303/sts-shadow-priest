package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class SearingNightmare extends PriestCard {
    public static final String ID="shadowpriest:SearingNightmare";
    public SearingNightmare() { super(ID,2,CardType.POWER,CardRarity.RARE,CardTarget.SELF);baseMagicNumber=magicNumber=2; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) { addToBot(new ApplyPowerAction(p,p,new SearingNightmarePower(p,magicNumber),magicNumber)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeBaseCost(1); } }
    @Override public float getTitleFontSize(){return 20f;}
}
