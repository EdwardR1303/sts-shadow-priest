package shadowpriest.dev;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;
import static shadowpriest.dev.CardBatchSmokeTest.*;

final class ThirdBatchSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m, other, victim;
    private static VoidformRelic relic;
    private static int hp, otherHp, playerHp;
    private static float playerX, mX, otherX;
    private static AbstractCard card;
    static void append(Queue<Runnable> q) {
        q.add(() -> {
            p = AbstractDungeon.player; m = AbstractDungeon.getMonsters().monsters.get(0);
            other = AbstractDungeon.getMonsters().monsters.get(1); relic = VoidformRelic.current(); reset();
            check(AbstractDungeon.rareCardPool.size() == 8 && AbstractDungeon.rareCardPool.group.stream()
                .filter(c -> c.cardID.startsWith("shadowpriest:")).count() == 8,
                "Eight native rares form the entire rare reward pool");
            com.megacrit.cardcrawl.rooms.AbstractRoom room = AbstractDungeon.currMapNode.room;
            com.megacrit.cardcrawl.relics.QuestionCard question = new com.megacrit.cardcrawl.relics.QuestionCard();
            p.relics.add(question);
            try {
                AbstractDungeon.currMapNode.room = new com.megacrit.cardcrawl.rooms.MonsterRoomBoss();
                java.util.ArrayList<AbstractCard> rewards = AbstractDungeon.getRewardCards();
                check(rewards.size() == 4 && rewards.stream().map(c -> c.cardID).distinct().count() == 4
                    && rewards.stream().allMatch(c -> c.rarity == AbstractCard.CardRarity.RARE || c instanceof LegendaryCard)
                    && rewards.stream().filter(c -> c instanceof LegendaryCard).count() <= 1,
                    "Question Card generates four distinct boss choices with at most one Legendary");
            } finally { AbstractDungeon.currMapNode.room = room; p.relics.remove(question); }
            for (String id : new String[]{"TentacleSlam", "VoidBlast", "MassDispel", "VampiricEmbrace"}) {
                AbstractCard c = CardLibrary.getCard("shadowpriest:" + id).makeCopy(); c.upgrade();
                AbstractCard copy = c.makeStatEquivalentCopy(); Texture portrait = CustomCard.getPortraitImage((CustomCard)c);
                check(copy.upgraded && copy.cost == c.cost && copy.baseDamage == c.baseDamage && copy.magicNumber == c.magicNumber,
                    id + " registers and preserves upgraded values in copies");
                check(c.portrait.getRegionWidth() == 250 && portrait.getWidth() == 500 && portrait.getHeight() == 380,
                    id + " loads its WoW artwork at normal and inspection sizes");
            }
            check(new ShadowDotPower(m, ShadowDotPower.TOUCH, 20).amount == 5,
                "Touch constructor enforces five stacks even for an oversized application");
            play(upgraded(new ShadowWordPain()), m, 0);
        });
        q.add(() -> {
            check(dot(m, ShadowDotPower.PAIN).amount == 5 && dot(m, ShadowDotPower.PAIN).damagePerTurn == 3
                && EnergyPanel.totalCount == 0, "Pain+ applies five fixed-damage stacks at zero energy");
            reset(); m.powers.add(new ShadowDotPower(m, ShadowDotPower.TOUCH, 1)); play(new VampiricTouch(), m, 3);
        });
        q.add(() -> {
            check(dot(m, ShadowDotPower.TOUCH).amount == 4, "Touch application adds three to one remaining stack");
            play(upgraded(new VampiricTouch()), m, 3);
        });
        q.add(() -> {
            check(dot(m, ShadowDotPower.TOUCH).amount == 5 && dot(m, ShadowDotPower.TOUCH).damagePerTurn == 5,
                "Upgraded Touch adds four without raising damage or exceeding five");
            play(new MindFlayInsanity(), m, 20);
        });
        q.add(() -> {
            check(dot(m, ShadowDotPower.TOUCH).amount == 5, "Twenty-energy duration extension still caps Touch at five");
            reset(); playerX = p.hb.cX; mX = m.hb.cX; otherX = other.hb.cX;
            p.hb.move(100, p.hb.cY); m.hb.move(400, m.hb.cY); other.hb.move(700, other.hb.cY);
            hp = m.currentHealth; otherHp = other.currentHealth; play(new TentacleSlam(), other, 3);
        });
        q.add(() -> {
            check(m.currentHealth == hp && other.currentHealth == otherHp - 8 && EnergyPanel.totalCount == 1,
                "Tentacle Slam damages only its selected target and costs two");
            check(dot(m, ShadowDotPower.TOUCH).amount == 3 && dot(other, ShadowDotPower.TOUCH).amount == 3
                && relic.state.amount() == 0, "Slam applies Touch to the target and intervening enemy after its hit");
            reset(); play(upgraded(new TentacleSlam()), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == 990 && EnergyPanel.totalCount == 2 && m.hasPower(ShadowDotPower.TOUCH)
                && !other.hasPower(ShadowDotPower.TOUCH), "Slam+ costs one, deals ten, and excludes enemies beyond the target");
            reset(); p.hb.move(800, p.hb.cY); m.hb.move(500, m.hb.cY); other.hb.move(200, other.hb.cY);
            play(new TentacleSlam(), other, 3);
        });
        q.add(() -> {
            check(m.hasPower(ShadowDotPower.TOUCH) && other.hasPower(ShadowDotPower.TOUCH), "Slam also finds intervening enemies to the player's left");
            p.hb.move(playerX, p.hb.cY); m.hb.move(mX, m.hb.cY); other.hb.move(otherX, other.hb.cY);
            reset(); dots(m); m.powers.add(new MadnessPower(m, 10));
            other.powers.add(new ShadowDotPower(other, ShadowDotPower.TOUCH, 4));
            other.powers.add(new ShadowDotPower(other, ShadowDotPower.PAIN, 2));
            other.powers.add(new MadnessPower(other, 7)); hp = m.currentHealth; otherHp = other.currentHealth;
            play(new VoidBlast(), m, 3);
        });
        q.add(() -> {
            check(m.currentHealth == hp - 10 && other.currentHealth == otherHp && EnergyPanel.totalCount == 2,
                "Void Blast deals ten to one target for one energy");
            check(dot(m, ShadowDotPower.TOUCH).amount == 3 && dot(m, ShadowDotPower.PAIN).amount == 5 && MadnessPower.stacks(m) == 10,
                "Void Blast leaves source stacks unchanged");
            check(dot(other, ShadowDotPower.TOUCH).amount == 5 && dot(other, ShadowDotPower.PAIN).amount == 5
                && MadnessPower.stacks(other) == 17, "Void Blast adds all three debuffs, caps duration, and adds Madness rather than replacing it");
            play(upgraded(new VoidBlast()), other, 3);
        });
        q.add(() -> {
            check(other.currentHealth == otherHp - 15 && MadnessPower.stacks(m) == 27 && dot(m, ShadowDotPower.TOUCH).amount == 5,
                "Void Blast+ deals fifteen and alternating spread adds the accumulated Madness");
            reset(); dots(m); m.powers.add(new MadnessPower(m, 10)); other.powers.add(new ArtifactPower(other, 2));
            play(new VoidBlast(), m, 3);
        });
        q.add(() -> {
            check(!other.hasPower(ShadowDotPower.TOUCH) && !other.hasPower(ShadowDotPower.PAIN)
                && MadnessPower.stacks(other) == 10 && !other.hasPower("Artifact"),
                "Spread consumes Artifact once per application in Touch, Pain, Madness order");
            reset(); victim = new Cultist(500f, 0f); victim.currentHealth = 5;
            victim.powers.add(new ShadowDotPower(victim, ShadowDotPower.TOUCH, 2));
            victim.powers.add(new MadnessPower(victim, 11));
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(victim, false));
        });
        q.add(() -> play(new VoidBlast(), victim, 3));
        q.add(() -> {
            check(victim.isDeadOrEscaped() && dot(m, ShadowDotPower.TOUCH).amount == 2 && MadnessPower.stacks(other) == 11,
                "A killing Void Blast still spreads the source's pre-hit stacks");
            AbstractDungeon.getMonsters().monsters.remove(victim); reset();
            m.addBlock(25); other.addBlock(12); m.powers.add(new ArtifactPower(m, 3)); other.powers.add(new ArtifactPower(other, 1));
            m.powers.add(new StrengthPower(m, 9)); other.powers.add(new StrengthPower(other, -3));
            m.powers.add(new RitualPower(m, 2, false)); m.powers.add(new MetallicizePower(m, 4));
            card = new MassDispel(); play(card, null, 3);
        });
        q.add(() -> {
            check(m.currentBlock == 0 && other.currentBlock == 0 && !m.hasPower("Artifact") && !other.hasPower("Artifact"),
                "Mass Dispel removes all enemy Block and Artifact without being blocked by Artifact");
            check(!m.hasPower("Strength") && other.getPower("Strength").amount == -3 && m.hasPower("Ritual") && m.hasPower("Metallicize"),
                "Dispel removes positive Strength but preserves negative Strength and future buff sources");
            check(EnergyPanel.totalCount == 1 && p.discardPile.contains(card) && !card.exhaust, "Mass Dispel costs two and remains reusable");
            play(upgraded(new MassDispel()), null, 1);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 0, "Mass Dispel+ costs one");
            reset(); play(new VampiricEmbrace(), null, 3);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount == 0 && p.getPower(VampiricEmbracePower.POWER_ID).amount == 1, "Vampiric Embrace costs three and grants one healing per Touch event");
            dots(m); playerHp = p.currentHealth; dot(m, ShadowDotPower.TOUCH).atStartOfTurn(); dot(m, ShadowDotPower.PAIN).atStartOfTurn();
        });
        q.add(() -> {
            check(p.currentHealth == playerHp + 1 && m.currentHealth == 992, "Natural Touch heals once; Pain damage does not heal");
            play(upgraded(new VampiricEmbrace()), null, 3);
        });
        q.add(() -> {
            check(p.getPower(VampiricEmbracePower.POWER_ID).amount == 3, "Base and upgraded Embrace stack to three healing per Touch event");
            other.powers.add(new ShadowDotPower(other, ShadowDotPower.TOUCH, 3));
            p.powers.add(new PsychicLinkPower(p, 2)); playerHp = p.currentHealth; hp = m.currentHealth; otherHp = other.currentHealth;
            play(new MindSpike(), m, 3);
        });
        q.add(() -> {
            check(p.currentHealth == playerHp + 12 && m.currentHealth == hp - 16 && other.currentHealth == otherHp - 10,
                "Two Psychic Links across two targets cause four Touch heals with stacked Embrace and no cap");
            check(dot(m, ShadowDotPower.TOUCH).amount == 2 && dot(other, ShadowDotPower.TOUCH).amount == 3,
                "Link-triggered healing consumes no Touch duration");
            m.powers.add(new BufferPower(m, 1)); playerHp = p.currentHealth; hp = m.currentHealth;
            dot(m, ShadowDotPower.TOUCH).bonusTick();
        });
        q.add(() -> {
            check(m.currentHealth == hp && p.currentHealth == playerHp, "A completely prevented Touch tick grants no healing");
            m.powers.add(new IntangiblePower(m, 1)); dot(m, ShadowDotPower.TOUCH).bonusTick();
        });
        q.add(() -> {
            check(m.currentHealth == hp - 1 && p.currentHealth == playerHp + 3, "A Touch tick reduced to one damage still grants its fixed Embrace healing");
            reset(); p.powers.add(new VampiricEmbracePower(p, 2)); m.powers.add(new ShadowDotPower(m, ShadowDotPower.TOUCH, 3));
            m.powers.add(new InvinciblePower(m, 200)); hp = m.currentHealth; playerHp = p.currentHealth;
            AbstractDungeon.actionManager.addToBottom(new DamageAction(m, new DamageInfo(p, 199, DamageInfo.DamageType.NORMAL)));
        });
        q.add(() -> dot(m, ShadowDotPower.TOUCH).bonusTick());
        q.add(() -> {
            check(m.currentHealth == hp - 200 && p.currentHealth == playerHp + 2, "Touch respects the Heart's 200-damage Invincible budget and heals on the one HP actually removed");
            dot(m, ShadowDotPower.TOUCH).bonusTick();
        });
        q.add(() -> {
            check(m.currentHealth == hp - 200 && p.currentHealth == playerHp + 2,
                "Touch cannot bypass an exhausted Invincible budget or heal from zero damage");
            reset(); p.powers.add(new VampiricEmbracePower(p, 2)); victim = new Cultist(500f, 0f); victim.currentHealth = 1;
            victim.powers.add(new ShadowDotPower(victim, ShadowDotPower.TOUCH, 3)); playerHp = p.currentHealth;
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(victim, false));
        });
        q.add(() -> dot(victim, ShadowDotPower.TOUCH).bonusTick());
        q.add(() -> {
            check(victim.isDeadOrEscaped() && p.currentHealth == playerHp + 2, "A killing Touch tick grants one Embrace heal");
            AbstractDungeon.getMonsters().monsters.remove(victim); reset();
        });
    }
    private static ShadowDotPower dot(AbstractMonster target, String id) { return (ShadowDotPower)target.getPower(id); }
}
