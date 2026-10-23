package shadowpriest.mechanics;

/** Combat-only state. Charge serials invalidate copied/recovered activation cards. */
public final class InsanityState {
    public static final int THRESHOLD = 10;
    private int insanity;
    private boolean active;
    private int charge;

    public int amount() { return insanity; }
    public int charge() { return charge; }
    public boolean active() { return active; }
    public boolean ready() { return insanity == THRESHOLD && !active; }

    public void gain(int uniqueDots) {
        if (active || ready() || uniqueDots <= 0) return;
        insanity = Math.min(THRESHOLD, insanity + uniqueDots);
        if (ready()) charge++;
    }

    public boolean activate(int cardCharge) {
        if (!ready() || cardCharge != charge) return false;
        active = true;
        return true;
    }

    public void endTurn() { if (active) reset(); }
    public void reset() { insanity = 0; active = false; charge++; }

    public static int effectiveCost(int original, boolean attack, boolean active) {
        return active && attack && original > 0 ? original - 1 : original;
    }
}
