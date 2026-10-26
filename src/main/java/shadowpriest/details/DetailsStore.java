package shadowpriest.details;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.UUID;
import java.util.concurrent.*;
import org.apache.logging.log4j.LogManager;

/** Snapshots are serialized on the game thread; the worker never reads live game state. */
public final class DetailsStore {
    public static final Gson JSON=new GsonBuilder().setPrettyPrinting().create();
    private static final ExecutorService WRITER=Executors.newSingleThreadExecutor(r->{
        Thread t=new Thread(r,"Shadow Priest Details writer");t.setDaemon(true);return t;
    });
    private static final Path ROOT=Paths.get("details");
    public static volatile String error;
    private static Path path(String id) { return ROOT.resolve(UUID.fromString(id).toString()+".json"); }
    public static void save(CombatReport.Run run) {
        final String json=JSON.toJson(run); final Path destination=path(run.id);
        WRITER.submit(()->{
            try {
                Files.createDirectories(ROOT);
                Path temp=destination.resolveSibling(destination.getFileName()+".tmp");
                Files.write(temp,json.getBytes(StandardCharsets.UTF_8));
                try { Files.move(temp,destination,StandardCopyOption.REPLACE_EXISTING,StandardCopyOption.ATOMIC_MOVE); }
                catch(AtomicMoveNotSupportedException ex) { Files.move(temp,destination,StandardCopyOption.REPLACE_EXISTING); }
                error=null;
            } catch(Exception ex) {
                if(error==null) LogManager.getLogger(DetailsStore.class).warn("Cannot save combat Details",ex);
                error="Could not save Details. See console.log.";
            }
        });
    }
    public static CombatReport.Run load(String id) {
        try {
            Path file=path(id);
            if(!Files.exists(file))return null;
            CombatReport.Run run=JSON.fromJson(new String(Files.readAllBytes(file),StandardCharsets.UTF_8),CombatReport.Run.class);
            return run!=null && run.schemaVersion==1 && id.equals(run.id) && run.combats!=null ? run:null;
        } catch(Exception ex) { LogManager.getLogger(DetailsStore.class).warn("Cannot restore combat Details",ex);return null; }
    }
    public static void flush() {
        try { WRITER.submit(()->{}).get(3,TimeUnit.SECONDS); }
        catch(Exception ex) { LogManager.getLogger(DetailsStore.class).warn("Details writer did not finish",ex); }
    }
    private DetailsStore() {}
}
