package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class ShadowMend extends PriestCard {
    public static final String ID = "shadowpriest:ShadowMend";
    public ShadowMend() {
        super(ID, 1, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 6;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new HealAction(p, p, magicNumber));
        // Run after useCard has charged the old cost. Only this combat instance changes.
        addToBot(new AbstractGameAction() { @Override public void update() { updateCost(1); isDone = true; } });
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeMagicNumber(3); }
    }
}
