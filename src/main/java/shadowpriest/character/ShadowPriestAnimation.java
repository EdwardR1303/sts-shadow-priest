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
    private float time, form, mist;

    private static final class Assets {
        private static final Texture SPRITE = loadSprite();
        private static final ShaderProgram SHADER = loadShader("priest.frag");
        private static final ShaderProgram MIST = loadShader("mist.frag");
        private static final ShaderProgram ORB = loadShader("void-orb.frag");
        private static Texture loadSprite() {
            Texture texture = ImageMaster.loadImage("shadowpriest/images/character/shadow_priest.png");
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            return texture;
        }
        private static ShaderProgram loadShader(String fragment) {
            ShaderProgram shader = new ShaderProgram(
                Gdx.files.internal("shadowpriest/shaders/priest.vert"),
                Gdx.files.internal("shadowpriest/shaders/" + fragment));
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
        boolean intangible = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player != null
            && com.megacrit.cardcrawl.dungeons.AbstractDungeon.player.hasPower(com.megacrit.cardcrawl.powers.IntangiblePlayerPower.POWER_ID);
        mist = MathUtils.clamp(mist + Math.signum((intangible ? 1f : 0f) - mist) * dt * 2f, 0f, 1f);
        float s = Settings.scale;
        float height = 350f * s;
        float width = height * Assets.SPRITE.getWidth() / Assets.SPRITE.getHeight();
        float left = x - width * 0.5f;
        // Bare feet remain visibly above the combat floor throughout the hover.
        float bottom = y + 18f * s + MathUtils.sin(time * 1.7f) * 6f * s;
        float oldColor = batch.getPackedColor();
        int oldSrc = batch.getBlendSrcFunc(), oldDst = batch.getBlendDstFunc();
        ShaderProgram oldShader = batch.getShader();
        ShaderProgram shader = Assets.SHADER;
        try {
            batch.setShader(Assets.ORB);
            Assets.ORB.setUniformf("u_time", time);
            Assets.ORB.setUniformf("u_form", form);
            Assets.ORB.setUniformf("u_shadow", 1f);
            batch.setColor(1f, 1f, 1f, .45f * (1f - mist));
            batch.draw(ImageMaster.WHITE_SQUARE_IMG, x - 65f*s, y - 7f*s, 130f*s, 22f*s);
            batch.flush();
            batch.setShader(shader);
            shader.setUniformf("u_form", form);
            if (form > 0f) {
                shader.setUniformf("u_silhouette", 1f);
                batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
                float pulse = 0.85f + 0.15f * MathUtils.sin(time * 3.2f);
                // Overlapping translucent silhouettes form a soft outline without a second sprite.
                for (int ring = 3; ring >= 1; ring--) {
                    float radius = ring * 4f * s;
                    batch.setColor(0.64f, 0.15f, 1f, form * pulse * (0.12f / ring) * (1f - mist));
                    for (int i = 0; i < 12; i++) {
                        float angle = i * MathUtils.PI2 / 12f;
                        draw(batch, left + MathUtils.cos(angle) * radius, bottom + MathUtils.sin(angle) * radius, width, height);
                    }
                }
                batch.flush();
            }
            batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
            shader.setUniformf("u_silhouette", 0f);
            batch.setColor(1f, 1f, 1f, 1f - mist);
            draw(batch, left, bottom, width, height);
            batch.flush();
            // Independent phases keep the shoulder orbs alive while the body hovers.
            batch.setShader(Assets.ORB);
            Assets.ORB.setUniformf("u_time", time);
            Assets.ORB.setUniformf("u_form", form);
            Assets.ORB.setUniformf("u_shadow", 0f);
            batch.setColor(1f, 1f, 1f, 1f - mist);
            for (int side = -1; side <= 1; side += 2) {
                float phase = time * (1.3f + form * .7f) + side * 1.8f;
                float size = (58f + form * 8f) * s;
                float ox = x + side * 83f*s + MathUtils.sin(phase) * 5f*s;
                float oy = bottom + 335f*s + MathUtils.cos(phase) * 8f*s;
                batch.draw(ImageMaster.WHITE_SQUARE_IMG, ox-size/2f, oy-size/2f, size, size);
            }
            batch.flush();
            if (mist > 0f) {
                batch.setShader(Assets.MIST);
                Assets.MIST.setUniformf("u_time", time);
                Assets.MIST.setUniformf("u_form", form);
                batch.setColor(1f, 1f, 1f, mist);
                batch.draw(ImageMaster.WHITE_SQUARE_IMG, x - 180f*s, y + 10f*s, 360f*s, 310f*s);
                batch.flush();
            }
        } finally {
            batch.setShader(oldShader);
            batch.setBlendFunction(oldSrc, oldDst);
            batch.setColor(oldColor);
        }
        shadowpriest.legendary.WeaponRenderer.render(batch,x,y,1f-mist);
    }

    private void draw(SpriteBatch batch, float x, float y, float width, float height) {
        batch.draw(Assets.SPRITE, x, y, width, height, 0, 0,
            Assets.SPRITE.getWidth(), Assets.SPRITE.getHeight(), !flipX, flipY);
    }
}
