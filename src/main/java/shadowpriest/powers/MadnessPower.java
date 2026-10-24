package shadowpriest.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MadnessPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:Madness";
    public MadnessPower(AbstractCreature owner, int amount) {
        this.owner = owner; this.amount = amount; ID = POWER_ID;
        name = "Madness"; type = PowerType.DEBUFF;
        AbilityPowerArt.apply(this, "shadow_word_madness.jpg"); updateDescription();
    }
    public static int stacks(AbstractCreature target) {
        return target != null && target.hasPower(POWER_ID) ? Math.max(0, target.getPower(POWER_ID).amount) : 0;
    }
    @Override public void updateDescription() {
        description = "#b" + amount + " Madness. Counts as one unique DoT. Does not tick, decay, or expire. Shadow Word: Madness deals Attack damage based on these stacks.";
    }
}
