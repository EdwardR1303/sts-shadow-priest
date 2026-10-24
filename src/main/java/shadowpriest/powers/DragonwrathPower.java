package shadowpriest.powers;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class DragonwrathPower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:Dragonwrath";
 public DragonwrathPower(AbstractCreature p,int chance){owner=p;amount=chance;ID=POWER_ID;name="Dragonwrath";type=PowerType.BUFF;AbilityPowerArt.apply(this,"dragonwrath.jpg");updateDescription();}
 @Override public void onUseCard(AbstractCard card,UseCardAction action){
 if(card.type!=AbstractCard.CardType.ATTACK || card.purgeOnUse || AbstractDungeon.miscRng.random(99)>=amount) return;
 flash();shadowpriest.legendary.LegendaryEquipment.pulse=1f;
 final AbstractCard copy=card.makeStatEquivalentCopy(); final int x=card.energyOnUse;
 final AbstractMonster originalTarget=action.target instanceof AbstractMonster?(AbstractMonster)action.target:null;
 copy.purgeOnUse=true;copy.freeToPlayOnce=true;copy.energyOnUse=x;
 addToBot(new AbstractGameAction(){ public void update(){
 AbstractMonster chosen=originalTarget;
 if(chosen!=null && chosen.isDeadOrEscaped()) chosen=AbstractDungeon.getRandomMonster();
 if(AbstractDungeon.getMonsters().areMonstersBasicallyDead()){isDone=true;return;}
 AbstractDungeon.player.limbo.addToBottom(copy);
 copy.current_x=card.current_x;copy.current_y=card.current_y;
 if(chosen!=null) copy.calculateCardDamage(chosen);else copy.applyPowers();
 AbstractDungeon.actionManager.addCardQueueItem(new CardQueueItem(copy,chosen,x,true,true),true);isDone=true;
 }});
 }
 @Override public void updateDescription(){description="Each played Attack has a #b"+amount+"% chance to repeat for free with the same X. Repeats cannot trigger this again.";}
}
