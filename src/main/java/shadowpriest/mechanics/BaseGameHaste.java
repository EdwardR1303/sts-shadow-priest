package shadowpriest.mechanics;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction.AttackEffect;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.actions.defect.EvokeWithoutRemovingOrbAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import javassist.CannotCompileException;
import javassist.expr.*;

/** Base-game cards with explicit repetition counts; no global action duplication. */
public final class BaseGameHaste {
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.red.SwordBoomerang",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.red.Pummel",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.green.BouncingFlask",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.blue.RipAndTear",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.purple.Ragnarok",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.purple.Tantrum",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.purple.Omniscience",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.tempCards.Expunger",method="use")
    public static class Magic {
        @SpireInstrumentPatch public static ExprEditor instrument(){return new ExprEditor(){
            @Override public void edit(FieldAccess f)throws CannotCompileException {
                if(f.isReader() && f.getFieldName().equals("magicNumber"))
                    f.replace("{ $_ = shadowpriest.mechanics.Repetitions.add($proceed()); }");
            }
        };}
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.green.DaggerSpray",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.red.TwinStrike",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.green.Eviscerate",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.green.GlassKnife",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.green.RiddleWithHoles",method="use")
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.purple.FlyingSleeves",method="use")
    public static class Fixed {
        // Queue extra hits before the original sequence, preserving one-time effects such as Glass Knife's loss.
        @SpirePrefixPatch public static void before(AbstractCard __instance,AbstractPlayer p,AbstractMonster m){
            for(int i=0;i<Repetitions.haste();i++) {
                if(__instance.cardID.equals("Dagger Spray"))
                    AbstractDungeon.actionManager.addToBottom(new DamageAllEnemiesAction(p,__instance.multiDamage,__instance.damageTypeForTurn,AttackEffect.SLASH_HORIZONTAL));
                else AbstractDungeon.actionManager.addToBottom(new DamageAction(m,new DamageInfo(p,__instance.damage,__instance.damageTypeForTurn),AttackEffect.SLASH_HORIZONTAL));
            }
        }
    }
    @SpirePatch(cls="com.megacrit.cardcrawl.cards.blue.Dualcast",method="use")
    public static class Dual {
        @SpirePrefixPatch public static void before(){
            for(int i=0;i<Repetitions.haste();i++)AbstractDungeon.actionManager.addToBottom(new EvokeWithoutRemovingOrbAction(1));
        }
    }
}
