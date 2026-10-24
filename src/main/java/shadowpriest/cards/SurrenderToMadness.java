package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.SurrenderExposurePower;
import shadowpriest.powers.VoidformPower;
import shadowpriest.relics.VoidformRelic;

public class SurrenderToMadness extends PriestCard {
    public static final String ID = "shadowpriest:SurrenderToMadness";
    public SurrenderToMadness() {
        super(ID, 3, CardType.SKILL, CardRarity.RARE, CardTarget.SELF); exhaust = true;
    }
    @Override public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;
        if (VoidformRelic.isPermanent()) {
            cantUseMessage = "You have already surrendered this combat."; return false;
        }
        return true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        addToBot(new AbstractGameAction() {
            @Override public void update() {
                // Resolve in the queue so duplicated plays cannot apply the bargain twice.
                if (!VoidformRelic.isPermanent()) {
                    VoidformRelic relic = VoidformRelic.current();
                    if (relic != null) relic.surrender();
                    addToTop(new ApplyPowerAction(p, p, new SurrenderExposurePower(p), -1));
                    if (p.getPower(VoidformPower.POWER_ID) instanceof VoidformPower)
                        ((VoidformPower)p.getPower(VoidformPower.POWER_ID)).makePermanent();
                    else addToTop(new ApplyPowerAction(p, p, new VoidformPower(p, true), -1));
                    p.hand.applyPowers();
                }
                isDone = true;
            }
        });
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeBaseCost(2); } }
}
