package shadowpriest.character;

import basemod.animations.AbstractAnimation;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import shadowpriest.relics.VoidformRelic;

/** A transparent painted sprite with subtle motion and a state-driven Voidform aura. */
public final class ShadowPriestAnimation extends AbstractAnimation {
    private boolean flipX, flipY;
    private float time, form;

    private static final class Assets {
        private static final Texture SPRITE = loadSprite();
        private static final ShaderProgram SHADER = loadShader();
        private static Texture loadSprite() {
            Texture texture = ImageMaster.loadImage("shadowpriest/images/character/shadow_priest.png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return texture;
        }
        private static ShaderProgram loadShader() {
            ShaderProgram shader = new ShaderProgram(
                Gdx.files.internal("shadowpriest/shaders/priest.vert"),
                Gdx.files.internal("shadowpriest/shaders/priest.frag"));
            if (!shader.isCompiled()) throw new IllegalStateException("Shadow Priest shader: " + shader.getLog());
            return shader;
        }
    }

    @Override public Type type() { return Type.SPRITE; }
    @Override public void setFlip(boolean horizontal, boolean vertical) { flipX = horizontal; flipY = vertical; }

    @Override public void renderSprite(SpriteBatch batch, float x, float y) {
        float dt = Math.min(Gdx.graphics.getDeltaTime(), 0.1f);
        time += dt;
        float target = VoidformRelic.isActive() ? 1f : 0f;
        form = MathUtils.clamp(form + Math.signum(target - form) * dt * 4f, 0f, 1f);
        float s = Settings.scale;
        float height = 350f * s;
        float width = height * Assets.SPRITE.getWidth() / Assets.SPRITE.getHeight();
        float left = x - width * 0.5f;
        // The generated image has a small transparent margin beneath the feet.
        float bottom = y - 8f * s + MathUtils.sin(time * 1.7f) * 2f * s;
        float oldColor = batch.getPackedColor();
        int oldSrc = batch.getBlendSrcFunc(), oldDst = batch.getBlendDstFunc();
        ShaderProgram oldShader = batch.getShader();
        ShaderProgram shader = Assets.SHADER;
        try {
            batch.setShader(shader);
            shader.setUniformf("u_form", form);
            if (form > 0f) {
                shader.setUniformf("u_silhouette", 1f);
                batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                float pulse = 0.85f + 0.15f * MathUtils.sin(time * 3.2f);
                // Overlapping translucent silhouettes form a soft outline without a second sprite.
                for (int ring = 3; ring >= 1; ring--) {
                    float radius = ring * 4f * s;
                    batch.setColor(0.64f, 0.15f, 1f, form * pulse * (0.12f / ring));
                    for (int i = 0; i < 12; i++) {
                        float angle = i * MathUtils.PI2 / 12f;
                        draw(batch, left + MathUtils.cos(angle) * radius, bottom + MathUtils.sin(angle) * radius, width, height);
                    }
                }
                batch.flush();
            }
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shader.setUniformf("u_silhouette", 0f);
            batch.setColor(1f, 1f, 1f, 1f);
            draw(batch, left, bottom, width, height);
            batch.flush();
        } finally {
            batch.setShader(oldShader);
            batch.setBlendFunction(oldSrc, oldDst);
            batch.setColor(oldColor);
        }
    }

    private void draw(SpriteBatch batch, float x, float y, float width, float height) {
        batch.draw(Assets.SPRITE, x, y, width, height, 0, 0,
            Assets.SPRITE.getWidth(), Assets.SPRITE.getHeight(), flipX, flipY);
    }
}
