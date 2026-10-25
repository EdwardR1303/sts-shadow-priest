package shadowpriest.powers;

import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

/** Recolor the existing game icon while preserving its silhouette, shading, and alpha. */
final class PowerIconTint {
    static TextureAtlas.AtlasRegion tint(TextureAtlas.AtlasRegion original, float red, float green, float blue) {
        TextureData data = original.getTexture().getTextureData();
        if (!data.isPrepared()) data.prepare();
        Pixmap src = data.consumePixmap();
        Pixmap dst = new Pixmap(original.getRegionWidth(), original.getRegionHeight(), Pixmap.Format.RGBA8888);
        Color c = new Color();
        for (int x = 0; x < dst.getWidth(); x++) for (int y = 0; y < dst.getHeight(); y++) {
            Color.rgba8888ToColor(c, src.getPixel(original.getRegionX()+x, original.getRegionY()+y));
            float v = Math.max(c.r, Math.max(c.g, c.b));
            dst.setColor(v * red, v * green, v * blue, c.a); dst.drawPixel(x, y);
        }
        Texture texture = new Texture(dst); texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureAtlas.AtlasRegion result = new TextureAtlas.AtlasRegion(texture, 0, 0, dst.getWidth(), dst.getHeight());
        dst.dispose(); if (data.disposePixmap()) src.dispose(); return result;
    }
}
