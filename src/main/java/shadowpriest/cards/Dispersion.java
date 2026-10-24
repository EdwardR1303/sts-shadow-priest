package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class Dispersion extends PriestCard {
    public static final String ID = "shadowpriest:Dispersion";
    public Dispersion() {
        super(ID, 2, CardType.SKILL, CardRarity.UNCOMMON, CardTarget.SELF);
        exhaust = true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new ApplyPowerAction(p, p, new IntangiblePlayerPower(p, 1), 1));
        addToBot(new com.megacrit.cardcrawl.actions.watcher.PressEndTurnButtonAction());
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); selfRetain = true; upgradeText(); }
    }
}
