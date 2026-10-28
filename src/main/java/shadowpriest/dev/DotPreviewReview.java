package shadowpriest.dev;

import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import shadowpriest.mechanics.DotTiming;
import shadowpriest.powers.*;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused bar rendering and forecast-versus-resolution checks; no full-run simulation. */
public final class DotPreviewReview {
    private static AbstractMonster m;
    private static int expected, before;
    private static void reset(int hp) {
        AbstractDungeon.player.powers.clear();m.powers.clear();m.maxHealth=m.currentHealth=hp;m.currentBlock=0;
        m.healthBarUpdatedEvent();
    }
    private static void dots() {
        m.powers.add(new ShadowDotPower(m,ShadowDotPower.TOUCH,3));
        m.powers.add(new ShadowDotPower(m,ShadowDotPower.PAIN,2));
        m.powers.add(new MadnessPower(m,7));
    }
    private static int preview() {
        int sum=0;
        for(AbstractPower power:m.powers)if(power instanceof HealthBarRenderPower)sum+=((HealthBarRenderPower)power).getHealthBarAmount();
        return sum;
    }
    private static void resolve() {expected=preview();before=m.currentHealth;DotTiming.tick();}
    private static void capture(Queue<Runnable> q,String name,Runnable setup) {
        q.add(()->{setup.run();CardArtReview.requestCombat(name);});
        for(int i=0;i<5;i++)q.add(()->{});
        q.add(()->check(CardArtReview.capturedCombat(name),name+" rendered"));
    }
    public static void append(Queue<Runnable> q) {
        m=AbstractDungeon.getMonsters().monsters.get(0);
        capture(q,"dot-preview-partial",()->{
            reset(40);dots();m.currentBlock=30;
            check(preview()==12,"Touch, Pain and rounded-up Madness combine; Block is bypassed");
            int turns=m.getPower(ShadowDotPower.TOUCH).amount;
            for(int i=0;i<50;i++)preview();
            check(m.currentHealth==40&&m.currentBlock==30&&m.getPower(ShadowDotPower.TOUCH).amount==turns,"Rendering leaves HP, Block and stacks unchanged");
        });
        q.add(()->{AbstractDungeon.player.powers.add(new MasteryPower(AbstractDungeon.player,2));check(preview()==18,"Mastery updates each DoT preview live");resolve();});
        q.add(()->{check(before-m.currentHealth==expected&&m.currentBlock==30,"Forecast equals actual natural ticks through Block");reset(40);dots();AbstractDungeon.player.powers.add(new MasteryPower(AbstractDungeon.player,-99));check(preview()==0,"Negative Mastery floors preview at zero");});
        capture(q,"dot-preview-lethal",()->{reset(10);dots();check(preview()==10,"Lethal forecast fills exactly remaining HP");});
        capture(q,"dot-preview-poison",()->{reset(40);dots();m.powers.add(new PoisonPower(m,AbstractDungeon.player,8));check(preview()==12,"Purple forecast excludes the separate green Poison preview");});
        capture(q,"dot-preview-intangible",()->{reset(40);dots();m.powers.add(new IntangiblePower(m,1));check(preview()==3,"Intangible limits each of three ticks separately");});
        q.add(DotPreviewReview::resolve);
        q.add(()->{check(before-m.currentHealth==expected,"Intangible forecast matches resolved damage");reset(40);dots();m.powers.add(new InvinciblePower(m,6));check(preview()==6,"Heart cap shared across all DoTs");resolve();});
        q.add(()->{check(before-m.currentHealth==expected&&m.getPower("Invincible").amount==0,"Heart cap forecast matches damage without mutating cap during preview");check(preview()==0,"Exhausted Heart cap shows no upcoming HP loss");reset(40);dots();m.powers.add(new BufferPower(m,1));check(preview()==7,"Buffer prevents first positive tick only");resolve();});
        q.add(()->{check(before-m.currentHealth==expected,"Buffer forecast matches actual natural ticks");reset(40);dots();m.getPower(ShadowDotPower.TOUCH).amount=0;check(preview()==7,"Expired DoTs are excluded");m.powers.clear();m.powers.add(new MadnessPower(m,1));check(preview()==1,"One Madness stack previews its final tick");m.powers.clear();check(preview()==0,"No DoTs means no purple preview");});
    }
}
