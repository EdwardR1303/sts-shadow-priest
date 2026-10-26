package shadowpriest.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import shadowpriest.relics.VoidformRelic;

public final class GainInsanityAction extends AbstractGameAction {
    public GainInsanityAction(int amount) { this.amount=amount; }
    @Override public void update() {
        VoidformRelic relic=VoidformRelic.current();
        if(relic!=null) relic.gainInsanity(amount);
        isDone=true;
    }
}
