package shadowpriest.legendary;
import basemod.abstracts.CustomSavable;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.rooms.MonsterRoomBoss;
import shadowpriest.cards.*;
import java.util.*;
public class LegendaryEquipment implements CustomSavable<ArrayList<String>> {
 public static final String[] IDS={Dragonwrath.ID,Xalatath.ID,Valanyr.ID};
 public static float pulse;
 @SpirePatch(clz=AbstractPlayer.class,method=SpirePatch.CLASS)
 public static class State {
  public static SpireField<Set<String>> acquired=new SpireField<>(HashSet::new);
  public static SpireField<String> equipped=new SpireField<>(()->null);
 }
 public static boolean isLegendary(AbstractCard c){return c instanceof LegendaryCard;}
 public static boolean ownsAny(){return AbstractDungeon.player!=null && AbstractDungeon.player.masterDeck.group.stream().anyMatch(LegendaryEquipment::isLegendary);}
 public static boolean canAcquire(AbstractCard c){
  return !isLegendary(c) || (AbstractDungeon.player!=null && !ownsAny() && !State.acquired.get(AbstractDungeon.player).contains(c.cardID));
 }
 public static int chance(int ascension){return Math.max(5,Math.min(20,ascension));}
 public static ArrayList<AbstractCard> replaceReward(ArrayList<AbstractCard> cards){
  if(!(AbstractDungeon.player instanceof shadowpriest.character.ShadowPriest) || !(AbstractDungeon.getCurrRoom() instanceof MonsterRoomBoss)
    || AbstractDungeon.actNum<1 || AbstractDungeon.actNum>2 || ownsAny()) return cards;
  ArrayList<AbstractCard> eligible=new ArrayList<>();ArrayList<Integer> rareSlots=new ArrayList<>();
  for(String id:IDS){AbstractCard c=CardLibrary.getCard(id);if(c!=null && canAcquire(c)) eligible.add(c);}
  for(int i=0;i<cards.size();i++) if(cards.get(i).rarity==AbstractCard.CardRarity.RARE) rareSlots.add(i);
  if(eligible.isEmpty() || rareSlots.isEmpty() || AbstractDungeon.cardRng.random(99)>=chance(AbstractDungeon.ascensionLevel)) return cards;
  AbstractCard selected=eligible.get(AbstractDungeon.cardRng.random(eligible.size()-1)).makeCopy();
  if(AbstractDungeon.player.hasRelic("Frozen Egg 2")) selected.upgrade();
  cards.set(rareSlots.get(AbstractDungeon.cardRng.random(rareSlots.size()-1)),selected);return cards;
 }
 public static void equip(AbstractPlayer p){
  State.equipped.set(p,null);pulse=0;
  for(AbstractCard c:p.masterDeck.group) if(c instanceof LegendaryCard){
   State.equipped.set(p,c.cardID);State.acquired.get(p).add(c.cardID);((LegendaryCard)c).equip(p);break;
  }
 }
 public ArrayList<String> onSave(){return AbstractDungeon.player==null?new ArrayList<>():new ArrayList<>(State.acquired.get(AbstractDungeon.player));}
 public void onLoad(ArrayList<String> ids){
  if(AbstractDungeon.player==null) return;
  Set<String> history=State.acquired.get(AbstractDungeon.player);history.clear();if(ids!=null) history.addAll(ids);
  for(AbstractCard c:AbstractDungeon.player.masterDeck.group) if(isLegendary(c)) history.add(c.cardID);
 }
 @SpirePatch(clz=AbstractPlayer.class,method="preBattlePrep")
 public static class Equip { @SpirePostfixPatch public static void after(AbstractPlayer __instance){equip(__instance);} }
 @SpirePatch(clz=CardGroup.class,method="initializeDeck")
 public static class RemoveFromDraw { @SpirePostfixPatch public static void after(CardGroup __instance){__instance.group.removeIf(LegendaryEquipment::isLegendary);} }
 @SpirePatch(clz=AbstractDungeon.class,method="getRewardCards")
 public static class Reward { @SpirePostfixPatch public static ArrayList<AbstractCard> after(ArrayList<AbstractCard> __result){return replaceReward(__result);} }
 @SpirePatch(clz=CardGroup.class,method="addToTop")
 @SpirePatch(clz=CardGroup.class,method="addToBottom")
 @SpirePatch(clz=CardGroup.class,method="addToRandomSpot")
 public static class Obtain {
  @SpirePrefixPatch public static SpireReturn<Void> before(CardGroup __instance,AbstractCard c){
   if(AbstractDungeon.player==null || __instance!=AbstractDungeon.player.masterDeck || !isLegendary(c)) return SpireReturn.Continue();
   if(!CardCrawlGame.loadingSave && !canAcquire(c)) return SpireReturn.Return(null);
   if(ownsAny()) return SpireReturn.Return(null);
   State.acquired.get(AbstractDungeon.player).add(c.cardID);return SpireReturn.Continue();
  }
 }
 public static CardGroup copyableDeck(){
  CardGroup group=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
  for(AbstractCard c:AbstractDungeon.player.masterDeck.group) if(!isLegendary(c)) group.addToBottom(c);
  return group;
 }
}
