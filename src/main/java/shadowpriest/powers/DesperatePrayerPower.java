package shadowpriest.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class DesperatePrayerPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:DesperatePrayer";
    private boolean spent;
    public DesperatePrayerPower(AbstractCreature owner) {
        this.owner = owner; ID = POWER_ID; amount = -1;
        name = "Desperate Prayer"; type = PowerType.BUFF; loadRegion("regen"); updateDescription();
    }
    @Override public void stackPower(int ignored) { spent = false; }
    @Override public void wasHPLost(DamageInfo info, int damageAmount) {
        if (spent || damageAmount <= 0 || info.type != DamageInfo.DamageType.NORMAL
            || !(info.owner instanceof AbstractMonster)) return;
        spent = true;
        // This hook precedes HP subtraction. Reject lethal hits before queuing the recovery.
        final boolean survived = damageAmount < owner.currentHealth;
        addToTop(new AbstractGameAction() {
            @Override public void update() {
                if (survived && !owner.isDeadOrEscaped() && owner.currentHealth > 0) owner.heal(damageAmount);
                addToTop(new RemoveSpecificPowerAction(owner, owner, ID));
                isDone = true;
            }
        });
    }
    @Override public void atStartOfTurn() { addToBot(new RemoveSpecificPowerAction(owner, owner, ID)); }
    @Override public void updateDescription() {
        description = "Before your next turn, heal the HP lost to the next enemy Attack after taking damage. Does not prevent lethal damage.";
    }
}
