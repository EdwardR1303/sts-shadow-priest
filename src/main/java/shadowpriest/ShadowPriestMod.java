package shadowpriest;

import basemod.BaseMod;
import basemod.interfaces.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireInitializer;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.FontHelper;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.*;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import com.megacrit.cardcrawl.ui.panels.EnergyPanel;
import shadowpriest.cards.*;
import shadowpriest.character.ShadowPriest;
import shadowpriest.relics.VoidformRelic;
import shadowpriest.dev.CombatSmokeTest;

@SpireInitializer
public class ShadowPriestMod implements EditCharactersSubscriber, EditCardsSubscriber,
        EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
        PostUpdateSubscriber, PostRenderSubscriber {
    public static String makeID(String id) { return "shadowpriest:" + id; }
    public static String characterPath(String file) { return "shadowpriest/images/character/" + file; }
    public static void initialize() { BaseMod.subscribe(new ShadowPriestMod()); ShadowPriest.Meta.registerColor(); }
    @Override public void receiveEditCharacters() { ShadowPriest.Meta.registerCharacter(); }
    @Override public void receiveEditCards() {
        BaseMod.addDynamicVariable(new DotTurnsVariable());
        for (AbstractCard c : new AbstractCard[]{new MindSpike(), new PowerWordShield(), new VampiricTouch(), new ShadowWordPain(),
            new Bulwark(), new ShadowMend(), new Fade(), new MindFlay(), new ShadowWordDeath(), new MindBlast(),
            new Dispersion(), new DesperatePrayer(), new Foresight(), new MindFlayInsanity(), new MindSear(), new PsychicLink(), new PowerWordFortitude(), new ReapingMadness(), new ShadowWordMadness(), new Halo(), new EnterVoidform()}) {
            BaseMod.addCard(c); UnlockTracker.unlockCard(c.cardID);
        }
    }
    @Override public void receiveEditRelics() { BaseMod.addRelicToCustomPool(new VoidformRelic(), ShadowPriest.Meta.CARD_COLOR); }
    @Override public void receiveEditStrings() {
        String path = "shadowpriest/localization/eng/";
        BaseMod.loadCustomStringsFile(CardStrings.class, path + "CardStrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, path + "CharacterStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, path + "RelicStrings.json");
    }
    @Override public void receiveEditKeywords() {
        BaseMod.addKeyword("shadowpriest", "Vampiric Touch", new String[]{"touch"}, "A unique DoT. Deals the applied damage before the enemy acts. Ignores Block and Strength. Reapplying replaces damage and duration.");
        BaseMod.addKeyword("shadowpriest", "Shadow Word: Pain", new String[]{"pain"}, "A unique DoT. Deals the applied damage before the enemy acts. Ignores Block and Strength. Reapplying replaces damage and duration.");
        BaseMod.addKeyword("shadowpriest", "Madness", new String[]{"madness"}, "Stacks permanently and counts as one unique DoT. Never ticks or decays. Shadow Word: Madness deals Attack damage based on these stacks.");
        BaseMod.addKeyword("shadowpriest", "Voidform", new String[]{"voidform"}, "Attacks cost #b1 less and deal #b50% more damage until turn end. X costs are unchanged.");
    }
    @Override public void receivePostUpdate() {
        VoidformRelic relic = VoidformRelic.current();
        if (relic != null) relic.deliverReadyCard();
        if (Boolean.getBoolean("shadowpriest.smoketest")) CombatSmokeTest.update();
    }
    @Override public void receivePostRender(SpriteBatch sb) {
        VoidformRelic relic = VoidformRelic.current();
        if (relic == null || AbstractDungeon.currMapNode == null || AbstractDungeon.getCurrRoom() == null || AbstractDungeon.getCurrRoom().phase != AbstractRoom.RoomPhase.COMBAT) return;
        EnergyPanel orb = AbstractDungeon.overlayMenu.energyPanel;
        if (orb == null || orb.isHidden || AbstractDungeon.isScreenUp) return;
        float s = Settings.scale, w = 172 * s, h = 14 * s;
        float x = orb.current_x - w / 2f, y = orb.current_y - 112 * s;
        float oldColor = sb.getPackedColor();
        sb.setColor(new Color(0.31f, 0.21f, 0.43f, 1f));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, x - 2 * s, y - 2 * s, w + 4 * s, h + 4 * s);
        sb.setColor(new Color(0.09f, 0.06f, 0.14f, 0.95f)); sb.draw(ImageMaster.WHITE_SQUARE_IMG, x, y, w, h);
        sb.setColor(relic.state.active() ? Color.MAGENTA : new Color(0.52f, 0.25f, 0.85f, 1f));
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, x, y, w * relic.state.amount() / 10f, h);
        String text = relic.state.recovering() ? "RECOVERING" : relic.state.active() ? "VOIDFORM" : relic.state.ready() ? "VOIDFORM READY" : "Insanity " + relic.state.amount() + " / 10";
        FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, text, orb.current_x, y + 30 * s, Color.WHITE, 0.8f);
        sb.setColor(oldColor);
    }
}
