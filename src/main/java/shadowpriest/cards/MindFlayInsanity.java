package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;

public class MindFlayInsanity extends PriestCard {
    public static final String ID = "shadowpriest:MindFlayInsanity";
    public MindFlayInsanity() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 6;
    }
    @Override public float getTitleFontSize() { return 16f; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        final int repeats = spendX(p) + (upgraded ? 1 : 0);
        if (repeats > 0) addToBot(VoidBolt.addStacks(p, m, repeats));
        for (int i = 0; i < repeats; i++)
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeText(); }
    }
}
