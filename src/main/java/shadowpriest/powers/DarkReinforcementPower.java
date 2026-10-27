package shadowpriest.powers;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class DarkReinforcementPower extends AbstractPower {
    public static final String POWER_ID="shadowpriest:DarkReinforcement";
    public int mastery;
    public DarkReinforcementPower(AbstractCreature owner,boolean upgraded){
        this.owner=owner;ID=POWER_ID;name="Dark Reinforcement";type=PowerType.BUFF;amount=1;mastery=upgraded?1:0;
        AbilityPowerArt.apply(this,"dark_reinforcement.png");updateDescription();
    }
    @Override public void onCardDraw(AbstractCard card){
        if(card.type!=AbstractCard.CardType.CURSE)return;
        flash();addToBot(new GainEnergyAction(amount));addToBot(new DrawCardAction(owner,amount));
        if(mastery>0)addToBot(new ApplyPowerAction(owner,owner,new MasteryPower(owner,mastery),mastery));
    }
    @Override public void updateDescription(){description="Whenever you draw a Curse, gain #b"+amount+" Energy and draw #b"+amount+" card(s)."+(mastery>0?" Gain #b"+mastery+" Mastery as well.":"");}
}
