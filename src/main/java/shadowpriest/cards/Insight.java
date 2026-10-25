package shadowpriest.cards;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import shadowpriest.powers.*;
public class Insight extends PriestCard {
 public static final String ID="shadowpriest:Insight";
 public Insight(){super(ID,0,CardType.SKILL,CardRarity.COMMON,CardTarget.ENEMY);exhaust=true;}
 @Override public boolean canUse(AbstractPlayer p,AbstractMonster m){if(p.hasPower(InsightLockPower.POWER_ID)){cantUseMessage="Shadowy Insight has already been played this turn.";return false;}return super.canUse(p,m);}
 @Override public void use(AbstractPlayer p,AbstractMonster m){if(p.hasPower(InsightLockPower.POWER_ID))return;
  // Lock synchronously, even when the target has no DoTs or drawing is prevented.
  p.powers.add(new InsightLockPower(p));java.util.Collections.sort(p.powers);
  addToBot(new DrawCardAction(p,ShadowDotPower.count(m)));
 }
 @Override public void upgrade(){if(!upgraded){upgradeName();exhaust=false;upgradeText();}}
}
