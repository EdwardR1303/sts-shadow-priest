package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.details.*;
import shadowpriest.powers.*;
import shadowpriest.relics.VoidformRelic;
import java.util.*;
import static shadowpriest.details.CombatReport.*;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused observation tests; no complete-run simulation. */
public final class DetailsSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m;
    private static long before;private static int turns,events;
    private static String runId;
    private static long awaitingVictory;
    public static boolean handleCompletion(){
        if(awaitingVictory==0)return false;
        if(DetailsTracker.active()){
            if(System.currentTimeMillis()-awaitingVictory>5000)throw new AssertionError("Victory report did not close");
            return true;
        }
        check(c().outcome.equals("Victory"),"Victory closes and saves the report after the final hit");
        DetailsStore.flush();CombatSmokeTest.finish(null);return true;
    }
    private static CombatReport c(){return DetailsTracker.current();}
    private static long total(Metric metric){return c().total(metric,0);}
    private static long source(Metric metric,String name){
        return c().rows(metric,0).stream().filter(row->row.source.name.equals(name)).mapToLong(row->row.amount).sum();
    }
    private static void play(AbstractCard card){
        p.hand.addToTop(card);EnergyPanel.totalCount=3;card.energyOnUse=3;
        card.applyPowers();card.calculateCardDamage(m);p.useCard(card,m,3);
    }
    private static void hit(int amount){p.damage(new DamageInfo(m,amount,DamageInfo.DamageType.NORMAL));}
    public static void append(Queue<Runnable> q){
        q.add(()->{
            p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);
            check(DetailsTracker.active() && c().turns==1,"Details begins before combat effects and first player turn is one");
            DetailsTracker.begin();DetailsTracker.startTurn();
            p.powers.clear();p.currentBlock=0;m.powers.clear();m.currentBlock=0;
            play(new MindSpike());
        });
        q.add(()->{check(source(Metric.DAMAGE_DEALT,"Mind Spike")==6,"Direct Attack damage attributed to its card");play(new MindFlay());});
        q.add(()->{
            check(source(Metric.DAMAGE_DEALT,"Mind Flay")==6,"Multi-hit damage is summed under one card");
            check(c().rows(Metric.DAMAGE_DEALT,0).stream().filter(row->row.source.name.equals("Mind Flay")).findFirst().get().events==3,"Multi-hit records three distinct damage events");
            m.currentBlock=100;before=total(Metric.DAMAGE_DEALT);play(new MindSpike());
        });
        q.add(()->{check(total(Metric.DAMAGE_DEALT)==before,"Fully blocked Attack adds no HP damage");
            m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,3));
            m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,3));m.powers.add(new MadnessPower(m,6));
            ((ShadowDotPower)m.getPower(ShadowDotPower.TOUCH)).naturalTick();
            ((ShadowDotPower)m.getPower(ShadowDotPower.PAIN)).naturalTick();
            ((MadnessPower)m.getPower(MadnessPower.POWER_ID)).naturalTick();
        });
        q.add(()->{
            check(source(Metric.DAMAGE_DEALT,"Vampiric Touch")==5,"Touch damage has its own source and bypasses Block");
            check(source(Metric.DAMAGE_DEALT,"Shadow Word: Pain")==3,"Pain is not mislabeled as Madness");
            check(source(Metric.DAMAGE_DEALT,"Madness")==3,"Madness ticks have their own source");
            p.powers.add(new PsychicLinkPower(p,1));m.currentBlock=0;play(new MindSpike());
        });
        q.add(()->{
            check(source(Metric.DAMAGE_DEALT,"Vampiric Touch (Psychic Link)")==5,"Psychic Link distinguishes triggered Touch from natural ticks");
            p.powers.clear();HaloPower halo=new HaloPower(p,7);halo.atEndOfTurn(true);
        });
        q.add(()->{check(source(Metric.DAMAGE_DEALT,"Halo")==7,"Queued nested Halo pulse attributed to Halo");play(new ShadowWordDeath());});
        q.add(()->{
            check(source(Metric.DAMAGE_TAKEN,"Shadow Word: Death")==3,"Shadow Word Death backlash attributed to the card");
            new ShadowMendDebtPower(p,4).atStartOfTurn();
        });
        q.add(()->{
            check(source(Metric.DAMAGE_TAKEN,"Shadow Mend Debt")==4,"Ongoing self-damage attributed to its debuff");
            p.currentBlock=5;before=total(Metric.DAMAGE_TAKEN);hit(8);
            check(total(Metric.DAMAGE_TAKEN)==before+3,"Incoming damage counts HP loss after Block");
            check(source(Metric.DAMAGE_TAKEN,m.name)==3,"Incoming hits attributed to the enemy");
            p.powers.add(new IntangiblePlayerPower(p,1));before=total(Metric.DAMAGE_TAKEN);hit(100);
            check(total(Metric.DAMAGE_TAKEN)==before+1,"Incoming damage respects Intangible");
            p.powers.clear();p.currentHealth=10;p.powers.add(new GuardianAngelPower(p));before=total(Metric.DAMAGE_TAKEN);hit(100);
            check(p.currentHealth==1 && total(Metric.DAMAGE_TAKEN)==before+9,"Guardian Angel prevented HP loss is excluded");
            p.powers.clear();p.currentHealth=10;p.relics.add(new com.megacrit.cardcrawl.relics.LizardTail());
            before=total(Metric.DAMAGE_TAKEN);hit(100);
            check(p.currentHealth>10&&total(Metric.DAMAGE_TAKEN)==before+10,"Lizard Tail revival does not hide the preceding lethal HP loss");
            p.relics.removeIf(r->r.relicId.equals("Lizard Tail"));
            p.currentHealth=500;p.powers.clear();p.currentBlock=0;p.powers.add(new DexterityPower(p,4));play(new PowerWordShield());
        });
        q.add(()->{
            check(source(Metric.BLOCK,"Power Word: Shield")==9,"Block counts actual Dexterity-adjusted gain");
            p.powers.add(new FrailPower(p,1,false));play(new PowerWordShield());
        });
        q.add(()->{
            check(source(Metric.BLOCK,"Power Word: Shield")==15,"Block counts actual Frail-adjusted gain");
            p.powers.clear();p.currentBlock=997;play(new PowerWordShield());
        });
        q.add(()->{
            check(source(Metric.BLOCK,"Power Word: Shield")==17,"Block cap records only two Block actually added");
            p.currentBlock=30;p.powers.add(new BarrierPower(p,30));before=total(Metric.BLOCK);play(new PowerWordShield());
        });
        q.add(()->{
            check(total(Metric.BLOCK)==before+5 && p.currentBlock==35 && BarrierPower.remaining(p)==30,"Barrier permits ordinary Block and Details counts only the new gain");
            p.powers.clear();p.currentBlock=0;m.powers.clear();m.powers.add(new AtonementPower(m,2));play(new MindSpike());
        });
        q.add(()->{
            check(source(Metric.BLOCK,"Atonement")==2,"Triggered Atonement Block is credited to the debuff, not the Attack");
            m.powers.clear();
            int ready=c().readyTurns.size(), entries=c().activationTurns.size();
            VoidformRelic r=VoidformRelic.current();r.reset();r.gainInsanity(100);r.activate(r.state.charge());
            check(c().readyTurns.size()==ready+1&&c().activationTurns.size()==entries+1,"Voidform readiness and entry are counted once");
        });
        q.add(()->{
            p.powers.clear();VoidformRelic.current().reset();turns=c().turns;
            p.endTurnQueued=true; // Exercise the real enemy turn and start-of-turn lifecycle.
        });
        q.add(()->{
            check(c().turns==turns+1,"A real turn transition increments Details exactly once");
            check(c().total(Metric.DAMAGE_DEALT,c().turns)==0,"Empty turn remains present with zero damage");
            play(new MindSpike());
        });
        q.add(()->{
            check(c().total(Metric.DAMAGE_DEALT,c().turns)==6,"Turn filter isolates damage in the new turn");
            long previous=total(Metric.DAMAGE_DEALT);m.currentHealth=2;
            // Keep the battle alive through a second enemy, so the harness can inspect the lethal result.
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new com.megacrit.cardcrawl.monsters.exordium.Cultist(300,0),false));
            before=previous;
        });
        q.add(()->{m.currentHealth=100;play(new MindSear());});
        q.add(()->{
            check(source(Metric.DAMAGE_DEALT,"Mind Sear")==36,"X-cost area damage aggregates six hits across two enemies");
            before=total(Metric.DAMAGE_DEALT);m.currentHealth=2;play(new MindSpike());
        });
        q.add(()->{
            check(total(Metric.DAMAGE_DEALT)==before+2,"Killing hit counts remaining HP, not overkill");
            m=AbstractDungeon.getMonsters().monsters.get(1);
            DetailsTracker.save();DetailsStore.flush();runId=DetailsTracker.run().id;
            Run saved=DetailsStore.load(runId);
            check(saved!=null&&saved.combats.get(saved.combats.size()-1).total(Metric.DAMAGE_DEALT,0)==total(Metric.DAMAGE_DEALT),"Disk report round-trip preserves real combat totals");
            check(DetailsStore.load("../invalid")==null,"Invalid saved report ID cannot escape Details folder");
            events=c().events.size();DetailsScreen.INSTANCE.show();
            check(AbstractDungeon.screen==DetailsScreen.DETAILS&&AbstractDungeon.isScreenUp,"Details opens a modal game screen");
            CardArtReview.requestCombat("details-damage");
        });
        q.add(()->{});q.add(()->{});
        q.add(()->{
            check(CardArtReview.capturedCombat("details-damage"),"Damage report screenshot captured");
            DetailsScreen.INSTANCE.selectMetric(Metric.BLOCK);DetailsScreen.INSTANCE.selectTurn(1);
            CardArtReview.requestCombat("details-block");
        });
        q.add(()->{});q.add(()->{});
        q.add(()->{
            check(CardArtReview.capturedCombat("details-block"),"Block report screenshot captured");
            check(c().events.size()==events,"Viewing reports does not change combat statistics");
            DetailsScreen.INSTANCE.dismiss();
            check(AbstractDungeon.screen==AbstractDungeon.CurrentScreen.NONE&&!AbstractDungeon.isScreenUp,"Closing Details restores combat input");
            DetailsScreen.INSTANCE.show();com.megacrit.cardcrawl.helpers.input.InputHelper.pressedEscape=true;
            basemod.ReflectionHacks.privateMethod(com.megacrit.cardcrawl.ui.panels.TopPanel.class,"updateSettingsButtonLogic").invoke(AbstractDungeon.topPanel);
        });
        q.add(()->{
            check(AbstractDungeon.screen==AbstractDungeon.CurrentScreen.NONE&&!AbstractDungeon.isScreenUp,"Escape closes Details without opening Settings");
            AbstractDungeon.dungeonMapScreen.open(false);DetailsScreen.INSTANCE.show();DetailsScreen.INSTANCE.dismiss();
            check(AbstractDungeon.screen==AbstractDungeon.CurrentScreen.MAP&&AbstractDungeon.isScreenUp,"Closing Details restores the map");
            AbstractDungeon.closeCurrentScreen();
            String saved=DetailsTracker.saveField().onSave();long total=total(Metric.DAMAGE_DEALT);
            DetailsTracker.resetRun();DetailsTracker.saveField().onLoad(saved);
            check(DetailsTracker.run().id.equals(runId)&&DetailsTracker.run().combats.get(0).total(Metric.DAMAGE_DEALT,0)==total,"Save-field reload restores run identity and history");
            DetailsTracker.begin();DetailsTracker.startTurn();
            before=total(Metric.DAMAGE_DEALT);int hp=m.currentHealth;
            m.damage(new DamageInfo(p,9999,DamageInfo.DamageType.NORMAL));
            check(total(Metric.DAMAGE_DEALT)==before+hp,"Final killing blow is recorded before victory callbacks");
            awaitingVictory=System.currentTimeMillis();
        });
    }
}
