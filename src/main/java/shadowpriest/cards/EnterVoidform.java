package shadowpriest.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.relics.VoidformRelic;

public class EnterVoidform extends PriestCard {
    public static final String ID = "shadowpriest:EnterVoidform";
    public final int charge;
    public EnterVoidform() { this(-1); }
    public EnterVoidform(int charge) {
        super(ID, 0, CardType.SKILL, CardRarity.SPECIAL, CardTarget.SELF);
        this.charge = charge; selfRetain = true; exhaust = true;
    }
    @Override public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;
        VoidformRelic relic = VoidformRelic.current();
        if (relic == null || !relic.state.ready() || relic.state.charge() != charge) {
            cantUseMessage = "This Voidform charge is no longer ready.";
            return false;
        }
        return true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        VoidformRelic relic = VoidformRelic.current();
        if (relic != null && relic.state.ready() && relic.state.charge()==charge) {
            relic.activate(charge);
            addToBot(new com.megacrit.cardcrawl.actions.common.DrawCardAction(p,1));
        }
    }
    @Override public AbstractCard makeCopy() { return new EnterVoidform(charge); }
    @Override public void upgrade() { }
    @Override public boolean canUpgrade() { return false; }
}
