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
    static void yoggDescription(String description){
        FrameBuffer frame=new FrameBuffer(Pixmap.Format.RGBA8888,600,260,false);SpriteBatch batch=new SpriteBatch();frame.begin();
        try{
            Gdx.gl.glClearColor(.055f,.045f,.085f,1);Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0,0,600,260));batch.begin();
            com.megacrit.cardcrawl.helpers.FontHelper.renderSmartText(batch,com.megacrit.cardcrawl.helpers.FontHelper.tipBodyFont,description,35,190,530,34,Color.WHITE);
            batch.end();Pixmap pixels=ScreenUtils.getFrameBufferPixmap(0,0,600,260),flipped=new Pixmap(600,260,Pixmap.Format.RGBA8888);
            try{for(int x=0;x<600;x++)for(int y=0;y<260;y++)flipped.drawPixel(x,259-y,pixels.getPixel(x,y));
                Gdx.files.local("card-review").mkdirs();PixmapIO.writePNG(Gdx.files.local("card-review/yogg-energy-description.png"),flipped);
            }finally{pixels.dispose();flipped.dispose();}
        }finally{frame.end();batch.dispose();frame.dispose();}
    }
    static void singularityFx(){
        FrameBuffer frame=new FrameBuffer(Pixmap.Format.RGBA8888,1200,700,false);
        SpriteBatch batch=new SpriteBatch();frame.begin();
        try{
            Gdx.gl.glClearColor(.04f,.02f,.07f,1);Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0,0,1200,700));batch.begin();
            for(int tier=0;tier<3;tier++)for(int stage=0;stage<2;stage++){
                shadowpriest.vfx.ShadowSpellEffect effect=new shadowpriest.vfx.ShadowSpellEffect(200+tier*400,stage==0?510:180,tier*50);
                effect.duration=(.65f+.15f*tier)*(stage==0?.65f:.2f);effect.render(batch);
            }
            batch.end();Pixmap pixels=ScreenUtils.getFrameBufferPixmap(0,0,1200,700),flipped=new Pixmap(1200,700,Pixmap.Format.RGBA8888);
            try{for(int x=0;x<1200;x++)for(int y=0;y<700;y++)flipped.drawPixel(x,699-y,pixels.getPixel(x,y));
                Gdx.files.local("card-review").mkdirs();PixmapIO.writePNG(Gdx.files.local("card-review/observations-singularity-fx.png"),flipped);
            }finally{pixels.dispose();flipped.dispose();}
        }finally{frame.end();batch.dispose();frame.dispose();}
    }
    private static String pendingCombat, capturedCombat;
    private static int captureFrames;

    static void requestCombat(String name) {
        pendingCombat = name;
        capturedCombat = null;
        captureFrames = 10;
    }

    static boolean capturedCombat(String name) { return name.equals(capturedCombat); }

    /** Capture the real combat framebuffer after the normal character and HUD draw. */
    static void captureCombat(SpriteBatch batch) {
        if (pendingCombat == null || --captureFrames > 0) return;
        batch.flush();
        int width = Gdx.graphics.getWidth(), height = Gdx.graphics.getHeight();
        Pixmap pixels = ScreenUtils.getFrameBufferPixmap(0, 0, width, height);
        Pixmap flipped = new Pixmap(width, height, Pixmap.Format.RGBA8888);
        try {
            for (int x=0; x<width; x++) for (int y=0; y<height; y++)
                flipped.drawPixel(x, height-1-y, pixels.getPixel(x,y));
            Gdx.files.local("card-review").mkdirs();
            PixmapIO.writePNG(Gdx.files.local("card-review/combat-"+pendingCombat+".png"), flipped);
            capturedCombat = pendingCombat;
            pendingCombat = null;
        } finally { pixels.dispose(); flipped.dispose(); }
    }

    static void weapon(String id) {
        FrameBuffer frame = new FrameBuffer(Pixmap.Format.RGBA8888, 600, 600, false);
        SpriteBatch batch = new SpriteBatch();
        com.megacrit.cardcrawl.characters.AbstractPlayer player = com.megacrit.cardcrawl.dungeons.AbstractDungeon.player;
        String previous = shadowpriest.legendary.LegendaryEquipment.State.equipped.get(player);
        frame.begin();
        try {
            shadowpriest.legendary.LegendaryEquipment.State.equipped.set(player, id);
            Gdx.gl.glClearColor(0.05f,0.03f,0.08f,1f); Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            batch.setProjectionMatrix(new Matrix4().setToOrtho2D(0,0,600,600));
            batch.begin(); new shadowpriest.character.ShadowPriestAnimation().renderSprite(batch,250,120); batch.end();
            Pixmap pixels=ScreenUtils.getFrameBufferPixmap(0,0,600,600);
            Pixmap flipped=new Pixmap(600,600,Pixmap.Format.RGBA8888);
            for(int x=0;x<600;x++) for(int y=0;y<600;y++) flipped.drawPixel(x,599-y,pixels.getPixel(x,y));
            PixmapIO.writePNG(Gdx.files.local("card-review/weapon-"+id.split(":")[1]+".png"),flipped);
            pixels.dispose(); flipped.dispose();
        } finally {
            shadowpriest.legendary.LegendaryEquipment.State.equipped.set(player,previous);
            frame.end(); batch.dispose(); frame.dispose();
        }
    }
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
    static void mastery(AbstractPower power) { powerIcon(power, "mastery-icon"); }
    static void powerIcon(AbstractPower power, String name) {
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
            PixmapIO.writePNG(Gdx.files.local(name+".png"),flipped);
            pixels.dispose(); flipped.dispose();
        } finally { frame.end(); batch.dispose(); frame.dispose(); }
    }
}
