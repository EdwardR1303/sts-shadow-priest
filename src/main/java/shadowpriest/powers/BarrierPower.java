package shadowpriest.powers;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

/** A finite reserve: retention and gain lock end together when Block is depleted. */
public class BarrierPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:Barrier";
    private boolean intact = true;
    public BarrierPower(AbstractCreature owner) {
        this.owner = owner; ID = POWER_ID; name = "Power Word: Barrier";
        amount = -1; type = PowerType.BUFF;
        AbilityPowerArt.apply(this, "power_word_barrier.jpg"); updateDescription();
    }
    public static boolean active(AbstractCreature owner) {
        AbstractPower power = owner.getPower(POWER_ID);
        return power instanceof BarrierPower && ((BarrierPower) power).intact;
    }
    @Override public void updateDescription() {
        description = "Retain Block until it is depleted. NL You cannot gain additional Block while this barrier lasts.";
    }
    @SpirePatch(clz = AbstractCreature.class, method = "hasPower")
    public static class Retention {
        @SpirePostfixPatch public static boolean after(boolean __result, AbstractCreature __instance, String targetID) {
            return __result || ("Barricade".equals(targetID) && active(__instance));
        }
    }
    @SpirePatch(clz = AbstractCreature.class, method = "addBlock", paramtypez = {int.class})
    public static class PreventGain {
        @SpirePrefixPatch public static SpireReturn<Void> before(AbstractCreature __instance, int blockAmount) {
            return active(__instance) ? SpireReturn.Return(null) : SpireReturn.Continue();
        }
    }
    @SpirePatch(clz = AbstractCreature.class, method = "loseBlock", paramtypez = {int.class, boolean.class})
    public static class Break {
        @SpirePostfixPatch public static void after(AbstractCreature __instance, int amount, boolean noAnimation) {
            if (amount <= 0 || __instance.currentBlock > 0 || !active(__instance)) return;
            BarrierPower power = (BarrierPower) __instance.getPower(POWER_ID);
            // Release immediately, even if another gain is already in the action queue.
            power.intact = false;
            AbstractDungeon.actionManager.addToTop(new RemoveSpecificPowerAction(__instance, __instance, power));
        }
    }
}
