package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;

public class InsidiousIre extends PriestCard {
    public static final String ID = "shadowpriest:InsidiousIre";
    public InsidiousIre() { super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF); baseMagicNumber = magicNumber = 1; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new ApplyPowerAction(p, p, new InsidiousIrePower(p, magicNumber), magicNumber)); }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeBaseCost(1); } }
}
