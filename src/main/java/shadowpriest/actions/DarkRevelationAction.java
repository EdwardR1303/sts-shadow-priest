package shadowpriest.actions;

import basemod.BaseMod;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardGroup;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.cards.*;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/** Direct applications only; maintenance and Powers which enable later applications are excluded. */
public final class DarkRevelationAction extends AbstractGameAction {
    private static final Set<String> APPLICATIONS=new HashSet<>(Arrays.asList(
        VampiricTouch.ID,ShadowWordPain.ID,UnfurlingDarkness.ID,BloodMagic.ID,TentacleSlam.ID,
        ShadowWordMadness.ID,ShadowWordTorment.ID,MindTrauma.ID,VoidTendrils.ID,
        EncroachingShadows.ID,DarkVoid.ID,ShadowCrash.ID,VoidBlast.ID));
    private boolean opened;
    public static boolean eligible(AbstractCard c) { return APPLICATIONS.contains(c.cardID); }
    public DarkRevelationAction() { actionType=ActionType.CARD_MANIPULATION; }
    @Override public void update() {
        AbstractPlayer p=AbstractDungeon.player;
        if(!opened) {
            CardGroup choices=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c:p.drawPile.group) if(eligible(c)) choices.addToTop(c);
            if(choices.isEmpty()) { isDone=true;return; }
            choices.sortAlphabetically(true);
            AbstractDungeon.gridSelectScreen.open(choices,1,"Choose a DoT application card. It costs 0 this turn.",false);
            opened=true;return;
        }
        if(AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())return;
        AbstractCard selected=AbstractDungeon.gridSelectScreen.selectedCards.get(0);
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        if(p.drawPile.contains(selected) && eligible(selected)) {
            selected.setCostForTurn(0);
            if(p.hand.size()>=BaseMod.MAX_HAND_SIZE) {
                p.drawPile.moveToDiscardPile(selected);p.createHandIsFullDialog();
            } else {
                AbstractDungeon.getCurrRoom().souls.remove(selected);p.drawPile.moveToHand(selected);
            }
        }
        isDone=true;
    }
}
