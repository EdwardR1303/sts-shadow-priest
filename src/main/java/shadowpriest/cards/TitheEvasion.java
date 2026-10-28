package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.powers.*;

public class TitheEvasion extends PriestCard {
 public static final String ID="shadowpriest:TitheEvasion";
 public TitheEvasion() { super(ID,1,CardType.POWER,CardRarity.COMMON,CardTarget.SELF); baseMagicNumber=magicNumber=1; }
 @Override public void use(AbstractPlayer p, AbstractMonster m) { final int value=magicNumber;
 addToBot(new AbstractGameAction(){ public void update(){
 addToTop(new ApplyPowerAction(p,p,new TitheEvasionPower(p,value),value));
 isDone=true;
 }}); }
 @Override public void upgrade() { if (!upgraded) { upgradeName(); upgradeMagicNumber(1); } }
 @com.evacipated.cardcrawl.modthespire.lib.SpirePatch(clz=AbstractPlayer.class,method=com.evacipated.cardcrawl.modthespire.lib.SpirePatch.CLASS)
 public static class Benefit { public static com.evacipated.cardcrawl.modthespire.lib.SpireField<Integer> granted=new com.evacipated.cardcrawl.modthespire.lib.SpireField<>(()->0); }
 @com.evacipated.cardcrawl.modthespire.lib.SpirePatch(clz=AbstractPlayer.class,method="preBattlePrep")
 public static class Reset { @com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch public static void after(AbstractPlayer __instance){Benefit.granted.set(__instance,0);} }

}
