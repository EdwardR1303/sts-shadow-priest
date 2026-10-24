package shadowpriest.mechanics;

/** Combat-only state. Charge serials invalidate copied/recovered activation cards. */
public final class InsanityState {
    public static final int THRESHOLD = 10;
    private int insanity;
    private boolean active;
    private int charge;
    private int recoveryStarts;

    public int amount() { return insanity; }
    public int charge() { return charge; }
    public boolean active() { return active; }
    public boolean ready() { return insanity == THRESHOLD && !active; }
    public boolean recovering() { return !active && recoveryStarts > 0; }
    public void startTurn() { if (recoveryStarts > 0) recoveryStarts--; }

    public void gain(int uniqueDots) {
        if (active || ready() || recoveryStarts > 0 || uniqueDots <= 0) return;
        insanity = Math.min(THRESHOLD, insanity + uniqueDots);
        if (ready()) charge++;
    }

    public boolean activate(int cardCharge) {
        if (!ready() || cardCharge != charge) return false;
        active = true;
        recoveryStarts = 2;
        return true;
    }

    public void endTurn() {
        if (active) { insanity = 0; active = false; charge++; }
    }
    public void reset() { insanity = 0; active = false; recoveryStarts = 0; charge++; }

    public static int effectiveCost(int original, boolean attack, boolean active) {
        return active && attack && original > 0 ? original - 1 : original;
    }
}
