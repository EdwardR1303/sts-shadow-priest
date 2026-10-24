package shadowpriest.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class HaloPower extends AbstractPower {
    private static long serial;
    public final int pulseDamage;
    public HaloPower(AbstractCreature owner, int damage) {
        this.owner = owner; amount = 3; pulseDamage = damage;
        ID = "shadowpriest:Halo:" + serial++;
        name = "Halo (" + damage + ")"; type = PowerType.BUFF; isTurnBased = true;
        AbilityPowerArt.apply(this, "halo.jpg"); updateDescription();
    }
    @Override public void atEndOfTurn(boolean isPlayer) {
        if (!isPlayer || amount <= 0) return;
        flash();
        addToBot(new AbstractGameAction() {
            @Override public void update() {
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters)
                    if (!m.isDeadOrEscaped()) addToBot(new DamageAction(m,
                        new DamageInfo(owner, pulseDamage, DamageInfo.DamageType.THORNS), AttackEffect.FIRE));
                addToBot(new ReducePowerAction(owner, owner, HaloPower.this, 1));
                isDone = true;
            }
        });
    }
    @Override public void updateDescription() {
        description = "At the end of your turn, deal #b" + pulseDamage + " damage to ALL enemies. #b" + amount
            + " pulse(s) remaining. Respects Block. Generates no Insanity. Each cast is a separate aura.";
    }
}
