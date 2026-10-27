package shadowpriest.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.rooms.AbstractRoom;

/** Keep the printed formula, with a combat-only parenthetical result. */
final class FormulaPreview {
    static boolean inCombat() {
        return AbstractDungeon.player != null && AbstractDungeon.currMapNode != null && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT;
    }
    static void set(AbstractCard card, String result) {
        CardStrings strings = CardCrawlGame.languagePack.getCardStrings(card.cardID);
        String base = card.upgraded && strings.UPGRADE_DESCRIPTION != null
                && !strings.UPGRADE_DESCRIPTION.isEmpty() ? strings.UPGRADE_DESCRIPTION : strings.DESCRIPTION;
        String text = base + (result == null || !inCombat() ? "" : " NL (" + result + ")");
        if (!text.equals(card.rawDescription)) { card.rawDescription = text; card.initializeDescription(); }
    }
}
