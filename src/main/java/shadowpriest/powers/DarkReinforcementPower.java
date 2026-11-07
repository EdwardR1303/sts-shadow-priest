package shadowpriest.powers;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class DarkReinforcementPower extends AbstractPower {
    public static final String POWER_ID="shadowpriest:DarkReinforcement";
    public int devourCopies;
    public DarkReinforcementPower(AbstractCreature owner,boolean upgraded){
        this.owner=owner;ID=POWER_ID;name="Dark Reinforcement";type=PowerType.BUFF;amount=1;devourCopies=upgraded?1:0;
        AbilityPowerArt.apply(this,"dark_reinforcement.png");updateDescription();
    }
    @Override public void onCardDraw(AbstractCard card){
        if(card.type!=AbstractCard.CardType.CURSE)return;
        flash();addToBot(new GainEnergyAction(amount));addToBot(new DrawCardAction(owner,amount));
    }
    public void onDevour(){if(devourCopies>0){
        flash();
        com.megacrit.cardcrawl.actions.AbstractGameAction energy=new GainEnergyAction(devourCopies),draw=new DrawCardAction(owner,devourCopies);
        shadowpriest.details.DetailsTracker.tag(energy,shadowpriest.details.DetailsTracker.power(this));
        shadowpriest.details.DetailsTracker.tag(draw,shadowpriest.details.DetailsTracker.power(this));
        addToBot(energy);addToBot(draw);
    }}
    @Override public void updateDescription(){description="Whenever you draw a Curse, gain #b"+amount+" Energy and draw #b"+amount+" card(s)."+(devourCopies>0?" Whenever you Devour a Curse, gain #b"+devourCopies+" Energy and draw #b"+devourCopies+" card(s).":"");}
}
