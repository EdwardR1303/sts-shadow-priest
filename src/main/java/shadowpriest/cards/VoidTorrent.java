package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class VoidTorrent extends PriestCard {
    public static final String ID = "shadowpriest:VoidTorrent";
    public VoidTorrent() {
        super(ID, -1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY);
        baseDamage = 4; baseMagicNumber = magicNumber = 4;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        int repeats = spendX(p);
        final VoidTorrent torrent = (VoidTorrent)makeStatEquivalentCopy();
        final int first = baseDamage, step = magicNumber;
        for (int i = 0; i < repeats; i++) {
            final int hitBase = first + i * step;
            addToBot(new AbstractGameAction() {
                @Override public void update() {
                    if (VoidBolt.alive(m)) {
                        // Recalculate from each hit's base, never from the previous modified damage.
                        int hit = torrent.damageAt(hitBase, m);
                        addToTop(new DamageAction(m, new DamageInfo(p, hit, torrent.damageTypeForTurn), AttackEffect.NONE));
                        addToTop(shadowpriest.vfx.ShadowFx.hit(shadowpriest.vfx.ShadowSpellEffect.Style.TORRENT, p, m));
                    }
                    isDone = true;
                }
            });
        }
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeDamage(1); upgradeMagicNumber(1); }
    }
    @Override public void applyPowers() {
        super.applyPowers(); updatePreview(null);
    }
    @Override public void calculateCardDamage(AbstractMonster target) {
        super.calculateCardDamage(target); updatePreview(target);
    }
    @Override public void resetAttributes() { super.resetAttributes(); FormulaPreview.set(this, null); }
    private void updatePreview(AbstractMonster target) {
        if (!FormulaPreview.inCombat()) { FormulaPreview.set(this, null); return; }
        AbstractPlayer p = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
        int hits = Math.max(0, com.megacrit.cardcrawl.ui.panels.EnergyPanel.totalCount)
                + (p.hasPower(shadowpriest.powers.HastePower.POWER_ID) ? p.getPower(shadowpriest.powers.HastePower.POWER_ID).amount : 0)
                + (p.hasRelic("Chemical X") ? 2 : 0);
        FormulaPreview.set(this, previewDamage(target, hits) + " total damage / " + hits + " hits");
    }
    public long previewDamage(AbstractMonster target, int hits) {
        long total = 0;
        for (int i = 0; i < hits; i++) total += damageAt(baseDamage + i * magicNumber, target);
        return total;
    }
    private int damageAt(int hitBase, AbstractMonster target) {
        int savedBase = baseDamage, savedDamage = damage;
        boolean savedModified = isDamageModified;
        try {
            baseDamage = hitBase;
            if (target == null) super.applyPowers(); else super.calculateCardDamage(target);
            return damage;
        } finally { baseDamage = savedBase; damage = savedDamage; isDamageModified = savedModified; }
    }
}
