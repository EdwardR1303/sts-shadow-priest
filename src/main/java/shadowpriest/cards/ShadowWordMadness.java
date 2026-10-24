package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.MadnessPower;

public class ShadowWordMadness extends PriestCard {
    public static final String ID = "shadowpriest:ShadowWordMadness";
    public ShadowWordMadness() {
        super(ID, 1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseMagicNumber = magicNumber = 5;
        baseDamage = 5;
    }
    @Override public void calculateCardDamage(AbstractMonster m) { calculateHit(m, true); }
    private void calculateHit(AbstractMonster m, boolean preview) {
        int original = baseDamage;
        baseDamage = MadnessPower.stacks(m) + (preview && !m.hasPower("Artifact") ? magicNumber : 0);
        try { super.calculateCardDamage(m); }
        finally { baseDamage = original; }
        isDamageModified = damage != baseDamage;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        if (upgraded) {
            for (AbstractMonster enemy : AbstractDungeon.getMonsters().monsters)
                if (!enemy.isDeadOrEscaped()) apply(p, enemy);
        } else apply(p, m);
        // Resolve after application so Artifact and other vetoes affect the actual stack total.
        addToBot(new AbstractGameAction() {
            @Override public void update() {
                if (m != null && !m.isDeadOrEscaped()) {
                    calculateHit(m, false);
                    addToTop(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AttackEffect.FIRE));
                }
                isDone = true;
            }
        });
    }
    private void apply(AbstractPlayer p, AbstractMonster m) {
        if (m != null) addToBot(new ApplyPowerAction(m, p, new MadnessPower(m, magicNumber), magicNumber));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeText(); }
    }
}
