package shadowpriest.dev;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.utils.ScreenUtils;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.powers.AbstractPower;

/** Opt-in game-rendered review images; never called by normal gameplay. */
final class CardArtReview {
    static void card(AbstractCard card, String name) {
        FrameBuffer frame = new FrameBuffer(Pixmap.Format.RGBA8888, 600, 800, false);
        SpriteBatch batch = new SpriteBatch();
        frame.begin();
        try {
            Gdx.gl.glClearColor(0.05f,0.03f,0.08f,1f); Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0,0,600,800));
            card.current_x=300; card.current_y=400; card.drawScale=1; card.angle=0;
            batch.begin(); card.render(batch); batch.end();
            Pixmap pixels=ScreenUtils.getFrameBufferPixmap(0,0,600,800);
            Pixmap flipped=new Pixmap(600,800,Pixmap.Format.RGBA8888);
            for(int x=0;x<600;x++) for(int y=0;y<800;y++) flipped.drawPixel(x,799-y,pixels.getPixel(x,y));
            Gdx.files.local("card-review").mkdirs();
            PixmapIO.writePNG(Gdx.files.local("card-review/"+name+".png"),flipped);
            pixels.dispose(); flipped.dispose();
        } finally { frame.end(); batch.dispose(); frame.dispose(); }
    }
    static void mastery(AbstractPower power) {
        FrameBuffer frame=new FrameBuffer(Pixmap.Format.RGBA8888,96,96,false);
        SpriteBatch batch=new SpriteBatch();
        frame.begin();
        try {
            Gdx.gl.glClearColor(0.05f,0.03f,0.08f,1f); Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0,0,96,96));
            batch.begin(); batch.draw(power.region48,0,0,96,96); batch.end();
            Pixmap pixels=ScreenUtils.getFrameBufferPixmap(0,0,96,96);
            Pixmap flipped=new Pixmap(96,96,Pixmap.Format.RGBA8888);
            for(int x=0;x<96;x++) for(int y=0;y<96;y++) flipped.drawPixel(x,95-y,pixels.getPixel(x,y));
            PixmapIO.writePNG(Gdx.files.local("mastery-icon.png"),flipped);
            pixels.dispose(); flipped.dispose();
        } finally { frame.end(); batch.dispose(); frame.dispose(); }
    }
}
