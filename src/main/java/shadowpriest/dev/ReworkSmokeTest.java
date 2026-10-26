package shadowpriest.dev;

import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.patches.CustomTargeting;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.red.Whirlwind;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.mechanics.*;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Regression scenarios through actual engine card plays, targeting, hooks and turn transitions. */
public final class ReworkSmokeTest {
 private static AbstractPlayer p;
 private static AbstractMonster m, other;
 private static VoidformRelic relic;
 private static AbstractCard card;
 private static int hp;
 private static int stacks(AbstractMonster t,String id){return t.hasPower(id)?t.getPower(id).amount:0;}
 private static int stat(String id){return p.hasPower(id)?p.getPower(id).amount:0;}
 private static <T extends AbstractCard> T up(T c){c.upgrade();return c;}
 private static void reset(){
  p.powers.clear();p.currentHealth=200;p.maxHealth=500;p.currentBlock=0;
  p.hand.clear();p.drawPile.clear();p.discardPile.clear();p.exhaustPile.clear();p.limbo.clear();
  p.relics.removeIf(r->r.relicId.equals("Chemical X"));
  for(AbstractMonster t:AbstractDungeon.getMonsters().monsters){t.powers.clear();t.currentHealth=t.maxHealth=1000;t.currentBlock=0;}
  relic.reset();ReturningDots.clear();p.gameHandSize=p.masterHandSize;
 }
 private static void dots(AbstractMonster t){t.powers.add(new ShadowDotPower(t,ShadowDotPower.TOUCH,1));t.powers.add(new ShadowDotPower(t,ShadowDotPower.PAIN,1));}
 private static void allDots(AbstractMonster t){dots(t);t.powers.add(new MadnessPower(t,6));}
 private static void play(AbstractCard c,AbstractMonster target,int energy){
  EnergyPanel.totalCount=energy;c.energyOnUse=energy;p.drawPile.removeCard(c);p.discardPile.removeCard(c);p.hand.addToTop(c);
  if(!c.canUse(p,target))throw new AssertionError("Cannot play "+c.name+": "+c.cantUseMessage);
  p.useCard(c,target,energy);
 }
 private static void nextTurn(){
  for(AbstractMonster t:AbstractDungeon.getMonsters().monsters){t.setMove((byte)0,AbstractMonster.Intent.NONE);t.createIntent();}
  for(int i=0;i<10;i++)p.drawPile.addToTop(new PowerWordShield());
  AbstractDungeon.getCurrRoom().skipMonsterTurn=false;
  AbstractDungeon.overlayMenu.endTurnButton.disable(true);p.endTurnQueued=true;
 }
 public static void append(Queue<Runnable> q){
 q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);other=AbstractDungeon.getMonsters().monsters.get(1);relic=VoidformRelic.current();reset();
  check(AbstractDungeon.commonCardPool.size()==21&&AbstractDungeon.uncommonCardPool.size()==19&&AbstractDungeon.rareCardPool.size()==11,"Reworked reward pools contain 21 commons, 19 uncommons and 11 rares");
  for(String id:new String[]{"MindFlayInsanity","MindSear","VoidShield","VoidBolt","VoidVolley","VoidBlast","MindSpike","VampiricTouch","MindFlay","MindBlast","Penance","Insight","ShadowWordMadness","DispelMagic","InsidiousIre","UnfurlingDarkness","DarkVoid","PowerInfusion","ShadowCrash","CrystallineReflection","ProtectedChannel","Catharsis","SearingNightmare","DarkEvangelism"}){
   AbstractCard c=CardLibrary.getCard("shadowpriest:"+id);check(c!=null,id+" registered");c=c.makeCopy();c.upgrade();AbstractCard copy=c.makeStatEquivalentCopy();
   com.badlogic.gdx.graphics.Texture art=CustomCard.getPortraitImage((CustomCard)c);
   check(c.portrait.getRegionWidth()==250&&art.getWidth()==500&&art.getHeight()==380,id+" has both artwork sizes");
   CardArtReview.card(c,id);
   check(copy.upgraded&&copy.cost==c.cost&&copy.baseDamage==c.baseDamage&&copy.magicNumber==c.magicNumber&&copy.exhaust==c.exhaust,id+" preserves upgrade in copies");
  }
  CardArtReview.powerIcon(new HastePower(p,2),"haste-icon");
  play(new MindSpike(),m,3);
 });
 q.add(()->{check(relic.state.amount()==10&&m.currentHealth==994,"Mind Spike grants ten Insanity without DoTs");reset();dots(m);m.currentBlock=50;play(new MindSpike(),m,3);});
 q.add(()->{check(relic.state.amount()==20&&m.currentHealth==1000,"Blocked Mind Spike adds explicit ten to two-DoT hit generation");reset();dots(m);play(new MindFlay(),m,3);});
 q.add(()->{check(m.currentHealth==994&&relic.state.amount()==30,"Mind Flay generates five per hit per DoT");check(stacks(m,ShadowDotPower.TOUCH)==4&&stacks(m,ShadowDotPower.PAIN)==4,"Mind Flay extends after every hit");reset();allDots(m);m.powers.add(new ArtifactPower(m,2));play(up(new MindFlay()),m,3);});
 q.add(()->{check(stacks(m,ShadowDotPower.TOUCH)==5&&stacks(m,ShadowDotPower.PAIN)==5&&MadnessPower.stacks(m)==6&&stacks(m,"Artifact")==2,"Flay+ caps existing Touch/Pain without changing Madness or consuming Artifact");check(relic.state.amount()==60,"Four hits against three DoTs generate sixty Insanity");reset();play(new MindFlay(),m,3);});
 q.add(()->{check(ShadowDotPower.count(m)==0,"Mind Flay never creates missing DoTs");reset();card=up(new VampiricTouch());play(card,m,0);});
 q.add(()->{check(EnergyPanel.totalCount==0&&stacks(m,ShadowDotPower.TOUCH)==3,"VT+ costs zero and applies three stacks");nextTurn();});
 q.add(()->{check(p.hand.contains(card)&&m.currentHealth==995&&stacks(m,ShadowDotPower.TOUCH)==2,"VT+ ticks once during a real turn boundary and returns to hand");reset();p.powers.add(new MasteryPower(p,2));m.currentBlock=100;m.powers.add(new MadnessPower(m,6));DotTiming.tick();});
 q.add(()->{check(m.currentHealth==995&&m.currentBlock==100&&MadnessPower.stacks(m)==5&&relic.state.amount()==0,"Madness uses half rounded up plus Mastery, bypasses Block, removes one and generates no Insanity");p.powers.clear();p.powers.add(new MasteryPower(p,-99));DotTiming.tick();});
 q.add(()->{check(m.currentHealth==995&&MadnessPower.stacks(m)==4,"Negative Mastery floors Madness damage at zero without slowing decay");reset();m.powers.add(new MadnessPower(m,1));p.powers.add(new MasteryPower(p,9));m.powers.add(new IntangiblePower(m,1));DotTiming.tick();});
 q.add(()->{check(m.currentHealth==999&&!m.hasPower(MadnessPower.POWER_ID),"Intangible limits Madness to one and final stack expires");reset();m.currentBlock=2;play(new ShadowWordMadness(),m,3);});
 q.add(()->{check(m.currentHealth==996&&MadnessPower.stacks(m)==4&&relic.state.amount()==0,"Madness attack applies only HP removed, after its hit, without immediate tick");reset();m.currentBlock=100;play(up(new ShadowWordMadness()),m,3);});
 q.add(()->{check(!m.hasPower(MadnessPower.POWER_ID),"Fully blocked Madness attack applies no stacks");reset();m.powers.add(new ArtifactPower(m,1));play(up(new ShadowWordMadness()),m,3);});
 q.add(()->{check(m.currentHealth==990&&!m.hasPower("Artifact")&&!m.hasPower(MadnessPower.POWER_ID),"Artifact prevents Madness application without preventing direct damage");reset();allDots(m);play(up(new MindBlast()),m,3);});
 q.add(()->{check(m.currentHealth==976,"Mind Blast+ deals twenty-four against three DoTs");reset();play(up(new UnfurlingDarkness()),m,0);});
 q.add(()->{check(m.currentHealth==995&&stacks(m,ShadowDotPower.TOUCH)==3,"Unfurling Darkness+ attacks and applies Touch at zero energy");reset();other.currentBlock=100;play(new DarkVoid(),null,1);});
 q.add(()->{check(m.currentHealth==997&&stacks(m,ShadowDotPower.PAIN)==3&&!other.hasPower(ShadowDotPower.PAIN),"Dark Void checks HP removed separately for each target");reset();play(up(new DarkVoid()),null,1);});
 q.add(()->{check(m.currentHealth==995&&other.currentHealth==995&&stacks(other,ShadowDotPower.PAIN)==5,"Dark Void+ deals five and applies five Pain to both targets");reset();p.powers.add(new StrengthPower(p,-10));play(new DarkVoid(),null,1);});
 q.add(()->{check(ShadowDotPower.count(m)==0,"Negative Strength can prevent Dark Void from applying Pain");reset();card=new Penance();CustomTargeting.setCardTarget(card,p);play(card,null,1);});
 q.add(()->{check(p.currentHealth==209&&p.exhaustPile.contains(card)&&m.currentHealth==1000,"Penance self targeting heals nine and Exhausts without enemy damage");reset();p.powers.add(new StrengthPower(p,10));relic.gainInsanity(100);relic.activate(relic.state.charge());});
 q.add(()->{card=up(new Penance());CustomTargeting.setCardTarget(card,p);play(card,null,0);});
 q.add(()->{check(p.currentHealth==212&&EnergyPanel.totalCount==0&&p.exhaustPile.contains(card),"Penance+ heals twelve independent of Strength and Voidform while receiving the Attack discount");reset();card=up(new Penance());CustomTargeting.setCardTarget(card,m);play(card,m,1);});
 q.add(()->{check(m.currentHealth==988&&p.currentHealth==200&&p.currentBlock==0&&p.exhaustPile.contains(card),"Penance enemy targeting deals four hits without healing or Block conversion");reset();p.powers.add(new PsychicLinkPower(p,1));dots(m);card=new Penance();CustomTargeting.setCardTarget(card,p);play(card,null,1);});
 q.add(()->{check(m.currentHealth==995&&p.currentHealth==209&&relic.state.amount()==0,"Healing Penance remains an Attack play for Psychic Link without hit Insanity");reset();p.powers.add(new DragonwrathPower(p,100));card=new Penance();CustomTargeting.setCardTarget(card,p);play(card,null,1);});
 q.add(()->{check(p.currentHealth==218&&m.currentHealth==1000,"Dragonwrath preserves Penance self-healing mode");reset();p.powers.add(new DragonwrathPower(p,100));card=new Penance();CustomTargeting.setCardTarget(card,m);play(card,null,1);});
 q.add(()->{check(m.currentHealth==982&&other.currentHealth==1000,"Dragonwrath preserves custom Penance enemy target");reset();m.currentBlock=50;m.powers.add(new ArtifactPower(m,3));play(up(new DispelMagic()),m,0);});
 q.add(()->{check(m.currentBlock==0&&!m.hasPower("Artifact")&&stat("Dexterity")==1&&MasteryPower.current()==1,"Dispel+ grants each stat once despite many Block/Artifact points");play(up(new DispelMagic()),m,0);});
 q.add(()->{check(stat("Dexterity")==1&&MasteryPower.current()==1,"Empty Dispel grants no further stats");reset();allDots(m);for(int i=0;i<6;i++)p.drawPile.addToTop(new PowerWordShield());card=up(new Insight());play(card,m,0);});
 q.add(()->{check(p.hand.size()==3&&p.discardPile.contains(card),"Shadowy Insight+ draws three and remains reusable");check(!new Insight().canUse(p,m)&&!up(new Insight()).canUse(p,m),"Insight lock is shared by base and upgraded copies");nextTurn();});
 q.add(()->{check(!p.hasPower(InsightLockPower.POWER_ID)&&up(new Insight()).canUse(p,m),"Insight lock expires on the next actual player turn");reset();play(new Insight(),m,0);});
 q.add(()->{check(p.hand.isEmpty()&&p.hasPower(InsightLockPower.POWER_ID),"Zero-draw Insight still consumes the shared turn allowance");reset();play(up(new PowerInfusion()),null,0);});
 q.add(()->{check(stat(HastePower.POWER_ID)==2&&EnergyPanel.totalCount==0,"Power Infusion+ grants two Haste at zero energy");play(new MindSear(),null,0);});
 q.add(()->{check(m.currentHealth==990&&other.currentHealth==990&&EnergyPanel.totalCount==0,"Haste enables zero-energy X Attack without spending energy");reset();p.powers.add(new HastePower(p,2));p.relics.add(new ChemicalX());play(new VoidTorrent(),m,0);});
 q.add(()->{check(m.currentHealth==960&&EnergyPanel.totalCount==0,"Haste plus Chemical X give Torrent four escalating hits at zero energy");reset();p.powers.add(new HastePower(p,2));play(new Whirlwind(),null,0);});
 q.add(()->{check(m.currentHealth==990&&other.currentHealth==990,"Haste also affects an off-color base-game X Attack");reset();p.powers.add(new HastePower(p,2));p.relics.add(new ChemicalX());p.powers.add(new DragonwrathPower(p,100));play(new VoidTorrent(),m,0);});
 q.add(()->{check(m.currentHealth==920,"Dragonwrath repeats the same effective X without applying Haste/Chemical X twice");reset();p.powers.add(new HastePower(p,2));p.powers.add(new DexterityPower(p,2));p.powers.add(new ProtectedChannelPower(p,3));play(new MindSear(),null,0);});
 q.add(()->{check(p.currentBlock==10&&m.currentHealth==990,"Protected Channel uses effective X at zero energy and Dexterity per instance");reset();p.powers.add(new HastePower(p,2));p.powers.add(new ProtectedChannelPower(p,3));play(up(new MindSear()),null,0);});
 q.add(()->{check(p.currentBlock==6&&m.currentHealth==985,"Printed X+1 upgrade adds an attack repetition but not Protected Channel Block");reset();p.powers.add(new HastePower(p,2));p.relics.add(new ChemicalX());play(up(new DarkEvangelism()),null,0);});
 q.add(()->{check(MasteryPower.current()==5&&EnergyPanel.totalCount==0,"Dark Evangelism+ includes Haste, Chemical X and upgraded bonus at zero energy");nextTurn();});
 q.add(()->{check(stat(HastePower.POWER_ID)==1&&MasteryPower.current()==5,"Haste halves at turn end while Mastery remains");reset();p.powers.add(new HastePower(p,2));p.powers.add(new DexterityPower(p,2));p.powers.add(new ArtifactPower(p,1));p.powers.add(new ThornsPower(p,4));play(new CrystallineReflection(),null,0);});
 q.add(()->{check(p.currentBlock==14&&stat(ReflectionThornsPower.POWER_ID)==2&&stat("Artifact")==1,"Reflection uses effective X for per-instance Dexterity and Artifact-safe temporary Thorns");p.damage(new DamageInfo(m,1,DamageInfo.DamageType.NORMAL));});
 q.add(()->{check(m.currentHealth==994&&p.currentHealth==200,"Temporary and permanent Thorns both retaliate on a blocked enemy Attack");nextTurn();});
 q.add(()->{check(!p.hasPower(ReflectionThornsPower.POWER_ID)&&stat("Thorns")==4&&stat("Artifact")==1,"Temporary Thorns expiry preserves permanent Thorns and Artifact");reset();play(new ShadowCrash(),null,0);});
 q.add(()->{check(ShadowDotPower.count(m)==0,"Base Shadow Crash does not apply immediately");nextTurn();});
 q.add(()->{check(stacks(m,ShadowDotPower.TOUCH)==5&&stacks(other,ShadowDotPower.TOUCH)==5&&m.currentHealth==1000,"Shadow Crash applies at next turn start without immediate damage or decay");reset();other.powers.add(new ArtifactPower(other,1));play(up(new ShadowCrash()),null,0);});
 q.add(()->{check(stacks(m,ShadowDotPower.TOUCH)==5&&!other.hasPower(ShadowDotPower.TOUCH)&&!other.hasPower("Artifact"),"Shadow Crash+ applies immediately and respects per-target Artifact");reset();play(new SearingNightmare(),null,3);});
 q.add(()->{check(m.currentHealth==985&&other.currentHealth==985&&MadnessPower.stacks(m)==15,"Searing Nightmare deals three hits and adds fifteen Madness without immediate ticks");check(relic.state.amount()==20,"Later Searing Nightmare hits count the Madness established by earlier repetitions");DotTiming.tick();});
 q.add(()->{check(m.currentHealth==977&&MadnessPower.stacks(m)==14,"Fifteen Madness ticks for eight then loses one stack");reset();p.powers.add(new HastePower(p,2));p.relics.add(new ChemicalX());play(up(new SearingNightmare()),null,0);});
 q.add(()->{check(m.currentHealth==975&&MadnessPower.stacks(m)==25,"Searing Nightmare+ combines Haste/Chemical X and its extra repetition at zero energy");reset();m.powers.add(new ArtifactPower(m,1));other.currentBlock=100;play(new SearingNightmare(),null,2);});
 q.add(()->{check(MadnessPower.stacks(m)==5&&MadnessPower.stacks(other)==10&&other.currentHealth==1000,"Nightmare applications check Artifact separately and do not require HP damage");reset();allDots(m);dots(other);play(up(new InsidiousIre()),null,1);});
 q.add(()->{check(!m.hasPower("Vulnerable"),"Ire has no immediate Vulnerable application");nextTurn();});
 q.add(()->{check(!m.hasPower("Vulnerable"),"Ire checks surviving DoTs after end-turn decay, not last-turn setup");reset();m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,3));m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,3));m.powers.add(new MadnessPower(m,6));p.powers.add(new InsidiousIrePower(p,1));nextTurn();});
 q.add(()->{check(stacks(m,"Vulnerable")==1&&!other.hasPower("Vulnerable"),"Ire applies Vulnerable only to an enemy with all three DoTs at player turn start");reset();allDots(m);m.powers.add(new ArtifactPower(m,1));new InsidiousIrePower(p,1).atStartOfTurn();});
 q.add(()->{check(!m.hasPower("Artifact")&&!m.hasPower("Vulnerable"),"Artifact blocks Ire's debuff");reset();p.currentHealth=450;p.powers.add(new StrengthPower(p,2));card=up(new Catharsis());play(card,m,2);});
 q.add(()->{check(m.currentHealth==948&&card.selfRetain&&p.exhaustPile.contains(card),"Catharsis+ uses missing HP plus Attack modifiers and has Retain/Exhaust");reset();relic.gainInsanity(15);p.powers.add(new DexterityPower(p,4));play(new VoidShield(),null,1);});
 q.add(()->{check(p.currentBlock==7,"Void Shield at fifteen Insanity grants three Block plus Dexterity once");reset();relic.gainInsanity(15);p.powers.add(new DexterityPower(p,4));p.powers.add(new FrailPower(p,2,false));play(up(new VoidShield()),null,1);});
 q.add(()->{check(relic.state.amount()==35&&p.currentBlock==8,"Void Shield+ gains Insanity first and applies Dexterity and Frail once to its total");reset();relic.gainInsanity(100);relic.activate(relic.state.charge());});
 q.add(()->play(new MindSpike(),m,0));
 q.add(()->{check(relic.state.amount()==100&&m.currentHealth==991,"Voidform discounts and amplifies Mind Spike while suppressing its explicit Insanity");nextTurn();});
 q.add(()->{check(!relic.state.active()&&relic.state.recovering()&&relic.state.amount()==0,"Actual turn transition exits Voidform, resets Insanity and enters Recharge");play(new MindSpike(),m,1);});
 q.add(()->{check(relic.state.amount()==0,"Recharge blocks explicit Mind Spike gains");nextTurn();});
 q.add(()->{check(!relic.state.recovering(),"Recharge ends after the entire following turn");play(new MindSpike(),m,1);});
 q.add(()->{check(relic.state.amount()==10,"Insanity generation resumes after Recharge");reset();});
 }
}
