package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class FocusedWill extends PriestCard {
    public static final String ID="shadowpriest:FocusedWill";
    public FocusedWill() { super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);baseBlock=7;baseMagicNumber=magicNumber=10; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) {
        final int insanity=magicNumber;
        addToBot(new shadowpriest.actions.DevourAction(devoured->java.util.Arrays.asList(
            new GainBlockAction(p,p,blockWithBonus(devoured)),new GainInsanityAction(insanity))));
    }
    public int blockWithBonus(boolean devoured) {
        int savedBase=baseBlock,savedBlock=block;boolean savedModified=isBlockModified;
        baseBlock=savedBase+(devoured?7:0);
        super.applyPowers();int result=block;
        baseBlock=savedBase;block=savedBlock;isBlockModified=savedModified;
        return result;
    }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeBlock(3);upgradeMagicNumber(5); } }

}
