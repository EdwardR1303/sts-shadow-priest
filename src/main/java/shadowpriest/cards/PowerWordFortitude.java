package shadowpriest.cards;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;

public class PowerWordFortitude extends PriestCard {
    public static final String ID = "shadowpriest:PowerWordFortitude";
    @SpirePatch(clz = AbstractPlayer.class, method = SpirePatch.CLASS)
    public static class Benefit {
        public static SpireField<Integer> granted = new SpireField<>(() -> 0);
    }
    public static void resetBenefit() {
        if (AbstractDungeon.player != null) Benefit.granted.set(AbstractDungeon.player, 0);
    }
    public PowerWordFortitude() {
        super(ID, 0, CardType.POWER, CardRarity.COMMON, CardTarget.SELF);
        baseMagicNumber = magicNumber = 2;
    }
    @Override public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;
        if (Benefit.granted.get(p) >= magicNumber) {
            cantUseMessage = "Fortitude has already granted this much Dexterity this combat.";
            return false;
        }
        return true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        final int benefit = magicNumber;
        addToBot(new AbstractGameAction() {
            @Override public void update() {
                int difference = benefit - Benefit.granted.get(p);
                if (difference > 0) {
                    Benefit.granted.set(p, benefit);
                    addToTop(new ApplyPowerAction(p, p, new DexterityPower(p, difference), difference));
                }
                isDone = true;
            }
        });
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeMagicNumber(1); }
    }
}
