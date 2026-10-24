package shadowpriest.powers;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.relics.VoidformRelic;
public class ShadowCommittalPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:ShadowCommittal";
    public ShadowCommittalPower(AbstractCreature owner, int amount) {
        this.owner = owner; this.amount = amount; ID = POWER_ID; name = "Shadow Committal"; type = PowerType.BUFF;
        AbilityPowerArt.apply(this, "shadow_committal.png"); updateDescription();
    }
    @Override public void onUseCard(AbstractCard card, UseCardAction action) {
        if (!VoidformRelic.isActive()) return; // Eligibility is sampled when the card is played.
        AbstractPower stat;
        switch (card.type) {
            case ATTACK: stat = new StrengthPower(owner, amount); break;
            case SKILL: stat = new DexterityPower(owner, amount); break;
            case POWER: stat = new MasteryPower(owner, amount); break;
            default: return;
        }
        addToBot(new ApplyPowerAction(owner, owner, stat, amount));
    }
    @Override public void updateDescription() { description = "During Voidform, after playing an Attack gain #b" + amount + " Strength; a Skill, Dexterity; a Power, Mastery. Stats last for combat."; }
}
