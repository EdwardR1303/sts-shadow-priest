package shadowpriest.relics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.expr.ExprEditor;
import javassist.expr.MethodCall;
import shadowpriest.powers.HastePower;

public class VoidVengeance extends PriestRelic {
    public static final String ID = "shadowpriest:VoidVengeance";
    public VoidVengeance() { super(ID, RelicTier.BOSS, "shadowpriest/images/cards/icons/void_bolt.jpg"); }
    @Override public AbstractRelic makeCopy() { return new VoidVengeance(); }
    @Override public void atPreBattle() { counter = 1; }
    @Override public void atTurnStart() { counter = 1; }
    @Override public void onVictory() { counter = -1; }

    public static boolean available(AbstractCard card) {
        if (card.type != AbstractCard.CardType.CURSE || card.dontTriggerOnUseCard
                || AbstractDungeon.player == null) return false;
        AbstractRelic relic = AbstractDungeon.player.getRelic(ID);
        return relic instanceof VoidVengeance && relic.counter == 1;
    }

    @Override public void onUseCard(AbstractCard card, UseCardAction action) {
        if (available(card)) {
            counter = 0;
            action.exhaustCard = true;
            flash();
        }
    }

    @Override public void onExhaust(AbstractCard card) {
        if (card.type == AbstractCard.CardType.CURSE) {
            flash();
            addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player,
                    new HastePower(AbstractDungeon.player, 2), 2));
        }
    }

    // Extend only the Curse permission check; preserve Normality, Velvet Choker,
    // turn-end restrictions, and Blue Candle's independent play permission.
    @SpirePatch(clz = AbstractCard.class, method = "canUse")
    public static class CursePermission {
        @SpireInstrumentPatch public static ExprEditor edit() {
            return new ExprEditor() {
                @Override public void edit(MethodCall call) throws CannotCompileException {
                    if (call.getMethodName().equals("hasRelic"))
                        call.replace("{ $_ = $proceed($$) || (\"Blue Candle\".equals($1) && shadowpriest.relics.VoidVengeance.available(this)); }");
                }
            };
        }
    }

    @SpirePatch(clz = AbstractCard.class, method = "freeToPlay")
    public static class FreeCurse {
        @SpirePostfixPatch public static boolean after(boolean __result, AbstractCard __instance) {
            return __result || available(__instance);
        }
    }

    // Preserve free payment after onUseCard consumes the allowance. The engine
    // clears freeToPlayOnce when UseCardAction finishes; no permanent cost edit.
    @SpirePatch(clz = AbstractPlayer.class, method = "useCard")
    public static class PreservePayment {
        @SpirePrefixPatch public static void before(AbstractPlayer __instance, AbstractCard c) {
            if (available(c)) c.freeToPlayOnce = true;
        }
    }
}
