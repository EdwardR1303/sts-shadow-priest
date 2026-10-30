package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;

public class PurifyDisease extends PriestCard {
    public static final String ID = "shadowpriest:PurifyDisease";
    public PurifyDisease() { super(ID, 0, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF); exhaust = true; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        for (String id : new String[]{"Weakened", "Frail", "Vulnerable"}) addToBot(new RemoveSpecificPowerAction(p, p, id));
        addToBot(new shadowpriest.actions.DevourAction(devoured -> devoured
            ? java.util.Collections.singletonList(new ApplyPowerAction(p,p,new com.megacrit.cardcrawl.powers.ArtifactPower(p,1),1))
            : java.util.Collections.emptyList()));
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); selfRetain=true; upgradeText(); } }
}
