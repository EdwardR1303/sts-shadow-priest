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
                        torrent.baseDamage = hitBase;
                        torrent.calculateCardDamage(m);
                        addToTop(new DamageAction(m, new DamageInfo(p, torrent.damage, torrent.damageTypeForTurn), AttackEffect.FIRE));
                    }
                    isDone = true;
                }
            });
        }
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeDamage(1); upgradeMagicNumber(1); }
    }
}
