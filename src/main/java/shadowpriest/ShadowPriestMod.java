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
import shadowpriest.relics.*;
import shadowpriest.dev.CombatSmokeTest;

@SpireInitializer
public class ShadowPriestMod implements EditCharactersSubscriber, EditCardsSubscriber,
        EditRelicsSubscriber, EditStringsSubscriber, EditKeywordsSubscriber,
        PostUpdateSubscriber, PostRenderSubscriber, PostInitializeSubscriber, PreStartGameSubscriber {
    public static String makeID(String id) { return "shadowpriest:" + id; }
    public static String characterPath(String file) { return "shadowpriest/images/character/" + file; }
    public static void initialize() { BaseMod.subscribe(new ShadowPriestMod()); ShadowPriest.Meta.registerColor(); BaseMod.addSaveField("shadowpriest:details", shadowpriest.details.DetailsTracker.saveField()); BaseMod.addSaveField("shadowpriest:legendaryHistory", new shadowpriest.legendary.LegendaryEquipment()); }
    @Override public void receiveEditCharacters() { ShadowPriest.Meta.registerCharacter(); }
    @Override public void receiveEditCards() {
        BaseMod.addDynamicVariable(new DotTurnsVariable());
        for (AbstractCard c : new AbstractCard[]{new MindSpike(), new PowerWordShield(), new VampiricTouch(), new ShadowWordPain(),
            new Bulwark(), new FlashHeal(), new BloodMagic(), new MindSoothe(), new MentalFortitude(), new VoidShield(), new Insight(), new PowerWordBarrier(), new Piety(), new Serendipity(), new TitheEvasion(), new ShadowMend(), new Fade(), new MindFlay(), new ShadowWordDeath(), new MindBlast(),
            new Dispersion(), new DesperatePrayer(), new Foresight(), new MindFlayInsanity(), new MindSear(), new PsychicLink(), new PowerWordFortitude(), new ReapingMadness(), new ShadowWordMadness(), new Halo(), new TentacleSlam(), new VoidBlast(), new MassDispel(), new VampiricEmbrace(), new VoidBolt(), new VoidVolley(), new VoidTorrent(), new SurrenderToMadness(), new Penance(), new Atonement(), new PurifyDisease(), new DispelMagic(), new BodyAndSoul(), new InsidiousIre(), new PetrifyingScream(), new ShadowCommittal(), new Dragonwrath(), new Xalatath(), new Valanyr(), new EnterVoidform(), new UnfurlingDarkness(), new DarkVoid(), new PowerInfusion(), new ShadowCrash(), new CrystallineReflection(), new ProtectedChannel(), new Catharsis(), new SearingNightmare(), new DarkEvangelism(), new ShadowWordTorment(), new MindTrauma(), new VoidTendrils(), new EncroachingShadows(), new FocusedWill(), new ShadowWeaving(), new DarkRevelation(), new VoidSurge(), new MadnessOverwhelming(), new Singularity(), new TwinSuns(), new MaladyOfTheMind(), new VoidEntropy(), new DarkReinforcement(), new VoidRay(), new MentalDecay(), new EventHorizon()}) {
            BaseMod.addCard(c); UnlockTracker.unlockCard(c.cardID);
        }
    }
    @Override public void receiveEditRelics() {
        for(com.megacrit.cardcrawl.relics.AbstractRelic r:new com.megacrit.cardcrawl.relics.AbstractRelic[]{
            new VoidformRelic(),new ShadowyPreparation(),new IdolOfCthun(),new IdolOfYshaarj(),new IdolOfNzoth(),
            new IdolOfYoggSaron(),new ImprovedVoidform(),new VoidVengeance(),
            new Misery(),new InescapableTorment(),new SharedSuffering()})
            BaseMod.addRelicToCustomPool(r,ShadowPriest.Meta.CARD_COLOR);
    }
    @Override public void receiveEditStrings() {
        String path = "shadowpriest/localization/eng/";
        BaseMod.loadCustomStringsFile(CardStrings.class, path + "CardStrings.json");
        BaseMod.loadCustomStringsFile(CharacterStrings.class, path + "CharacterStrings.json");
        BaseMod.loadCustomStringsFile(RelicStrings.class, path + "RelicStrings.json");
    }
    @Override public void receiveEditKeywords() {
        BaseMod.addKeyword("shadowpriest", "Barrier", new String[]{"barrier"}, "Persistent Block that absorbs damage before other Block. Other Block gains do not increase it. Recasting refills the reserve to the card value after modifiers, without stacking or reducing a stronger reserve.");
        BaseMod.addKeyword("shadowpriest", "Atonement", new String[]{"atonement"}, "A debuff: your direct Attack hits on this enemy grant Block equal to its amount. No Dexterity scaling. Does not stack; stronger applications replace weaker ones. Not a DoT.");
        BaseMod.addKeyword("shadowpriest", "Insanity", new String[]{"insanity"}, "Gain #b10 per direct Attack hit and #b1 per DoT tick, including triggered ticks. HP-damaging events grant #b5. At #b100, receive Enter Voidform. No gain while ready, active, or in Recharge.");
        BaseMod.addKeyword("Legendary", new String[]{"legendary"}, "Only one Legendary may be in your deck. Equips once at combat start without drawing or playing a card. Cannot be duplicated into your deck.");
        BaseMod.addKeyword("shadowpriest", "Shadow Mend Debt", new String[]{"debt"}, "At the start of each turn, lose HP equal to your combined debt. Lasts for combat.");
        BaseMod.addKeyword("shadowpriest", "Vampiric Touch", new String[]{"touch"}, "Before the enemy acts, deal #b5 plus Mastery damage and remove one stack. Ignores Block and Strength. Applications add remaining ticks, capped at #b5.");
        BaseMod.addKeyword("shadowpriest", "Shadow Word: Pain", new String[]{"pain"}, "Before the enemy acts, deal #b3 plus Mastery damage and remove one stack. Ignores Block and Strength. Applications add remaining ticks, capped at #b5.");
        BaseMod.addKeyword("shadowpriest", "Madness", new String[]{"madness"}, "At the end of your turn, deal half the stacks rounded up plus Mastery damage, ignoring Block. Remove #b1 stack. No stack cap.");
        BaseMod.addKeyword("shadowpriest", "Extend", new String[]{"extend"}, "Add stacks to existing Touch and Pain. Never creates a missing DoT. Does not consume Artifact.");
        BaseMod.addKeyword("shadowpriest", "Haste", new String[]{"haste"}, "Each stack adds #b1 to effective X, including at zero energy. Stacks with Chemical X. At the end of your turn, lose half your Haste (rounded up).");
        BaseMod.addKeyword("shadowpriest", "Recharge", new String[]{"recharge"}, "After Voidform ends, no Insanity can be gained until the end of the following player turn.");
        BaseMod.addKeyword("shadowpriest", "DoT", new String[]{"dot", "dots"}, "Touch, Pain, and Madness. Each counts once per enemy. Tick at turn end, ignoring Block and Attack modifiers; Mastery increases their damage. Each tick, including triggered ticks, grants #b1 Insanity.");
        BaseMod.addKeyword("shadowpriest", "Mastery", new String[]{"mastery"}, "Each point adds #b1 damage to damaging DoT ticks. Can be negative; tick damage cannot fall below zero.");
        BaseMod.addKeyword("shadowpriest", "Voidform", new String[]{"voidform"}, "Attacks cost #b1 less and deal #b50% more damage. X costs are unchanged. Normally ends at turn end; Surrender to Madness extends it for the rest of combat.");
    }
    @Override public void receivePostInitialize() {
        BaseMod.addCustomScreen(shadowpriest.details.DetailsScreen.INSTANCE);
        BaseMod.addTopPanelItem(new shadowpriest.details.DetailsScreen.Button());
    }
    @Override public void receivePreStartGame() { shadowpriest.details.DetailsTracker.resetRun(); }
    @Override public void receivePostUpdate() {
        shadowpriest.details.DetailsTracker.update();
        VoidformRelic relic = VoidformRelic.current();
        if (relic != null) relic.deliverReadyCard();
        if (Boolean.getBoolean("shadowpriest.smoketest")) CombatSmokeTest.update();
    }
    @Override public void receivePostRender(SpriteBatch sb) {
        renderInsanityBar(sb);
        if (Boolean.getBoolean("shadowpriest.smoketest")) CombatSmokeTest.renderReview(sb);
    }
    private void renderInsanityBar(SpriteBatch sb) {
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
        sb.draw(ImageMaster.WHITE_SQUARE_IMG, x, y, w * (relic.state.active() ? 1f : relic.state.amount() / (float)shadowpriest.mechanics.InsanityState.THRESHOLD), h);
        String text = relic.state.surrendered() ? "SURRENDERED" : relic.state.recovering() ? "RECHARGE" : relic.state.active() ? "VOIDFORM" : relic.state.ready() ? "VOIDFORM READY" : "Insanity " + relic.state.amount() + " / " + shadowpriest.mechanics.InsanityState.THRESHOLD;
        FontHelper.renderFontCentered(sb, FontHelper.tipBodyFont, text, orb.current_x, y + 30 * s, Color.WHITE, 0.8f);
        sb.setColor(oldColor);
    }
}
