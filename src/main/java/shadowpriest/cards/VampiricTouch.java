package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.ShadowDotPower;

public class VampiricTouch extends PriestCard {
    public static final String ID = "shadowpriest:VampiricTouch";
    public VampiricTouch() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 3;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(m, p, new ShadowDotPower(m, ShadowDotPower.TOUCH, magicNumber), magicNumber));
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeMagicNumber(1); } }
}
