package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.ArtifactPower;
import com.megacrit.cardcrawl.powers.StrengthPower;

public class MassDispel extends PriestCard {
    public static final String ID = "shadowpriest:MassDispel";
    public MassDispel() { super(ID, 2, CardType.SKILL, CardRarity.RARE, CardTarget.ALL_ENEMY); }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override public void update() {
                for (AbstractMonster enemy : AbstractDungeon.getMonsters().monsters) {
                    if (enemy.isDeadOrEscaped()) continue;
                    addToBot(new RemoveAllBlockAction(enemy, p));
                    addToBot(new RemoveSpecificPowerAction(enemy, p, ArtifactPower.POWER_ID));
                    if (enemy.hasPower(StrengthPower.POWER_ID) && enemy.getPower(StrengthPower.POWER_ID).amount > 0)
                        addToBot(new RemoveSpecificPowerAction(enemy, p, StrengthPower.POWER_ID));
                }
                isDone = true;
            }
        });
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeBaseCost(1); } }
}
