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
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Small deterministic combat scenarios for the November balance pass, not a full-run simulation. */
public final class NovemberBalanceSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m,n;
    private static VoidformRelic relic;
    private static AbstractCard played,first,second;
    private static void reset(){
        p.powers.clear();p.loseBlock();p.maxHealth=200;p.currentHealth=100;
        p.relics.removeIf(r->!r.relicId.equals(VoidformRelic.ID));relic.reset();
        for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
        for(int i=0;i<20;i++)p.drawPile.addToTop(new MindSpike());
        for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters){enemy.powers.clear();enemy.loseBlock();enemy.maxHealth=enemy.currentHealth=10000;}
        EnergyPanel.totalCount=3;
    }
    private static <T extends AbstractCard>T up(T c){c.upgrade();return c;}
    private static void curse(){p.hand.addToTop(new MindDecay());}
    private static void play(AbstractCard c,AbstractMonster target,int energy){
        played=c;p.hand.addToTop(c);c.energyOnUse=energy;EnergyPanel.totalCount=energy;
        c.applyPowers();if(target!=null)c.calculateCardDamage(target);
        check(c.canUse(p,target),"Playable: "+c.name);p.useCard(c,target,energy);
    }
    private static int stat(String id){return p.hasPower(id)?p.getPower(id).amount:0;}
    private static int dot(AbstractMonster enemy,String id){return enemy.hasPower(id)?enemy.getPower(id).amount:0;}
    private static void dots(){m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,3));m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,3));m.powers.add(new MadnessPower(m,10));}
    private static void channel(){p.powers.add(new ProtectedChannelPower(p,3));}
    /** Isolated regression for the Touch/Pain-only bonus ticks; avoids replaying the full balance suite. */
    public static void appendFlayChecks(Queue<Runnable> q){
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);relic=VoidformRelic.current();reset();
            dots();m.getPower(MadnessPower.POWER_ID).amount=100;p.powers.add(new VampiricEmbracePower(p,1));channel();play(new MindFlayInsanity(),m,2);});
        q.add(()->{check(m.currentHealth==9972,"Base Flay: two Attack hits plus Touch/Pain ticks; no damage from 102 Madness");
            check(dot(m,MadnessPower.POWER_ID)==102&&dot(m,ShadowDotPower.TOUCH)==5&&dot(m,ShadowDotPower.PAIN)==5,"All existing stacks still increase; bonus ticks consume none");
            check(p.currentHealth==102&&p.currentBlock==6&&relic.state.amount()==24,"Only Touch/Pain ticks grant healing/Insanity; Channel counts two primary ticks");
            reset();dots();m.getPower(MadnessPower.POWER_ID).amount=100;p.powers.add(new HastePower(p,1));p.relics.add(new com.megacrit.cardcrawl.relics.ChemicalX());p.powers.add(new MasteryPower(p,2));p.powers.add(new VampiricEmbracePower(p,1));channel();play(up(new MindFlayInsanity()),m,0);});
        q.add(()->{check(m.currentHealth==9928&&dot(m,MadnessPower.POWER_ID)==104,"Upgraded zero-Energy Flay counts Haste/Chemical X once and never ticks Madness");
            check(p.currentHealth==104&&p.currentBlock==12&&relic.state.amount()==48,"Mastery affects Touch/Pain; four repeated heals and Channel grants remain");
            reset();m.powers.add(new MadnessPower(m,100));play(new MindFlayInsanity(),m,2);});
        q.add(()->{check(m.currentHealth==9988&&dot(m,MadnessPower.POWER_ID)==102&&ShadowDotPower.count(m)==1,"Madness-only target takes Attack damage only; no missing DoTs created");
            check(relic.state.amount()==20,"Madness-only target grants no bonus-tick Insanity");((MadnessPower)m.getPower(MadnessPower.POWER_ID)).naturalTick();});
        q.add(()->{check(m.currentHealth==9937&&dot(m,MadnessPower.POWER_ID)==101,"Natural Madness damage and decay remain unchanged");
            for(AbstractCard c:new AbstractCard[]{new MindFlayInsanity(),up(new MindFlayInsanity())}){c.applyPowers();CardArtReview.card(c,"flay-touch-pain"+(c.upgraded?"-plus":""));}});
    }
    public static void append(Queue<Runnable> q){
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);relic=VoidformRelic.current();reset();n=new Cultist(450,0);AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(n,false));});
        q.add(()->{reset();values();curse();play(new TentacleSlam(),m,2);});
        q.add(()->{check(m.hasPower("stslib:Stunned")||m.powers.stream().anyMatch(x->x.name.equals("Stunned")),"Tentacle Slam Devour stuns");check(EnergyPanel.totalCount==0&&m.currentHealth==9992,"Slam no longer refunds Energy");reset();curse();m.powers.add(new ArtifactPower(m,1));play(new TentacleSlam(),m,2);});
        q.add(()->{check(!m.hasPower(ShadowDotPower.TOUCH)&&m.powers.stream().anyMatch(x->x.name.equals("Stunned")),"Touch consumes Artifact before the separate stun");reset();curse();play(new UnfurlingDarkness(),m,1);});
        q.add(()->{check(m.currentHealth==9993&&n.currentHealth==9993&&dot(n,ShadowDotPower.TOUCH)==3,"Unfurling hits and applies Touch to both enemies once");check(relic.state.amount()==30,"Unfurling gives explicit ten once plus ten per hit");reset();p.powers.add(new DexterityPower(p,4));p.addBlock(5);p.powers.add(new BarrierPower(p,5));curse();play(new FocusedWill(),null,1);});
        q.add(()->{check(p.currentBlock==17&&BarrierPower.remaining(p)==17,"Focused Will converts old and new Block without duplicating it or Dexterity");p.addBlock(5);p.damage(new DamageInfo(m,3,DamageInfo.DamageType.NORMAL));check(p.currentBlock==19&&BarrierPower.remaining(p)==14,"New ordinary Block leaves reserve alone; damage spends Barrier first");reset();m.addBlock(23);p.powers.add(new DexterityPower(p,9));p.powers.add(new FrailPower(p,2,false));VoidShield shift=up(new VoidShield());shift.calculateCardDamage(m);check(shift.rawDescription.contains("23 Block")&&shift.selfRetain,"Void Shift previews transfer and upgrade Retains");play(shift,m,1);});
        q.add(()->{check(p.currentBlock==23&&m.currentBlock==0,"Void Shift transfers exactly despite Dexterity and Frail");reset();curse();play(up(new Insight()),m,0);});
        q.add(()->{check(p.discardPile.contains(played)&&!p.exhaustPile.contains(played),"Devoured Insight+ avoids printed Exhaust");check(p.hand.size()==1&&!new Insight().canUse(p,m),"Insight+ draws one at zero DoTs and locks all copies");reset();play(up(new Insight()),m,0);});
        q.add(()->{check(p.exhaustPile.contains(played),"Insight+ without Curse still Exhausts");reset();curse();Insight forced=new Insight();forced.exhaustOnUseOnce=true;play(forced,m,0);});
        q.add(()->{check(p.exhaustPile.contains(played),"Devour does not cancel externally forced Exhaust");reset();curse();p.powers.add(new CorruptionPower(p));play(new Insight(),m,0);});
        q.add(()->{check(p.exhaustPile.contains(played),"Corruption still Exhausts a Devoured Insight");reset();p.powers.add(new DarkReinforcementPower(p,true));p.powers.add(new VampiricEmbracePower(p,2));curse();play(new FocusedWill(),null,1);});
        q.add(()->{check(p.currentHealth==102&&EnergyPanel.totalCount==1&&p.hand.size()==1&&MasteryPower.current()==0,"Devour grants Embrace healing and upgraded Reinforcement Energy/draw, no Mastery");reset();p.powers.add(new DarkReinforcementPower(p,true));p.drawPile.addToTop(new MindDecay());EnergyPanel.totalCount=0;AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));});
        q.add(()->{check(EnergyPanel.totalCount==1&&p.hand.size()==2&&MasteryPower.current()==0,"Actual Curse draw still grants Energy/draw without Mastery");reset();play(new Atonement(),m,1);});
        q.add(()->{check(dot(m,AtonementPower.POWER_ID)==3&&p.exhaustPile.contains(played),"Atonement applies three and Exhausts");play(up(new Atonement()),m,1);});
        q.add(()->{check(dot(m,AtonementPower.POWER_ID)==8&&p.currentBlock==3,"Atonement stacks; repeat hit triggers prior amount");m.powers.add(new ArtifactPower(m,1));play(new Atonement(),m,1);});
        q.add(()->{check(dot(m,AtonementPower.POWER_ID)==8&&!m.hasPower("Artifact"),"Artifact blocks each new Atonement application");reset();channel();play(new MindSear(),null,3);});
        q.add(()->{check(p.currentBlock==9&&dot(m,MadnessPower.POWER_ID)==6&&dot(n,MadnessPower.POWER_ID)==6,"Three Sear waves grant three Channel ticks, not six, and Madness per hit");reset();channel();play(up(new DarkEvangelism()),null,3);});
        q.add(()->{check(p.currentBlock==12&&MasteryPower.current()==4,"Dark Evangelism+ X+1 Mastery units trigger four Channel grants");reset();channel();p.powers.add(new DexterityPower(p,4));play(up(new CrystallineReflection()),null,3);});
        q.add(()->{check(p.currentBlock==45,"Reflection+ grants (8+4)*3 plus Channel 3*3, no extra Thorns ticks or Channel Dexterity");reset();channel();p.powers.add(new HastePower(p,2));p.relics.add(new com.megacrit.cardcrawl.relics.ChemicalX());play(up(new MindSear()),null,0);});
        q.add(()->{check(p.currentBlock==15&&dot(m,MadnessPower.POWER_ID)==10,"Zero-energy Sear+ counts Haste/Chemical X once plus printed extra wave");reset();dots();p.powers.add(new VampiricEmbracePower(p,1));channel();play(new MindFlayInsanity(),m,2);});
        q.add(()->{check(m.currentHealth==9972&&dot(m,ShadowDotPower.TOUCH)==5&&dot(m,ShadowDotPower.PAIN)==5&&dot(m,MadnessPower.POWER_ID)==12,"Insanity adds all stacks then hits and ticks only Touch/Pain without decay");check(p.currentHealth==102&&p.currentBlock==6&&relic.state.amount()==24,"Bonus Touch heals, ticks grant Insanity, Channel counts primary repetitions only");reset();play(up(new PowerInfusion()),null,0);});
        q.add(()->{check(stat(HastePower.POWER_ID)==2&&stat(PowerInfusionPower.POWER_ID)==3,"Infusion+ protects three decay boundaries for zero Energy");play(new PowerInfusion(),null,0);});
        q.add(()->{check(stat(HastePower.POWER_ID)==4&&stat(PowerInfusionPower.POWER_ID)==3,"Base recast adds Haste without shortening upgraded protection");p.getPower(HastePower.POWER_ID).atEndOfTurn(true);p.getPower(PowerInfusionPower.POWER_ID).atEndOfTurn(true);});
        q.add(()->{check(stat(HastePower.POWER_ID)==4&&stat(PowerInfusionPower.POWER_ID)==2,"Protected turn skips decay and consumes one protection");reset();play(new Halo(),null,1);});
        q.add(()->{for(AbstractPower power:p.powers)if(power instanceof HaloPower)power.atEndOfTurn(true);});
        q.add(()->{check(p.currentHealth==102&&m.currentHealth==9996&&n.currentHealth==9996,"Halo heals once per pulse rather than per target");reset();play(up(new BodyAndSoul()),null,2);});
        q.add(()->{EnergyPanel.totalCount=2;check(up(new BodyAndSoul()).canUse(p,null),"Further Body and Soul copies remain playable");play(up(new BodyAndSoul()),null,2);});
        q.add(()->{check(Math.abs(((BodyAndSoulPower)p.getPower(BodyAndSoulPower.POWER_ID)).missChance()-.51)<.00001,"Two upgraded dodge copies give 51 percent");play(new BodyAndSoul(),null,2);});
        q.add(()->{check(Math.abs(((BodyAndSoulPower)p.getPower(BodyAndSoulPower.POWER_ID)).missChance()-.608)<.00001,"Adding base dodge gives 60.8 percent");reset();p.powers.add(new MasteryPower(p,-3));p.powers.add(new StrengthPower(p,-2));p.powers.add(new DexterityPower(p,-2));p.powers.add(new SurrenderDeadlinePower(p,2));m.powers.add(new StrengthPower(m,5));n.powers.add(new StrengthPower(n,-2));m.addBlock(30);m.powers.add(new ArtifactPower(m,2));play(up(new MassDispel()),null,1);});
        q.add(()->{check(MasteryPower.current()==0&&stat("Strength")==0&&stat("Dexterity")==0&&p.hasPower(SurrenderDeadlinePower.POWER_ID),"Mass Dispel removes negative stats but keeps Surrender");check(m.currentBlock==0&&!m.hasPower("Strength")&&!m.hasPower("Artifact")&&dot(n,"Strength")==-2,"Mass Dispel removes enemy benefits and preserves negative Strength");reset();setupEntry(true);});
        q.add(()->{check(MasteryPower.current()==0&&stat("Strength")==1&&m.currentHealth+n.currentHealth==19984,"Left Committal activates before Volley and rewards its Attack after damage");check(!p.hand.contains(first)&&!p.hand.contains(second)&&EnergyPanel.totalCount==1,"Both automatic cards play once without spending Energy");reset();setupEntry(false);});
        q.add(()->{check(stat("Strength")==0&&p.hasPower(ShadowCommittalPower.POWER_ID),"Left Volley resolves before right Committal and receives no retroactive stat");reset();play(up(new ShadowMend()),null,1);});
        q.add(()->{check(p.currentHealth==114&&stat(ShadowMendDebtPower.POWER_ID)==2,"Mend+ heals fourteen but preserves two debt");reset();play(new DarkVoid(),null,1);});
        q.add(()->{check(p.hand.group.stream().anyMatch(c->c instanceof MindDecay)&&m.currentHealth==9994,"Dark Void creates Mind Decay in hand and deals six");check(shadowpriest.actions.DarkRevelationAction.eligible(new MindSear()),"Tutor accepts new direct Madness applicator Mind Sear");reset();new Valanyr().equip(p);});
        q.add(()->{check(p.currentBlock==0&&p.hasPower("Barricade")&&p.hasPower(GuardianAngelPower.POWER_ID),"Base Valanyr grants permanent retention and independent Guardian Angel without opening Block");
            reset();channel();p.powers.add(new HastePower(p,2));play(up(new com.megacrit.cardcrawl.cards.green.Malaise()),m,0);
        });
        q.add(()->{check(p.currentBlock==9&&dot(m,"Strength")==-3&&dot(m,"Weakened")==3,"Off-color Malaise+ counts X+1 once, not both debuffs, and works at zero energy");visuals();});
    }
    private static void setupEntry(boolean committalFirst){
        first=committalFirst?up(new ShadowCommittal()):new VoidVolley();
        second=committalFirst?new VoidVolley():up(new ShadowCommittal());
        p.hand.group.add(first);p.hand.group.add(second);p.hand.refreshHandLayout();EnergyPanel.totalCount=1;
        relic.enterImmediately();
    }
    private static void values(){
        check(new MindFlay().baseDamage==3&&up(new MindBlast()).baseDamage==14&&up(new MindBlast()).baseMagicNumber==5,"Flay/Blast accepted damage values");
        check(new VoidBolt().baseDamage==11&&up(new VoidVolley()).baseDamage==15,"Bolt and Volley share buffed values");
        check(new ShadowWordMadness().baseDamage==10&&up(new ShadowWordMadness()).baseDamage==15,"Madness Attack damage values");
        check(up(new Fade()).baseMagicNumber==4&&up(new VoidTendrils()).baseBlock==8&&up(new EncroachingShadows()).baseMagicNumber==11,"Defensive numeric buffs");
        check(new ProtectedChannel().cost==1&&up(new SearingNightmare()).cost==1&&up(new SearingNightmare()).magicNumber==4,"Channel and Nightmare costs and values");
        check(new TitheEvasion().magicNumber==2&&up(new TitheEvasion()).cost==0&&up(new TitheEvasion()).magicNumber==2,"Tithe upgrades cost, not prevention");
    }
    private static void visuals(){
        for(AbstractCard c:new AbstractCard[]{up(new ShadowCommittal()),up(new MindFlayInsanity()),new ProtectedChannel(),up(new Insight()),new TentacleSlam(),new VoidShield()}){
            c.applyPowers();CardArtReview.card(c,"november-"+c.cardID.split(":")[1]);
        }
    }
}
