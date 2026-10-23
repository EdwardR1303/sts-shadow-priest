package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.ShadowDotPower;

/** MagicNumber is fixed tick damage, independent of the attack damage calculation. */
public abstract class ShadowDotCard extends PriestCard {
    private final String powerId;
    protected ShadowDotCard(String id, String powerId) {
        super(id, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        this.powerId = powerId;
        baseMagicNumber = magicNumber = 5;
    }
    public int turns() { return upgraded ? 4 : 3; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(ShadowDotPower.apply(m, p, powerId, turns(), magicNumber));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeMagicNumber(1); initializeDescription(); }
    }
}
