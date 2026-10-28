package shadowpriest.mechanics;

import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.powers.MadnessPower;
import shadowpriest.powers.ShadowDotPower;

/** Read-only forecast of the next natural ticks, in DotTiming's power order. */
public final class DotHealthPreview {
    public static final Color PURPLE = new Color(.65f, .25f, .95f, 1f);

    public static int amount(AbstractPower requested) {
        AbstractCreature owner = requested.owner;
        if (!(owner instanceof AbstractMonster) || owner.isDeadOrEscaped() || owner.currentHealth <= 0) return 0;
        int remainingHP = owner.currentHealth;
        int buffer = owner.hasPower("Buffer") ? Math.max(0, owner.getPower("Buffer").amount) : 0;
        int cap = owner.hasPower("Invincible") ? Math.max(0, owner.getPower("Invincible").amount) : Integer.MAX_VALUE;
        boolean intangible = owner.hasPower("Intangible") || owner.hasPower("IntangiblePlayer");
        for (AbstractPower power : owner.powers) {
            int hit;
            if (power.amount <= 0) continue;
            if (power instanceof ShadowDotPower) hit = ((ShadowDotPower)power).tickDamage();
            else if (power instanceof MadnessPower) hit = ((MadnessPower)power).tickDamage();
            else continue;
            if (intangible) hit = Math.min(1, hit);
            // Never call damage hooks here: Buffer and Invincible mutate their state.
            for (AbstractPower defense : owner.powers) {
                if ("Buffer".equals(defense.ID) && buffer > 0 && hit > 0) { buffer--; hit = 0; }
                else if ("Invincible".equals(defense.ID)) { hit = Math.min(hit, cap); cap -= hit; }
            }
            hit = Math.min(hit, remainingHP);
            if (power == requested) return hit;
            remainingHP -= hit;
        }
        return 0;
    }

    private DotHealthPreview() { }
}
