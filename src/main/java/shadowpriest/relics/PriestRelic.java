package shadowpriest.relics;

import basemod.abstracts.CustomRelic;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.character.ShadowPriest;
import java.util.HashMap;

/** Shared in-game medallion frame around the original WoW icon artwork. */
public abstract class PriestRelic extends CustomRelic {
    private static final HashMap<String,Texture> ART = new HashMap<>();
    private final String artPath;
    protected PriestRelic(String id, RelicTier tier, String artPath) {
        super(id, art(artPath,128), tier, LandingSound.MAGICAL);
        this.artPath=artPath;
    }
    @Override public void loadLargeImg() { largeImg=art(artPath,256); }
    @Override public String getUpdatedDescription() { return DESCRIPTIONS[0]; }
    @Override public boolean canSpawn() { return AbstractDungeon.player instanceof ShadowPriest; }
    private static Texture art(String path,int size) {
        String key=path+size;if(ART.containsKey(key))return ART.get(key);
        Pixmap source=new Pixmap(Gdx.files.internal(path));
        Pixmap canvas=new Pixmap(size,size,Pixmap.Format.RGBA8888);
        try {
            // Transparent silhouette, bevelled rim, inset original icon. Source files stay intact.
            for(int x=0;x<size;x++)for(int y=0;y<size;y++) {
                float nx=(x-size/2f)/(size*.25f),ny=(y-size/2f)/(size*.25f),r=(float)Math.sqrt(nx*nx+ny*ny);
                if(r>1f)continue;
                if(r>.87f) {
                    float shade=.6f-.2f*ny;
                    canvas.setColor(shade,.35f*shade,.7f*shade,1f);canvas.drawPixel(x,y);
                } else {
                    int sx=Math.min(source.getWidth()-1,Math.max(0,(int)((nx/.87f+1)*.5f*source.getWidth())));
                    int sy=Math.min(source.getHeight()-1,Math.max(0,(int)((ny/.87f+1)*.5f*source.getHeight())));
                    canvas.drawPixel(x,y,source.getPixel(sx,sy));
                }
            }
            Texture texture=new Texture(canvas);texture.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
            ART.put(key,texture);return texture;
        } finally {source.dispose();canvas.dispose();}
    }
}
