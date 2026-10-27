# Combat effects ? first pass

October 27, 2026. Twelve Attack cards and Voidform entry now have thematic effects. This is a first pass; other cards retain their existing effects.

| Cards / event | Effect |
|---|---|
| Mind Spike | Narrow violet projectile and impact ring |
| Void Bolt, Void Volley, Void Surge | Brighter shadow bolts toward each actual target |
| Mind Blast, Void Blast | Localized psychic burst |
| Mind Flay | Thin pulsing tether for each hit |
| Mind Flay: Insanity, Void Torrent | Wider channel pulses for each hit |
| Mind Sear | Expanding violet pulse on every living enemy per hit |
| Singularity | Initial burst, then an inward spiral when DoTs are consumed |
| Void Ray | Broad violet beam using Hyperbeam's existing laser texture and short beam sound |
| Entering Voidform | Expanding aura and rising motes, including initial Surrender/Void Entropy entry |

## Timing and integration

Visual-only actions finish immediately and leave the existing damage actions, hit counts, target choices, DoT maintenance, costs, and Insanity rules in place. Multi-hit effects are emitted per hit; zero-repeat X cards emit nothing. Dead or half-dead targets do not receive new visuals. Effects last 0.4 seconds, or 0.7 for Void Ray and Voidform entry. No animation wait is added. Minor sounds are throttled to one per 140 milliseconds, so AoE and high-Haste channels do not stack numerous simultaneous cues.

Void Ray creates its effect directly after Curse selection and keeps damage, Curse exhaustion, and permanent growth together in the same action update, preserving the lethal-hit handling. Its beam extends across the screen visually but remains a single-target attack. Voidform's effect is on initial power application; extending an active form does not replay it.

The custom renderer borrows the installed game's `combat/laserThick` and `glowSpark` atlas regions, with violet additive layers and procedural rings. No game assets were copied into the repository. Drawing restores the SpriteBatch color and blend state; atlas textures remain owned by the game. Visual motion uses no gameplay RNG. Dimensions use Settings.scale and the current viewport width.

## Verification

- Incremental Maven `verify`: **28 unit tests passed**.
- `scripts/render-fx.ps1`: offscreen OpenGL rendering with no game window or save access. Nine styles each rendered at three animation times; checks cover render-state restoration, GL errors, and expiry.
- Reviewed the 27-frame contact sheet, then refined beam thickness and replaced solid discs with rings to keep targets readable.
- `scripts/render-fx.ps1 -SkipBuild -Scale 2`: all nine styles passed again using 3840?2160 logical coordinates projected into a 960?540 offscreen surface. This checks scaling, not full-resolution game performance.
- No combat simulation or second game instance was launched during the user's playtest. Timing, sound mix, and readability against actual encounters remain for in-game playtesting.

Output lives under `.local/fx-review/<logical-width>/`. The renderer is a test-only entry point and is not included in the mod JAR.
