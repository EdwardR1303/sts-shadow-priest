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

## First reward-card batch

On October 23, 2026, Maven `clean verify` passed all 5 unit tests. The reward-card suite passed 73 actual-game checks, followed by the full combined suite passing all 115 checks at 1920 x 1080 windowed. The final result was `ALL CHECKS PASSED`.

This batch supersedes the earlier DoT values: Touch is now 5 damage for 3 turns (7 upgraded), and Pain is 3 damage for 5 turns (5 upgraded). Upgrades leave duration unchanged. Updated regression checks verify independent expiry, refresh, and fixed damage. New coverage includes:

- Six custom commons and six custom uncommons in rewards; registration and upgraded copies for all 12 cards.
- Played DoTs returning after end-turn discard, with unplayed, exhausted, and full-hand cases.
- Bulwark retaining Block for exactly one turn; Shadow Mend paying its current cost before increasing it, including repeated plays and copies.
- Fade reducing damage after Block and combining with Intangible; multi-hit Insanity generation and Mind Blast's DoT bonus under Voidform.
- Death's per-hit threshold and backlash, fully blocked hits, and Fade interaction.
- Both X-cost cards, duration extension, zero-energy upgrades, free plays, and Chemical X.
- Dispersion ending the turn and exhausting; its upgrade retaining the two-energy cost.
- Prayer's damage-then-heal order, consumption, expiry, mitigation, zero-cost upgrade, and failure to prevent lethal damage or resurrect the player.
- Foresight's Power-only chooser, base retrieval, upgraded free play, reuse without Exhaust, and empty-pile behavior.
- Psychic Link triggering once per Attack card across Touch targets, preserving durations, ignoring attack modifiers, generating no extra Insanity, and stacking without recursion.

See [the implementation rules](card-batch-01.md) for the exact card values and edge-case decisions. New reward-card portraits and power icons are temporary. This validation establishes controlled combat behavior; full-run balance and human review of the new cards remain pending.

## Second reward-card batch and recovery

On October 23, 2026, Maven `clean verify` passed all 7 unit tests. The reward-card suite passed 120 actual-game checks, followed by the full combined suite passing all 162 checks at 1920 x 1080 windowed. Both game runs ended with `ALL CHECKS PASSED`.

The 47 added checks cover:

- Registration and upgraded copies for Fortitude, Reaping Madness, Madness, and Halo; loading all four portraits at normal and inspection sizes.
- Fortitude granting its own 2/3 Dexterity independently of other sources, granting only the upgrade difference, blocking equal/weaker copies even after Dexterity loss, and clearing its flag after combat.
- Reaping's cost, deferred gain, stacking, threshold delivery, and charge cap.
- Actual turn transitions through Voidform and recovery: blocked attacks and end-of-turn gain throughout the recovery turn, then both sources resuming. Unit tests also cover recovery reset at combat end.
- Madness applying before damage, adding permanent stacks, never ticking or decaying, ignoring duration extension, and counting as one unique DoT for Mind Blast and area-hit Insanity.
- Madness+ spreading only stacks, Artifact blocking new stacks while preserving damage from existing stacks, normal damage modifiers and Block, Voidform's discount, and one Psychic Link trigger.
- Halo costing one as a Skill, no immediate damage or Psychic Link trigger, end-of-turn pulses respecting Block and ignoring offensive modifiers, no extra Insanity, separate base/upgraded aura counters, independent expiry, and no Voidform damage or cost bonus.

The source icons and generated Reaping Madness artwork were inspected; all four images and source attribution were verified inside the built JAR. The reused card framing path was exercised in-game. Human review of final presentation and full-run balance remains pending. See [second-batch rules](card-batch-02.md) and [artwork provenance and prompt](card-batch-02-art.md).

## Third reward-card batch and capped DoT duration

On October 23, 2026, the final Maven `clean verify` passed all 7 unit tests and the full combined suite passed all 201 actual-game checks at 1920 x 1080 windowed, ending with `ALL CHECKS PASSED`. An earlier reward-only run passed 158 checks; the final build added the four-choice boss-reward regression, bringing reward-only mode to 159 checks.

This batch supersedes previous Touch/Pain upgrade and refresh behavior. Touch always deals 5 damage and applies 3 stacks, or 4 upgraded. Pain always deals 3 damage and applies 5 stacks; its upgrade costs zero. Applications add duration to a hard cap of five, including Mind Flay: Insanity extension and Void Blast spread. Natural ticks consume one stack; Psychic Link consumes none.

The 39 added checks cover:

- Three native rares plus temporary supplements; actual Question Card generation of four distinct boss rares without exhausting the pool.
- Registration, upgraded copies, and both portrait sizes for Tentacle Slam, Void Blast, Mass Dispel, and Vampiric Embrace.
- Fixed damage, additive duration, five-stack constructor/application caps, and a twenty-energy duration extension remaining capped.
- Tentacle Slam's selected-target damage, energy costs, application after the hit, and intervening-target selection in either direction.
- Void Blast's additive Touch/Pain/Madness spread, unchanged source, capped duration, repeated Madness growth, separate Artifact checks, and snapshots surviving a killing blow.
- Mass Dispel removing Block, Artifact, and positive Strength while preserving negative Strength and future buff sources; reusable base and upgraded costs.
- Embrace's cost, base/upgraded stacking, natural and Psychic Link healing across multiple enemies, no healing from Pain or prevented ticks, full fixed healing from a reduced but damaging tick, and killing-tick healing.
- The Heart's Invincible power at a 200-damage budget: Touch respects the remaining budget, heals when one HP is removed, and grants no healing once the budget is exhausted.

All four source WoW icons were inspected, loaded by the game, and packaged with attribution. See [third-batch rules and artwork sources](card-batch-03.md). The Invincible checks exercise that power in a controlled encounter; they do not establish an A20 Heart victory or balanced full-run sustain.

## Fourth reward-card batch

On October 23, 2026, Maven `clean verify` passed all 7 unit tests, and the full suite passed all **233 actual-game checks** at 1920 x 1080 windowed. The result ended with `ALL CHECKS PASSED`. Reward-only mode now contains 191 checks; this batch was validated through the combined run.

The 32 new checks cover:

- Registration, upgraded copies, and both WoW portrait sizes for Void Bolt, Void Volley, and Void Torrent.
- Bolt's base/upgraded damage and energy, existing-only DoT maintenance, capped duration, existing Madness growth, Artifact behavior, and free amplified damage during Voidform.
- Volley resolving three hits with one Psychic Link trigger and one Embrace healing event; Insanity and maintenance per hit; upgraded values and caps; target-specific modifiers; and selecting a survivor after a killing bolt while excluding half-dead enemies.
- Both X cards at zero energy, Chemical X at zero energy, free play preserving energy, and undiscounted X with amplified damage during Voidform.
- Torrent's escalating base/upgraded sequences, reset on reuse, unchanged stored starting damage, separate modifiers and rounding per hit, blocked-hit Insanity generation, and stopping without retargeting after killing its selected enemy.

The existing reward checks now verify ten native Commons, nine native Uncommons, four native Rares with no Ironclad supplements, and actual generation of four distinct boss rares with Question Card. The 28-entry catalog passed schema and unique-ID validation. Source icons were visually inspected and verified with their attribution inside the built JAR.

After all checks passed, automatic shutdown logged a `ClosedByInterruptException` while the game's background writer validated an isolated `.smoketest/preferences/SHADOW_PRIEST` preference file. Combat assertions completed before shutdown; this run does not validate save/reload. Normal game saves were not used. See [fourth-batch rules](card-batch-04.md) for exact semantics and artwork sources.

## Surrender to Madness

On October 23, 2026, Maven `clean verify` passed all **10 unit tests**, and the full combined suite passed all **265 actual-game checks** at 1920 x 1080 windowed, ending with `ALL CHECKS PASSED`. The three added unit tests cover lasting state across turns, bypassing the threshold/recovery, invalidating ready charges, extending active Voidform, and reset to the ordinary combat lifecycle.

The reward-only suite subsequently passed all **223 checks** after the permanent-state meter label and Voidform keyword description were updated. The final localization correction links the card's Voidform text to that keyword; a final `clean verify` validated the packaged build.

The 32 added game checks cover:

- Rare Skill registration, base/upgrade energy costs, Exhaust, upgraded copies, and both portrait sizes using the archived WoW ability icon.
- Entry below ten Insanity, disabled repeat plays, free amplified Attacks, X-cost damage/energy, actual persistence across an enemy turn, and absence of new Insanity or activation charges.
- Doubling Attack damage before Block, matching enemy intent previews, multihit damage, combination with Vulnerable, and unchanged HP-loss and non-Attack damage.
- Intangible capping incoming damage, Fade reducing subsequent HP loss, and Desperate Prayer recovering a survived doubled hit.
- Artifact preventing exposure while preserving Voidform; later Artifact not removing an existing penalty; actual Orange Pellets activation through Skill, Power, and Attack plays cleansing only the penalty.
- Cleansing not enabling another Surrender, invalidating an earned charge, extending the same active Power without stacking bonuses, bypassing recovery, and queued duplicates consuming only one Artifact.
- Lasting Voidform and its discount working without the starter relic; controlled combat cleanup restoring normal state and playability.

The catalog validates against its schema with 29 unique entries. The source icon was visually inspected and verified alongside its attribution in the built JAR. Proposed non-starter relics and Mastery remain unimplemented; no claim is made about testing their future combinations with Surrender. Full-run balance, including A20 Heart performance with and without cleansing, remains for human playtests. See [Surrender rules](surrender-to-madness.md).

## Fifth reward-card batch and Mastery

Across October 23-24, 2026, `clean verify` passed all **10 unit tests**. The focused fifth-batch suite passed **69 actual-game checks**, and the full combined regression suite passed **331 checks**, both at 1920 x 1080 windowed and ending in `ALL CHECKS PASSED`.

The 66 added combat checks cover:

- Registration, upgraded copies, rarity pool counts (14/11/7), both portrait sizes for eight cards, and exclusion of the two unresolved drafts.
- Penance's HP-only conversion, blocked/fully blocked hits, Strength, Dexterity/Frail exclusion, Link exclusion, and overkill capping.
- Atonement's unique-DoT count, once-only Block modifiers, and pre-kill snapshot.
- Purify's exact debuff whitelist, Retain/Exhaust, and Dispel's base/upgraded removal without deleting Strength.
- Seeded dodge success/failure, independent multihit rolls, preserved Block, non-Attack exclusions, nonstacking upgrades, and highest-benefit tracking/reset.
- Positive/negative Mastery, both DoTs, Link, duration consumption at zero damage, Intangible's zero floor, Embrace eligibility, Artifact, and stacking through zero.
- Ire's per-hit base/upgraded formula, nonnegative bonus, three-DoT condition, per-target area damage, Link exclusion, escalating X hits with Voidform, and mixed-copy stacking.
- Scream's costs, Exhaust, Artifact, actual skipped enemy actions with continuing DoT ticks/healing, and consecutive stuns.
- Committal's Voidform condition, stat gain after the triggering card, once-per-card multihits, existing/new Power copies, persistent stats after ordinary Voidform, and continuing Surrender turns.

An initial consecutive-stun assertion included damage from the uncontrolled second enemy; the fixture was corrected to keep that enemy idle before rerunning successfully. No stun restriction was added to mask the fixture failure.

The seven original WoW icons were verified through named spell metadata and visually inspected. Shadow Committal uses original generated art. The final build repeated all 69 focused checks after the title-layout adjustment. All eight upgraded cards were rendered by the game for visual review; Shadow Committal uses a smaller title font to clear the energy orb. The rendered Mastery icon was inspected and retains the Strength silhouette in purple. See [batch rules and artwork prompt](card-batch-05.md). These tests establish controlled behavior; full-run balance, A20 Heart performance, and combinations with unimplemented relics remain open.

## Limits

These are correctness checks in a controlled encounter, not a full-run balance study. Only BaseMod and StSLib were loaded alongside this mod. Interactions with other mods, every relic or card, save/reload during combat, and long-run progression have not been exhaustively tested. The X-cost check verifies exclusion from the discount, not every X-cost card's effects.

Some art remains placeholder. Rewards now contain fourteen Shadow Priest Commons, eleven Uncommons, and seven Rares, with no Ironclad supplements. Question Card can generate four distinct native boss choices. Human playtests should evaluate setup damage taken, time to earn Voidform, hand quality at activation, sustain, Madness spreading, and whether waiting or immediate activation is more enjoyable.
