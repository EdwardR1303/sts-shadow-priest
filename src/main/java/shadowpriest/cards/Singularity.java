package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;

public class Singularity extends PriestCard {
    public static final String ID="shadowpriest:Singularity";
    public Singularity() { super(ID,2,CardType.ATTACK,CardRarity.UNCOMMON,CardTarget.ENEMY); baseDamage=16;baseMagicNumber=magicNumber=3; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { final Singularity attackCard=this;
        addToBot(shadowpriest.vfx.ShadowFx.hit(shadowpriest.vfx.ShadowSpellEffect.Style.BLAST, p, m));
        addToBot(new DamageAction(m,new DamageInfo(p,damage,damageTypeForTurn),AbstractGameAction.AttackEffect.NONE));
        addToBot(new AbstractGameAction(){public void update(){
            if(VoidBolt.alive(m)){
                int stacks=0;
                for(String id:new String[]{ShadowDotPower.TOUCH,ShadowDotPower.PAIN,MadnessPower.POWER_ID})
                    if(m.hasPower(id))stacks+=Math.max(0,m.getPower(id).amount);
                if(stacks>0){
                    int hit=attackCard.bonusDamage(m,stacks);
                    addToTop(new DamageAction(m,new DamageInfo(p,hit,damageTypeForTurn),AttackEffect.NONE));
                    addToTop(shadowpriest.vfx.ShadowFx.hit(shadowpriest.vfx.ShadowSpellEffect.Style.COLLAPSE, p, m));
                    for(String id:new String[]{ShadowDotPower.TOUCH,ShadowDotPower.PAIN,MadnessPower.POWER_ID})
                        if(m.hasPower(id))addToTop(new RemoveSpecificPowerAction(m,p,id));
                }
            }
            isDone=true;
        }}); }
    @Override public void upgrade() { if(!upgraded) { upgradeName(); upgradeDamage(6);upgradeMagicNumber(2); } }
    public int bonusDamage(AbstractMonster target,int stacks){
        AbstractPlayer p=AbstractDungeon.player;
        float value=Math.max(0,magicNumber+MasteryPower.current())*(float)stacks;
        for(com.megacrit.cardcrawl.relics.AbstractRelic relic:p.relics)value=relic.atDamageModify(value,this);
        for(com.megacrit.cardcrawl.powers.AbstractPower power:p.powers)
            if(!power.ID.equals("Strength"))value=power.atDamageGive(value,damageTypeForTurn,this);
        value=p.stance.atDamageGive(value,damageTypeForTurn,this);
        for(com.megacrit.cardcrawl.powers.AbstractPower power:target.powers)value=power.atDamageReceive(value,damageTypeForTurn,this);
        for(com.megacrit.cardcrawl.powers.AbstractPower power:p.powers)value=power.atDamageFinalGive(value,damageTypeForTurn,this);
        for(com.megacrit.cardcrawl.powers.AbstractPower power:target.powers)value=power.atDamageFinalReceive(value,damageTypeForTurn,this);
        return Math.max(0,(int)Math.floor(value));
    }
}
