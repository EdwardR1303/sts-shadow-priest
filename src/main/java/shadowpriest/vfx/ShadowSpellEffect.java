package shadowpriest.vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

/** Small, deterministic animations using the game's existing VFX atlas; no gameplay RNG. */
public final class ShadowSpellEffect extends AbstractGameEffect {
    public enum Style { SPIKE, BOLT, FLAY, TORRENT, BLAST, SEAR, COLLAPSE, ASCEND, RAY }
    private final Style style;
    private final float sx, sy, tx, ty;
    private final AtlasRegion laser, spark;
    private int collapseTier;

    public static int collapseTier(int damage) { return damage>=100?2:damage>=50?1:0; }
    public ShadowSpellEffect(float tx,float ty,int damage) {
        this(Style.COLLAPSE,tx,ty,tx,ty);
        collapseTier=collapseTier(damage);
        startingDuration=duration=.65f+.15f*collapseTier;
    }

    public ShadowSpellEffect(Style style, float sx, float sy, float tx, float ty) {
        this.style = style; this.sx = sx; this.sy = sy; this.tx = tx; this.ty = ty;
        laser = ImageMaster.vfxAtlas.findRegion("combat/laserThick");
        spark = ImageMaster.vfxAtlas.findRegion("glowSpark");
        startingDuration = duration = style == Style.RAY || style == Style.ASCEND ? .7f : .4f;
    }

    @Override public void update() {
        duration -= Gdx.graphics.getDeltaTime();
        if (duration <= 0) isDone = true;
    }

    @Override public void render(SpriteBatch sb) {
        float t = MathUtils.clamp(1 - duration / startingDuration, 0, 1);
        float alpha = Math.min(1, (1 - t) * 3);
        Color old = sb.getColor().cpy();
        int src = sb.getBlendSrcFunc(), dst = sb.getBlendDstFunc();
        sb.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE);
        try {
            switch (style) {
                case SPIKE:
                case BOLT:
                    float progress = Math.min(1, t * 3);
                    float x = sx + (tx - sx) * progress, y = sy + (ty - sy) * progress;
                    beam(sb, x - (tx - sx) * .14f, y - (ty - sy) * .14f, x, y,
                            style == Style.SPIKE ? 10 : 22, alpha);
                    orb(sb, x, y, style == Style.SPIKE ? 25 : 42, alpha);
                    if (progress == 1) circle(sb, tx, ty, 35 + t * 65, alpha * .6f, t * 80);
                    break;
                case FLAY:
                case TORRENT:
                    beam(sb, sx, sy, tx, ty, style == Style.FLAY ? 25 : 75, alpha);
                    for (int i = 0; i < 8; i++) {
                        float f = i / 7f;
                        orb(sb, sx + (tx - sx) * f, sy + (ty - sy) * f + MathUtils.sin(f * 20 - t * 16) * 9 * Settings.scale,
                                style == Style.FLAY ? 13 : 24, alpha * .65f);
                    }
                    orb(sb, tx, ty, style == Style.FLAY ? 50 : 95, alpha);
                    break;
                case RAY:
                    // Hyperbeam's laserThick texture, layered with a violet halo and white core.
                    float aim = MathUtils.atan2(ty - sy, tx - sx);
                    beam(sb, sx, sy, sx + MathUtils.cos(aim) * Settings.WIDTH,
                            sy + MathUtils.sin(aim) * Settings.WIDTH, 450 + 25 * MathUtils.sin(t * 60), alpha);
                    orb(sb, sx, sy, 160, alpha);
                    break;
                case COLLAPSE: {
                    float size=140+collapseTier*120;
                    float pull=Math.max(0,1-t/.72f);
                    collapseRing(sb,tx,ty,size*2*pull+12,alpha);
                    for(int ring=0;ring<=collapseTier;ring++)
                        collapseRing(sb,tx,ty,size*(1.4f-ring*.3f)*pull+10,alpha*.65f);
                    int strands=8+collapseTier*8;
                    for (int i = 0; i < strands; i++) {
                        float angle = i * MathUtils.PI2 / strands + t * 3;
                        float radius = pull * size * Settings.scale;
                        float px=tx+MathUtils.cos(angle)*radius,py=ty+MathUtils.sin(angle)*radius;
                        beam(sb,px,py,tx+MathUtils.cos(angle+.35f)*radius*.65f,
                            ty+MathUtils.sin(angle+.35f)*radius*.65f,6+collapseTier*3,alpha*.7f);
                        orb(sb,px,py,15+collapseTier*7,alpha);
                    }
                    orb(sb,tx,ty,(35+size*.5f)*(1-pull),alpha);
                    if(t>.72f){
                        float burst=(t-.72f)/.28f;
                        collapseRing(sb,tx,ty,size*2*burst,(1-burst)*.9f);
                        orb(sb,tx,ty,size*(1-burst),1-burst);
                    }
                    break;
                }
                case ASCEND:
                    circle(sb, tx, ty, 80 + 340 * t, alpha * .8f, t * 90);
                    for (int i = 0; i < 9; i++) {
                        float angle = i * MathUtils.PI2 / 9;
                        float radius = (25 + 95 * t) * Settings.scale;
                        orb(sb, tx + MathUtils.cos(angle) * radius, ty + MathUtils.sin(angle) * radius + t * 95 * Settings.scale, 45, alpha);
                    }
                    break;
                default:
                    float size = style == Style.SEAR ? 180 : 125;
                    orb(sb, tx, ty, size * (1 - t * .5f), alpha);
                    circle(sb, tx, ty, size * (.4f + t), alpha * .8f, t * 120);
                    break;
            }
        } finally { sb.setColor(old); sb.setBlendFunction(src, dst); }
    }

    private void beam(SpriteBatch sb, float x1, float y1, float x2, float y2, float width, float alpha) {
        float dx = x2 - x1, dy = y2 - y1, length = (float)Math.sqrt(dx * dx + dy * dy);
        float angle = MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees;
        float h = width * Settings.scale;
        sb.setColor(.48f, .08f, 1f, alpha * .85f);
        sb.draw(laser, x1, y1 - h / 2, 0, h / 2, length, h, 1, 1, angle);
        sb.setColor(.9f, .65f, 1f, alpha);
        sb.draw(laser, x1, y1 - h / 8, 0, h / 8, length, h / 4, 1, 1, angle);
    }
    private void collapseRing(SpriteBatch sb,float x,float y,float diameter,float alpha){
        float radius=diameter*Settings.scale/2;
        for(int i=0;i<64;i++){
            float a=i*MathUtils.PI2/64,b=(i+1)*MathUtils.PI2/64;
            float x1=x+MathUtils.cos(a)*radius,y1=y+MathUtils.sin(a)*radius;
            float dx=(MathUtils.cos(b)-MathUtils.cos(a))*radius,dy=(MathUtils.sin(b)-MathUtils.sin(a))*radius;
            float length=(float)Math.sqrt(dx*dx+dy*dy),angle=MathUtils.atan2(dy,dx)*MathUtils.radiansToDegrees;
            for(int layer=0;layer<2;layer++){
                float width=(layer==0?6:1.5f)*Settings.scale;
                sb.setColor(layer==0?.45f:.85f,layer==0?.1f:.5f,1,alpha*(layer==0?.3f:.9f));
                sb.draw(ImageMaster.WHITE_SQUARE_IMG,x1,y1-width/2,0,width/2,length+Settings.scale,width,1,1,angle,0,0,1,1,false,false);
            }
        }
    }
    private void orb(SpriteBatch sb, float x, float y, float size, float alpha) {
        float s = size * Settings.scale;
        sb.setColor(.6f, .12f, 1f, alpha);
        sb.draw(spark, x - s / 2, y - s / 2, s, s);
        sb.setColor(.95f, .7f, 1f, alpha * .8f);
        sb.draw(spark, x - s / 6, y - s / 6, s / 3, s / 3);
    }
    private void circle(SpriteBatch sb, float x, float y, float size, float alpha, float angle) {
        float radius = size * Settings.scale / 2;
        sb.setColor(.65f, .18f, 1f, alpha);
        // Thin curved filaments leave the target visible instead of covering it with a solid disc.
        for (int i = 0; i < 32; i++) {
            float a = (angle + i * 360f / 32) * MathUtils.degreesToRadians;
            float b = a + MathUtils.PI2 / 32;
            float x1 = x + MathUtils.cos(a) * radius, y1 = y + MathUtils.sin(a) * radius;
            float dx = MathUtils.cos(b) * radius + x - x1, dy = MathUtils.sin(b) * radius + y - y1;
            float h = 18 * Settings.scale;
            sb.draw(laser, x1, y1 - h / 2, 0, h / 2, (float)Math.sqrt(dx * dx + dy * dy), h,
                    1, 1, MathUtils.atan2(dy, dx) * MathUtils.radiansToDegrees);
        }
    }
    @Override public void dispose() { /* Atlas textures belong to the game. */ }
}
