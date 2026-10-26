package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.actions.GainBarrierAction;

public class PowerWordBarrier extends PriestCard {
 public static final String ID="shadowpriest:PowerWordBarrier";
 public PowerWordBarrier() { super(ID,3,CardType.SKILL,CardRarity.UNCOMMON,CardTarget.SELF); baseBlock=30; }
 @Override public float getTitleFontSize() { return 16f; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new GainBarrierAction(p,block)); }
 @Override public void upgrade() { if(!upgraded){upgradeName();upgradeBlock(10);} }
}
