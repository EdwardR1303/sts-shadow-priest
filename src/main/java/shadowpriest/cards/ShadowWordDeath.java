package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class ShadowWordDeath extends PriestCard {
    public static final String ID = "shadowpriest:ShadowWordDeath";
    public ShadowWordDeath() {
        super(ID, 0, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 10; baseMagicNumber = magicNumber = 20;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        for (int i = 0; i < (upgraded ? 2 : 1); i++)
            addToBot(new shadowpriest.actions.DeathHitAction(p, m, new DamageInfo(p, damage, damageTypeForTurn), magicNumber));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeMagicNumber(10); upgradeText(); }
    }
}
