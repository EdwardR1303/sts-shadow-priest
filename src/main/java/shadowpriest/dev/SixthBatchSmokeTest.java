package shadowpriest.dev;
import com.megacrit.cardcrawl.actions.*;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.legendary.*;
import shadowpriest.actions.*;
import shadowpriest.relics.VoidformRelic;
import java.util.*;
import static shadowpriest.dev.CardBatchSmokeTest.*;
import static shadowpriest.dev.CombatSmokeTest.check;
public class SixthBatchSmokeTest {
 private static AbstractPlayer p;private static AbstractMonster m;
 private static AbstractCard c,kept;private static int hp,before;
 private static Boolean chooseAttacks;
 private static Random rng;
 private static ValanyrPower ward;
 private static final ArrayList<AbstractCard> deck=new ArrayList<>();
 private static ArrayList<String> history;
 public static void handleSelection(){
  if(chooseAttacks!=null && AbstractDungeon.screen==AbstractDungeon.CurrentScreen.GRID){
   for(AbstractCard c:AbstractDungeon.gridSelectScreen.targetGroup.group) if(c instanceof SerendipityAction.Choice && ((SerendipityAction.Choice)c).attacks==chooseAttacks){
    AbstractDungeon.gridSelectScreen.selectedCards.add(c);chooseAttacks=null;AbstractDungeon.closeCurrentScreen();break;
   }
  }
 }
 // Effect-only fixtures also identify the visible weapon; real preBattlePrep is checked below.
 private static void equipForReview(LegendaryCard card){LegendaryEquipment.State.equipped.set(p,card.cardID);card.equip(p);}
 private static int stat(String id){AbstractPower power=p.getPower(id);return power==null?0:power.amount;}
 private static void seedProc(boolean yes,int chance){long seed=0;while((new Random(seed).random(99)<chance)!=yes)seed++;AbstractDungeon.miscRng=new Random(seed);}
 public static void append(Queue<Runnable> q){
 q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);reset();
 check(AbstractDungeon.commonCardPool.size()==21 && AbstractDungeon.uncommonCardPool.size()==19 && AbstractDungeon.rareCardPool.size()==11,"Sixth batch has complete 20/14/8 ordinary reward pools");
 for(String id:new String[]{"FlashHeal","BloodMagic","ShadowMend","VoidShield","Insight","Piety","Serendipity","PowerWordBarrier","TitheEvasion","MindSoothe","MentalFortitude","Dragonwrath","Xalatath","Valanyr"}){
 AbstractCard card=CardLibrary.getCard("shadowpriest:"+id);check(card!=null,id+" registers");card=card.makeCopy();CardArtReview.card(card,id);card.upgrade();CardArtReview.card(card,id+"Plus");AbstractCard copy=card.makeStatEquivalentCopy();
 check(copy.upgraded && card.cardID.equals(copy.cardID) && card.baseMagicNumber==copy.baseMagicNumber && card.baseBlock==copy.baseBlock && card.exhaust==copy.exhaust,id+" upgraded copy preserves mechanics");
 }
 for(String id:LegendaryEquipment.IDS)CardArtReview.weapon(id);
 // Review the earlier cards whose generic portraits were replaced after the first full run.
 for(String id:new String[]{"MindFlay","MindFlayInsanity","MindBlast","MindSear","ShadowWordDeath","Bulwark","Fade","Dispersion","DesperatePrayer","PsychicLink","Foresight","EnterVoidform"}){
  AbstractCard artCard=CardLibrary.getCard("shadowpriest:"+id).makeCopy();
  CardArtReview.card(artCard,id);artCard.upgrade();CardArtReview.card(artCard,id+"Plus");
 }
 play(new BloodMagic(),m,3);});
 q.add(()->{check(m.currentHealth==982 && p.currentHealth==197 && EnergyPanel.totalCount==1,"Blood Magic deals 18, loses 3 HP, costs 2");reset();play(upgraded(new BloodMagic()),m,3);});
 q.add(()->{check(m.currentHealth==976 && p.currentHealth==197,"Blood Magic+ deals 24 with unchanged HP loss");reset();play(new ShadowMend(),null,3);});
 q.add(()->{check(p.currentHealth==210 && stat(ShadowMendDebtPower.POWER_ID)==2,"Shadow Mend heals ten and adds two debt");play(upgraded(new ShadowMend()),null,3);});
 q.add(()->{check(p.currentHealth==220 && stat(ShadowMendDebtPower.POWER_ID)==3,"Shadow Mend+ heals ten and adds one to combined debt");endTurn(true);});
 q.add(()->{check(p.currentHealth==217 && stat(ShadowMendDebtPower.POWER_ID)==3,"Combined debt ticks once for three and does not decay");play(new TitheEvasion(),null,3);});
 q.add(()->{check(stat(TitheEvasionPower.POWER_ID)==1 && !new TitheEvasion().canUse(p,null),"Tithe base installs one reduction and blocks equal copies");play(upgraded(new TitheEvasion()),null,3);});
 q.add(()->{check(stat(TitheEvasionPower.POWER_ID)==2 && !upgraded(new TitheEvasion()).canUse(p,null),"Tithe+ replaces base protection with two");hp=p.currentHealth;endTurn(true);});
 q.add(()->{check(p.currentHealth==hp-1,"Tithe reduces combined debt once, not per application");hp=p.currentHealth;play(new BloodMagic(),m,3);});
 q.add(()->{check(p.currentHealth==hp-3,"Tithe excludes Blood Magic HP loss");hp=p.currentHealth;play(upgraded(new ShadowWordDeath()),m,0);});
 q.add(()->{check(p.currentHealth==hp-2,"Tithe reduces both Death+ backlash events");hp=p.currentHealth;p.damage(new DamageInfo(p,3,DamageInfo.DamageType.HP_LOSS));check(p.currentHealth==hp-3,"Tithe does not reduce unrelated self HP loss");
 hp=p.currentHealth;p.damage(new DamageInfo(m,3,DamageInfo.DamageType.NORMAL));check(p.currentHealth==hp-3,"Tithe does not reduce enemy attacks");
 reset();p.powers.add(new ArtifactPower(p,1));play(new ShadowMend(),null,3);});
 q.add(()->{check(p.currentHealth==210 && !p.hasPower(ShadowMendDebtPower.POWER_ID) && !p.hasPower("Artifact"),"Artifact blocks Mend debt without blocking healing");reset();VoidformRelic.current().gainInsanity(80);p.powers.add(new DexterityPower(p,3));play(new VoidShield(),null,3);});
 q.add(()->{check(p.currentBlock==19,"Void Shield at 80 Insanity adds Dexterity once");reset();VoidformRelic.current().gainInsanity(80);play(upgraded(new VoidShield()),null,3);});
 q.add(()->{check(p.currentBlock==20 && VoidformRelic.current().state.ready(),"Void Shield+ reaches threshold before calculating Block");reset();p.powers.add(new VoidformPower(p,true));play(new VoidShield(),null,3);});
 q.add(()->{check(p.currentBlock==20,"Void Shield counts permanent Voidform as 100");reset();VoidformRelic.current().state.gain(100);VoidformRelic.current().state.activate(VoidformRelic.current().state.charge());VoidformRelic.current().state.endTurn();play(upgraded(new VoidShield()),null,3);});
 q.add(()->{check(p.currentBlock==0 && VoidformRelic.current().state.amount()==0,"Void Shield+ cannot gain Insanity during recovery");reset();p.hand.addToTop(new Piety());p.drawPile.addToTop(new MindSpike());p.drawPile.addToTop(new MindFlay());p.drawPile.addToTop(new PowerWordShield());p.drawPile.addToTop(new Fade());c=new Piety();play(c,null,0);});
 q.add(()->{check(p.hand.size()==5 && p.hand.group.stream().filter(a->a.type==AbstractCard.CardType.ATTACK).count()==2,"Piety ignores other Piety copies and snapshots both draw conditions");check(p.exhaustPile.contains(c),"Base Piety exhausts");reset();p.hand.addToTop(new PowerWordShield());p.discardPile.addToTop(new MindSpike());c=upgraded(new Piety());play(c,null,0);});
 q.add(()->{check(p.hand.size()==1 && p.discardPile.contains(c) && p.drawPile.isEmpty(),"Piety+ does not reshuffle or exhaust when no matching draw exists");reset();p.powers.add(new NoDrawPower(p));p.drawPile.addToTop(new MindSpike());play(new Piety(),null,0);});
 q.add(()->{check(p.hand.isEmpty() && p.drawPile.size()==1,"Piety respects No Draw");reset();for(int i=0;i<9;i++)p.hand.addToTop(new PowerWordShield());for(int i=0;i<3;i++)p.drawPile.addToTop(new MindSpike());play(new Piety(),null,0);});
 q.add(()->{check(p.hand.size()==10 && p.drawPile.size()==2,"Piety respects ten-card hand capacity");reset();p.powers.add(new DexterityPower(p,4));for(int i=0;i<4;i++)p.hand.addToTop(new MindSpike());kept=p.hand.getTopCard();play(new PowerWordBarrier(),null,3);});
 q.add(()->{check(p.currentBlock==36,"Barrier applies Dexterity per remaining card");endTurn(true);});
 q.add(()->{check(p.hand.contains(kept) && p.hand.size()>=4 && p.currentBlock==0,"Barrier retains cards across turn without retaining Block");reset();p.powers.add(new DexterityPower(p,4));for(int i=0;i<4;i++)p.hand.addToTop(new MindSpike());play(upgraded(new PowerWordBarrier()),null,3);});
 q.add(()->{check(p.currentBlock==44,"Barrier+ grants forty-four Block with four cards and four Dexterity");reset();p.powers.add(new DexterityPower(p,4));p.powers.add(new FrailPower(p,2,false));for(int i=0;i<4;i++)p.hand.addToTop(new MindSpike());play(new PowerWordBarrier(),null,3);});
 q.add(()->{check(p.currentBlock==24,"Barrier rounds Frail per card and does not apply modifiers twice");reset();p.hand.addToTop(new Fade());p.hand.addToTop(new PowerWordShield());p.hand.addToTop(new PsychicLink());p.drawPile.addToTop(new MindSpike());p.drawPile.addToTop(new MindFlay());chooseAttacks=true;c=new Serendipity();play(c,null,0);});
 q.add(()->{check(p.hand.size()==3 && p.hand.group.stream().filter(a->a.type==AbstractCard.CardType.ATTACK).count()==2 && p.exhaustPile.contains(c),"Serendipity chooser discards two Skills, preserves Power, draws two Attacks, exhausts");reset();p.hand.addToTop(new MindSpike());p.hand.addToTop(new MindFlay());p.drawPile.addToTop(new Fade());chooseAttacks=false;c=upgraded(new Serendipity());play(c,null,0);});
 q.add(()->{check(p.hand.size()==1 && p.hand.getTopCard() instanceof Fade && p.discardPile.contains(c),"Serendipity+ reverse choice draws only available Skill and remains reusable");reset();play(new MentalFortitude(),null,3);});
 q.add(()->{check(stat("Dexterity")==2 && stat(MasteryPower.POWER_ID)==2 && stat("Strength")==-2,"Mental Fortitude gains both stats and loses two Strength");play(upgraded(new MentalFortitude()),null,3);});
 q.add(()->{check(stat("Dexterity")==5 && stat(MasteryPower.POWER_ID)==5 && stat("Strength")==-4,"Mental Fortitude+ repeats with three stat gains and unchanged Strength loss");reset();dots(m);play(new MindSoothe(),m,0);});
 q.add(()->{check(m.getPower(MindSoothePower.POWER_ID).amount==2,"Soothe snapshots two DoTs");play(upgraded(new MindSoothe()),m,0);});
 q.add(()->{check(m.getPower(MindSoothePower.POWER_ID).amount==5,"Soothe+ adds flat one and stacks per-hit reduction");DamageInfo hit=new DamageInfo(m,10,DamageInfo.DamageType.NORMAL);hit.applyPowers(m,p);check(hit.output==5,"Soothe reduces normal outgoing damage before Block");p.addBlock(3);p.damage(hit);check(p.currentHealth==198,"Soothe and Block combine without double reduction");endTurn(true);});
 q.add(()->{check(!m.hasPower(MindSoothePower.POWER_ID),"Soothe expires after enemy turn");reset();m.powers.add(new ArtifactPower(m,1));dots(m);play(new MindSoothe(),m,0);});
 q.add(()->{check(!m.hasPower(MindSoothePower.POWER_ID) && !m.hasPower("Artifact"),"Artifact blocks Soothe");reset();LegendaryEquipment.State.equipped.set(p,Dragonwrath.ID);p.powers.add(new DragonwrathPower(p,20));rng=AbstractDungeon.miscRng;seedProc(true,20);play(new MindFlay(),m,3);});
 q.add(()->{check(m.currentHealth==988 && EnergyPanel.totalCount==2,"Dragonwrath repeats whole multihit Attack for free exactly once");reset();dots(m);p.powers.add(new PsychicLinkPower(p,1));LegendaryEquipment.State.equipped.set(p,Dragonwrath.ID);p.powers.add(new DragonwrathPower(p,20));seedProc(true,20);play(new MindSpike(),m,3);});
 q.add(()->{check(m.currentHealth==978 && VoidformRelic.current().state.amount()==40,"Dragonwrath repeats Psychic Link triggers and generates Insanity for both Attack hits");reset();LegendaryEquipment.State.equipped.set(p,Dragonwrath.ID);p.powers.add(new DragonwrathPower(p,20));seedProc(false,20);play(new MindFlay(),m,3);});
 q.add(()->{check(m.currentHealth==994,"Failed Dragonwrath roll does not repeat");reset();LegendaryEquipment.State.equipped.set(p,Dragonwrath.ID);p.powers.add(new DragonwrathPower(p,20));seedProc(true,20);play(new VoidTorrent(),m,3);});
 q.add(()->{check(m.currentHealth==952 && EnergyPanel.totalCount==0,"Dragonwrath repeats original X=3 torrent sequence without spending more energy");reset();LegendaryEquipment.State.equipped.set(p,Dragonwrath.ID);p.powers.add(new DragonwrathPower(p,20));seedProc(true,20);play(new BloodMagic(),m,3);});
 q.add(()->{check(m.currentHealth==964 && p.currentHealth==194,"Dragonwrath repeats Blood Magic backlash along with damage");AbstractDungeon.miscRng=rng;reset();equipForReview(new Xalatath());});
 q.add(()->{check(stat("Strength")+stat("Dexterity")+stat(MasteryPower.POWER_ID)==8,"Xalatath base awards exactly eight stat points");check(stat("Strength")%2==0 && stat("Dexterity")%2==0,"Xalatath grants stats in pairs");reset();equipForReview(upgraded(new Xalatath()));});
 q.add(()->{check(stat("Strength")+stat("Dexterity")+stat(MasteryPower.POWER_ID)==10,"Xalatath+ awards ten stat points");reset();equipForReview(new Valanyr());});
 q.add(()->{ward=(ValanyrPower)p.getPower(ValanyrPower.POWER_ID);check(p.currentBlock==5 && p.hasPower("Barricade") && ward.rescue,"Valanyr equips five Block, retention, and independent rescue");endTurn(true);});
 q.add(()->{check(p.currentBlock==5 && ward.ward,"Valanyr retains Block through a real turn");p.damage(new DamageInfo(m,5,DamageInfo.DamageType.NORMAL));check(p.currentBlock==0 && !ward.ward && ward.rescue && !p.hasPower("Barricade"),"Exact Block depletion shatters only retention");p.addBlock(12);endTurn(true);});
 q.add(()->{check(p.currentBlock==0,"Valanyr retention does not return after gaining Block");p.damage(new DamageInfo(m,999,DamageInfo.DamageType.NORMAL));check(p.currentHealth==1 && !p.isDead && !ward.rescue,"Valanyr consumes rescue and leaves one HP on lethal damage");p.currentHealth=10;p.damage(new DamageInfo(m,4,DamageInfo.DamageType.NORMAL));check(p.currentHealth==6,"Valanyr rescue supplies no lasting damage immunity");reset();equipForReview(upgraded(new Valanyr()));});
 q.add(()->{check(p.currentBlock==10,"Valanyr+ starts with ten Block");p.powers.add(new BarricadePower(p));p.powers.add(new BlurPower(p,1));p.loseBlock();check(p.hasPower("Barricade") && p.hasPower("Blur"),"Shattering Valanyr preserves independent Barricade and Blur");reset();equipForReview(new Valanyr());});
 q.add(()->{p.currentHealth=3;p.powers.add(new FadePower(p,2));java.util.Collections.sort(p.powers);p.damage(new DamageInfo(p,3,DamageInfo.DamageType.HP_LOSS));ward=(ValanyrPower)p.getPower(ValanyrPower.POWER_ID);check(p.currentHealth==2 && ward.rescue,"Fade prevents lethality before Valanyr spends rescue");p.damage(new DamageInfo(p,10,DamageInfo.DamageType.HP_LOSS));check(p.currentHealth==1 && !ward.rescue,"Valanyr also prevents lethal HP loss");reset();
 deck.clear();deck.addAll(p.masterDeck.group);history=new LegendaryEquipment().onSave();p.masterDeck.clear();LegendaryEquipment.State.acquired.get(p).clear();
 p.masterDeck.addToTop(new Dragonwrath());check(p.masterDeck.size()==1 && LegendaryEquipment.ownsAny(),"First Legendary can be acquired");p.masterDeck.addToTop(new Xalatath());p.masterDeck.addToBottom(new Dragonwrath());check(p.masterDeck.size()==1,"All further Legendary identities and permanent copies are rejected");
 p.masterDeck.addToTop(new MindSpike());check(LegendaryEquipment.copyableDeck().size()==1,"Permanent copy chooser excludes the Legendary but preserves ordinary cards");
 com.google.gson.Gson gson=new com.google.gson.Gson();
 ArrayList<String> saved=gson.fromJson(gson.toJson(new LegendaryEquipment().onSave()),new com.google.gson.reflect.TypeToken<ArrayList<String>>(){}.getType());LegendaryEquipment.State.acquired.get(p).clear();new LegendaryEquipment().onLoad(saved);check(!LegendaryEquipment.canAcquire(new Dragonwrath()),"Legendary history survives JSON save serialization roundtrip");
 p.masterDeck.removeCard(Dragonwrath.ID);check(!LegendaryEquipment.canAcquire(new Dragonwrath()) && LegendaryEquipment.canAcquire(new Valanyr()),"Removal frees slot without restoring acquired identity eligibility");
 p.masterDeck.addToTop(new Valanyr());p.drawPile.initializeDeck(p.masterDeck);check(p.drawPile.size()==1 && p.drawPile.getTopCard() instanceof MindSpike,"Combat deck contains ordinary cards but no Legendary draw");
 check(!new Valanyr().canUse(p,null),"Temporary Legendary cannot be played to activate equipment");
 new com.megacrit.cardcrawl.relics.DollysMirror().onEquip();
 check(AbstractDungeon.gridSelectScreen.targetGroup.size()==1 && AbstractDungeon.gridSelectScreen.targetGroup.getTopCard() instanceof MindSpike,"Actual Mirror selection excludes Legendary equipment");
 AbstractDungeon.closeCurrentScreen();AbstractDungeon.getCurrRoom().phase=com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT;
 new com.megacrit.cardcrawl.events.shrines.Duplicator().use();
 check(AbstractDungeon.gridSelectScreen.targetGroup.size()==1 && AbstractDungeon.gridSelectScreen.targetGroup.getTopCard() instanceof MindSpike,"Actual Duplicator selection excludes Legendary equipment");AbstractDungeon.closeCurrentScreen();
 p.masterDeck.clear();LegendaryEquipment.State.acquired.get(p).clear();
 check(LegendaryEquipment.chance(0)==5 && LegendaryEquipment.chance(5)==5 && LegendaryEquipment.chance(10)==10 && LegendaryEquipment.chance(20)==20,"Legendary reward chance follows agreed 5-percent floor and Ascension scaling");
 com.megacrit.cardcrawl.rooms.AbstractRoom room=AbstractDungeon.currMapNode.room;int act=AbstractDungeon.actNum,asc=AbstractDungeon.ascensionLevel;Random cardRng=AbstractDungeon.cardRng;
 try{
 AbstractDungeon.currMapNode.room=new com.megacrit.cardcrawl.rooms.MonsterRoomBoss();AbstractDungeon.actNum=1;AbstractDungeon.ascensionLevel=20;AbstractDungeon.cardRng=new Random(1234L);int offered=0;
 for(int i=0;i<100;i++){ArrayList<AbstractCard> cards=AbstractDungeon.getRewardCards();long legs=cards.stream().filter(LegendaryEquipment::isLegendary).count();if(legs>0)offered++;if(legs>1)throw new AssertionError("Multiple Legendaries in one reward");}
 check(offered>0 && offered<50,"Actual boss card rewards occasionally replace one Rare with a Legendary");
 p.masterDeck.addToTop(new Dragonwrath());check(AbstractDungeon.getRewardCards().stream().noneMatch(LegendaryEquipment::isLegendary),"Owning any Legendary excludes all Legendary boss offers");p.masterDeck.clear();AbstractDungeon.actNum=3;
 check(AbstractDungeon.getRewardCards().stream().noneMatch(LegendaryEquipment::isLegendary),"Act three does not offer Legendary cards");
 AbstractDungeon.actNum=2;LegendaryEquipment.State.acquired.get(p).addAll(Arrays.asList(LegendaryEquipment.IDS));
 check(AbstractDungeon.getRewardCards().stream().noneMatch(LegendaryEquipment::isLegendary),"Exhausted Legendary history leaves ordinary boss rewards");
 }finally{AbstractDungeon.currMapNode.room=room;AbstractDungeon.actNum=act;AbstractDungeon.ascensionLevel=asc;AbstractDungeon.cardRng=cardRng;}
 p.masterDeck.clear();p.masterDeck.group.addAll(deck);new LegendaryEquipment().onLoad(history);reset();
 });
 q.add(()->{
  deck.clear();deck.addAll(p.masterDeck.group);history=new LegendaryEquipment().onSave();
 });
 for(String weaponId:LegendaryEquipment.IDS) {
 q.add(()->{
  p.masterDeck.clear();LegendaryEquipment.State.acquired.get(p).clear();
  p.masterDeck.addToTop(upgraded(CardLibrary.getCard(weaponId).makeCopy()));
  for(int i=0;i<5;i++)p.masterDeck.addToTop(new MindSpike());
  p.preBattlePrep();
 });
 q.add(()->{
  boolean effect=weaponId.equals(Dragonwrath.ID)?stat(DragonwrathPower.POWER_ID)==25:
   weaponId.equals(Xalatath.ID)?stat("Strength")+stat("Dexterity")+stat(MasteryPower.POWER_ID)==10:
   p.currentBlock==10 && p.hasPower(ValanyrPower.POWER_ID);
  check(effect && weaponId.equals(LegendaryEquipment.State.equipped.get(p)) && p.hand.isEmpty(),weaponId+" equips effect and weapon before opening draw");
  check(AbstractDungeon.actionManager.cardsPlayedThisCombat.isEmpty(),"Automatic equipment does not count as a played Power");
  AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,5));
 });
 q.add(()->{
  check(p.hand.size()==5 && p.hand.group.stream().noneMatch(LegendaryEquipment::isLegendary),"Opening hand stays full without drawing equipment");
  CardArtReview.requestCombat(weaponId.split(":")[1]);
  AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.utility.WaitAction(5f));
 });
 q.add(()->check(CardArtReview.capturedCombat(weaponId.split(":")[1]),weaponId+" actual combat frame captured after normal rendering"));
 }
 q.add(()->{
  p.masterDeck.clear();p.masterDeck.group.addAll(deck);new LegendaryEquipment().onLoad(history);LegendaryEquipment.State.equipped.set(p,null);reset();
  for(int i=0;i<10;i++)p.drawPile.addToTop(new MindSpike());
  AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(p,p,new InsightDebtPower(p,7),7));
 });
 q.add(()->endTurn(true));
 q.add(()->{check(p.hand.isEmpty() && p.gameHandSize==p.masterHandSize && !p.hasPower(InsightDebtPower.POWER_ID),"Excess Insight debt draws zero and restores normal next-turn draw");reset();});
 }
}
