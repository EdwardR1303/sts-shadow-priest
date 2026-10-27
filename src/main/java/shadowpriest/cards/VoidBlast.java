package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.MadnessPower;
import shadowpriest.powers.ShadowDotPower;

public class VoidBlast extends PriestCard {
    public static final String ID = "shadowpriest:VoidBlast";
    public VoidBlast() { super(ID, 1, CardType.ATTACK, CardRarity.RARE, CardTarget.ENEMY); baseDamage = 10; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        if (m == null) return;
        // Snapshot before the hit so killing the source still spreads its existing debuffs.
        int touch = m.hasPower(ShadowDotPower.TOUCH) ? m.getPower(ShadowDotPower.TOUCH).amount : 0;
        int pain = m.hasPower(ShadowDotPower.PAIN) ? m.getPower(ShadowDotPower.PAIN).amount : 0;
        int madness = MadnessPower.stacks(m);
        addToBot(shadowpriest.vfx.ShadowFx.hit(shadowpriest.vfx.ShadowSpellEffect.Style.BLAST, p, m));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        for (AbstractMonster enemy : AbstractDungeon.getMonsters().monsters) {
            if (enemy == m || enemy.isDeadOrEscaped()) continue;
            if (touch > 0) addToBot(ShadowDotPower.apply(enemy, p, ShadowDotPower.TOUCH, touch));
            if (pain > 0) addToBot(ShadowDotPower.apply(enemy, p, ShadowDotPower.PAIN, pain));
            if (madness > 0) addToBot(new ApplyPowerAction(enemy, p, new MadnessPower(enemy, madness), madness));
        }
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeDamage(5); } }
}
