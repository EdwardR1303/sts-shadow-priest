package shadowpriest.powers;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.mechanics.Channeling;
public class ProtectedChannelPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:ProtectedChannel";
 public ProtectedChannelPower(AbstractCreature owner,int amount){this.owner=owner;this.amount=amount;ID=POWER_ID;name="Protected Channel";type=PowerType.BUFF;loadRegion("blur");updateDescription();}
 @Override public void onUseCard(AbstractCard c,UseCardAction a){if(c.cost!=-1)return;int x=Channeling.effective(c);final int base=amount;
  addToBot(new com.megacrit.cardcrawl.actions.AbstractGameAction(){public void update(){
   float adjusted=base;for(AbstractPower power:owner.powers)if(!power.ID.equals("Dexterity"))adjusted=power.modifyBlock(adjusted);
   for(AbstractPower power:owner.powers)if(!power.ID.equals("Dexterity"))adjusted=power.modifyBlockLast(adjusted);
   int fixedBlock=Math.max(0,(int)Math.floor(adjusted));
   for(int i=0;i<x;i++)addToTop(new GainBlockAction(owner,owner,fixedBlock));isDone=true;
  }});
 }
 @Override public void updateDescription(){description="Whenever you play an X-cost card, gain #b"+amount+" Block X times.";}
}
