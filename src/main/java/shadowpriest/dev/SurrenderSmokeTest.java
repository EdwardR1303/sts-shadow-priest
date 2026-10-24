package shadowpriest.dev;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.OrangePellets;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;
import static shadowpriest.dev.CardBatchSmokeTest.*;

final class SurrenderSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m;
    private static VoidformRelic relic;
    private static AbstractCard card;
    private static EnterVoidform oldToken;
    private static VoidformPower originalPower;
    private static int hp;
    static void append(Queue<Runnable> q) {
        q.add(() -> {
            p = AbstractDungeon.player; m = AbstractDungeon.getMonsters().monsters.get(0);
            relic = VoidformRelic.current(); reset();
            SurrenderToMadness c = (SurrenderToMadness)CardLibrary.getCard(SurrenderToMadness.ID).makeCopy(); c.upgrade();
            AbstractCard copy = c.makeStatEquivalentCopy(); Texture art = CustomCard.getPortraitImage(c);
            check(c.cost == 2 && copy.cost == 2 && copy.upgraded && copy.exhaust
                && c.type == AbstractCard.CardType.SKILL && c.rarity == AbstractCard.CardRarity.RARE,
                "Surrender registers as a rare exhaust Skill with a two-cost upgraded copy");
            check(c.portrait.getRegionWidth() == 250 && art.getWidth() == 500 && art.getHeight() == 380,
                "Surrender loads its archived WoW icon at both portrait sizes");
            relic.gainInsanity(20); card = new SurrenderToMadness(); play(card, null, 3);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 0 && p.exhaustPile.contains(card) && relic.state.surrendered()
                && VoidformRelic.isPermanent() && relic.state.amount() == 0,
                "Surrender spends three, exhausts, and enters lasting Voidform from only 20 Insanity");
            check(p.hasPower(SurrenderExposurePower.POWER_ID)
                && p.getPower(SurrenderExposurePower.POWER_ID).type == AbstractPower.PowerType.DEBUFF,
                "Surrender applies its damage exposure as a separate debuff");
            EnergyPanel.totalCount = 3;
            check(!new SurrenderToMadness().canUse(p, null), "Additional Surrender copies cannot be played after surrendering");
            play(new MindSpike(), m, 0);
        });
        q.add(() -> {
            check(m.currentHealth == 991 && EnergyPanel.totalCount == 0, "Lasting Voidform makes Mind Spike free with nine damage");
            hp = p.currentHealth; p.addBlock(7); attack(10);
        });
        q.add(() -> {
            check(p.currentHealth == hp - 13 && p.currentBlock == 0, "Exposure doubles ten damage before subtracting seven Block");
            m.setMove((byte)0, AbstractMonster.Intent.ATTACK, 10); m.createIntent();
            check(m.getIntentDmg() == 20, "Enemy intent preview displays the doubled Attack damage");
            hp = p.currentHealth; attack(3); attack(3);
        });
        q.add(() -> {
            check(p.currentHealth == hp - 12, "Exposure doubles each hit of a multihit enemy Attack");
            hp = p.currentHealth;
            damage(new DamageInfo(p, 4, DamageInfo.DamageType.HP_LOSS));
            damage(new DamageInfo(m, 4, DamageInfo.DamageType.THORNS));
        });
        q.add(() -> {
            check(p.currentHealth == hp - 8, "Exposure leaves self-inflicted HP loss and non-Attack damage unchanged");
            p.powers.add(new IntangiblePlayerPower(p, 1)); hp = p.currentHealth; attack(20);
        });
        q.add(() -> {
            check(p.currentHealth == hp - 1, "Intangible still caps an exposed Attack at one damage");
            p.powers.removeIf(power -> power instanceof IntangiblePlayerPower);
            p.powers.add(new FadePower(p, 1)); hp = p.currentHealth; attack(10);
        });
        q.add(() -> {
            check(p.currentHealth == hp - 19, "Fade reduces HP loss after the Attack damage multiplier");
            p.powers.removeIf(power -> power instanceof FadePower);
            p.powers.add(new VulnerablePower(p, 2, false)); hp = p.currentHealth; attack(10);
        });
        q.add(() -> {
            check(p.currentHealth == hp - 30, "Exposure stacks multiplicatively with ordinary Vulnerable");
            p.powers.removeIf(power -> power instanceof VulnerablePower);
            hp = p.currentHealth; play(new DesperatePrayer(), null, 3);
        });
        q.add(() -> attack(10));
        q.add(() -> {
            check(p.currentHealth == hp && !p.hasPower(DesperatePrayerPower.POWER_ID),
                "Prayer can heal a survived doubled Attack after its damage resolves");
            play(new VoidTorrent(), m, 3);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 0 && m.currentHealth == 955,
                "Lasting Voidform amplifies Torrent but does not discount its X cost");
            for (int i = 0; i < 15; i++) p.drawPile.addToTop(new PowerWordShield());
            endTurn(true);
        });
        q.add(() -> {
            check(VoidformRelic.isActive() && VoidformRelic.isPermanent() && p.hasPower(SurrenderExposurePower.POWER_ID)
                && !relic.state.recovering(), "Surrender's benefit and penalty both survive a real turn boundary without recovery");
            relic.gainInsanity(100);
            check(relic.state.amount() == 0 && p.hand.group.stream().noneMatch(c -> c instanceof EnterVoidform),
                "Lasting Voidform generates no Insanity or new activation charge");
            play(new MindSpike(), m, 0);
        });
        q.add(() -> {
            check(m.currentHealth == 946 && EnergyPanel.totalCount == 0, "Voidform's free amplified Attack still works on the following turn");
            reset(); p.powers.add(new ArtifactPower(p, 1)); card = upgraded(new SurrenderToMadness()); play(card, null, 3);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 1 && p.exhaustPile.contains(card) && VoidformRelic.isPermanent()
                && !p.hasPower(SurrenderExposurePower.POWER_ID) && !p.hasPower("Artifact"),
                "Artifact prevents the exposure while Surrender+ spends two and keeps lasting Voidform");
            hp = p.currentHealth; attack(10);
        });
        q.add(() -> {
            check(p.currentHealth == hp - 10, "Artifact-protected Surrender takes ordinary Attack damage");
            reset(); play(new SurrenderToMadness(), null, 3);
        });
        q.add(() -> {
            p.powers.add(new ArtifactPower(p, 1)); hp = p.currentHealth; attack(10);
        });
        q.add(() -> {
            check(p.currentHealth == hp - 20 && p.hasPower("Artifact") && p.hasPower(SurrenderExposurePower.POWER_ID),
                "Artifact gained afterward does not cleanse existing Surrender exposure");
            reset(); OrangePellets pellets = new OrangePellets(); p.relics.add(pellets); pellets.atTurnStart();
            play(new SurrenderToMadness(), null, 3);
        });
        q.add(() -> play(new PowerWordFortitude(), null, 0));
        q.add(() -> play(new MindSpike(), m, 0));
        q.add(() -> {
            check(!p.hasPower(SurrenderExposurePower.POWER_ID) && VoidformRelic.isPermanent() && relic.state.surrendered(),
                "Actual Orange Pellets removes Surrender exposure after Skill, Power, and Attack plays, preserving Voidform");
            EnergyPanel.totalCount = 3;
            check(!new SurrenderToMadness().canUse(p, null), "Cleansing exposure does not allow another Surrender play");
            hp = p.currentHealth; attack(10);
        });
        q.add(() -> {
            check(p.currentHealth == hp - 10, "Orange Pellets restores ordinary incoming Attack damage");
            p.relics.removeIf(r -> r instanceof OrangePellets); reset();
            relic.gainInsanity(100); oldToken = new EnterVoidform(relic.state.charge());
            play(upgraded(new SurrenderToMadness()), null, 2);
        });
        q.add(() -> {
            check(!oldToken.canUse(p, null) && !relic.state.ready(), "Surrender invalidates a previously earned activation card");
            reset(); relic.gainInsanity(100); relic.activate(relic.state.charge());
        });
        q.add(() -> {
            originalPower = (VoidformPower)p.getPower(VoidformPower.POWER_ID); play(upgraded(new SurrenderToMadness()), null, 3);
        });
        q.add(() -> {
            check(p.getPower(VoidformPower.POWER_ID) == originalPower && originalPower.permanent && EnergyPanel.totalCount == 1,
                "Surrender extends the existing Voidform power instead of adding another; its Skill cost is not discounted");
            play(new MindSpike(), m, 0);
        });
        q.add(() -> {
            check(m.currentHealth == 991, "Extending existing Voidform does not stack its damage multiplier");
            reset(); relic.gainInsanity(100); relic.activate(relic.state.charge());
        });
        q.add(() -> { for (int i = 0; i < 15; i++) p.drawPile.addToTop(new PowerWordShield()); endTurn(true); });
        q.add(() -> {
            check(relic.state.recovering() && !relic.state.active(), "Ordinary Voidform still ends and enters recovery");
            play(new SurrenderToMadness(), null, 3);
        });
        q.add(() -> {
            check(VoidformRelic.isPermanent() && !relic.state.recovering(), "Surrender can override the recovery restriction");
            reset(); p.powers.add(new ArtifactPower(p, 2));
            new SurrenderToMadness().use(p, null); new SurrenderToMadness().use(p, null);
        });
        q.add(() -> {
            check(VoidformRelic.isPermanent() && p.getPower("Artifact").amount == 1,
                "Queued duplicate Surrender effects resolve the bargain once, consuming only one Artifact");
            reset(); p.relics.remove(relic); play(upgraded(new SurrenderToMadness()), null, 2);
        });
        q.add(() -> play(new MindSpike(), m, 0));
        q.add(() -> {
            check(VoidformRelic.isPermanent() && m.currentHealth == 991 && EnergyPanel.totalCount == 0,
                "Surrender provides amplified free Attacks even without the Voidform starter relic");
            p.relics.add(relic); reset();
            EnergyPanel.totalCount = 3;
            check(!VoidformRelic.isActive() && !relic.state.surrendered() && new SurrenderToMadness().canUse(p, null),
                "Combat cleanup removes Surrender state and allows the card in a new combat");
        });
    }
    private static void attack(int base) { damage(new DamageInfo(m, base, DamageInfo.DamageType.NORMAL)); }
    private static void damage(DamageInfo info) {
        info.applyPowers(info.owner, p);
        AbstractDungeon.actionManager.addToBottom(new DamageAction(p, info));
    }
}
