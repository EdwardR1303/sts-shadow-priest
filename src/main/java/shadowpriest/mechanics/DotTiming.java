package shadowpriest.mechanics;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.powers.*;
/** One shared player-end-turn trigger; skipped enemy actions still take their DoT damage. */
public final class DotTiming {
 public static void tick(){
  AbstractPower weaving=AbstractDungeon.player.getPower(ShadowWeavingPower.POWER_ID);
  if(weaving instanceof ShadowWeavingPower)((ShadowWeavingPower)weaving).beforeDots();
  for(AbstractMonster m:AbstractDungeon.getMonsters().monsters)if(!m.isDeadOrEscaped())
  for(AbstractPower p:new java.util.ArrayList<>(m.powers)){
   if(p instanceof ShadowDotPower)((ShadowDotPower)p).naturalTick();
   else if(p instanceof MadnessPower)((MadnessPower)p).naturalTick();
  }
 }
 @SpirePatch(clz=AbstractRoom.class,method="applyEndOfTurnPreCardPowers")
 public static class EndTurn { @SpirePostfixPatch public static void after(){tick();} }
}
