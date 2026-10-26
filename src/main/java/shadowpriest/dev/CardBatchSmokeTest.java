package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.mechanics.ReturningDots;
import shadowpriest.powers.*;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Opt-in checks using actual card plays and patched engine actions. */
public final class CardBatchSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m, other;
    private static VoidformRelic relic;
    private static AbstractCard card, secondCard, selection;
    private static AbstractCard previewCard;
    private static float previewSeconds;
    private static boolean previewObserved;
    private static int hp, enemyHp, otherHp, turn;

    public static void handleSelection() {
        if (previewCard != null && p != null && p.limbo.contains(previewCard)) {
            previewSeconds += com.badlogic.gdx.Gdx.graphics.getDeltaTime();
            if (!previewObserved) {
                check(previewCard.current_y > 0f && previewCard.drawScale >= 0.5f
                    && previewCard.current_x == previewCard.target_x
                    && !p.hasPower(PsychicLinkPower.POWER_ID),
                    "Foresight+ displays the chosen Power on screen before applying it");
                previewObserved = true;
            }
        }
        if (selection != null && AbstractDungeon.screen == AbstractDungeon.CurrentScreen.GRID) {
            check(AbstractDungeon.gridSelectScreen.targetGroup.group.stream()
                .allMatch(c -> c.type == AbstractCard.CardType.POWER), "Foresight chooser contains only Powers");
            check(AbstractDungeon.gridSelectScreen.targetGroup.contains(selection), "Foresight allows choosing the intended Power");
            AbstractDungeon.gridSelectScreen.selectedCards.add(selection);
            selection = null;
            AbstractDungeon.closeCurrentScreen();
        }
    }
    public static void append(Queue<Runnable> q) {
        if (Boolean.getBoolean("shadowpriest.latestbatchonly")) {
            q.add(() -> {
                p = AbstractDungeon.player; m = AbstractDungeon.getMonsters().monsters.get(0);
                other = AbstractDungeon.getMonsters().monsters.get(1); relic = VoidformRelic.current(); reset();
            });
            SixthBatchSmokeTest.append(q);
        ReworkSmokeTest.append(q);
            q.add(() -> CombatSmokeTest.finish(null));
            return;
        }
        q.add(() -> {
            p = AbstractDungeon.player; m = AbstractDungeon.getMonsters().monsters.get(0);
            other = AbstractDungeon.getMonsters().monsters.get(1); relic = VoidformRelic.current();
            reset();
            check(AbstractDungeon.commonCardPool.size() == 21 && AbstractDungeon.uncommonCardPool.size() == 19,
                "Reward pools contain twenty custom commons and fourteen custom uncommons");
            check(AbstractDungeon.commonCardPool.group.stream().allMatch(c -> c.cardID.startsWith("shadowpriest:"))
                && AbstractDungeon.uncommonCardPool.group.stream().allMatch(c -> c.cardID.startsWith("shadowpriest:")),
                "Normal reward pools use the new Shadow Priest cards");
            for (String id : new String[]{"Bulwark","FlashHeal","Fade","MindFlay","ShadowWordDeath","MindBlast",
                "Dispersion","DesperatePrayer","Foresight","MindFlayInsanity","MindSear","PsychicLink"}) {
                AbstractCard c = CardLibrary.getCard("shadowpriest:" + id).makeCopy();
                c.upgrade(); AbstractCard copy = c.makeStatEquivalentCopy();
                check(copy.upgraded && copy.cardID.equals(c.cardID) && copy.cost == c.cost
                    && copy.baseDamage == c.baseDamage && copy.baseMagicNumber == c.baseMagicNumber
                    && copy.baseBlock == c.baseBlock && copy.selfRetain == c.selfRetain && copy.exhaust == c.exhaust,
                    id + " registers and preserves upgraded values in copies");
            }
            card = new VampiricTouch(); secondCard = new ShadowWordPain();
            p.hand.addToTop(secondCard);
            for (int i = 0; i < 20; i++) p.drawPile.addToTop(new PowerWordShield());
            play(card, m, 3);
        });
        q.add(() -> endTurn(true));
        q.add(() -> {
            check(GameActionManager.turn > turn && p.hand.contains(card), "A played DoT returns after actual end-turn discard");
            check(!p.hand.contains(secondCard) && p.discardPile.contains(secondCard), "An unplayed DoT discards normally");
            endTurn(true);
        });
        q.add(() -> {
            check(!p.hand.contains(card) && p.discardPile.contains(card), "Returning DoT must be played again to return a second time");
            reset(); card = new VampiricTouch(); play(card, m, 3);
        });
        q.add(() -> {
            p.discardPile.moveToExhaustPile(card);
            endTurn(true);
        });
        q.add(() -> {
            check(p.exhaustPile.contains(card) && !p.hand.contains(card), "Return does not recover an exhausted DoT");
            reset(); card = new VampiricTouch(); play(card, m, 3);
        });
        q.add(() -> {
            for (int i = 0; i < 10; i++) {
                AbstractCard retained = new PowerWordShield(); retained.selfRetain = true; p.hand.addToTop(retained);
            }
            endTurn(true);
        });
        q.add(() -> {
            check(p.hand.size() == 10 && p.discardPile.contains(card), "Full hand leaves the returning DoT in discard without duplication");
            reset(); card = upgraded(new Bulwark()); play(card, null, 3);
        });
        q.add(() -> {
            check(p.currentBlock == 15 && p.getPower("Blur").amount == 1 && EnergyPanel.totalCount == 1,
                "Bulwark+ grants 15 Block and one Blur for two energy");
            endTurn(true);
        });
        q.add(() -> {
            check(p.currentBlock == 15 && !p.hasPower("Blur"), "Bulwark retains Block through one turn boundary");
            endTurn(true);
        });
        q.add(() -> {
            check(p.currentBlock == 0, "Bulwark does not retain Block for a second turn");
            reset(); card = new FlashHeal(); play(card, null, 3);
        });
        q.add(() -> {
            check(p.currentHealth == 206 && card.cost == 2 && EnergyPanel.totalCount == 2,
                "Flash Heal heals six, pays its old cost, then raises its own cost");
            play(card, null, 3);
        });
        q.add(() -> {
            check(p.currentHealth == 212 && card.cost == 3 && EnergyPanel.totalCount == 1,
                "Repeated Flash Heal costs two and rises to three");
            check(new FlashHeal().cost == 1 && card.makeCopy().cost == 1, "Fresh Flash Heal copies start at one energy");
            check(card.makeStatEquivalentCopy().cost == 3, "Combat copies preserve Flash Heal's increased cost");
            reset(); card = upgraded(new FlashHeal()); play(card, null, 3);
        });
        q.add(() -> {
            check(p.currentHealth == 209 && card.cost == 2, "Flash Heal+ heals nine and still increases cost");
            reset(); play(upgraded(new Fade()), null, 3);
        });
        q.add(() -> {
            hp = p.currentHealth; p.addBlock(5);
            p.damage(new DamageInfo(m, 8, DamageInfo.DamageType.NORMAL));
            check(p.currentHealth == hp - 1 && p.currentBlock == 0, "Fade+ reduces HP loss after Block");
            hp = p.currentHealth;
            p.damage(new DamageInfo(p, 3, DamageInfo.DamageType.HP_LOSS));
            check(p.currentHealth == hp - 1, "Fade+ also reduces self-inflicted HP loss");
            p.powers.add(new IntangiblePlayerPower(p, 1)); hp = p.currentHealth;
            p.damage(new DamageInfo(m, 40, DamageInfo.DamageType.NORMAL));
            check(p.currentHealth == hp, "Fade and Intangible reduce an incoming hit to zero");
            endTurn(true);
        });
        q.add(() -> {
            check(!p.hasPower(FadePower.POWER_ID), "Fade expires at the next player turn");
            reset(); dots(m); enemyHp = m.currentHealth; play(new MindFlay(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 6 && relic.state.amount() == 30, "Mind Flay hits three times for six damage and 30 Insanity");
            reset(); dots(m); p.powers.add(new StrengthPower(p, 2)); enemyHp = m.currentHealth;
            play(upgraded(new MindFlay()), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 16 && relic.state.amount() == 40, "Mind Flay+ has four Strength-scaled hits and 40 Insanity");
            reset(); dots(m); enemyHp = m.currentHealth; play(new MindBlast(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 14 && relic.state.amount() == 10, "Mind Blast adds damage for two distinct DoTs, but is one hit");
            reset(); dots(m); relic.state.gain(100); relic.activate(relic.state.charge());
        });
        q.add(() -> { enemyHp = m.currentHealth; play(upgraded(new MindBlast()), m, 0); });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 30 && EnergyPanel.totalCount == 0, "Mind Blast+ previews and deals 30 damage for zero energy in Voidform");
            reset(); hp = p.currentHealth; enemyHp = m.currentHealth; play(upgraded(new ShadowWordDeath()), m, 0);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 20 && p.currentHealth == hp - 6, "Death+ has two hits and three HP backlash per eligible hit");
            reset(); p.powers.add(new FadePower(p, 2)); hp = p.currentHealth;
            play(upgraded(new ShadowWordDeath()), m, 0);
        });
        q.add(() -> {
            check(p.currentHealth == hp - 2, "Fade+ reduces Death+ backlash to one HP per hit");
            reset(); m.currentHealth = 305; hp = p.currentHealth;
            play(upgraded(new ShadowWordDeath()), m, 0);
        });
        q.add(() -> {
            check(m.currentHealth == 285 && p.currentHealth == hp - 3, "Death+ reevaluates the health threshold before each hit");
            reset(); m.addBlock(40); hp = p.currentHealth; play(new ShadowWordDeath(), m, 0);
        });
        q.add(() -> {
            check(p.currentHealth == hp, "A fully blocked Death hit does not charge backlash");
            reset(); dots(m); enemyHp = m.currentHealth; play(new MindFlayInsanity(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 18 && relic.state.amount() == 30 && EnergyPanel.totalCount == 0,
                "Mind Flay: Insanity spends three energy for three normal hits");
            check(m.getPower(ShadowDotPower.TOUCH).amount == 5 && m.getPower(ShadowDotPower.PAIN).amount == 5,
                "Mind Flay: Insanity extends each existing DoT up to five without changing its damage");
            reset(); dots(m); enemyHp = m.currentHealth; play(upgraded(new MindFlayInsanity()), m, 0);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 6 && m.getPower(ShadowDotPower.TOUCH).amount == 4
                && relic.state.amount() == 10, "Upgraded X=0 Mind Flay adds one turn and one hit");
            reset(); dots(m); card = new MindFlayInsanity(); card.freeToPlayOnce = true; enemyHp = m.currentHealth;
            play(card, m, 2);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 12 && EnergyPanel.totalCount == 2, "Free-to-play X card uses the energy amount without spending it");
            reset(); dots(m); dots(other); enemyHp = m.currentHealth; otherHp = other.currentHealth;
            play(new MindSear(), null, 2);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 10 && other.currentHealth == otherHp - 10 && relic.state.amount() == 40
                && EnergyPanel.totalCount == 0, "Mind Sear deals repeated area damage and counts each target's DoTs");
            reset(); p.relics.add(new ChemicalX()); dots(m); enemyHp = m.currentHealth;
            play(upgraded(new MindSear()), null, 0);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 15 && relic.state.amount() == 30, "Chemical X and upgraded X=0 add three Mind Sear hits");
            p.relics.removeIf(r -> r instanceof ChemicalX);
            reset(); card = upgraded(new Dispersion()); turn = GameActionManager.turn;
            play(card, null, 3);
        });
        q.add(() -> {
            check(GameActionManager.turn > turn && p.exhaustPile.contains(card), "Dispersion+ actually ends the turn and Exhausts");
            check(card.selfRetain && card.cost == 2, "Dispersion upgrade adds Retain without changing its two-energy cost");
            reset(); play(new DesperatePrayer(), null, 3);
        });
        q.add(() -> {
            hp = p.currentHealth; p.addBlock(8);
            p.damage(new DamageInfo(m, 5, DamageInfo.DamageType.NORMAL));
            check(p.hasPower(DesperatePrayerPower.POWER_ID), "Fully blocked attacks do not consume Desperate Prayer");
            p.loseBlock();
            p.damage(new DamageInfo(p, 3, DamageInfo.DamageType.HP_LOSS));
            check(p.hasPower(DesperatePrayerPower.POWER_ID), "Self-damage does not consume Desperate Prayer");
            hp = p.currentHealth;
            p.damage(new DamageInfo(m, 9, DamageInfo.DamageType.NORMAL));
            check(p.currentHealth == hp - 9, "Prayer allows HP loss before its queued heal");
        });
        q.add(() -> {
            check(p.currentHealth == hp && !p.hasPower(DesperatePrayerPower.POWER_ID), "Prayer restores actual attack HP loss and consumes itself");
            p.damage(new DamageInfo(m, 7, DamageInfo.DamageType.NORMAL));
            check(p.currentHealth == hp - 7, "Subsequent enemy hits are not healed");
            reset(); p.powers.add(new FadePower(p, 2)); play(upgraded(new DesperatePrayer()), null, 0);
        });
        q.add(() -> {
            hp = p.currentHealth; p.addBlock(3);
            p.damage(new DamageInfo(m, 9, DamageInfo.DamageType.NORMAL));
            check(p.currentHealth == hp - 4, "Prayer+ observes damage after Block and Fade");
        });
        q.add(() -> {
            check(p.currentHealth == hp && EnergyPanel.totalCount == 0, "Prayer+ heals only the four HP lost for zero energy");
            reset(); play(new DesperatePrayer(), null, 3);
        });
        q.add(() -> endTurn(true));
        q.add(() -> {
            check(!p.hasPower(DesperatePrayerPower.POWER_ID), "Unused Prayer expires at the next player turn");
            reset(); card = new PsychicLink(); p.drawPile.addToTop(card); p.drawPile.addToTop(new MindSpike());
            selection = card;
            play(new Foresight(), null, 3);
        });
        q.add(() -> {
            check(p.hand.contains(card) && !p.drawPile.contains(card) && EnergyPanel.totalCount == 3
                && !p.hasPower(PsychicLinkPower.POWER_ID) && selection == null,
                "Base Foresight confirms its only Power, fetching it without playing or spending energy");
            reset(); card = new PsychicLink(); secondCard = new PsychicLink();
            p.drawPile.addToTop(card); p.drawPile.addToTop(secondCard); p.drawPile.addToTop(new MindSpike()); selection = secondCard;
            previewCard = secondCard;
            play(upgraded(new Foresight()), null, 0);
        });
        q.add(() -> {
            check(p.hasPower(PsychicLinkPower.POWER_ID) && p.getPower(PsychicLinkPower.POWER_ID).amount == 1
                && EnergyPanel.totalCount == 0 && p.drawPile.contains(card) && !p.drawPile.contains(secondCard),
                "Foresight+ selects and plays the chosen Power for free");
            check(p.discardPile.group.stream().anyMatch(c -> c instanceof Foresight)
                && !p.discardPile.contains(secondCard), "Foresight remains reusable while the played Power leaves the card cycle");
            check(previewObserved && previewSeconds >= 0.2f && !p.limbo.contains(secondCard),
                "Foresight+ gives a readable preview even in Fast Mode and cleans up limbo");
            reset(); card = new PsychicLink(); p.drawPile.addToTop(card); selection = card; previewCard = card;
            for (int i = 0; i < basemod.BaseMod.MAX_HAND_SIZE; i++) p.hand.addToTop(new MindSpike());
            play(upgraded(new Foresight()), null, 0);
        });
        q.add(() -> {
            check(selection == null && previewObserved && previewSeconds >= 0.2f
                && p.hasPower(PsychicLinkPower.POWER_ID) && !p.drawPile.contains(card)
                && !p.limbo.contains(card) && p.hand.size() == basemod.BaseMod.MAX_HAND_SIZE
                && EnergyPanel.totalCount == 0,
                "Foresight+ confirms and visibly plays its only Power for free with a full hand");
            reset(); p.drawPile.addToTop(new MindSpike()); card = new Foresight(); play(card, null, 0);
        });
        q.add(() -> {
            check(p.discardPile.contains(card) && p.drawPile.size() == 1 && !AbstractDungeon.isScreenUp,
                "Foresight without a Power is a harmless dead draw, with no stuck selection screen");
            reset(); play(new PsychicLink(), null, 3);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 1 && p.hasPower(PsychicLinkPower.POWER_ID), "Psychic Link costs two and installs its Power");
            dots(m); other.powers.add(new ShadowDotPower(other, ShadowDotPower.TOUCH, 3));
            enemyHp = m.currentHealth; otherHp = other.currentHealth;
            play(upgraded(new MindFlay()), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 13 && other.currentHealth == otherHp - 5,
                "Psychic Link triggers once per Attack card, not once per hit, on all Touch targets");
            check(m.getPower(ShadowDotPower.TOUCH).amount == 5 && other.getPower(ShadowDotPower.TOUCH).amount == 3
                && relic.state.amount() == 40, "Link bonus ticks preserve duration and generate no extra Insanity");
            relic.reset(); p.powers.add(new StrengthPower(p, 20)); p.powers.add(new WeakPower(p, 2, false));
            m.addBlock(999); other.addBlock(999); enemyHp = m.currentHealth; otherHp = other.currentHealth;
            play(new MindSpike(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp - 5 && other.currentHealth == otherHp - 5,
                "Link damage ignores attack modifiers and bypasses enemy Block");
            enemyHp = m.currentHealth; otherHp = other.currentHealth; play(new PowerWordShield(), null, 3);
        });
        q.add(() -> {
            check(m.currentHealth == enemyHp && other.currentHealth == otherHp, "Skills do not trigger Psychic Link");
            reset(); play(upgraded(new PsychicLink()), null, 3);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 2, "Psychic Link+ costs one energy");
            dots(m); enemyHp = m.currentHealth; play(upgraded(new PsychicLink()), null, 3);
        });
        q.add(() -> play(new MindSpike(), m, 3));
        q.add(() -> {
            check(m.currentHealth == enemyHp - 16, "Two Psychic Links provide two bonus ticks without recursion");
        });
        SecondBatchSmokeTest.append(q);
        ThirdBatchSmokeTest.append(q);
        FourthBatchSmokeTest.append(q);
        SurrenderSmokeTest.append(q);
        FifthBatchSmokeTest.append(q);
        SixthBatchSmokeTest.append(q);
        ReworkSmokeTest.append(q);
        q.add(() -> {
            reset(); p.currentHealth = 5; play(new DesperatePrayer(), null, 3);
        });
        q.add(() -> {
            p.damage(new DamageInfo(m, 5, DamageInfo.DamageType.NORMAL));
            check(p.isDead && p.currentHealth == 0, "Desperate Prayer does not prevent an actually lethal enemy attack");
            // Resolve the recovery action explicitly because the death screen stops normal combat updates.
            for (AbstractGameAction a : new java.util.ArrayList<>(AbstractDungeon.actionManager.actions)) {
                if (a.getClass().getName().startsWith("shadowpriest.powers.DesperatePrayerPower")) a.update();
            }
            check(p.currentHealth == 0, "Prayer's queued recovery cannot resurrect a dead player");
            CombatSmokeTest.finish(null);
        });
    }

    static void reset() {
        shadowpriest.legendary.LegendaryEquipment.State.equipped.set(p,null);
        p.powers.clear(); p.gameHandSize=p.masterHandSize; TitheEvasion.Benefit.granted.set(p,0);
        com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager.removeAllBlockInstances(p);
        p.currentBlock = 0; p.currentHealth = 200; p.maxHealth = 500;
        p.hand.clear(); p.drawPile.clear(); p.discardPile.clear(); p.exhaustPile.clear(); p.limbo.clear();
        for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            monster.powers.clear();
            com.evacipated.cardcrawl.mod.stslib.blockmods.BlockModifierManager.removeAllBlockInstances(monster);
            monster.currentBlock = 0; monster.currentHealth = monster.maxHealth = 1000;
        }
        relic.reset(); BodyAndSoul.resetBenefit(); PowerWordFortitude.resetBenefit(); ReturningDots.clear(); selection = null;
        previewCard = null; previewSeconds = 0f; previewObserved = false;
    }
    static void dots(AbstractMonster target) {
        target.powers.add(new ShadowDotPower(target, ShadowDotPower.TOUCH, 3));
        target.powers.add(new ShadowDotPower(target, ShadowDotPower.PAIN, 5));
    }
    static <T extends AbstractCard> T upgraded(T c) { c.upgrade(); return c; }
    static void play(AbstractCard c, AbstractMonster target, int energy) {
        p.drawPile.removeCard(c); p.discardPile.removeCard(c);
        EnergyPanel.totalCount = energy; c.energyOnUse = energy;
        if (!p.hand.contains(c)) p.hand.addToTop(c);
        if (!c.canUse(p, target)) throw new AssertionError("Cannot play " + c.cardID + ": " + c.cantUseMessage);
        p.useCard(c, target, energy);
    }
    static void endTurn(boolean quietMonsters) {
        turn = GameActionManager.turn;
        // Run the actual enemy phase so atEndOfRound powers expire normally.
        // These controlled Cultists use an empty move rather than skipping the phase.
        AbstractDungeon.getCurrRoom().skipMonsterTurn = false;
        if (quietMonsters) for (AbstractMonster monster : AbstractDungeon.getMonsters().monsters) {
            monster.setMove((byte) 0, AbstractMonster.Intent.NONE);
            monster.createIntent();
        }
        AbstractDungeon.overlayMenu.endTurnButton.disable(true); p.endTurnQueued = true;
    }
}
