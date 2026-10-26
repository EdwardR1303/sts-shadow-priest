package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class ShadowWordTorment extends PriestCard {
    public static final String ID="shadowpriest:ShadowWordTorment";
    public ShadowWordTorment() { super(ID,1,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);baseDamage=6;baseMagicNumber=magicNumber=3; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) { addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));addToBot(ShadowDotPower.apply(m,p,ShadowDotPower.PAIN,magicNumber)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeDamage(3);upgradeMagicNumber(2); } }
    @Override public float getTitleFontSize(){return 16f;}
}
