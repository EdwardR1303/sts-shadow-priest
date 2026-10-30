package shadowpriest.cards;

import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import shadowpriest.actions.DevourAction;

/** Gold means a specific conditional benefit is available, not that playing is recommended. */
public final class ConditionalCards {
    private static boolean predicting;
    public static boolean devours(AbstractCard c) {
        return c instanceof TentacleSlam || c instanceof UnfurlingDarkness || c instanceof MindTrauma
            || c instanceof ShadowMend || c instanceof PurifyDisease || c instanceof FocusedWill
            || c instanceof TwinSuns || c instanceof VoidRay;
    }
    private static boolean supported(AbstractCard c) {
        return devours(c)||c instanceof SurrenderToMadness||c instanceof Bulwark||c instanceof Piety
            ||c instanceof DispelMagic||c instanceof MassDispel||c instanceof ShadowWordDeath;
    }
    public static AbstractMonster hovered() {
        if(!FormulaPreview.inCombat())return null;
        for(AbstractMonster m:AbstractDungeon.getMonsters().monsters)if(VoidBolt.alive(m)&&m.hb.hovered)return m;
        return null;
    }
    private static boolean noType(AbstractCard.CardType type) {
        return AbstractDungeon.player.hand.group.stream().noneMatch(c->c.type==type&&!(c instanceof Piety));
    }
    private static boolean cleanse() {
        return AbstractDungeon.player.hasPower("Weakened")||AbstractDungeon.player.hasPower("Frail")||AbstractDungeon.player.hasPower("Vulnerable");
    }
    private static boolean enemyBuff(AbstractMonster m) {
        return m.currentBlock>0||m.hasPower("Artifact")||positiveStrength(m);
    }
    private static boolean positiveStrength(AbstractMonster m) {
        return m!=null&&m.hasPower("Strength")&&m.getPower("Strength").amount>0;
    }
    private static boolean targetBonus(AbstractCard c,AbstractMonster m) {
        if(!VoidBolt.alive(m))return false;
        if(c instanceof ShadowWordDeath)return lethal((ShadowWordDeath)c,m);
        if(c instanceof DispelMagic)return m.currentBlock>0||(c.upgraded&&m.hasPower("Artifact"));
        return c instanceof TwinSuns&&positiveStrength(m);
    }
    public static boolean ready(AbstractCard c,AbstractMonster target) {
        if(!FormulaPreview.inCombat())return false;
        if(devours(c)&&DevourAction.available())return true;
        if(c instanceof SurrenderToMadness)return SurrenderToMadness.unlocked();
        if(c instanceof Bulwark)return Bulwark.empowered(AbstractDungeon.player);
        if(c instanceof Piety)return noType(AbstractCard.CardType.ATTACK)||noType(AbstractCard.CardType.SKILL);
        if(c instanceof PurifyDisease)return cleanse();
        if(c instanceof MassDispel)return AbstractDungeon.getMonsters().monsters.stream().anyMatch(m->VoidBolt.alive(m)&&enemyBuff(m))
            ||(c.upgraded&&AbstractDungeon.player.powers.stream().anyMatch(p->p.type==AbstractPower.PowerType.DEBUFF));
        if(target!=null)return targetBonus(c,target);
        return AbstractDungeon.getMonsters().monsters.stream().anyMatch(m->targetBonus(c,m));
    }
    public static void update(AbstractCard c,AbstractMonster target) {
        if(predicting||!supported(c)||c.rawDescription==null)return;
        String text=c.rawDescription.replace("*","");
        if(c instanceof SurrenderToMadness)text=text.replaceAll("\\([0-9]+/2\\)","("+(FormulaPreview.inCombat()?Math.min(2,SurrenderToMadness.completedForms()):0)+"/2)");
        if(FormulaPreview.inCombat()) {
            if(devours(c)&&DevourAction.available())text=clause(text,"shadowpriest:Devour:");
            if(c instanceof SurrenderToMadness&&SurrenderToMadness.unlocked())text=clause(text,"Playable only");
            if(c instanceof Bulwark&&Bulwark.empowered(AbstractDungeon.player))text=clause(text,"Below 35%");
            if(c instanceof Piety){
                if(noType(AbstractCard.CardType.ATTACK))text=clause(text,"If your hand has no Attacks");
                if(noType(AbstractCard.CardType.SKILL))text=clause(text,"If it has no Skills");
            }
            if(c instanceof PurifyDisease&&cleanse())text=clause(text,"Remove Weak");
            if(c instanceof MassDispel){
                if(AbstractDungeon.getMonsters().monsters.stream().anyMatch(m->VoidBolt.alive(m)&&enemyBuff(m)))text=clause(text,"Remove ALL enemies");
                if(c.upgraded&&AbstractDungeon.player.powers.stream().anyMatch(p->p.type==AbstractPower.PowerType.DEBUFF))text=clause(text,"Remove your debuffs");
            }
            // Targeted text makes no promise until a specific enemy is hovered.
            if(target!=null){
                if(c instanceof ShadowWordDeath&&lethal((ShadowWordDeath)c,target))text=clause(text,"If this kills");
                if(c instanceof TwinSuns&&positiveStrength(target))text=clause(text,"If the enemy");
                if(c instanceof DispelMagic){
                    if(target.currentBlock>0)text=clause(text,"If Block was removed");
                    if(c.upgraded&&target.hasPower("Artifact"))text=clause(text,"If Artifact was removed");
                }
            }
        }
        if(!text.equals(c.rawDescription)){c.rawDescription=text;c.initializeDescription();}
    }
    private static String clause(String text,String start) {
        int begin=text.indexOf(start);if(begin<0)return text;
        int end=text.indexOf('.',begin);if(end<0)end=text.length()-1;
        String[] words=text.substring(begin,end+1).split(" ");
        for(int i=0;i<words.length;i++)if(!words[i].startsWith("!")&&!words[i].contains(":"))words[i]="*"+words[i];
        return text.substring(0,begin)+String.join(" ",words)+text.substring(end+1);
    }
    public static boolean lethal(ShadowWordDeath c,AbstractMonster m) {
        if(!VoidBolt.alive(m)||m.halfDead||c.purgeOnUse||c.exhaust||c.exhaustOnUseOnce||m.hasPower("Malleable"))return false;
        int oldDamage=c.damage;boolean oldModified=c.isDamageModified;
        int hit;
        predicting=true;
        try {c.calculateCardDamage(m);hit=c.damage;}
        finally {c.damage=oldDamage;c.isDamageModified=oldModified;predicting=false;}
        int block=m.currentBlock,hp=m.currentHealth;
        int buffer=m.hasPower("Buffer")?m.getPower("Buffer").amount:0;
        int cap=m.hasPower("Invincible")?Math.max(0,m.getPower("Invincible").amount):Integer.MAX_VALUE;
        int repeats=c.upgraded?shadowpriest.mechanics.Repetitions.add(2):1;
        for(int i=0;i<repeats;i++){
            int loss=Math.max(0,hit-block);block=Math.max(0,block-hit);
            if(loss>0&&buffer>0){buffer--;continue;}
            loss=Math.min(loss,cap);cap-=loss;hp-=loss;
            if(hp<=0)return true;
        }
        return false;
    }
}
