package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.*;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
public class BodyAndSoul extends PriestCard {
    public static final String ID = "shadowpriest:BodyAndSoul";
    @SpirePatch(clz=AbstractPlayer.class, method=SpirePatch.CLASS)
    public static class Benefit { public static SpireField<Integer> granted = new SpireField<>(() -> 0); }
    @SpirePatch(clz=AbstractPlayer.class, method="preBattlePrep")
    public static class Reset { @SpirePostfixPatch public static void reset(AbstractPlayer __instance) { Benefit.granted.set(__instance, 0); } }
    public static void resetBenefit() { if (AbstractDungeon.player != null) Benefit.granted.set(AbstractDungeon.player, 0); }
    public BodyAndSoul() { super(ID, 2, CardType.POWER, CardRarity.UNCOMMON, CardTarget.SELF); baseMagicNumber = magicNumber = 20; }
    @Override public boolean canUse(AbstractPlayer p, AbstractMonster m) {
        if (!super.canUse(p, m)) return false;
        if (Benefit.granted.get(p) >= magicNumber) { cantUseMessage = "Body and Soul has already granted this dodge chance this combat."; return false; }
        return true;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        final int chance = magicNumber;
        addToBot(new AbstractGameAction() {
            @Override public void update() {
                if (chance > Benefit.granted.get(p)) {
                    Benefit.granted.set(p, chance);
                    addToTop(new ApplyPowerAction(p, p, new BodyAndSoulPower(p, chance), chance));
                }
                isDone = true;
            }
        });
    }
    @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeMagicNumber(10); } }
}
