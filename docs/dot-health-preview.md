# DoT health-bar preview

Touch, Pain, and Madness contribute a purple segment to enemy health bars through StSLib's `HealthBarRenderPower` interface. The segment represents HP expected to be removed by their next natural player-end-of-turn ticks. When those ticks cover the enemy's remaining HP, the remaining bar is purple. Normal Poison keeps its green segment.

The forecast reads the actual tick-damage methods, so current Mastery (including negative Mastery), Madness rounding, and live stack changes are reflected immediately. Touch/Pain duration does not multiply a single turn's preview. Expired effects contribute nothing. Block, Strength, Weak, and Vulnerable do not affect these HP-loss ticks.

Intangible limits each tick separately. The forecast shares the Heart's remaining Invincible allowance across ticks and accounts for Buffer in the same power order as damage resolution. Computing the preview never runs damage callbacks or changes HP, stacks, Block, or damage allowances.

This predicts the currently applied DoTs, not all possible end-of-turn damage. Halo, future card plays, triggered bonus ticks, and hypothetical changes to Mastery are excluded. Poison's own preview is managed by the base game/StSLib. Other mods' arbitrary damage-interception hooks are not simulated, and reaching zero HP can still trigger an enemy's normal revival or phase transition.

Focused review: `./scripts/playtest.ps1 -SkipBuild -DotPreviewOnly -Windowed -Width 1920 -Height 1080`. It checks forecasts against resolved ticks and captures partial, lethal, Poison coexistence, and Intangible bars in the isolated test profile.
