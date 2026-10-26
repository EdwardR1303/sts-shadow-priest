package shadowpriest.details;

import basemod.abstracts.CustomSavable;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowpriest.character.ShadowPriest;
import java.util.*;
import static shadowpriest.details.CombatReport.*;

/** Read-only observer. Source tags follow queued actions, including actions queued by other actions. */
public final class DetailsTracker implements CustomSavable<String> {
    private static Run run;
    private static CombatReport combat;
    private static boolean active, firstTurn, dirty;
    private static long lastSave;
    private static final Map<AbstractGameAction,Tag> tags=new WeakHashMap<>();
    private static final Deque<Tag> contexts=new ArrayDeque<>();
    private static final Deque<DamageFrame> damage=new ArrayDeque<>();
    private static final Source UNKNOWN=new Source("unknown","Unattributed effect","unknown",false);
    private static final class Tag {
        final Source source; final String trigger;
        Tag(Source source,String trigger){this.source=source;this.trigger=trigger;}
    }
    private static final class DamageFrame {
        final AbstractCreature target; final DamageInfo info; final Tag tag;
        DamageFrame(AbstractCreature target,DamageInfo info,Tag tag){this.target=target;this.info=info;this.tag=tag;}
    }
    public static Run run(){return run;}
    public static CombatReport current(){return combat;}
    public static boolean active(){return active;}
    public static void resetRun(){
        save();run=null;combat=null;active=false;dirty=false;
        tags.clear();contexts.clear();damage.clear();
    }
    public static void begin(){
        if(!(AbstractDungeon.player instanceof ShadowPriest))return;
        if(run==null){
            run=new Run();run.seed=String.valueOf(Settings.seed);run.ascension=AbstractDungeon.ascensionLevel;
            run.character="Shadow Priest";
        }
        StringJoiner enemies=new StringJoiner(" + ");
        if(AbstractDungeon.getMonsters()!=null)
            for(AbstractMonster m:AbstractDungeon.getMonsters().monsters)enemies.add(m.name);
        combat=run.begin(AbstractDungeon.floorNum,AbstractDungeon.actNum,enemies.length()==0?"Combat":enemies.toString());
        active=true;firstTurn=true;dirty=true;tags.clear();contexts.clear();damage.clear();
    }
    public static void startTurn(){
        if(!active)return;
        if(firstTurn)firstTurn=false;else combat.turns++;
        dirty=true;save();
    }
    public static void update(){
        if(active && AbstractDungeon.player!=null && AbstractDungeon.currMapNode!=null){
            AbstractRoom room=AbstractDungeon.getCurrRoom();
            // PostUpdate runs after the lethal hit (and any Fairy/Lizard Tail revival) has finished.
            if(AbstractDungeon.player.isDead)finish("Defeat");
            else if(room!=null && room.isBattleOver)finish(room.smoked?"Escaped":"Victory");
            else if(room!=null && room.phase!=AbstractRoom.RoomPhase.COMBAT)finish("Interrupted");
        }
        if(dirty && System.currentTimeMillis()-lastSave>2000)save();
    }
    public static void finish(String outcome){if(active){combat.outcome=outcome;active=false;dirty=true;save();}}
    public static void save(){if(run!=null && dirty){DetailsStore.save(run);dirty=false;lastSave=System.currentTimeMillis();}}
    @Override public String onSave(){save();DetailsStore.flush();return run==null?null:run.id;}
    @Override public void onLoad(String id){
        if(id==null)return;
        DetailsStore.flush();
        Run restored=DetailsStore.load(id);
        if(restored!=null){run=restored;combat=null;active=false;dirty=false;}
    }
    public static void ready(){if(active){combat.readyTurns.add(combat.turns);dirty=true;}}
    public static void activated(){if(active){combat.activationTurns.add(combat.turns);dirty=true;}}
    private static Tag currentTag(){
        if(!contexts.isEmpty())return contexts.peek();
        return AbstractDungeon.actionManager==null?null:tags.get(AbstractDungeon.actionManager.currentAction);
    }
    public static Source card(AbstractCard c){return new Source(c.cardID+(c.upgraded?"+":""),c.name,"card",true);}
    public static Source power(AbstractPower p){
        String id=p.ID,name=p.name;
        if(id.startsWith("shadowpriest:Halo:")){id="shadowpriest:Halo";name="Halo";}
        boolean dot=id.equals("shadowpriest:Touch")||id.equals("shadowpriest:Pain")||id.equals("shadowpriest:Madness");
        return new Source(id,name,dot?"dot":"power",dot || p.owner==AbstractDungeon.player);
    }
    public static Source relic(AbstractRelic r){return new Source(r.relicId,r.name,"relic",true);}
    public static void pushCard(AbstractCard card){contexts.push(new Tag(card(card),null));}
    public static void popCard(){if(!contexts.isEmpty())contexts.pop();}
    public static void queue(AbstractGameAction action){
        if(!active || tags.containsKey(action))return;
        Tag tag=currentTag();if(tag!=null)tags.put(action,tag);
    }
    public static void tag(AbstractGameAction action,Source source){
        if(active && !tags.containsKey(action))tags.put(action,new Tag(source,null));
    }
    public static <T extends AbstractGameAction>T dot(T action,AbstractPower power){
        if(!active)return action;
        Source source=power(power);Tag parent=currentTag();String trigger=null;
        if(parent!=null && parent.source.id.equals("shadowpriest:PsychicLink")){
            trigger=parent.source.name;
            source=new Source(source.id+"@"+parent.source.id,source.name+" ("+trigger+")","dot",true);
        }
        tags.put(action,new Tag(source,trigger));return action;
    }
    public static void beforeDamage(AbstractCreature target,DamageInfo info){
        Tag tag=currentTag();
        if(tag==null){
            AbstractCreature owner=info.owner;
            Source source=owner==null?UNKNOWN:new Source(owner.id,owner==AbstractDungeon.player?"Unattributed player effect":owner.name,
                owner==AbstractDungeon.player?"unknown":"enemy",owner==AbstractDungeon.player);
            tag=new Tag(source,null);
        }
        damage.push(new DamageFrame(target,info,tag));
    }
    public static void afterDamage(){if(!damage.isEmpty())damage.pop();}
    public static void healthWrite(AbstractCreature target,int before,int after){
        if(!active || damage.isEmpty() || damage.peek().target!=target)return;
        DamageFrame frame=damage.peek();int amount=hpLost(before,after);
        if(target==AbstractDungeon.player)record(Metric.DAMAGE_TAKEN,frame.tag,amount,target.name);
        else if(frame.info.owner==AbstractDungeon.player || (frame.info.owner==null && frame.tag.source.player))
            record(Metric.DAMAGE_DEALT,frame.tag,amount,target.name);
    }
    public static void block(AbstractCreature target,int before){
        if(active && target==AbstractDungeon.player){
            Tag tag=currentTag();record(Metric.BLOCK,tag==null?new Tag(UNKNOWN,null):tag,Math.max(0,target.currentBlock-before),target.name);
        }
    }
    private static void record(Metric metric,Tag tag,int amount,String target){
        if(amount<=0)return;
        combat.record(metric,tag.source,amount,target,tag.trigger);dirty=true;
    }
    private DetailsTracker(){}
    public static DetailsTracker saveField(){return new DetailsTracker();}
}
