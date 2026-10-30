package shadowpriest.cards;

import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class Bulwark extends PriestCard {
    public static final String ID = "shadowpriest:Bulwark";
    public Bulwark() {
        super(ID, 2, CardType.SKILL, CardRarity.COMMON, CardTarget.SELF);
        baseBlock = 12;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        int grants=empowered(p)?2:1;
        for(int i=0;i<grants;i++){
            addToBot(new GainBlockAction(p, p, block)); addToBot(new ApplyPowerAction(p, p, new BlurPower(p, 1), 1));
        }
    }
    public static boolean empowered(AbstractPlayer p){return p.currentHealth*100L<p.maxHealth*35L;}
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeBlock(4); }
    }
}
