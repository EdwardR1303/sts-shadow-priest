package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
import shadowpriest.actions.GainInsanityAction;

public class VoidTendrils extends PriestCard {
    public static final String ID="shadowpriest:VoidTendrils";
    public VoidTendrils() { super(ID,1,CardType.SKILL,CardRarity.COMMON,CardTarget.ALL_ENEMY);baseBlock=4;baseMagicNumber=magicNumber=2; }
    @Override public void use(AbstractPlayer p,AbstractMonster m) {
        for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters) if(VoidBolt.alive(enemy)) {
            addToBot(new GainBlockAction(p,p,block));
            if(attacking(enemy)) addToBot(ShadowDotPower.apply(enemy,p,ShadowDotPower.PAIN,magicNumber));
        }
 }
    @Override public void upgrade() { if(!upgraded) { upgradeName();upgradeBlock(1);upgradeMagicNumber(1); } }
    public static boolean attacking(AbstractMonster m) {
        return m.intent==AbstractMonster.Intent.ATTACK || m.intent==AbstractMonster.Intent.ATTACK_BUFF
            || m.intent==AbstractMonster.Intent.ATTACK_DEBUFF || m.intent==AbstractMonster.Intent.ATTACK_DEFEND;
    }
}
