package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class PowerWordShield extends PriestCard {
    public static final String ID = "shadowpriest:PowerWordShield";
    public PowerWordShield() {
        super(ID, 1, CardType.SKILL, CardRarity.BASIC, CardTarget.SELF);
        baseBlock = 5; tags.add(CardTags.STARTER_DEFEND);
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new GainBlockAction(p, p, block)); }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeBlock(3); } }
}
