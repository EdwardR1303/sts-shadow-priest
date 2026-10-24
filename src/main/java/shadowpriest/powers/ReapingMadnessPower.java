package shadowpriest.powers;

import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.relics.VoidformRelic;

public class ReapingMadnessPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:ReapingMadness";
    public ReapingMadnessPower(AbstractCreature owner, int amount) {
        this.owner = owner; this.amount = amount; ID = POWER_ID;
        name = "Reaping Madness"; type = PowerType.BUFF;
        AbilityPowerArt.apply(this, "reaping_madness.png"); updateDescription();
    }
    @Override public void atEndOfTurn(boolean isPlayer) {
        VoidformRelic relic = VoidformRelic.current();
        if (isPlayer && relic != null) { flash(); relic.gainInsanity(amount); }
    }
    @Override public void updateDescription() {
        description = "At the end of your turn, gain #b" + amount + " Insanity. Cannot gain while Voidform is ready, active, or recovering.";
    }
}
