package shadowpriest.relics;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowpriest.powers.ShadowDotPower;

public class Misery extends PriestRelic {
    public static final String ID = "shadowpriest:Misery";
    public Misery() { super(ID, RelicTier.UNCOMMON, "shadowpriest/images/relics/icons/misery.jpg"); }

    public static void onTouchApplied(AbstractCreature target, AbstractCreature source, int stacks) {
        if (source != AbstractDungeon.player || !(target instanceof AbstractMonster)
                || target.isDeadOrEscaped() || stacks <= 0 || source == null) return;
        AbstractRelic relic = AbstractDungeon.player.getRelic(ID);
        if (relic == null) return;
        relic.flash();
        AbstractDungeon.actionManager.addToTop(ShadowDotPower.apply(target, source, ShadowDotPower.PAIN, stacks));
    }

    @Override public AbstractRelic makeCopy() { return new Misery(); }
}
