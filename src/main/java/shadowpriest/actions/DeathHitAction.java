package shadowpriest.actions;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

/** Resolve each hit and its backlash together, including the final killing blow. */
public class DeathHitAction extends AbstractGameAction {
    private final AbstractPlayer player;
    private final AbstractMonster monster;
    private final DamageInfo info;
    private final int threshold;
    public DeathHitAction(AbstractPlayer player, AbstractMonster monster, DamageInfo info, int threshold) {
        this.player = player; this.monster = monster; this.info = info; this.threshold = threshold;
        actionType = ActionType.DAMAGE;
    }
    @Override public void update() {
        if (monster != null && !monster.isDeadOrEscaped() && !player.isDead) {
            boolean backlash = monster.currentHealth * 100L > monster.maxHealth * (long) threshold;
            int before = monster.currentHealth;
            monster.damage(info);
            if (backlash && monster.currentHealth < before) {
                player.damage(new DamageInfo(player, 3, DamageInfo.DamageType.HP_LOSS));
            }
        }
        isDone = true;
    }
}
