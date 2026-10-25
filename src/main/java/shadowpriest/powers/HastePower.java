package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
public class HastePower extends AbstractPower {
 private static com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion small, large;
 public static final String POWER_ID="shadowpriest:Haste";
 public HastePower(AbstractCreature owner,int amount){this.owner=owner;this.amount=amount;ID=POWER_ID;name="Haste";type=PowerType.BUFF;isTurnBased=true;loadRegion("dexterity");
  if(small==null){small=PowerIconTint.tint(region48,1f,0.88f,0.1f);large=PowerIconTint.tint(region128,1f,0.88f,0.1f);}
  region48=small;region128=large;updateDescription();}
 @Override public void atEndOfTurn(boolean isPlayer){if(isPlayer)addToBot(new RemoveSpecificPowerAction(owner,owner,this));}
 @Override public void updateDescription(){description="X-cost cards have #b"+amount+" additional effective X this turn, including at zero energy. Stacks with Chemical X.";}
}
