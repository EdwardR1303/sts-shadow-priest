package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;

public class DarkReinforcement extends PriestCard {
    public static final String ID="shadowpriest:DarkReinforcement";
    public DarkReinforcement() { super(ID,1,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);  }
    @Override public float getTitleFontSize() { return 18f; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { final boolean mastery=upgraded;
        addToBot(new AbstractGameAction(){public void update(){
            if(p.getPower(DarkReinforcementPower.POWER_ID) instanceof DarkReinforcementPower){
                DarkReinforcementPower power=(DarkReinforcementPower)p.getPower(DarkReinforcementPower.POWER_ID);
                power.stackPower(1); if(mastery)power.mastery++;power.updateDescription();
            }else addToTop(new ApplyPowerAction(p,p,new DarkReinforcementPower(p,mastery),1));
            isDone=true;
        }}); }
    @Override public void upgrade() { if(!upgraded) { upgradeName(); upgradeText(); } }
    
}
