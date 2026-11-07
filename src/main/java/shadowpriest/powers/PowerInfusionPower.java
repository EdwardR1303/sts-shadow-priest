package shadowpriest.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;

/** Skipped decay boundaries, refreshed without shortening an existing duration. */
public final class PowerInfusionPower extends AbstractPower {
    public static final String POWER_ID="shadowpriest:PowerInfusion";
    public PowerInfusionPower(AbstractCreature p){this(p,2);}
    public PowerInfusionPower(AbstractCreature p,int turns){owner=p;ID=POWER_ID;name="Power Infusion";amount=turns;type=PowerType.BUFF;isTurnBased=true;loadRegion("dexterity");updateDescription();}
    @Override public void stackPower(int value){amount=Math.max(amount,value);updateDescription();}
    @Override public void atEndOfTurn(boolean player){if(player)addToBot(new ReducePowerAction(owner,owner,this,1));}
    @Override public void updateDescription(){description="Haste does not decay for the next #b"+amount+" end-of-turn steps.";}
}
