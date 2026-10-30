package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import shadowpriest.powers.ShadowDotPower;

public class TentacleSlam extends PriestCard {
    public static final String ID = "shadowpriest:TentacleSlam";
    public TentacleSlam() {
        super(ID, 2, CardType.ATTACK, CardRarity.COMMON, CardTarget.ENEMY);
        baseDamage = 8; baseMagicNumber = magicNumber = 3;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        if (m == null) return;
        final float left = Math.min(p.hb.cX, m.hb.cX), right = Math.max(p.hb.cX, m.hb.cX);
        final int hit=damage,stacks=magicNumber;
        final shadowpriest.mechanics.PlayedEnergy.Receipt paid=shadowpriest.mechanics.PlayedEnergy.receipt();
        addToBot(new shadowpriest.actions.DevourAction(devoured->{
            java.util.List<AbstractGameAction> effects=new java.util.ArrayList<>();
            // Refund before the hit so a combat-ending blow cannot discard the refund action.
            if(devoured&&paid.spent>0)effects.add(new com.megacrit.cardcrawl.actions.common.GainEnergyAction(paid.spent));
            effects.add(new DamageAction(m,new DamageInfo(p,hit,damageTypeForTurn),AbstractGameAction.AttackEffect.BLUNT_HEAVY));
            for(AbstractMonster enemy:AbstractDungeon.getMonsters().monsters)
                if(VoidBolt.alive(enemy)&&(enemy==m||(enemy.hb.cX>=left&&enemy.hb.cX<=right)))
                    effects.add(ShadowDotPower.apply(enemy,p,ShadowDotPower.TOUCH,stacks));
            return effects;
        }));
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeDamage(2); upgradeBaseCost(1); }
    }
}
