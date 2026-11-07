package shadowpriest.dev;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.random.Random;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused equipment checks; no full-run simulation. */
public final class LegendaryBalanceSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m;
    private static Random savedRng;
    private static final String[] STATS={"Strength",MasteryPower.POWER_ID,"Dexterity"};
    private static final int[] expected=new int[3];
    private static <T extends AbstractCard>T up(T c){c.upgrade();return c;}
    private static void reset(){
        p.powers.clear();p.loseBlock();p.currentHealth=p.maxHealth=100;
        p.hand.clear();p.drawPile.clear();p.discardPile.clear();p.exhaustPile.clear();
        m.powers.clear();m.currentHealth=m.maxHealth=1000;
        shadowpriest.relics.VoidformRelic.current().reset();
    }
    private static void rollCheck(int perRoll){
        int total=0;
        for(int i=0;i<STATS.length;i++){
            int actual=p.hasPower(STATS[i])?p.getPower(STATS[i]).amount:0;
            check(actual==expected[i]*perRoll,"Four independent rolls grant "+perRoll+" per selected "+STATS[i]);
            total+=actual;
        }
        check(total==4*perRoll&&!p.hasPower(HastePower.POWER_ID),"Stat budget is "+4*perRoll+" with no Haste");
    }
    private static void nextTurn(){
        m.setMove((byte)0,AbstractMonster.Intent.NONE);m.createIntent();
        for(int i=0;i<10;i++)p.drawPile.addToTop(new PowerWordShield());
        AbstractDungeon.getCurrRoom().skipMonsterTurn=false;
        AbstractDungeon.overlayMenu.endTurnButton.disable(true);p.endTurnQueued=true;
    }
    public static void append(Queue<Runnable> q){
        q.add(()->{
            p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);savedRng=AbstractDungeon.miscRng;reset();
            Random expectedRng=new Random(13579L);for(int i=0;i<4;i++)expected[expectedRng.random(2)]++;
            AbstractDungeon.miscRng=new Random(13579L);new Xalatath().equip(p);
        });
        q.add(()->{rollCheck(2);reset();AbstractDungeon.miscRng=new Random(13579L);Xalatath upgraded=up(new Xalatath());check(upgraded.magicNumber==4,"Upgrade keeps four rolls");upgraded.equip(p);});
        q.add(()->{rollCheck(3);nextTurn();});
        q.add(()->{rollCheck(3);AbstractDungeon.miscRng=savedRng;reset();new Valanyr().equip(p);});
        q.add(()->{check(p.currentBlock==0&&p.hasPower("Barricade")&&p.hasPower(GuardianAngelPower.POWER_ID),"Base Valanyr starts at zero Block with retention and Guardian Angel");p.addBlock(14);nextTurn();});
        q.add(()->{check(p.currentBlock==14,"Valanyr retains subsequently gained Block through a real turn");p.damage(new DamageInfo(m,14,DamageInfo.DamageType.NORMAL));});
        q.add(()->{check(p.currentBlock==0&&p.hasPower(ValanyrPower.POWER_ID)&&p.hasPower("Barricade"),"Exact depletion does not remove Valanyr's Barricade");p.addBlock(9);nextTurn();});
        q.add(()->{check(p.currentBlock==9,"Block gained after depletion is still retained next turn");p.loseBlock();p.currentHealth=3;p.damage(new DamageInfo(p,9,DamageInfo.DamageType.HP_LOSS));});
        q.add(()->{check(p.currentHealth==1&&!p.hasPower(GuardianAngelPower.POWER_ID)&&p.hasPower("Barricade"),"Guardian Angel is consumed independently; Barricade persists after explicit removal and rescue");reset();p.powers.add(new DexterityPower(p,5));up(new Valanyr()).equip(p);});
        q.add(()->{
            check(p.currentBlock==10&&p.hasPower("Barricade"),"Valanyr+ grants flat ten opening Block, unaffected by Dexterity");
            for(AbstractCard c:new AbstractCard[]{new Xalatath(),up(new Xalatath()),new Valanyr(),up(new Valanyr())})
                CardArtReview.card(c,"legendary-balance-"+c.cardID.split(":")[1]+(c.upgraded?"-plus":""));
        });
    }
}
