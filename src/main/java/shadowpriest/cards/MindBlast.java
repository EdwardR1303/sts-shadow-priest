package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class MindBlast extends PriestCard {
    public static final String ID = "shadowpriest:MindBlast";
    public MindBlast() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10; baseMagicNumber = magicNumber = 2;
    }
    @Override public void calculateCardDamage(AbstractMonster m) {
        int original = baseDamage;
        baseDamage += baseMagicNumber * ShadowDotPower.count(m);
        try { super.calculateCardDamage(m); }
        finally { baseDamage = original; }
        isDamageModified = damage != baseDamage;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeDamage(2); upgradeMagicNumber(2); }
    }
}
