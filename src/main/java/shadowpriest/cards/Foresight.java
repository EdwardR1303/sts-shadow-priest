package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class Foresight extends PriestCard {
    public static final String ID = "shadowpriest:Foresight";
    public Foresight() {
        super(ID, 0, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.NONE);

    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new shadowpriest.actions.ForesightAction(upgraded));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeText(); }
    }
}
