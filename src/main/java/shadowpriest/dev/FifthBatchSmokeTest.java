package shadowpriest.dev;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.CardLibrary;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.monsters.exordium.Cultist;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.random.Random;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;
import static shadowpriest.dev.CardBatchSmokeTest.*;

final class FifthBatchSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m, other, victim;
    private static VoidformRelic relic;
    private static AbstractCard card;
    private static Random savedRng;
    private static int expectedHits;
    static void append(Queue<Runnable> q) {
        q.add(() -> {
            p = AbstractDungeon.player; m = AbstractDungeon.getMonsters().monsters.get(0);
            other = AbstractDungeon.getMonsters().monsters.get(1); relic = VoidformRelic.current(); reset();
            check(AbstractDungeon.commonCardPool.size()==21 && AbstractDungeon.uncommonCardPool.size()==19 && AbstractDungeon.rareCardPool.size()==11,
                "Current roster supplies 21 commons, 19 uncommons, and 11 rares");
            for (String id : new String[]{"Penance","Atonement","PurifyDisease","DispelMagic","BodyAndSoul","InsidiousIre","PetrifyingScream","ShadowCommittal"}) {
                AbstractCard c = CardLibrary.getCard("shadowpriest:"+id).makeCopy(); c.upgrade();
                AbstractCard copy = c.makeStatEquivalentCopy(); Texture art = CustomCard.getPortraitImage((CustomCard)c);
                check(copy.upgraded && copy.cost==c.cost && copy.baseDamage==c.baseDamage && copy.magicNumber==c.magicNumber && copy.selfRetain==c.selfRetain && copy.exhaust==c.exhaust,
                    id+" preserves upgraded values in copies");
                check(c.portrait.getRegionWidth()==250 && art.getWidth()==500 && art.getHeight()==380, id+" loads its final artwork in both sizes");
                if(Boolean.getBoolean("shadowpriest.latestbatchonly")) CardArtReview.card(c,id);
            }
            check(CardLibrary.getCard("shadowpriest:MindSoothe")!=null && CardLibrary.getCard("shadowpriest:MentalFortitude")!=null,
                "Both former drafts now register after approval");
            m.addBlock(4); p.powers.add(new DexterityPower(p, 10)); p.powers.add(new FrailPower(p, 2, false)); play(new Penance(),m,3);
        });
        q.add(() -> {
            check(m.currentHealth==995 && p.currentBlock==0 && EnergyPanel.totalCount==2,"Penance deals three three-damage hits through four Block without generating Block");
            reset(); p.powers.add(new StrengthPower(p,2)); play(upgraded(new Penance()),m,3);
        });
        q.add(() -> {
            check(m.currentHealth==980 && p.currentBlock==0,"Penance+ Strength improves each of four hits");
            reset(); dots(m); p.powers.add(new PsychicLinkPower(p,1)); play(new Penance(),m,3);
        });
        q.add(() -> {
            check(m.currentHealth==986 && p.currentBlock==0,"Penance triggers Psychic Link once after its three hits");
            reset(); m.addBlock(20); play(new Penance(),m,3);
        });
        q.add(() -> {
            check(m.currentHealth==1000 && p.currentBlock==0,"Fully blocked Penance grants no Block");
            reset(); victim=new Cultist(500f,0f); victim.currentHealth=3;
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(victim,false));
        });
        q.add(() -> play(upgraded(new Penance()),victim,3));
        q.add(() -> {
            check(victim.isDeadOrEscaped() && p.currentBlock==0,"Penance stops damaging a killed target and grants no Block");
            AbstractDungeon.getMonsters().monsters.remove(victim); reset(); dots(m); p.powers.add(new DexterityPower(p,2)); play(new Atonement(),m,3);
        });
        q.add(() -> {
            check(m.currentHealth==995 && p.currentBlock==8,"Atonement counts two unique DoTs and adds Dexterity once");
            reset(); allDots(m); p.powers.add(new DexterityPower(p,2)); p.powers.add(new FrailPower(p,2,false)); play(upgraded(new Atonement()),m,3);
        });
        q.add(() -> {
            check(m.currentHealth==993 && p.currentBlock==10,"Atonement+ applies Dexterity and Frail once to twelve base Block");
            reset(); victim=new Cultist(500f,0f); victim.currentHealth=1; allDots(victim);
            AbstractDungeon.actionManager.addToBottom(new SpawnMonsterAction(victim,false));
        });
        q.add(() -> play(new Atonement(),victim,3));
        q.add(() -> {
            check(victim.isDeadOrEscaped() && p.currentBlock==9,"Atonement snapshots DoTs before a killing blow");
            AbstractDungeon.getMonsters().monsters.remove(victim); reset();
            p.powers.add(new WeakPower(p,3,false)); p.powers.add(new FrailPower(p,3,false)); p.powers.add(new VulnerablePower(p,3,false));
            p.powers.add(new SurrenderExposurePower(p)); p.powers.add(new StrengthPower(p,-2));
            card=upgraded(new PurifyDisease()); play(card,null,0);
        });
        q.add(() -> {
            check(!p.hasPower("Weakened") && !p.hasPower("Frail") && !p.hasPower("Vulnerable") && p.hasPower(SurrenderExposurePower.POWER_ID) && stat("Strength")==-2,
                "Purify removes only its three named debuffs");
            check(p.exhaustPile.contains(card) && card.selfRetain && EnergyPanel.totalCount==0,"Purify+ costs zero, retains, and exhausts");
            reset(); m.addBlock(50); m.powers.add(new ArtifactPower(m,3)); m.powers.add(new StrengthPower(m,4)); play(new DispelMagic(),m,0);
        });
        q.add(() -> {
            check(m.currentBlock==0 && m.getPower("Artifact").amount==3 && m.getPower("Strength").amount==4,"Base Dispel removes only Block");
            m.addBlock(50); play(upgraded(new DispelMagic()),m,0);
        });
        q.add(() -> {
            check(m.currentBlock==0 && !m.hasPower("Artifact") && m.getPower("Strength").amount==4,"Dispel+ removes all Artifact and Block, preserving Strength");
            reset(); play(new BodyAndSoul(),null,3);
        });
        q.add(() -> {
            check(stat(BodyAndSoulPower.POWER_ID)==20 && EnergyPanel.totalCount==1,"Body and Soul installs twenty percent dodge for two energy");
            EnergyPanel.totalCount=3; check(!new BodyAndSoul().canUse(p,null),"Equal Body and Soul copies cannot be played");
            play(upgraded(new BodyAndSoul()),null,3);
        });
        q.add(() -> {
            check(stat(BodyAndSoulPower.POWER_ID)==30 && !upgraded(new BodyAndSoul()).canUse(p,null),"Body and Soul+ replaces twenty with thirty, never adds them");
            savedRng=AbstractDungeon.miscRng; long seed=0; while(new Random(seed).random(99)>=30) seed++;
            AbstractDungeon.miscRng=new Random(seed); p.addBlock(7); attack(20);
        });
        q.add(() -> {
            check(p.currentHealth==200 && p.currentBlock==7,"A seeded dodge prevents HP loss and preserves Block");
            long seed=0; while(new Random(seed).random(99)<30) seed++;
            AbstractDungeon.miscRng=new Random(seed); attack(20);
        });
        q.add(() -> {
            check(p.currentHealth==187 && p.currentBlock==0,"A failed dodge consumes Block and deals ordinary damage");
            p.currentHealth=200; AbstractDungeon.miscRng=new Random(123L); Random expected=new Random(123L); expectedHits=0;
            for(int i=0;i<12;i++) { if(expected.random(99)>=30) expectedHits++; attack(2); }
        });
        q.add(() -> {
            check(p.currentHealth==200-2*expectedHits,"Multihit attacks roll independently against seeded dodge RNG");
            p.damage(new DamageInfo(m,4,DamageInfo.DamageType.HP_LOSS)); p.damage(new DamageInfo(m,4,DamageInfo.DamageType.THORNS));
            check(p.currentHealth==192-2*expectedHits,"Dodge does not prevent HP loss or non-Attack damage");
            p.powers.removeIf(pow -> pow.ID.equals(BodyAndSoulPower.POWER_ID)); EnergyPanel.totalCount=3;
            check(!new BodyAndSoul().canUse(p,null),"Removing dodge does not reset its highest-benefit restriction");
            AbstractDungeon.miscRng=savedRng; reset(); EnergyPanel.totalCount=3;
            check(new BodyAndSoul().canUse(p,null),"Combat benefit reset permits Body and Soul again");
            allDots(m); p.powers.add(new MasteryPower(p,2));
            if(Boolean.getBoolean("shadowpriest.latestbatchonly")) CardArtReview.mastery(p.getPower(MasteryPower.POWER_ID));
            p.powers.add(new StrengthPower(p,20)); p.powers.add(new WeakPower(p,2,false));
            m.addBlock(99); dot(ShadowDotPower.TOUCH).bonusTick(); dot(ShadowDotPower.PAIN).bonusTick();
        });
        q.add(() -> {
            check(m.currentHealth==988 && m.currentBlock==99 && MadnessPower.stacks(m)==5,"Positive Mastery modifies both damaging DoTs, ignoring Block and attack modifiers, leaving Madness alone");
            check(dot(ShadowDotPower.TOUCH).amount==3 && dot(ShadowDotPower.PAIN).amount==5,"Bonus Mastery ticks do not spend duration");
            reset(); allDots(m); p.powers.add(new MasteryPower(p,-4)); p.powers.add(new VampiricEmbracePower(p,1));
            m.powers.add(new IntangiblePower(m,1)); dot(ShadowDotPower.TOUCH).bonusTick(); dot(ShadowDotPower.PAIN).bonusTick();
        });
        q.add(() -> {
            check(m.currentHealth==999 && p.currentHealth==201,"Negative Mastery produces one/zero ticks; Intangible never raises zero and only damaging Touch heals");
            reset(); allDots(m); p.powers.add(new MasteryPower(p,-8)); p.powers.add(new VampiricEmbracePower(p,2));
            dot(ShadowDotPower.TOUCH).naturalTick(); dot(ShadowDotPower.PAIN).naturalTick();
        });
        q.add(() -> {
            check(m.currentHealth==1000 && p.currentHealth==200 && dot(ShadowDotPower.TOUCH).amount==2 && dot(ShadowDotPower.PAIN).amount==4,
                "Zero-damage natural ticks still spend duration, do not heal, and preserve DoT identities");
            reset(); p.powers.add(new ArtifactPower(p,1)); add(new ApplyPowerAction(p,p,new MasteryPower(p,-2),-2));
        });
        q.add(() -> {
            check(!p.hasPower(MasteryPower.POWER_ID) && !p.hasPower("Artifact"),"Artifact blocks negative Mastery application");
            add(new ApplyPowerAction(p,p,new MasteryPower(p,2),2));
        });
        q.add(() -> add(new ApplyPowerAction(p,p,new MasteryPower(p,-5),-5)));
        q.add(() -> {
            check(MasteryPower.current()==-3 && p.getPower(MasteryPower.POWER_ID).type==AbstractPower.PowerType.DEBUFF,"Mastery stacks through zero and changes to a negative debuff");
            reset(); play(new PetrifyingScream(),m,3);
        });
        q.add(() -> {
            check(m.hasPower("stslib:Stunned") && m.intent==AbstractMonster.Intent.STUN && EnergyPanel.totalCount==1,"Rare Scream installs Stun and costs two");
            reset(); m.powers.add(new ArtifactPower(m,2)); card=upgraded(new PetrifyingScream()); play(card,m,3);
        });
        q.add(() -> {
            check(!m.hasPower("stslib:Stunned") && m.getPower("Artifact").amount==1 && p.exhaustPile.contains(card) && EnergyPanel.totalCount==2,
                "Scream+ costs one, exhausts, and Artifact prevents Stun");
            reset(); allDots(m); p.powers.add(new MasteryPower(p,2)); p.powers.add(new VampiricEmbracePower(p,1));
            m.setMove((byte)1,AbstractMonster.Intent.ATTACK,6); m.createIntent();
            play(new PetrifyingScream(),m,3);
        });
        q.add(() -> { fillDraw(); endTurn(false); });
        q.add(() -> {
            check(p.currentHealth==201 && m.currentHealth==983 && dot(ShadowDotPower.TOUCH).amount==2 && dot(ShadowDotPower.PAIN).amount==4,
                "An actually stunned enemy skips its Attack but still takes Mastery DoT ticks and heals Embrace");
            play(upgraded(new PetrifyingScream()),m,3);
        });
        q.add(() -> { fillDraw(); endTurn(false); });
        q.add(() -> {
            check(p.currentHealth==202 && m.currentHealth==967,"A second Scream permits consecutive DoT turns without enemy Attack damage (player="+p.currentHealth+", enemy="+m.currentHealth+")");
            reset(); play(new ShadowCommittal(),null,3);
        });
        q.add(() -> play(new MindSpike(),m,3));
        q.add(() -> {
            check(m.currentHealth==994 && stat("Strength")==0 && MasteryPower.current()==0,"Committal grants no stats outside Voidform and does not trigger itself");
            relic.gainInsanity(100); relic.activate(relic.state.charge());
        });
        q.add(() -> { m.currentHealth=1000; play(new MindFlay(),m,0); });
        q.add(() -> {
            check(m.currentHealth==991 && stat("Strength")==1,"Committal grants Strength once after all Attack hits, not before or per hit");
            play(new PowerWordShield(),null,3);
        });
        q.add(() -> {
            check(p.currentBlock==5 && stat("Dexterity")==1,"Committal grants Dexterity after the triggering Skill has blocked");
            play(new PowerWordFortitude(),null,3);
        });
        q.add(() -> {
            check(stat("Dexterity")==3 && MasteryPower.current()==1,"Committal grants Mastery after a Power resolves");
            play(upgraded(new ShadowCommittal()),null,3);
        });
        q.add(() -> {
            check(stat(ShadowCommittalPower.POWER_ID)==2 && MasteryPower.current()==2 && EnergyPanel.totalCount==1,"Second Committal costs two and only the existing copy rewards its play");
            play(new MindSpike(),m,0);
        });
        q.add(() -> {
            check(stat("Strength")==3,"Stacked Committal grants two Strength per Attack card");
            fillDraw(); endTurn(true);
        });
        q.add(() -> {
            check(!VoidformRelic.isActive() && stat("Strength")==3 && stat("Dexterity")==3 && MasteryPower.current()==2,
                "Committal stats survive ordinary Voidform expiry");
            reset(); play(upgraded(new SurrenderToMadness()),null,3);
        });
        q.add(() -> play(upgraded(new ShadowCommittal()),null,3));
        q.add(() -> { fillDraw(); endTurn(true); });
        q.add(() -> play(new MindSpike(),m,0));
        q.add(() -> {
            check(VoidformRelic.isPermanent() && stat("Strength")==1,"Committal continues scaling on later Surrender turns");
            reset(); check(MasteryPower.current()==0 && !p.hasPower(ShadowCommittalPower.POWER_ID) && !p.hasPower(InsidiousIrePower.POWER_ID),"Combat fixture reset clears all new scaling effects");
        });
    }
    private static void add(com.megacrit.cardcrawl.actions.AbstractGameAction a) { AbstractDungeon.actionManager.addToBottom(a); }
    private static void attack(int n) { DamageInfo info=new DamageInfo(m,n,DamageInfo.DamageType.NORMAL); info.applyPowers(m,p); add(new DamageAction(p,info)); }
    private static int stat(String id) { AbstractPower power=p.getPower(id); return power==null?0:power.amount; }
    private static void allDots(AbstractMonster target) { dots(target); target.powers.add(new MadnessPower(target,5)); }
    private static ShadowDotPower dot(String id) { return (ShadowDotPower)m.getPower(id); }
    private static void fillDraw() { other.setMove((byte)0, AbstractMonster.Intent.NONE); other.createIntent(); for(int i=0;i<15;i++) p.drawPile.addToTop(new PowerWordShield()); }
}
