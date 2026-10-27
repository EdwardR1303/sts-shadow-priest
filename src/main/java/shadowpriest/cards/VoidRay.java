package shadowpriest.cards;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.*;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import shadowpriest.powers.*;

public class VoidRay extends PriestCard {
    public static final String ID="shadowpriest:VoidRay";
    public VoidRay() { super(ID,1,CardType.ATTACK,CardRarity.RARE,CardTarget.ENEMY); baseDamage=15;baseMagicNumber=magicNumber=5;exhaust=true; }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { addToBot(new shadowpriest.actions.VoidRayAction(this,m,damage,magicNumber)); }
    @Override public void upgrade() { if(!upgraded) { upgradeName(); upgradeDamage(8);upgradeMagicNumber(3);refreshBaseDamage(); } }
    public void refreshBaseDamage(){baseDamage=(upgraded?23:15)+misc;}
    @Override public void applyPowers(){refreshBaseDamage();super.applyPowers();}
    @Override public void calculateCardDamage(AbstractMonster m){refreshBaseDamage();super.calculateCardDamage(m);}
    @Override public AbstractCard makeStatEquivalentCopy(){AbstractCard copy=super.makeStatEquivalentCopy();((VoidRay)copy).refreshBaseDamage();return copy;}
    public void grow(int amount){
        java.util.Set<AbstractCard> copies=java.util.Collections.newSetFromMap(new java.util.IdentityHashMap<AbstractCard,Boolean>());
        copies.add(this);AbstractPlayer p=AbstractDungeon.player;
        for(CardGroup group:new CardGroup[]{p.masterDeck,p.hand,p.drawPile,p.discardPile,p.exhaustPile,p.limbo})
            for(AbstractCard c:group.group)if(c instanceof VoidRay&&c.uuid.equals(uuid))copies.add(c);
        for(AbstractCard c:copies){c.misc+=amount;((VoidRay)c).refreshBaseDamage();c.applyPowers();}
    }

    @com.evacipated.cardcrawl.modthespire.lib.SpirePatch(clz=com.megacrit.cardcrawl.helpers.CardLibrary.class,method="getCopy",paramtypez={String.class,int.class,int.class})
    public static class RestoreGrowth {
        @com.evacipated.cardcrawl.modthespire.lib.SpirePostfixPatch
        public static AbstractCard after(AbstractCard __result){
            if(__result instanceof VoidRay){((VoidRay)__result).refreshBaseDamage();__result.damage=__result.baseDamage;}
            return __result;
        }
    }
}
