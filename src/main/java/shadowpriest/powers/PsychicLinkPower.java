package shadowpriest.powers;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class PsychicLinkPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:PsychicLink";
    public PsychicLinkPower(AbstractCreature owner, int amount) {
        this.owner = owner; this.amount = amount; this.ID = POWER_ID;
        name = "Psychic Link"; type = PowerType.BUFF; loadRegion("thousandCuts"); updateDescription();
    }
    @Override public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type != AbstractCard.CardType.ATTACK) return;
        final int triggers = amount;
        addToBot(new AbstractGameAction() {
            @Override public void update() {
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) {
                    if (m.isDeadOrEscaped()) continue;
                    AbstractPower power = m.getPower(ShadowDotPower.TOUCH);
                    if (power instanceof ShadowDotPower) {
                        for (int i = 0; i < triggers; i++) ((ShadowDotPower) power).bonusTick();
                    }
                }
                isDone = true;
            }
        });
    }
    @Override public void updateDescription() {
        description = "Whenever you play an Attack, trigger Vampiric Touch damage on ALL afflicted enemies #b"
            + amount + " time(s), without reducing its duration.";
    }
}
