# Prototype validation: 0.1.0

Tested October 23, 2026 on Windows with Slay the Spire 12-18-2022, ModTheSpire 3.30.3, BaseMod 5.56.0, and StSLib 2.12.0. The isolated test launcher used 3840 x 2160 borderless.

## Results

Maven `clean verify`: 5 unit tests passed, no failures or skipped tests. These cover Insanity persistence, threshold capping, charge validation, reset, and cost edge cases.

Actual-game controlled encounter: 33 checks passed. The opt-in driver used game actions, real card plays, and real end-turn processing. Coverage includes:

- Character, ten-card deck, relic, and populated prototype reward pools.
- Two distinct DoTs, no immediate tick, refresh without stacking, independent duration, and expiry after three ticks.
- DoT independence from Strength, Weak, Vulnerable, and Block; Artifact blocking application and Intangible reducing each tick to 1.
- Three fully blocked Mind Spikes against two DoTs yielding 6 Insanity; persistence through an enemy turn.
- Retained zero-cost activation, no automatic transformation, Exhaust, and rejection of a copied spent charge.
- Mind Spike previewing and dealing 9 damage at zero energy during Voidform.
- A two-cost Bash spending one energy, and a temporarily discounted Bash spending zero.
- X-cost discount exclusion; actual turn-end expiry restoring normal costs and damage.
- Full-hand deferral, delivery when a slot opens, and combat reset.
- Shield granting 5 Block for 1 energy.
- Twin Strike generating 4 Insanity from two hits against two DoTs; Cleave generating 3 across targets with two and one DoTs.

The complete local check output is `.smoketest/smoke-results.txt`; it ends with `ALL CHECKS PASSED`. It is intentionally ignored by Git along with game logs and saves. Commands to reproduce are in [development and testing](development.md).

Artwork follow-up on the same date: replaced Touch/Pain debuff placeholders with their World of Warcraft icons. Rebuilt and repeated all 33 in-game checks and 5 unit tests successfully, including loading both custom textures during combat. Verified that both icon files and their source attribution are packaged in the JAR. Source images were inspected; an unobstructed in-game visual inspection remains pending.

Character-art follow-up: added a transparent generated combat sprite, shadow tint, idle bob, and a state-driven purple Voidform glow; moved Insanity beneath the energy orb. Visually checked the normal character, both WoW debuff icons, and the relocated meter at 4K. The sprite has real alpha and fits above the health bar; the meter is legible beneath the orb without covering the draw pile. All 33 combat checks and 5 unit tests passed with the renderer installed, including Voidform activation and expiry. A clean final combat run also passed at 1920 x 1080 windowed. A temporary extended visual-review pause was removed after combat state changed during the pause; sustained active-glow visual review remains for human playtesting.

Starter-art and DoT-upgrade follow-up: all 44 in-game checks passed, with 5 unit tests passing in `clean verify`. The added checks cover all four starter cards' normal/inspection portrait sizes, upgraded card copies, both 6-damage/four-turn DoTs, four fixed ticks despite offensive modifiers and Block, replacing base/upgraded applications in either direction, Artifact blocking both refresh values, and independent mixed 5/6-damage DoTs. The generated frame and source icons were inspected separately; final portrait layout remains available for human review in the game.

Voidform relic follow-up: inspected the generated cutout and its alpha channel, installed padded normal/enlarged relic textures, and repeated all 44 in-game checks successfully with the new relic loaded. The final in-game relic appearance remains for human review. The design-only JSON catalog and template also validate against their local schema; starter counts and DoT upgrade values match the prototype.

## Limits

These are correctness checks in a controlled encounter, not a full-run balance study. Only BaseMod and StSLib were loaded alongside this mod. Interactions with other mods, every relic or card, save/reload during combat, and long-run progression have not been exhaustively tested. The X-cost check verifies exclusion from the discount, not every X-cost card's effects.

Some art remains placeholder and rewards use the Ironclad pool. Human playtests should evaluate setup damage taken, time to earn Voidform, hand quality at activation, and whether waiting or immediate activation is more enjoyable.
