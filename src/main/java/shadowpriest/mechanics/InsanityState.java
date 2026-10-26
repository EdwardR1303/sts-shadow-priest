package shadowpriest.mechanics;

/** Combat-only state. Charge serials invalidate copied/recovered activation cards. */
public final class InsanityState {
    public static final int THRESHOLD = 100;
    public static final int PER_DOT = 5;
    public static final int PER_DAMAGE_TAKEN = 5;
    private int insanity;
    private boolean active;
    private boolean surrendered;
    private int charge;
    private int recoveryStarts;

    public int amount() { return insanity; }
    public int charge() { return charge; }
    public boolean active() { return active; }
    public boolean surrendered() { return surrendered; }
    public boolean ready() { return insanity == THRESHOLD && !active; }
    public boolean recovering() { return !active && recoveryStarts > 0; }
    public void startTurn() { if (recoveryStarts > 0) recoveryStarts--; }

    /** Accept resource points; attack callers convert their unique-DoT count first. */
    public void gain(int points) {
        if (active || ready() || recoveryStarts > 0 || points <= 0) return;
        insanity += Math.min(THRESHOLD - insanity, points);
        if (ready()) charge++;
    }

    /** One gain per HP-damaging event, independent of its damage amount or source. */
    public void gainFromHpLoss(int damageAmount) {
        if (damageAmount > 0) gain(PER_DAMAGE_TAKEN);
    }

    public boolean activate(int cardCharge) {
        if (!ready() || cardCharge != charge) return false;
        active = true;
        recoveryStarts = 2;
        return true;
    }

    /** Bypass charge/recovery once; an already active form is extended, not entered again. */
    public boolean surrender() {
        if (surrendered) return false;
        surrendered = true; active = true; insanity = 0; recoveryStarts = 0; charge++;
        return true;
    }
    public void endTurn() {
        if (surrendered) return;
        if (active) { insanity = 0; active = false; charge++; }
    }
    public void reset() { insanity = 0; active = false; surrendered = false; recoveryStarts = 0; charge++; }

    public static int effectiveCost(int original, boolean attack, boolean active) {
        return active && attack && original > 0 ? original - 1 : original;
    }
}
