package shadowpriest.dev;

import java.util.Queue;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.details.*;
import shadowpriest.powers.*;
import shadowpriest.relics.*;
import static shadowpriest.details.CombatReport.*;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Narrow attribution and layout checks; no full-run simulation. */
public final class InsanityDetailsSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m;private static VoidformRelic r;
    private static long source(String name){return DetailsTracker.current().rows(Metric.INSANITY,0).stream().filter(row->row.source.name.equals(name)).mapToLong(row->row.amount).sum();}
    private static long total(){return DetailsTracker.current().total(Metric.INSANITY,0);}
    private static void play(AbstractCard c){p.hand.addToTop(c);EnergyPanel.totalCount=3;c.applyPowers();c.calculateCardDamage(m);p.useCard(c,m,3);}
    public static void append(Queue<Runnable> q){
        q.add(()->{
            p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);r=VoidformRelic.current();
            p.powers.clear();p.loseBlock();p.currentHealth=p.maxHealth=500;m.powers.clear();m.currentHealth=m.maxHealth=1000;
            p.relics.removeIf(relic->!relic.relicId.equals(VoidformRelic.ID));r.reset();
            for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
            DetailsTracker.begin();DetailsTracker.startTurn();new ShadowyPreparation().atBattleStart();
            check(source("Shadowy Preparation")==10,"Combat-start Insanity is credited to Shadowy Preparation");play(new MindSpike());
        });
        q.add(()->{check(source("Mind Spike")==20,"Mind Spike combines direct-hit and explicit card Insanity");play(new MindFlay());});
        q.add(()->{
            check(source("Mind Flay")==30,"Multi-hit Insanity is credited once per hit to the card");
            m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,3));((ShadowDotPower)m.getPower(ShadowDotPower.TOUCH)).naturalTick();
        });
        q.add(()->{
            check(source("Vampiric Touch")==1,"Natural DoT tick has its own Insanity source");
            p.powers.add(new PsychicLinkPower(p,1));play(new MindSpike());
        });
        q.add(()->{
            check(source("Vampiric Touch (Psychic Link)")==1,"Psychic Link ticks keep their triggered source");
            p.powers.clear();p.damage(new DamageInfo(m,1,DamageInfo.DamageType.NORMAL));
            check(source(m.name+" (damage taken)")==5,"Enemy HP damage attributes five Insanity to the enemy");
            play(new ShadowWordDeath());
        });
        q.add(()->{
            check(source("Shadow Word: Death")==10&&source("Shadow Word: Death (damage taken)")==3,"Self-damage source is separate and only the three points remaining before cap count");
            check(total()==100&&r.state.ready(),"Source totals equal the actual hundred-point bar");
            r.gainInsanity(50);check(total()==100,"Ready-state gains are excluded");
            r.activate(r.state.charge());r.gainInsanity(50);check(total()==100,"Active Voidform gains are excluded");r.onPlayerEndTurn();
        });
        q.add(()->{
            new ReapingMadnessPower(p,12).atEndOfTurn(true);check(total()==100,"Recharge gains are excluded");
            r.state.startTurn();r.state.startTurn();DetailsTracker.startTurn();new ReapingMadnessPower(p,12).atEndOfTurn(true);
            check(source("Reaping Madness")==12&&DetailsTracker.current().total(Metric.INSANITY,2)==12,"Power gains retain their source and turn");
            p.currentBlock=20;p.damage(new DamageInfo(m,1,DamageInfo.DamageType.NORMAL));check(total()==112,"Fully blocked incoming damage grants no Insanity");
            r.enterImmediately();check(total()==112,"Forced Voidform entry does not invent generated Insanity");
        });
        q.add(()->{
            DetailsTracker.save();DetailsStore.flush();Run saved=DetailsStore.load(DetailsTracker.run().id);
            check(saved.overall().total(Metric.INSANITY,0)==112&&saved.overall().insanityRecorded,"Insanity survives saving and Overall aggregation");
            IdolOfYoggSaron yogg=new IdolOfYoggSaron();check(yogg.description.contains("[E] [E] [E] ."),"Yogg's three energy tokens are separate from punctuation");CardArtReview.yoggDescription(yogg.description);
            DetailsScreen.INSTANCE.show();DetailsScreen.INSTANCE.selectMetric(Metric.INSANITY);CardArtReview.requestCombat("details-insanity");
        });
        q.add(()->{});q.add(()->{});
        q.add(()->{check(CardArtReview.capturedCombat("details-insanity"),"Five-metric Details layout and Insanity sources rendered");DetailsScreen.INSTANCE.selectOverall(true);CardArtReview.requestCombat("details-insanity-overall");});
        q.add(()->{});q.add(()->{});
        q.add(()->{check(CardArtReview.capturedCombat("details-insanity-overall"),"Overall Insanity report rendered");DetailsScreen.INSTANCE.dismiss();});
    }
}
