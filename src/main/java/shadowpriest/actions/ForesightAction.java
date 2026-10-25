package shadowpriest.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.NewQueueCardAction;
import com.megacrit.cardcrawl.actions.utility.UnlimboAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/** A restricted tutor, not a normal draw: no shuffle and no on-draw triggers. */
public class ForesightAction extends AbstractGameAction {
    private final boolean play;
    private boolean opened;
    public ForesightAction(boolean play) { this.play = play; actionType = ActionType.CARD_MANIPULATION; }
    @Override public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!opened) {
            CardGroup powers = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : p.drawPile.group) if (c.type == AbstractCard.CardType.POWER) powers.addToTop(c);
            if (powers.isEmpty()) { isDone = true; return; }
            // Choosing is part of Foresight's feedback, even when only one Power remains.
            powers.sortAlphabetically(true);
            AbstractDungeon.gridSelectScreen.open(powers, 1, play ? "Choose a Power to play for free." : "Choose a Power to put into your hand.", false);
            opened = true;
            return;
        }
        if (!AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) {
            AbstractCard selected = AbstractDungeon.gridSelectScreen.selectedCards.get(0);
            AbstractDungeon.gridSelectScreen.selectedCards.clear();
            select(selected);
        }
    }
    private void select(AbstractCard card) {
        AbstractPlayer p = AbstractDungeon.player;
        if (!p.drawPile.contains(card) || card.type != AbstractCard.CardType.POWER) { isDone = true; return; }
        if (!play) {
            if (p.hand.size() >= BaseMod.MAX_HAND_SIZE) {
                p.drawPile.moveToDiscardPile(card); p.createHandIsFullDialog();
            } else {
                AbstractDungeon.getCurrRoom().souls.remove(card);
                p.drawPile.moveToHand(card);
            }
        } else {
            p.drawPile.removeCard(card);
            AbstractDungeon.getCurrRoom().souls.remove(card);
            p.limbo.addToTop(card);
            card.unhover();
            card.current_x = card.target_x = Settings.WIDTH / 2f;
            card.current_y = card.target_y = Settings.HEIGHT / 2f;
            card.angle = card.targetAngle = 0f;
            card.drawScale = 0.75f; card.targetDrawScale = 0.85f;
            card.lighten(false);
            card.freeToPlayOnce = true;
            card.applyPowers();
            // addToTop reverses insertion order: show the chosen card, then use the
            // base game's Unlimbo/NewQueue sequence to play it through normal hooks.
            addToTop(new NewQueueCardAction(card, true, false, true));
            addToTop(new UnlimboAction(card));
            addToTop(new PreviewPowerAction());
        }
        isDone = true;
    }

    private static final class PreviewPowerAction extends AbstractGameAction {
        private PreviewPowerAction() {
            actionType = ActionType.WAIT;
            // Ordinary WaitAction is capped at 0.1s in Fast Mode, too short to read.
            duration = Settings.FAST_MODE ? 0.45f : 0.75f;
        }
        @Override public void update() { tickDuration(); }
    }
}
