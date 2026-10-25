package shadowpriest.mechanics;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import javassist.CtBehavior;
import shadowpriest.powers.HastePower;

/** Snapshot effective X once at the actual play boundary, including off-color cards. */
public final class Channeling {
    @SpirePatch(clz=AbstractCard.class, method=SpirePatch.CLASS)
    public static class Fields {
        public static SpireField<Integer> effective = new SpireField<>(()->-1);
        public static SpireField<Integer> repeat = new SpireField<>(()->-1);
    }
    public static int effective(AbstractCard c) {
        int cached = Fields.effective.get(c);
        if (cached >= 0) return cached;
        AbstractPlayer p=AbstractDungeon.player;
        return Math.max(0,c.energyOnUse<0?EnergyPanel.totalCount:c.energyOnUse)
            + (p.hasPower(HastePower.POWER_ID)?p.getPower(HastePower.POWER_ID).amount:0)
            + (p.hasRelic("Chemical X")?2:0);
    }
    public static void preserve(AbstractCard from, AbstractCard to) {
        if (from.cost == -1) Fields.repeat.set(to, effective(from));
    }
    @SpirePatch(clz=AbstractPlayer.class,method="useCard")
    public static class Prepare {
        @SpireInsertPatch(locator=Locator.class)
        public static void before(AbstractPlayer __instance, AbstractCard c) {
            if (c.cost != -1) return;
            int repeated=Fields.repeat.get(c);
            Fields.effective.set(c,-1);
            int x=repeated>=0?repeated:effective(c);
            Fields.repeat.set(c,-1);
            Fields.effective.set(c,x);
            // Base-game X cards add Chemical X themselves; provide the other bonuses here.
            c.energyOnUse=x-(__instance.hasRelic("Chemical X")?2:0);
        }
        private static class Locator extends SpireInsertLocator {
            public int[] Locate(CtBehavior method) throws Exception {
                return LineFinder.findInOrder(method,new Matcher.MethodCallMatcher(AbstractCard.class,"use"));
            }
        }
    }
}
