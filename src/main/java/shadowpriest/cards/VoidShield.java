package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class VoidShield extends PriestCard {
    public static final String ID="shadowpriest:VoidShield";
    public VoidShield() { super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.SELF);baseBlock=5;baseMagicNumber=magicNumber=10; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) { addToBot(new GainBlockAction(p,p,totalBlock())); }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeBlock(3);upgradeMagicNumber(-5); } }
    @Override public void applyPowers() {
        super.applyPowers();
        FormulaPreview.set(this, FormulaPreview.inCombat() ? totalBlock() + " total Block" : null);
    }
    @Override public void resetAttributes() { super.resetAttributes(); FormulaPreview.set(this, null); }

    public int totalBlock() {
        shadowpriest.relics.VoidformRelic r=shadowpriest.relics.VoidformRelic.current();
        int insanity=shadowpriest.relics.VoidformRelic.isActive()?100:r==null?0:r.state.amount();
        int saved=baseBlock;
        try { baseBlock+=insanity/magicNumber;super.applyPowers();return block; }
        finally { baseBlock=saved;super.applyPowers(); }
    }

}
