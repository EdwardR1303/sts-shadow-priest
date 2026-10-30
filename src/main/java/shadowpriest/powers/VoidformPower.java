package shadowpriest.powers;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.relics.VoidformRelic;
import basemod.interfaces.AlternateCardCostModifier;
import com.badlogic.gdx.graphics.Color;

public class VoidformPower extends AbstractPower implements AlternateCardCostModifier {
    public static final String POWER_ID = "shadowpriest:VoidformActive";
    public boolean permanent;
    public VoidformPower(AbstractCreature owner) { this(owner, false); }
    public VoidformPower(AbstractCreature owner, boolean permanent) {
        this.ID = POWER_ID; this.owner = owner; name = "Voidform"; amount = -1;
        this.permanent = permanent;
        type = PowerType.BUFF; isTurnBased = !permanent; loadRegion("doubleDamage"); updateDescription();
    }
    @Override public void onInitialApplication() {
        shadowpriest.vfx.ShadowFx.show(shadowpriest.vfx.ShadowSpellEffect.Style.ASCEND, owner, owner);
        shadowpriest.cards.VoidVolley.onVoidformEntry();
    }
    public void makePermanent() { permanent = true; isTurnBased = false; updateDescription(); }
    @Override public float atDamageFinalGive(float damage, DamageInfo.DamageType type, AbstractCard card) {
        return VoidformRelic.isActive() && card.type == AbstractCard.CardType.ATTACK ? damage * 1.5f : damage;
    }
    @Override public void updateDescription() {
        description = "Attacks cost #b1 less and deal #b50% more damage "
            + (permanent ? "for the rest of combat." : com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.hasRelic(shadowpriest.relics.ImprovedVoidform.ID) ? "until the end of a turn after at least three Attack cards are played during this activation." : "this turn.") + " X costs are unchanged.";
    }
    // Surrender also works after a starter swap or when acquired by another character.
    // With the starter present, its cost provider remains the sole source of the discount.
    @Override public boolean costEffectActive(AbstractCard card) {
        return permanent && VoidformRelic.current() == null && card.type == AbstractCard.CardType.ATTACK
            && card.cost >= 0 && card.costForTurn >= 0;
    }
    @Override public int getAlternateResource(AbstractCard card) { return costEffectActive(card) ? 1 : 0; }
    @Override public boolean prioritizeAlternateCost(AbstractCard card) { return true; }
    @Override public boolean canSplitCost(AbstractCard card) { return true; }
    @Override public int spendAlternateCost(AbstractCard card, int amount) { return costEffectActive(card) ? Math.max(0,amount-1) : amount; }
    @Override public String replaceCostString(AbstractCard card, String text, Color color) {
        return costEffectActive(card) && !card.freeToPlay() ? Integer.toString(Math.max(0,card.costForTurn-1)) : text;
    }
}
