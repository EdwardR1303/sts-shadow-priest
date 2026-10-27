package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.VampiricEmbracePower;

public class VampiricEmbrace extends PriestCard {
    public static final String ID = "shadowpriest:VampiricEmbrace";
    public VampiricEmbrace() {
        super(ID, 2, CardType.POWER, CardRarity.RARE, CardTarget.SELF); baseMagicNumber = magicNumber = 1;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new VampiricEmbracePower(p, magicNumber), magicNumber));
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeMagicNumber(1); } }
}
