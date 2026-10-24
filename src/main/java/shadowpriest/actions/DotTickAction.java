package shadowpriest.actions;

import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.VampiricEmbracePower;

/** Ordinary engine damage, followed by healing only when a Touch tick actually removes HP. */
public class DotTickAction extends DamageAction {
    private final AbstractCreature victim;
    private final boolean touch;
    private boolean healed;
    public DotTickAction(AbstractCreature target, int damage, boolean touch) {
        super(target, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.HP_LOSS), AttackEffect.POISON);
        victim = target; this.touch = touch;
    }
    @Override public void update() {
        int before = victim.currentHealth;
        AbstractPlayer p = AbstractDungeon.player;
        int healing = touch && p.hasPower(VampiricEmbracePower.POWER_ID)
            ? p.getPower(VampiricEmbracePower.POWER_ID).amount : 0;
        super.update();
        // Resolve synchronously: a final killing blow can clear the queued actions/powers.
        if (!healed && healing > 0 && victim.currentHealth < before && !p.isDead && p.currentHealth > 0) {
            healed = true; p.heal(healing);
        }
    }
}
