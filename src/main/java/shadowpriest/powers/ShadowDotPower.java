package shadowpriest.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

/** amount is remaining turns, never damage stacks. Only these two IDs count for Insanity. */
public class ShadowDotPower extends AbstractPower {
    public static final String TOUCH = "shadowpriest:Touch";
    public static final String PAIN = "shadowpriest:Pain";
    public static final int DAMAGE = 5;
    public ShadowDotPower(AbstractCreature owner, String id, int turns) {
        this.owner = owner; ID = id; amount = turns;
        name = TOUCH.equals(id) ? "Vampiric Touch" : "Shadow Word: Pain";
        type = PowerType.DEBUFF; isTurnBased = true;
        loadRegion(TOUCH.equals(id) ? "poison" : "vulnerable");
        updateDescription();
    }
    @Override public void stackPower(int turns) { amount = turns; updateDescription(); }
    @Override public void updateDescription() {
        description = "Before acting, lose #b5 HP. #b" + amount + " turn(s) remaining. Reapplying refreshes duration. Ignores Block and Strength.";
    }
    @Override public void atStartOfTurn() {
        if (amount <= 0 || owner.isDeadOrEscaped()) return;
        flashWithoutSound();
        // HP_LOSS skips attack calculations, so explicitly honor the monster's Intangible.
        int damage = owner.hasPower("Intangible") || owner.hasPower("IntangiblePlayer") ? 1 : DAMAGE;
        addToBot(new DamageAction(owner, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.POISON));
        addToBot(new ReducePowerAction(owner, AbstractDungeon.player, this, 1));
    }
    public static int count(AbstractCreature target) {
        if (target == null) return 0;
        return (target.hasPower(TOUCH) ? 1 : 0) + (target.hasPower(PAIN) ? 1 : 0);
    }
}
