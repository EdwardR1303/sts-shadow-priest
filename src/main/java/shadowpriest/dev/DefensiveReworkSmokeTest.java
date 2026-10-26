package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.legendary.LegendaryEquipment;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Small opt-in suite for finite Block reserves, DoT upkeep, and character appearances. */
public final class DefensiveReworkSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m;
    private static ValanyrPower valanyr;
    private static void reset() {
        p.powers.clear(); p.currentBlock=0; p.currentHealth=p.maxHealth=500;
        p.hand.clear(); p.drawPile.clear(); p.discardPile.clear(); p.exhaustPile.clear(); p.limbo.clear();
        m.powers.clear(); m.currentBlock=0; m.currentHealth=m.maxHealth=1000;
        p.relics.removeIf(r->r.relicId.equals("Chemical X"));
        VoidformRelic.current().reset();
    }
    private static <T extends AbstractCard> T up(T c) { c.upgrade(); return c; }
    private static void play(AbstractCard c, AbstractMonster target, int energy) {
        EnergyPanel.totalCount=energy; c.energyOnUse=energy; p.hand.addToTop(c);
        check(c.canUse(p,target), c.name+" is playable in fixture");
        p.useCard(c,target,energy);
    }
    private static void nextTurn() {
        m.setMove((byte)0,AbstractMonster.Intent.NONE); m.createIntent();
        for(int i=0;i<10;i++) p.drawPile.addToTop(new PowerWordShield());
        AbstractDungeon.getCurrRoom().skipMonsterTurn=false;
        AbstractDungeon.overlayMenu.endTurnButton.disable(true); p.endTurnQueued=true;
    }
    private static void dots() {
        m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,1));
        m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,4));
        m.powers.add(new MadnessPower(m,5));
    }
    private static int stacks(String id) { return m.hasPower(id)?m.getPower(id).amount:0; }
    private static void waitFrame() { AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8f)); }
    public static void append(Queue<Runnable> q) {
        BarrierReserveSmokeTest.append(q);
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);reset();dots();play(new MindFlayInsanity(),m,2);});
        q.add(()->{check(stacks(ShadowDotPower.TOUCH)==3&&stacks(ShadowDotPower.PAIN)==5&&MadnessPower.stacks(m)==7,"Mind Flay Insanity increases all existing DoTs and honors Touch/Pain caps");
            check(m.currentHealth==988&&VoidformRelic.current().state.amount()==30,"Two hits deal six each and count all three DoTs");reset();dots();m.powers.add(new ArtifactPower(m,1));play(new MindFlayInsanity(),m,2);});
        q.add(()->{check(stacks(ShadowDotPower.TOUCH)==3&&stacks(ShadowDotPower.PAIN)==5&&MadnessPower.stacks(m)==5&&!m.hasPower("Artifact"),"Artifact blocks extra Madness but not Touch/Pain upkeep");reset();play(up(new MindFlayInsanity()),m,0);});
        q.add(()->{check(m.currentHealth==994&&ShadowDotPower.count(m)==0,"Upgraded zero-energy Flay hits once without creating missing DoTs");reset();dots();m.powers.add(new ArtifactPower(m,1));play(new MindFlayInsanity(),m,0);});
        q.add(()->{check(m.currentHealth==1000&&MadnessPower.stacks(m)==5&&m.hasPower("Artifact"),"Zero repeats do not damage, add stacks, or consume Artifact");reset();dots();
            p.powers.add(new HastePower(p,1));p.relics.add(new com.megacrit.cardcrawl.relics.ChemicalX());play(up(new MindFlayInsanity()),m,0);});
        q.add(()->{check(m.currentHealth==976&&MadnessPower.stacks(m)==9&&stacks(ShadowDotPower.TOUCH)==5,"Flay upkeep and hits both include Haste, Chemical X, and upgrade");reset();
            CardArtReview.card(new PowerWordBarrier(),"barrier-rework");CardArtReview.card(up(new MindFlayInsanity()),"flay-insanity-rework");
            LegendaryEquipment.State.equipped.set(p,Dragonwrath.ID);
            p.powers.add(new IntangiblePlayerPower(p,2));waitFrame();});
        q.add(()->{CardArtReview.requestCombat("dispersion-mist");waitFrame();});
        q.add(()->{check(CardArtReview.capturedCombat("dispersion-mist"),"Intangible mist rendered in combat");nextTurn();});
        q.add(()->{check(p.getPower(IntangiblePlayerPower.POWER_ID).amount==1,"Mist remains tied to remaining Intangible duration");nextTurn();});
        q.add(()->{check(!p.hasPower(IntangiblePlayerPower.POWER_ID),"Intangible expires naturally");waitFrame();});
        q.add(()->{CardArtReview.requestCombat("dispersion-reformed");waitFrame();});
        q.add(()->{check(CardArtReview.capturedCombat("dispersion-reformed"),"Priest and legendary weapon reform after Intangible");
            p.playDeathAnimation();check(p.img==p.corpseImg&&p.corpseImg.getWidth()==400,"Death switches to fitted fallen-priest sprite");
            CardArtReview.requestCombat("fallen-priest");waitFrame();});
        q.add(()->check(CardArtReview.capturedCombat("fallen-priest"),"Death renderer captured with the new priest corpse"));
    }
}
