package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.*;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.RelicLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.relics.*;
import shadowpriest.powers.*;
import shadowpriest.legendary.LegendaryEquipment;
import java.util.*;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Actual reward pools, card payment, combat-deck construction, and stance lifecycles. */
public final class RelicSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m;private static VoidformRelic starter;
    private static AbstractCard slam;private static int hand;
    private static final String[] IDS={ShadowyPreparation.ID,IdolOfCthun.ID,IdolOfYshaarj.ID,IdolOfNzoth.ID,IdolOfYoggSaron.ID,ImprovedVoidform.ID,VoidVengeance.ID};
    private static int stat(String id){return p.hasPower(id)?p.getPower(id).amount:0;}
    private static void own(AbstractRelic r){r.instantObtain(p,p.relics.size(),false);}
    private static void reset(){
        p.relics.removeIf(r->!r.relicId.equals(VoidformRelic.ID));
        p.powers.clear();p.currentBlock=0;p.currentHealth=p.maxHealth=500;
        p.hand.clear();p.drawPile.clear();p.discardPile.clear();p.exhaustPile.clear();p.limbo.clear();
        m.powers.clear();m.currentHealth=m.maxHealth=3000;m.currentBlock=0;
        starter.reset();LegendaryEquipment.State.equipped.set(p,null);
        p.masterDeck.clear();for(int i=0;i<10;i++)p.masterDeck.addToTop(new MindSpike());
        for(int i=0;i<20;i++)p.drawPile.addToTop(new PowerWordShield());
        EnergyPanel.totalCount=3;
    }
    private static void play(AbstractCard c,AbstractMonster target,int energy){
        p.drawPile.removeCard(c);p.discardPile.removeCard(c);p.hand.removeCard(c);p.hand.addToTop(c);c.energyOnUse=energy;EnergyPanel.totalCount=energy;
        if(!c.canUse(p,target))throw new AssertionError("Cannot play "+c.name+": "+c.cantUseMessage);
        p.useCard(c,target,energy);
    }
    private static void nextTurn(){
        for(AbstractMonster t:AbstractDungeon.getMonsters().monsters){t.setMove((byte)0,AbstractMonster.Intent.NONE);t.createIntent();}
        AbstractDungeon.getCurrRoom().skipMonsterTurn=false;AbstractDungeon.overlayMenu.endTurnButton.disable(true);p.endTurnQueued=true;
    }
    private static ArrayList<String> pool(AbstractRelic.RelicTier tier){
        switch(tier){case COMMON:return AbstractDungeon.commonRelicPool;case UNCOMMON:return AbstractDungeon.uncommonRelicPool;case RARE:return AbstractDungeon.rareRelicPool;case BOSS:return AbstractDungeon.bossRelicPool;default:return AbstractDungeon.shopRelicPool;}
    }
    private static void initPools(){
        try{java.lang.reflect.Method method=AbstractDungeon.class.getDeclaredMethod("initializeRelicList");method.setAccessible(true);method.invoke(CardCrawlGame.dungeon);}
        catch(Exception e){throw new RuntimeException(e);}
    }
    private static void pools(){
        for(String id:IDS){AbstractRelic r=RelicLibrary.getRelic(id);check(r.relicId.equals(id)&&pool(r.tier).contains(id),id+" registered in its initialized class pool");
            check(r.canSpawn(),id+" can spawn for priest");ArrayList<String> values=pool(r.tier);values.remove(id);values.add(0,id);
            AbstractRelic reward=AbstractDungeon.returnRandomRelic(r.tier);check(reward.relicId.equals(id)&&reward!=r,id+" selected through real reward factory");
            reward.loadLargeImg();check(reward.img.getWidth()==128&&reward.largeImg.getWidth()==256&&!reward.description.isEmpty(),id+" loads both art sizes and description");
        }
        AbstractPlayer.PlayerClass selected=CardCrawlGame.chosenCharacter;
        try{
            AbstractDungeon.player=CardCrawlGame.characterManager.getCharacter(AbstractPlayer.PlayerClass.IRONCLAD);CardCrawlGame.chosenCharacter=AbstractPlayer.PlayerClass.IRONCLAD;initPools();
            for(String id:IDS){AbstractRelic r=RelicLibrary.getRelic(id);check(!pool(r.tier).contains(id)&&!r.canSpawn(),id+" excluded from Ironclad pools and spawning");}
        }finally{AbstractDungeon.player=p;CardCrawlGame.chosenCharacter=selected;initPools();}
        check(AbstractDungeon.shopRelicPool.stream().noneMatch(id->id.startsWith("shadowpriest:")),"Unreviewed Shop relic remains absent");
    }
    public static void append(Queue<Runnable> q){
        if(Boolean.getBoolean("shadowpriest.relicartonly")){
            q.add(()->{p=AbstractDungeon.player;for(String id:IDS)own(RelicLibrary.getRelic(id).makeCopy());
                AbstractDungeon.actionManager.addToBottom(new WaitAction(2f));});
            q.add(()->{CardArtReview.requestCombat("relics-equipped");AbstractDungeon.actionManager.addToBottom(new WaitAction(1f));});
            q.add(()->check(CardArtReview.capturedCombat("relics-equipped"),"All seven relic icons captured in the actual top bar"));
            return;
        }
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);starter=VoidformRelic.current();pools();reset();
            own(new ShadowyPreparation());starter.gainInsanity(70);p.preBattlePrep();p.applyStartOfCombatLogic();});
        q.add(()->{check(starter.state.amount()==10,"Preparation grants ten after combat reset, not seventy plus ten");
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Cultist(300f,0),false));});
        q.add(()->{check(starter.state.amount()==10,"Later enemy summon does not grant Preparation again");
            Collections.reverse(p.relics);p.preBattlePrep();p.applyStartOfCombatLogic();});
        q.add(()->{check(starter.state.amount()==20,"Preparation counts two enemies and works with reversed relic order");reset();
            own(new IdolOfCthun());p.preBattlePrep();p.applyStartOfCombatPreDrawLogic();AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,5));p.applyStartOfCombatLogic();});
        q.add(()->{check(p.hand.size()==6,"CThun adds an extra card without reducing the opening five draws");
            slam=p.hand.group.stream().filter(c->c instanceof TentacleSlam).findFirst().orElseThrow(()->new AssertionError("Missing Slam"));
            check(!slam.upgraded&&slam.cost==0&&slam.costForTurn==0,"CThun creates an unupgraded combat-long zero-cost Slam");
            check(p.masterDeck.group.stream().noneMatch(c->c instanceof TentacleSlam),"Generated Slam never enters permanent deck");play(slam,m,0);});
        q.add(()->{check(p.discardPile.contains(slam)&&EnergyPanel.totalCount==0,"Generated Slam plays for zero and discards normally");nextTurn();});
        q.add(()->{play(slam,m,0);});
        q.add(()->{check(slam.cost==0&&slam.costForTurn==0,"CThun discount survives turn and replay");slam.upgrade();
            AbstractCard copy=slam.makeStatEquivalentCopy();check(slam.cost==0&&copy.cost==0&&copy.upgraded,"Combat upgrade and stat-equivalent copies preserve free cost");
            check(new TentacleSlam().cost==2,"Other Tentacle Slams retain normal cost");reset();own(new VoidVengeance());
            p.masterDeck.clear();p.masterDeck.addToTop(new Regret());p.masterDeck.addToTop(new AscendersBane());p.masterDeck.addToTop(new CurseOfTheBell());p.masterDeck.addToTop(new Dazed());p.masterDeck.addToTop(new MindSpike());p.preBattlePrep();});
        q.add(()->{check(p.drawPile.group.stream().filter(c->c.type==AbstractCard.CardType.CURSE).count()==3,"Vengeance preserves starting combat Curses");
            check(p.masterDeck.group.stream().filter(c->c.type==AbstractCard.CardType.CURSE).count()==3&&p.drawPile.group.stream().anyMatch(c->c instanceof Dazed),"Permanent Curses and combat Status are preserved");
            check(p.getRelic(VoidVengeance.ID).counter==1,"Vengeance starts with one free Curse play");reset();own(new IdolOfNzoth());
            p.masterDeck.addToTop(new Valanyr());p.preBattlePrep();});
        q.add(()->{check(stat(MasteryPower.POWER_ID)==1&&p.hasPower(GuardianAngelPower.POWER_ID),"NZoth grants one Mastery for actual Legendary equipment activation");
            check(AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty(),"Equipment still does not count as a globally played Power");
            ((IdolOfNzoth)p.getRelic(IdolOfNzoth.ID)).legendaryEquipped();new Valanyr().use(p,null);});
        q.add(()->{check(stat(MasteryPower.POWER_ID)==1,"Duplicate notification and forced Legendary use cannot grant extra Mastery");
            play(new DarkEvangelism(),null,2);});
        q.add(()->{check(stat(MasteryPower.POWER_ID)==4,"Normal Power resolves its two Mastery then NZoth adds one");p.preBattlePrep();});
        q.add(()->{check(stat(MasteryPower.POWER_ID)==1,"NZoth equipment benefit resets for next combat");reset();own(new IdolOfYshaarj());own(new IdolOfYoggSaron());own(new ImprovedVoidform());
            starter.gainInsanity(100);starter.activate(starter.state.charge());});
        q.add(()->{check(EnergyPanel.totalCount==6,"Yogg grants three energy on successful normal entry");starter.activate(starter.state.charge());});
        q.add(()->{check(EnergyPanel.totalCount==6,"Invalid repeat activation grants no energy");hand=p.hand.size();play(new MindSpike(),m,3);});
        q.add(()->{check(EnergyPanel.totalCount==3&&p.hand.size()==hand,"Yshaarj does not draw for Attack discounted to zero");check(starter.state.attacksPlayed()==1,"One Attack increments Improved counter once");
            hand=p.hand.size();play(new BloodMagic(),m,3);});
        q.add(()->{check(EnergyPanel.totalCount==2&&p.hand.size()==hand+1,"Yshaarj draws once after a discounted two-cost Attack spends one energy");
            check(starter.state.attacksPlayed()==2,"Second Attack recorded");nextTurn();});
        q.add(()->{check(starter.state.active()&&p.hasPower(VoidformPower.POWER_ID)&&starter.state.attacksPlayed()==2,"Voidform and its power persist after two Attacks across a real turn");
            check(!starter.state.recovering(),"Carryover does not prematurely start Recharge");
            hand=p.hand.size();play(new MindFlay(),m,3);});
        q.add(()->{check(starter.state.active()&&starter.state.attacksPlayed()==3,"Multihit Flay counts once and third Attack does not immediately end Voidform");
            check(p.hand.size()==hand,"Discounted free multihit gives no Yshaarj draw");
            hand=p.hand.size();play(new PowerWordShield(),null,3);});
        q.add(()->{check(p.hand.size()==hand+1&&EnergyPanel.totalCount==2,"Paid Skill draws one in extended Voidform");
            hand=p.hand.size();play(new ReapingMadness(),null,3);});
        q.add(()->{check(p.hand.size()==hand+1,"Paid Power draws one in extended Voidform");nextTurn();});
        q.add(()->{check(!starter.state.active()&&!p.hasPower(VoidformPower.POWER_ID)&&starter.state.recovering(),"Third-Attack turn ends form and starts following-turn Recharge");
            starter.gainInsanity(100);check(starter.state.amount()==0,"Recharge blocks generation after extended form");nextTurn();});
        q.add(()->{check(!starter.state.recovering(),"Recharge completes after exactly one subsequent player turn");reset();own(new IdolOfYshaarj());own(new IdolOfYoggSaron());own(new ImprovedVoidform());
            starter.gainInsanity(100);starter.activate(starter.state.charge());});
        q.add(()->{hand=p.hand.size();play(new MindSear(),null,2);});
        q.add(()->{check(p.hand.size()==hand+1&&EnergyPanel.totalCount==0,"Positive-energy X draws once, not once per energy or hit");
            p.relics.add(new ChemicalX());p.powers.add(new HastePower(p,2));hand=p.hand.size();play(new MindSear(),null,0);});
        q.add(()->{check(p.hand.size()==hand,"Haste and Chemical X with zero actual energy do not trigger draw");
            AbstractCard free=new BloodMagic();free.freeToPlayOnce=true;hand=p.hand.size();play(free,m,3);});
        q.add(()->{check(EnergyPanel.totalCount==3&&p.hand.size()==hand,"Free-to-play Attack gives no Yshaarj draw");
            play(new SurrenderToMadness(),null,3);});
        q.add(()->{check(starter.state.surrendered()&&EnergyPanel.totalCount==0,"Surrender extends active form without awarding Yogg energy again");nextTurn();});
        q.add(()->{check(starter.state.active()&&!starter.state.recovering(),"Surrender remains permanent with Improved Voidform");reset();own(new IdolOfYoggSaron());play(new SurrenderToMadness(),null,3);});
        q.add(()->{check(EnergyPanel.totalCount==3&&VoidformRelic.isPermanent(),"Surrender from outside Voidform earns Yogg energy");reset();
            for(String id:IDS)own(RelicLibrary.getRelic(id).makeCopy());CardArtReview.requestCombat("relics-equipped");AbstractDungeon.actionManager.addToBottom(new WaitAction(1f));});
        q.add(()->check(CardArtReview.capturedCombat("relics-equipped"),"All seven relic icons captured in the actual top bar"));
    }
}

