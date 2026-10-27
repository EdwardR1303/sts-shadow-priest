package shadowpriest.details;

import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;
import java.nio.file.*;
import java.nio.charset.StandardCharsets;
import static org.junit.Assert.*;
import static shadowpriest.details.CombatReport.*;

public class DetailsHistoryTest {
    @Rule public TemporaryFolder folder=new TemporaryFolder();
    private Path root(){return folder.getRoot().toPath();}
    private Run report(String date){Run r=new Run();r.startedAt=date;r.ascension=12;r.character="Shadow Priest";r.seed="123";
        CombatReport c=r.begin(7,1,"Slime Boss");c.outcome="Defeat";c.turns=3;
        c.record(Metric.DAMAGE_DEALT,new Source("spike","Mind Spike","card",true),27,"Slime Boss",null);return r;}
    private void write(Run r)throws Exception{Files.write(root().resolve(r.id+".json"),DetailsStore.JSON.toJson(r).getBytes(StandardCharsets.UTF_8));}
    @Test public void archiveListsNewestFirstAndLoadsFullBreakdownWithoutWriting()throws Exception{
        Run old=report("2026-10-25T12:00:00Z"),recent=report("2026-10-27T12:00:00Z"),legacy=report("Mon Oct 26 01:26:30 PDT 2026");write(old);write(recent);write(legacy);
        byte[] before=Files.readAllBytes(root().resolve(recent.id+".json"));
        DetailsHistory.Catalog c=DetailsHistory.scan(root());assertEquals(3,c.entries.size());assertEquals(recent.id,c.entries.get(0).id);assertEquals(legacy.id,c.entries.get(1).id);
        assertEquals(12,c.entries.get(0).ascension);assertEquals(7,c.entries.get(0).floor);assertEquals("Defeat",c.entries.get(0).lastOutcome);
        Run loaded=DetailsHistory.load(root(),recent.id);assertEquals(27,loaded.overall().total(Metric.DAMAGE_DEALT,0));
        loaded.combats.clear();assertArrayEquals(before,Files.readAllBytes(root().resolve(recent.id+".json")));
    }
    @Test public void malformedAndFutureReportsAreSkippedButValidReportsRemain()throws Exception{
        Run valid=report("2026-10-27T12:00:00Z");write(valid);
        Run future=report("2026-10-28T12:00:00Z");future.schemaVersion=2;write(future);
        Run broken=report("2026-10-29T12:00:00Z");broken.combats.get(0).events=null;write(broken);
        Files.write(root().resolve("bad.json"),"broken".getBytes(StandardCharsets.UTF_8));
        DetailsHistory.Catalog c=DetailsHistory.scan(root());assertEquals(1,c.entries.size());assertEquals(3,c.skipped);
        assertEquals(valid.id,c.entries.get(0).id);assertNull(DetailsHistory.load(root(),"../outside"));
    }
    @Test public void missingFolderAndEmptyRunsGiveEmptyHistory()throws Exception{
        assertTrue(DetailsHistory.scan(root().resolve("missing")).entries.isEmpty());write(new Run());
        DetailsHistory.Catalog c=DetailsHistory.scan(root());assertTrue(c.entries.isEmpty());assertEquals(0,c.skipped);
    }
}
