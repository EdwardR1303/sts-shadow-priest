package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.*;
import shadowpriest.mechanics.CombatPowerPlays;
import shadowpriest.actions.*;
import java.util.*;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused coverage of the ten final collectible cards; no run simulation. */
public final class FinalBatchSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m,n;private static VoidformRelic r;
    private static VoidRay master,other,ray;private static AbstractCard curse,selected,token;
    private static int hp;private static boolean ending;private static long victoryWait;
    private static <T extends AbstractCard>T up(T c){c.upgrade();return c;}
    private static int stat(String id){return p.hasPower(id)?p.getPower(id).amount:0;}
    private static void add(AbstractGameAction a){AbstractDungeon.actionManager.addToBottom(a);}
    private static void reset(){
        p.powers.clear();p.loseBlock();p.currentHealth=p.maxHealth=500;
        p.relics.removeIf(x->!x.relicId.equals(VoidformRelic.ID));r.reset();
        for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
        for(AbstractMonster t:AbstractDungeon.getMonsters().monsters){t.powers.clear();t.loseBlock();t.currentHealth=t.maxHealth=10000;}
        CombatPowerPlays.State.count.set(p,0);selected=null;EnergyPanel.totalCount=3;
        shadowpriest.legendary.LegendaryEquipment.State.equipped.set(p,null);
    }
    private static void play(AbstractCard c,AbstractMonster target){
        p.drawPile.removeCard(c);p.discardPile.removeCard(c);p.hand.removeCard(c);p.hand.addToTop(c);
        EnergyPanel.totalCount=3;c.energyOnUse=3;c.applyPowers();if(target!=null)c.calculateCardDamage(target);
        check(c.canUse(p,target),"Playable: "+c.name);p.useCard(c,target,3);
    }
    private static void dots(int touch,int pain,int madness){
        if(touch>0)m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,touch));
        if(pain>0)m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,pain));
        if(madness>0)m.powers.add(new MadnessPower(m,madness));
    }
    private static void nextTurn(){
        for(AbstractMonster t:AbstractDungeon.getMonsters().monsters){t.setMove((byte)0,AbstractMonster.Intent.NONE);t.createIntent();}
        AbstractDungeon.getCurrRoom().skipMonsterTurn=false;AbstractDungeon.overlayMenu.endTurnButton.disable(true);p.endTurnQueued=true;
    }
    public static void handleSelection(){
        if(p==null||AbstractDungeon.screen!=AbstractDungeon.CurrentScreen.GRID)return;
        if(AbstractDungeon.actionManager.currentAction instanceof VoidRayAction){
            AbstractCard choice=selected!=null?selected:p.hand.group.stream().filter(c->c.type==AbstractCard.CardType.CURSE).findFirst().orElse(null);
            check(choice!=null&&p.hand.contains(choice),"Void Ray visibly selects an actual hand Curse");
            AbstractDungeon.gridSelectScreen.selectedCards.clear();AbstractDungeon.gridSelectScreen.selectedCards.add(choice);AbstractDungeon.closeCurrentScreen();
        }else if(AbstractDungeon.actionManager.currentAction instanceof ForesightAction){
            AbstractDungeon.gridSelectScreen.selectedCards.clear();AbstractDungeon.gridSelectScreen.selectedCards.add(selected);AbstractDungeon.closeCurrentScreen();
        }
    }
    public static boolean handleCompletion(){
        if(!ending||AbstractDungeon.getCurrRoom()==null||!m.isDying)return false;
        if(victoryWait==0)victoryWait=System.currentTimeMillis();
        if(AbstractDungeon.getCurrRoom().phase==AbstractRoom.RoomPhase.COMBAT){
            if(System.currentTimeMillis()-victoryWait>8000)throw new AssertionError("Final hit did not finish combat");
            return true;
        }
        check(master.misc==5,"Void Ray persists earned growth when its hit ends combat");
        CombatSmokeTest.finish(null);return true;
    }
    public static void append(Queue<Runnable> q){
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);r=VoidformRelic.current();
            add(new SpawnMonsterAction(new Cultist(300f,0),false));});
        q.add(()->{n=AbstractDungeon.getMonsters().monsters.get(1);reset();
            int[][] expected={{11,13,4},{7,14,5},{4,5,5}};CardGroup[] pools={AbstractDungeon.commonCardPool,AbstractDungeon.uncommonCardPool,AbstractDungeon.rareCardPool};
            for(int i=0;i<pools.length;i++){int[] found=new int[3];for(AbstractCard c:pools[i].group)if(c.color==shadowpriest.character.ShadowPriest.Meta.CARD_COLOR){if(c.type==AbstractCard.CardType.ATTACK)found[0]++;else if(c.type==AbstractCard.CardType.SKILL)found[1]++;else if(c.type==AbstractCard.CardType.POWER)found[2]++;}check(Arrays.equals(found,expected[i]),"Reward pool "+Arrays.toString(found));}
            for(String id:new String[]{"VoidSurge","MadnessOverwhelming","Singularity","TwinSuns","MaladyOfTheMind","VoidEntropy","DarkReinforcement","VoidRay","MentalDecay","EventHorizon"}){
                AbstractCard c=CardLibrary.getCard("shadowpriest:"+id).makeCopy();check(c.portrait!=null&&!c.rawDescription.isEmpty(),id+" registered with art/text");
                CardArtReview.card(c,"final-"+id);c.upgrade();check(c.makeStatEquivalentCopy().upgraded,id+" upgrade copies correctly");CardArtReview.card(c,"final-"+id+"-plus");
            }
            check(DarkRevelationAction.eligible(new MadnessOverwhelming())&&DarkRevelationAction.eligible(new MentalDecay())&&!DarkRevelationAction.eligible(new MaladyOfTheMind()),"Tutor includes new applications but excludes multiplication");
            play(new VoidSurge(),m);
        });
        q.add(()->{check(m.currentHealth==9994&&r.state.amount()==50&&EnergyPanel.totalCount==2,"Void Surge is a one-energy Strike with fifty total Insanity");reset();play(up(new VoidSurge()),m);});
        q.add(()->{check(m.currentHealth==9991&&r.state.amount()==60,"Void Surge upgrade deals nine and generates sixty");reset();play(new EventHorizon(),null);});
        q.add(()->{check(stat(HastePower.POWER_ID)==0&&CombatPowerPlays.count()==1,"Event Horizon does not trigger itself; actual Power play counted");play(up(new EventHorizon()),null);});
        q.add(()->{check(stat(HastePower.POWER_ID)==2&&stat(EventHorizonPower.POWER_ID)==5,"Existing Horizon triggers on another copy; coefficients stack");
            MadnessOverwhelming c=new MadnessOverwhelming();c.applyPowers();check(c.magicNumber==15,"Madness card displays total from two Powers");play(c,m);});
        q.add(()->{check(MadnessPower.stacks(m)==15,"Base Madness applies its displayed total");play(up(new MadnessOverwhelming()),m);});
        q.add(()->{check(MadnessPower.stacks(m)==39,"Upgraded Madness adds twenty-four after two Powers");
            selected=new DarkReinforcement();p.drawPile.addToTop(selected);play(up(new Foresight()),null);});
        q.add(()->{check(CombatPowerPlays.count()==3&&stat(HastePower.POWER_ID)==7,"Free Foresight Power counts and triggers combined Horizon");reset();
            p.masterDeck.addToTop(new Xalatath());p.preBattlePrep();});
        q.add(()->{check(CombatPowerPlays.count()==0,"Automatic Legendary equipment is not a Power play");p.masterDeck.group.removeIf(c->c instanceof Xalatath);reset();
            play(new MadnessOverwhelming(),m);});
        q.add(()->{check(MadnessPower.stacks(m)==5,"Madness works before any Powers");m.powers.add(new ArtifactPower(m,1));play(up(new MaladyOfTheMind()),m);});
        q.add(()->{check(MadnessPower.stacks(m)==5&&!m.hasPower(ArtifactPower.POWER_ID),"Artifact blocks multiplication without removing existing Madness");play(new MaladyOfTheMind(),m);});
        q.add(()->{check(MadnessPower.stacks(m)==10,"Base Malady doubles stacks");play(up(new MaladyOfTheMind()),m);});
        q.add(()->{check(MadnessPower.stacks(m)==30&&p.exhaustPile.group.stream().filter(c->c instanceof MaladyOfTheMind).count()==3,"Upgrade triples and all Malady copies exhaust");reset();play(new MaladyOfTheMind(),m);});
        q.add(()->{check(MadnessPower.stacks(m)==0,"Malady never creates absent Madness");reset();dots(5,5,20);play(new Singularity(),m);});
        q.add(()->{check(m.currentHealth==9894&&ShadowDotPower.count(m)==0&&r.state.amount()==20,"Singularity consumes thirty stacks for 106 damage in two hits");reset();dots(5,5,20);p.powers.add(new StrengthPower(p,4));p.powers.add(new MasteryPower(p,2));play(up(new Singularity()),m);});
        q.add(()->{check(m.currentHealth==9764,"Singularity upgrade adds Strength once and Mastery per consumed stack");reset();dots(5,5,20);p.powers.add(new StrengthPower(p,4));p.powers.add(new MasteryPower(p,2));p.powers.add(new WeakPower(p,1,false));m.powers.add(new VulnerablePower(m,1,false));r.enterImmediately();});
        q.add(()->play(new Singularity(),m));
        q.add(()->{check(m.currentHealth==9714,"Weak, Vulnerable, and Voidform affect both Singularity hits with independent rounding");reset();dots(0,0,10);p.powers.add(new MasteryPower(p,-9));play(new Singularity(),m);});
        q.add(()->{check(m.currentHealth==9984&&MadnessPower.stacks(m)==0,"Negative Mastery floors bonus at zero while still consuming stacks");reset();play(new Singularity(),m);});
        q.add(()->{check(m.currentHealth==9984&&r.state.amount()==10,"No DoTs means only the initial Singularity hit");reset();play(up(new TwinSuns()),m);});
        q.add(()->{check(stat(HastePower.POWER_ID)==0,"Upgraded Twin Suns has no unconditional Haste floor");m.powers.add(new StrengthPower(m,3));play(new TwinSuns(),m);});
        q.add(()->{check(stat(HastePower.POWER_ID)==3&&m.getPower("Strength").amount==3&&p.exhaustPile.isEmpty(),"Twin Suns grants positive Strength as Haste without stealing or exhausting");play(up(new TwinSuns()),m);});
        q.add(()->{check(stat(HastePower.POWER_ID)==7,"Repeatable upgraded Twin Suns adds Strength plus one");reset();for(int i=0;i<5;i++)p.drawPile.addToTop(new MindSpike());play(new VoidEntropy(),null);});
        q.add(()->{check(r.state.active()&&r.state.amount()==100&&p.hand.size()==2&&p.hand.group.stream().noneMatch(c->c instanceof EnterVoidform),"Entropy enters immediately and draws exactly two without token");nextTurn();});
        q.add(()->{check(r.state.recovering()&&!r.state.active(),"Entropy's form ends into Recharge");for(int i=0;i<5;i++)p.drawPile.addToTop(new MindSpike());p.hand.clear();play(up(new VoidEntropy()),null);});
        q.add(()->{check(r.state.active()&&!r.state.recovering()&&p.hand.size()==3,"Entropy bypasses Recharge and upgrade draws three");nextTurn();});
        q.add(()->{check(r.state.recovering(),"Bypassed form causes fresh Recharge afterward");reset();r.gainInsanity(100);});
        q.add(()->{token=p.hand.group.stream().filter(c->c instanceof EnterVoidform).findFirst().orElse(null);check(token!=null,"Ready-charge token exists before Entropy");p.relics.add(new IdolOfYoggSaron());p.relics.add(new ImprovedVoidform());play(new VoidEntropy(),null);});
        q.add(()->{check(r.state.active()&&!p.hand.contains(token)&&!token.canUse(p,null)&&EnergyPanel.totalCount==4,"Entropy consumes ready token and triggers Yogg once");play(new MindSpike(),m);});
        q.add(()->{check(r.state.attacksPlayed()==1,"Improved activation tracks attacks");play(new VoidEntropy(),null);});
        q.add(()->{check(r.state.attacksPlayed()==1&&EnergyPanel.totalCount==1,"Active Entropy neither resets activation nor retriggers entry energy");reset();play(up(new DarkReinforcement()),null);});
        q.add(()->{play(new DarkReinforcement(),null);});
        q.add(()->{DarkReinforcementPower power=(DarkReinforcementPower)p.getPower(DarkReinforcementPower.POWER_ID);check(power.amount==2&&power.mastery==1,"Mixed Reinforcement copies stack benefits independently");
            p.hand.clear();p.drawPile.clear();p.discardPile.clear();p.drawPile.addToTop(new MindSpike());p.drawPile.addToTop(new MindSpike());p.drawPile.addToTop(new Regret());EnergyPanel.totalCount=0;add(new DrawCardAction(p,1));});
        q.add(()->{check(p.hand.size()==3&&EnergyPanel.totalCount==2&&MasteryPower.current()==1,"Actual Curse draw grants stacked energy/draw and upgraded-only Mastery");
            p.hand.clear();EnergyPanel.totalCount=0;play(new MentalDecay(),null);});
        q.add(()->{check(MadnessPower.stacks(m)==10&&MadnessPower.stacks(n)==10,"Mental Decay applies Madness to every enemy");
            curse=p.hand.group.stream().filter(c->c instanceof MindDecay).findFirst().orElse(null);
            check(curse!=null&&curse.selfRetain&&curse.makeStatEquivalentCopy().selfRetain,"Generated Mind Decay retains through copying");
            check(EnergyPanel.totalCount==2&&MasteryPower.current()==1,"Creating Curse does not trigger Reinforcement");
            hp=p.currentHealth;for(int i=0;i<12;i++)p.drawPile.addToTop(new MindSpike());nextTurn();});
        q.add(()->{check(p.hand.contains(curse)&&p.currentHealth==hp-2,"Mind Decay remains in hand and deals its end-turn damage");
            reset();m.powers.add(new ArtifactPower(m,1));play(up(new MentalDecay()),null);});
        q.add(()->{check(MadnessPower.stacks(m)==0&&MadnessPower.stacks(n)==15&&!m.hasPower(ArtifactPower.POWER_ID),"Upgraded AoE respects each enemy's Artifact independently");
            reset();master=new VoidRay();other=new VoidRay();p.masterDeck.addToTop(master);p.masterDeck.addToTop(other);ray=(VoidRay)master.makeSameInstanceOf();curse=new Necronomicurse();p.hand.addToTop(curse);p.relics.add(new VoidVengeance());selected=curse;play(ray,m);});
        q.add(()->{check(m.currentHealth==9985&&master.misc==5&&ray.misc==5&&other.misc==0,"Void Ray deals original damage and grows only its matching permanent copy");
            check(p.exhaustPile.contains(ray)&&p.exhaustPile.contains(curse)&&stat(HastePower.POWER_ID)==2&&p.hand.group.stream().anyMatch(c->c instanceof Necronomicurse),"Ray exhausts itself and Curse, granting Vengeance Haste and normal Curse return");
            AbstractCard restored=CardLibrary.getCopy(VoidRay.ID,1,master.misc);check(restored.baseDamage==28&&restored.misc==5,"Save restoration preserves growth and upgrade base damage");
            master.upgrade();check(master.baseDamage==28,"Upgrading a grown Ray preserves earned growth");
            selected=null;ray=(VoidRay)master.makeSameInstanceOf();play(ray,m);});
        q.add(()->{check(master.misc==13&&ray.baseDamage==36,"Upgraded Ray earns eight additional permanent damage");
            int old=master.misc;ray=(VoidRay)master.makeStatEquivalentCopy();check(!ray.uuid.equals(master.uuid),"Temporary copy has independent identity");play(ray,m);});
        q.add(()->{check(master.misc==13&&ray.misc==21,"Temporary Ray grows itself without leaking growth to permanent original");
            p.hand.clear();ray=new VoidRay();play(ray,m);});
        q.add(()->{check(ray.misc==0,"Ray without a Curse still attacks but never grows");reset();
            master=new VoidRay();p.masterDeck.addToTop(master);ray=(VoidRay)master.makeSameInstanceOf();p.hand.addToTop(new Regret());
            n.currentHealth=0;n.isDead=true;n.isDying=true;m.currentHealth=1;ending=true;play(ray,m);});
    }
}
