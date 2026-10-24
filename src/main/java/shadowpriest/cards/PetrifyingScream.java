package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;

import com.evacipated.cardcrawl.mod.stslib.actions.common.StunMonsterAction;
public class PetrifyingScream extends PriestCard {
    public static final String ID = "shadowpriest:PetrifyingScream";
    public PetrifyingScream() { super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ENEMY); exhaust = true; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new StunMonsterAction(m, p)); }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeBaseCost(1); } }
}
