package shadowpriest.legendary;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.cards.*;
import java.util.HashMap;
public class WeaponRenderer {
 private static final HashMap<String,TextureRegion> CACHE=new HashMap<>();private static float time;
 private static TextureRegion get(String id){
  if(CACHE.containsKey(id))return CACHE.get(id);
  String name=id.equals(Dragonwrath.ID)?"dragonwrath":id.equals(Xalatath.ID)?"xalatath":"valanyr";
  Pixmap pix=new Pixmap(Gdx.files.internal("shadowpriest/images/weapons/"+name+".png"));
  int left=pix.getWidth(),top=pix.getHeight(),right=0,bottom=0;
  for(int x=0;x<pix.getWidth();x++)for(int y=0;y<pix.getHeight();y++)if((pix.getPixel(x,y)&255)>8){left=Math.min(left,x);right=Math.max(right,x);top=Math.min(top,y);bottom=Math.max(bottom,y);}
  Texture texture=new Texture(pix);pix.dispose();texture.setFilter(Texture.TextureFilter.Linear,Texture.TextureFilter.Linear);
  TextureRegion result=new TextureRegion(texture,left,top,right-left+1,bottom-top+1);CACHE.put(id,result);return result;
 }
 public static void render(SpriteBatch batch,float x,float y){
  if(AbstractDungeon.player==null)return;
  String id=LegendaryEquipment.State.equipped.get(AbstractDungeon.player);if(id==null)return;
  float dt=Math.min(Gdx.graphics.getDeltaTime(),0.1f);time+=dt;LegendaryEquipment.pulse=Math.max(0,LegendaryEquipment.pulse-dt);
  TextureRegion region=get(id);float scale=Settings.scale;
  float w=(id.equals(Dragonwrath.ID)?230f:id.equals(Xalatath.ID)?145f:185f)*scale,h=w*region.getRegionHeight()/region.getRegionWidth();
  float cx=x+145f*scale,cy=y+175f*scale+MathUtils.sin(time*1.8f)*7f*scale,angle=80f+MathUtils.sin(time)*4f;
  float old=batch.getPackedColor();int src=batch.getBlendSrcFunc(),dst=batch.getBlendDstFunc();
  try{
   if(LegendaryEquipment.pulse>0){batch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE);batch.setColor(0.9f,0.5f,1f,LegendaryEquipment.pulse*0.4f);
    for(int i=0;i<8;i++){float a=i*MathUtils.PI2/8f,ox=MathUtils.cos(a)*5f*scale,oy=MathUtils.sin(a)*5f*scale;batch.draw(region,cx-w/2+ox,cy-h/2+oy,w/2,h/2,w,h,1,1,angle);}}
   batch.setBlendFunction(GL20.GL_SRC_ALPHA,GL20.GL_ONE_MINUS_SRC_ALPHA);batch.setColor(Color.WHITE);batch.draw(region,cx-w/2,cy-h/2,w/2,h/2,w,h,1,1,angle);
  }finally{batch.setBlendFunction(src,dst);batch.setColor(old);}
 }
}
