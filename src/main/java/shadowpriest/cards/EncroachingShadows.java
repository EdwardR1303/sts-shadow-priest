package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class EncroachingShadows extends PriestCard {
    public static final String ID="shadowpriest:EncroachingShadows";
    public EncroachingShadows() { super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY);baseBlock=5;baseMagicNumber=magicNumber=3; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) { addToBot(new GainBlockAction(p,p,block));addToBot(new ApplyPowerAction(m,p,new MadnessPower(m,magicNumber),magicNumber)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeBlock(3);upgradeMagicNumber(2); } }
    @Override public float getTitleFontSize(){return 19f;}
}
