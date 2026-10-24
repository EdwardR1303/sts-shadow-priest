package shadowpriest.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;

/** amount is remaining turns, never damage stacks. Only these two IDs count for Insanity. */
public class ShadowDotPower extends AbstractPower {
    public static final String TOUCH = "shadowpriest:Touch";
    public static final String PAIN = "shadowpriest:Pain";
    public static final int DAMAGE = 5;
    public int damagePerTurn;
    private Integer incomingDamage;
    public ShadowDotPower(AbstractCreature owner, String id, int turns) {
        this(owner, id, turns, DAMAGE);
    }
    public ShadowDotPower(AbstractCreature owner, String id, int turns, int damage) {
        this.owner = owner; ID = id; amount = turns;
        damagePerTurn = damage;
        name = TOUCH.equals(id) ? "Vampiric Touch" : "Shadow Word: Pain";
        type = PowerType.DEBUFF; isTurnBased = true;
        TextureAtlas.AtlasRegion[] icons = TOUCH.equals(id) ? Icons.TOUCH : Icons.PAIN;
        region48 = icons[0];
        region128 = icons[1];
        updateDescription();
    }
    // Load once after the graphics context exists; every application shares these textures.
    private static final class Icons {
        private static final TextureAtlas.AtlasRegion[] TOUCH = load("vampiric_touch.jpg");
        private static final TextureAtlas.AtlasRegion[] PAIN = load("shadow_word_pain.jpg");
        private static TextureAtlas.AtlasRegion[] load(String file) {
            Texture texture = ImageMaster.loadImage("shadowpriest/images/powers/" + file);
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return new TextureAtlas.AtlasRegion[]{region(texture, 48), region(texture, 128)};
        }
        private static TextureAtlas.AtlasRegion region(Texture texture, int size) {
            TextureAtlas.AtlasRegion region = new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
            // Keep the whole original image while drawing at the game's standard icon sizes.
            region.packedWidth = region.originalWidth = size;
            region.packedHeight = region.originalHeight = size;
            return region;
        }
    }
    public static ApplyPowerAction apply(AbstractCreature target, AbstractCreature source, String id, int turns, int damage) {
        ShadowDotPower incoming = new ShadowDotPower(target, id, turns, damage);
        return new ApplyPowerAction(target, source, incoming, turns) {
            @Override public void update() {
                AbstractPower existing = target == null ? null : target.getPower(id);
                ShadowDotPower current = existing instanceof ShadowDotPower ? (ShadowDotPower) existing : null;
                // Transfer tick damage only if the engine actually calls stackPower. Artifact and
                // other application vetoes still run through the normal ApplyPowerAction pipeline.
                if (current != null) current.incomingDamage = incoming.damagePerTurn;
                try { super.update(); }
                finally { if (current != null) current.incomingDamage = null; }
            }
        };
    }
    @Override public void stackPower(int turns) {
        amount = turns;
        if (incomingDamage != null) damagePerTurn = incomingDamage;
        updateDescription();
    }
    @Override public void updateDescription() {
        description = "Before acting, lose #b" + damagePerTurn + " HP. #b" + amount + " turn(s) remaining. Reapplying replaces damage and duration. Ignores Block and Strength.";
    }
    @Override public void atStartOfTurn() {
        if (amount <= 0 || owner.isDeadOrEscaped()) return;
        bonusTick();
        addToBot(new ReducePowerAction(owner, AbstractDungeon.player, this, 1));
    }
    public void bonusTick() {
        if (amount <= 0 || owner.isDeadOrEscaped()) return;
        flashWithoutSound();
        // HP_LOSS skips attack calculations, so explicitly honor the monster's Intangible.
        int damage = owner.hasPower("Intangible") || owner.hasPower("IntangiblePlayer") ? 1 : damagePerTurn;
        addToBot(new DamageAction(owner, new DamageInfo(AbstractDungeon.player, damage, DamageInfo.DamageType.HP_LOSS), AbstractGameAction.AttackEffect.POISON));
    }
    public static int count(AbstractCreature target) {
        if (target == null) return 0;
        return (target.hasPower(TOUCH) ? 1 : 0) + (target.hasPower(PAIN) ? 1 : 0);
    }
}
