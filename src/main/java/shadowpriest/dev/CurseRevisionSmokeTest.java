package shadowpriest.dev;

import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.cards.curses.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.actions.ExhaustHandCurseAction;
import shadowpriest.cards.*;
import shadowpriest.mechanics.PlayerTurnWindow;
import shadowpriest.powers.*;
import shadowpriest.relics.*;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Short real-engine checks for the five accepted curse/setup revisions. */
public final class CurseRevisionSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m;
    private static AbstractCard chosen, other;
    private static int masterSize;
    private static boolean selected;

    private static void reset() {
        p.powers.clear(); p.loseBlock(); p.currentHealth=p.maxHealth=100;
        p.relics.removeIf(r->!r.relicId.equals(VoidformRelic.ID));
        VoidformRelic.current().reset(); PlayerTurnWindow.active=true;
        for(CardGroup g:new CardGroup[]{p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})g.clear();
        for(int i=0;i<20;i++)p.drawPile.addToBottom(new MindSpike());
        m.powers.clear();m.loseBlock();m.currentHealth=m.maxHealth=10000;
        EnergyPanel.totalCount=3; chosen=null;other=null;selected=false;
    }
    private static <T extends AbstractCard>T up(T c){c.upgrade();return c;}
    private static int stat(String id){return p.hasPower(id)?p.getPower(id).amount:0;}
    private static long count(CardGroup group,Class<?> type){return group.group.stream().filter(type::isInstance).count();}
    private static void play(AbstractCard c,AbstractMonster target,int energy){
        p.hand.addToTop(c);c.energyOnUse=energy;EnergyPanel.totalCount=energy;
        c.applyPowers();if(target!=null)c.calculateCardDamage(target);
        check(c.canUse(p,target),"Curse revision playable: "+c.name);p.useCard(c,target,energy);
    }
    public static void handleSelection(){
        if(p==null||chosen==null||AbstractDungeon.screen!=AbstractDungeon.CurrentScreen.GRID
                ||!(AbstractDungeon.actionManager.currentAction instanceof ExhaustHandCurseAction))return;
        check(p.hand.contains(chosen)&&p.hand.contains(other),"Purify visibly offers a choice between actual hand Curses");
        AbstractDungeon.gridSelectScreen.selectedCards.clear();
        AbstractDungeon.gridSelectScreen.selectedCards.add(chosen);
        AbstractDungeon.closeCurrentScreen();selected=true;
    }
    public static void append(Queue<Runnable> q){
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);masterSize=p.masterDeck.size();reset();play(new BloodMagic(),m,2);});
        q.add(()->{check(m.currentHealth==9985&&m.getPower(ShadowDotPower.TOUCH).amount==3&&m.getPower(ShadowDotPower.PAIN).amount==3,"Blood Magic deals 15 and applies three of each DoT");check(count(p.discardPile,Pain.class)==1&&p.currentHealth==100,"Blood Magic generates one discard Pain without immediate HP loss");reset();m.powers.add(new ArtifactPower(m,2));play(up(new BloodMagic()),m,2);});
        q.add(()->{check(m.currentHealth==9980&&!m.hasPower(ShadowDotPower.TOUCH)&&!m.hasPower(ShadowDotPower.PAIN)&&!m.hasPower("Artifact")&&count(p.discardPile,Pain.class)==1,"Blood Magic+ deals 20; both blocked applications still generate Pain");reset();play(up(new BloodMagic()),m,2);});
        q.add(()->{check(m.getPower(ShadowDotPower.TOUCH).amount==5&&m.getPower(ShadowDotPower.PAIN).amount==5,"Blood Magic+ applies five Touch and five Pain");reset();play(new MindTrauma(),m,1);});
        q.add(()->{check(m.currentHealth==9994&&MadnessPower.stacks(m)==6&&VoidformRelic.current().state.amount()==20,"Mind Trauma deals six, applies six Madness, and gains twenty total Insanity");reset();m.currentBlock=100;m.powers.add(new ArtifactPower(m,1));play(up(new MindTrauma()),m,1);});
        q.add(()->{check(m.currentBlock==91&&MadnessPower.stacks(m)==0&&VoidformRelic.current().state.amount()==20,"Trauma+ deals nine through Block calculation and retains Insanity when Artifact prevents Madness");reset();play(up(new MindTrauma()),m,1);});
        q.add(()->{check(MadnessPower.stacks(m)==9,"Trauma+ applies nine Madness");reset();play(new PurifyDisease(),null,0);});
        q.add(()->{check(count(p.exhaustPile,PurifyDisease.class)==1,"Base Purify works and Exhausts without Curses or debuffs");reset();p.relics.add(new VoidVengeance());chosen=new Doubt();p.hand.addToTop(chosen);play(new PurifyDisease(),null,0);});
        q.add(()->{check(p.exhaustPile.contains(chosen)&&stat(HastePower.POWER_ID)==2,"Purify exhausts the only Curse and triggers two Haste from Void Vengeance");reset();p.relics.add(new VoidVengeance());chosen=new Doubt();other=new Pain();p.hand.addToTop(chosen);p.hand.addToTop(other);p.powers.add(new WeakPower(p,2,false));play(up(new PurifyDisease()),null,0);});
        q.add(()->{check(selected&&p.exhaustPile.contains(chosen)&&p.hand.contains(other),"Purify+ exhausts only the selected Curse");check(!p.hasPower("Weakened")&&stat("Artifact")==1&&stat(HastePower.POWER_ID)==2,"Purify+ cleanses, grants Artifact, and preserves Curse exhaust rewards");reset();p.powers.add(new ArtifactPower(p,1));p.powers.add(new DarkReinforcementPower(p,true));play(new MentalFortitude(),null,2);});
        q.add(()->{check(stat("Dexterity")==2&&stat(MasteryPower.POWER_ID)==2&&!p.hasPower("Strength")&&stat("Artifact")==1,"Fortitude grants stats without Strength loss or consuming Artifact");check(count(p.hand,Doubt.class)==1&&p.hand.size()==1&&EnergyPanel.totalCount==0,"Created Doubt enters hand without Dark Reinforcement draw/energy/Mastery triggers");play(up(new MentalFortitude()),null,1);});
        q.add(()->{check(stat("Dexterity")==4&&stat(MasteryPower.POWER_ID)==4&&count(p.hand,Doubt.class)==2,"Fortitude+ remains repeatable at one energy and adds one Doubt per play");reset();for(int i=0;i<10;i++)p.hand.addToTop(new MindSpike());new MentalFortitude().use(p,null);});
        q.add(()->{check(p.hand.size()==10&&count(p.discardPile,Doubt.class)==1,"Fortitude's generated Doubt goes to discard when hand is full");reset();m.powers.add(new MadnessPower(m,7));play(new MaladyOfTheMind(),m,0);});
        q.add(()->{check(MadnessPower.stacks(m)==14&&count(p.discardPile,MindDecay.class)==1&&count(p.exhaustPile,MaladyOfTheMind.class)==1,"Zero-cost Malady doubles Madness, creates discard Mind Decay, and Exhausts");reset();m.powers.add(new MadnessPower(m,7));play(up(new MaladyOfTheMind()),m,0);});
        q.add(()->{check(MadnessPower.stacks(m)==21&&count(p.discardPile,MindDecay.class)==1,"Malady+ triples Madness and generates its Curse");reset();m.powers.add(new MadnessPower(m,7));m.powers.add(new ArtifactPower(m,1));play(up(new MaladyOfTheMind()),m,0);});
        q.add(()->{check(MadnessPower.stacks(m)==7&&!m.hasPower("Artifact")&&count(p.discardPile,MindDecay.class)==1,"Artifact blocks Malady multiplication but not Curse creation");reset();play(new MaladyOfTheMind(),m,0);});
        q.add(()->{check(MadnessPower.stacks(m)==0&&count(p.discardPile,MindDecay.class)==1,"Malady with no Madness still creates its Curse");check(p.discardPile.group.stream().filter(c->c instanceof MindDecay).allMatch(c->c.selfRetain),"Generated Mind Decay preserves Retain");check(p.masterDeck.size()==masterSize&&p.masterDeck.group.stream().noneMatch(c->c.type==AbstractCard.CardType.CURSE),"All generated Curses remain combat-only");
            for(AbstractCard c:new AbstractCard[]{new BloodMagic(),up(new BloodMagic()),new MindTrauma(),up(new MindTrauma()),new PurifyDisease(),up(new PurifyDisease()),new MentalFortitude(),up(new MentalFortitude()),new MaladyOfTheMind(),up(new MaladyOfTheMind())})
                CardArtReview.card(c,"curse-revision-"+c.cardID.split(":")[1]+(c.upgraded?"-plus":""));
        });
    }
}
