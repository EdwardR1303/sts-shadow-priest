package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.*;
import shadowpriest.mechanics.*;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused 0.1.2 integration checks; no full-run simulations. */
public final class BetaBalanceSmokeTest {
    private static AbstractPlayer p; private static AbstractMonster m;
    private static Catharsis held,drawn; private static VoidVolley volley; private static int health;
    private static void reset(){
        p.powers.clear();p.loseBlock();p.currentHealth=p.maxHealth=500;
        p.relics.removeIf(r->!r.relicId.equals(VoidformRelic.ID));VoidformRelic.current().reset();
        for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
        for(int i=0;i<20;i++)p.drawPile.addToBottom(new MindSpike());
        m.powers.clear();m.loseBlock();m.currentHealth=m.maxHealth=10000;
        EnergyPanel.totalCount=3;PlayerTurnWindow.active=true;
    }
    private static void play(AbstractCard c,AbstractMonster target,int energy){
        p.hand.removeCard(c);p.hand.addToTop(c);EnergyPanel.totalCount=energy;c.energyOnUse=energy;
        c.applyPowers();if(target!=null)c.calculateCardDamage(target);
        check(c.canUse(p,target),"Playable: "+c.name);p.useCard(c,target,energy);
    }
    private static <T extends AbstractCard>T plus(T c){c.upgrade();return c;}
    private static void dots(){m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,5));m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,5));m.powers.add(new MadnessPower(m,10));}
    private static int stat(String id){return p.hasPower(id)?p.getPower(id).amount:0;}
    private static void endBuffs(){for(AbstractPower power:new java.util.ArrayList<>(p.powers))power.atEndOfTurn(true);}
    private static void render(AbstractCard c,String name){c.applyPowers();CardArtReview.card(c,name);}
    public static void append(Queue<Runnable> q){
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);reset();dots();p.powers.add(new StrengthPower(p,9));p.powers.add(new SearingNightmarePower(p,2));
            Singularity c=plus(new Singularity());c.calculateCardDamage(m);check(c.previewBonus(m)==100,"Singularity preview excludes initial hit, Strength and pre-hit Nightmare stacks");play(c,m,2);});
        q.add(()->{check(m.currentHealth==9900 && MadnessPower.stacks(m)==2 && !m.hasPower(ShadowDotPower.TOUCH),"Singularity consumes 20 stacks, hits once, then reapplies two Madness");check(VoidformRelic.current().state.amount()==10,"Singularity generates one hit of Insanity");reset();play(new Singularity(),m,2);});
        q.add(()->{check(m.currentHealth==10000 && VoidformRelic.current().state.amount()==0,"Unprepared Singularity deals no damage and generates no Insanity");reset();dots();p.powers.add(new HastePower(p,2));p.powers.add(new SearingNightmarePower(p,2));MindFlay c=new MindFlay();c.applyPowers();check(c.rawDescription.contains("shadowpriest:Repeats"),"Mind Flay text uses dynamic repetitions: "+c.rawDescription);check(new Repetitions.CountVariable().value(c)==5 && new Repetitions.CountVariable().isModified(c),"Haste repetition count is five and marked green");play(c,m,1);});
        q.add(()->{check(m.currentHealth==9990 && MadnessPower.stacks(m)==20 && VoidformRelic.current().state.amount()==50,"Mind Flay adds two hits exactly once with per-hit Nightmare and Insanity");reset();p.powers.add(new HastePower(p,2));play(new com.megacrit.cardcrawl.cards.green.DaggerSpray(),null,1);});
        q.add(()->{check(m.currentHealth==9984,"Dagger Spray deals four hits with two Haste");reset();p.powers.add(new HastePower(p,2));play(new com.megacrit.cardcrawl.cards.red.Pummel(),m,1);});
        q.add(()->{check(m.currentHealth==9988,"Pummel gains two repetitions without replacing its final hit");reset();p.powers.add(new HastePower(p,2));play(plus(new ShadowWordDeath()),m,0);});
        q.add(()->{check(m.currentHealth==9960 && p.currentHealth==488,"Death+ repeats four times including each self-damage payment");reset();p.currentHealth=400;p.powers.add(new HastePower(p,2));play(new Penance(),null,1);});
        q.add(()->{check(p.currentHealth==415 && p.exhaustPile.group.stream().anyMatch(c->c instanceof Penance),"Penance heals five times and Exhausts");reset();p.powers.add(new HastePower(p,3));play(new Halo(),null,1);});
        q.add(()->{check(p.powers.stream().anyMatch(power->power instanceof HaloPower && power.amount==3),"Halo keeps three turns despite Haste");reset();play(new PowerInfusion(),null,1);});
        q.add(()->{check(stat(HastePower.POWER_ID)==2 && stat(PowerInfusionPower.POWER_ID)==2,"Infusion grants Haste and two decay skips");endBuffs();});
        q.add(()->{check(stat(HastePower.POWER_ID)==2 && stat(PowerInfusionPower.POWER_ID)==1,"First protected turn does not decay");endBuffs();});
        q.add(()->{check(stat(HastePower.POWER_ID)==2 && !p.hasPower(PowerInfusionPower.POWER_ID),"Second protected turn does not decay");endBuffs();});
        q.add(()->{check(stat(HastePower.POWER_ID)==1,"Normal decay resumes on third end turn");play(new PowerInfusion(),null,1);});
        q.add(()->{play(new PowerInfusion(),null,1);});
        q.add(()->{check(stat(HastePower.POWER_ID)==5 && stat(PowerInfusionPower.POWER_ID)==2,"Infusion stacks Haste but only refreshes protection to two");reset();held=new Catharsis();drawn=new Catharsis();p.hand.addToTop(held);p.drawPile.addToTop(drawn);p.powers.add(new TitheEvasionPower(p,2));p.addBlock(3);health=p.currentHealth;p.damage(new DamageInfo(m,4,DamageInfo.DamageType.THORNS));check(p.currentBlock==0 && p.currentHealth==health && held.storedDamage==0,"Tithe spends Block before preventing HP loss and Catharsis gain");p.damage(new DamageInfo(p,7,DamageInfo.DamageType.HP_LOSS));check(held.storedDamage==25 && drawn.storedDamage==15 && p.currentHealth==495,"Catharsis tracks actual HP loss per copy at five or three");p.heal(5);check(held.storedDamage==25,"Healing preserves Catharsis damage");check(((Catharsis)held.makeStatEquivalentCopy()).storedDamage==25 && new Catharsis().storedDamage==0,"Temporary copies preserve stored damage; fresh copies start at zero");PlayerTurnWindow.active=false;p.damage(new DamageInfo(m,3,DamageInfo.DamageType.NORMAL));check(p.currentHealth==497 && held.storedDamage==40,"Tithe does not reduce enemy-turn damage");PlayerTurnWindow.active=true;play(new TitheEvasion(),null,1);});
        q.add(()->{EnergyPanel.totalCount=1;check(stat(TitheEvasionPower.POWER_ID)==3 && new TitheEvasion().canUse(p,null),"Tithe stacks and additional copies remain playable");reset();p.powers.add(new HastePower(p,2));p.powers.add(new DexterityPower(p,7));p.powers.add(new ProtectedChannelPower(p,3));play(new MindSear(),null,0);});
        q.add(()->{check(p.currentBlock==6 && m.currentHealth==9988,"Protected Channel uses effective X once and ignores Dexterity");reset();p.powers.add(new ArtifactPower(p,1));play(new SurrenderToMadness(),null,3);});
        q.add(()->{check(VoidformRelic.isPermanent() && p.hasPower("Artifact") && p.getPower(SurrenderExposurePower.POWER_ID).type==AbstractPower.PowerType.BUFF,"Surrender's penalty is a buff and leaves Artifact intact");p.powers.add(new WeakPower(p,2,false));m.powers.add(new StrengthPower(m,4));m.powers.add(new ArtifactPower(m,2));m.addBlock(20);MassDispel c=plus(new MassDispel());check(c.cost==2,"Mass Dispel+ retains two energy cost");play(c,null,2);});
        q.add(()->{check(!p.hasPower("Weakened") && p.hasPower(SurrenderExposurePower.POWER_ID) && !m.hasPower("Strength") && !m.hasPower("Artifact") && m.currentBlock==0,"Mass Dispel+ cleanses debuffs and enemy defenses but preserves Surrender");health=p.currentHealth;DamageInfo hit=new DamageInfo(m,10,DamageInfo.DamageType.NORMAL);hit.applyPowers(m,p);p.damage(hit);check(p.currentHealth==health-20,"Cleansed Surrender still doubles enemy Attack damage");reset();p.powers.add(new HastePower(p,2));volley=plus(new VoidVolley());p.hand.addToTop(volley);VoidformRelic.current().enterImmediately();});
        q.add(()->{check(m.currentHealth==9905 && EnergyPanel.totalCount==3 && p.discardPile.contains(volley),"Voidform automatically plays five upgraded bolts for 95 damage without energy cost");check(AbstractDungeon.actionManager.cardsPlayedThisTurn.contains(volley),"Free Volley counts as a real Attack play");reset();p.relics.add(new IdolOfYoggSaron());volley=new VoidVolley();p.hand.addToTop(volley);EnergyPanel.totalCount=0;VoidformRelic.current().enterImmediately();});
        q.add(()->{check(m.currentHealth==9955 && EnergyPanel.totalCount==3,"Volley uses entry energy from Yogg once it resolves");reset();PetrifyingScream c=plus(new PetrifyingScream());check(c.cost==2 && c.target==AbstractCard.CardTarget.ALL_ENEMY && c.exhaust,"Scream+ upgrades to all enemies at two energy with Exhaust");play(c,null,2);});
        q.add(()->{check(m.hasPower("stslib:Stunned"),"Scream+ applies Stun");reset();check(new VoidBolt().baseDamage==10 && plus(new VoidBolt()).baseDamage==13,"Void Bolt damage is 10/13");check(new EncroachingShadows().baseBlock==6 && plus(new EncroachingShadows()).baseBlock==9,"Encroaching Shadows Block is 6/9");check(new Halo().magicNumber==4 && plus(new Halo()).magicNumber==6,"Halo damage is 4/6");check(new SearingNightmare().magicNumber==2,"Searing Nightmare applies two Madness per hit");dots();Insight insight=new Insight();insight.calculateCardDamage(m);p.powers.add(new HastePower(p,4));check(insight.rawDescription.contains("(3 cards)") && Repetitions.base(insight)==-1,"For-each draw preview is unchanged by Haste");VoidShield shield=new VoidShield();VoidformRelic.current().gainInsanity(50);shield.applyPowers();check(shield.rawDescription.contains("(10 total Block)"),"Void Shield shows evaluated total");
            render(plus(new VoidVolley()),"balance-volley-plus");render(plus(new Catharsis()),"balance-catharsis-plus");render(new MindFlay(),"balance-mind-flay-haste");render(new com.megacrit.cardcrawl.cards.green.DaggerSpray(),"balance-dagger-spray-haste");
        });
    }
}
