package shadowpriest.actions;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import shadowpriest.powers.ShadowDotPower;
import shadowpriest.relics.Misery;

/** Keeps successful direct applications separate from existing-only stack extensions. */
public final class ApplyShadowDotAction extends ApplyPowerAction {
    private static ApplyShadowDotAction resolving;
    private final String dotId;
    private boolean applied;

    public ApplyShadowDotAction(AbstractCreature target, AbstractCreature source, String id, int stacks) {
        super(target, source, new ShadowDotPower(target, id, stacks), stacks);
        dotId = id;
    }

    @Override public void update() {
        ApplyShadowDotAction previous = resolving;
        resolving = this;
        try { super.update(); }
        finally { resolving = previous; }
    }

    // Called only after vanilla ApplyPowerAction has passed Artifact and other vetoes.
    public static void applied(ShadowDotPower power) {
        ApplyShadowDotAction action = resolving;
        if (action == null || action.applied || action.target != power.owner
                || !action.dotId.equals(power.ID)) return;
        action.applied = true;
        if (ShadowDotPower.TOUCH.equals(power.ID))
            Misery.onTouchApplied(action.target, action.source, action.amount);
    }
}
