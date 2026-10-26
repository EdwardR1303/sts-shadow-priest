package shadowpriest.dev;

import com.megacrit.cardcrawl.actions.utility.WaitAction;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.powers.*;
import shadowpriest.legendary.LegendaryEquipment;
import shadowpriest.relics.VoidformRelic;
import java.util.Queue;
import static shadowpriest.dev.CombatSmokeTest.check;

/** Focused checks for support-stat lifecycle and independently visible Val'anyr buffs. */
public final class SupportBalanceSmokeTest {
    private static AbstractPlayer p;
    private static AbstractMonster m;
    private static GuardianAngelPower angel;
    private static void reset() {
        p.powers.clear(); p.currentBlock=0; p.currentHealth=p.maxHealth=500;
        p.hand.clear(); p.drawPile.clear(); p.discardPile.clear(); p.exhaustPile.clear(); p.limbo.clear();
        m.powers.clear(); m.currentBlock=0; m.currentHealth=m.maxHealth=1000;
        p.relics.removeIf(r->r.relicId.equals("Chemical X"));
        VoidformRelic.current().reset();
    }
    private static <T extends AbstractCard> T up(T c) { c.upgrade(); return c; }
    private static int haste() { return p.hasPower(HastePower.POWER_ID)?p.getPower(HastePower.POWER_ID).amount:0; }
    private static void play(AbstractCard c, AbstractMonster target, int energy) {
        EnergyPanel.totalCount=energy; c.energyOnUse=energy; p.hand.addToTop(c);
        check(c.canUse(p,target),c.name+" is playable in fixture");p.useCard(c,target,energy);
    }
    private static void nextTurn() {
        m.setMove((byte)0,AbstractMonster.Intent.NONE);m.createIntent();
        for(int i=0;i<10;i++)p.drawPile.addToTop(new PowerWordShield());
        AbstractDungeon.getCurrRoom().skipMonsterTurn=false;
        AbstractDungeon.overlayMenu.endTurnButton.disable(true);p.endTurnQueued=true;
    }
    public static void append(Queue<Runnable> q) {
        q.add(()->{p=AbstractDungeon.player;m=AbstractDungeon.getMonsters().monsters.get(0);reset();play(new PowerInfusion(),null,3);});
        q.add(()->{check(haste()==2&&EnergyPanel.totalCount==2&&p.exhaustPile.size()==1,"Power Infusion grants two Haste, costs one, and exhausts");nextTurn();});
        q.add(()->{check(haste()==1,"Two Haste leaves one next turn");play(new CrystallineReflection(),null,0);});
        q.add(()->{check(p.currentBlock==5&&haste()==1,"Carried Haste powers an X Skill at zero energy without being consumed");nextTurn();});
        q.add(()->{check(!p.hasPower(HastePower.POWER_ID),"Last Haste stack expires rather than becoming permanent");reset();p.powers.add(new HastePower(p,5));nextTurn();});
        q.add(()->{check(haste()==2,"Five Haste loses three, rounding the loss up");nextTurn();});
        q.add(()->{check(haste()==1,"Odd-stack decay continues to one");nextTurn();});
        q.add(()->{check(haste()==0,"Odd-stack decay reaches zero");reset();play(up(new PowerInfusion()),null,0);});
        q.add(()->{p.relics.add(new com.megacrit.cardcrawl.relics.ChemicalX());p.powers.add(new DexterityPower(p,2));p.powers.add(new ProtectedChannelPower(p,3));play(new MindSear(),null,0);});
        q.add(()->{check(m.currentHealth==980&&p.currentBlock==20,"Haste plus Chemical X drive four hits and four Dexterity-modified Protected Channel blocks");reset();new Valanyr().equip(p);});
        q.add(()->{angel=(GuardianAngelPower)p.getPower(GuardianAngelPower.POWER_ID);
            check(p.currentBlock==5&&p.hasPower("Barricade")&&angel.available(),"Val'anyr grants Block retention and independent Guardian Angel");
            check(p.getPower(ValanyrPower.POWER_ID).name.equals("Barricade (Val'anyr)")&&angel.name.equals("Guardian Angel"),"Both buffs have separate visible names");
            LegendaryEquipment.State.equipped.set(p,Valanyr.ID);CardArtReview.card(new PowerInfusion(),"power-infusion-decay");
            CardArtReview.requestCombat("valanyr-separate-buffs");AbstractDungeon.actionManager.addToBottom(new WaitAction(0.8f));});
        q.add(()->{check(CardArtReview.capturedCombat("valanyr-separate-buffs"),"Separate Val'anyr buffs captured in combat");p.loseBlock();});
        q.add(()->{check(!p.hasPower(ValanyrPower.POWER_ID)&&!p.hasPower("Barricade")&&p.hasPower(GuardianAngelPower.POWER_ID),"Depleted ward disappears while Guardian Angel remains");
            p.damage(new DamageInfo(m,999,DamageInfo.DamageType.NORMAL));check(p.currentHealth==1&&!angel.available(),"Guardian Angel consumes itself on lethal damage");
            p.currentHealth=10;p.damage(new DamageInfo(m,4,DamageInfo.DamageType.NORMAL));check(p.currentHealth==6,"Consumed rescue does not prevent subsequent damage");});
        q.add(()->{check(!p.hasPower(GuardianAngelPower.POWER_ID),"Spent Guardian Angel icon is removed");reset();new Valanyr().equip(p);});
        q.add(()->{p.powers.add(new BarricadePower(p));p.powers.add(new BlurPower(p,2));p.loseBlock();});
        q.add(()->{check(p.getPower("Barricade")!=null&&p.hasPower("Blur")&&p.hasPower(GuardianAngelPower.POWER_ID),"Val'anyr depletion preserves independent Barricade, Blur, and rescue");
            p.currentHealth=3;p.powers.add(new FadePower(p,2));java.util.Collections.sort(p.powers);
            p.damage(new DamageInfo(p,3,DamageInfo.DamageType.HP_LOSS));angel=(GuardianAngelPower)p.getPower(GuardianAngelPower.POWER_ID);
            check(p.currentHealth==2&&angel.available(),"Fade resolves before Guardian Angel spends rescue");
            p.damage(new DamageInfo(p,10,DamageInfo.DamageType.HP_LOSS));check(p.currentHealth==1&&!angel.available(),"Guardian Angel also prevents lethal HP loss");});
        for (int choice=0;choice<3;choice++) {
            final int statChoice=choice;
            q.add(()->{reset();
                for(long seed=0;seed<100000;seed++) {
                    com.megacrit.cardcrawl.random.Random candidate=new com.megacrit.cardcrawl.random.Random(seed);
                    boolean matches=true;for(int i=0;i<4;i++)if(candidate.random(2)!=statChoice)matches=false;
                    if(matches){AbstractDungeon.miscRng=new com.megacrit.cardcrawl.random.Random(seed);break;}
                }
                new Xalatath().equip(p);
            });
            q.add(()->{String id=statChoice==0?"Strength":statChoice==1?MasteryPower.POWER_ID:"Dexterity";
                check(p.hasPower(id)&&p.getPower(id).amount==8&&haste()==0,"Xalatath can roll all eight points into "+id+" without Haste");});
        }
        q.add(()->{reset();up(new Xalatath()).equip(p);play(up(new PowerInfusion()),null,0);});
        q.add(()->{int sum=0;for(String id:new String[]{"Strength","Dexterity",MasteryPower.POWER_ID})if(p.hasPower(id))sum+=p.getPower(id).amount;
            check(sum==10&&haste()==2,"Xalatath+ grants ten permanent points and leaves Power Infusion Haste independent");nextTurn();});
        q.add(()->{int sum=0;for(String id:new String[]{"Strength","Dexterity",MasteryPower.POWER_ID})if(p.hasPower(id))sum+=p.getPower(id).amount;
            check(sum==10&&haste()==1,"Xalatath does not reroll or accumulate at turn start while Haste decays");
            CardArtReview.card(new Xalatath(),"xalatath-three-stats");});
    }
}
