package shadowpriest.vfx;

import com.badlogic.gdx.*;
import com.badlogic.gdx.backends.lwjgl.*;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.Matrix4;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import org.lwjgl.opengl.Pbuffer;
import org.lwjgl.opengl.PixelFormat;
import java.lang.reflect.*;

/** Opt-in, offscreen OpenGL review. No game window, saves, audio, or combat simulation. */
public final class FxRenderReview {
    public static void main(String[] args) throws Exception {
        LwjglNativesLoader.load();
        Pbuffer surface = new Pbuffer(960, 540, new PixelFormat(), null);
        surface.makeCurrent();
        Constructor<?> constructor = Class.forName("com.badlogic.gdx.backends.lwjgl.LwjglGL20").getDeclaredConstructor();
        constructor.setAccessible(true);
        Gdx.gl = Gdx.gl20 = (GL20)constructor.newInstance();
        Gdx.files = new LwjglFiles();
        Gdx.graphics = (Graphics)Proxy.newProxyInstance(Graphics.class.getClassLoader(), new Class[]{Graphics.class}, (proxy, method, values) -> {
            switch (method.getName()) {
                case "getWidth": case "getBackBufferWidth": return 960;
                case "getHeight": case "getBackBufferHeight": return 540;
                case "getDeltaTime": case "getRawDeltaTime": return 1 / 60f;
                case "getGL20": return Gdx.gl20;
            }
            return defaultValue(method.getReturnType());
        });
        Gdx.app = (Application)Proxy.newProxyInstance(Application.class.getClassLoader(), new Class[]{Application.class}, (proxy, method, values) -> {
            if (method.getName().equals("hashCode")) return System.identityHashCode(proxy);
            if (method.getName().equals("equals")) return proxy == values[0];
            if (method.getName().equals("getType")) return Application.ApplicationType.Desktop;
            return defaultValue(method.getReturnType());
        });
        Settings.scale = args.length > 0 ? Float.parseFloat(args[0]) : .5f;
        Settings.WIDTH = (int)(1920 * Settings.scale); Settings.HEIGHT = (int)(1080 * Settings.scale);
        ImageMaster.vfxAtlas = new TextureAtlas(Gdx.files.internal("vfx/vfx.atlas"));
        SpriteBatch batch = new SpriteBatch();
        batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0, 0, Settings.WIDTH, Settings.HEIGHT));
        Gdx.gl.glViewport(0, 0, 960, 540);
        for (ShadowSpellEffect.Style style : ShadowSpellEffect.Style.values()) {
            float s = Settings.scale;
            ShadowSpellEffect effect = new ShadowSpellEffect(style, 360 * s, 520 * s,
                    (style == ShadowSpellEffect.Style.ASCEND ? 360 : 1400) * s, 520 * s);
            for (int frame = 0; frame < 3; frame++) {
                effect.duration = effect.startingDuration * (1 - new float[]{.12f, .4f, .75f}[frame]);
                Gdx.gl.glClearColor(.035f, .025f, .055f, 1);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                batch.begin();
                batch.setColor(.3f, .4f, .5f, .6f); Color prior = batch.getColor().cpy();
                int src = batch.getBlendSrcFunc(), dst = batch.getBlendDstFunc();
                effect.render(batch);
                if (!batch.getColor().equals(prior) || batch.getBlendSrcFunc() != src || batch.getBlendDstFunc() != dst)
                    throw new AssertionError("Render state leak: " + style);
                batch.end();
                Pixmap raw = com.badlogic.gdx.utils.ScreenUtils.getFrameBufferPixmap(0, 0, 960, 540);
                Pixmap flipped = new Pixmap(960, 540, Pixmap.Format.RGBA8888);
                for (int y = 0; y < 540; y++) for (int x = 0; x < 960; x++) flipped.drawPixel(x, y, raw.getPixel(x, 539 - y));
                PixmapIO.writePNG(Gdx.files.local(".local/fx-review/" + Settings.WIDTH + "/" + style + "-" + frame + ".png"), flipped);
                raw.dispose(); flipped.dispose();
                if (Gdx.gl.glGetError() != GL20.GL_NO_ERROR) throw new AssertionError("OpenGL error: " + style);
            }
            effect.duration = 0; effect.update();
            if (!effect.isDone) throw new AssertionError("Effect does not expire: " + style);
            System.out.println("PASS " + style + ": three rendered frames, state restored, expires");
        }
        batch.dispose(); ImageMaster.vfxAtlas.dispose(); surface.destroy();
    }
    private static Object defaultValue(Class<?> type) {
        if (type == boolean.class) return false;
        if (type == int.class) return 0;
        if (type == long.class) return 0L;
        if (type == float.class) return 0f;
        return null;
    }
}
