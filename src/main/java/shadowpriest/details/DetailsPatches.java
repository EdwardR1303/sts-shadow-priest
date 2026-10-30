package shadowpriest.details;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.GameActionManager;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import javassist.CannotCompileException;
import javassist.expr.*;

public final class DetailsPatches {
    @SpirePatch(clz=com.megacrit.cardcrawl.dungeons.AbstractDungeon.class,method="render")
    public static class ModalLayer {
        @SpirePostfixPatch public static void after(com.megacrit.cardcrawl.dungeons.AbstractDungeon __instance,
                                                  com.badlogic.gdx.graphics.g2d.SpriteBatch sb) {
            if(com.megacrit.cardcrawl.dungeons.AbstractDungeon.screen==DetailsScreen.DETAILS)
                DetailsScreen.INSTANCE.renderAboveGameUI(sb);
        }
    }
    @SpirePatch(clz=com.megacrit.cardcrawl.core.CardCrawlGame.class,method="dispose")
    public static class Shutdown {
        @SpirePrefixPatch public static void before(){DetailsTracker.save();DetailsStore.flush();}
    }
    @SpirePatch(clz=com.megacrit.cardcrawl.ui.panels.TopPanel.class,method="updateSettingsButtonLogic")
    public static class Escape {
        @SpirePrefixPatch public static SpireReturn<Void> before(){
            if(com.megacrit.cardcrawl.dungeons.AbstractDungeon.screen==DetailsScreen.DETAILS
                && com.megacrit.cardcrawl.helpers.input.InputHelper.pressedEscape){
                DetailsScreen.INSTANCE.dismiss();return SpireReturn.Return(null);
            }
            return SpireReturn.Continue();
        }
    }
    @SpirePatch(clz=AbstractPlayer.class,method="preBattlePrep")
    public static class Begin { @SpirePrefixPatch public static void before(){DetailsTracker.begin();} }
    @SpirePatch(clz=AbstractPlayer.class,method="applyStartOfTurnRelics")
    public static class Turn { @SpirePrefixPatch public static void before(){DetailsTracker.startTurn();} }
    @SpirePatch(clz=AbstractPlayer.class,method="useCard")
    public static class Card {
        @SpirePrefixPatch public static void before(AbstractPlayer __instance,AbstractCard c){DetailsTracker.pushCard(c);}
        @SpirePostfixPatch public static void after(){DetailsTracker.popCard();}
    }
    @SpirePatch(clz=GameActionManager.class,method="addToBottom")
    @SpirePatch(clz=GameActionManager.class,method="addToTop")
    public static class Queue { @SpirePrefixPatch public static void before(GameActionManager __instance,AbstractGameAction action){DetailsTracker.queue(action);} }
    @SpirePatch(clz=AbstractPower.class,method="addToBot")
    @SpirePatch(clz=AbstractPower.class,method="addToTop")
    public static class Power { @SpirePrefixPatch public static void before(AbstractPower __instance,AbstractGameAction action){DetailsTracker.tag(action,DetailsTracker.power(__instance));} }
    @SpirePatch(clz=AbstractRelic.class,method="addToBot")
    @SpirePatch(clz=AbstractRelic.class,method="addToTop")
    public static class Relic { @SpirePrefixPatch public static void before(AbstractRelic __instance,AbstractGameAction action){DetailsTracker.tag(action,DetailsTracker.relic(__instance));} }
    @SpirePatch(clz=AbstractCard.class,method="addToBot")
    @SpirePatch(clz=AbstractCard.class,method="addToTop")
    public static class CardAction { @SpirePrefixPatch public static void before(AbstractCard __instance,AbstractGameAction action){DetailsTracker.tag(action,DetailsTracker.card(__instance));} }
    @SpirePatch(clz=AbstractMonster.class,method="damage")
    @SpirePatch(clz=AbstractPlayer.class,method="damage")
    public static class Damage {
        @SpirePrefixPatch public static void before(AbstractCreature __instance,DamageInfo info){DetailsTracker.beforeDamage(__instance,info);}
        @SpirePostfixPatch public static void after(){DetailsTracker.afterDamage();}
        @SpireInstrumentPatch public static ExprEditor instrument(){return new ExprEditor(){
            @Override public void edit(MethodCall call)throws CannotCompileException{
                if(call.getMethodName().equals("decrementBlock"))
                    call.replace("{ int before=$0.currentBlock; $_=$proceed($$); shadowpriest.details.DetailsTracker.absorbed($0,before-$0.currentBlock); }");
            }
            @Override public void edit(FieldAccess field)throws CannotCompileException{
                if(field.isWriter() && field.getFieldName().equals("currentHealth"))
                    field.replace("{ shadowpriest.details.DetailsTracker.healthWrite($0, $0.currentHealth, $1); $proceed($$); }");
            }
        };}
    }
    @SpirePatch(clz=AbstractCreature.class,method=SpirePatch.CLASS)
    public static class BlockState { public static SpireField<Integer> before=new SpireField<>(()->0); }
    @SpirePatch(clz=AbstractCreature.class,method="addBlock")
    public static class Block {
        @SpirePrefixPatch public static void before(AbstractCreature __instance){BlockState.before.set(__instance,__instance.currentBlock);}
        @SpirePostfixPatch public static void after(AbstractCreature __instance){DetailsTracker.block(__instance,BlockState.before.get(__instance));}
    }
    @SpirePatch(clz=AbstractCreature.class,method="heal",paramtypez={int.class,boolean.class})
    public static class Healing {
        @SpirePrefixPatch public static void before(AbstractCreature __instance){HealingState.before.set(__instance,__instance.currentHealth);}
        @SpirePostfixPatch public static void after(AbstractCreature __instance){DetailsTracker.healed(__instance,HealingState.before.get(__instance));}
    }
    @SpirePatch(clz=AbstractCreature.class,method=SpirePatch.CLASS)
    public static class HealingState {public static SpireField<Integer> before=new SpireField<>(()->0);}
    private DetailsPatches(){}
}
