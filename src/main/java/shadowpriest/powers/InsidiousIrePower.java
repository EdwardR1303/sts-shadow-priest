package shadowpriest.powers;
import com.evacipated.cardcrawl.mod.stslib.damagemods.AbstractDamageModifier;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.DamageModApplyingPower;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.*;
public class InsidiousIrePower extends AbstractPower implements DamageModApplyingPower {
    public static final String POWER_ID = "shadowpriest:InsidiousIre";
    public int copies = 1;
    public InsidiousIrePower(AbstractCreature owner, int bonus) {
        this.owner = owner; amount = bonus; ID = POWER_ID; name = "Insidious Ire"; type = PowerType.BUFF;
        AbilityPowerArt.apply(this, "insidious_ire.jpg"); updateDescription();
    }
    @Override public void stackPower(int bonus) { amount += bonus; copies++; updateDescription(); }
    public int bonus(AbstractCreature target) { return ShadowDotPower.count(target) == 3 ? Math.max(0, amount + copies * MasteryPower.current()) : 0; }
    @Override public boolean shouldPushMods(DamageInfo info, Object instigator, List<AbstractDamageModifier> mods) {
        return instigator instanceof AbstractCard && ((AbstractCard)instigator).type == AbstractCard.CardType.ATTACK
            && (info == null || info.type == DamageInfo.DamageType.NORMAL) && mods.stream().noneMatch(m -> m instanceof IreDamage);
    }
    @Override public List<AbstractDamageModifier> modsToPush(DamageInfo info, Object instigator, List<AbstractDamageModifier> mods) {
        return Collections.singletonList(new IreDamage(this));
    }
    @Override public void updateDescription() { description = "Each Attack hit against an enemy with Touch, Pain, and Madness deals #b" + amount + " plus #b" + copies + " times your Mastery additional damage, with a minimum bonus of zero."; }
    private static class IreDamage extends AbstractDamageModifier {
        private final InsidiousIrePower power;
        IreDamage(InsidiousIrePower power) { this.power = power; }
        @Override public float atDamageGive(float damage, DamageInfo.DamageType type, AbstractCreature target, AbstractCard card) {
            return type == DamageInfo.DamageType.NORMAL ? damage + power.bonus(target) : damage;
        }
        @Override public AbstractDamageModifier makeCopy() { return new IreDamage(power); }
    }
}
