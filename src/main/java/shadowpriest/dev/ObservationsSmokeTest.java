package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.details.*;
import shadowpriest.powers.*;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.details.CombatReport.*;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Short engine regression for the post-0.1.2 observations; no run simulation. */
public final class ObservationsSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m;private static MindDecay decay;
    private static long before;private static int hp;
    private static CombatReport report(){return DetailsTracker.current();}
    private static long total(Metric metric){return report().total(metric,0);}
    private static void hit(int damage){
        DamageAction action=new DamageAction(m,new DamageInfo(p,damage,DamageInfo.DamageType.NORMAL));
        DetailsTracker.tag(action,DetailsTracker.card(new MindSpike()));AbstractDungeon.actionManager.addToBottom(action);
    }
    private static void play(AbstractCard card){p.hand.addToTop(card);EnergyPanel.totalCount=3;card.applyPowers();card.calculateCardDamage(m);p.useCard(card,m,3);}
    public static void append(Queue<Runnable> q){
        q.add(()->{
            p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);
            p.powers.clear();p.loseBlock();p.currentHealth=p.maxHealth=500;m.powers.clear();m.currentHealth=m.maxHealth=1000;
            for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
            p.relics.removeIf(r->!r.relicId.equals(VoidformRelic.ID));VoidformRelic.current().reset();
            DetailsTracker.begin();DetailsTracker.startTurn();m.currentBlock=20;hit(10);
        });
        q.add(()->{
            check(total(Metric.DAMAGE_DEALT)==10&&report().total(Metric.DAMAGE_DEALT,0,false)==0,"Fully blocked damage is recorded and can be excluded");
            m.currentBlock=4;hit(10);
        });
        q.add(()->{
            Row row=report().rows(Metric.DAMAGE_DEALT,0).get(0);
            check(row.amount==20&&row.blocked==14&&row.events==2,"Partial Block is a separate segment in the same source row");
            before=total(Metric.DAMAGE_DEALT);m.powers.add(new BufferPower(m,1));hit(50);
        });
        q.add(()->{
            check(total(Metric.DAMAGE_DEALT)==before,"Buffer prevention is neither damage nor overkill");
            m.powers.clear();m.powers.add(new IntangiblePlayerPower(m,1));hit(50);
        });
        q.add(()->{
            check(total(Metric.DAMAGE_DEALT)==before+1,"Intangible only records the one resolved damage");
            m.powers.clear();m.powers.add(new InvinciblePower(m,3));before=total(Metric.DAMAGE_DEALT);hit(50);
        });
        q.add(()->{
            check(total(Metric.DAMAGE_DEALT)==before+3,"Heart-style damage cap does not become overkill");
            m.powers.clear();p.currentHealth=498;play(new FlashHeal());
        });
        q.add(()->{
            check(total(Metric.HEALING)==2&&p.currentHealth==500,"Healing records actual HP restored, excluding overheal");
            check(report().rows(Metric.HEALING,0).get(0).source.name.equals("Flash Heal"),"Healing is attributed to its card");
            play(new FlashHeal());
        });
        q.add(()->{
            check(total(Metric.HEALING)==2,"Full-health healing adds no event");
            p.powers.add(new VampiricEmbracePower(p,2));p.currentHealth=490;
            m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,3));((ShadowDotPower)m.getPower(ShadowDotPower.TOUCH)).naturalTick();
        });
        q.add(()->{
            check(report().rows(Metric.HEALING,0).stream().anyMatch(r->r.source.name.equals("Vampiric Embrace")&&r.amount==2),"Touch-triggered healing is credited to Vampiric Embrace");
            m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,3));m.powers.add(new MadnessPower(m,10));
            p.powers.clear();p.powers.add(new DexterityPower(p,8));p.powers.add(new FrailPower(p,2,false));p.loseBlock();
            ShadowWeaving weaving=new ShadowWeaving();weaving.applyPowers();check(weaving.rawDescription.contains("(6 Block at current DoTs)"),"Weaving preview ignores Dexterity and Frail");
            new ShadowWeavingPower(p,2).beforeDots();
        });
        q.add(()->{
            check(p.currentBlock==6,"Weaving grants one flat six Block, unaffected by Dexterity or Frail");
            check(new EncroachingShadows().magicNumber==5,"Encroaching Shadows applies five Madness");EncroachingShadows upgraded=new EncroachingShadows();upgraded.upgrade();check(upgraded.magicNumber==7,"Encroaching Shadows+ applies seven Madness");
            Singularity singularity=new Singularity();singularity.calculateCardDamage(m);
            check(singularity.rawDescription.startsWith("Deal")&&singularity.rawDescription.indexOf("damage)")<singularity.rawDescription.indexOf("Remove"),"Singularity's evaluated damage precedes DoT removal text");
            CardArtReview.card(singularity,"observations-singularity");
            MindSear x=new MindSear();x.applyPowers();check(x.rawDescription.contains("X ="),"X preview appears in combat");
            AbstractRoom.RoomPhase phase=AbstractDungeon.getCurrRoom().phase;AbstractDungeon.getCurrRoom().phase=AbstractRoom.RoomPhase.COMPLETE;
            try{x.resetAttributes();check(!x.rawDescription.contains("X =")&&!new MindSear().rawDescription.contains("X ="),"Existing and new X cards hide the energy preview outside combat");CardArtReview.card(x,"observations-x-outside");}
            finally{AbstractDungeon.getCurrRoom().phase=phase;}
            p.powers.clear();p.currentBlock=30;decay=new MindDecay();p.hand.addToTop(decay);hp=p.currentHealth;decay.atTurnStart();
        });
        q.add(()->{
            check(p.currentHealth==hp-1&&p.currentBlock==30&&decay.selfRetain,"Retained Mind Decay loses one HP at turn start without consuming Block");
            hp=p.currentHealth;m.isDying=true;decay.atTurnStart();m.isDying=false;
            check(p.currentHealth==hp&&AbstractDungeon.actionManager.actions.isEmpty(),"Mind Decay does not queue after the final enemy dies");
            check(shadowpriest.vfx.ShadowSpellEffect.collapseTier(49)==0&&shadowpriest.vfx.ShadowSpellEffect.collapseTier(50)==1&&shadowpriest.vfx.ShadowSpellEffect.collapseTier(99)==1&&shadowpriest.vfx.ShadowSpellEffect.collapseTier(100)==2,"Singularity FX thresholds are 50 and 100 damage");
            CardArtReview.singularityFx();
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new com.megacrit.cardcrawl.monsters.exordium.Cultist(300,0),false));
        });
        q.add(()->{m.powers.clear();m.currentHealth=2;m.currentBlock=3;before=total(Metric.DAMAGE_DEALT);hit(10);});
        q.add(()->{
            check(total(Metric.DAMAGE_DEALT)==before+10,"Lethal hit includes absorbed damage and overkill");
            Event last=report().events.get(report().events.size()-1);check(last.amount==2&&last.blocked==3&&last.overkill==5,"Lethal hit splits into two HP, three absorbed, five overkill");
            DetailsTracker.save();DetailsStore.flush();Run saved=DetailsStore.load(DetailsTracker.run().id);
            check(saved!=null&&saved.overall().total(Metric.HEALING,0)==total(Metric.HEALING)&&saved.overall().extendedMetrics,"Extended metrics survive save and Overall aggregation");
            DetailsScreen.INSTANCE.show();CardArtReview.requestCombat("observations-details");
        });
        q.add(()->{});q.add(()->{});
        q.add(()->{check(CardArtReview.capturedCombat("observations-details"),"Segmented damage report rendered");DetailsScreen.INSTANCE.selectMetric(Metric.HEALING);CardArtReview.requestCombat("observations-healing");});
        q.add(()->{});q.add(()->{});
        q.add(()->{check(CardArtReview.capturedCombat("observations-healing"),"Healing report rendered");DetailsScreen.INSTANCE.dismiss();});
    }
}
