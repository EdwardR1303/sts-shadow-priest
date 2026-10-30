package shadowpriest.vfx;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.vfx.ShadowSpellEffect.Style;

/** Visual-only actions resolve immediately, leaving existing hit timing and mechanics intact. */
public final class ShadowFx {
    private static long lastMinorSound;
    private ShadowFx() { }
    public static AbstractGameAction singularity(AbstractCreature target,int damage) {
        return new AbstractGameAction(){
            @Override public void update(){
                if(target!=null&&!target.isDeadOrEscaped()){
                    AbstractDungeon.effectsQueue.add(new ShadowSpellEffect(target.hb.cX,target.hb.cY,damage));
                    CardCrawlGame.sound.playV("ATTACK_HEAVY",.6f+.2f*ShadowSpellEffect.collapseTier(damage));
                }
                isDone=true;
            }
        };
    }
    public static AbstractGameAction hit(Style style, AbstractCreature source, AbstractCreature target) {
        return new AbstractGameAction() {
            @Override public void update() { show(style, source, target); isDone = true; }
        };
    }
    public static AbstractGameAction sear(AbstractCreature source) {
        return new AbstractGameAction() {
            @Override public void update() {
                for (AbstractMonster m : AbstractDungeon.getMonsters().monsters) show(Style.SEAR, source, m);
                isDone = true;
            }
        };
    }
    public static void show(Style style, AbstractCreature source, AbstractCreature target) {
        if (source == null || target == null || target.isDeadOrEscaped() || target.isDying || (target instanceof AbstractMonster && ((AbstractMonster)target).halfDead)) return;
        AbstractDungeon.effectsQueue.add(new ShadowSpellEffect(style,
                source.hb.cX + (source.flipHorizontal ? -1 : 1) * source.hb.width * .25f,
                source.hb.cY + source.hb.height * .1f, target.hb.cX, target.hb.cY));
        if (style == Style.RAY) CardCrawlGame.sound.play("ATTACK_MAGIC_BEAM_SHORT");
        else if (style == Style.COLLAPSE) CardCrawlGame.sound.play("ATTACK_HEAVY");
        else if (style == Style.ASCEND) CardCrawlGame.sound.play("ATTACK_MAGIC_SLOW_2");
        else {
            // One cue for an AoE pulse; rapid X repeats do not create an audio pile-up.
            long now = System.nanoTime();
            if (now - lastMinorSound > 140000000L) {
                lastMinorSound = now;
                CardCrawlGame.sound.play(style == Style.FLAY || style == Style.TORRENT
                        ? "ATTACK_MAGIC_FAST_2" : "ATTACK_MAGIC_FAST_1");
            }
        }
    }
}
