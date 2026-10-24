package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class BloodMagic extends PriestCard {
 public static final String ID="shadowpriest:BloodMagic";
 public BloodMagic() { super(ID,2,CardType.ATTACK,CardRarity.COMMON,CardTarget.ENEMY); baseDamage=18; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn),AbstractGameAction.AttackEffect.SLASH_HEAVY));
 addToBot(new shadowpriest.actions.PriestHpLossAction(3)); }
 @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeDamage(6); } }

}
