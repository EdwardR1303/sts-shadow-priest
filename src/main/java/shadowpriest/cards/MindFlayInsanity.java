package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class MindFlayInsanity extends PriestCard {
    public static final String ID = "shadowpriest:MindFlayInsanity";
    public MindFlayInsanity() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ENEMY);
        baseDamage = 4;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        final int repeats = spendX(p) + (upgraded ? 1 : 0);
        addToBot(new AbstractGameAction() {
            @Override public void update() {
                if (m != null && !m.isDeadOrEscaped()) {
                    for (AbstractPower power : m.powers) if (power instanceof ShadowDotPower) {
                        power.amount += repeats; power.updateDescription();
                    }
                }
                isDone = true;
            }
        });
        for (int i = 0; i < repeats; i++)
            addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeText(); }
    }
}
