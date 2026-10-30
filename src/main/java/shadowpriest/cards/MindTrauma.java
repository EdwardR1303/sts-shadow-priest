package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class MindTrauma extends PriestCard {
    public static final String ID="shadowpriest:MindTrauma";
    public MindTrauma() { super(ID,1,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);baseDamage=6;baseMagicNumber=magicNumber=6; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) {
        final int hit=damage,baseStacks=magicNumber;
        addToBot(new shadowpriest.actions.DevourAction(devoured->{
            int stacks=baseStacks+(devoured?6:0);
            return java.util.Arrays.asList(new DamageAction(m,new DamageInfo(p,hit,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE),
                new ApplyPowerAction(m,p,new MadnessPower(m,stacks),stacks),new GainInsanityAction(10));
        }));
    }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeDamage(3);upgradeMagicNumber(3); } }

}
