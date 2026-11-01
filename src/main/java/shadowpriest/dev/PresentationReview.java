package shadowpriest.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.IntangiblePlayerPower;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;

/** Short opt-in visual review, without simulating a run or taking player turns. */
final class PresentationReview {
    static void append(Queue<Runnable> steps) {
        steps.add(() -> {
            CombatSmokeTest.check(AbstractDungeon.player.getTitle(
                shadowpriest.character.ShadowPriest.Meta.SHADOW_PRIEST).contains("Xal'atath"), "Xal'atath title loads");
            AbstractDungeon.player.hand.clear();
            VoidformRelic.current().reset();
            CardArtReview.requestCombat("xalatath-normal");
        });
        waitFrames(steps, 6);
        steps.add(() -> VoidformRelic.current().enterImmediately());
        waitFrames(steps, 5);
        steps.add(() -> CardArtReview.requestCombat("xalatath-voidform"));
        waitFrames(steps, 6);
        steps.add(() -> AbstractDungeon.player.addPower(new IntangiblePlayerPower(AbstractDungeon.player,1)));
        waitFrames(steps, 8);
        steps.add(() -> CardArtReview.requestCombat("xalatath-dispersion"));
        waitFrames(steps, 6);
        steps.add(() -> {
            surfaces();
            CombatSmokeTest.check(CardArtReview.capturedCombat("xalatath-dispersion"), "Normal, Voidform, and Dispersion rendered without shader errors");
        });
    }
    private static void waitFrames(Queue<Runnable> steps,int count) {
        for(int i=0;i<count;i++)steps.add(() -> {});
    }
    private static void surfaces() {
        FrameBuffer frame=new FrameBuffer(Pixmap.Format.RGBA8888,1920,1080,false);
        SpriteBatch batch=new SpriteBatch();
        frame.begin();
        try {
            Gdx.gl.glClearColor(.06f,.04f,.09f,1);Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0,0,1920,1080));
            batch.begin();
            draw(batch,"select/portrait.png",0,540,960,540);
            draw(batch,"ending/heart_victory.png",960,540,960,540);
            AbstractDungeon.player.renderShoulderImg(batch);
            batch.draw(AbstractDungeon.player.corpseImg,1110,130);
            batch.end();
            Pixmap pixels=ScreenUtils.getFrameBufferPixmap(0,0,1920,1080);
            Pixmap flipped=new Pixmap(1920,1080,Pixmap.Format.RGBA8888);
            try {
                for(int x=0;x<1920;x++)for(int y=0;y<1080;y++)flipped.drawPixel(x,1079-y,pixels.getPixel(x,y));
                PixmapIO.writePNG(Gdx.files.local("card-review/xalatath-surfaces.png"),flipped);
            } finally {pixels.dispose();flipped.dispose();}
        } finally {frame.end();batch.dispose();frame.dispose();}
    }
    private static void draw(SpriteBatch batch,String name,float x,float y,float w,float h) {
        Texture texture=ImageMaster.loadImage("shadowpriest/images/character/"+name);
        batch.draw(texture,x,y,w,h);
        batch.flush();texture.dispose();
    }
}
