package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;

public class MentalDecay extends PriestCard {
    public static final String ID="shadowpriest:MentalDecay";
    public MentalDecay() { super(ID,1,CardType.SKILL,CardRarity.RARE,CardTarget.ALL_ENEMY); baseMagicNumber=magicNumber=10; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters)
            if(VoidBolt.alive(enemy))addToBot(new ApplyPowerAction(enemy,p,new MadnessPower(enemy,magicNumber),magicNumber));
        AbstractCard curse=new RetainedDecay();
        addToBot(new MakeTempCardInHandAction(curse,1,false)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName(); upgradeMagicNumber(5); } }
    
}
