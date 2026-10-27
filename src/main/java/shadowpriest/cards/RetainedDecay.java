package shadowpriest.cards;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.Decay;
/** A combat-generated vanilla Decay with Retain; never registered in reward pools. */
public class RetainedDecay extends Decay {
    public RetainedDecay(){super();selfRetain=true;rawDescription="Retain. NL "+rawDescription;initializeDescription();}
    @Override public AbstractCard makeCopy(){return new RetainedDecay();}
}
