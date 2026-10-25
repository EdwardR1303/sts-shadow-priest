package shadowpriest.dev;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;
import static shadowpriest.dev.CardBatchSmokeTest.*;

/** Actual engine checks for nonstacking buffs, recovery, decaying Madness, and independent auras. */
final class SecondBatchSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m, other;
    private static VoidformRelic relic;
    private static int hp, otherHp, charge;
    private static HaloPower first, second;
    static void append(Queue<Runnable> q) {
        q.add(() -> {
            p = AbstractDungeon.player; m = AbstractDungeon.getMonsters().monsters.get(0);
            other = AbstractDungeon.getMonsters().monsters.get(1); relic = VoidformRelic.current(); reset();
            for (String id : new String[]{"PowerWordFortitude", "ReapingMadness", "ShadowWordMadness", "Halo"}) {
                AbstractCard c = CardLibrary.getCard("shadowpriest:" + id).makeCopy(); c.upgrade();
                AbstractCard copy = c.makeStatEquivalentCopy(); Texture portrait = CustomCard.getPortraitImage((CustomCard)c);
                check(copy.upgraded && copy.cost == c.cost && copy.magicNumber == c.magicNumber && copy.type == c.type,
                    id + " registers and preserves its upgrade in copies");
                check(c.portrait.getRegionWidth() == 250 && portrait.getWidth() == 500 && portrait.getHeight() == 380,
                    id + " loads ability artwork at both card sizes");
            }
            p.powers.add(new DexterityPower(p, 5)); play(new PowerWordFortitude(), null, 0);
        });
        q.add(() -> {
            check(p.getPower("Dexterity").amount == 7 && EnergyPanel.totalCount == 0, "Fortitude adds two independently of other Dexterity for zero energy");
            check(!new PowerWordFortitude().canUse(p, null) && upgraded(new PowerWordFortitude()).canUse(p, null),
                "Fortitude blocks equal copies but permits the stronger upgrade");
            play(upgraded(new PowerWordFortitude()), null, 0);
        });
        q.add(() -> {
            check(p.getPower("Dexterity").amount == 8, "Upgraded Fortitude after base grants only one additional Dexterity");
            p.powers.removeIf(power -> power.ID.equals("Dexterity"));
            check(!new PowerWordFortitude().canUse(p, null) && !upgraded(new PowerWordFortitude()).canUse(p, null),
                "Losing Dexterity does not reopen Fortitude, including weaker copies");
            relic.onVictory();
            check(new PowerWordFortitude().canUse(p, null), "Combat reset clears Fortitude's contribution flag");
            reset(); play(upgraded(new PowerWordFortitude()), null, 0);
        });
        q.add(() -> {
            check(p.getPower("Dexterity").amount == 3 && !new PowerWordFortitude().canUse(p, null), "Fortitude+ first grants three and blocks a later base copy");
            reset(); play(new ReapingMadness(), null, 3);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 2 && relic.state.amount() == 0, "Reaping Madness costs one and does not grant Insanity immediately");
            play(upgraded(new ReapingMadness()), null, 3);
        });
        q.add(() -> { check(p.getPower(ReapingMadnessPower.POWER_ID).amount == 30, "Base and upgraded Reaping Madness stack to 30 per turn"); endTurn(true); });
        q.add(() -> {
            check(relic.state.amount() == 30, "Reaping Madness grants Insanity at actual turn end");
            relic.gainInsanity(70); relic.activate(relic.state.charge());
        });
        q.add(() -> endTurn(true));
        q.add(() -> {
            check(!relic.state.active() && relic.state.recovering() && relic.state.amount() == 0,
                "Voidform ends at zero and the next turn enters recovery");
            dots(m); play(new MindFlay(), m, 3);
        });
        q.add(() -> { check(relic.state.amount() == 0, "Recovery blocks Insanity from every hit of an Attack"); endTurn(true); });
        q.add(() -> {
            check(!relic.state.recovering() && relic.state.amount() == 0,
                "Recovery blocks end-of-turn Reaping Madness and clears at the following turn start");
            play(new MindSpike(), m, 3);
        });
        q.add(() -> { check(relic.state.amount() == 20, "Normal attack generation resumes after recovery"); endTurn(true); });
        q.add(() -> {
            check(relic.state.amount() == 50, "Passive generation also resumes after recovery");
            reset(); play(upgraded(new ReapingMadness()), null, 3); relic.gainInsanity(90);
        });
        q.add(() -> endTurn(true));
        q.add(() -> {
            check(relic.state.ready() && p.hand.group.stream().anyMatch(c -> c instanceof EnterVoidform),
                "End-of-turn threshold delivers Enter Voidform on the next player turn");
            charge = relic.state.charge(); endTurn(true);
        });
        q.add(() -> {
            check(relic.state.amount() == 100 && relic.state.charge() == charge, "Reaping cannot overflow or create a second ready charge");
            reset(); p.powers.add(new PsychicLinkPower(p, 1)); dots(m);
            hp = m.currentHealth; play(new Halo(), null, 3);
        });
        q.add(() -> {
            check(m.currentHealth == hp && EnergyPanel.totalCount == 2, "Halo is a one-energy Skill with no immediate damage or Psychic Link trigger");
            reset(); m.powers.add(new MadnessPower(m, 5)); other.powers.add(new MadnessPower(other, 5));
            p.powers.add(new StrengthPower(p, 20)); p.powers.add(new WeakPower(p, 2, false));
            hp = m.currentHealth; otherHp = other.currentHealth; m.addBlock(2); play(new Halo(), null, 3);
        });
        q.add(() -> { first = halo(); endTurn(true); });
        q.add(() -> {
            check(m.currentHealth == hp - 4 && other.currentHealth == otherHp - 6 && first.amount == 2,
                "Halo pulses respect Block and offensive modifiers; Madness ticks independently");
            check(relic.state.amount() == 0 && ShadowDotPower.count(m) == 1, "Halo adds no unique DoT and its pulses generate no Insanity");
            play(upgraded(new Halo()), null, 3);
        });
        q.add(() -> {
            second = (HaloPower)p.powers.stream().filter(power -> power instanceof HaloPower && power != first).findFirst().get();
            check(!first.ID.equals(second.ID) && first.amount == 2 && second.amount == 3 && second.pulseDamage == 5,
                "Each Halo cast has its own icon, damage, and duration without refreshing the older aura");
            hp = m.currentHealth; otherHp = other.currentHealth; endTurn(true);
        });
        q.add(() -> {
            check(m.currentHealth == hp - 10 && other.currentHealth == otherHp - 10 && first.amount == 1 && second.amount == 2,
                "Overlapping Halos deal eight plus two from Madness");
            endTurn(true);
        });
        q.add(() -> {
            check(!p.hasPower(first.ID) && p.hasPower(second.ID) && second.amount == 1, "The older Halo expires after exactly three pulses");
            hp = m.currentHealth; endTurn(true);
        });
        q.add(() -> {
            check(!p.hasPower(second.ID) && m.currentHealth == hp - 6, "The later Halo keeps its own final pulse and then expires");
            reset(); relic.gainInsanity(100); relic.activate(relic.state.charge());
        });
        q.add(() -> {
            check(!new Halo().canUse(p, null) || VoidformRelic.effectiveCost(new Halo(), 1) == 1, "Voidform does not discount Halo");
            hp = m.currentHealth; play(upgraded(new Halo()), null, 1);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 0, "Halo still pays one energy in Voidform");
            // Exercise the pulse while the damage buff is still active, independent of end-turn ordering.
            halo().atEndOfTurn(true);
        });
        q.add(() -> { check(m.currentHealth == hp - 5, "Halo pulses do not inherit the active Voidform damage multiplier"); reset(); });
    }
    private static HaloPower halo() {
        return (HaloPower)p.powers.stream().filter(power -> power instanceof HaloPower).findFirst().get();
    }
}
