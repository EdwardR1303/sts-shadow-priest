package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class ShadowCrash extends PriestCard {
 public static final String ID="shadowpriest:ShadowCrash";
 public ShadowCrash(){super(ID,0,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.ALL_ENEMY);baseMagicNumber=magicNumber=5;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){if(upgraded){for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(enemy))addToBot(ShadowDotPower.apply(enemy,p,ShadowDotPower.TOUCH,magicNumber));}
 else addToBot(new ApplyPowerAction(p,p,new ShadowCrashPower(p,magicNumber),magicNumber));}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeText();}}
}
