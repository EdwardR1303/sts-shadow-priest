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
        if (small == null) { small = purple(region48); large = purple(region128); }
        region48 = small; region128 = large; updateDescription();
    }
    private static TextureAtlas.AtlasRegion purple(TextureAtlas.AtlasRegion original) {
        TextureData data = original.getTexture().getTextureData();
        if (!data.isPrepared()) data.prepare();
        Pixmap src = data.consumePixmap();
        Pixmap dst = new Pixmap(original.getRegionWidth(), original.getRegionHeight(), Pixmap.Format.RGBA8888);
        Color c = new Color();
        for (int x = 0; x < dst.getWidth(); x++) for (int y = 0; y < dst.getHeight(); y++) {
            Color.rgba8888ToColor(c, src.getPixel(original.getRegionX()+x, original.getRegionY()+y));
            float v = Math.max(c.r, Math.max(c.g, c.b));
            dst.setColor(v * 0.72f, v * 0.25f, v, c.a); dst.drawPixel(x, y);
        }
        Texture texture = new Texture(dst); texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureAtlas.AtlasRegion result = new TextureAtlas.AtlasRegion(texture, 0, 0, dst.getWidth(), dst.getHeight());
        dst.dispose(); if (data.disposePixmap()) src.dispose(); return result;
    }
    public static int current() {
        if (AbstractDungeon.player == null) return 0;
        AbstractPower power = AbstractDungeon.player.getPower(POWER_ID); return power == null ? 0 : power.amount;
    }
    @Override public void stackPower(int n) { amount += n; updateDescription(); refreshDots(); }
    @Override public void reducePower(int n) { amount -= n; updateDescription(); refreshDots(); }
    @Override public void updateDescription() {
        type = amount < 0 ? PowerType.DEBUFF : PowerType.BUFF;
        description = "Damaging DoTs deal " + (amount < 0 ? "#r" + (-amount) + " less" : "#b" + amount + " more") + " damage per tick, to a minimum of zero. Does not change Madness or Halo.";
    }
    public static void refreshDots() {
        if (AbstractDungeon.currMapNode != null && AbstractDungeon.getMonsters() != null)
            for (com.megacrit.cardcrawl.monsters.AbstractMonster m : AbstractDungeon.getMonsters().monsters)
                for (AbstractPower p : m.powers) if (p instanceof ShadowDotPower) p.updateDescription();
    }
    @Override public void onInitialApplication() { refreshDots(); }
    @Override public void onRemove() { addToBot(new com.megacrit.cardcrawl.actions.AbstractGameAction() {
        @Override public void update() { refreshDots(); isDone = true; }
    }); }
}
