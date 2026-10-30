package shadowpriest.mechanics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.EnergyManager;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;

/** Receipt for energy actually paid by useCard, after alternate costs/free-play modifiers. */
public final class PlayedEnergy {
    public static final class Receipt { public int spent; }
    private static final java.util.Deque<Receipt> plays=new java.util.ArrayDeque<>();
    public static Receipt receipt() { return plays.isEmpty()?new Receipt():plays.peek(); }
    @SpirePatch(clz=AbstractPlayer.class,method="useCard")
    public static class Play {
        @SpirePrefixPatch public static void before(){plays.push(new Receipt());}
        @SpirePostfixPatch public static void after(){plays.pop();}
    }
    @SpirePatch(clz=EnergyManager.class,method="use")
    public static class Spend {
        @SpirePrefixPatch public static void before(EnergyManager __instance,int e){
            if(!plays.isEmpty())plays.peek().spent+=Math.max(0,Math.min(e,EnergyPanel.totalCount));
        }
    }
}
