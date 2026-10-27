package shadowpriest.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import java.util.HashMap;
import java.util.Map;

/** Framed ability icons, with full-portrait illustrations for Legendary equipment. */
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
            case FlashHeal.ID: icon = "cards/icons/flash_heal.jpg"; break;
            case ShadowMend.ID: icon = "cards/icons/shadow_mend.jpg"; break;
            case PowerWordBarrier.ID: icon = "cards/icons/power_word_barrier.jpg"; break;
            case TitheEvasion.ID: icon = "cards/icons/tithe_evasion.jpg"; break;
            case VoidShield.ID: icon = "cards/icons/void_shield.jpg"; break;
            case MindSoothe.ID: icon = "cards/icons/mind_soothe.jpg"; break;
            case MentalFortitude.ID: icon = "cards/icons/mental_fortitude.jpg"; break;
            case Piety.ID: icon = "cards/icons/piety.jpg"; break;
            case Insight.ID: icon = "cards/icons/shadowy_insight.jpg"; break;
            case "shadowpriest:SerendipityAttack":
            case "shadowpriest:SerendipitySkill":
            case Serendipity.ID: icon = "cards/icons/serendipity.jpg"; break;
            case Dragonwrath.ID: icon = "cards/legendary/dragonwrath.png"; break;
            case Xalatath.ID: icon = "cards/legendary/xalatath.png"; break;
            case Valanyr.ID: icon = "cards/legendary/valanyr.png"; break;
            case BloodMagic.ID: icon = "cards/icons/blood_magic.png"; break;
            case MindFlay.ID: icon = "cards/icons/mind_flay.jpg"; break;
            case MindFlayInsanity.ID: icon = "cards/icons/mind_flay_insanity.jpg"; break;
            case MindBlast.ID: icon = "cards/icons/mind_blast.jpg"; break;
            case MindSear.ID: icon = "cards/icons/mind_sear.jpg"; break;
            case ShadowWordDeath.ID: icon = "cards/icons/shadow_word_death.jpg"; break;
            case Bulwark.ID: icon = "cards/icons/bulwark.jpg"; break;
            case Fade.ID: icon = "cards/icons/fade.jpg"; break;
            case Dispersion.ID: icon = "cards/icons/dispersion.jpg"; break;
            case DesperatePrayer.ID: icon = "cards/icons/desperate_prayer.jpg"; break;
            case PsychicLink.ID: icon = "cards/icons/psychic_link.jpg"; break;
            case Foresight.ID: icon = "cards/icons/foresight.png"; break;
            case EnterVoidform.ID: icon = "relics/voidform_source.jpg"; break;
            case UnfurlingDarkness.ID: icon = "cards/icons/unfurling_darkness.jpg"; break;
            case DarkVoid.ID: icon = "cards/icons/dark_void.jpg"; break;
            case PowerInfusion.ID: icon = "cards/icons/power_infusion.jpg"; break;
            case CrystallineReflection.ID: icon = "cards/icons/crystalline_reflection.jpg"; break;
            case Catharsis.ID: icon = "cards/icons/catharsis.jpg"; break;
            case DarkEvangelism.ID: icon = "cards/icons/dark_evangelism.jpg"; break;
            case ShadowCrash.ID: icon = "cards/icons/shadow_crash.jpg"; break;
            case SearingNightmare.ID: icon = "cards/icons/searing_nightmare.jpg"; break;
            case ProtectedChannel.ID: icon = "cards/icons/shadow_committal.png"; break;
            case ShadowWordTorment.ID: icon = "cards/icons/shadow_word_torment.png"; break;
            case MindTrauma.ID: icon = "cards/icons/mind_trauma.jpg"; break;
            case VoidTendrils.ID: icon = "cards/icons/void_tendrils.jpg"; break;
            case EncroachingShadows.ID: icon = "cards/icons/encroaching_shadows.jpg"; break;
            case FocusedWill.ID: icon = "cards/icons/focused_will.jpg"; break;
            case ShadowWeaving.ID: icon = "cards/icons/shadow_weaving.jpg"; break;
            case DarkRevelation.ID: icon = "cards/icons/dark_revelation.jpg"; break;
            case VoidSurge.ID: icon = "cards/icons/void_surge.jpg"; break;
            case MadnessOverwhelming.ID: icon = "cards/icons/madness_overwhelming.png"; break;
            case Singularity.ID: icon = "cards/icons/singularity.jpg"; break;
            case TwinSuns.ID: icon = "cards/icons/twin_suns.png"; break;
            case MaladyOfTheMind.ID: icon = "cards/icons/malady_of_the_mind.jpg"; break;
            case VoidEntropy.ID: icon = "cards/icons/void_entropy.jpg"; break;
            case DarkReinforcement.ID: icon = "cards/icons/dark_reinforcement.png"; break;
            case VoidRay.ID: icon = "cards/icons/void_ray.jpg"; break;
            case MentalDecay.ID: icon = "cards/icons/mental_decay.jpg"; break;
            case MindDecay.ID: icon = "cards/icons/mind_decay.png"; break;
            case EventHorizon.ID: icon = "cards/icons/event_horizon.png"; break;
            default: return null;
        }
        if (CACHE.containsKey(id)) return CACHE.get(id);
        Pixmap frame = new Pixmap(Gdx.files.internal("shadowpriest/images/cards/shadow_frame.png"));
        Pixmap source = new Pixmap(Gdx.files.internal("shadowpriest/images/" + icon));
        Pixmap large = new Pixmap(500, 380, Pixmap.Format.RGBA8888);
        Pixmap small = new Pixmap(250, 190, Pixmap.Format.RGBA8888);
        try {
            Pixmap.setFilter(Pixmap.Filter.BiLinear);
            if (icon.startsWith("cards/legendary/")) {
                // Fill the portrait without stretching the weapon or adding an icon inset.
                int cropWidth = source.getWidth(), cropHeight = source.getHeight();
                if (cropWidth * 380L > cropHeight * 500L) cropWidth = cropHeight * 500 / 380;
                else cropHeight = cropWidth * 380 / 500;
                large.drawPixmap(source, (source.getWidth() - cropWidth) / 2,
                    (source.getHeight() - cropHeight) / 2, cropWidth, cropHeight, 0, 0, 500, 380);
            } else {
            large.drawPixmap(frame, 0, 0, frame.getWidth(), frame.getHeight(), 0, 0, 500, 380);
            int size = 276, x = (500 - size) / 2, y = (380 - size) / 2;
            for (int i = 10; i >= 1; i--) {
                large.setColor(0.52f, 0.22f, 0.85f, 0.12f + (10 - i) * 0.025f);
                large.drawRectangle(x - i, y - i, size + i * 2, size + i * 2);
            }
            large.drawPixmap(source, 0, 0, source.getWidth(), source.getHeight(), x, y, size, size);
            large.setColor(0.64f, 0.48f, 0.73f, 0.9f);
            large.drawRectangle(x - 1, y - 1, size + 2, size + 2);
            }
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
