package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.MasteryPower;
public class MentalFortitude extends PriestCard {
 @Override public float getTitleFontSize() { return 20f; }
 public static final String ID="shadowpriest:MentalFortitude";
 public MentalFortitude(){super(ID,2,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF);baseMagicNumber=magicNumber=2;cardsToPreview=new com.megacrit.cardcrawl.cards.curses.Doubt();}
 public void use(AbstractPlayer p,AbstractMonster m){
 addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,magicNumber),magicNumber));
 addToBot(new ApplyPowerAction(p,p,new MasteryPower(p,magicNumber),magicNumber));
 addToBot(new com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction(new com.megacrit.cardcrawl.cards.curses.Doubt(),1,false));
 }
 public void upgrade(){if(!upgraded){upgradeName();upgradeBaseCost(1);}}
}
