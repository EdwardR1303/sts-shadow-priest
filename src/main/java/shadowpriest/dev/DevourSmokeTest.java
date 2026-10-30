package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.potions.FairyPotion;
import com.megacrit.cardcrawl.relics.LizardTail;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.actions.DevourAction;
import shadowpriest.mechanics.CombatPowerPlays;
import shadowpriest.powers.*;
import shadowpriest.relics.*;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused checks of the Devour/conditional-glow/Surrender revision, not a simulated run. */
public final class DevourSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m,n;private static VoidformRelic r;
    private static AbstractCard chosen,other,played;private static boolean selected,ending;
    private static int hp;
    private static void reset(){
        p.powers.clear();p.loseBlock();p.currentHealth=p.maxHealth=200;
        p.relics.removeIf(relic->!relic.relicId.equals(VoidformRelic.ID));r.reset();
        for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
        for(int i=0;i<30;i++)p.drawPile.addToTop(new MindSpike());
        for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters){enemy.powers.clear();enemy.loseBlock();enemy.currentHealth=enemy.maxHealth=10000;}
        CombatPowerPlays.State.count.set(p,0);EnergyPanel.totalCount=3;chosen=null;other=null;selected=false;
    }
    private static <T extends AbstractCard>T up(T c){c.upgrade();return c;}
    private static int stat(String id){return p.hasPower(id)?p.getPower(id).amount:0;}
    private static long count(CardGroup g,Class<?> kind){return g.group.stream().filter(kind::isInstance).count();}
    private static void curse(){p.hand.addToTop(new MindDecay());}
    private static void play(AbstractCard c,AbstractMonster target,int energy){
        played=c;p.hand.addToTop(c);c.energyOnUse=energy;EnergyPanel.totalCount=energy;
        c.applyPowers();if(target!=null)c.calculateCardDamage(target);
        check(c.canUse(p,target),"Playable: "+c.name);p.useCard(c,target,energy);
    }
    private static void unlock(){for(int i=0;i<2;i++){r.state.enterImmediately();r.state.endTurn();}}
    private static SurrenderDeadlinePower deadline(){return (SurrenderDeadlinePower)p.getPower(SurrenderDeadlinePower.POWER_ID);}
    public static void handleSelection(){
        if(chosen==null||AbstractDungeon.screen!=AbstractDungeon.CurrentScreen.GRID)return;
        check(AbstractDungeon.actionManager.currentAction instanceof DevourAction,"Multiple Curses open the Devour chooser");
        check(AbstractDungeon.gridSelectScreen.targetGroup.size()==2&&AbstractDungeon.gridSelectScreen.targetGroup.contains(chosen),"Chooser offers only both actual hand Curses");
        AbstractDungeon.gridSelectScreen.selectedCards.add(chosen);AbstractDungeon.closeCurrentScreen();selected=true;chosen=null;
    }
    public static boolean handleCompletion(){
        if(!ending)return false;
        if(Boolean.getBoolean("shadowpriest.devourvictory")){
            if(!AbstractDungeon.getCurrRoom().isBattleOver)return false;
            check(!p.isDead&&p.currentHealth>0,"Final-turn DoT lethal wins before Surrender's deadline");
            ending=false;CombatSmokeTest.finish(null);return true;
        }
        if(!p.isDead)return false;
        check(p.currentHealth==0&&AbstractDungeon.deathScreen!=null,"Fifth turn ends in unavoidable death");
        check(p.potions.get(0) instanceof FairyPotion&&p.getRelic("Lizard Tail").counter==-1
            &&((GuardianAngelPower)p.getPower(GuardianAngelPower.POWER_ID)).available(),"Deadline bypasses Fairy, Lizard Tail, and Guardian Angel without consuming them");
        ending=false;CombatSmokeTest.finish(null);return true;
    }
    public static void append(Queue<Runnable> q){
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);r=VoidformRelic.current();reset();
            n=new Cultist(450,0);AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(n,false));});
        q.add(()->{reset();play(new MindTrauma(),m,1);});
        q.add(()->{check(MadnessPower.stacks(m)==6&&m.currentHealth==9994,"Trauma without Curse keeps base effects");reset();curse();p.powers.add(new HastePower(p,5));play(up(new MindTrauma()),m,1);});
        q.add(()->{check(MadnessPower.stacks(m)==15&&count(p.exhaustPile,MindDecay.class)==1,"Trauma+ consumes once for 15 Madness; Haste does not multiply Devour");reset();curse();m.powers.add(new ArtifactPower(m,1));play(new MindTrauma(),m,1);});
        q.add(()->{check(MadnessPower.stacks(m)==0&&!m.hasPower("Artifact"),"Combined Devour Madness makes one Artifact check");reset();curse();p.powers.add(new DexterityPower(p,4));play(new FocusedWill(),null,1);});
        q.add(()->{check(p.currentBlock==18&&r.state.amount()==10,"Focused Will combines 14 base Block and Dexterity once");reset();curse();p.powers.add(new DexterityPower(p,4));p.powers.add(new FrailPower(p,2,false));play(up(new FocusedWill()),null,1);});
        q.add(()->{check(p.currentBlock==15&&r.state.amount()==15,"Focused Will+ applies Frail once to combined 17+4 Block");reset();p.currentHealth=100;p.powers.add(new ShadowMendDebtPower(p,4));p.powers.add(new ArtifactPower(p,1));curse();play(new ShadowMend(),null,1);});
        q.add(()->{check(p.currentHealth==110&&stat(ShadowMendDebtPower.POWER_ID)==4&&stat("Artifact")==1,"Devoured Mend preserves existing debt and Artifact while healing");reset();p.currentHealth=100;play(up(new ShadowMend()),null,1);});
        q.add(()->{check(stat(ShadowMendDebtPower.POWER_ID)==1&&p.currentHealth==110,"Mend+ without Curse still creates one debt");reset();play(up(new PurifyDisease()),null,0);});
        q.add(()->{check(!p.hasPower("Artifact")&&played.selfRetain&&p.exhaustPile.contains(played),"Purify+ has Retain but no unconditional Artifact");reset();chosen=new Doubt();other=new Pain();p.hand.addToTop(chosen);p.hand.addToTop(other);p.powers.add(new WeakPower(p,2,false));p.relics.add(new VoidVengeance());play(new PurifyDisease(),null,0);});
        q.add(()->{check(selected&&count(p.exhaustPile,Doubt.class)==1&&p.hand.contains(other),"Devour exhausts only the chosen Curse");check(!p.hasPower("Weakened")&&stat("Artifact")==1&&stat(HastePower.POWER_ID)==2,"Purify cleanses before conditional Artifact and triggers Void Vengeance");reset();curse();play(new TwinSuns(),m,1);});
        q.add(()->{check(stat(HastePower.POWER_ID)==2,"Twin Suns Devour works against zero Strength");reset();curse();m.powers.add(new StrengthPower(m,4));play(up(new TwinSuns()),m,1);});
        q.add(()->{check(stat(HastePower.POWER_ID)==7&&m.getPower("Strength").amount==4,"Twin Suns+ combines enemy Strength, upgrade, and Devour without removing Strength");reset();curse();play(new TentacleSlam(),m,2);});
        q.add(()->{check(EnergyPanel.totalCount==2&&m.currentHealth==9992,"Tentacle refunds actual two energy, keeps base damage");reset();curse();TentacleSlam c=new TentacleSlam();c.freeToPlayOnce=true;play(c,m,3);});
        q.add(()->{check(EnergyPanel.totalCount==3,"Free Tentacle cannot create energy");reset();curse();r.enterImmediately();});
        q.add(()->play(new TentacleSlam(),m,2));
        q.add(()->{check(EnergyPanel.totalCount==2,"Voidform Tentacle refunds only the discounted payment");reset();curse();play(up(new UnfurlingDarkness()),m,0);});
        q.add(()->{check(m.getPower(ShadowDotPower.TOUCH).amount==3&&n.getPower(ShadowDotPower.TOUCH).amount==3,"Unfurling spreads Touch to other enemies");reset();m.currentBlock=4;play(new ShadowWordMadness(),m,1);});
        q.add(()->{check(MadnessPower.stacks(m)==5&&count(p.hand,MindDecay.class)==1,"SW Madness applies only HP removed and creates retained fuel");reset();m.powers.add(new ArtifactPower(m,1));play(up(new ShadowWordMadness()),m,1);});
        q.add(()->{check(m.currentHealth==9987&&MadnessPower.stacks(m)==0&&count(p.hand,MindDecay.class)==1,"SW Madness+ deals 13 and creates Curse even when Artifact blocks application");reset();CombatPowerPlays.State.count.set(p,2);play(new MadnessOverwhelming(),m,1);});
        q.add(()->{check(MadnessPower.stacks(m)==20&&count(p.discardPile,MindDecay.class)==1,"Overwhelming uses 10 plus 5 per Power and creates discard fuel");reset();CombatPowerPlays.State.count.set(p,2);play(up(new MadnessOverwhelming()),m,1);});
        q.add(()->{check(MadnessPower.stacks(m)==31,"Overwhelming+ uses 15 plus 8 per Power");reset();glows();visuals();reset();
            SurrenderToMadness s=new SurrenderToMadness();check(!s.canUse(p,null),"Surrender locked at zero completed forms");s.use(p,null);});
        q.add(()->{check(!VoidformRelic.isPermanent(),"Forced use cannot bypass Surrender's gate");r.state.enterImmediately();check(!new SurrenderToMadness().canUse(p,null),"Entering a form does not count as leaving it");r.state.endTurn();check(!new SurrenderToMadness().canUse(p,null),"Surrender locked at one completed form");r.state.enterImmediately();r.state.endTurn();
            check(r.state.recovering()&&new SurrenderToMadness().canUse(p,null),"Surrender unlocks after second exit, including Recharge");
            p.powers.add(new ArtifactPower(p,1));play(up(new SurrenderToMadness()),null,0);});
        q.add(()->{check(VoidformRelic.isPermanent()&&deadline().amount==5&&stat("Strength")==3&&stat(MasteryPower.POWER_ID)==3,"Surrender+ immediately grants three of each stat and a five-turn deadline");check(stat("Artifact")==1&&!new SurrenderToMadness().canUse(p,null),"Artifact stays intact and duplicate Surrender is locked");
            check(VoidformRelic.effectiveCost(new BloodMagic(),2)==1,"Surrender retains normal one-energy Attack discount");new SurrenderToMadness().use(p,null);});
        q.add(()->{check(deadline().amount==5&&stat("Strength")==3,"Forced duplicate cannot refresh timer or stats");endTurn();});
        q.add(()->{check(deadline().amount==4&&stat("Strength")==6&&stat(MasteryPower.POWER_ID)==6,"Casting turn counts as turn one; following start adds stats");
            SurrenderDeadlinePower d=deadline();int old=d.amount;boolean battle=AbstractDungeon.getCurrRoom().isBattleOver;AbstractDungeon.getCurrRoom().isBattleOver=true;d.finishTurn();check(d.amount==old&&!p.isDead,"Victory cancels deadline");AbstractDungeon.getCurrRoom().isBattleOver=battle;
            reset();check(!new SurrenderToMadness().canUse(p,null)&&r.state.completedForms()==0,"Combat reset clears gate progress");unlock();play(new SurrenderToMadness(),null,0);});
        q.add(()->{check(stat("Strength")==2&&stat(MasteryPower.POWER_ID)==2,"Base Surrender grants two of each stat");m.powers.add(new MadnessPower(m,10));hp=m.currentHealth;endTurn();});
        q.add(()->{check(deadline().amount==4&&m.currentHealth<hp,"Natural DoTs resolve on Surrender turns");endTurn();});
        q.add(()->{check(deadline().amount==3,"Second turn leaves three");endTurn();});
        q.add(()->{check(deadline().amount==2,"Third turn leaves two");endTurn();});
        q.add(()->{check(deadline().amount==1&&!p.isDead,"Fourth turn survives with one turn remaining");
            if(Boolean.getBoolean("shadowpriest.devourvictory")){
                for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters){enemy.powers.clear();enemy.currentHealth=1;enemy.powers.add(new MadnessPower(enemy,2));}
            }
            p.powers.add(new GuardianAngelPower(p));p.powers.add(new IntangiblePlayerPower(p,3));p.addBlock(999);p.relics.add(new LizardTail());FairyPotion fairy=new FairyPotion();p.potions.set(0,fairy);fairy.setAsObtained(0);ending=true;endTurn();});
    }
    private static void visuals(){
        reset();curse();
        for(AbstractCard c:new AbstractCard[]{new TentacleSlam(),new MindTrauma(),up(new PurifyDisease()),up(new FocusedWill()),new ShadowWordMadness(),up(new MadnessOverwhelming())}){
            c.applyPowers();ConditionalCards.update(c,null);c.triggerOnGlowCheck();c.beginGlowing();
            CardArtReview.card(c,"devour-"+c.cardID.split(":")[1]);
        }
        reset();SurrenderToMadness s=up(new SurrenderToMadness());ConditionalCards.update(s,null);CardArtReview.card(s,"surrender-locked");
        unlock();ConditionalCards.update(s,null);s.triggerOnGlowCheck();s.beginGlowing();CardArtReview.card(s,"surrender-ready");
        ShadowWordDeath death=up(new ShadowWordDeath());m.currentHealth=19;death.calculateCardDamage(m);ConditionalCards.update(death,m);death.beginGlowing();CardArtReview.card(death,"death-lethal");
    }
    private static void endTurn(){
        AbstractDungeon.getCurrRoom().skipMonsterTurn=false;
        for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters){enemy.setMove((byte)0,AbstractMonster.Intent.NONE);enemy.createIntent();}
        AbstractDungeon.overlayMenu.endTurnButton.disable(true);p.endTurnQueued=true;
    }
    private static void glows(){
        MindTrauma trauma=new MindTrauma();check(!ConditionalCards.ready(trauma,null),"Devour has no ready glow without Curse");curse();check(ConditionalCards.ready(trauma,null),"Devour glows with Curse");ConditionalCards.update(trauma,null);check(trauma.rawDescription.contains("*Apply"),"Ready Devour clause uses gold text");p.hand.clear();ConditionalCards.update(trauma,null);check(!trauma.rawDescription.contains("*"),"Gold text clears when Curse leaves hand");
        p.currentHealth=69;check(ConditionalCards.ready(new Bulwark(),null),"Bulwark glows below 35 percent");p.currentHealth=70;check(!ConditionalCards.ready(new Bulwark(),null),"Bulwark is not empowered at exactly 35 percent");
        Piety piety=new Piety();p.hand.addToTop(piety);p.hand.addToTop(new Piety());check(ConditionalCards.ready(piety,null),"Piety ignores other Piety copies");p.hand.addToTop(new MindSpike());p.hand.addToTop(new PowerWordShield());check(!ConditionalCards.ready(piety,null),"Piety is unlit when both card types are present");p.hand.clear();
        m.currentBlock=5;check(ConditionalCards.ready(new DispelMagic(),m)&&!ConditionalCards.ready(new DispelMagic(),n),"Dispel glow follows hovered enemy");m.currentBlock=0;m.powers.add(new ArtifactPower(m,1));check(!ConditionalCards.ready(new DispelMagic(),m)&&ConditionalCards.ready(up(new DispelMagic()),m),"Only upgraded Dispel recognizes Artifact");check(ConditionalCards.ready(new MassDispel(),null),"Mass Dispel sees removable enemy buff");m.powers.clear();
        p.powers.add(new WeakPower(p,1,false));check(ConditionalCards.ready(new PurifyDisease(),null),"Purify glows for a cleansable debuff");p.powers.clear();
        ShadowWordDeath death=new ShadowWordDeath();m.currentHealth=10;check(ConditionalCards.lethal(death,m),"Death recognizes lethal direct hit");m.currentBlock=1;check(!ConditionalCards.lethal(death,m),"Death respects target Block");death.upgrade();m.currentHealth=19;check(ConditionalCards.lethal(death,m),"Death+ combines its hits through Block");m.currentHealth=25;check(!ConditionalCards.lethal(death,m),"Two hits are not incorrectly lethal");p.powers.add(new HastePower(p,1));check(ConditionalCards.lethal(death,m),"Death+ includes Haste repetitions");m.powers.add(new BufferPower(m,2));check(!ConditionalCards.lethal(death,m),"Death preview accounts for Buffer");m.powers.clear();m.powers.add(new IntangiblePower(m,1));check(!ConditionalCards.lethal(death,m),"Death preview accounts for Intangible");
    }
}
