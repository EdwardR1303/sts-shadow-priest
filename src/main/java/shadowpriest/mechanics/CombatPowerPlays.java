package shadowpriest.mechanics;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
/** Actual Power plays only, independent of automatic Legendary equipment. */
public final class CombatPowerPlays {
    @SpirePatch(clz=AbstractPlayer.class,method=SpirePatch.CLASS)
    public static class State { public static SpireField<Integer> count=new SpireField<>(()->0); }
    public static int count(){return AbstractDungeon.player==null?0:State.count.get(AbstractDungeon.player);}
    @SpirePatch(clz=AbstractPlayer.class,method="preBattlePrep")
    public static class Reset { @SpirePrefixPatch public static void before(AbstractPlayer __instance){State.count.set(__instance,0);} }
    @SpirePatch(clz=AbstractPlayer.class,method="useCard")
    public static class Played { @SpirePrefixPatch public static void before(AbstractPlayer __instance,AbstractCard c){
        if(c.type==AbstractCard.CardType.POWER&&!c.dontTriggerOnUseCard)State.count.set(__instance,State.count.get(__instance)+1);
    } }
}
