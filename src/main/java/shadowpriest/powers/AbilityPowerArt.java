package shadowpriest.powers;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import java.util.HashMap;
import java.util.Map;

final class AbilityPowerArt {
    private static final Map<String, Texture> CACHE = new HashMap<>();
    static void apply(AbstractPower power, String file) {
        Texture texture = CACHE.computeIfAbsent(file, f -> {
            Texture t = ImageMaster.loadImage("shadowpriest/images/cards/icons/" + f);
            t.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return t;
        });
        power.region48 = region(texture, 48);
        power.region128 = region(texture, 128);
    }
    private static TextureAtlas.AtlasRegion region(Texture texture, int size) {
        TextureAtlas.AtlasRegion r = new TextureAtlas.AtlasRegion(texture, 0, 0, texture.getWidth(), texture.getHeight());
        r.packedWidth = r.originalWidth = size; r.packedHeight = r.originalHeight = size;
        return r;
    }
}
