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
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused real-engine checks for the persistent, first-spent Barrier reserve. */
public final class BarrierReserveSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m;
    private static long generated;
    private static int reserve(){return BarrierPower.remaining(p);}
    private static long blockTotal(){return DetailsTracker.current().total(CombatReport.Metric.BLOCK,0);}
    private static void reset(){
        p.loseBlock();p.powers.clear();p.currentBlock=0;p.currentHealth=p.maxHealth=500;
        p.hand.clear();p.drawPile.clear();p.discardPile.clear();p.exhaustPile.clear();p.limbo.clear();
        p.relics.removeIf(r->!r.relicId.equals(VoidformRelic.ID));VoidformRelic.current().reset();
        m.powers.clear();m.currentBlock=0;m.currentHealth=m.maxHealth=10000;
    }
    private static <T extends AbstractCard>T up(T card){card.upgrade();return card;}
    private static void play(AbstractCard card){
        p.hand.addToTop(card);EnergyPanel.totalCount=3;card.energyOnUse=3;
        check(card.canUse(p,m),card.name+" remains playable");p.useCard(card,m,3);
    }
    private static void hit(int damage){p.damage(new DamageInfo(m,damage,DamageInfo.DamageType.NORMAL));}
    private static void nextTurn(){
        m.setMove((byte)0,AbstractMonster.Intent.NONE);m.createIntent();
        for(int i=0;i<6;i++)p.drawPile.addToTop(new PowerWordShield());
        p.endTurnQueued=true;
    }
    public static void append(Queue<Runnable> q){
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);reset();p.addBlock(8);play(new PowerWordBarrier());});
        q.add(()->{
            check(p.currentBlock==38&&reserve()==30&&EnergyPanel.totalCount==0,"Base Barrier adds a thirty-point reserve alongside ordinary Block for three energy");
            check(!p.hasPower("Barricade"),"Barrier no longer impersonates unrestricted Barricade");
            hit(12);check(p.currentBlock==26&&reserve()==18,"Damage consumes Barrier before the eight ordinary Block");
            p.damage(new DamageInfo(p,5,DamageInfo.DamageType.HP_LOSS));
            check(p.currentHealth==495&&p.currentBlock==26&&reserve()==18,"HP loss bypasses Block without consuming Barrier");
            p.addBlock(5);check(p.currentBlock==31&&reserve()==18,"Additional Block does not refill the reserve");
            generated=blockTotal();nextTurn();
        });
        q.add(()->{
            check(p.currentBlock==18&&reserve()==18,"Real turn boundary expires ordinary Block and retains exactly the remaining Barrier");
            check(blockTotal()==generated,"Retention does not generate Block or inflate Details");
            hit(16);check(p.currentBlock==2&&reserve()==2,"A nearly depleted Barrier tracks its two remaining points");play(new PowerWordShield());
        });
        q.add(()->{
            check(p.currentBlock==7&&reserve()==2,"Shield works normally with two Barrier remaining");
            m.powers.add(new AtonementPower(m,2));play(new MindSpike());
        });
        q.add(()->{
            check(p.currentBlock==9&&reserve()==2,"Passive Atonement Block works without changing Barrier");
            hit(2);check(p.currentBlock==7&&reserve()==0&&!BarrierPower.active(p),"Exact Barrier depletion leaves ordinary Block intact");
        });
        q.add(()->{
            check(!p.hasPower(BarrierPower.POWER_ID),"Spent reserve removes the buff even while ordinary Block remains");
            nextTurn();
        });
        q.add(()->{check(p.currentBlock==0,"Ordinary Block expires normally after Barrier is gone");reset();play(new PowerWordBarrier());});
        q.add(()->{hit(18);p.addBlock(8);generated=blockTotal();play(new PowerWordBarrier());});
        q.add(()->{
            check(p.currentBlock==38&&reserve()==30,"Recast refills twelve Barrier to thirty and preserves eight ordinary Block");
            check(blockTotal()==generated+18,"Details counts only the eighteen newly generated Block on refresh");
            play(up(new PowerWordBarrier()));
        });
        q.add(()->{check(p.currentBlock==48&&reserve()==40,"Upgraded recast raises the reserve to forty");generated=blockTotal();play(new PowerWordBarrier());});
        q.add(()->{
            check(p.currentBlock==48&&reserve()==40&&blockTotal()==generated,"Weaker recast neither stacks nor reduces the stronger reserve");
            hit(45);check(p.currentBlock==3&&reserve()==0,"One hit can consume the entire reserve and then ordinary Block");
        });
        q.add(()->{reset();p.powers.add(new DexterityPower(p,4));play(up(new PowerWordBarrier()));});
        q.add(()->{check(p.currentBlock==44&&reserve()==44,"Barrier plus applies Dexterity once");generated=blockTotal();nextTurn();});
        q.add(()->{
            check(p.currentBlock==44&&reserve()==44&&blockTotal()==generated,"Dexterity is not reapplied on retention");
            reset();p.powers.add(new DexterityPower(p,4));p.powers.add(new FrailPower(p,2,false));play(new PowerWordBarrier());
        });
        q.add(()->{check(p.currentBlock==25&&reserve()==25,"Frail and Dexterity set the initial reserve using ordinary Block rounding");reset();p.addBlock(990);play(new PowerWordBarrier());});
        q.add(()->{
            check(p.currentBlock==999&&reserve()==9,"Block cap limits Barrier to actual new Block, without converting existing Block");
            nextTurn();
        });
        q.add(()->{check(p.currentBlock==9&&reserve()==9,"Cap-limited Barrier retains only nine points (Block="+p.currentBlock+", reserve="+reserve()+", power="+(p.getPower(BarrierPower.POWER_ID)==null?"missing":p.getPower(BarrierPower.POWER_ID).amount)+")");reset();p.powers.add(new DexterityPower(p,-30));play(new PowerWordBarrier());});
        q.add(()->{check(p.currentBlock==0&&!p.hasPower(BarrierPower.POWER_ID),"Zero-value cast creates no empty Barrier buff");reset();play(new PowerWordBarrier());});
        q.add(()->{p.addBlock(8);p.powers.add(new BarricadePower(p));nextTurn();});
        q.add(()->{
            check(p.currentBlock==38&&reserve()==30,"Independent Barricade retains ordinary Block alongside Barrier");
            hit(30);check(p.currentBlock==8&&reserve()==0&&p.hasPower("Barricade"),"Spending Barrier preserves independent Barricade");
        });
        q.add(()->{reset();play(new PowerWordBarrier());});
        q.add(()->{p.addBlock(8);p.powers.add(new BlurPower(p,2));nextTurn();});
        q.add(()->{check(p.currentBlock==38&&reserve()==30,"Blur independently preserves ordinary Block");reset();play(new PowerWordBarrier());});
        q.add(()->{p.addBlock(8);p.powers.add(new ValanyrPower(p));nextTurn();});
        q.add(()->{
            check(p.currentBlock==38&&reserve()==30,"Valanyr independently preserves ordinary Block");
            hit(30);check(p.currentBlock==8&&reserve()==0&&p.hasPower("Barricade"),"Spending Barrier does not break Valanyr when ordinary Block remains");
        });
        q.add(()->{reset();play(new PowerWordBarrier());});
        q.add(()->{p.addBlock(20);p.relics.add(new com.megacrit.cardcrawl.relics.Calipers());nextTurn();});
        q.add(()->{
            check(p.currentBlock==35&&reserve()==30,"Calipers expires fifteen ordinary Block without consuming Barrier");
            nextTurn();
        });
        q.add(()->{
            check(p.currentBlock==30&&reserve()==30,"Calipers cannot spend Barrier when fewer than fifteen ordinary Block remain");
            p.loseBlock();
        });
        q.add(()->{
            check(p.currentBlock==0&&!p.hasPower(BarrierPower.POWER_ID),"Explicit full Block removal consumes the reserve without resurrection");
            reset();play(new PowerWordBarrier());
        });
        q.add(()->{
            p.powers.add(new ShadowWeavingPower(p,2));m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,3));
            nextTurn();
        });
        q.add(()->{
            check(p.currentBlock==30&&reserve()==30,"End-turn passive Block expires normally without canceling or refilling Barrier");
            m.powers.clear();p.powers.removeIf(power->power instanceof ShadowWeavingPower);
            p.addBlock(8);hit(12);CardArtReview.card(new PowerWordBarrier(),"barrier-reserve");
            CardArtReview.card(up(new PowerWordBarrier()),"barrier-reserve-plus");
            CardArtReview.requestCombat("barrier-reserve");
        });
        q.add(()->{});q.add(()->{});
        q.add(()->check(CardArtReview.capturedCombat("barrier-reserve"),"Tracked Barrier stacks and ordinary Block captured in combat"));
    }
}
