package shadowpriest.powers;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.EndTurnAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.screens.DeathScreen;

/** An inseparable bargain: stats each turn and a visible, unpreventable deadline. */
public final class SurrenderDeadlinePower extends AbstractPower {
    public static final String POWER_ID="shadowpriest:SurrenderDeadline";
    public final int gain;
    public SurrenderDeadlinePower(AbstractCreature owner,int gain) {
        this.owner=owner;this.gain=gain;ID=POWER_ID;name="Surrender to Madness";amount=5;
        type=PowerType.BUFF;isTurnBased=true;
        AbilityPowerArt.apply(this,"surrender_to_madness.jpg");updateDescription();
    }
    @Override public void atStartOfTurn() {
        addToBot(new ApplyPowerAction(owner,owner,new StrengthPower(owner,gain),gain));
        addToBot(new ApplyPowerAction(owner,owner,new MasteryPower(owner,gain),gain));
    }
    @Override public void stackPower(int amount) { /* Duplicate plays never refresh the bargain. */ }
    @Override public void updateDescription() {
        description="At the start of your turn, gain #b"+gain+" Strength and #b"+gain+" Mastery. NL Die after #b"+amount
            +" more turn"+(amount==1?"":"s")+" end"+(amount==1?"s":"")+". This death cannot be prevented or revived. Win combat to escape.";
    }
    public void finishTurn() {
        if(owner.isDead || AbstractDungeon.getMonsters().areMonstersBasicallyDead()
                || AbstractDungeon.getCurrRoom().isBattleOver) return;
        amount--;updateDescription();flash();
        if(amount>0)return;
        // This is death, not damage: Block, prevention, Fairy, Tail, and Guardian Angel cannot intercept it.
        owner.currentHealth=0;owner.isDead=true;owner.healthBarUpdatedEvent();
        AbstractDungeon.player.loseBlock();
        AbstractDungeon.deathScreen=new DeathScreen(AbstractDungeon.getMonsters());
    }
    /** EndTurnAction resolves after friendly end-turn actions, before the enemy takes its turn. */
    @SpirePatch(clz=EndTurnAction.class,method="update")
    public static class Deadline {
        @SpirePrefixPatch public static SpireReturn<Void> before(EndTurnAction __instance) {
            AbstractPower power=AbstractDungeon.player.getPower(POWER_ID);
            if(power instanceof SurrenderDeadlinePower)((SurrenderDeadlinePower)power).finishTurn();
            if(AbstractDungeon.player.isDead){__instance.isDone=true;return SpireReturn.Return();}
            return SpireReturn.Continue();
        }
    }
}
