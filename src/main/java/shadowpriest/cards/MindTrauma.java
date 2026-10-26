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
    public MindTrauma() { super(ID,1,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);baseDamage=4;baseMagicNumber=magicNumber=4; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) { addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));addToBot(new ApplyPowerAction(m,p,new MadnessPower(m,magicNumber),magicNumber));addToBot(new GainInsanityAction(10)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeDamage(3);upgradeMagicNumber(2); } }

}
