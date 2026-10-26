package shadowpriest.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

/** Val'anyr's one-use rescue, independent of its breakable Block retention. */
public class GuardianAngelPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:GuardianAngel";
    private boolean spent;
    public GuardianAngelPower(AbstractCreature owner) {
        this.owner = owner; ID = POWER_ID; name = "Guardian Angel";
        amount = -1; priority = 10000; type = PowerType.BUFF;
        AbilityPowerArt.apply(this, "guardian_angel.jpg"); updateDescription();
    }
    public boolean available() { return !spent; }
    @Override public int onLoseHp(int loss) {
        if (!spent && owner.currentHealth > 0 && loss >= owner.currentHealth) {
            spent = true; flash(); shadowpriest.legendary.LegendaryEquipment.pulse = 1f;
            addToTop(new RemoveSpecificPowerAction(owner, owner, this));
            return owner.currentHealth - 1;
        }
        return loss;
    }
    @Override public void updateDescription() {
        description = "The next lethal damage leaves you at #b1 HP. NL Consumed when triggered. Does not protect against subsequent hits.";
    }
}
