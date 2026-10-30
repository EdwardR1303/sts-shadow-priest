package shadowpriest.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;

/** Exhaust one actual hand Curse, offering a choice only when there are multiple. */
public final class ExhaustHandCurseAction extends AbstractGameAction {
    private boolean opened;

    public ExhaustHandCurseAction() { actionType = ActionType.CARD_MANIPULATION; }

    @Override public void update() {
        AbstractPlayer p = AbstractDungeon.player;
        if (!opened) {
            CardGroup choices = new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for (AbstractCard c : p.hand.group)
                if (c.type == AbstractCard.CardType.CURSE) choices.addToTop(c);
            if (choices.isEmpty()) { isDone = true; return; }
            if (choices.size() == 1) { exhaust(choices.getTopCard()); return; }
            AbstractDungeon.gridSelectScreen.open(choices, 1, "Choose a Curse to Exhaust.", false);
            opened = true;
            return;
        }
        if (AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) return;
        AbstractCard curse = AbstractDungeon.gridSelectScreen.selectedCards.remove(0);
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        exhaust(curse);
    }

    private void exhaust(AbstractCard curse) {
        AbstractPlayer p = AbstractDungeon.player;
        if (p.hand.contains(curse) && curse.type == AbstractCard.CardType.CURSE)
            p.hand.moveToExhaustPile(curse);
        isDone = true;
    }
}
