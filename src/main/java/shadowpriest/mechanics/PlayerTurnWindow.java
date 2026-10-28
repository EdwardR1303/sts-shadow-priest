package shadowpriest.mechanics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.MonsterGroup;

/** Includes player start/end effects, but not the subsequent enemy phase. */
public final class PlayerTurnWindow {
    public static boolean active=true;
    @SpirePatch(clz=AbstractPlayer.class,method="preBattlePrep")
    public static class Begin { @SpirePrefixPatch public static void before(){active=true;} }
    @SpirePatch(clz=AbstractPlayer.class,method="applyStartOfTurnRelics")
    public static class Next {
        @SpirePrefixPatch public static void before(){
            com.megacrit.cardcrawl.dungeons.AbstractDungeon.actionManager.addToBottom(new com.megacrit.cardcrawl.actions.AbstractGameAction(){
                public void update(){active=true;isDone=true;}
            });
        }
    }
    @SpirePatch(clz=MonsterGroup.class,method="queueMonsters")
    public static class End { @SpirePrefixPatch public static void before(){active=false;} }
}
