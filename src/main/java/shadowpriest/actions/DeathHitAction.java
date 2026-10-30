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
    private final com.megacrit.cardcrawl.cards.AbstractCard card;
    public DeathHitAction(AbstractPlayer player, AbstractMonster monster, DamageInfo info, com.megacrit.cardcrawl.cards.AbstractCard card) {
        this.player = player; this.monster = monster; this.info = info; this.card=card;
        actionType = ActionType.DAMAGE;
    }
    @Override public void update() {
        if (monster!=null && !monster.isDeadOrEscaped() && !monster.isDying && !monster.halfDead && !player.isDead) {
            int before = monster.currentHealth;
            monster.damage(info);
            if(monster.currentHealth<=0 || monster.isDying || monster.halfDead){
                if(!card.purgeOnUse)card.returnToHand=true;
            }else if(monster.currentHealth<before)PriestHpLossAction.damage(player,2);
        }
        isDone = true;
    }
}
