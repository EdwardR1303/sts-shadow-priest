package shadowpriest.dev;

import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import shadowpriest.details.*;
import java.util.*;
import static shadowpriest.details.CombatReport.*;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Short UI-only fixture review. Does not play cards or simulate additional combats. */
public final class OverallDetailsReview {
    private static List<CombatReport> saved;
    private static void click(float x,float y){
        int oldX=InputHelper.mX,oldY=InputHelper.mY;
        InputHelper.mX=(int)((Settings.WIDTH-1360*Settings.scale)/2+x*Settings.scale);
        InputHelper.mY=(int)((Settings.HEIGHT-820*Settings.scale)/2+y*Settings.scale);
        InputHelper.justClickedLeft=true;
        try{DetailsScreen.INSTANCE.update();}finally{InputHelper.mX=oldX;InputHelper.mY=oldY;InputHelper.justClickedLeft=false;}
    }
    public static void append(Queue<Runnable> q){
        q.add(()->{
            Run run=DetailsTracker.run();saved=new ArrayList<>(run.combats);run.combats.clear();
            CombatReport first=run.begin(1,1,"Cultist");first.turns=5;first.outcome="Victory";
            first.record(Metric.DAMAGE_DEALT,new Source("spike","Mind Spike","card",true),24,"Cultist",null);
            first.record(Metric.DAMAGE_DEALT,new Source("touch","Vampiric Touch","dot",true),30,"Cultist",null);
            first.record(Metric.BLOCK,new Source("shield","Power Word: Shield","card",true),36,"Player",null);
            first.record(Metric.DAMAGE_TAKEN,new Source("cultist","Cultist","enemy",false),12,"Player",null);
            first.readyTurns.add(3);first.activationTurns.add(4);
            CombatReport current=run.begin(2,1,"Slimes");current.turns=2;
            current.record(Metric.DAMAGE_DEALT,new Source("spike","Mind Spike","card",true),18,"Slime",null);
            current.record(Metric.DAMAGE_DEALT,new Source("halo","Halo","power",true),30,"Slime",null);
            current.record(Metric.BLOCK,new Source("barrier","Power Word: Barrier","card",true),20,"Player",null);
            current.record(Metric.DAMAGE_TAKEN,new Source("slime","Slime","enemy",false),7,"Player",null);
            current.readyTurns.add(2);current.activationTurns.add(2);
            DetailsScreen.INSTANCE.show();click(970,773);
            check(DetailsScreen.INSTANCE.isOverall(),"Overall button opens the run aggregate");
            check(run.overall().total(Metric.DAMAGE_DEALT,0)==102&&run.overall().turns==7,"UI fixture combines both combats and their turns");
            CardArtReview.requestCombat("details-overall");
        });
        q.add(()->{});q.add(()->{});
        q.add(()->{
            check(CardArtReview.capturedCombat("details-overall"),"Overall damage report rendered");
            click(1100,610);CardArtReview.requestCombat("details-overall-block");
        });
        q.add(()->{});q.add(()->{});
        q.add(()->{
            check(CardArtReview.capturedCombat("details-overall-block"),"Overall Block metric rendered");
            click(120,449);check(!DetailsScreen.INSTANCE.isOverall(),"Selecting an overall combat opens its individual report");
            click(120,406);click(970,773);check(DetailsScreen.INSTANCE.isOverall(),"Overall can be reopened after selecting a combat turn");
            click(1110,773);check(!DetailsScreen.INSTANCE.isOverall(),"Combat button returns to individual report mode");
            DetailsScreen.INSTANCE.dismiss();
            Run run=DetailsTracker.run();run.combats.clear();run.combats.addAll(saved);
        });
    }
}
