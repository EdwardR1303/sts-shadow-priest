package shadowpriest.patches;

import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePrefixPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpireReturn;
import com.megacrit.cardcrawl.integrations.steam.SteamIntegration;

/** Isolated test progression must never award achievements to the live Steam account. */
@SpirePatch(clz=SteamIntegration.class, method="unlockAchievement")
public final class LocalTestAchievements {
    @SpirePrefixPatch
    public static SpireReturn<Void> before() {
        return Boolean.getBoolean("shadowpriest.playtest")
            ? SpireReturn.Return(null) : SpireReturn.Continue();
    }
}
