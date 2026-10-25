package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class CrystallineReflection extends PriestCard {
 public static final String ID="shadowpriest:CrystallineReflection";
 public CrystallineReflection(){super(ID,-1,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);baseBlock=5;}
 @Override public float getTitleFontSize(){return 16f;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){int x=spendX(p);for(int i=0;i<x;i++)addToBot(new GainBlockAction(p,p,block));if(x>0)addToBot(new ApplyPowerAction(p,p,new ReflectionThornsPower(p,x),x));}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeBlock(2);}}
}
