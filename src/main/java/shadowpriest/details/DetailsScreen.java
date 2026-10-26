package shadowpriest.details;

import basemod.TopPanelItem;
import basemod.abstracts.CustomScreen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireEnum;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.helpers.TipHelper;
import com.megacrit.cardcrawl.helpers.input.InputHelper;
import java.util.*;
import static shadowpriest.details.CombatReport.*;

/** Native modal screen: combat input/actions pause while the report is open. */
public final class DetailsScreen extends CustomScreen {
    @SpireEnum public static AbstractDungeon.CurrentScreen DETAILS;
    public static final DetailsScreen INSTANCE=new DetailsScreen();
    private static final Color BG=new Color(.055f,.045f,.085f,1), PANEL=new Color(.105f,.085f,.15f,1);
    private static final Color MUTED=new Color(.68f,.65f,.76f,1), GOLD=new Color(.94f,.78f,.45f,1);
    private static final Color[] COLORS={new Color(.66f,.40f,.95f,1),new Color(.92f,.38f,.43f,1),new Color(.29f,.68f,.87f,1)};
    private static final String[] TITLES={"Damage dealt","Damage taken","Block generated"};
    private AbstractDungeon.CurrentScreen returnScreen;
    private int combatIndex,turn,turnOffset,rowOffset;
    private Metric metric=Metric.DAMAGE_DEALT;
    private boolean overall;
    private float x,y,s;
    @Override public AbstractDungeon.CurrentScreen curScreen(){return DETAILS;}
    public boolean canOpen(){
        if(AbstractDungeon.player==null || AbstractDungeon.currMapNode==null)return false;
        AbstractDungeon.CurrentScreen screen=AbstractDungeon.screen;
        return screen==AbstractDungeon.CurrentScreen.NONE || screen==AbstractDungeon.CurrentScreen.MAP
            || screen==AbstractDungeon.CurrentScreen.COMBAT_REWARD || screen==AbstractDungeon.CurrentScreen.DEATH
            || screen==AbstractDungeon.CurrentScreen.VICTORY;
    }
    public void show(){
        if(!canOpen())return;
        returnScreen=AbstractDungeon.screen;
        Run run=DetailsTracker.run();combatIndex=run==null?0:Math.max(0,run.combats.size()-1);
        turn=turnOffset=rowOffset=0;reopen();
        InputHelper.justClickedLeft=false;
    }
    @Override public void reopen(){
        AbstractDungeon.screen=DETAILS;AbstractDungeon.isScreenUp=true;
        AbstractDungeon.overlayMenu.showBlackScreen();AbstractDungeon.overlayMenu.hideCombatPanels();
    }
    @Override public void close(){
        AbstractDungeon.previousScreen=returnScreen==AbstractDungeon.CurrentScreen.NONE?null:returnScreen;
        genericScreenOverlayReset();
    }
    public void dismiss(){InputHelper.pressedEscape=false;InputHelper.justClickedLeft=false;AbstractDungeon.closeCurrentScreen();}
    @Override public void openingSettings(){AbstractDungeon.previousScreen=DETAILS;}
    private void layout(){s=Settings.scale;x=(Settings.WIDTH-1360*s)/2;y=(Settings.HEIGHT-820*s)/2;}
    private CombatReport report(){
        Run run=DetailsTracker.run();
        return run==null||run.combats.isEmpty()?null:overall?run.overall():run.combats.get(Math.max(0,Math.min(combatIndex,run.combats.size()-1)));
    }
    public void selectOverall(boolean selected){overall=selected;turn=turnOffset=rowOffset=0;}
    public boolean isOverall(){return overall;}
    public void selectMetric(Metric selected){metric=selected;rowOffset=0;}
    public void selectTurn(int selected){turn=overall?0:selected;rowOffset=0;}
    @Override public void update(){
        layout();
        if(InputHelper.pressedEscape || click(1220,752,112,42)){dismiss();return;}
        CombatReport c=report();if(c==null)return;
        Run run=DetailsTracker.run();
        if(click(920,752,136,42))selectOverall(true);
        if(click(1068,752,136,42))selectOverall(false);
        if(!overall){
            if(click(28,690,44,40)&&combatIndex>0){combatIndex--;turn=turnOffset=rowOffset=0;}
            if(click(1288,690,44,40)&&combatIndex<run.combats.size()-1){combatIndex++;turn=turnOffset=rowOffset=0;}
        }
        for(int i=0;i<3;i++)if(click(28+i*440,568,424,100))selectMetric(Metric.values()[i]);
        c=report();
        if(click(28,480,244,42))selectTurn(0);
        int choices=overall?run.combats.size():c.turns;
        for(int i=0;i<8;i++)if(turnOffset+i+1<=choices && click(28,428-i*43,244,39)){
            if(overall){combatIndex=turnOffset+i;selectOverall(false);c=report();choices=c.turns;}
            else selectTurn(turnOffset+i+1);
            break;
        }
        if(click(28,65,108,35))turnOffset=Math.max(0,turnOffset-8);
        if(click(164,65,108,35))turnOffset=Math.min(Math.max(0,choices-8),turnOffset+8);
        int max=Math.max(0,c.rows(metric,turn).size()-8);
        if(click(1118,65,96,35))rowOffset=Math.max(0,rowOffset-8);
        if(click(1228,65,104,35))rowOffset=Math.min(max,rowOffset+8);
        if(inside(28,108,244,364)){
            if(InputHelper.scrolledUp)turnOffset=Math.max(0,turnOffset-1);
            if(InputHelper.scrolledDown)turnOffset=Math.min(Math.max(0,choices-8),turnOffset+1);
        }else if(inside(300,108,1032,414)){
            if(InputHelper.scrolledUp)rowOffset=Math.max(0,rowOffset-1);
            if(InputHelper.scrolledDown)rowOffset=Math.min(max,rowOffset+1);
        }
    }
    @Override public void render(SpriteBatch sb){
        layout();float color=sb.getPackedColor();
        rect(sb,-x/s,-y/s,Settings.WIDTH/s,Settings.HEIGHT/s,new Color(0,0,0,.78f));
        rect(sb,0,0,1360,820,BG);rect(sb,0,815,1360,5,COLORS[0]);
        text(sb,"DETAILS",28,778,GOLD,1.3f);text(sb,"Shadow Priest  /  "+(overall?"Run report":"Combat report"),218,778,MUTED,.85f);
        button(sb,"Overall",920,752,136,42,overall);button(sb,"Combat",1068,752,136,42,!overall);
        button(sb,"Close",1220,752,112,42,false);
        CombatReport c=report();
        if(c==null){
            text(sb,"No combat recorded yet.",60,550,Color.WHITE,1.1f);
            text(sb,"Tracking begins with your next combat. Earlier runs cannot be reconstructed.",60,500,MUTED,.85f);
            sb.setColor(color);return;
        }
        Run run=DetailsTracker.run();
        if(overall){
            text(sb,"Overall  |  Run so far",28,716,Color.WHITE,1);
            text(sb,run.combats.size()+" recorded combats  |  "+c.turns+" total turns  |  Includes the current combat",28,684,MUTED,.75f);
        }else{
            button(sb,"<",28,690,44,40,false);button(sb,">",1288,690,44,40,false);
            text(sb,fit("Floor "+c.floor+"  |  "+c.encounter,82),90,716,Color.WHITE,1);
            text(sb,(combatIndex+1)+" / "+run.combats.size()+" combats  |  "+c.outcome+"  |  "+c.turns+" turns",90,684,MUTED,.75f);
        }
        for(int i=0;i<3;i++){
            Metric m=Metric.values()[i];float left=28+i*440;
            rect(sb,left,568,424,100,PANEL);
            rect(sb,left,568,424,metric==m?4:1,COLORS[i]);
            text(sb,TITLES[i],left+16,642,metric==m?COLORS[i]:MUTED,.85f);
            text(sb,String.format(Locale.ROOT,"%,d",c.total(m,0)),left+16,599,Color.WHITE,1.2f);
            right(sb,String.format(Locale.ROOT,"%.1f / turn",c.perTurn(m)),left+408,600,MUTED,.8f);
        }
        text(sb,overall?"COMBATS":"TURN",28,540,MUTED,.8f);
        text(sb,TITLES[metric.ordinal()]+" by source  /  "+(overall?"Overall":turn==0?"All turns":"Turn "+turn),300,540,Color.WHITE,.9f);
        right(sb,"Amount    /    Share",1328,540,MUTED,.75f);
        button(sb,overall?"All combats":"All turns",28,480,244,42,turn==0);
        for(int i=0;i<8;i++){
            int n=turnOffset+i+1;if(n>(overall?run.combats.size():c.turns))break;
            CombatReport fight=overall?run.combats.get(n-1):c;
            button(sb,overall?n+". Floor "+fight.floor:"Turn "+n,28,428-i*43,244,39,!overall&&turn==n);
            right(sb,Long.toString(fight.total(metric,overall?0:n)),258,454-i*43,MUTED,.75f);
        }
        List<Row> rows=c.rows(metric,turn);long total=c.total(metric,turn);
        rowOffset=Math.min(rowOffset,Math.max(0,rows.size()-8));
        for(int i=0;i<8 && rowOffset+i<rows.size();i++){
            Row row=rows.get(rowOffset+i);float bottom=476-i*43;
            rect(sb,300,bottom,1032,37,PANEL);
            Color bar=COLORS[metric.ordinal()].cpy();bar.a=.33f;
            rect(sb,300,bottom,(float)(1032.0*row.amount/rows.get(0).amount),37,bar);
            text(sb,(rowOffset+i+1)+".  "+fit(row.source.name,57),312,bottom+25,Color.WHITE,.78f);
            right(sb,String.format(Locale.ROOT,"%,d     %5.1f%%",row.amount,row.percent(total)),1318,bottom+25,Color.WHITE,.78f);
        }
        if(rows.isEmpty())text(sb,"No "+TITLES[metric.ordinal()].toLowerCase(Locale.ROOT)+" recorded for this selection.",316,492,MUTED,.85f);
        button(sb,"Earlier",28,65,108,35,false);button(sb,"Later",164,65,108,35,false);
        String voidform=overall?String.format(Locale.ROOT,"Voidform: %d entries  |  %.2f / combat  |  %d charges earned",c.activationTurns.size(),c.activationTurns.size()/(double)run.combats.size(),c.readyTurns.size())
            :"Voidform: "+c.activationTurns.size()+" entries  |  First ready: "+first(c.readyTurns)+"  |  First entry: "+first(c.activationTurns);
        text(sb,voidform,300,142,GOLD,.72f);
        text(sb,"HP damage excludes Block and overkill. Block counts actual gains.",300,111,MUTED,.72f);
        text(sb,(overall?"Averages use all recorded turns. Select a combat on the left for its turns.":"Enemy actions belong to the preceding player turn."),300,82,MUTED,.72f);
        button(sb,"Up",1118,65,96,35,false);button(sb,"Down",1228,65,104,35,false);
        text(sb,DetailsStore.error==null?"Saved to details/"+run.id+".json":DetailsStore.error,28,28,DetailsStore.error==null?MUTED:COLORS[1],.64f);
        sb.setColor(color);
    }
    private static String first(List<Integer> turns){return turns.isEmpty()?"--":"turn "+turns.get(0);}
    private static String fit(String text,int length){return text.length()<=length?text:text.substring(0,length-3)+"...";}
    private boolean inside(float a,float b,float w,float h){return InputHelper.mX>=x+a*s&&InputHelper.mX<=x+(a+w)*s&&InputHelper.mY>=y+b*s&&InputHelper.mY<=y+(b+h)*s;}
    private boolean click(float a,float b,float w,float h){
        if(!InputHelper.justClickedLeft||!inside(a,b,w,h))return false;
        InputHelper.justClickedLeft=false;return true;
    }
    private void rect(SpriteBatch sb,float a,float b,float w,float h,Color color){sb.setColor(color);sb.draw(ImageMaster.WHITE_SQUARE_IMG,x+a*s,y+b*s,w*s,h*s);}
    private void text(SpriteBatch sb,String value,float a,float b,Color color,float size){
        FontHelper.tipBodyFont.getData().setScale(size);
        FontHelper.renderFontLeftTopAligned(sb,FontHelper.tipBodyFont,value,x+a*s,y+b*s,color);
        FontHelper.tipBodyFont.getData().setScale(1);
    }
    private void right(SpriteBatch sb,String value,float a,float b,Color color,float size){
        FontHelper.tipBodyFont.getData().setScale(size);
        FontHelper.renderFontRightTopAligned(sb,FontHelper.tipBodyFont,value,x+a*s,y+b*s,color);
        FontHelper.tipBodyFont.getData().setScale(1);
    }
    private void button(SpriteBatch sb,String label,float a,float b,float w,float h,boolean selected){
        rect(sb,a,b,w,h,selected?new Color(.25f,.17f,.36f,1):inside(a,b,w,h)?new Color(.2f,.16f,.27f,1):PANEL);
        text(sb,label,a+12,b+h-9,selected?Color.WHITE:MUTED,.78f);
    }
    public static final class Button extends TopPanelItem {
        public Button(){super(icon(),"shadowpriest:Details");}
        private static Texture icon(){
            Pixmap p=new Pixmap(64,64,Pixmap.Format.RGBA8888);
            p.setColor(0,0,0,0);p.fill();p.setColor(.14f,.10f,.20f,1);p.fillCircle(32,32,27);
            p.setColor(.9f,.76f,.46f,1);p.drawCircle(32,32,27);
            p.setColor(.7f,.46f,.95f,1);p.fillRectangle(16,32,8,15);p.fillRectangle(28,24,8,23);p.fillRectangle(40,15,8,32);
            Texture t=new Texture(p);p.dispose();t.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);return t;
        }
        @Override protected void onClick(){if(AbstractDungeon.screen==DETAILS)INSTANCE.dismiss();else INSTANCE.show();}
        @Override protected void onHover(){
            super.onHover();
            TipHelper.renderGenericTip(Math.min(x,Settings.WIDTH-360*Settings.scale),Settings.HEIGHT-110*Settings.scale,"Details",
                "Damage dealt, damage taken, and Block generated by turn and source. NL Open during combat, on the map, or from combat rewards.");
        }
    }
}
