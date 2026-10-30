package shadowpriest.details;

import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.*;
import java.util.stream.Stream;
import static shadowpriest.details.CombatReport.*;

/** Read-only archive access, independent of the active tracker and game save. */
public final class DetailsHistory {
    public static final class Entry {
        public final String id, startedAt, character, seed, lastEncounter, lastOutcome;
        public final int ascension, combats, floor;
        Entry(Run run) {
            id=run.id;startedAt=run.startedAt;character=run.character;seed=run.seed;ascension=run.ascension;combats=run.combats.size();
            CombatReport last=run.combats.get(combats-1);floor=last.floor;lastEncounter=last.encounter;lastOutcome=last.outcome;
        }
    }
    public static final class Catalog {
        public final List<Entry> entries=new ArrayList<>();
        public int skipped;
    }
    public static Catalog scan(Path root) throws java.io.IOException {
        Catalog catalog=new Catalog();
        if(!Files.exists(root))return catalog;
        try(Stream<Path> files=Files.list(root)) {
            files.filter(p->Files.isRegularFile(p)&&p.getFileName().toString().endsWith(".json")).forEach(p->{
                String name=p.getFileName().toString();Run run=load(root,name.substring(0,name.length()-5));
                if(run==null)catalog.skipped++;
                else if(!run.combats.isEmpty())catalog.entries.add(new Entry(run));
            });
        }
        catalog.entries.sort(Comparator.comparingLong((Entry e)->timestamp(e.startedAt)).reversed().thenComparing(e->e.id));
        return catalog;
    }
    public static long timestamp(String value) {
        try{return java.time.Instant.parse(value).toEpochMilli();}
        catch(Exception ignored){
            // Early Details builds used Date.toString(), including a named timezone.
            try{return new java.text.SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy",Locale.US).parse(value).getTime();}
            catch(Exception invalid){return 0;}
        }
    }
    public static Run load(Path root,String id) {
        try {
            String canonical=UUID.fromString(id).toString();if(!canonical.equals(id))return null;
            Run run=DetailsStore.JSON.fromJson(new String(Files.readAllBytes(root.resolve(canonical+".json")),StandardCharsets.UTF_8),Run.class);
            if(run==null||run.schemaVersion!=1||!id.equals(run.id)||run.startedAt==null||run.combats==null)return null;
            for(CombatReport c:run.combats) {
                if(c==null||c.encounter==null||c.outcome==null||c.turns<1||c.events==null||c.readyTurns==null||c.activationTurns==null)return null;
                for(Event e:c.events)
                    if(e==null||e.metric==null||e.source==null||e.source.id==null||e.source.name==null||e.amount<0||e.blocked<0||e.overkill<0)return null;
            }
            return run;
        } catch(Exception ignored) { return null; }
    }
    private DetailsHistory() { }
}
