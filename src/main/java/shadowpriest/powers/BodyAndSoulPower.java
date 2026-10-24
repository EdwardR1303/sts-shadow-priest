package shadowpriest.powers;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class BodyAndSoulPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:BodyAndSoul";
    public BodyAndSoulPower(AbstractCreature owner, int chance) {
        this.owner = owner; amount = chance; ID = POWER_ID; name = "Body and Soul"; type = PowerType.BUFF;
        AbilityPowerArt.apply(this, "body_and_soul.jpg"); updateDescription();
    }
    @Override public void stackPower(int chance) { amount = Math.max(amount, chance); updateDescription(); }
    @Override public void updateDescription() { description = "Each enemy Attack hit has a #b" + amount + "% chance to miss before consuming Block. Copies do not stack."; }
    @SpirePatch(clz=AbstractPlayer.class, method="damage")
    public static class Dodge {
        @SpirePrefixPatch public static SpireReturn<Void> before(AbstractPlayer __instance, DamageInfo info) {
            AbstractPower power = __instance.getPower(POWER_ID);
            if (power != null && info.type == DamageInfo.DamageType.NORMAL && info.owner != null && info.owner != __instance
                && AbstractDungeon.miscRng.random(99) < power.amount) {
                power.flash(); __instance.lastDamageTaken = 0;
                AbstractDungeon.effectList.add(new com.megacrit.cardcrawl.vfx.TextAboveCreatureEffect(__instance.hb.cX, __instance.hb.cY, "Miss", com.badlogic.gdx.graphics.Color.VIOLET));
                return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
}
