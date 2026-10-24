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
            if (powers.size() == 1) { select(powers.getTopCard()); return; }
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
            card.current_y = -200f * Settings.scale;
            card.target_x = Settings.WIDTH / 2f; card.target_y = Settings.HEIGHT / 2f;
            card.freeToPlayOnce = true;
            card.applyPowers();
            addToTop(new NewQueueCardAction(card, true, false, true));
            addToTop(new UnlimboAction(card));
        }
        isDone = true;
    }
}
