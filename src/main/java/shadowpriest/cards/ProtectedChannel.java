package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;
public class ProtectedChannel extends PriestCard {
 public static final String ID="shadowpriest:ProtectedChannel";
 public ProtectedChannel(){super(ID,2,CardType.POWER,CardRarity.UNCOMMON,CardTarget.SELF);baseMagicNumber=magicNumber=3;}
 @Override public void use(AbstractPlayer p,AbstractMonster m){addToBot(new ApplyPowerAction(p,p,new ProtectedChannelPower(p,magicNumber),magicNumber));}
 @Override public void upgrade(){if(!upgraded){upgradeName();upgradeMagicNumber(2);}}
}
