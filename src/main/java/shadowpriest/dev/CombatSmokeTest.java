package shadowpriest.dev;

import basemod.devcommands.ConsoleCommand;
import com.badlogic.gdx.Gdx;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.Bash;
import com.megacrit.cardcrawl.cards.red.Whirlwind;
import com.megacrit.cardcrawl.cards.red.TwinStrike;
import com.megacrit.cardcrawl.cards.red.Cleave;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.character.ShadowPriest;
import shadowpriest.powers.ShadowDotPower;
import shadowpriest.relics.VoidformRelic;

import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

/** Opt-in integration checks in the real patched game. Never runs in a normal launch. */
public final class CombatSmokeTest {
    private static boolean started, fightRequested, initialized, finished;
    private static final Queue<Runnable> steps = new ArrayDeque<>();
    private static final List<String> results = new ArrayList<>();
    private static AbstractPlayer p;
    private static AbstractMonster m;
    private static AbstractMonster second;
    private static VoidformRelic relic;
    private static EnterVoidform token;
    private static int hp, energy, turn;
    private static float delay;
    private static long began = System.currentTimeMillis();

    public static void update() {
        if (finished) return;
        try {
            if (System.currentTimeMillis() - began > 180000) throw new AssertionError("Timed out waiting for engine; remaining steps: " + steps.size());
            if (!started && CardCrawlGame.mainMenuScreen != null && CardCrawlGame.mode == CardCrawlGame.GameMode.CHAR_SELECT) {
                started = true;
                Settings.seed = 123456789L; Settings.seedSet = true; Settings.isDailyRun = false; Settings.isTrial = false;
                AbstractDungeon.isAscensionMode = false; AbstractDungeon.ascensionLevel = 0;
                CardCrawlGame.chosenCharacter = ShadowPriest.Meta.SHADOW_PRIEST;
                CardCrawlGame.loadingSave = false;
                AbstractDungeon.generateSeeds();
                CardCrawlGame.mainMenuScreen.isFadingOut = true;
                CardCrawlGame.mainMenuScreen.fadeOutMusic();
                return;
            }
            if (!started || AbstractDungeon.player == null || CardCrawlGame.dungeon == null || AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom() == null) return;
            if (!fightRequested) {
                // Let the initial transition finish; requesting a fight earlier can be overwritten by Neow entry.
                if (CardCrawlGame.dungeonTransitionScreen != null || !(AbstractDungeon.getCurrRoom() instanceof com.megacrit.cardcrawl.neow.NeowRoom)) return;
                delay += Gdx.graphics.getDeltaTime();
                if (delay < 2f) return;
                delay = 0;
                fightRequested = true;
                AbstractDungeon.closeCurrentScreen();
                ConsoleCommand.execute(new String[]{"fight", "Cultist"});
                return;
            }
            if (AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;
            if (AbstractDungeon.actionManager.currentAction != null || !AbstractDungeon.actionManager.actions.isEmpty()
                || !AbstractDungeon.actionManager.cardQueue.isEmpty() || !AbstractDungeon.actionManager.monsterQueue.isEmpty()
                || AbstractDungeon.actionManager.turnHasEnded || AbstractDungeon.player.isEndingTurn
                || VoidformRelic.pendingCardDelivery()) return;
            delay += Gdx.graphics.getDeltaTime();
            if (delay < 0.15f) return;
            delay = 0;
            if (!initialized) { initialize(); initialized = true; }
            if (steps.isEmpty()) { finish(null); return; }
            steps.remove().run();
        } catch (Throwable failure) { finish(failure); }
    }

    private static void initialize() {
        p = AbstractDungeon.player; m = AbstractDungeon.getMonsters().monsters.get(0); relic = VoidformRelic.current();
        check(p instanceof ShadowPriest && relic != null, "Custom character and starting relic load");
        check(p.masterDeck.size() == 10, "Ten-card starter deck loads");
        check(AbstractDungeon.commonCardPool.size() > 0 && AbstractDungeon.uncommonCardPool.size() > 0 && AbstractDungeon.rareCardPool.size() > 0, "Prototype reward pools are populated");
        p.hand.clear(); p.drawPile.clear(); p.discardPile.clear(); p.exhaustPile.clear(); p.limbo.clear();
        m.powers.clear(); m.maxHealth = m.currentHealth = 1000; p.maxHealth = p.currentHealth = 500; relic.reset();
        steps.add(() -> play(new VampiricTouch(), m));
        steps.add(() -> play(new ShadowWordPain(), m));
        steps.add(() -> {
            check(ShadowDotPower.count(m) == 2, "Distinct DoTs coexist");
            check(m.currentHealth == 1000, "Applying DoTs does not tick immediately");
            m.currentBlock = 40;
            p.powers.add(new StrengthPower(p, 10)); p.powers.add(new WeakPower(p, 2, false));
            m.powers.add(new VulnerablePower(m, 2, false));
            hp = m.currentHealth; tickDots();
        });
        steps.add(() -> {
            check(m.currentHealth == hp - 10 && m.currentBlock == 40, "DoTs bypass Block and ignore Strength, Weak, and Vulnerable");
            check(m.getPower(ShadowDotPower.TOUCH).amount == 2 && relic.state.amount() == 0, "DoTs reduce duration and generate no Insanity");
            p.powers.clear(); m.powers.removeIf(power -> !(power instanceof ShadowDotPower));
            hp = m.currentHealth; play(new VampiricTouch(), m);
        });
        steps.add(() -> {
            check(m.getPower(ShadowDotPower.TOUCH).amount == 3 && m.getPower(ShadowDotPower.PAIN).amount == 2 && m.currentHealth == hp, "Reapplication refreshes only its own duration without ticking");
            m.currentBlock = 0;
            // Restore both durations so exactly three tick cycles can be checked.
            play(new ShadowWordPain(), m);
        });
        steps.add(() -> { hp = m.currentHealth; tickDots(); });
        steps.add(CombatSmokeTest::tickDots);
        steps.add(CombatSmokeTest::tickDots);
        steps.add(() -> {
            check(m.currentHealth == hp - 30 && ShadowDotPower.count(m) == 0, "Both DoTs expire after exactly three 5-damage ticks");
            add(new ApplyPowerAction(m, p, new ArtifactPower(m, 1), 1));
        });
        steps.add(() -> play(new VampiricTouch(), m));
        steps.add(() -> {
            check(!m.hasPower(ShadowDotPower.TOUCH) && !m.hasPower(ArtifactPower.POWER_ID), "Artifact blocks a DoT application");
            play(new VampiricTouch(), m);
        });
        steps.add(() -> play(new ShadowWordPain(), m));
        steps.add(() -> { m.currentBlock = 999; play(new MindSpike(), m); });
        steps.add(() -> play(new MindSpike(), m));
        steps.add(() -> play(new MindSpike(), m));
        steps.add(() -> {
            check(relic.state.amount() == 6, "Three fully blocked Mind Spikes into two DoTs generate 6 Insanity");
            turn = com.megacrit.cardcrawl.actions.GameActionManager.turn;
            AbstractDungeon.overlayMenu.endTurnButton.disable(true); p.endTurnQueued = true;
        });
        steps.add(() -> {
            check(com.megacrit.cardcrawl.actions.GameActionManager.turn > turn && relic.state.amount() == 6, "Insanity persists across an actual enemy turn");
            play(new MindSpike(), m);
        });
        steps.add(() -> play(new MindSpike(), m));
        steps.add(() -> {
            token = findToken();
            check(relic.state.ready() && !relic.state.active() && token != null, "Threshold creates an on-demand card without activating");
            check(token.cost == 0 && token.selfRetain && token.exhaust, "Activation card costs 0, Retains, and Exhausts");
            turn = com.megacrit.cardcrawl.actions.GameActionManager.turn;
            AbstractDungeon.overlayMenu.endTurnButton.disable(true); p.endTurnQueued = true;
        });
        steps.add(() -> {
            check(com.megacrit.cardcrawl.actions.GameActionManager.turn > turn && p.hand.contains(token) && relic.state.ready(), "Ready card survives an actual end turn");
            play(token, null);
        });
        steps.add(() -> {
            check(relic.state.active() && p.exhaustPile.contains(token), "Playing the card activates Voidform and exhausts it");
            check(!token.makeCopy().canUse(p, null), "Copied activation cannot reuse an active charge");
            m.powers.clear(); m.currentBlock = 0;
            MindSpike spike = new MindSpike(); spike.calculateCardDamage(m);
            check(spike.damage == 9 && spike.costForTurn == 1 && VoidformRelic.effectiveCost(spike, spike.costForTurn) == 0, "Voidform previews 9 damage and discounts without mutating base cost");
            p.hand.addToTop(spike); p.energy.energy = 0; EnergyPanel.totalCount = 0;
            check(spike.canUse(p, m), "Discounted Mind Spike is playable at zero energy");
            hp = m.currentHealth; p.useCard(spike, m, 0);
        });
        steps.add(() -> {
            check(m.currentHealth == hp - 9 && EnergyPanel.totalCount == 0, "Mind Spike deals 9 and spends zero energy in engine");
            Bash bash = new Bash(); p.hand.addToTop(bash); EnergyPanel.totalCount = 3;
            energy = EnergyPanel.totalCount; p.useCard(bash, m, energy);
        });
        steps.add(() -> {
            check(EnergyPanel.totalCount == energy - 1, "A base-game 2-cost Attack spends 1 energy in Voidform");
            check(VoidformRelic.effectiveCost(new Whirlwind(), -1) == -1, "X-cost attacks remain unchanged");
            Bash modified = new Bash(); modified.setCostForTurn(1);
            p.hand.addToTop(modified); EnergyPanel.totalCount = 0;
            check(modified.canUse(p, m), "Voidform combines with an existing temporary cost reduction");
            p.useCard(modified, m, 0);
        });
        steps.add(() -> {
            check(EnergyPanel.totalCount == 0, "Temporarily discounted Attack spends zero energy");
            turn = com.megacrit.cardcrawl.actions.GameActionManager.turn;
            AbstractDungeon.overlayMenu.endTurnButton.disable(true); p.endTurnQueued = true;
        });
        steps.add(() -> {
            check(com.megacrit.cardcrawl.actions.GameActionManager.turn > turn && !relic.state.active() && relic.state.amount() == 0, "Actual end turn expires Voidform and resets Insanity");
            MindSpike spike = new MindSpike(); spike.applyPowers();
            check(spike.damage == 6 && VoidformRelic.effectiveCost(spike, 1) == 1, "Attack cost and damage return to normal");
            p.hand.clear(); for (int i=0; i<10; i++) p.hand.addToTop(new PowerWordShield());
            relic.state.gain(10);
        });
        steps.add(() -> check(findToken() == null && relic.state.ready(), "A full hand preserves the earned charge"));
        steps.add(() -> p.hand.removeTopCard());
        steps.add(() -> {
            check(findToken() != null && p.hand.size() == 10, "Activation arrives when a hand slot opens");
            relic.onVictory();
            check(relic.state.amount() == 0 && !relic.state.ready(), "Victory resets Insanity and invalidates ready cards");
        });
        steps.add(() -> {
            p.hand.clear(); p.currentBlock = 0; play(new PowerWordShield(), null);
        });
        steps.add(() -> {
            check(p.currentBlock == 5 && EnergyPanel.totalCount == 2, "Power Word: Shield grants 5 Block for 1 energy");
            m.powers.clear(); m.currentBlock = 0;
            m.powers.add(new ShadowDotPower(m, ShadowDotPower.TOUCH, 3));
            m.powers.add(new ShadowDotPower(m, ShadowDotPower.PAIN, 3));
            m.powers.add(new IntangiblePower(m, 1));
            hp = m.currentHealth; tickDots();
        });
        steps.add(() -> {
            check(m.currentHealth == hp - 2 && relic.state.amount() == 0, "Intangible reduces each DoT tick to 1 without generating Insanity");
            m.powers.removeIf(power -> !(power instanceof ShadowDotPower));
            play(new TwinStrike(), m);
        });
        steps.add(() -> {
            check(relic.state.amount() == 4, "A two-hit Attack into two DoTs generates 4 Insanity");
            relic.reset();
            second = new Cultist(300f, 0f); second.maxHealth = second.currentHealth = 1000;
            add(new SpawnMonsterAction(second, false));
        });
        steps.add(() -> {
            second.powers.add(new ShadowDotPower(second, ShadowDotPower.TOUCH, 3));
            play(new Cleave(), null);
        });
        steps.add(() -> {
            check(relic.state.amount() == 3, "An area Attack counts each target's distinct DoTs (2 plus 1)");
        });
        steps.add(() -> {
            p.hand.clear(); relic.reset(); m.powers.clear(); m.currentBlock = 0;
            for (PriestCard card : new PriestCard[]{new MindSpike(), new PowerWordShield(), new VampiricTouch(), new ShadowWordPain()}) {
                com.badlogic.gdx.graphics.Texture portrait = basemod.abstracts.CustomCard.getPortraitImage(card);
                check(card.portrait.getRegionWidth() == 250 && card.portrait.getRegionHeight() == 190
                    && portrait.getWidth() == 500 && portrait.getHeight() == 380, card.name + " has small and inspection artwork");
            }
            VampiricTouch touch = new VampiricTouch(); touch.upgrade();
            ShadowDotCard copy = (ShadowDotCard) touch.makeStatEquivalentCopy();
            check(copy.magicNumber == 6 && copy.turns() == 4 && copy.cost == 1, "Upgraded DoT copies preserve 6 damage, 4 turns, and 1 energy");
            play(touch, m);
        });
        steps.add(() -> { ShadowWordPain pain = new ShadowWordPain(); pain.upgrade(); play(pain, m); });
        steps.add(() -> {
            check(dot(ShadowDotPower.TOUCH).damagePerTurn == 6 && dot(ShadowDotPower.TOUCH).amount == 4
                && dot(ShadowDotPower.PAIN).damagePerTurn == 6 && dot(ShadowDotPower.PAIN).amount == 4,
                "Both upgraded DoTs apply 6 damage for 4 turns");
            p.powers.add(new StrengthPower(p, 10)); p.powers.add(new WeakPower(p, 2, false));
            m.powers.add(new VulnerablePower(m, 2, false)); m.currentBlock = 40;
            hp = m.currentHealth; tickDots();
        });
        steps.add(CombatSmokeTest::tickDots);
        steps.add(CombatSmokeTest::tickDots);
        steps.add(CombatSmokeTest::tickDots);
        steps.add(() -> {
            check(m.currentHealth == hp - 48 && m.currentBlock == 40 && ShadowDotPower.count(m) == 0 && relic.state.amount() == 0,
                "Upgraded DoTs deal four fixed 6-damage ticks each, then expire");
            p.powers.clear(); m.powers.clear(); m.currentBlock = 0;
            play(new VampiricTouch(), m);
        });
        steps.add(() -> { VampiricTouch touch = new VampiricTouch(); touch.upgrade(); play(touch, m); });
        steps.add(() -> {
            check(dot(ShadowDotPower.TOUCH).damagePerTurn == 6 && dot(ShadowDotPower.TOUCH).amount == 4,
                "Upgraded reapplication replaces base tick damage and duration");
            play(new VampiricTouch(), m);
        });
        steps.add(() -> {
            check(dot(ShadowDotPower.TOUCH).damagePerTurn == 5 && dot(ShadowDotPower.TOUCH).amount == 3,
                "Base reapplication replaces upgraded damage and duration without stacking");
            add(new ApplyPowerAction(m, p, new ArtifactPower(m, 1), 1));
        });
        steps.add(() -> { VampiricTouch touch = new VampiricTouch(); touch.upgrade(); play(touch, m); });
        steps.add(() -> {
            check(dot(ShadowDotPower.TOUCH).damagePerTurn == 5 && dot(ShadowDotPower.TOUCH).amount == 3 && !m.hasPower(ArtifactPower.POWER_ID),
                "Artifact blocks both damage and duration changes on an upgraded refresh");
            ShadowWordPain pain = new ShadowWordPain(); pain.upgrade(); play(pain, m);
        });
        steps.add(() -> { hp = m.currentHealth; tickDots(); });
        steps.add(() -> check(m.currentHealth == hp - 11 && dot(ShadowDotPower.TOUCH).damagePerTurn == 5 && dot(ShadowDotPower.PAIN).damagePerTurn == 6,
            "Mixed base and upgraded DoTs retain independent tick damage"));
    }

    private static ShadowDotPower dot(String id) { return (ShadowDotPower) m.getPower(id); }

    private static void tickDots() {
        for (AbstractPower power : new ArrayList<>(m.powers)) if (power instanceof ShadowDotPower) power.atStartOfTurn();
    }
    private static void play(AbstractCard card, AbstractMonster target) {
        EnergyPanel.totalCount = 3;
        if (!p.hand.contains(card)) p.hand.addToTop(card);
        if (!card.canUse(p, target)) throw new AssertionError("Cannot play " + card.cardID + ": " + card.cantUseMessage);
        p.useCard(card, target, EnergyPanel.totalCount);
    }
    private static EnterVoidform findToken() {
        for (AbstractCard c : p.hand.group) if (c instanceof EnterVoidform && ((EnterVoidform)c).charge == relic.state.charge()) return (EnterVoidform)c;
        return null;
    }
    private static void add(AbstractGameAction a) { AbstractDungeon.actionManager.addToBottom(a); }
    private static void check(boolean condition, String label) {
        if (!condition) throw new AssertionError(label);
        results.add("PASS: " + label); System.out.println("SHADOWTEST PASS: " + label);
    }
    private static void finish(Throwable failure) {
        finished = true;
        if (failure != null) { results.add("FAIL: " + failure); failure.printStackTrace(); }
        results.add(failure == null ? "ALL CHECKS PASSED" : "FAILED");
        try { Files.write(Paths.get("smoke-results.txt"), results, StandardCharsets.UTF_8); } catch (Exception e) { e.printStackTrace(); }
        System.out.println("SHADOWTEST " + results.get(results.size() - 1));
        Gdx.app.exit();
    }
}
