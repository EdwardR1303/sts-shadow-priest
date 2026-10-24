package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;

public class DispelMagic extends PriestCard {
    public static final String ID = "shadowpriest:DispelMagic";
    public DispelMagic() { super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.ENEMY); }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new RemoveAllBlockAction(m, p));
        if (upgraded) addToBot(new RemoveSpecificPowerAction(m, p, "Artifact"));
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeText(); } }
}
