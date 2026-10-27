package shadowpriest.powers;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class EventHorizonPower extends AbstractPower {
    public static final String POWER_ID="shadowpriest:EventHorizon";
    public EventHorizonPower(AbstractCreature owner,int amount){this.owner=owner;this.amount=amount;ID=POWER_ID;name="Event Horizon";type=PowerType.BUFF;AbilityPowerArt.apply(this,"event_horizon.png");updateDescription();}
    @Override public void onUseCard(AbstractCard card,UseCardAction action){if(card.type==AbstractCard.CardType.POWER)addToBot(new ApplyPowerAction(owner,owner,new HastePower(owner,amount),amount));}
    @Override public void updateDescription(){description="Whenever you play a Power, gain #b"+amount+" Haste.";}
}
