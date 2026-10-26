package shadowpriest.powers;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public final class AtonementPower extends AbstractPower {
    public static final String POWER_ID="shadowpriest:Atonement";
    public AtonementPower(AbstractCreature owner,int amount) {
        this.owner=owner;this.amount=amount;ID=POWER_ID;name="Atonement";type=PowerType.DEBUFF;
        AbilityPowerArt.apply(this,"atonement.jpg");updateDescription();
    }
    @Override public void stackPower(int value) { amount=Math.max(amount,value);updateDescription(); }
    @Override public int onAttacked(DamageInfo info,int damage) {
        if(info.owner==AbstractDungeon.player && info.type==DamageInfo.DamageType.NORMAL)
            addToTop(new GainBlockAction(AbstractDungeon.player,AbstractDungeon.player,amount));
        return damage;
    }
    @Override public void updateDescription() {
        description="Whenever you directly Attack this enemy, gain #b"+amount+" Block. Does not stack; stronger applications replace weaker ones. Not a DoT.";
    }
}
