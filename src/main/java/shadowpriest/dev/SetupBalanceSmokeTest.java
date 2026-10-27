package shadowpriest.dev;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.actions.DarkRevelationAction;
import shadowpriest.mechanics.DotTiming;
import shadowpriest.relics.VoidformRelic;
import java.util.*;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused real-engine tests for the accepted setup/defense/Insanity redesign. */
public final class SetupBalanceSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m,n;private static VoidformRelic r;
    private static AbstractCard selected;private static EnterVoidform token;private static int before;
    private static void add(AbstractGameAction a){AbstractDungeon.actionManager.addToBottom(a);}
    private static <T extends AbstractCard>T up(T c){c.upgrade();return c;}
    private static int stacks(AbstractMonster target,String id){return target.hasPower(id)?target.getPower(id).amount:0;}
    private static void reset(){
        p.powers.clear();p.currentBlock=0;p.currentHealth=p.maxHealth=500;
        p.hand.clear();p.drawPile.clear();p.discardPile.clear();p.exhaustPile.clear();p.limbo.clear();
        p.relics.removeIf(relic->!relic.relicId.equals(VoidformRelic.ID));r.reset();
        for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters){enemy.powers.clear();enemy.currentBlock=0;enemy.currentHealth=enemy.maxHealth=10000;}
        EnergyPanel.totalCount=3;
    }
    private static void play(AbstractCard c,AbstractMonster target,int energy){
        p.hand.removeCard(c);p.drawPile.removeCard(c);p.discardPile.removeCard(c);p.hand.addToTop(c);
        EnergyPanel.totalCount=energy;c.energyOnUse=energy;
        check(c.canUse(p,target),"Playable: "+c.name);
        p.useCard(c,target,energy);
    }
    private static void dots(AbstractMonster enemy,int duration){
        enemy.powers.add(new ShadowDotPower(enemy,ShadowDotPower.TOUCH,duration));
        enemy.powers.add(new ShadowDotPower(enemy,ShadowDotPower.PAIN,duration));
        enemy.powers.add(new MadnessPower(enemy,duration));
    }
    public static void handleSelection(){
        if(!(AbstractDungeon.actionManager!=null && AbstractDungeon.actionManager.currentAction instanceof DarkRevelationAction)
            || AbstractDungeon.screen!=AbstractDungeon.CurrentScreen.GRID || selected==null)return;
        check(AbstractDungeon.player.drawPile.contains(selected)&&DarkRevelationAction.eligible(selected),"Tutor presents an eligible draw-pile selection");
        AbstractDungeon.gridSelectScreen.selectedCards.clear();AbstractDungeon.gridSelectScreen.selectedCards.add(selected);
        AbstractDungeon.closeCurrentScreen();
    }
    public static void append(Queue<Runnable> q){
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);r=VoidformRelic.current();
            add(new SpawnMonsterAction(new Cultist(300f,0),false));});
        q.add(()->{n=AbstractDungeon.getMonsters().monsters.get(1);reset();
            int[][] expected={{11,13,4},{7,14,5},{4,5,5}};CardGroup[] pools={AbstractDungeon.commonCardPool,AbstractDungeon.uncommonCardPool,AbstractDungeon.rareCardPool};
            for(int i=0;i<pools.length;i++){int[] found=new int[3];for(AbstractCard c:pools[i].group)if(c.color==shadowpriest.character.ShadowPriest.Meta.CARD_COLOR){if(c.type==AbstractCard.CardType.ATTACK)found[0]++;else if(c.type==AbstractCard.CardType.SKILL)found[1]++;else if(c.type==AbstractCard.CardType.POWER)found[2]++;}
                check(Arrays.equals(found,expected[i]),"Initialized reward pool distribution "+Arrays.toString(found));}
            String[] names={"ShadowWordTorment","MindTrauma","VoidTendrils","EncroachingShadows","FocusedWill","ShadowWeaving","DarkRevelation","Atonement","SearingNightmare","VoidShield"};
            for(String name:names){AbstractCard c=CardLibrary.getCard("shadowpriest:"+name).makeCopy();
                check(c.portrait!=null&&!c.rawDescription.isEmpty(),name+" registered with art/text");CardArtReview.card(c,"setup-"+name);c.upgrade();
                check(c.makeStatEquivalentCopy().upgraded,name+" upgrade survives copy");CardArtReview.card(c,"setup-"+name+"-plus");}
            play(new MindSpike(),m,1);});
        q.add(()->{check(r.state.amount()==20,"Undotted Mind Spike earns ten per hit plus ten explicit");reset();dots(m,4);play(new MindFlay(),m,1);});
        q.add(()->{check(r.state.amount()==30,"Three Flay hits earn thirty regardless of three DoTs");reset();m.currentBlock=100;play(up(new MindFlay()),m,1);});
        q.add(()->{check(r.state.amount()==40&&m.currentHealth==10000,"Four fully blocked Flay hits earn forty without DoTs");reset();play(new MindSear(),null,3);});
        q.add(()->{check(r.state.amount()==60&&m.currentHealth==9982&&n.currentHealth==9982,"Three Mind Sear waves hit two targets for sixty Insanity and eighteen damage each");reset();
            p.relics.add(new ChemicalX());p.powers.add(new HastePower(p,1));play(new MindSear(),null,0);});
        q.add(()->{check(r.state.amount()==60&&EnergyPanel.totalCount==0,"Zero-energy Haste/Chemical X attacks generate per-hit Insanity");reset();play(up(new ShadowWordDeath()),m,0);});
        q.add(()->{check(r.state.amount()==30&&p.currentHealth==494,"Death plus earns twenty from attacks and ten from its two HP-loss events");reset();m.currentHealth=5;play(up(new ShadowWordDeath()),m,0);});
        q.add(()->{check(r.state.amount()==10,"No second-hit Insanity after target dies");
            // Replace dead fixture rather than pretending it is alive.
            AbstractDungeon.getMonsters().monsters.remove(m);m=n;add(new SpawnMonsterAction(new Cultist(-100f,0),false));});
        q.add(()->{n=AbstractDungeon.getMonsters().monsters.stream().filter(enemy->enemy!=m).findFirst().get();reset();dots(m,1);DotTiming.tick();});
        q.add(()->{check(r.state.amount()==3&&ShadowDotPower.count(m)==0,"Three natural final ticks earn one each and then expire");reset();dots(m,2);p.powers.add(new MasteryPower(p,-99));DotTiming.tick();});
        q.add(()->{check(r.state.amount()==3&&m.currentHealth==10000,"Zero-damage DoT ticks still count once");reset();dots(m,3);dots(n,3);p.powers.add(new PsychicLinkPower(p,1));play(new MindSpike(),m,1);});
        q.add(()->{check(r.state.amount()==22,"Psychic Link adds one per triggered Touch tick, not Attack-hit Insanity (actual "+r.state.amount()+")");reset();play(new Halo(),null,1);});
        q.add(()->{for(AbstractPower power:new ArrayList<>(p.powers))if(power instanceof HaloPower)power.atEndOfTurn(true);});
        q.add(()->{check(r.state.amount()==0,"Halo is not a DoT and grants no tick Insanity");reset();r.gainInsanity(99);dots(m,1);DotTiming.tick();});
        q.add(()->{check(r.state.ready()&&r.state.amount()==100,"Tick threshold crossing caps at one ready charge");token=new EnterVoidform(r.state.charge());p.drawPile.addToTop(new PowerWordShield());play(token,null,0);});
        q.add(()->{check(r.state.active()&&p.hand.group.stream().anyMatch(c->c instanceof PowerWordShield),"Valid Enter Voidform draws one after entry");before=p.hand.size();new EnterVoidform(token.charge).use(p,null);});
        q.add(()->{check(p.hand.size()==before,"Stale activation cannot draw");r.onPlayerEndTurn();});
        q.add(()->{r.atTurnStart();dots(m,2);DotTiming.tick();play(new MindSpike(),n,1);});
        q.add(()->{check(r.state.amount()==0&&r.state.recovering(),"Recharge blocks tick, hit, and explicit gains");reset();play(new Atonement(),m,1);});
        q.add(()->{check(stacks(m,AtonementPower.POWER_ID)==2&&p.currentBlock==0,"Atonement applies after its initial hit without self-trigger");p.powers.add(new DexterityPower(p,10));play(new MindFlay(),m,1);});
        q.add(()->{check(p.currentBlock==6,"Atonement grants fixed two per hit without Dexterity");p.currentBlock=0;play(new Atonement(),m,1);});
        q.add(()->{check(p.currentBlock==2&&stacks(m,AtonementPower.POWER_ID)==2&&!p.exhaustPile.group.stream().anyMatch(c->c instanceof Atonement),"Repeat Atonement triggers existing debuff but neither stacks nor exhausts");play(up(new Atonement()),m,1);});
        q.add(()->{check(stacks(m,AtonementPower.POWER_ID)==3,"Upgraded Atonement replaces two with three");m.powers.add(new ArtifactPower(m,1));play(new Atonement(),m,1);});
        q.add(()->{check(stacks(m,AtonementPower.POWER_ID)==3,"Weaker application cannot downgrade Atonement");
            check(stacks(m,"Artifact")==1,"Redundant Atonement is a regular attack and does not consume Artifact");p.currentBlock=0;dots(m,2);DotTiming.tick();});
        q.add(()->{check(p.currentBlock==0,"Atonement ignores DoT ticks and is not a fourth DoT");reset();m.powers.add(new ArtifactPower(m,1));play(new Atonement(),m,1);});
        q.add(()->{check(!m.hasPower(AtonementPower.POWER_ID)&&!m.hasPower("Artifact"),"Artifact blocks initial Atonement");reset();play(new Fade(),null,0);});
        q.add(()->{p.damage(new DamageInfo(m,1,DamageInfo.DamageType.NORMAL));check(p.currentHealth==500&&r.state.amount()==0,"Zero-cost Fade prevents one HP and no damage-taken Insanity");reset();
            VoidShield shield=new VoidShield();check(shield.totalBlock()==5,"Void Shield has a five Block floor");r.gainInsanity(50);check(shield.totalBlock()==10,"Void Shield adds five at fifty Insanity");
            p.powers.add(new DexterityPower(p,4));check(shield.totalBlock()==14,"Void Shield applies Dexterity once");shield.upgrade();check(shield.totalBlock()==22,"Upgraded Shield uses eight plus one per five plus Dexterity");
            r.gainInsanity(50);r.activate(r.state.charge());});
        q.add(()->{VoidShield shield=up(new VoidShield());check(shield.totalBlock()==32,"Active Voidform supplies the hundred-Insanity Shield value");r.onPlayerEndTurn();});
        q.add(()->{play(up(new VoidShield()),null,1);});
        q.add(()->{check(p.currentBlock==12&&r.state.amount()==0,"Recharge Shield retains base Block and generates no Insanity");reset();
            m.setMove((byte)0,AbstractMonster.Intent.ATTACK,6);m.createIntent();n.setMove((byte)0,AbstractMonster.Intent.BUFF);n.createIntent();p.powers.add(new DexterityPower(p,3));play(new VoidTendrils(),null,1);});
        q.add(()->{check(p.currentBlock==14&&stacks(m,ShadowDotPower.PAIN)==2&&!n.hasPower(ShadowDotPower.PAIN),"Tendrils applies Dexterity per enemy and Pain only to attackers");reset();play(up(new FocusedWill()),null,1);});
        q.add(()->{check(p.currentBlock==10&&r.state.amount()==15,"Focused Will grants upgraded Block and immediate Insanity");reset();play(new ShadowWeaving(),null,1);});
        q.add(()->{p.powers.add(new DexterityPower(p,4));dots(m,1);dots(n,1);DotTiming.tick();});
        q.add(()->{check(p.currentBlock==16&&ShadowDotPower.count(m)==0&&ShadowDotPower.count(n)==0,"Weaving counts six final-tick DoTs and applies Dexterity once");p.currentBlock=0;DotTiming.tick();});
        q.add(()->{check(p.currentBlock==0,"Weaving grants no Dexterity-only Block with no DoTs");reset();play(up(new ShadowWeaving()),null,1);});
        q.add(()->{play(new ShadowWeaving(),null,1);});
        q.add(()->{dots(m,2);p.powers.add(new DexterityPower(p,4));DotTiming.tick();});
        q.add(()->{check(p.currentBlock==19,"Stacked Weaving combines coefficients into one grant");reset();play(new PowerWordBarrier(),null,3);});
        q.add(()->{p.powers.add(new ShadowWeavingPower(p,3));dots(m,2);DotTiming.tick();play(new VoidTendrils(),null,1);});
        q.add(()->{check(p.currentBlock==47&&BarrierPower.remaining(p)==30,"Barrier allows ordinary Weaving and Tendrils gains without refilling its reserve");reset();play(new SearingNightmare(),null,2);});
        q.add(()->{check(p.hasPower(SearingNightmarePower.POWER_ID)&&p.hand.isEmpty(),"Searing Nightmare is a played persistent Power");m.currentBlock=100;play(new MindSear(),null,3);});
        q.add(()->{check(MadnessPower.stacks(m)==3&&MadnessPower.stacks(n)==3,"Nightmare applies Madness per hit to every enemy including blocked hits");
            before=r.state.amount();DotTiming.tick();});
        q.add(()->{check(MadnessPower.stacks(m)==2&&MadnessPower.stacks(n)==2&&r.state.amount()==before+2,"Madness ticks generate one each without recursively applying Madness");reset();
            p.powers.add(new SearingNightmarePower(p,1));m.powers.add(new ArtifactPower(m,1));play(new MindFlay(),m,1);});
        q.add(()->{check(MadnessPower.stacks(m)==2,"Artifact blocks first Nightmare application; two later hits apply Madness (actual "+MadnessPower.stacks(m)+")");reset();play(new BloodMagic(),m,2);});
        q.add(()->{check(m.currentHealth==9986&&stacks(m,ShadowDotPower.TOUCH)==2&&stacks(m,ShadowDotPower.PAIN)==2&&p.currentHealth==500,"Blood Magic deals fourteen and applies both DoTs without self-damage");reset();play(up(new BloodMagic()),m,2);});
        q.add(()->{check(m.currentHealth==9982&&stacks(m,ShadowDotPower.TOUCH)==3&&stacks(m,ShadowDotPower.PAIN)==3,"Upgraded Blood Magic deals eighteen and applies three of each");reset();play(up(new ShadowWordTorment()),m,1);});
        q.add(()->{check(m.currentHealth==9991&&stacks(m,ShadowDotPower.PAIN)==5,"Torment plus supplies independent common Pain");reset();m.currentBlock=100;play(up(new MindTrauma()),m,1);});
        q.add(()->{check(MadnessPower.stacks(m)==6&&r.state.amount()==20,"Trauma applies Madness through Block plus ten explicit Insanity");reset();play(up(new EncroachingShadows()),m,1);});
        q.add(()->{check(p.currentBlock==8&&MadnessPower.stacks(m)==5,"Encroaching Shadows defends and independently applies Madness");reset();play(up(new UnfurlingDarkness()),m,0);});
        q.add(()->{check(r.state.amount()==20&&stacks(m,ShadowDotPower.TOUCH)==3,"Free upgraded Unfurling supplies Touch and twenty total Insanity");reset();play(new MindBlast(),m,1);});
        q.add(()->{check(r.state.amount()==20&&m.currentHealth==9990,"Mind Blast has explicit ten alongside its hit");reset();play(up(new Bulwark()),null,2);});
        q.add(()->{check(p.currentBlock==16&&p.hasPower("Blur"),"Bulwark plus grants sixteen and one Blur");reset();play(new MindSoothe(),m,0);});
        q.add(()->{check(stacks(m,MindSoothePower.POWER_ID)==1,"Mind Soothe works before any DoT setup");reset();play(new ReapingMadness(),null,1);});
        q.add(()->{check(r.state.amount()==20,"Reaping immediately grants twenty");p.getPower(ReapingMadnessPower.POWER_ID).atEndOfTurn(true);});
        q.add(()->{check(r.state.amount()==30,"Reaping preserves its ongoing ten");reset();p.powers.add(new DexterityPower(p,2));play(new ProtectedChannel(),null,2);});
        q.add(()->{check(p.currentBlock==8,"Protected Channel grants six upfront plus Dexterity once");play(new MindSear(),null,2);});
        q.add(()->{check(p.currentBlock==18,"Protected Channel still grants per-X Block with per-instance Dexterity");reset();
            selected=new BloodMagic();p.drawPile.addToTop(selected);p.drawPile.addToTop(new MindFlay());p.drawPile.addToTop(new SearingNightmare());
            check(!DarkRevelationAction.eligible(new MindFlay())&&!DarkRevelationAction.eligible(new SearingNightmare())&&DarkRevelationAction.eligible(new VoidBlast()),"Tutor excludes maintenance and enabling Powers but allows conditional spread");play(new DarkRevelation(),null,1);});
        q.add(()->{check(p.hand.contains(selected)&&selected.cost==2&&selected.costForTurn==0&&p.exhaustPile.group.stream().anyMatch(c->c instanceof DarkRevelation),"Tutor chooses the actual card, discounts only this turn, and Exhausts");
            selected.resetAttributes();check(selected.costForTurn==2,"Tutor discount expires on normal reset");reset();p.discardPile.addToTop(new BloodMagic());play(up(new DarkRevelation()),null,0);});
        q.add(()->{check(p.hand.isEmpty()&&p.discardPile.size()==1,"Empty tutor does not search discard or reshuffle");reset();selected=new VampiricTouch();p.drawPile.addToTop(selected);
            for(int i=0;i<BaseMod.MAX_HAND_SIZE;i++)p.hand.addToTop(new PowerWordShield());add(new DarkRevelationAction());});
        q.add(()->check(p.hand.size()==BaseMod.MAX_HAND_SIZE&&p.discardPile.contains(selected),"Tutor respects full hand with normal discard fallback"));
    }
}
