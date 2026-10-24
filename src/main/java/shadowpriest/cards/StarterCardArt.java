package shadowpriest.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;

/** Keeps the actual ability icons intact, surrounded by the shared painted frame. */
final class StarterCardArt {
    private static final Map<String, Texture[]> CACHE = new HashMap<>();
    static Texture[] get(String id) {
        String icon;
        switch (id) {
            case MindSpike.ID: icon = "cards/icons/mind_spike.jpg"; break;
            case PowerWordShield.ID: icon = "cards/icons/power_word_shield.jpg"; break;
            case VampiricTouch.ID: icon = "powers/vampiric_touch.jpg"; break;
            case ShadowWordPain.ID: icon = "powers/shadow_word_pain.jpg"; break;
            case PowerWordFortitude.ID: icon = "cards/icons/power_word_fortitude.jpg"; break;
            case ReapingMadness.ID: icon = "cards/icons/reaping_madness.png"; break;
            case ShadowWordMadness.ID: icon = "cards/icons/shadow_word_madness.jpg"; break;
            case Halo.ID: icon = "cards/icons/halo.jpg"; break;
            case TentacleSlam.ID: icon = "cards/icons/tentacle_slam.jpg"; break;
            case VoidBlast.ID: icon = "cards/icons/void_blast.jpg"; break;
            case MassDispel.ID: icon = "cards/icons/mass_dispel.jpg"; break;
            case VampiricEmbrace.ID: icon = "cards/icons/vampiric_embrace.jpg"; break;
            case VoidBolt.ID: icon = "cards/icons/void_bolt.jpg"; break;
            case VoidVolley.ID: icon = "cards/icons/void_volley.jpg"; break;
            case VoidTorrent.ID: icon = "cards/icons/void_torrent.jpg"; break;
            case SurrenderToMadness.ID: icon = "cards/icons/surrender_to_madness.jpg"; break;
            case Penance.ID: icon = "cards/icons/penance.jpg"; break;
            case Atonement.ID: icon = "cards/icons/atonement.jpg"; break;
            case PurifyDisease.ID: icon = "cards/icons/purify_disease.jpg"; break;
            case DispelMagic.ID: icon = "cards/icons/dispel_magic.jpg"; break;
            case BodyAndSoul.ID: icon = "cards/icons/body_and_soul.jpg"; break;
            case InsidiousIre.ID: icon = "cards/icons/insidious_ire.jpg"; break;
            case PetrifyingScream.ID: icon = "cards/icons/petrifying_scream.jpg"; break;
            case ShadowCommittal.ID: icon = "cards/icons/shadow_committal.png"; break;
            default: return null;
        }
        if (CACHE.containsKey(id)) return CACHE.get(id);
        Pixmap frame = new Pixmap(Gdx.files.internal("shadowpriest/images/cards/shadow_frame.png"));
        Pixmap source = new Pixmap(Gdx.files.internal("shadowpriest/images/" + icon));
        Pixmap large = new Pixmap(500, 380, Pixmap.Format.RGBA8888);
        Pixmap small = new Pixmap(250, 190, Pixmap.Format.RGBA8888);
        try {
            Pixmap.setFilter(Pixmap.Filter.BiLinear);
            large.drawPixmap(frame, 0, 0, frame.getWidth(), frame.getHeight(), 0, 0, 500, 380);
            int size = 276, x = (500 - size) / 2, y = (380 - size) / 2;
            for (int i = 10; i >= 1; i--) {
                large.setColor(0.52f, 0.22f, 0.85f, 0.12f + (10 - i) * 0.025f);
                large.drawRectangle(x - i, y - i, size + i * 2, size + i * 2);
            }
            large.drawPixmap(source, 0, 0, source.getWidth(), source.getHeight(), x, y, size, size);
            large.setColor(0.64f, 0.48f, 0.73f, 0.9f);
            large.drawRectangle(x - 1, y - 1, size + 2, size + 2);
            small.drawPixmap(large, 0, 0, 500, 380, 0, 0, 250, 190);
            Texture[] textures = {new Texture(small), new Texture(large)};
            for (Texture texture : textures) texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            CACHE.put(id, textures);
            return textures;
        } finally {
            frame.dispose(); source.dispose(); large.dispose(); small.dispose();
        }
    }
}
