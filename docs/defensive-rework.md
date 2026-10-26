# Defensive rework — October 25, 2026

## Power Word: Barrier

Uncommon Skill, 3 energy. Gain 30 Block (40 upgraded), with Dexterity and normal Block modifiers applied once. Keep existing Block. Retain the resulting reserve until it is depleted. No additional Block can be gained while the barrier is active, including from cards, relics, powers, or forced copies of Barrier. Other Barriers are unplayable until it breaks. No hand retention or Exhaust.

The reserve uses a separate power that supplies Barricade retention. Depletion releases the gain lock immediately and removes only this power. Independent Barricade and Blur remain; Val'anyr still follows its own depletion and lethal-protection rules. A zero-value grant with no existing Block does not activate the barrier.

## Mind Flay: Insanity

Add X stacks to every existing DoT, then deal 6 damage X times. Upgrade adds one stack and hit. Effective X includes Haste and Chemical X. Never create a missing DoT. Touch/Pain duration maintenance follows their cap and bypasses Artifact; additional Madness follows the same application rules as Void Bolt, including Artifact. Cap details stay in keyword/rules documentation instead of card text.

## Intangible and defeat visuals

Intangible crossfades the priest and floating legendary weapon into animated purple-black mist over half a second. The shader follows the actual Intangible power, including stacked duration and natural expiry. Voidform adds a brighter violet tint to the mist. The body and weapon reform when Intangible ends.

The separate death sprite now depicts the fallen priest in matching regalia. The full-resolution transparent source is retained; the game fits it to 400 logical pixels wide when loading because the base-game corpse renderer uses native texture dimensions.

- Asset: [`fallen_priest.png`](../src/main/resources/shadowpriest/images/character/fallen_priest.png)
- Reference: [`shadow_priest.png`](../src/main/resources/shadowpriest/images/character/shadow_priest.png)
- Mode: built-in image generation, transparent background, reference-based generation.
- Prompt: “Create a transparent-background game sprite for a fallen Shadow Priest, using the attached existing standing character as the identity, costume, color, and painted-art reference. Same adult fantasy elf priest with dark violet skin, long silver-white hair, elaborate gold-edged blue and purple Transcendence-inspired robes and tall ornate crown. Depict the whole character lying collapsed horizontally on their side on an invisible ground plane, feet toward the left and head toward the right, eyes closed, robes naturally folded, crown and shoulder armor recognizable. Non-graphic defeat pose: no blood, no wounds, no gore. Side-on / slight three-quarter game battle camera, not a top-down view. Match the reference's polished hand-painted fantasy strategy-card-game artwork and strong readable silhouette. A few very faint violet wisps may dissipate above the shoulders, but keep the body crisp and grounded. Single isolated sprite only, full body completely inside frame with transparent padding. Wide landscape composition, truly transparent background, no floor, no scene, no text, no UI, no weapon. This replaces a placeholder corpse sprite in a game mod.”

## Focused validation

`scripts/playtest.ps1 -SkipBuild -DefensiveReworkOnly -Width 1920 -Height 1080 -Windowed` runs only this batch in the isolated smoke-test profile. It checks reserve gain/retention/depletion, forced recasts, Dexterity/Frail, zero Block, independent retention effects, DoT upkeep and Artifact, X modifiers, Intangible expiry, and the separate corpse renderer. Combat screenshots are saved in `.smoketest/card-review/` for visual inspection. The old Sixth Batch Barrier expectations have also been updated to the new design.

Validation result: build passed, 14 unit tests passed, and all 44 focused in-game checks passed. Reviewed the actual combat screenshots for mist, reformation with Dragonwrath, and the fallen-priest sprite, plus both changed card descriptions. The full simulation suite was not rerun.
