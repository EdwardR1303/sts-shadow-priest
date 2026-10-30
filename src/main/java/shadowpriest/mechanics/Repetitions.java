package shadowpriest.mechanics;

import basemod.abstracts.DynamicVariable;
import com.evacipated.cardcrawl.modthespire.lib.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.powers.HastePower;

/** Explicit within-card repetitions only. Never derives repeats from arbitrary action counts. */
public final class Repetitions {
    public static int haste() {
        return AbstractDungeon.player == null ? 0 : Math.max(0,
            AbstractDungeon.player.hasPower(HastePower.POWER_ID) ? AbstractDungeon.player.getPower(HastePower.POWER_ID).amount : 0);
    }
    public static int add(int count) { return count + haste(); }
    public static int base(AbstractCard c) {
        switch(c.cardID) {
            case "shadowpriest:MindFlay": case "shadowpriest:Penance":
            case "Sword Boomerang": case "Pummel": case "Bouncing Flask": case "Rip and Tear":
            case "Ragnarok": case "Tantrum": case "Omniscience": case "Expunger": return c.baseMagicNumber;
            case "Dagger Spray": case "Twin Strike": case "Glass Knife": case "FlyingSleeves": case "Dualcast": return 2;
            case "Eviscerate": return 3;
            case "Riddle With Holes": return 5;
            case "shadowpriest:ShadowWordDeath": return c.upgraded ? 2 : -1;
            default: return -1;
        }
    }
    public static int xPreview(AbstractCard c) {
        int chem=AbstractDungeon.player!=null && AbstractDungeon.player.hasRelic("Chemical X") ? 2 : 0;
        return Math.max(0,EnergyPanel.totalCount)+haste()+chem;
    }
    public static class CountVariable extends DynamicVariable {
        @Override public String key(){return "shadowpriest:Repeats";}
        @Override public int baseValue(AbstractCard c){return Math.max(0,base(c));}
        @Override public int value(AbstractCard c){return baseValue(c)+haste();}
        @Override public boolean isModified(AbstractCard c){return haste()>0;}
        @Override public boolean upgraded(AbstractCard c){return c.upgraded && c.upgradedMagicNumber;}
    }
    public static class XVariable extends DynamicVariable {
        @Override public String key(){return "shadowpriest:EffectiveX";}
        @Override public int baseValue(AbstractCard c){return Math.max(0,EnergyPanel.totalCount);}
        @Override public int value(AbstractCard c){return xPreview(c);}
        @Override public boolean isModified(AbstractCard c){return value(c)>baseValue(c);}
        @Override public boolean upgraded(AbstractCard c){return false;}
    }
    @SpirePatch(clz=AbstractCard.class,method="applyPowers")
    @SpirePatch(clz=AbstractCard.class,method="calculateCardDamage")
    @SpirePatch(clz=AbstractCard.class,method="resetAttributes")
    public static class Refresh {
        @SpirePostfixPatch public static void after(AbstractCard __instance){
            String old=__instance.rawDescription;
            Text.before(__instance);
            if(!old.equals(__instance.rawDescription))__instance.initializeDescription();
        }
    }
    /** Convert only known repetition text, never 'for each', durations or trigger counts. */
    @SpirePatch(clz=AbstractCard.class,method="initializeDescription")
    public static class Text {
        @SpirePrefixPatch public static void before(AbstractCard __instance) {
            AbstractCard c=__instance;
            if(c.rawDescription==null)return;
            boolean combat=AbstractDungeon.player!=null && AbstractDungeon.currMapNode!=null && AbstractDungeon.getCurrRoom().phase==com.megacrit.cardcrawl.rooms.AbstractRoom.RoomPhase.COMBAT;
            if(!combat)c.rawDescription=c.rawDescription.replace(" NL (X = !shadowpriest:EffectiveX!)", "");
            if(combat && c.cost==-1 && !c.rawDescription.contains("!shadowpriest:EffectiveX!"))
                c.rawDescription+=" NL (X = !shadowpriest:EffectiveX!)";
            if(base(c)>=0) {
                c.rawDescription=c.rawDescription.replace("twice", "!shadowpriest:Repeats! times")
                    .replace("!M! times", "!shadowpriest:Repeats! times")
                    .replace("3 times", "!shadowpriest:Repeats! times")
                    .replace("5 times", "!shadowpriest:Repeats! times");
                if(c.cardID.equals("Expunger"))c.rawDescription=c.rawDescription.replace("X times", "!shadowpriest:Repeats! times");
            }
        }
    }
}
