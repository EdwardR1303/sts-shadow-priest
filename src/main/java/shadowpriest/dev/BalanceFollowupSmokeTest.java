package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.relics.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.relics.*;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Small followup suite; retention cannot pass through a shuffle/redraw. */
public final class BalanceFollowupSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m;
    private static MindDecay curse;
    private static int hp, plays, expected;
    private static Singularity singularity;
    private static VoidTorrent torrent;
    private static int stat(String id) { return p.hasPower(id) ? p.getPower(id).amount : 0; }
    private static void reset() {
        p.powers.clear(); p.loseBlock(); p.currentHealth=p.maxHealth=500;
        p.relics.removeIf(r -> !r.relicId.equals(VoidformRelic.ID)); VoidformRelic.current().reset();
        for (CardGroup g : new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo}) g.clear();
        for (int i=0;i<30;i++) p.drawPile.addToTop(new MindSpike());
        m.powers.clear(); m.loseBlock(); m.currentHealth=m.maxHealth=10000;
        EnergyPanel.totalCount=3;
    }
    private static void play(AbstractCard card, AbstractMonster target, int energy) {
        p.hand.removeCard(card); p.hand.addToTop(card); EnergyPanel.totalCount=energy; card.energyOnUse=energy;
        card.applyPowers(); if(target!=null)card.calculateCardDamage(target);
        check(card.canUse(p,target),"Playable: "+card.name); p.useCard(card,target,energy);
    }
    private static void nextTurn() {
        m.setMove((byte)0,AbstractMonster.Intent.NONE); m.createIntent();
        AbstractDungeon.getCurrRoom().skipMonsterTurn=false;
        AbstractDungeon.overlayMenu.endTurnButton.disable(true); p.endTurnQueued=true;
    }
    private static void dots() {
        m.powers.clear(); m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,5));
        m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,5)); m.powers.add(new MadnessPower(m,20));
    }
    public static void append(Queue<Runnable> q) {
        q.add(() -> {
            p=AbstractDungeon.player; m=AbstractDungeon.getMonsters().monsters.get(0); reset();
            VampiricEmbrace embrace=new VampiricEmbrace(); check(embrace.cost==2,"Embrace costs two before upgrade");
            p.currentHealth=400; play(embrace,null,2);
        });
        q.add(() -> {
            check(EnergyPanel.totalCount==0 && stat(VampiricEmbracePower.POWER_ID)==1,"Two-energy Embrace retains one-heal effect");
            AbstractDungeon.actionManager.addToBottom(new shadowpriest.actions.DotTickAction(m,5,true));
        });
        q.add(() -> {
            check(p.currentHealth==401,"Touch heals one with base Embrace"); reset();
            VampiricEmbrace plus=new VampiricEmbrace(); plus.upgrade();
            check(plus.cost==2 && plus.magicNumber==2,"Embrace upgrade keeps two cost and two healing");
            CardArtReview.card(plus,"followup-embrace-plus");
            VoidEntropy entropy=new VoidEntropy();
            check(!entropy.rawDescription.contains("Recharge"),"Entropy omits redundant Recharge wording");
            CardArtReview.card(entropy,"followup-entropy");
            MentalDecay mental=new MentalDecay();
            check(mental.cardsToPreview instanceof MindDecay,"Mental Decay previews its named Curse");
            CardArtReview.card(mental,"followup-mental-decay"); play(mental,null,1);
        });
        q.add(() -> {
            curse=(MindDecay)p.hand.group.stream().filter(c->c instanceof MindDecay).findFirst().get();
            check(curse.type==AbstractCard.CardType.CURSE && curse.color==AbstractCard.CardColor.CURSE && curse.selfRetain,"Mind Decay is a retained Curse");
            check(curse.makeStatEquivalentCopy() instanceof MindDecay && curse.makeStatEquivalentCopy().selfRetain,"Curse copies preserve identity and Retain");
            check(!curse.canUse(p,null),"Mind Decay is normally unplayable");
            check(p.masterDeck.group.stream().noneMatch(c->c instanceof MindDecay),"Generated Curse never enters permanent deck");
            CardArtReview.card(curse,"followup-mind-decay");
            m.powers.clear(); p.powers.add(new BlurPower(p,1)); p.addBlock(2); hp=p.currentHealth; plays=AbstractDungeon.actionManager.cardsPlayedThisCombat.size(); nextTurn();
        });
        q.add(() -> {
            check(p.hand.contains(curse) && !p.discardPile.contains(curse) && p.drawPile.size()>=20,"Curse remains retained with enough draw cards to prevent reshuffle");
            check(p.currentHealth==hp,"Retained Block absorbs Mind Decay's start-of-turn damage");
            check(AbstractDungeon.actionManager.cardsPlayedThisCombat.size()==plays,"Curse start-of-turn penalty is not a card play");
            check(!curse.dontTriggerOnUseCard,"Retained Curse has no stale autoplay flag");
            p.getRelic(VoidformRelic.ID).atPreBattle(); nextTurn();
        });
        q.add(() -> {
            check(p.hand.contains(curse) && p.currentHealth==hp-2,"Same Curse survives another turn and deals two unblocked damage");
            check(VoidformRelic.current().state.amount()==5,"Unblocked Curse damage generates normal damage-taken Insanity");
            VoidVengeance vengeance=new VoidVengeance(); vengeance.instantObtain(p,p.relics.size(),false); vengeance.atPreBattle();
            play(curse,null,0);
        });
        q.add(() -> {
            check(p.exhaustPile.contains(curse) && stat(HastePower.POWER_ID)==2,"Vengeance plays and exhausts Mind Decay for two Haste");
            check(p.currentHealth==hp-2 && p.getRelic(VoidVengeance.ID).counter==0,"Vengeance spends its allowance without adding Curse penalty");
            reset(); p.relics.add(new BlueCandle()); curse=new MindDecay(); hp=p.currentHealth; play(curse,null,0);
        });
        q.add(() -> {
            check(p.exhaustPile.contains(curse) && p.currentHealth==hp-1,"Blue Candle exhausts Mind Decay with its normal one-HP cost");
            reset(); VoidVengeance r=new VoidVengeance();r.instantObtain(p,p.relics.size(),false);r.atPreBattle();
            curse=new MindDecay();p.hand.addToTop(curse);AbstractDungeon.actionManager.addToBottom(new ExhaustSpecificCardAction(curse,p.hand));
        });
        q.add(() -> {
            check(p.exhaustPile.contains(curse) && stat(HastePower.POWER_ID)==2 && p.getRelic(VoidVengeance.ID).counter==1,"External Curse exhaust grants Haste without spending free play");
            reset(); for(int i=0;i<10;i++)p.hand.addToTop(new MindSpike());
            AbstractDungeon.actionManager.addToBottom(new MakeTempCardInHandAction(new MindDecay(),1,false));
        });
        q.add(() -> {
            curse=(MindDecay)p.discardPile.group.stream().filter(c->c instanceof MindDecay).findFirst().get();
            check(curse.selfRetain,"Full hand sends retained Curse to discard");
            p.discardPile.removeCard(curse);p.drawPile.addToTop(curse);p.hand.clear();
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
        });
        q.add(() -> {
            check(p.hand.contains(curse) && curse.selfRetain,"Mind Decay keeps Retain when redrawn");
            reset();dots();singularity=new Singularity();singularity.applyPowers();
            check(!singularity.rawDescription.contains("bonus damage"),"Singularity has no stale target preview before targeting");
            singularity.calculateCardDamage(m);check(singularity.previewBonus(m)==90 && singularity.rawDescription.contains("(90 bonus damage)"),"Singularity previews thirty-stack bonus separately from initial hit");
            CardArtReview.card(singularity,"followup-singularity-target");
            p.powers.add(new StrengthPower(p,4));p.powers.add(new MasteryPower(p,2));
            p.powers.add(new WeakPower(p,1,false));m.powers.add(new VulnerablePower(m,1,false));
            VoidformRelic.current().enterImmediately();
        });
        q.add(() -> {
            singularity.calculateCardDamage(m);
            check(singularity.previewBonus(m)==253 && singularity.damage==33,"Preview includes Mastery Weak Vulnerable Voidform and ignores Strength on bonus");
            expected=singularity.damage+singularity.previewBonus(m);play(singularity,m,2);
        });
        q.add(() -> {
            check(m.currentHealth==10000-expected,"Singularity actual damage matches two previewed hits");
            reset();dots();p.powers.add(new SearingNightmarePower(p,2));singularity=new Singularity();singularity.calculateCardDamage(m);
            check(singularity.previewBonus(m)==96,"Preview includes Madness applied by first hit");
            expected=singularity.damage+singularity.previewBonus(m);play(singularity,m,2);
        });
        q.add(() -> {
            check(m.currentHealth==10000-expected,"Searing Nightmare preview matches damage after first-hit application");
            reset();singularity=new Singularity();singularity.upgrade();dots();singularity.calculateCardDamage(m);
            check(singularity.previewBonus(m)==150,"Upgraded Singularity uses five damage per stack");
            CardArtReview.card(singularity,"followup-singularity-plus");
            m.powers.clear();singularity.calculateCardDamage(m);check(singularity.rawDescription.contains("(0 bonus damage)"),"Preview updates when target has no DoTs");
            singularity.resetAttributes();check(!singularity.rawDescription.contains("bonus damage"),"Reset clears targeted preview");
            dots();p.powers.add(new MasteryPower(p,-9));singularity.calculateCardDamage(m);check(singularity.previewBonus(m)==0,"Negative Mastery preview floors at zero");
            reset();p.powers.add(new StrengthPower(p,2));p.powers.add(new WeakPower(p,1,false));m.powers.add(new VulnerablePower(m,1,false));
            p.powers.add(new HastePower(p,1));p.relics.add(new ChemicalX());torrent=new VoidTorrent();EnergyPanel.totalCount=0;torrent.calculateCardDamage(m);
            check(torrent.previewDamage(m,3)==32 && torrent.rawDescription.contains("(32 total damage / 3 hits)"),"Torrent previews escalating separately rounded hits with zero-energy Haste and Chemical X");
            CardArtReview.card(torrent,"followup-torrent-target");expected=(int)torrent.previewDamage(m,3);play(torrent,m,0);
        });
        q.add(() -> {
            check(m.currentHealth==10000-expected,"Torrent actual damage matches preview without modifying hit bases");
            reset();torrent=new VoidTorrent();EnergyPanel.totalCount=0;torrent.applyPowers();check(torrent.rawDescription.contains("(0 total damage / 0 hits)"),"Zero-X Torrent previews zero");
            torrent.upgrade();EnergyPanel.totalCount=2;torrent.calculateCardDamage(m);check(torrent.previewDamage(m,2)==15,"Upgraded Torrent previews five then ten");
            check(torrent.baseDamage==5 && torrent.damage==5,"Preview does not overwrite Torrent first hit");
            CardArtReview.card(torrent,"followup-torrent-plus");
            VoidformRelic.current().gainInsanity(50);p.powers.add(new DexterityPower(p,3));VoidShield shield=new VoidShield();shield.applyPowers();
            check(shield.totalBlock()==13 && shield.block==8 && shield.rawDescription.contains("(13 total Block)"),"Shield previews formula total while preserving base Block number");
            shield.upgrade();shield.applyPowers();check(shield.totalBlock()==21,"Upgraded Shield previews eight plus Dexterity plus ten Insanity Block");
            CardArtReview.card(shield,"followup-shield-plus");expected=shield.totalBlock();play(shield,null,1);
        });
        q.add(() -> check(p.currentBlock==expected,"Void Shield gain matches parenthetical total"));
    }
}
