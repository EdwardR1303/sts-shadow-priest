package shadowpriest.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import shadowpriest.details.DetailsTracker;

/** Generated-only Curse: retained copies tick at turn start, without a card play. */
public class MindDecay extends CustomCard {
    public static final String ID = "shadowpriest:MindDecay";
    public MindDecay() {
        super(ID, CardCrawlGame.languagePack.getCardStrings(ID).NAME,
                "shadowpriest/images/cards/skill/default.png", -2,
                CardCrawlGame.languagePack.getCardStrings(ID).DESCRIPTION,
                CardType.CURSE, CardColor.CURSE, CardRarity.SPECIAL, CardTarget.NONE);
        selfRetain = true;
        Texture[] art = StarterCardArt.get(ID);
        portrait = new TextureAtlas.AtlasRegion(art[0], 0, 0, 250, 190);
    }
    @Override protected Texture getPortraitImage() { return StarterCardArt.get(ID)[1]; }
    @Override public void atTurnStart() {
        AbstractPlayer p = AbstractDungeon.player;
        // This hook also visits draw/discard piles. Only copies already in hand qualify.
        if (!combatOngoing() || !p.hand.contains(this)) return;
        DamageAction penalty = new DamageAction(p, new DamageInfo(p, 2, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.POISON) {
            @Override public void update() {
                // Earlier queued start-of-turn effects can finish the encounter.
                if (!combatOngoing()) { isDone = true; return; }
                super.update();
            }
        };
        DetailsTracker.tag(penalty, DetailsTracker.card(this));
        addToBot(penalty);
    }
    private static boolean combatOngoing() {
        return AbstractDungeon.player != null && AbstractDungeon.currMapNode != null
                && AbstractDungeon.getCurrRoom() != null
                && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT
                && !AbstractDungeon.getCurrRoom().isBattleOver
                && AbstractDungeon.getMonsters() != null
                && AbstractDungeon.getMonsters().monsters.stream().anyMatch(m -> !m.isDeadOrEscaped() && !m.isDying);
    }
    @Override public void use(AbstractPlayer p, AbstractMonster m) { /* Curse-playing relics supply exhaustion. */ }
    @Override public void upgrade() { }
    @Override public AbstractCard makeCopy() { return new MindDecay(); }
}
