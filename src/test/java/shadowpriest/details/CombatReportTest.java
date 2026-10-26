package shadowpriest.details;

import org.junit.Test;
import java.util.List;
import static org.junit.Assert.*;
import static shadowpriest.details.CombatReport.*;

public class CombatReportTest {
    private final Source spike=new Source("spike","Mind Spike","card",true);
    private final Source touch=new Source("touch","Vampiric Touch","dot",true);
    @Test public void overallCombinesCompletedAndCurrentCombatsWithWeightedAverages(){
        Run run=new Run();CombatReport first=run.begin(1,1,"Cultist");
        first.turns=2;first.outcome="Victory";
        first.record(Metric.DAMAGE_DEALT,spike,30,"Cultist",null);
        first.record(Metric.BLOCK,spike,8,"Player",null);
        first.readyTurns.add(1);first.activationTurns.add(2);
        CombatReport current=run.begin(2,1,"Slimes");current.turns=4;
        current.record(Metric.DAMAGE_DEALT,spike,6,"Slime",null);
        current.record(Metric.DAMAGE_DEALT,touch,24,"Slime",null);
        current.record(Metric.DAMAGE_TAKEN,touch,9,"Player",null);
        current.activationTurns.add(3);
        CombatReport total=run.overall();
        assertEquals(6,total.turns);assertEquals(60,total.total(Metric.DAMAGE_DEALT,0));
        assertEquals(10,total.perTurn(Metric.DAMAGE_DEALT),0);
        assertEquals(9,total.total(Metric.DAMAGE_TAKEN,0));assertEquals(8,total.total(Metric.BLOCK,0));
        List<Row> rows=total.rows(Metric.DAMAGE_DEALT,0);
        assertEquals("spike",rows.get(0).source.id);assertEquals(36,rows.get(0).amount);
        assertEquals(60,rows.get(0).percent(60),0);assertEquals(2,total.activationTurns.size());
        assertEquals(1,total.readyTurns.size());assertEquals(2,run.combats.size());
        current.record(Metric.DAMAGE_DEALT,spike,6,"Slime",null);
        assertEquals(66,run.overall().total(Metric.DAMAGE_DEALT,0));
        assertEquals(30,first.total(Metric.DAMAGE_DEALT,0));
    }
    @Test public void overallUsesRestoredHistoryAndDoesNotCountReplayedAttemptTwice(){
        Run run=new Run();CombatReport first=run.begin(1,1,"Cultist");
        first.record(Metric.DAMAGE_DEALT,spike,10,"Cultist",null);
        run.begin(2,1,"Slimes").record(Metric.DAMAGE_DEALT,spike,100,"Slime",null);
        Run loaded=DetailsStore.JSON.fromJson(DetailsStore.JSON.toJson(run),Run.class);
        loaded.begin(2,1,"Slimes").record(Metric.DAMAGE_DEALT,touch,5,"Slime",null);
        assertEquals(15,loaded.overall().total(Metric.DAMAGE_DEALT,0));
        assertEquals(2,loaded.overall().turns);assertEquals(2,loaded.combats.size());
        assertEquals(2,loaded.combats.get(1).attempt);
    }
    @Test public void overallHandlesEmptyRunAndZeroEventCombats(){
        Run run=new Run();assertEquals(0,run.overall().turns);
        assertEquals(0,run.overall().perTurn(Metric.BLOCK),0);
        run.begin(1,1,"Cultist").turns=3;
        assertEquals(3,run.overall().turns);assertTrue(run.overall().rows(Metric.BLOCK,0).isEmpty());
    }
    @Test public void aggregatesAndSortsSourcesWithoutMixingMetrics(){
        CombatReport c=new CombatReport(1,1,"Cultist");
        c.record(Metric.DAMAGE_DEALT,spike,6,"Cultist",null);
        c.record(Metric.DAMAGE_DEALT,touch,5,"Cultist",null);
        c.record(Metric.DAMAGE_DEALT,spike,6,"Cultist",null);
        c.record(Metric.DAMAGE_TAKEN,spike,3,"Player",null);
        List<Row> rows=c.rows(Metric.DAMAGE_DEALT,0);
        assertEquals(17,c.total(Metric.DAMAGE_DEALT,0));assertEquals(2,rows.size());
        assertEquals("spike",rows.get(0).source.id);assertEquals(2,rows.get(0).events);
        assertEquals(100,rows.get(0).percent(17)+rows.get(1).percent(17),.00001);
    }
    @Test public void emptyTurnsRemainInAverageAndFiltersAreIndependent(){
        CombatReport c=new CombatReport(1,1,"Cultist");
        c.record(Metric.BLOCK,spike,9,"Player",null);c.turns=3;
        c.record(Metric.BLOCK,spike,6,"Player",null);
        assertEquals(5,c.perTurn(Metric.BLOCK),0);assertEquals(0,c.total(Metric.BLOCK,2));
        assertEquals(6,c.total(Metric.BLOCK,3));assertTrue(c.rows(Metric.BLOCK,2).isEmpty());
    }
    @Test public void hpLossExcludesOverkillHealingAndDeadTargets(){
        assertEquals(5,hpLost(5,-995));assertEquals(0,hpLost(-995,0));
        assertEquals(0,hpLost(10,20));assertEquals(0,hpLost(0,-100));assertEquals(3,hpLost(20,17));
    }
    @Test public void replayReplacesOnlySameEncounterOnSameFloor(){
        Run run=new Run();CombatReport old=run.begin(4,1,"Cultist");
        old.record(Metric.DAMAGE_DEALT,spike,100,"Cultist",null);
        CombatReport next=run.begin(4,1,"Cultist");
        assertEquals(1,run.combats.size());assertEquals(2,next.attempt);assertEquals(0,next.total(Metric.DAMAGE_DEALT,0));
        run.begin(5,1,"Cultist");assertEquals(2,run.combats.size());
    }
    @Test public void jsonPreservesEventsAndVoidformTiming(){
        Run run=new Run();CombatReport c=run.begin(4,1,"Cultist");
        c.record(Metric.DAMAGE_DEALT,touch,5,"Cultist","Psychic Link");c.readyTurns.add(1);c.activationTurns.add(2);
        Run copy=DetailsStore.JSON.fromJson(DetailsStore.JSON.toJson(run),Run.class);
        assertEquals(run.id,copy.id);assertEquals(5,copy.combats.get(0).total(Metric.DAMAGE_DEALT,0));
        assertEquals("Psychic Link",copy.combats.get(0).events.get(0).trigger);
        assertEquals(Integer.valueOf(2),copy.combats.get(0).activationTurns.get(0));
    }
    @Test public void nonPositiveEventsDoNotPolluteBreakdowns(){
        CombatReport c=new CombatReport(1,1,"Cultist");
        c.record(Metric.BLOCK,spike,0,"Player",null);c.record(Metric.BLOCK,spike,-2,"Player",null);
        assertTrue(c.events.isEmpty());assertEquals(0,c.perTurn(Metric.BLOCK),0);
    }
}
