package shadowpriest.cards;

import basemod.abstracts.DynamicVariable;
import com.megacrit.cardcrawl.cards.AbstractCard;

public final class DotTurnsVariable extends DynamicVariable {
    @Override public String key() { return "shadowpriest:Turns"; }
    @Override public boolean isModified(AbstractCard card) { return false; }
    @Override public int value(AbstractCard card) { return ((ShadowDotCard) card).turns(); }
    @Override public int baseValue(AbstractCard card) { return value(card); }
    @Override public boolean upgraded(AbstractCard card) { return card.upgraded; }
}
