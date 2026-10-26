package shadowpriest.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public final class ShadowWeavingPower extends AbstractPower {
    public static final String POWER_ID="shadowpriest:ShadowWeaving";
    public ShadowWeavingPower(AbstractCreature owner,int amount) {
        this.owner=owner;this.amount=amount;ID=POWER_ID;name="Shadow Weaving";type=PowerType.BUFF;
        AbilityPowerArt.apply(this,"shadow_weaving.jpg");updateDescription();
    }
    /** Shared end-turn hook calls this before natural ticks remove their last stacks. */
    public void beforeDots() {
        int count=0;
        for(AbstractMonster m:AbstractDungeon.getMonsters().monsters) if(!m.isDeadOrEscaped()) count+=ShadowDotPower.count(m);
        if(count==0)return;
        AbstractCard calculation=new shadowpriest.cards.PowerWordShield();
        calculation.baseBlock=count*amount;calculation.applyPowers();
        addToBot(new GainBlockAction(owner,owner,calculation.block));
    }
    @Override public void updateDescription() { description="At turn end, gain #b"+amount+" Block per active DoT across ALL enemies, as one combined Block grant."; }
}
