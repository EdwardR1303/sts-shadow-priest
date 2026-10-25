package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;

public class MindSpike extends PriestCard {
    public static final String ID = "shadowpriest:MindSpike";
    public MindSpike() {
        super(ID, 1, CardType.ATTACK, CardRarity.BASIC, CardTarget.ENEMY);
        baseDamage = 6;
        tags.add(CardTags.STRIKE); tags.add(CardTags.STARTER_STRIKE);
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        addToBot(new com.megacrit.cardcrawl.actions.AbstractGameAction() {
            public void update() { shadowpriest.relics.VoidformRelic r = shadowpriest.relics.VoidformRelic.current();
                if (r != null) r.gainInsanity(10); isDone = true; }
        });
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeDamage(3); } }
}
