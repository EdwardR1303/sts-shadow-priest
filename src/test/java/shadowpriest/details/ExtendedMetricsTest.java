package shadowpriest.details;

import org.junit.Test;
import static org.junit.Assert.*;
import static shadowpriest.details.CombatReport.*;

public class ExtendedMetricsTest {
    @Test public void insanityUsesSharedSortingTurnsAndSavedOverall(){
        Run run=new Run();CombatReport c=run.begin(1,1,"Cultist");c.insanityRecorded=true;
        c.record(Metric.INSANITY,new Source("prep","Preparation","relic",true),10,"Priest",null);
        c.turns=2;c.record(Metric.INSANITY,new Source("flay","Mind Flay","card",true),30,"Priest",null);
        Run saved=DetailsStore.JSON.fromJson(DetailsStore.JSON.toJson(run),Run.class);
        assertTrue(saved.overall().insanityRecorded);assertEquals(40,saved.overall().total(Metric.INSANITY,0));
        assertEquals(20,saved.overall().perTurn(Metric.INSANITY),0);assertEquals(30,saved.combats.get(0).total(Metric.INSANITY,2));
        assertEquals("flay",saved.overall().rows(Metric.INSANITY,0).get(0).source.id);
        saved.begin(2,1,"Legacy");assertFalse(saved.overall().insanityRecorded);
    }
    private final Source spike=new Source("spike","Mind Spike","card",true);
    private final Source flay=new Source("flay","Mind Flay","card",true);
    @Test public void absorbsOverkillFilteringAndSorting(){
        CombatReport c=new CombatReport(1,1,"Cultist");
        c.recordDamage(spike,5,20,3,"Cultist",null);
        c.recordDamage(flay,10,0,0,"Cultist",null);
        assertEquals(38,c.total(Metric.DAMAGE_DEALT,0));
        assertEquals(18,c.total(Metric.DAMAGE_DEALT,0,false));
        assertEquals("spike",c.rows(Metric.DAMAGE_DEALT,0).get(0).source.id);
        assertEquals(20,c.rows(Metric.DAMAGE_DEALT,0).get(0).blocked);
        assertEquals(3,c.rows(Metric.DAMAGE_DEALT,0).get(0).overkill);
        assertEquals("flay",c.rows(Metric.DAMAGE_DEALT,0,false).get(0).source.id);
        c.turns=2;c.recordDamage(spike,0,5,0,"Cultist",null);
        assertTrue(c.rows(Metric.DAMAGE_DEALT,2,false).isEmpty());
        assertEquals(5,c.total(Metric.DAMAGE_DEALT,2));
    }
    @Test public void extendedHistoryAndActualHealingRoundTrip(){
        Run r=new Run();CombatReport c=r.begin(1,1,"Cultist");c.extendedMetrics=true;
        c.recordDamage(spike,4,8,2,"Cultist",null);
        c.record(Metric.HEALING,flay,3,"Priest",null);
        Run copy=DetailsStore.JSON.fromJson(DetailsStore.JSON.toJson(r),Run.class);
        assertEquals(14,copy.overall().total(Metric.DAMAGE_DEALT,0));
        assertEquals(3,copy.overall().total(Metric.HEALING,0));
        assertTrue(copy.overall().extendedMetrics);
        copy.begin(2,1,"Legacy");assertFalse(copy.overall().extendedMetrics);
    }
    @Test public void legacyHistoryDoesNotInventUnrecordedDamage(){
        Run r=new Run();r.begin(1,1,"Legacy").record(Metric.DAMAGE_DEALT,spike,6,"Cultist",null);
        Run copy=DetailsStore.JSON.fromJson(DetailsStore.JSON.toJson(r).replace("\"blocked\": 0,","").replace("\"overkill\": 0,",""),Run.class);
        assertEquals(6,copy.overall().total(Metric.DAMAGE_DEALT,0));
        assertEquals(0,copy.overall().total(Metric.HEALING,0));
        assertFalse(copy.overall().extendedMetrics);
    }
}
