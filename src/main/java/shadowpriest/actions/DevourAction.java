package shadowpriest.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.List;
import java.util.function.Function;

/** Select and consume one real hand Curse, then resolve the card's effects in order. */
public final class DevourAction extends AbstractGameAction {
    private final Function<Boolean, List<AbstractGameAction>> effects;
    private boolean opened;
    public DevourAction(Function<Boolean, List<AbstractGameAction>> effects) {
        this.effects=effects; actionType=ActionType.CARD_MANIPULATION;
    }
    public static boolean available() {
        return AbstractDungeon.player!=null && AbstractDungeon.player.hand.group.stream()
            .anyMatch(c -> c.type==AbstractCard.CardType.CURSE);
    }
    @Override public void update() {
        AbstractPlayer p=AbstractDungeon.player;
        if(!opened) {
            CardGroup choices=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c:p.hand.group) if(c.type==AbstractCard.CardType.CURSE) choices.addToTop(c);
            if(choices.isEmpty()) { resolve(null); return; }
            if(choices.size()==1) { resolve(choices.getTopCard()); return; }
            AbstractDungeon.gridSelectScreen.open(choices,1,"Devour: choose a Curse to Exhaust.",false);
            opened=true; return;
        }
        if(AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) return;
        AbstractCard curse=AbstractDungeon.gridSelectScreen.selectedCards.remove(0);
        AbstractDungeon.gridSelectScreen.selectedCards.clear(); resolve(curse);
    }
    private void resolve(AbstractCard curse) {
        AbstractPlayer p=AbstractDungeon.player;
        boolean consumed=curse!=null && curse.type==AbstractCard.CardType.CURSE && p.hand.contains(curse);
        if(consumed) p.hand.moveToExhaustPile(curse);
        List<AbstractGameAction> actions=effects.apply(consumed);
        for(int i=actions.size()-1;i>=0;i--) addToTop(actions.get(i));
        isDone=true;
    }
}
