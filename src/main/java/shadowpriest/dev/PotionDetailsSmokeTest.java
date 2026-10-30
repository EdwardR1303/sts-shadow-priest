package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.potions.*;
import com.megacrit.cardcrawl.powers.RegenPower;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.MindSpike;
import shadowpriest.details.*;
import shadowpriest.relics.VoidformRelic;
import java.nio.file.Paths;
import java.util.Queue;
import static shadowpriest.details.CombatReport.*;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Short potion attribution and final-hit archive checks in the real engine. */
public final class PotionDetailsSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m,n;
    private static boolean ending;private static long started;
    private static long source(Metric metric,String id){return DetailsTracker.current().rows(metric,0).stream().filter(r->id.equals(r.source.id)).mapToLong(r->r.amount).sum();}
    private static void potion(AbstractPotion potion,AbstractCreature target){potion.use(target);}
    public static void append(Queue<Runnable> q){
        q.add(()->{
            p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);
            p.powers.clear();p.loseBlock();p.currentHealth=p.maxHealth=100;m.powers.clear();m.currentHealth=m.maxHealth=1000;
            p.relics.removeIf(r->!r.relicId.equals(VoidformRelic.ID));VoidformRelic.current().reset();
            for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
            DetailsTracker.begin();DetailsTracker.startTurn();m.currentBlock=7;potion(new FirePotion(),m);
        });
        q.add(()->{
            check(source(Metric.DAMAGE_DEALT,"Fire Potion")==20,"Fire Potion receives named damage credit");
            Row row=DetailsTracker.current().rows(Metric.DAMAGE_DEALT,0).get(0);
            check(row.source.kind.equals("potion")&&row.blocked==7&&m.currentHealth==987,"Potion damage preserves absorbed Block and actual HP damage");
            n=new Cultist(300,0);n.currentHealth=n.maxHealth=1000;AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(n,false));
        });
        q.add(()->{potion(new ExplosivePotion(),null);});
        q.add(()->{check(source(Metric.DAMAGE_DEALT,"Explosive Potion")==20&&m.currentHealth==977&&n.currentHealth==990,"Explosive Potion credits both enemies to one potion source");potion(new BlockPotion(),p);});
        q.add(()->{check(source(Metric.BLOCK,"Block Potion")==12&&p.currentBlock==12,"Block Potion records its Block under the potion name");p.currentHealth=98;potion(new BloodPotion(),p);});
        q.add(()->{check(source(Metric.HEALING,"BloodPotion")==2&&p.currentHealth==100,"Blood Potion records actual healing without overheal");potion(new StrengthPotion(),p);});
        q.add(()->{MindSpike c=new MindSpike();p.hand.addToTop(c);EnergyPanel.totalCount=1;c.applyPowers();c.calculateCardDamage(m);p.useCard(c,m,1);});
        q.add(()->{check(source(Metric.DAMAGE_DEALT,MindSpike.ID)==8&&source(Metric.DAMAGE_DEALT,"Strength Potion")==0,"Potion buffs do not steal later card damage attribution");p.powers.clear();potion(new RegenPotion(),p);});
        q.add(()->{p.currentHealth=96;((RegenPower)p.getPower(RegenPower.POWER_ID)).atEndOfTurn(true);});
        q.add(()->{check(source(Metric.HEALING,RegenPower.POWER_ID)==4&&source(Metric.HEALING,"Regen Potion")==0,"Recurring Regeneration retains power attribution and excludes overheal");p.powers.clear();p.loseBlock();p.currentHealth=5;
            FairyPotion fairy=new FairyPotion();p.potions.set(0,fairy);fairy.setAsObtained(0);
            p.damage(new DamageInfo(m,10,DamageInfo.DamageType.NORMAL));
            check(!p.isDead&&p.currentHealth==30&&source(Metric.HEALING,"FairyPotion")==30,"Fairy revival is credited to the potion during lethal damage");
            check(DetailsTracker.current().rows(Metric.HEALING,0).stream().noneMatch(r->r.source.kind.equals("enemy")),"Lethal attacker receives no Fairy healing credit");
            potion(new FirePotion(){@Override public void use(AbstractCreature victim){addToTop(new AbstractGameAction(){@Override public void update(){addToTop(new DamageAction(victim,new DamageInfo(p,3,DamageInfo.DamageType.THORNS)));isDone=true;}});}},m);
        });
        q.add(()->{check(source(Metric.DAMAGE_DEALT,"Fire Potion")==23,"Nested potion actions queued at the top inherit their source");
            DetailsTracker.save();DetailsStore.flush();Run saved=DetailsHistory.load(Paths.get("details"),DetailsTracker.run().id);
            check(saved!=null&&saved.overall().rows(Metric.DAMAGE_DEALT,0).stream().anyMatch(r->r.source.id.equals("Fire Potion")&&r.amount==23),"Potion sources survive save and archive reload");
            check(saved.overall().events.stream().allMatch(e->e.source.id!=null&&!e.source.id.isEmpty()),"All recorded sources have IDs");
            n.currentHealth=1;potion(new FirePotion(),n);
        });
        q.add(()->{m.currentHealth=2;m.currentBlock=3;ending=true;started=System.currentTimeMillis();potion(new FirePotion(),m);});
    }
    public static boolean handleCompletion(){
        if(!ending)return false;
        if(DetailsTracker.active()){
            if(System.currentTimeMillis()-started>10000)throw new AssertionError("Potion victory did not finish tracking");
            return true;
        }
        check(source(Metric.DAMAGE_DEALT,"Fire Potion")==63,"Combat-ending Fire Potion retains damage and overkill credit");
        Event last=DetailsTracker.current().events.stream().filter(e->e.metric==Metric.DAMAGE_DEALT).reduce((a,b)->b).get();
        check(last.amount==2&&last.blocked==3&&last.overkill==15,"Final potion hit splits HP, absorbed Block, and overkill correctly");
        DetailsStore.flush();Run loaded=DetailsHistory.load(Paths.get("details"),DetailsTracker.run().id);
        check(loaded!=null&&loaded.combats.get(loaded.combats.size()-1).outcome.equals("Victory"),"Potion-ended combat remains visible in saved history");
        ending=false;CombatSmokeTest.finish(null);return true;
    }
}
