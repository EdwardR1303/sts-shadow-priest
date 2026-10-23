package shadowpriest.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.relics.VoidformRelic;

public class VoidformPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:VoidformActive";
    public VoidformPower(AbstractCreature owner) {
        this.ID = POWER_ID; this.owner = owner; name = "Voidform"; amount = -1;
        type = PowerType.BUFF; isTurnBased = true; loadRegion("doubleDamage"); updateDescription();
    }
    @Override public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return VoidformRelic.isActive() && card.type == AbstractCard.CardType.ATTACK ? damage * 1.5f : damage;
    }
    @Override public void updateDescription() {
        description = "Attacks cost #b1 less and deal #b50% more damage this turn. X costs are unchanged.";
    }
}
