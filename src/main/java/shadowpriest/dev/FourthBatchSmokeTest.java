package shadowpriest.dev;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.SpawnMonsterAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.relics.ChemicalX;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;
import static shadowpriest.dev.CardBatchSmokeTest.*;

final class FourthBatchSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m, other, victim;
    private static VoidformRelic relic;
    private static AbstractCard card;
    private static Random savedRng;
    static void append(Queue<Runnable> q) {
        q.add(() -> {
            p = AbstractDungeon.player; m = AbstractDungeon.getMonsters().monsters.get(0);
            other = AbstractDungeon.getMonsters().monsters.get(1); relic = VoidformRelic.current(); clean();
            for (String id : new String[]{"VoidBolt", "VoidVolley", "VoidTorrent"}) {
                AbstractCard c = CardLibrary.getCard("shadowpriest:" + id).makeCopy(); c.upgrade();
                AbstractCard copy = c.makeStatEquivalentCopy(); Texture portrait = CustomCard.getPortraitImage((CustomCard)c);
                check(copy.upgraded && copy.baseDamage == c.baseDamage && copy.magicNumber == c.magicNumber && copy.cost == c.cost,
                    id + " registers and preserves upgraded values in copies");
                check(c.portrait.getRegionWidth() == 250 && portrait.getWidth() == 500 && portrait.getHeight() == 380,
                    id + " loads its WoW artwork at both portrait sizes");
            }
            m.powers.add(new ShadowDotPower(m, ShadowDotPower.TOUCH, 1));
            m.powers.add(new ShadowDotPower(m, ShadowDotPower.PAIN, 4)); m.powers.add(new MadnessPower(m, 5));
            play(new VoidBolt(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 992 && EnergyPanel.totalCount == 2 && relic.state.amount() == 15,
                "Void Bolt deals eight for one energy and generates Insanity from existing DoTs");
            check(stacks(ShadowDotPower.TOUCH) == 2 && stacks(ShadowDotPower.PAIN) == 5 && MadnessPower.stacks(m) == 6,
                "Void Bolt adds one to existing Touch, Pain, and Madness");
            play(upgraded(new VoidBolt()), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 982 && stacks(ShadowDotPower.TOUCH) == 4 && stacks(ShadowDotPower.PAIN) == 5
                && MadnessPower.stacks(m) == 8, "Void Bolt+ deals ten and adds two, preserving duration caps");
            clean(); play(new VoidBolt(), m, 3);
        });
        q.add(() -> {
            check(m.powers.isEmpty() && relic.state.amount() == 0, "Void Bolt never creates a missing DoT");
            clean(); m.powers.add(new ShadowDotPower(m, ShadowDotPower.TOUCH, 1));
            m.powers.add(new MadnessPower(m, 5)); m.powers.add(new ArtifactPower(m, 1)); play(new VoidBolt(), m, 3);
        });
        q.add(() -> {
            check(stacks(ShadowDotPower.TOUCH) == 2 && MadnessPower.stacks(m) == 5 && !m.hasPower("Artifact"),
                "Bolt extends existing duration while Artifact blocks the additional Madness application");
            clean(); relic.gainInsanity(100); relic.activate(relic.state.charge());
        });
        q.add(() -> play(new VoidBolt(), m, 0));
        q.add(() -> {
            check(m.currentHealth == 988 && EnergyPanel.totalCount == 0, "Voidform makes Bolt free and raises its damage to twelve");
            clean(); other.halfDead = true; dots(m); m.powers.add(new MadnessPower(m, 5));
            p.powers.add(new PsychicLinkPower(p, 1)); p.powers.add(new VampiricEmbracePower(p, 2));
            play(new VoidVolley(), null, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 971 && other.currentHealth == 1000 && p.currentHealth == 202,
                "Three Volley bolts deal 24, with just one Link tick and one Embrace heal");
            check(relic.state.amount() == 45 && EnergyPanel.totalCount == 0 && MadnessPower.stacks(m) == 8
                && stacks(ShadowDotPower.TOUCH) == 5 && stacks(ShadowDotPower.PAIN) == 5,
                "Volley generates Insanity per hit and adds stacks per bolt within duration caps");
            clean(); other.halfDead = true; dots(m); m.powers.add(new MadnessPower(m, 5));
            play(upgraded(new VoidVolley()), null, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 970 && MadnessPower.stacks(m) == 11 && stacks(ShadowDotPower.TOUCH) == 5,
                "Volley+ fires three ten-damage bolts, each adding two stacks");
            clean(); play(new VoidVolley(), null, 0);
        });
        q.add(() -> play(upgraded(new VoidVolley()), null, 0));
        q.add(() -> {
            check(m.currentHealth == 1000 && other.currentHealth == 1000 && relic.state.amount() == 0,
                "Neither Volley version fires at zero energy without an external X bonus");
            clean(); other.halfDead = true; p.relics.add(new ChemicalX()); play(new VoidVolley(), null, 0);
        });
        q.add(() -> {
            check(m.currentHealth == 984 && EnergyPanel.totalCount == 0, "Chemical X gives zero-energy Volley two bolts");
            p.relics.removeIf(r -> r instanceof ChemicalX); clean(); other.halfDead = true;
            card = new VoidVolley(); card.freeToPlayOnce = true; play(card, null, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 976 && EnergyPanel.totalCount == 3, "Free Volley uses X without consuming energy");
            clean(); other.halfDead = true; p.powers.add(new StrengthPower(p, 2));
            m.powers.add(new VulnerablePower(m, 2, false)); play(new VoidVolley(), null, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 955, "Volley applies Strength and the selected target's Vulnerable to every bolt");
            clean(); other.halfDead = true; relic.gainInsanity(100); relic.activate(relic.state.charge());
        });
        q.add(() -> play(new VoidVolley(), null, 3));
        q.add(() -> {
            check(m.currentHealth == 964 && EnergyPanel.totalCount == 0,
                "Voidform amplifies every Volley bolt without discounting X");
            clean(); other.halfDead = true; victim = new Cultist(500f, 0f); victim.currentHealth = 1;
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(victim, false));
        });
        q.add(() -> {
            savedRng = AbstractDungeon.cardRandomRng;
            long seed = 0; while (new Random(seed).random(1) != 1) seed++;
            AbstractDungeon.cardRandomRng = new Random(seed); play(new VoidVolley(), null, 3);
        });
        q.add(() -> {
            check(victim.isDeadOrEscaped() && m.currentHealth == 984 && other.currentHealth == 1000,
                "Volley reselects a living target after its first bolt kills, without targeting half-dead enemies");
            AbstractDungeon.cardRandomRng = savedRng; AbstractDungeon.getMonsters().monsters.remove(victim); clean();
            dots(m); p.powers.add(new PsychicLinkPower(p, 1)); play(new VoidTorrent(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 971 && other.currentHealth == 1000 && relic.state.amount() == 30 && EnergyPanel.totalCount == 0,
                "Torrent hits for 4, 8, 12 on one target, generates Insanity per hit, and triggers Link once");
            clean(); card = upgraded(new VoidTorrent()); play(card, m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 970 && card.baseDamage == 5 && card.magicNumber == 5,
                "Torrent+ hits for 5, 10, 15 without mutating its stored starting damage");
            play(card, m, 1);
        });
        q.add(() -> {
            check(m.currentHealth == 965, "Replaying Torrent starts its escalation over");
            clean(); p.powers.add(new StrengthPower(p, 2)); p.powers.add(new WeakPower(p, 2, false));
            m.powers.add(new VulnerablePower(m, 2, false)); play(new VoidTorrent(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 968, "Torrent independently applies Strength, Weak, Vulnerable, and rounding to each base hit");
            clean(); relic.gainInsanity(100); relic.activate(relic.state.charge());
        });
        q.add(() -> play(new VoidTorrent(), m, 3));
        q.add(() -> {
            check(m.currentHealth == 964 && EnergyPanel.totalCount == 0,
                "Voidform Torrent hits for 6, 12, 18 and spends all three energy");
            clean(); relic.gainInsanity(100); relic.activate(relic.state.charge());
        });
        q.add(() -> play(upgraded(new VoidTorrent()), m, 3));
        q.add(() -> {
            check(m.currentHealth == 956, "Voidform Torrent+ rounds each hit separately: 7, 15, 22");
            clean(); play(new VoidTorrent(), m, 0);
        });
        q.add(() -> play(upgraded(new VoidTorrent()), m, 0));
        q.add(() -> {
            check(m.currentHealth == 1000, "Neither Torrent version hits at zero energy without an X bonus");
            clean(); p.relics.add(new ChemicalX()); play(new VoidTorrent(), m, 0);
        });
        q.add(() -> {
            check(m.currentHealth == 988 && EnergyPanel.totalCount == 0, "Chemical X gives zero-energy Torrent its first two escalating hits");
            p.relics.removeIf(r -> r instanceof ChemicalX); clean(); card = new VoidTorrent(); card.freeToPlayOnce = true;
            play(card, m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 976 && EnergyPanel.totalCount == 3, "Free Torrent resolves X escalating hits without spending energy");
            clean(); m.addBlock(999); dots(m); play(new VoidTorrent(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 1000 && m.currentBlock == 975 && relic.state.amount() == 30,
                "Fully blocked Torrent still generates Insanity separately for each direct hit");
            clean(); victim = new Cultist(500f, 0f); victim.currentHealth = 1; dots(victim);
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(victim, false));
        });
        q.add(() -> play(new VoidTorrent(), victim, 3));
        q.add(() -> {
            check(victim.isDeadOrEscaped() && m.currentHealth == 1000 && other.currentHealth == 1000 && relic.state.amount() == 10,
                "Torrent stops damaging after its chosen target dies and never redirects remaining hits");
            AbstractDungeon.getMonsters().monsters.remove(victim); clean();
        });
    }
    private static void clean() { other.halfDead = false; reset(); }
    private static int stacks(String id) { return m.getPower(id).amount; }
}
