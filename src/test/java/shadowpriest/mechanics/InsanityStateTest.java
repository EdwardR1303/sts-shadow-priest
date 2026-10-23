package shadowpriest.mechanics;

import org.junit.Test;
import static org.junit.Assert.*;

public class InsanityStateTest {
    @Test public void twoDotsAndThreeHitsProduceSixAndPersist() {
        InsanityState s = new InsanityState();
        s.gain(2); s.gain(2); s.gain(2); s.endTurn();
        assertEquals(6, s.amount()); assertFalse(s.ready());
    }
    @Test public void thresholdCapsOneChargeUntilChosen() {
        InsanityState s = new InsanityState(); s.gain(9); s.gain(2);
        int token = s.charge(); s.gain(100); s.endTurn();
        assertEquals(10, s.amount()); assertEquals(token, s.charge());
        assertTrue(s.ready()); assertFalse(s.active());
        assertTrue(s.activate(token)); assertFalse(s.activate(token));
        s.gain(5); assertEquals(10, s.amount());
        s.endTurn(); assertEquals(0, s.amount()); assertFalse(s.active());
    }
    @Test public void oldOrCopiedTokensCannotSpendANewCharge() {
        InsanityState s = new InsanityState(); s.gain(10); int old = s.charge();
        s.activate(old); s.endTurn(); s.gain(10);
        assertFalse(s.activate(old)); assertTrue(s.activate(s.charge()));
    }
    @Test public void combatResetInvalidatesReadyCards() {
        InsanityState s = new InsanityState(); s.gain(10); int old = s.charge();
        s.reset(); assertEquals(0, s.amount()); assertFalse(s.activate(old));
    }
    @Test public void costsRespectExistingChangesAndNeverMutateNegativeCosts() {
        assertEquals(0, InsanityState.effectiveCost(1, true, true));
        assertEquals(2, InsanityState.effectiveCost(3, true, true));
        assertEquals(0, InsanityState.effectiveCost(0, true, true));
        assertEquals(-1, InsanityState.effectiveCost(-1, true, true));
        assertEquals(-2, InsanityState.effectiveCost(-2, true, true));
        assertEquals(1, InsanityState.effectiveCost(1, false, true));
        assertEquals(1, InsanityState.effectiveCost(1, true, false));
    }
}
