package shadowpriest.mechanics;

import org.junit.Test;
import static org.junit.Assert.*;

public class InsanityStateTest {
    @Test public void improvedFormCarriesPartialAttackCountAndOnlyThenStartsRecovery() {
        InsanityState s=new InsanityState(); s.gain(100); s.activate(s.charge());
        s.attackPlayed(); s.attackPlayed(); s.endTurn(true);
        assertTrue(s.active()); assertEquals(2,s.attacksPlayed());
        s.startTurn(); s.endTurn(true); s.startTurn();
        assertTrue(s.active()); assertFalse(s.recovering());
        s.attackPlayed(); assertTrue(s.active()); // The third Attack does not end the stance mid-turn.
        s.attackPlayed(); s.endTurn(true); assertFalse(s.active()); assertEquals(0,s.amount());
        s.startTurn(); s.gain(100); assertEquals(0,s.amount()); assertTrue(s.recovering());
        s.endTurn(true); s.startTurn(); s.gain(100); assertTrue(s.ready());
        s.activate(s.charge()); assertEquals(0,s.attacksPlayed());
    }
    @Test public void improvedFormAllowsNoAttackTurnsButCannotEndSurrender() {
        InsanityState s=new InsanityState(); s.gain(100); s.activate(s.charge());
        for(int i=0;i<6;i++){s.endTurn(true);s.startTurn();}
        assertTrue(s.active()); assertEquals(0,s.attacksPlayed());
        s.surrender(); for(int i=0;i<5;i++)s.attackPlayed(); s.endTurn(true);
        assertTrue(s.surrendered()); assertTrue(s.active()); assertFalse(s.recovering());
        s.reset(); assertEquals(0,s.attacksPlayed()); assertFalse(s.active());
    }
    @Test public void gainsUsePointsAndOnlyOneHundredReadiesVoidform() {
        InsanityState s = new InsanityState();
        s.gain(10); assertEquals(10, s.amount()); assertFalse(s.ready());
        s.gain(89); assertEquals(99, s.amount()); assertFalse(s.ready());
        s.gain(1); assertEquals(100, s.amount()); assertTrue(s.ready());
    }
    @Test public void nonpositiveGainsAreIgnoredAndHugeGainsCapSafely() {
        InsanityState s = new InsanityState();
        s.gain(30); s.gain(0); s.gain(-10); assertEquals(30, s.amount());
        s.gain(Integer.MAX_VALUE); assertEquals(100, s.amount()); assertTrue(s.ready());
    }
    @Test public void twoDotsAndThreeHitsProduceThirtyAndPersist() {
        InsanityState s = new InsanityState();
        for (int i=0; i<3; i++) s.gain(2 * InsanityState.PER_DOT); s.endTurn();
        assertEquals(30, s.amount()); assertFalse(s.ready());
    }
    @Test public void thresholdCapsOneChargeUntilChosen() {
        InsanityState s = new InsanityState(); s.gain(90); s.gain(20);
        int token = s.charge(); s.gain(1000); s.endTurn();
        assertEquals(100, s.amount()); assertEquals(token, s.charge());
        assertTrue(s.ready()); assertFalse(s.active());
        assertTrue(s.activate(token)); assertFalse(s.activate(token));
        s.gain(50); assertEquals(100, s.amount());
        s.endTurn(); assertEquals(0, s.amount()); assertFalse(s.active());
    }
    @Test public void oldOrCopiedTokensCannotSpendANewCharge() {
        InsanityState s = new InsanityState(); s.gain(100); int old = s.charge();
        s.activate(old); s.endTurn(); s.startTurn(); s.endTurn(); s.startTurn(); s.gain(100);
        assertFalse(s.activate(old)); assertTrue(s.activate(s.charge()));
    }
    @Test public void combatResetInvalidatesReadyCards() {
        InsanityState s = new InsanityState(); s.gain(100); int old = s.charge();
        s.reset(); assertEquals(0, s.amount()); assertFalse(s.activate(old));
    }
    @Test public void recoveryBlocksTheEntireFollowingTurn() {
        InsanityState s = new InsanityState(); s.gain(100); s.activate(s.charge());
        s.endTurn(); s.gain(20); assertEquals(0, s.amount());
        s.startTurn(); assertTrue(s.recovering());
        s.gain(100); s.endTurn(); s.gain(20); assertEquals(0, s.amount());
        s.startTurn(); assertFalse(s.recovering()); s.gain(20); assertEquals(20, s.amount());
    }
    @Test public void combatResetClearsRecovery() {
        InsanityState s = new InsanityState(); s.gain(100); s.activate(s.charge()); s.endTurn();
        s.reset(); assertFalse(s.recovering()); s.gain(30); assertEquals(30, s.amount());
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
    @Test public void surrenderBypassesThresholdAndPersistsUntilCombatReset() {
        InsanityState s = new InsanityState(); s.gain(20);
        assertTrue(s.surrender()); assertTrue(s.active()); assertTrue(s.surrendered());
        for (int i = 0; i < 5; i++) { s.endTurn(); s.startTurn(); s.gain(100); }
        assertTrue(s.active()); assertEquals(0, s.amount()); assertFalse(s.ready());
        assertFalse(s.recovering()); assertFalse(s.surrender());
        s.reset(); assertFalse(s.active()); assertFalse(s.surrendered());
        s.gain(100); assertTrue(s.activate(s.charge())); s.endTurn(); assertFalse(s.active());
    }
    @Test public void surrenderInvalidatesReadyTokens() {
        InsanityState s = new InsanityState(); s.gain(100); int token = s.charge();
        s.surrender(); assertFalse(s.activate(token)); assertFalse(s.ready());
        assertNotEquals(token, s.charge());
    }
    @Test public void surrenderExtendsActiveFormAndCanBypassRecovery() {
        InsanityState s = new InsanityState(); s.gain(100); s.activate(s.charge());
        assertTrue(s.surrender()); s.endTurn(); assertTrue(s.active());
        s.reset(); s.gain(100); s.activate(s.charge()); s.endTurn(); s.startTurn();
        assertTrue(s.recovering()); assertTrue(s.surrender()); assertFalse(s.recovering());
        s.endTurn(); assertTrue(s.active());
    }
    @Test public void hpDamageRewardsEventsNotDamageMagnitude() {
        InsanityState s = new InsanityState();
        s.gainFromHpLoss(1); s.gainFromHpLoss(30); s.gainFromHpLoss(3);
        assertEquals(15, s.amount());
        s.gainFromHpLoss(0); s.gainFromHpLoss(-1);
        assertEquals(15, s.amount());
        s.endTurn(); assertEquals(15, s.amount());
    }
    @Test public void hpDamageCanReadyOnlyOneChargeAndObeysAllLockouts() {
        InsanityState s = new InsanityState(); s.gain(98);
        s.gainFromHpLoss(3); assertEquals(100, s.amount()); assertTrue(s.ready());
        int charge = s.charge(); s.gainFromHpLoss(10); assertEquals(charge, s.charge());
        assertTrue(s.activate(charge)); s.gainFromHpLoss(3); assertEquals(100, s.amount());
        s.endTurn(); s.gainFromHpLoss(3); assertEquals(0, s.amount());
        s.startTurn(); s.gainFromHpLoss(3); s.endTurn(); s.gainFromHpLoss(3);
        assertEquals(0, s.amount());
        s.startTurn(); s.gainFromHpLoss(3); assertEquals(5, s.amount());
        s.reset(); s.surrender(); s.gainFromHpLoss(3); assertEquals(0, s.amount());
        s.reset(); s.gainFromHpLoss(3); assertEquals(5, s.amount());
    }

}
