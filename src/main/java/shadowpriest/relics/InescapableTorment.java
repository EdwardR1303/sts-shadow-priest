package shadowpriest.relics;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import shadowpriest.powers.MasteryPower;

public class InescapableTorment extends PriestRelic {
    public static final String ID = "shadowpriest:InescapableTorment";
    public InescapableTorment() { super(ID, RelicTier.RARE, "shadowpriest/images/relics/icons/inescapable_torment.jpg"); }

    @Override public void atBattleStart() {
        AbstractPlayer p = AbstractDungeon.player;
        int curses = (int) p.masterDeck.group.stream().filter(c -> c.type == AbstractCard.CardType.CURSE).count();
        if (curses > 0) {
            flash();
            addToBot(new ApplyPowerAction(p, p, new MasteryPower(p, curses), curses));
        }
    }

    @Override public AbstractRelic makeCopy() { return new InescapableTorment(); }
}
