package shadowpriest.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class VampiricEmbracePower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:VampiricEmbrace";
    public VampiricEmbracePower(AbstractCreature owner, int amount) {
        this.owner = owner; this.amount = amount; ID = POWER_ID;
        name = "Vampiric Embrace"; type = PowerType.BUFF;
        AbilityPowerArt.apply(this, "vampiric_embrace.jpg"); updateDescription();
    }
    @Override public void updateDescription() {
        description = "Whenever Vampiric Touch removes enemy HP, heal #b" + amount
            + " HP. Includes Psychic Link ticks on each enemy. Multiple copies stack. No per-turn cap.";
    }
}
