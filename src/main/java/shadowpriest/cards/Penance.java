package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;

public class Penance extends PriestCard {
    public static final String ID = "shadowpriest:Penance";
    public Penance() { super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY); baseDamage = 6; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.FIRE) {
            private boolean converted;
            @Override public void update() {
                int before = m.currentHealth;
                super.update();
                if (!converted && m.currentHealth < before) {
                    converted = true;
                    // Exact HP conversion: no second Dexterity/Frail calculation, no overkill or Link.
                    p.addBlock(Math.max(0, before - Math.max(0, m.currentHealth)));
                }
            }
        });
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeDamage(2); } }
}
