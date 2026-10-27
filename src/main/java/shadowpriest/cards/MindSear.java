package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class MindSear extends PriestCard {
    public static final String ID = "shadowpriest:MindSear";
    public MindSear() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 6; isMultiDamage = true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        int repeats = spendX(p) + (upgraded ? 1 : 0);
        for (int i = 0; i < repeats; i++) {
            addToBot(shadowpriest.vfx.ShadowFx.sear(p));
            addToBot(new DamageAllEnemiesAction(p, multiDamage, damageTypeForTurn, AbstractGameAction.AttackEffect.NONE, true));
        }
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeText(); }
    }
}
