package shadowpriest.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.cards.PriestCard;
import java.util.ArrayList;
public class SerendipityAction extends AbstractGameAction {
 private boolean opened;
 public void update(){
  if(!opened){
   CardGroup options=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
   options.addToBottom(new Choice(true));options.addToBottom(new Choice(false));
   AbstractDungeon.gridSelectScreen.open(options,1,"Serendipity: choose your new hand.",false);opened=true;return;
  }
  if(AbstractDungeon.gridSelectScreen.selectedCards.isEmpty()) return;
  AbstractCard c=AbstractDungeon.gridSelectScreen.selectedCards.get(0);
  AbstractDungeon.gridSelectScreen.selectedCards.clear();
  if(c instanceof Choice) exchange(((Choice)c).attacks);
  isDone=true;
 }
 public static void exchange(boolean wantAttacks){
  AbstractPlayer p=AbstractDungeon.player;
  AbstractCard.CardType discard=wantAttacks?AbstractCard.CardType.SKILL:AbstractCard.CardType.ATTACK;
  int count=0;
  for(AbstractCard c:new ArrayList<>(p.hand.group)) if(c.type==discard){
   p.hand.moveToDiscardPile(c);c.triggerOnManualDiscard();GameActionManager.incrementDiscard(false);count++;
  }
  p.hand.refreshHandLayout();
  AbstractDungeon.actionManager.addToTop(new TypedDrawAction(wantAttacks?AbstractCard.CardType.ATTACK:AbstractCard.CardType.SKILL,count));
 }
 public static class Choice extends PriestCard {
  public final boolean attacks;
  public Choice(boolean attacks){super("shadowpriest:"+(attacks?"SerendipityAttack":"SerendipitySkill"),-2,CardType.SKILL,CardRarity.SPECIAL,CardTarget.NONE);this.attacks=attacks;}
  public void use(AbstractPlayer p,AbstractMonster m){} public void upgrade(){}
  @Override public AbstractCard makeCopy(){return new Choice(attacks);}
 }
}
