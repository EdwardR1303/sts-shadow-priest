package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;

public class MaladyOfTheMind extends PriestCard {
    public static final String ID="shadowpriest:MaladyOfTheMind";
    public MaladyOfTheMind() { super(ID,1,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.ENEMY); baseMagicNumber=magicNumber=2;exhaust=true; }
    @Override public float getTitleFontSize() { return 18f; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new AbstractGameAction(){public void update(){
            int stacks=MadnessPower.stacks(m)*(magicNumber-1);
            if(VoidBolt.alive(m)&&stacks>0)addToTop(new ApplyPowerAction(m,p,new MadnessPower(m,stacks),stacks));
            isDone=true;
        }}); }
    @Override public void upgrade() { if(!upgraded) { upgradeName(); upgradeMagicNumber(1);upgradeText(); } }

}
