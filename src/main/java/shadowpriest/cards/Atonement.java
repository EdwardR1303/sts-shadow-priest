package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;

public class Atonement extends PriestCard {
    public static final String ID = "shadowpriest:Atonement";
    public Atonement() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 5; baseMagicNumber = magicNumber = 3;
    }
    @Override public void calculateCardDamage(AbstractMonster m) {
        int saved = baseBlock;
        baseBlock = magicNumber * ShadowDotPower.count(m);
        try { super.calculateCardDamage(m); } finally { baseBlock = saved; }
        isBlockModified = block != baseBlock;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        calculateCardDamage(m); // Snapshot unique DoTs and Block modifiers before the killing hit.
        int granted = block;
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
        addToBot(new GainBlockAction(p, p, granted));
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeDamage(2); upgradeMagicNumber(1); } }
}
