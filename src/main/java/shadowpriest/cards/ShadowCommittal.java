package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;

public class ShadowCommittal extends PriestCard {
    public static final String ID = "shadowpriest:ShadowCommittal";
    public ShadowCommittal() { super(ID, 3, CardType.POWER, CardRarity.RARE, CardTarget.SELF); baseMagicNumber = magicNumber = 1; }
    @Override public float getTitleFontSize() { return 20f; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p, p, new ShadowCommittalPower(p, magicNumber), magicNumber)); }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeBaseCost(2); } }
}
