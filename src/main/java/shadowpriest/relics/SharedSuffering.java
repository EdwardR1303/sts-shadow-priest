package shadowpriest.relics;

import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowpriest.powers.ShadowDotPower;

public class SharedSuffering extends PriestRelic {
    public static final String ID = "shadowpriest:SharedSuffering";
    public SharedSuffering() { super(ID, RelicTier.SHOP, "shadowpriest/images/relics/icons/shared_suffering.jpg"); }

    @Override public void atBattleStart() {
        flash();
        for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
            if (!m.isDeadOrEscaped() && !m.isDying && !m.halfDead)
                addToBot(ShadowDotPower.apply(m, AbstractDungeon.player, ShadowDotPower.PAIN, 5));
    }

    @Override public AbstractRelic makeCopy() { return new SharedSuffering(); }
}
