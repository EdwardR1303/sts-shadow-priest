package shadowpriest.powers;

import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class FadePower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:Fade";
    public FadePower(AbstractCreature owner, int amount) {
        this.owner = owner; this.amount = amount; this.ID = POWER_ID;
        name = "Fade"; type = PowerType.BUFF; loadRegion("buffer"); updateDescription();
    }
    @Override public int onLoseHp(int damageAmount) { return Math.max(0, damageAmount - amount); }
    @Override public void atStartOfTurn() { addToBot(new RemoveSpecificPowerAction(owner, owner, ID)); }
    @Override public void updateDescription() {
        description = "Until your next turn, whenever you would lose HP, lose #b" + amount + " less.";
    }
}
