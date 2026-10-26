package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class Atonement extends PriestCard {
    public static final String ID="shadowpriest:Atonement";
    public Atonement() { super(ID,1,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY);baseDamage=5;baseMagicNumber=magicNumber=2; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) {
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));
        final int benefit=magicNumber;
        addToBot(new AbstractGameAction(){@Override public void update(){
            if(VoidBolt.alive(m) && (!m.hasPower(AtonementPower.POWER_ID) || m.getPower(AtonementPower.POWER_ID).amount<benefit))
                addToTop(new ApplyPowerAction(m,p,new AtonementPower(m,benefit),benefit));
            isDone=true;
        }});
    }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeDamage(2);upgradeMagicNumber(1); } }

}
