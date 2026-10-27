package shadowpriest.details;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import com.megacrit.cardcrawl.screens.mainMenu.MainMenuScreen;
import java.nio.file.Paths;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.CompletableFuture;

/** Menu-owned modal. Does not set dungeon screen state or load a game save. */
public final class DetailsHistoryScreen {
    private static boolean open;
    private static DetailsHistory.Catalog catalog;
    private static CompletableFuture<DetailsHistory.Catalog> scan;
    private static CompletableFuture<CombatReport.Run> loading;
    private static String error;
    private static int offset;
    private static float x,y,s;
    private static final Color BG=new Color(.055f,.045f,.085f,1),PANEL=new Color(.13f,.10f,.19f,1),MUTED=new Color(.7f,.67f,.78f,1);
    private static void layout(){s=Settings.scale;x=(Settings.WIDTH-1360*s)/2;y=(Settings.HEIGHT-820*s)/2;}
    public static boolean isOpen(){return open;}
    public static boolean ready(){return scan==null&&loading==null;}
    public static DetailsHistory.Catalog catalog(){return catalog;}
    public static void open(){open=true;offset=0;refresh();InputHelper.justClickedLeft=false;}
    private static void refresh(){
        error=null;catalog=null;
        scan=CompletableFuture.supplyAsync(()->{try{return DetailsHistory.scan(Paths.get("details"));}catch(Exception e){throw new RuntimeException(e);}});
    }
    public static void select(String id){
        error=null;loading=CompletableFuture.supplyAsync(()->DetailsHistory.load(Paths.get("details"),id));
    }
    public static void close(){
        if(DetailsScreen.INSTANCE.viewingArchive())DetailsScreen.INSTANCE.dismiss();
        open=false;scan=null;loading=null;InputHelper.pressedEscape=false;InputHelper.justClickedLeft=false;
    }
    private static boolean menuAvailable(MainMenuScreen menu){
        return menu.screen==MainMenuScreen.CurScreen.MAIN_MENU&&!menu.isSettingsUp&&!menu.isFadingOut;
    }
    private static void update(){
        layout();
        if(DetailsScreen.INSTANCE.viewingArchive()){DetailsScreen.INSTANCE.update();return;}
        if(InputHelper.pressedEscape||click(1220,752,112,42)){close();return;}
        if(scan!=null&&scan.isDone()){
            try{catalog=scan.join();offset=Math.min(offset,Math.max(0,catalog.entries.size()-8));}
            catch(Exception e){error="Could not read saved reports. Try Refresh.";}finally{scan=null;}
        }
        if(loading!=null&&loading.isDone()){
            try{CombatReport.Run run=loading.join();if(run==null)error="This report is unavailable or incompatible. Refresh the list to try another run.";
                else DetailsScreen.INSTANCE.showArchive(run,()->{});
            }catch(Exception e){error="Could not open this report.";}finally{loading=null;}
            return;
        }
        if(!ready())return;
        if(click(1068,752,136,42)){refresh();return;}
        if(catalog==null||error!=null)return;
        for(int i=0;i<8&&offset+i<catalog.entries.size();i++)
            if(click(28,610-i*66,1304,59)){select(catalog.entries.get(offset+i).id);return;}
        int max=Math.max(0,catalog.entries.size()-8);
        if(click(28,45,132,42)||InputHelper.scrolledUp)offset=Math.max(0,offset-8);
        if(click(172,45,132,42)||InputHelper.scrolledDown)offset=Math.min(max,offset+8);
    }
    private static void render(SpriteBatch sb){
        if(DetailsScreen.INSTANCE.viewingArchive()){DetailsScreen.INSTANCE.renderAboveGameUI(sb);return;}
        layout();float old=sb.getPackedColor();
        rect(sb,-x/s,-y/s,Settings.WIDTH/s,Settings.HEIGHT/s,new Color(0,0,0,.8f));
        rect(sb,0,0,1360,820,BG);rect(sb,0,815,1360,5,new Color(.65f,.35f,.95f,1));
        text(sb,"DETAILS",28,778,new Color(.94f,.78f,.45f,1),1.3f);
        text(sb,"Saved runs",225,778,Color.WHITE,1);
        button(sb,"Refresh",1068,752,136,42);button(sb,"Close",1220,752,112,42);
        text(sb,"Choose a run to inspect its Overall totals, combats, turns, and damage sources.",28,711,MUTED,.85f);
        if(!ready())text(sb,loading==null?"Loading saved runs...":"Opening report...",28,635,Color.WHITE,1);
        else if(error!=null)text(sb,error,28,635,new Color(1,.5f,.5f,1),.9f);
        else if(catalog==null||catalog.entries.isEmpty()){
            text(sb,"No recorded runs yet.",28,635,Color.WHITE,1);
            text(sb,"Details saves reports as you play. Runs from before recording was added cannot be reconstructed.",28,590,MUTED,.8f);
        }else{
            for(int i=0;i<8&&offset+i<catalog.entries.size();i++){
                DetailsHistory.Entry e=catalog.entries.get(offset+i);float bottom=610-i*66;
                rect(sb,28,bottom,1304,59,inside(28,bottom,1304,59)?new Color(.23f,.15f,.32f,1):PANEL);
                text(sb,date(e.startedAt)+"   |   "+safe(e.character,28)+"   |   A"+e.ascension,42,bottom+43,Color.WHITE,.85f);
                text(sb,e.combats+" combats  |  Last: Floor "+e.floor+", "+safe(e.lastEncounter,33)+" - "+safe(e.lastOutcome,20),42,bottom+19,MUTED,.7f);
                text(sb,"Seed: "+safe(e.seed,32),960,bottom+37,MUTED,.7f);
            }
            button(sb,"Newer",28,45,132,42);button(sb,"Older",172,45,132,42);
            text(sb,(offset+1)+"-"+Math.min(offset+8,catalog.entries.size())+" of "+catalog.entries.size()+" runs",330,74,MUTED,.8f);
        }
        text(sb,"Read-only history  |  Newest first"+(catalog!=null&&catalog.skipped>0?"  |  "+catalog.skipped+" unreadable or incompatible reports skipped":""),28,23,MUTED,.67f);
        sb.setColor(old);
    }
    private static String date(String value){long time=DetailsHistory.timestamp(value);return time==0?safe(value,20):DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm").withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(time));}
    private static String safe(String value,int max){if(value==null)return "Unknown";return value.length()>max?value.substring(0,max-3)+"...":value;}
    private static boolean inside(float a,float b,float w,float h){return InputHelper.mX>=x+a*s&&InputHelper.mX<=x+(a+w)*s&&InputHelper.mY>=y+b*s&&InputHelper.mY<=y+(b+h)*s;}
    private static boolean click(float a,float b,float w,float h){if(!InputHelper.justClickedLeft||!inside(a,b,w,h))return false;InputHelper.justClickedLeft=false;return true;}
    private static void rect(SpriteBatch sb,float a,float b,float w,float h,Color c){sb.setColor(c);sb.draw(ImageMaster.WHITE_SQUARE_IMG,x+a*s,y+b*s,w*s,h*s);}
    private static void text(SpriteBatch sb,String value,float a,float b,Color c,float scale){FontHelper.tipBodyFont.getData().setScale(scale);FontHelper.renderFontLeftTopAligned(sb,FontHelper.tipBodyFont,value,x+a*s,y+b*s,c);FontHelper.tipBodyFont.getData().setScale(1);}
    private static void button(SpriteBatch sb,String label,float a,float b,float w,float h){rect(sb,a,b,w,h,inside(a,b,w,h)?new Color(.25f,.17f,.36f,1):PANEL);text(sb,label,a+12,b+h-10,Color.WHITE,.82f);}
    private static void menuButtonLayout(){s=Settings.scale;x=Settings.WIDTH-280*s;y=Settings.HEIGHT-160*s;}

    @SpirePatch(clz=MainMenuScreen.class,method="update")
    public static class MenuUpdate {
        @SpirePrefixPatch public static SpireReturn<Void> before(MainMenuScreen __instance){
            if(open){update();return SpireReturn.Return(null);}
            if(menuAvailable(__instance)){menuButtonLayout();if(click(0,0,240,50)){open();return SpireReturn.Return(null);}}
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(clz=MainMenuScreen.class,method="render")
    public static class MenuRender {
        @SpirePostfixPatch public static void after(MainMenuScreen __instance,SpriteBatch sb){
            if(open)render(sb);
            else if(menuAvailable(__instance)){float old=sb.getPackedColor();menuButtonLayout();button(sb,"Combat Details",0,0,240,50);sb.setColor(old);}
        }
    }
}
