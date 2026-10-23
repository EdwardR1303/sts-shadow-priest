package shadowpriest.relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import basemod.interfaces.AlternateCardCostModifier;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowpriest.cards.EnterVoidform;
import shadowpriest.mechanics.InsanityState;
import shadowpriest.powers.ShadowDotPower;
import shadowpriest.powers.VoidformPower;

public class VoidformRelic extends CustomRelic implements AlternateCardCostModifier {
    public static final String ID = "shadowpriest:Voidform";
    public final InsanityState state = new InsanityState();
    public VoidformRelic() {
        super(ID, ImageMaster.loadImage("shadowpriest/images/badge.png"), RelicTier.STARTER, LandingSound.MAGICAL);
        counter = 0;
    }
    public static VoidformRelic current() {
        if (AbstractDungeon.player == null) return null;
        AbstractRelic relic = AbstractDungeon.player.getRelic(ID);
        return relic instanceof VoidformRelic ? (VoidformRelic) relic : null;
    }
    public static boolean isActive() { VoidformRelic r = current(); return r != null && r.state.active(); }
    public static int effectiveCost(AbstractCard card, int cost) {
        return InsanityState.effectiveCost(cost, card.type == AbstractCard.CardType.ATTACK && card.cost >= 0, isActive());
    }
    // BaseMod pays one point of each Attack's cost before energy. Nothing is written to the card,
    // so temporary cost changes, cards drawn later, and turn-end restoration compose naturally.
    @Override public boolean costEffectActive(AbstractCard card) {
        return state.active() && card.type == AbstractCard.CardType.ATTACK && card.cost >= 0 && card.costForTurn >= 0;
    }
    @Override public int getAlternateResource(AbstractCard card) { return costEffectActive(card) ? 1 : 0; }
    @Override public boolean prioritizeAlternateCost(AbstractCard card) { return true; }
    @Override public boolean canSplitCost(AbstractCard card) { return true; }
    @Override public int spendAlternateCost(AbstractCard card, int amount) { return costEffectActive(card) ? Math.max(0, amount - 1) : amount; }
    @Override public String replaceCostString(AbstractCard card, String text, Color color) {
        return costEffectActive(card) && !card.freeToPlay() ? Integer.toString(effectiveCost(card, card.costForTurn)) : text;
    }
    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public AbstractRelic makeCopy() { return new VoidformRelic(); }
    @Override public void atPreBattle() { reset(); }
    @Override public void onVictory() { reset(); }
    @Override public void onUnequip() { reset(); }
    @Override public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        if (info.owner != AbstractDungeon.player || info.type != DamageInfo.DamageType.NORMAL || target == null || target.isPlayer) return;
        // Hook runs before death cleanup, so killing blows use the target's current debuffs too.
        boolean wasReady = state.ready();
        state.gain(ShadowDotPower.count(target));
        counter = state.amount();
        if (!wasReady && state.ready()) { flash(); beginLongPulse(); }
    }
    public void deliverReadyCard() {
        if (!state.ready() || AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;
        if (AbstractDungeon.actionManager == null || !AbstractDungeon.actionManager.isEmpty()
            || AbstractDungeon.actionManager.currentAction != null || !AbstractDungeon.actionManager.cardQueue.isEmpty()
            || AbstractDungeon.actionManager.turnHasEnded || pendingCardDelivery()
            || AbstractDungeon.player.isEndingTurn) return;
        if (AbstractDungeon.player.hand.size() >= BaseMod.MAX_HAND_SIZE) return;
        if (hasChargeCard(AbstractDungeon.player.hand) || hasChargeCard(AbstractDungeon.player.drawPile)
            || hasChargeCard(AbstractDungeon.player.discardPile) || hasChargeCard(AbstractDungeon.player.limbo)) return;
        // Wait for a free slot. A full hand must never discard the earned charge.
        addToBot(new MakeTempCardInHandAction(new EnterVoidform(state.charge()), 1));
    }
    private boolean hasChargeCard(CardGroup group) {
        for (AbstractCard c : group.group) if (c instanceof EnterVoidform && ((EnterVoidform)c).charge == state.charge()) return true;
        return false;
    }
    public static boolean pendingCardDelivery() {
        return AbstractDungeon.effectList.stream().anyMatch(e -> e instanceof com.megacrit.cardcrawl.vfx.cardManip.ShowCardAndAddToHandEffect);
    }
    public void activate(int charge) {
        if (!state.activate(charge)) return;
        stopPulse(); flash();
        addToBot(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VoidformPower(AbstractDungeon.player)));
    }
    @Override public void onPlayerEndTurn() {
        if (!state.active()) return;
        state.endTurn(); counter = state.amount(); stopPulse();
        addToBot(new RemoveSpecificPowerAction(AbstractDungeon.player, AbstractDungeon.player, VoidformPower.POWER_ID));
        AbstractDungeon.player.hand.applyPowers();
    }
    public void reset() { state.reset(); counter = 0; stopPulse(); }
}
