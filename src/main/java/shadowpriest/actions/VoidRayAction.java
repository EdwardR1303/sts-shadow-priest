package shadowpriest.actions;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.cards.VoidRay;
/** Choose before delivering damage, so a lethal final hit cannot skip the earned permanent growth. */
public final class VoidRayAction extends AbstractGameAction {
    private final VoidRay card;private final AbstractMonster enemy;private final int damage,growth;
    private boolean opened;
    public VoidRayAction(VoidRay card,AbstractMonster enemy,int damage,int growth){this.card=card;this.enemy=enemy;this.damage=damage;this.growth=growth;actionType=ActionType.CARD_MANIPULATION;}
    @Override public void update(){
        AbstractPlayer p=AbstractDungeon.player;
        if(!opened){
            CardGroup choices=new CardGroup(CardGroup.CardGroupType.UNSPECIFIED);
            for(AbstractCard c:p.hand.group)if(c.type==AbstractCard.CardType.CURSE)choices.addToTop(c);
            if(choices.isEmpty()){resolve(null);return;}
            AbstractDungeon.gridSelectScreen.open(choices,1,"Choose a Curse to Exhaust and empower Void Ray.",false);opened=true;return;
        }
        if(AbstractDungeon.gridSelectScreen.selectedCards.isEmpty())return;
        AbstractCard curse=AbstractDungeon.gridSelectScreen.selectedCards.remove(0);
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        resolve(curse);
    }
    private void resolve(AbstractCard curse){
        AbstractPlayer p=AbstractDungeon.player;
        if(enemy!=null&&!enemy.isDeadOrEscaped()&&enemy.currentHealth>0){
            shadowpriest.vfx.ShadowFx.show(shadowpriest.vfx.ShadowSpellEffect.Style.RAY,p,enemy);
            enemy.damage(new DamageInfo(p,damage,card.damageTypeForTurn));
        }
        // Finish in the same update even on a lethal final hit; victory clears queued actions.
        if(curse!=null&&p.hand.contains(curse)&&curse.type==AbstractCard.CardType.CURSE){
            p.hand.moveToExhaustPile(curse);card.grow(growth);
        }
        isDone=true;
    }
}
