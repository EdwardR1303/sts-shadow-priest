package shadowpriest.powers;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.powers.AbstractPower;
public class MindSoothePower extends AbstractPower {
 public static final String POWER_ID="shadowpriest:MindSoothe";
 public MindSoothePower(AbstractCreature owner,int reduction){this.owner=owner;amount=reduction;ID=POWER_ID;name="Mind Soothe";type=PowerType.DEBUFF;isTurnBased=true;AbilityPowerArt.apply(this,"mind_soothe.jpg");updateDescription();}
 @Override public float atDamageFinalGive(float damage,DamageInfo.DamageType type){return type==DamageInfo.DamageType.NORMAL?Math.max(0,damage-amount):damage;}
 @Override public void atEndOfRound(){addToBot(new RemoveSpecificPowerAction(owner,owner,POWER_ID));}
 @Override public void updateDescription(){description="Deal #b"+amount+" less damage per Attack hit this turn. Removed after the enemy turn.";}
}
