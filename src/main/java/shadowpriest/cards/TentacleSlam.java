package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.ShadowDotPower;

public class TentacleSlam extends PriestCard {
    public static final String ID = "shadowpriest:TentacleSlam";
    public TentacleSlam() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8; baseMagicNumber = magicNumber = 3;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        if (m == null) return;
        float left = Math.min(p.hb.cX, m.hb.cX), right = Math.max(p.hb.cX, m.hb.cX);
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        for (AbstractMonster enemy : AbstractDungeon.getMonsters().monsters)
            if (!enemy.isDeadOrEscaped() && (enemy == m || (enemy.hb.cX >= left && enemy.hb.cX <= right)))
                addToBot(ShadowDotPower.apply(enemy, p, ShadowDotPower.TOUCH, magicNumber));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeDamage(2); upgradeBaseCost(1); }
    }
}
