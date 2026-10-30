package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class BloodMagic extends PriestCard {
    public static final String ID="shadowpriest:BloodMagic";
    public BloodMagic() { super(ID,2,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY);baseDamage=15;baseMagicNumber=magicNumber=3;cardsToPreview=new com.megacrit.cardcrawl.cards.curses.Pain(); }
    @Override public void use(AbstractPlayer p,AbstractMonster m) { addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn),AbstractGameAction.AttackEffect.FIRE));addToBot(ShadowDotPower.apply(m,p,ShadowDotPower.TOUCH,magicNumber));addToBot(ShadowDotPower.apply(m,p,ShadowDotPower.PAIN,magicNumber));addToBot(new MakeTempCardInDiscardAction(new com.megacrit.cardcrawl.cards.curses.Pain(),1)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeDamage(5);upgradeMagicNumber(2); } }

}
