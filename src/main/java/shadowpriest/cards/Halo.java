package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.HaloPower;

public class Halo extends PriestCard {
    public static final String ID = "shadowpriest:Halo";
    public Halo() {
        super(ID, 1, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 4;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new HaloPower(p, magicNumber), 3));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeMagicNumber(2); }
    }
}
