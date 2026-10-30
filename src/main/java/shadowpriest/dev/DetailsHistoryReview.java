package shadowpriest.dev;

import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import shadowpriest.details.*;
import java.nio.file.*;
import java.util.*;
import static shadowpriest.details.CombatReport.*;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Title-menu-only UI review, with isolated fixture reports and no dungeon launch. */
public final class DetailsHistoryReview {
    private static final Queue<Runnable> steps=new ArrayDeque<>();
    private static boolean initialized,finished;
    private static float delay;
    private static long began=System.currentTimeMillis();
    private static Run recent,old,tracked;
    private static AbstractDungeon.CurrentScreen dungeonScreen;
    private static void click(float a,float b){
        InputHelper.mX=(int)((Settings.WIDTH-1360*Settings.scale)/2+a*Settings.scale);
        InputHelper.mY=(int)((Settings.HEIGHT-820*Settings.scale)/2+b*Settings.scale);
        InputHelper.justClickedLeft=true;DetailsHistoryScreen.MenuUpdate.before(CardCrawlGame.mainMenuScreen);
    }
    public static void update(){
        if(finished)return;
        try{
            if(System.currentTimeMillis()-began>120000)throw new AssertionError("History UI review timed out");
            if(CardCrawlGame.mainMenuScreen==null||CardCrawlGame.mainMenuScreen.screen!=MainMenuScreen.CurScreen.MAIN_MENU)return;
            if(DetailsHistoryScreen.isOpen()&&!DetailsHistoryScreen.ready())return;
            delay+=Gdx.graphics.getDeltaTime();if(delay<(initialized?.4f:2f))return;delay=0;
            if(!initialized){initialize();initialized=true;return;}
            if(steps.isEmpty()){finished=true;CombatSmokeTest.finish(null);return;}
            steps.remove().run();
        }catch(Throwable failure){finished=true;CombatSmokeTest.finish(failure);}
    }
    private static Run fixture(String date,int damage){
        Run r=new Run();r.startedAt=date;r.character="Shadow Priest";r.seed="TEST-123";r.ascension=20;
        CombatReport c=r.begin(1,1,"Cultist");c.turns=4;c.outcome="Victory";
        c.record(Metric.DAMAGE_DEALT,new Source("touch","Vampiric Touch","dot",true),damage,"Cultist",null);
        c.record(Metric.BLOCK,new Source("shield","Power Word: Shield","card",true),18,"Player",null);
        CombatReport last=r.begin(2,1,"Slimes");last.turns=2;last.outcome="Defeat";
        last.record(Metric.DAMAGE_DEALT,new Source("spike","Mind Spike","card",true),12,"Slime",null);return r;
    }
    private static void initialize(){
        steps.add(()->{
            tracked=DetailsTracker.run();dungeonScreen=AbstractDungeon.screen;
            recent=fixture("2099-10-27T12:00:00Z",30);old=fixture("2099-10-26T12:00:00Z",20);
            DetailsStore.save(old);DetailsStore.save(recent);DetailsStore.flush();
            CardArtReview.requestCombat("history-menu-button");
        });
        steps.add(()->{
            check(CardArtReview.capturedCombat("history-menu-button"),"Main menu Details button rendered");
            com.megacrit.cardcrawl.screens.mainMenu.MenuButton button=CardCrawlGame.mainMenuScreen.buttons.stream().filter(b->b.result==DetailsHistoryScreen.DETAILS_HISTORY).findFirst().get();
            button.hb.clicked=true;button.update();
            check(DetailsHistoryScreen.isOpen(),"Main-menu button opens archive without a dungeon");
        });
        steps.add(()->{
            check(DetailsHistoryScreen.catalog()!=null&&DetailsHistoryScreen.catalog().entries.size()>=2,"Saved reports discovered asynchronously");
            check(DetailsHistoryScreen.catalog().entries.get(0).id.equals(recent.id),"Newest archive appears first");
            CardArtReview.requestCombat("history-run-list");
        });
        steps.add(()->{
            check(CardArtReview.capturedCombat("history-run-list"),"Run list rendered with dates ascension and last combat");click(200,640);
        });
        steps.add(()->{
            check(DetailsScreen.INSTANCE.viewingArchive()&&DetailsScreen.INSTANCE.viewedRun().id.equals(recent.id),"Clicking a row opens the selected saved run");
            check(DetailsScreen.INSTANCE.isOverall()&&DetailsScreen.INSTANCE.viewedRun().overall().total(Metric.DAMAGE_DEALT,0)==42,"Saved Overall totals include both combats");
            check(DetailsTracker.run()==tracked&&AbstractDungeon.screen==dungeonScreen,"Archive does not replace tracker or dungeon screen");
            CardArtReview.requestCombat("history-overall");
        });
        steps.add(()->{
            check(CardArtReview.capturedCombat("history-overall"),"Saved Overall report rendered");
            click(120,449);check(!DetailsScreen.INSTANCE.isOverall(),"Saved combat drill-down works");
            click(120,449);click(800,610);CardArtReview.requestCombat("history-turn-block");
        });
        steps.add(()->{
            check(CardArtReview.capturedCombat("history-turn-block"),"Saved combat turn and Block view rendered");
            InputHelper.pressedEscape=true;DetailsHistoryScreen.MenuUpdate.before(CardCrawlGame.mainMenuScreen);
            check(DetailsHistoryScreen.isOpen()&&!DetailsScreen.INSTANCE.viewingArchive(),"Escape from report returns to archive list");
            DetailsHistoryScreen.select(old.id);
        });
        steps.add(()->{
            check(DetailsScreen.INSTANCE.viewedRun().id.equals(old.id)&&DetailsScreen.INSTANCE.viewedRun().overall().total(Metric.DAMAGE_DEALT,0)==32,"A second run loads independently");
            click(1270,773);check(!DetailsScreen.INSTANCE.viewingArchive(),"Back button returns to run list");
            InputHelper.pressedEscape=true;DetailsHistoryScreen.MenuUpdate.before(CardCrawlGame.mainMenuScreen);
            check(!DetailsHistoryScreen.isOpen()&&!CardCrawlGame.mainMenuScreen.isSettingsUp,"Escape closes archive without opening Settings");
            check(DetailsTracker.run()==tracked&&AbstractDungeon.screen==dungeonScreen,"Leaving archive preserves original game state");
            try{Files.deleteIfExists(Paths.get("details",recent.id+".json"));Files.deleteIfExists(Paths.get("details",old.id+".json"));}
            catch(Exception e){throw new RuntimeException(e);}
        });
    }
}
