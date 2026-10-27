package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.ExhaustSpecificCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.MindSpike;
import shadowpriest.powers.HastePower;
import shadowpriest.relics.VoidVengeance;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Narrow real-engine coverage of Curse permission, payment, and exhaust hooks. */
public final class VoidVengeanceSmokeTest {
    private static AbstractPlayer p;
    private static VoidVengeance relic;
    private static AbstractCard played;
    private static int hp;
    private static int haste() { return p.hasPower(HastePower.POWER_ID) ? p.getPower(HastePower.POWER_ID).amount : 0; }
    private static void play(AbstractCard card) {
        p.hand.removeCard(card); p.hand.addToTop(card); EnergyPanel.totalCount = 0;
        check(card.canUse(p, null), card.name + " can be played at zero energy");
        if (card instanceof Pride) EnergyPanel.totalCount = 3;
        played = card; p.useCard(card, null, EnergyPanel.totalCount);
    }
    private static void exhaust(AbstractCard card) {
        p.hand.addToTop(card);
        AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(card, p.hand));
    }
    private static AbstractCard returnedCurse() {
        return p.hand.group.stream().filter(c -> c instanceof Necronomicurse).findFirst()
                .orElseThrow(() -> new AssertionError("Missing returned Necronomicurse"));
    }
    public static void append(Queue<Runnable> q) {
        q.add(() -> {
            p = AbstractDungeon.player; p.powers.clear();
            check(!new Regret().canUse(p, null), "Unplayable Curse stays unplayable without relic");
            relic = new VoidVengeance(); relic.instantObtain(p, p.relics.size(), false);
            p.masterDeck.addToTop(new Regret()); p.preBattlePrep();
        });
        q.add(() -> {
            check(p.drawPile.group.stream().anyMatch(c -> c instanceof Regret), "Starting Curse is no longer converted to Void Bolt");
            check(p.masterDeck.group.stream().anyMatch(c -> c instanceof Regret), "Permanent Curse remains intact");
            p.hand.clear(); p.drawPile.clear(); p.discardPile.clear(); p.powers.clear();
            hp = p.currentHealth;
            check(relic.counter == 1, "Combat starts with one free play");
            check(!new Dazed().canUse(p, null), "Status cards gain no play permission");
            play(new Regret());
        });
        q.add(() -> {
            check(p.exhaustPile.contains(played) && haste() == 2, "Played Curse exhausts and grants two Haste");
            check(p.currentHealth == hp && EnergyPanel.totalCount == 0, "Relic play spends no HP or energy");
            check(relic.counter == 0 && !new Regret().canUse(p, null), "Second Curse cannot use spent allowance");
            exhaust(new CurseOfTheBell());
        });
        q.add(() -> {
            check(haste() == 4 && relic.counter == 0, "External exhaust grants Haste after allowance spent");
            exhaust(new Dazed());
        });
        q.add(() -> {
            check(haste() == 4, "Status exhaust grants no Haste");
            p.powers.clear(); p.hand.clear(); p.hand.addToTop(new AscendersBane());
            for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) { m.setMove((byte)0, AbstractMonster.Intent.NONE); m.createIntent(); }
            AbstractDungeon.getCurrRoom().skipMonsterTurn = false;
            AbstractDungeon.overlayMenu.endTurnButton.disable(true); p.endTurnQueued = true;
        });
        q.add(() -> {
            check(p.exhaustPile.group.stream().anyMatch(c -> c instanceof AscendersBane) && haste() == 2,
                    "Ethereal Curse grants Haste through actual end-turn exhaustion");
            check(relic.counter == 1, "Actual next turn refreshes free play");
            exhaust(new Clumsy());
        });
        q.add(() -> {
            check(haste() == 4 && relic.counter == 1, "External exhaust does not consume available free play");
            EnergyPanel.totalCount = 0;
            check(!new MindSpike().canUse(p, AbstractDungeon.getMonsters().monsters.get(0)), "Ordinary cards do not gain free payment");
            p.hand.clear(); p.powers.clear();
            play(new Pride());
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 3 && p.exhaustPile.contains(played) && haste() == 2,
                    "Normally one-energy Pride plays free and exhausts");
            check(played.cost == 1 && played.costForTurn == 1 && !played.freeToPlayOnce,
                    "Curse cost and one-use free flag are not permanently changed");
            EnergyPanel.totalCount = 0;
            check(!new Pride().canUse(p, null), "Paid Curse needs energy after free allowance spent");
            relic.atTurnStart(); p.hand.clear();
            Normality normality = new Normality(); p.hand.addToTop(normality);
            AbstractDungeon.actionManager.cardsPlayedThisTurn.clear();
            for (int i = 0; i < 3; i++) AbstractDungeon.actionManager.cardsPlayedThisTurn.add(new MindSpike());
            check(!normality.canUse(p, null) && relic.counter == 1, "Normality restriction is respected without spending allowance");
            p.hand.clear(); AbstractDungeon.actionManager.cardsPlayedThisTurn.clear();
            AbstractDungeon.actionManager.turnHasEnded = true;
            check(!new Regret().canUse(p, null), "Turn-end play restriction is respected");
            AbstractDungeon.actionManager.turnHasEnded = false;
            p.powers.clear(); play(new Necronomicurse());
        });
        q.add(() -> {
            check(haste() == 2 && !returnedCurse().canUse(p, null), "Returned Necronomicurse cannot reuse relic allowance");
            new BlueCandle().instantObtain(p, p.relics.size(), false); hp = p.currentHealth;
            play(returnedCurse());
        });
        q.add(() -> {
            check(haste() == 4 && p.currentHealth == hp - 1 && relic.counter == 0,
                    "Blue Candle permits another exhaust, pays HP, and grants Haste");
            play(returnedCurse());
        });
        q.add(() -> {
            check(haste() == 6 && p.currentHealth == hp - 2, "Repeated returned copies keep granting Haste without a cap");
            relic.atTurnStart(); play(returnedCurse());
        });
        q.add(() -> {
            check(haste() == 8 && p.currentHealth == hp - 3 && relic.counter == 0,
                    "Blue Candle keeps its HP cost on the first Curse play too");
            relic.onVictory(); check(relic.counter == -1, "Victory clears combat allowance");
            relic.atPreBattle(); check(relic.counter == 1, "Following combat restores allowance");
        });
    }
}
