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

## Limits

These are correctness checks in a controlled encounter, not a full-run balance study. Only BaseMod and StSLib were loaded alongside this mod. Interactions with other mods, every relic or card, save/reload during combat, and long-run progression have not been exhaustively tested. The X-cost check verifies exclusion from the discount, not every X-cost card's effects.

Art remains placeholder and rewards use the Ironclad pool. Human playtests should evaluate setup damage taken, time to earn Voidform, hand quality at activation, and whether waiting or immediate activation is more enjoyable.
