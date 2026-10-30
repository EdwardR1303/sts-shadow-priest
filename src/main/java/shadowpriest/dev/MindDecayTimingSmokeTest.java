package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.BlurPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowpriest.cards.MindDecay;
import shadowpriest.cards.MindSpike;
import shadowpriest.powers.ShadowDotPower;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Narrow timing regression, including a real end-of-turn DoT victory. */
public final class MindDecayTimingSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m;
    private static MindDecay curse, drawn;
    private static int hp, plays;
    private static boolean ending;
    private static long waiting;
    private static void nextTurn() {
        m.setMove((byte)0,AbstractMonster.Intent.NONE);m.createIntent();
        AbstractDungeon.getCurrRoom().skipMonsterTurn=false;
        AbstractDungeon.overlayMenu.endTurnButton.disable(true);p.endTurnQueued=true;
    }
    public static boolean handleCompletion() {
        if(!ending || AbstractDungeon.getCurrRoom()==null)return false;
        if(AbstractDungeon.getCurrRoom().phase==AbstractRoom.RoomPhase.COMBAT) {
            if(System.currentTimeMillis()-waiting>12000)throw new AssertionError("DoT victory did not finish");
            return true;
        }
        check(m.isDeadOrEscaped() || m.isDying,"Final enemy died to the end-of-turn DoT");
        check(p.currentHealth==hp,"Retained Mind Decay does not damage player after final enemy dies");
        CombatSmokeTest.finish(null);return true;
    }
    public static void append(Queue<Runnable> q) {
        q.add(() -> {
            CardArtReview.singularityFx();
            p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);
            p.powers.clear();p.loseBlock();p.currentHealth=p.maxHealth=500;m.powers.clear();
            for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
            for(int i=0;i<30;i++)p.drawPile.addToTop(new MindSpike());
            curse=new MindDecay();drawn=new MindDecay();p.hand.addToTop(curse);p.drawPile.addToBottom(drawn);p.discardPile.addToTop(new MindDecay());
            check(curse.selfRetain && curse.rawDescription.contains("start of your turn"),"Retain and new timing text are present");
            int queued=AbstractDungeon.actionManager.actions.size();curse.triggerOnEndOfTurnForPlayingCard();
            check(AbstractDungeon.actionManager.actions.size()==queued,"No end-of-turn penalty is queued");
            hp=p.currentHealth;plays=AbstractDungeon.actionManager.cardsPlayedThisCombat.size();p.applyStartOfTurnCards();
        });
        q.add(() -> {
            check(p.currentHealth==hp-1,"Only the copy in hand ticks; draw/discard copies do not");
            check(p.hand.contains(curse) && AbstractDungeon.actionManager.cardsPlayedThisCombat.size()==plays,"Start penalty neither plays nor removes the retained Curse");
            p.addBlock(2);p.powers.add(new BlurPower(p,1));hp=p.currentHealth;nextTurn();
        });
        q.add(() -> {
            check(p.currentHealth==hp-1 && p.currentBlock==2,"Retained Block is preserved while Mind Decay removes one HP on an actual next turn");
            check(p.hand.contains(curse) && p.drawPile.size()>20,"Curse is retained without reshuffle or redraw");
            p.hand.addToTop(new MindDecay());hp=p.currentHealth;VoidformRelic.current().reset();nextTurn();
        });
        q.add(() -> {
            check(p.currentHealth==hp-2,"Two retained copies each tick once on the next turn");
            check(VoidformRelic.current().state.amount()==10,"Each unblocked penalty grants normal damage-taken Insanity");
            p.hand.clear();p.drawPile.removeCard(drawn);p.drawPile.addToTop(drawn);hp=p.currentHealth;
            AbstractDungeon.actionManager.addToBottom(new DrawCardAction(p,1));
        });
        q.add(() -> {
            check(p.hand.contains(drawn) && p.currentHealth==hp,"Drawing Mind Decay does not trigger its start-of-turn penalty");
            drawn.atTurnStart();
            AbstractGameAction pending=AbstractDungeon.actionManager.actions.remove(AbstractDungeon.actionManager.actions.size()-1);
            m.isDying=true;
            try { pending.update(); } finally { m.isDying=false; }
            check(pending.isDone && p.currentHealth==hp,"Already-queued penalty is cancelled if the last enemy is dying");
            AbstractRoom.RoomPhase old=AbstractDungeon.getCurrRoom().phase;
            int queued=AbstractDungeon.actionManager.actions.size();AbstractDungeon.getCurrRoom().phase=AbstractRoom.RoomPhase.COMPLETE;
            try { drawn.atTurnStart(); } finally { AbstractDungeon.getCurrRoom().phase=old; }
            check(AbstractDungeon.actionManager.actions.size()==queued,"No new penalty is queued outside combat");
            m.currentHealth=1;m.powers.clear();m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,1));
            hp=p.currentHealth;ending=true;waiting=System.currentTimeMillis();nextTurn();
        });
    }
}
