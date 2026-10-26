package shadowpriest.powers;

import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public final class SearingNightmarePower extends AbstractPower {
    public static final String POWER_ID="shadowpriest:SearingNightmare";
    public SearingNightmarePower(AbstractCreature owner,int amount) {
        this.owner=owner;this.amount=amount;ID=POWER_ID;name="Searing Nightmare";type=PowerType.BUFF;
        AbilityPowerArt.apply(this,"searing_nightmare.jpg");updateDescription();
    }
    @Override public void onAttack(DamageInfo info,int damage,AbstractCreature target) {
        if(info.owner==owner && info.type==DamageInfo.DamageType.NORMAL && target!=null && !target.isPlayer)
            addToTop(new ApplyPowerAction(target,owner,new MadnessPower(target,amount),amount));
    }
    @Override public void updateDescription() { description="Your direct Attack hits apply #b"+amount+" Madness to the enemy hit."; }
}
