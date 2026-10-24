package shadowpriest.actions;
import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import java.util.ArrayList;
/** Real draw hooks, but no reshuffle and only matching cards from the current draw pile. */
public class TypedDrawAction extends AbstractGameAction {
 private final AbstractCard.CardType type;
 public TypedDrawAction(AbstractCard.CardType type,int count){this.type=type;amount=count;}
 public void update(){
  AbstractPlayer p=AbstractDungeon.player;isDone=true;
  if(amount<=0 || p.hasPower("No Draw") || p.hand.size()>=BaseMod.MAX_HAND_SIZE) return;
  ArrayList<AbstractCard> eligible=new ArrayList<>();
  for(AbstractCard c:p.drawPile.group) if(c.type==type) eligible.add(c);
  if(eligible.isEmpty()) return;
  AbstractCard c=eligible.get(AbstractDungeon.cardRandomRng.random(eligible.size()-1));
  p.drawPile.removeCard(c);p.drawPile.addToTop(c);
  if(amount>1) addToTop(new TypedDrawAction(type,amount-1));
  addToTop(new DrawCardAction(p,1));
 }
}
