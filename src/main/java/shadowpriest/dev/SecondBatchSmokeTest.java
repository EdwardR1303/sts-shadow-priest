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

/** Actual engine checks for nonstacking buffs, recovery, permanent Madness, and independent auras. */
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
        q.add(() -> { check(p.getPower(ReapingMadnessPower.POWER_ID).amount == 3, "Base and upgraded Reaping Madness stack to three per turn"); endTurn(true); });
        q.add(() -> {
            check(relic.state.amount() == 3, "Reaping Madness grants Insanity at actual turn end");
            relic.gainInsanity(7); relic.activate(relic.state.charge());
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
        q.add(() -> { check(relic.state.amount() == 2, "Normal attack generation resumes after recovery"); endTurn(true); });
        q.add(() -> {
            check(relic.state.amount() == 5, "Passive generation also resumes after recovery");
            reset(); play(upgraded(new ReapingMadness()), null, 3); relic.gainInsanity(9);
        });
        q.add(() -> endTurn(true));
        q.add(() -> {
            check(relic.state.ready() && p.hand.group.stream().anyMatch(c -> c instanceof EnterVoidform),
                "End-of-turn threshold delivers Enter Voidform on the next player turn");
            charge = relic.state.charge(); endTurn(true);
        });
        q.add(() -> {
            check(relic.state.amount() == 10 && relic.state.charge() == charge, "Reaping cannot overflow or create a second ready charge");
            reset(); hp = m.currentHealth; play(new ShadowWordMadness(), m, 3);
        });
        q.add(() -> {
            check(MadnessPower.stacks(m) == 5 && m.currentHealth == hp - 5 && relic.state.amount() == 1,
                "Madness applies five before its single hit and immediately counts for Insanity");
            play(new ShadowWordMadness(), m, 3);
        });
        q.add(() -> {
            check(MadnessPower.stacks(m) == 10 && m.currentHealth == hp - 15 && ShadowDotPower.count(m) == 1,
                "Repeated Madness adds stacks and hits for the new total, but counts as one DoT");
            hp = m.currentHealth; endTurn(true);
        });
        q.add(() -> {
            check(m.currentHealth == hp && MadnessPower.stacks(m) == 10, "Madness never ticks, decays, or expires at a turn boundary");
            play(new MindFlayInsanity(), m, 2);
        });
        q.add(() -> {
            check(MadnessPower.stacks(m) == 10 && m.currentHealth == hp - 8, "Duration extension does not change Madness stacks");
            hp = m.currentHealth; otherHp = other.currentHealth; play(upgraded(new ShadowWordMadness()), m, 3);
        });
        q.add(() -> {
            check(MadnessPower.stacks(m) == 15 && MadnessPower.stacks(other) == 5
                && m.currentHealth == hp - 15 && other.currentHealth == otherHp,
                "Madness+ applies five to all enemies and damages only the selected target");
            dots(m); check(ShadowDotPower.count(m) == 3, "Madness, Touch, and Pain supply three distinct DoTs");
            MindBlast blast = new MindBlast(); blast.calculateCardDamage(m);
            check(blast.damage == 14, "Mind Blast includes Madness in its unique-DoT bonus");
            relic.reset(); play(new MindSear(), null, 1);
        });
        q.add(() -> {
            check(relic.state.amount() == 4, "Area attacks count Madness independently on every affected enemy");
            reset(); m.powers.add(new ArtifactPower(m, 1)); hp = m.currentHealth; play(new ShadowWordMadness(), m, 3);
        });
        q.add(() -> {
            check(!m.hasPower(MadnessPower.POWER_ID) && m.currentHealth == hp && relic.state.amount() == 0,
                "Artifact blocks initial Madness and its resulting zero-stack hit");
            m.powers.add(new MadnessPower(m, 10)); m.powers.add(new ArtifactPower(m, 1)); play(new ShadowWordMadness(), m, 3);
        });
        q.add(() -> {
            check(MadnessPower.stacks(m) == 10 && m.currentHealth == hp - 10, "Blocked Madness application still hits for existing stacks");
            reset(); p.powers.add(new StrengthPower(p, 2)); relic.gainInsanity(10); relic.activate(relic.state.charge());
        });
        q.add(() -> { hp = m.currentHealth; play(new ShadowWordMadness(), m, 0); });
        q.add(() -> {
            check(m.currentHealth == hp - 10 && EnergyPanel.totalCount == 0, "Madness receives Strength, Voidform damage, and the Attack discount once");
            reset(); m.powers.add(new MadnessPower(m, 5)); m.addBlock(30); hp = m.currentHealth; play(new ShadowWordMadness(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == hp && relic.state.amount() == 1, "Blocked Madness hit respects Block and still generates one Insanity");
            reset(); p.powers.add(new PsychicLinkPower(p, 1)); m.powers.add(new ShadowDotPower(m, ShadowDotPower.TOUCH, 3, 5));
            hp = m.currentHealth; play(new ShadowWordMadness(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == hp - 10 && relic.state.amount() == 2, "Madness triggers Psychic Link once and counts Touch plus Madness");
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
            check(m.currentHealth == hp - 1 && other.currentHealth == otherHp - 3 && first.amount == 2,
                "Halo pulses at casting-turn end, respects Block, and ignores offensive modifiers");
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
            check(m.currentHealth == hp - 8 && other.currentHealth == otherHp - 8 && first.amount == 1 && second.amount == 2,
                "Overlapping base and upgraded Halos deal eight in separate pulses");
            endTurn(true);
        });
        q.add(() -> {
            check(!p.hasPower(first.ID) && p.hasPower(second.ID) && second.amount == 1, "The older Halo expires after exactly three pulses");
            hp = m.currentHealth; endTurn(true);
        });
        q.add(() -> {
            check(!p.hasPower(second.ID) && m.currentHealth == hp - 5, "The later Halo keeps its own final pulse and then expires");
            reset(); relic.gainInsanity(10); relic.activate(relic.state.charge());
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
