package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.MadnessPower;
import shadowpriest.powers.ShadowDotPower;

public class VoidBolt extends PriestCard {
    public static final String ID = "shadowpriest:VoidBolt";
    public VoidBolt() {
        super(ID, 1, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8; baseMagicNumber = magicNumber = 1;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(shadowpriest.vfx.ShadowFx.hit(shadowpriest.vfx.ShadowSpellEffect.Style.BOLT, p, m));
        addToBot(new DamageAction(m, new DamageInfo(p, damage, damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
        addToBot(addStacks(p, m, magicNumber));
    }
    static boolean alive(AbstractMonster m) {
        return m != null && !m.isDeadOrEscaped() && !m.isDying && !m.halfDead;
    }
    static AbstractGameAction addStacks(AbstractPlayer p, AbstractMonster m, int stacks) {
        return new AbstractGameAction() {
            @Override public void update() {
                if (alive(m)) {
                    // Duration maintenance follows Mind Flay: Insanity. Madness is a debuff application.
                    for (String id : new String[]{ShadowDotPower.TOUCH, ShadowDotPower.PAIN})
                        if (m.getPower(id) instanceof ShadowDotPower) m.getPower(id).stackPower(stacks);
                    if (MadnessPower.stacks(m) > 0)
                        addToTop(new ApplyPowerAction(m, p, new MadnessPower(m, stacks), stacks));
                }
                isDone = true;
            }
        };
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeDamage(2); upgradeMagicNumber(1); }
    }
}
