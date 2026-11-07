package shadowpriest.mechanics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.ShadowCommittal;
import shadowpriest.cards.VoidVolley;
import java.util.*;

/** Snapshot the hand once, then let normal card-queue boundaries finish each play in order. */
public final class VoidformEntryCards {
    private static final Set<CardQueueItem> pending=Collections.newSetFromMap(new IdentityHashMap<>());
    public static void enter(){
        List<AbstractCard> cards=new ArrayList<>();
        for(AbstractCard c:AbstractDungeon.player.hand.group)
            if(c instanceof VoidVolley || c instanceof ShadowCommittal && c.upgraded)cards.add(c);
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction(){public void update(){
            for(AbstractCard c:cards)if(AbstractDungeon.player.hand.contains(c)){
                CardQueueItem item=new CardQueueItem(c,null,EnergyPanel.totalCount,true,true);
                pending.add(item);
                // Append, never push to the front: actions from each card drain before the next card.
                AbstractDungeon.actionManager.addCardQueueItem(item,false);
            }
            isDone=true;
        }});
    }
    @SpirePatch(clz=GameActionManager.class,method="getNextAction")
    public static class PrepareNext {
        @SpirePrefixPatch public static void before(GameActionManager __instance){
            if(!__instance.actions.isEmpty()||!__instance.preTurnActions.isEmpty())return;
            while(!__instance.cardQueue.isEmpty()){
                CardQueueItem next=__instance.cardQueue.get(0);
                if(!pending.remove(next))return;
                if(!AbstractDungeon.player.hand.contains(next.card)||AbstractDungeon.getCurrRoom().isBattleOver){
                    __instance.cardQueue.remove(0);continue;
                }
                next.energyOnUse=EnergyPanel.totalCount;
                next.card.freeToPlayOnce=true;
                return;
            }
        }
    }
    @SpirePatch(clz=AbstractPlayer.class,method="preBattlePrep")
    public static class Reset {
        @SpirePrefixPatch public static void before(){pending.clear();}
    }
}
