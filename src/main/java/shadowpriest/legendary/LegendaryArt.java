package shadowpriest.legendary;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import java.util.IdentityHashMap;
public class LegendaryArt {
 private static final IdentityHashMap<TextureAtlas.AtlasRegion,TextureAtlas.AtlasRegion> CACHE=new IdentityHashMap<>();
 private static TextureAtlas.AtlasRegion orange(TextureAtlas.AtlasRegion original){
  if(CACHE.containsKey(original))return CACHE.get(original);
  TextureData data=original.getTexture().getTextureData();if(!data.isPrepared())data.prepare();Pixmap src=data.consumePixmap();
  Pixmap dst=new Pixmap(original.getRegionWidth(),original.getRegionHeight(),Pixmap.Format.RGBA8888);Color c=new Color();
  for(int x=0;x<dst.getWidth();x++)for(int y=0;y<dst.getHeight();y++){
   Color.rgba8888ToColor(c,src.getPixel(original.getRegionX()+x,original.getRegionY()+y));
   float v=Math.max(c.r,Math.max(c.g,c.b));dst.setColor(v,v*0.43f,v*0.06f,c.a);dst.drawPixel(x,y);
  }
  Texture t=new Texture(dst);t.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
  TextureAtlas.AtlasRegion result=new TextureAtlas.AtlasRegion(original);result.setTexture(t);result.setRegion(0,0,dst.getWidth(),dst.getHeight());
  dst.dispose();if(data.disposePixmap())src.dispose();CACHE.put(original,result);return result;
 }
 public static void apply(CustomCard card){
  card.bannerSmallRegion=orange(ImageMaster.CARD_BANNER_RARE);card.bannerLargeRegion=orange(ImageMaster.CARD_BANNER_RARE_L);
  card.frameSmallRegion=orange(ImageMaster.CARD_FRAME_POWER_RARE);card.frameLargeRegion=orange(ImageMaster.CARD_FRAME_POWER_RARE_L);
  card.frameLeftRegion=orange(ImageMaster.CARD_RARE_FRAME_LEFT);card.frameMiddleRegion=orange(ImageMaster.CARD_RARE_FRAME_MID);card.frameRightRegion=orange(ImageMaster.CARD_RARE_FRAME_RIGHT);
  card.frameLeftLargeRegion=orange(ImageMaster.CARD_RARE_FRAME_LEFT_L);card.frameMiddleLargeRegion=orange(ImageMaster.CARD_RARE_FRAME_MID_L);card.frameRightLargeRegion=orange(ImageMaster.CARD_RARE_FRAME_RIGHT_L);
 }
}
