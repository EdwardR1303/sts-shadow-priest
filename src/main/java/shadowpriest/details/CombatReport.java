package shadowpriest.details;

import java.util.*;

/** Serializable, engine-independent combat ledger. Amounts are actual HP lost / Block added. */
public final class CombatReport {
    public enum Metric { DAMAGE_DEALT, DAMAGE_TAKEN, BLOCK }
    public static final class Source {
        public String id, name, kind;
        public boolean player;
        public Source(String id, String name, String kind, boolean player) {
            this.id=id; this.name=name; this.kind=kind; this.player=player;
        }
    }
    public static final class Event {
        public int turn, amount;
        public Metric metric;
        public Source source;
        public String target, trigger;
        Event(int turn, Metric metric, Source source, int amount, String target, String trigger) {
            this.turn=turn; this.metric=metric; this.source=source; this.amount=amount;
            this.target=target; this.trigger=trigger;
        }
    }
    public static final class Row {
        public final Source source;
        public long amount;
        public int events;
        Row(Source source) { this.source=source; }
        public double percent(long total) { return total==0 ? 0 : 100.0*amount/total; }
    }
    public static final class Run {
        public int schemaVersion=1, ascension;
        public String id=UUID.randomUUID().toString(), seed, character, startedAt=java.time.Instant.now().toString();
        public List<CombatReport> combats=new ArrayList<>();
        /** Derived view only: never appended to the saved combat list or counted as another fight. */
        public CombatReport overall() {
            CombatReport total=new CombatReport(0,0,"Overall");
            total.turns=0;
            for(CombatReport combat:combats){
                total.turns+=combat.turns;
                total.events.addAll(combat.events);
                total.readyTurns.addAll(combat.readyTurns);
                total.activationTurns.addAll(combat.activationTurns);
            }
            return total;
        }
        public CombatReport begin(int floor, int act, String encounter) {
            CombatReport next=new CombatReport(floor,act,encounter);
            // Reloading a saved floor replays it; replace its previous attempt, never add both to totals.
            if (!combats.isEmpty()) {
                CombatReport last=combats.get(combats.size()-1);
                if (last.floor==floor && last.act==act && last.encounter.equals(encounter)) {
                    next.attempt=last.attempt+1; combats.remove(combats.size()-1);
                }
            }
            combats.add(next); return next;
        }
    }
    public int floor, act, turns=1, attempt=1;
    public String encounter, outcome="In progress";
    public List<Event> events=new ArrayList<>();
    public List<Integer> readyTurns=new ArrayList<>(), activationTurns=new ArrayList<>();
    public CombatReport(int floor, int act, String encounter) { this.floor=floor;this.act=act;this.encounter=encounter; }
    public void record(Metric metric, Source source, int amount, String target, String trigger) {
        if (amount>0) events.add(new Event(turns,metric,source,amount,target,trigger));
    }
    public long total(Metric metric, int turn) {
        long sum=0;
        for(Event e:events) if(e.metric==metric && (turn==0 || e.turn==turn)) sum+=e.amount;
        return sum;
    }
    public List<Row> rows(Metric metric, int turn) {
        Map<String,Row> rows=new LinkedHashMap<>();
        for(Event e:events) if(e.metric==metric && (turn==0 || e.turn==turn)) {
            String key=e.source.kind+":"+e.source.id;
            Row row=rows.get(key);
            if(row==null) {row=new Row(e.source);rows.put(key,row);}
            row.amount+=e.amount;row.events++;
        }
        List<Row> sorted=new ArrayList<>(rows.values());
        sorted.sort(Comparator.comparingLong((Row row)->row.amount).reversed().thenComparing(row->row.source.name));
        return sorted;
    }
    public double perTurn(Metric metric) { return total(metric,0)/(double)Math.max(1,turns); }
    public static int hpLost(int before, int after) { return Math.max(0,Math.max(0,before)-Math.max(0,after)); }
}
