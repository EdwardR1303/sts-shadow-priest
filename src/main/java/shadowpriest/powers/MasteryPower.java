package shadowpriest.powers;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class MasteryPower extends AbstractPower {
    public static final String POWER_ID = "shadowpriest:Mastery";
    private static TextureAtlas.AtlasRegion small, large;
    public MasteryPower(AbstractCreature owner, int amount) {
        this.owner = owner; this.amount = amount; ID = POWER_ID; name = "Mastery"; canGoNegative = true;
        loadRegion("strength");
        if (small == null) { small = PowerIconTint.tint(region48, 0.72f, 0.25f, 1f); large = PowerIconTint.tint(region128, 0.72f, 0.25f, 1f); }
        region48 = small; region128 = large; updateDescription();
    }
    public static int current() {
        if (AbstractDungeon.player == null) return 0;
        AbstractPower power = AbstractDungeon.player.getPower(POWER_ID); return power == null ? 0 : power.amount;
    }
    @Override public void stackPower(int n) { amount += n; updateDescription(); refreshDots(); }
    @Override public void reducePower(int n) { amount -= n; updateDescription(); refreshDots(); }
    @Override public void updateDescription() {
        type = amount < 0 ? PowerType.DEBUFF : PowerType.BUFF;
        description = "Damaging DoTs deal " + (amount < 0 ? "#r" + (-amount) + " less" : "#b" + amount + " more") + " damage per tick, to a minimum of zero. Does not change stack decay or Halo.";
    }
    public static void refreshDots() {
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getMonsters() != null)
            for (com.megacrit.cardcrawl.monsters.AbstractMonster m : AbstractDungeon.getMonsters().monsters)
                for (AbstractPower p : m.powers) if (p instanceof ShadowDotPower || p instanceof MadnessPower) p.updateDescription();
    }
    @Override public void onInitialApplication() { refreshDots(); }
    @Override public void onRemove() { addToBot(new com.megacrit.cardcrawl.actions.AbstractGameAction() {
        @Override public void update() { refreshDots(); isDone = true; }
    }); }
}
