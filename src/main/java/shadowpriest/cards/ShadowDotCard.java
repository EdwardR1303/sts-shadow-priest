package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.ShadowDotPower;
import shadowpriest.mechanics.ReturningDots;

/** MagicNumber is fixed tick damage, independent of the attack damage calculation. */
public abstract class ShadowDotCard extends PriestCard {
    private final String powerId;
    protected ShadowDotCard(String id, String powerId) {
        super(id, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.ENEMY);
        this.powerId = powerId;
        baseMagicNumber = magicNumber = ShadowDotPower.TOUCH.equals(powerId) ? 5 : 3;
    }
    public int turns() { return ShadowDotPower.TOUCH.equals(powerId) ? (upgraded ? 4 : 3) : 5; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(ShadowDotPower.apply(m, p, powerId, turns()));
        ReturningDots.mark(this);
    }
    @Override public void upgrade() {
        if (!upgraded) {
            upgradeName();
            if (ShadowDotPower.PAIN.equals(powerId)) upgradeBaseCost(0);
            initializeDescription();
        }
    }
}
