package shadowpriest.powers;

import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

/** A normal, Artifact-blockable and cleanseable penalty, separate from lasting Voidform. */
public class SurrenderExposurePower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:SurrenderExposure";
    public SurrenderExposurePower(AbstractCreature owner) {
        this.owner = owner; ID = POWER_ID; name = "Surrender to Madness"; amount = -1;
        type = PowerType.DEBUFF; isTurnBased = false;
        AbilityPowerArt.apply(this, "surrender_to_madness.jpg"); updateDescription();
    }
    @Override public float atDamageReceive(float damage, DamageInfo.DamageType type) {
        return type == DamageInfo.DamageType.NORMAL ? damage * 2f : damage;
    }
    @Override public void updateDescription() {
        description = "Take double damage from Attacks before Block. This debuff can be prevented or removed without ending Voidform.";
    }
}
