package shadowpriest.mechanics;

import basemod.BaseMod;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.unique.RestoreRetainedCardsAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.LinkedHashSet;
import java.util.Set;

/** Actual played instances return once, after discarding and restoring retained cards. */
public final class ReturningDots {
    private static final Set<AbstractCard> played = new LinkedHashSet<>();
    public static void mark(AbstractCard card) { played.add(card); }
    public static void clear() { played.clear(); }
    public static void returnPlayed() {
        AbstractPlayer p = AbstractDungeon.player;
        if (p == null) { clear(); return; }
        for (AbstractCard card : played) {
            if (p.hand.contains(card) || p.exhaustPile.contains(card)) continue;
            CardGroup source = p.discardPile.contains(card) ? p.discardPile :
                p.drawPile.contains(card) ? p.drawPile : null;
            if (source == null) continue;
            if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                if (source == p.drawPile) source.moveToDiscardPile(card);
                p.createHandIsFullDialog();
            } else {
                AbstractDungeon.getCurrRoom().souls.remove(card);
                source.moveToHand(card);
            }
        }
        clear();
    }
    @SpirePatch(clz = RestoreRetainedCardsAction.class, method = "update")
    public static class AfterDiscard {
        @SpirePostfixPatch public static void after(RestoreRetainedCardsAction __instance) {
            if (__instance.isDone) returnPlayed();
        }
    }
}
