package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.*;
import shadowpriest.mechanics.*;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused October 29 low-pick revisions, with real card movement and cost payment. */
public final class LowPickSmokeTest {
    private static AbstractPlayer p;private static AbstractMonster m,n;private static VoidformRelic relic;
    private static ShadowWordDeath death;private static Serendipity retained;
    private static void reset(){
        p.powers.clear();p.loseBlock();p.currentHealth=p.maxHealth=100;
        p.relics.removeIf(r->!r.relicId.equals(VoidformRelic.ID));
        if(!p.hasRelic(VoidformRelic.ID))p.relics.add(relic);relic.reset();
        for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
        for(int i=0;i<20;i++)p.drawPile.addToBottom(new MindSpike());
        m.powers.clear();m.loseBlock();m.currentHealth=m.maxHealth=10000;
        EnergyPanel.totalCount=3;PlayerTurnWindow.active=true;
    }
    private static <T extends AbstractCard>T plus(T c){c.upgrade();return c;}
    private static void play(AbstractCard c,AbstractMonster target,int energy){
        p.hand.removeCard(c);p.hand.addToTop(c);c.energyOnUse=energy;EnergyPanel.totalCount=energy;
        c.applyPowers();if(target!=null)c.calculateCardDamage(target);
        check(c.canUse(p,target),"Playable: "+c.name);p.useCard(c,target,energy);
    }
    private static void spawn(int hp){n=new Cultist(300,0);n.currentHealth=n.maxHealth=hp;AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(n,false));}
    private static void dots(AbstractMonster target){target.powers.add(new ShadowDotPower(target,ShadowDotPower.TOUCH,3));target.powers.add(new ShadowDotPower(target,ShadowDotPower.PAIN,3));target.powers.add(new MadnessPower(target,3));}
    private static int stat(String id){return p.hasPower(id)?p.getPower(id).amount:0;}
    public static void append(Queue<Runnable> q){
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);relic=VoidformRelic.current();reset();play(new MindBlast(),m,1);});
        q.add(()->{check(relic.state.amount()==10&&m.currentHealth==9990,"Unprepared Mind Blast grants only the universal ten Insanity");reset();spawn(1);});
        q.add(()->{dots(n);play(new MindBlast(),n,1);});
        q.add(()->{check(relic.state.amount()==40,"Mind Blast snapshots three DoTs and grants thirty bonus Insanity on a killing hit");reset();m.powers.add(new ArtifactPower(m,1));play(new ShadowWordTorment(),m,1);});
        q.add(()->{check(m.currentHealth==9993&&!m.hasPower(ShadowDotPower.PAIN)&&MadnessPower.stacks(m)==3&&!m.hasPower("Artifact"),"Torment hits for seven then applies Pain and Madness independently through Artifact");reset();play(plus(new ShadowWordTorment()),m,1);});
        q.add(()->{check(m.currentHealth==9990&&m.getPower(ShadowDotPower.PAIN).amount==5&&MadnessPower.stacks(m)==5,"Torment+ deals ten and applies five of both debuffs");reset();p.currentHealth=34;p.powers.add(new DexterityPower(p,4));p.powers.add(new HastePower(p,8));play(new Bulwark(),null,2);});
        q.add(()->{check(p.currentBlock==32&&stat("Blur")==2,"Low-health Bulwark grants Block twice with Dexterity twice and no Haste repeats");reset();p.currentHealth=35;play(plus(new Bulwark()),null,2);});
        q.add(()->{check(p.currentBlock==16&&stat("Blur")==1,"Exactly 35 percent HP does not activate Bulwark's bonus");reset();p.currentHealth=34;play(plus(new Bulwark()),null,2);});
        q.add(()->{check(p.currentBlock==32&&stat("Blur")==2,"Bulwark+ grants thirty-two Block and two Blur below threshold");reset();p.powers.add(new WeakPower(p,2,false));p.powers.add(new FrailPower(p,2,false));p.powers.add(new VulnerablePower(p,2,false));PurifyDisease c=plus(new PurifyDisease());check(!c.selfRetain&&c.exhaust,"Purify+ replaces Retain with Artifact and keeps Exhaust");play(c,null,0);});
        q.add(()->{check(!p.hasPower("Weakened")&&!p.hasPower("Frail")&&!p.hasPower("Vulnerable")&&stat("Artifact")==1,"Purify+ cleanses the three debuffs then grants one Artifact");MentalFortitude c=plus(new MentalFortitude());check(c.cost==1&&c.magicNumber==2&&!c.exhaust,"Fortitude+ costs one and retains two-point stats");play(c,null,1);});
        q.add(()->{check(stat("Dexterity")==2&&stat(MasteryPower.POWER_ID)==2&&!p.hasPower("Strength")&&p.hasPower("Artifact")&&p.hand.group.stream().anyMatch(c->c instanceof com.megacrit.cardcrawl.cards.curses.Doubt),"Fortitude creates Doubt without spending Purify's Artifact");play(plus(new MentalFortitude()),null,1);});
        q.add(()->{check(stat("Dexterity")==4&&stat(MasteryPower.POWER_ID)==4&&!p.hasPower("Strength")&&p.hand.group.stream().filter(c->c instanceof com.megacrit.cardcrawl.cards.curses.Doubt).count()==2,"Fortitude remains repeatable and generates one Doubt per play");reset();spawn(10000);});
        q.add(()->{dots(m);n.powers.add(new ArtifactPower(n,1));play(new MindSoothe(),null,0);});
        q.add(()->{check(m.getPower(MindSoothePower.POWER_ID).amount==4&&!n.hasPower(MindSoothePower.POWER_ID)&&!n.hasPower("Artifact"),"AoE Soothe evaluates three DoTs on one enemy and strips another's Artifact");play(plus(new MindSoothe()),null,0);});
        q.add(()->{check(m.getPower(MindSoothePower.POWER_ID).amount==9&&n.getPower(MindSoothePower.POWER_ID).amount==2,"Soothe+ stacks per enemy with independent setup values");reset();spawn(10);});
        q.add(()->{death=plus(new ShadowWordDeath());p.powers.add(new HastePower(p,3));play(death,n,0);});
        q.add(()->{check(n.currentHealth==0&&p.currentHealth==100&&p.hand.contains(death)&&relic.state.amount()==10,"First-hit kill stops all remaining Haste hits, avoids backlash, and returns the same Death once");p.powers.clear();play(death,m,0);});
        q.add(()->{check(p.currentHealth==96&&m.currentHealth==9980&&p.discardPile.contains(death)&&!p.hand.contains(death),"Replaying returned Death resets its refund flag; two survivors cost four HP and discard");reset();spawn(15);});
        q.add(()->{death=plus(new ShadowWordDeath());play(death,n,0);});
        q.add(()->{check(p.currentHealth==98&&n.currentHealth==0&&p.hand.contains(death),"Second-hit kill pays backlash only for the first hit then refunds Death");reset();m.currentBlock=100;play(plus(new ShadowWordDeath()),m,0);});
        q.add(()->{check(p.currentHealth==100,"Death preserves the existing rule that fully blocked hits have no HP-loss backlash");reset();p.powers.add(new TitheEvasionPower(p,2));play(plus(new ShadowWordDeath()),m,0);});
        q.add(()->{check(p.currentHealth==100&&relic.state.amount()==20,"Tithe+ prevents Death backlash and its damage-taken Insanity");reset();spawn(10);});
        q.add(()->{death=new ShadowWordDeath();death.purgeOnUse=true;play(death,n,0);});
        q.add(()->{check(!death.returnToHand&&!p.hand.contains(death),"A purged replay cannot create a returned Death card");reset();retained=plus(new Serendipity());p.hand.addToTop(retained);check(retained.selfRetain&&!retained.exhaust&&new Serendipity().selfRetain&&new Serendipity().exhaust,"Serendipity retains in both versions; only the base Exhausts");m.setMove((byte)0,AbstractMonster.Intent.NONE);m.createIntent();p.endTurnQueued=true;});
        q.add(()->{check(p.hand.contains(retained),"Serendipity+ remains in hand across a real turn");reset();p.powers.add(new ArtifactPower(p,1));SurrenderToMadness c=plus(new SurrenderToMadness());check(c.cost==3&&c.exhaust,"Surrender+ retains three cost and Exhaust");play(c,null,3);});
        q.add(()->{check(VoidformRelic.isPermanent()&&EnergyPanel.totalCount==0&&p.hand.size()==2&&p.hasPower("Artifact"),"Surrender+ enters permanent form, draws two, spends three, and does not spend Artifact");check(VoidformRelic.effectiveCost(new BloodMagic(),2)==0&&new BloodMagic().cost==2,"Surrender previews fixed-cost Attacks as free without mutating their base cost");play(new BloodMagic(),m,0);});
        q.add(()->{check(EnergyPanel.totalCount==0&&m.currentHealth==9978,"A two-cost Attack plays for zero and receives the Voidform damage bonus");relic.onPlayerEndTurn();check(VoidformRelic.isPermanent()&&VoidformRelic.effectiveCost(new BloodMagic(),2)==0,"Free fixed-cost Attacks persist past turn end");p.powers.add(new HastePower(p,1));p.relics.add(new com.megacrit.cardcrawl.relics.ChemicalX());play(new MindFlayInsanity(),m,2);});
        q.add(()->{check(EnergyPanel.totalCount==0&&VoidformRelic.effectiveCost(new MindFlayInsanity(),-1)==-1,"Surrender leaves X-cost spending intact with Haste and Chemical X");check(!new SurrenderToMadness().canUse(p,null),"Additional Surrenders remain unplayable");p.powers.removeIf(power->power instanceof HastePower);int hp=p.currentHealth;DamageInfo hit=new DamageInfo(m,10,DamageInfo.DamageType.NORMAL);hit.applyPowers(m,p);p.damage(hit);check(p.currentHealth==hp-20,"Surrender still doubles enemy Attack damage");reset();p.relics.remove(relic);play(plus(new SurrenderToMadness()),null,3);});
        q.add(()->{check(VoidformRelic.isPermanent()&&new VoidformPower(p,true).getAlternateResource(new BloodMagic())==2,"Surrender's power supplies the full discount after starter removal");play(new BloodMagic(),m,0);});
        q.add(()->{check(m.currentHealth==9978&&EnergyPanel.totalCount==0,"Starterless Surrender makes two-cost Attacks playable for free");reset();dots(m);p.currentHealth=34;
            AbstractCard[] cards={plus(new MindBlast()),plus(new ShadowWordTorment()),plus(new ShadowWordDeath()),plus(new Bulwark()),plus(new PurifyDisease()),plus(new MindSoothe()),plus(new MentalFortitude()),plus(new Serendipity()),plus(new SurrenderToMadness())};
            for(AbstractCard c:cards){c.applyPowers();if(c.target==AbstractCard.CardTarget.ENEMY)c.calculateCardDamage(m);CardArtReview.card(c,"low-pick-"+c.cardID.split(":")[1]);}
        });
    }
}
