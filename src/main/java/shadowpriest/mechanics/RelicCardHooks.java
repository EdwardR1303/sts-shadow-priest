package shadowpriest.mechanics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.relics.*;
import java.util.ArrayDeque;

/** Observe actual payment after BaseMod discounts, not printed costs or effective X. */
public final class RelicCardHooks {
    private static final class Payment {
        final boolean eligible;
        int spent;
        Payment(AbstractCard c){eligible=!c.dontTriggerOnUseCard&&VoidformRelic.isActive();}
    }
    private static final ArrayDeque<Payment> payments=new ArrayDeque<>();
    @SpirePatch(clz=AbstractPlayer.class,method="useCard")
    public static class Play {
        @SpirePrefixPatch public static void before(AbstractPlayer __instance,AbstractCard c){payments.push(new Payment(c));}
        @SpirePostfixPatch public static void after(AbstractPlayer __instance){
            Payment p=payments.pop();
            if(p.eligible&&p.spent>0&&__instance.getRelic(IdolOfYshaarj.ID) instanceof IdolOfYshaarj)
                ((IdolOfYshaarj)__instance.getRelic(IdolOfYshaarj.ID)).paidCard();
        }
    }
    @SpirePatch(clz=EnergyPanel.class,method="useEnergy")
    public static class PaymentMade {
        private static final ArrayDeque<Integer> before=new ArrayDeque<>();
        @SpirePrefixPatch public static void before(){before.push(EnergyPanel.totalCount);}
        @SpirePostfixPatch public static void after(){
            int spent=Math.max(0,before.pop()-EnergyPanel.totalCount);
            if(!payments.isEmpty())payments.peek().spent+=spent;
        }
    }
    @SpirePatch(clz=AbstractCard.class,method=SpirePatch.CLASS)
    public static class FreeCard {
        public static SpireField<Boolean> fromCthun=new SpireField<>(()->false);
        public static void mark(AbstractCard c){fromCthun.set(c,true);c.cost=0;c.costForTurn=0;c.isCostModified=true;}
    }
    @SpirePatch(clz=AbstractCard.class,method="makeStatEquivalentCopy")
    public static class CopyFree {
        @SpirePostfixPatch public static AbstractCard after(AbstractCard __result,AbstractCard __instance){
            if(FreeCard.fromCthun.get(__instance))FreeCard.mark(__result);
            return __result;
        }
    }
    @SpirePatch(clz=AbstractCard.class,method="upgradeBaseCost")
    public static class UpgradeFree {
        @SpirePostfixPatch public static void after(AbstractCard __instance){
            if(FreeCard.fromCthun.get(__instance))FreeCard.mark(__instance);
        }
    }
}
