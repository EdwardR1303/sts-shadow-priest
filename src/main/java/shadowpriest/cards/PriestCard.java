package shadowpriest.cards;

import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import shadowpriest.character.ShadowPriest;

public abstract class PriestCard extends CustomCard {
    protected PriestCard(String id, int cost, CardType type, CardRarity rarity, CardTarget target) {
        super(id, CardCrawlGame.languagePack.getCardStrings(id).NAME,
            "shadowpriest/images/cards/" + (type == CardType.ATTACK ? "attack" : "skill") + "/default.png",
            cost, CardCrawlGame.languagePack.getCardStrings(id).DESCRIPTION,
            type, ShadowPriest.Meta.CARD_COLOR, rarity, target);
        Texture[] art = StarterCardArt.get(id);
        if (art != null) portrait = new TextureAtlas.AtlasRegion(art[0], 0, 0, 250, 190);
    }
    @Override protected Texture getPortraitImage() {
        Texture[] art = StarterCardArt.get(cardID);
        return art == null ? super.getPortraitImage() : art[1];
    }
}
