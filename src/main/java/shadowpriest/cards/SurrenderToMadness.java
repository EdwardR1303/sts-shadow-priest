package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.SurrenderDeadlinePower;
import shadowpriest.powers.VoidformPower;
import shadowpriest.relics.VoidformRelic;

public class SurrenderToMadness extends PriestCard {
    @Override public float getTitleFontSize(){return 18f;}
    public static final String ID = "shadowpriest:SurrenderToMadness";
    public SurrenderToMadness() {
        super(ID, 0, CardType.SKILL, CardRarity.RARE, CardTarget.SELF); exhaust = true;
        baseMagicNumber=magicNumber=2;
    }
    public static int completedForms() {
        VoidformRelic r=VoidformRelic.current();return r==null?0:r.state.completedForms();
    }
    public static boolean unlocked() { return completedForms()>=2 && !VoidformRelic.isPermanent(); }
    @Override public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;
        if (VoidformRelic.isPermanent()) {
            cantUseMessage = "You have already surrendered this combat."; return false;
        }
        if(completedForms()<2) {
            cantUseMessage="Leave Voidform twice this combat first ("+completedForms()+"/2).";return false;
        }
        return true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        if(!unlocked())return;
        final int gain=magicNumber;
        addToBot(new AbstractGameAction() {
            @Override public void update() {
                // Resolve in the queue so duplicated plays cannot apply the bargain twice.
                if (unlocked()) {
                    VoidformRelic relic = VoidformRelic.current();
                    if (relic != null) relic.surrender();
                    else if (!VoidformRelic.isActive()) shadowpriest.relics.IdolOfYoggSaron.enteredVoidform();
                    addToTop(new ApplyPowerAction(p,p,new SurrenderDeadlinePower(p,gain),5));
                    addToTop(new ApplyPowerAction(p,p,new shadowpriest.powers.MasteryPower(p,gain),gain));
                    addToTop(new ApplyPowerAction(p,p,new com.megacrit.cardcrawl.powers.StrengthPower(p,gain),gain));
                    if (p.getPower(VoidformPower.POWER_ID) instanceof VoidformPower)
                        ((VoidformPower)p.getPower(VoidformPower.POWER_ID)).makePermanent();
                    else addToTop(new ApplyPowerAction(p, p, new VoidformPower(p, true), -1));
                    p.hand.applyPowers();
                }
                isDone = true;
            }
        });
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeMagicNumber(1); upgradeText(); } }
}
