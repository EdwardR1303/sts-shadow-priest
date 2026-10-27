package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.cards.status.Dazed;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.*;
import java.util.Collections;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused engine checks for the final three relics, without a run simulation. */
public final class RelicCompletionSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster a, b;
    private static int stacks(AbstractMonster m, String id) { return m.hasPower(id) ? m.getPower(id).amount : 0; }
    private static int mastery() { return p.hasPower(MasteryPower.POWER_ID) ? p.getPower(MasteryPower.POWER_ID).amount : 0; }
    private static void own(AbstractRelic r) { r.instantObtain(p, p.relics.size(), false); }
    private static void clearDots() { a.powers.clear(); b.powers.clear(); VoidformRelic.current().reset(); }
    private static void apply(AbstractMonster m, String id, int n) {
        AbstractDungeon.actionManager.addToBottom(ShadowDotPower.apply(m, p, id, n));
    }
    private static void beginCombat() { p.preBattlePrep(); p.applyStartOfCombatPreDrawLogic(); p.applyStartOfCombatLogic(); }
    public static void append(Queue<Runnable> q) {
        q.add(() -> {
            p = AbstractDungeon.player; a = AbstractDungeon.getMonsters().monsters.get(0);
            RelicSmokeTest.pools();
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Cultist(300f, 0f), false));
        });
        q.add(() -> {
            b = AbstractDungeon.getMonsters().monsters.get(1); b.currentHealth = b.maxHealth = 3000;
            clearDots(); apply(a, ShadowDotPower.TOUCH, 3);
        });
        q.add(() -> {
            check(stacks(a, ShadowDotPower.TOUCH) == 3 && stacks(a, ShadowDotPower.PAIN) == 0, "No Misery effect without relic");
            own(new Misery()); clearDots(); new VampiricTouch().use(p, a);
        });
        q.add(() -> {
            check(stacks(a, ShadowDotPower.TOUCH) == 3 && stacks(a, ShadowDotPower.PAIN) == 3, "Vampiric Touch applies equal Pain with Misery");
            clearDots(); a.powers.add(new ShadowDotPower(a, ShadowDotPower.TOUCH, 4)); apply(a, ShadowDotPower.TOUCH, 3);
        });
        q.add(() -> {
            check(stacks(a, ShadowDotPower.TOUCH) == 5 && stacks(a, ShadowDotPower.PAIN) == 3, "Partial cap uses incoming three, not one gained");
            a.powers.removeIf(x -> x.ID.equals(ShadowDotPower.PAIN)); apply(a, ShadowDotPower.TOUCH, 3);
        });
        q.add(() -> {
            check(stacks(a, ShadowDotPower.TOUCH) == 5 && stacks(a, ShadowDotPower.PAIN) == 3, "Already capped Touch still triggers Misery once");
            apply(a, ShadowDotPower.TOUCH, 3);
        });
        q.add(() -> {
            check(stacks(a, ShadowDotPower.PAIN) == 5, "Misery Pain respects five-stack cap");
            clearDots(); a.powers.add(new ArtifactPower(a, 1)); apply(a, ShadowDotPower.TOUCH, 3);
        });
        q.add(() -> {
            check(!a.hasPower("Artifact") && stacks(a, ShadowDotPower.TOUCH) == 0 && stacks(a, ShadowDotPower.PAIN) == 0,
                    "Artifact prevents initial Touch and Misery");
            a.powers.add(new ShadowDotPower(a, ShadowDotPower.TOUCH, 5)); a.powers.add(new ArtifactPower(a, 1)); apply(a, ShadowDotPower.TOUCH, 3);
        });
        q.add(() -> {
            check(!a.hasPower("Artifact") && stacks(a, ShadowDotPower.TOUCH) == 5 && stacks(a, ShadowDotPower.PAIN) == 0,
                    "Artifact prevents capped reapplication from triggering Misery");
            clearDots(); apply(a, ShadowDotPower.PAIN, 3);
        });
        q.add(() -> check(stacks(a, ShadowDotPower.PAIN) == 3 && stacks(a, ShadowDotPower.TOUCH) == 0, "Pain does not recursively trigger Misery"));
        for (AbstractCard card : new AbstractCard[]{new MindFlay(), new MindFlayInsanity(), new VoidBolt(), new VoidVolley()}) {
            q.add(() -> {
                clearDots(); a.powers.add(new ShadowDotPower(a, ShadowDotPower.TOUCH, 1));
                card.energyOnUse = 1; EnergyPanel.totalCount = 1; card.use(p, a);
            });
            q.add(() -> check(stacks(a, ShadowDotPower.TOUCH) > 1 && stacks(a, ShadowDotPower.PAIN) == 0,
                    card.name + " extends Touch without Misery"));
        }
        for (AbstractCard card : new AbstractCard[]{new UnfurlingDarkness(), new TentacleSlam()}) {
            q.add(() -> { clearDots(); card.use(p, a); });
            q.add(() -> check(stacks(a, ShadowDotPower.TOUCH) > 0 && stacks(a, ShadowDotPower.PAIN) == stacks(a, ShadowDotPower.TOUCH),
                    card.name + " direct application triggers Misery"));
        }
        q.add(() -> { clearDots(); new BloodMagic().use(p, a); });
        q.add(() -> {
            check(stacks(a, ShadowDotPower.TOUCH) == 2 && stacks(a, ShadowDotPower.PAIN) == 4, "Blood Magic adds its own Pain and Misery Pain");
            clearDots(); a.powers.add(new ShadowDotPower(a, ShadowDotPower.TOUCH, 3)); new VoidBlast().use(p, a);
        });
        q.add(() -> {
            check(stacks(b, ShadowDotPower.TOUCH) == 3 && stacks(b, ShadowDotPower.PAIN) == 3, "Void Blast recipient triggers Misery");
            check(stacks(a, ShadowDotPower.PAIN) == 0, "Void Blast does not reapply to original target");
            clearDots(); a.powers.add(new ArtifactPower(a, 1)); new ShadowCrashPower(p, 3).atStartOfTurn();
        });
        q.add(() -> {
            check(stacks(a, ShadowDotPower.TOUCH) == 0 && stacks(a, ShadowDotPower.PAIN) == 0, "Delayed AoE respects blocked recipient");
            check(stacks(b, ShadowDotPower.TOUCH) == 3 && stacks(b, ShadowDotPower.PAIN) == 3, "Delayed AoE triggers Misery on successful recipient");
            clearDots(); own(new SharedSuffering()); beginCombat();
        });
        q.add(() -> {
            check(stacks(a, ShadowDotPower.PAIN) == 5 && stacks(b, ShadowDotPower.PAIN) == 5, "Shared Suffering applies five Pain to both starting enemies");
            check(stacks(a, ShadowDotPower.TOUCH) == 0 && stacks(b, ShadowDotPower.TOUCH) == 0, "Starting Pain does not reverse-trigger Misery");
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(new Cultist(550f, 0f), false));
        });
        q.add(() -> {
            check(stacks(AbstractDungeon.getMonsters().monsters.get(2), ShadowDotPower.PAIN) == 0, "Later summons do not receive Shared Suffering");
            clearDots(); p.preBattlePrep(); a.powers.add(new ArtifactPower(a, 1)); p.applyStartOfCombatLogic();
        });
        q.add(() -> {
            check(stacks(a, ShadowDotPower.PAIN) == 0 && !a.hasPower("Artifact") && stacks(b, ShadowDotPower.PAIN) == 5,
                    "Starting Pain is independently blocked by Artifact");
            own(new InescapableTorment()); p.masterDeck.clear();
            p.masterDeck.addToTop(new Regret()); p.masterDeck.addToTop(new Regret());
            p.masterDeck.addToTop(new AscendersBane()); p.masterDeck.addToTop(new CurseOfTheBell()); p.masterDeck.addToTop(new Necronomicurse());
            p.masterDeck.addToTop(new Dazed()); p.masterDeck.addToTop(new MindSpike());
            clearDots(); beginCombat();
        });
        q.add(() -> {
            check(mastery() == 5, "Torment counts duplicate Curses and all three special Curses, excluding Status and Attack");
            check(((ShadowDotPower)b.getPower(ShadowDotPower.PAIN)).tickDamage() == 8, "Starting Pain benefits from starting Mastery");
            AbstractCard curse = p.drawPile.group.stream().filter(c -> c instanceof AscendersBane).findFirst().get();
            AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(curse, p.drawPile));
        });
        q.add(() -> {
            check(mastery() == 5, "Exhausting a combat Curse does not reduce starting Mastery");
            p.hand.addToTop(new Regret()); p.masterDeck.group.removeIf(c -> c instanceof Regret);
            check(mastery() == 5, "Generated Curse and permanent removal do not change this combat's snapshot");
            Collections.reverse(p.relics); clearDots(); beginCombat();
        });
        q.add(() -> {
            check(mastery() == 3, "Next combat recounts permanent Curses instead of accumulating Mastery");
            check(((ShadowDotPower)b.getPower(ShadowDotPower.PAIN)).tickDamage() == 6, "Reversed relic order preserves Mastery-scaled starting Pain");
            p.masterDeck.group.removeIf(c -> c.type == AbstractCard.CardType.CURSE); clearDots(); beginCombat();
        });
        q.add(() -> {
            check(mastery() == 0 && !p.hasPower(MasteryPower.POWER_ID), "Curse-free deck gets no zero-stack Mastery power");
            own(new IdolOfNzoth()); p.masterDeck.addToTop(new Regret()); p.masterDeck.addToTop(new Valanyr());
            clearDots(); beginCombat();
        });
        q.add(() -> {
            check(mastery() == 2 && p.hasPower(GuardianAngelPower.POWER_ID), "Torment stacks with NZoth Legendary activation after combat reset");
            CardArtReview.requestCombat("relic-completion");
            AbstractDungeon.actionManager.addToBottom(new WaitAction(1f));
        });
        q.add(() -> check(CardArtReview.capturedCombat("relic-completion"), "New relic icons captured in actual combat top bar"));
    }
}
