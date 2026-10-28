package shadowpriest.cards;

import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import java.util.ArrayList;

public class VoidVolley extends PriestCard {
    public static final String ID = "shadowpriest:VoidVolley";
    public VoidVolley() {
        super(ID, -1, CardType.ATTACK, CardRarity.UNCOMMON, CardTarget.ALL_ENEMY);
        baseDamage = 10; baseMagicNumber = magicNumber = 1;
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) {
        int repeats = spendX(p);
        final VoidVolley volley = (VoidVolley)makeStatEquivalentCopy();
        for (int i = 0; i < repeats; i++) addToBot(new AbstractGameAction() {
            @Override public void update() {
                ArrayList<AbstractMonster> living = new ArrayList<>();
                for (AbstractMonster enemy : AbstractDungeon.getMonsters().monsters)
                    if (VoidBolt.alive(enemy)) living.add(enemy);
                if (!living.isEmpty()) {
                    AbstractMonster target = living.get(AbstractDungeon.cardRandomRng.random(living.size() - 1));
                    volley.calculateCardDamage(target);
                    // Effects, not card plays: resolve each hit and maintenance before selecting the next target.
                    addToTop(VoidBolt.addStacks(p, target, volley.magicNumber));
                    addToTop(new DamageAction(target, new DamageInfo(p, volley.damage, volley.damageTypeForTurn), AttackEffect.NONE));
                    addToTop(shadowpriest.vfx.ShadowFx.hit(shadowpriest.vfx.ShadowSpellEffect.Style.BOLT, p, target));
                }
                isDone = true;
            }
        });
    }
    public static void onVoidformEntry() {
        final java.util.ArrayList<AbstractCard> cards=new java.util.ArrayList<>();
        for(AbstractCard c:AbstractDungeon.player.hand.group)if(c instanceof VoidVolley)cards.add(c);
        AbstractDungeon.actionManager.addToBottom(new AbstractGameAction(){public void update(){
            for(AbstractCard c:cards)if(AbstractDungeon.player.hand.contains(c)){
                c.freeToPlayOnce=true;
                AbstractDungeon.actionManager.addCardQueueItem(new com.megacrit.cardcrawl.cards.CardQueueItem(c,null,com.megacrit.cardcrawl.ui.panels.EnergyPanel.totalCount,true,true),true);
            }
            isDone=true;
        }});
    }
    @Override public void upgrade() {
        if (!upgraded) { upgradeName(); upgradeDamage(3); upgradeMagicNumber(1); }
    }
}
