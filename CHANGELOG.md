# Changelog

## 1.0.1 - November 6, 2026

Playtested balance update with stronger Devour combinations, revised defensive options, Legendary improvements, and clearer Dragonwrath reporting.

### Devour and Curse support

- Tentacle Slam now stuns its target on Devour instead of refunding Energy.
- Unfurling Darkness deals 7 damage and, on Devour, deals its damage and applies Touch to ALL enemies. Its explicit 10 Insanity is granted once per play; its upgrade still costs 0.
- Focused Will grants 8/11 Block and 10/15 Insanity. Devour converts ALL current Block into persistent Barrier without generating extra Block.
- Shadowy Insight now uses Devour to avoid its printed Exhaust. The upgrade draws one additional card. The shared once-per-turn limit remains, and the Curse must already be in hand before drawing.
- Dark Reinforcement+ rewards both drawing and Devouring a Curse with 1 Energy and 1 card; it no longer grants Mastery. Vampiric Embrace also heals when you Devour.
- Madness Overwhelming now creates its Mind Decay in hand. Dark Void deals 6/10 AoE damage and adds a Mind Decay to hand. Shadow Word: Madness deals 10/15 damage.
- Shadow Mend+ heals 14 instead of 10, while both versions gain 2 Debt unless Devour prevents it.

### Defense, healing, and support

- Void Shield is now **Void Shift**: for 1 Energy, remove an enemy's Block and gain the amount removed. Its upgrade grants Retain. The transfer is not modified by Dexterity or Frail.
- Fade prevents 2/4 HP loss per event until your next turn. Mind Soothe reduces all enemies' Attack damage by 2/3 plus 1/2 per unique DoT on each target.
- Void Tendrils grants 6/8 Block per enemy. Encroaching Shadows grants 8/11 Block and applies 8/11 Madness. Crystalline Reflection grants 6/8 Block per repetition.
- Atonement deals 6/9 damage, applies 3/5 stacking Atonement, and Exhausts.
- Halo heals 2/3 HP once per pulse in addition to its existing AoE damage. Body and Soul copies now stack multiplicatively.
- Tithe Evasion prevents 2 HP loss per event during your turn; its upgrade now costs 0.
- Protected Channel costs 1 Energy and grants Block per primary X-based repetition, including non-damaging cards and printed X+1 upgrades. AoE targets do not multiply its triggers, and triggered Block does not gain Dexterity.
- Power Infusion costs 0 in both versions and protects Haste from decay for 2/3 turn ends. Mass Dispel costs 1 in both versions.

### Damage and Voidform

- Mind Flay deals 3 damage per hit before its existing upgrade. Mind Blast deals 10/14 base damage plus 3/5 per unique DoT.
- Mind Flay: Insanity now triggers extra Touch and Pain ticks for each repetition, without consuming their stacks. It still extends all existing DoTs, but its bonus ticks never trigger Madness.
- Mind Sear applies 2 Madness after each hit, including hits absorbed by Block. Searing Nightmare costs 1 and applies 2/4 Madness per qualifying hit.
- Void Bolt and Void Volley deal 11/15 damage per bolt.
- Shadow Committal is now **Shadowy Committal**. Its upgrade automatically plays for free when already in hand on entering Voidform. It and Void Volley resolve from left to right in hand order, completing each play before starting the next.

### Legendaries and Combat Details

- Xal'atath rolls four times in both versions; its upgrade grants 3 of the rolled stat each time instead of 2.
- Val'anyr now grants Barricade for the entire combat, even after Block reaches zero. The base version grants no opening Block; its upgrade grants 10. Guardian Angel remains independent.
- Details colors Dragonwrath's recorded damage contribution orange within each source bar, with a lighter orange segment for absorbed damage. The Damage dealt total also shows the contribution in orange parentheses, such as 428 (45); it is included in the total.
- Dragonwrath breakdowns work across combat, turn, Overall, and saved reports, respecting the blocked-damage filter and overkill. Older reports without repeat attribution cannot reconstruct that contribution.

Existing card and character IDs are preserved. Restart Slay the Spire after Steam downloads the update. Validation includes 42 passing unit tests, prior focused engine checks, and local playtesting; no full-run simulation was repeated for release packaging.

## 1.0.0 - October 31, 2026

- Released as **Xal'atath, the Shadow Priest**; removed the Beta label from the public listing and updated the mod-launcher name.
- Replaced the generic priest with matching Xal'atath illustrations for combat, character selection, rest sites, defeat, and Heart victory, including her signature cheek runes and bare feet.
- Added a visible floating idle pose and independently animated shadow orbs above her shoulders. Voidform brightens her purple aura and empowers the orbs; Dispersion fades into animated shadow mist.
- Updated the Workshop preview and character flavor text. Internal character IDs remain unchanged for existing saves and run history.
- The full release includes 75 collectible cards, 11 class relics, Devour and Curse synergies, and Combat Details. Card and relic balance is unchanged from 0.1.5.
- Developer tooling: added a short presentation review and a backed-up unlock script for the isolated local playtest profile. Local test launches suppress Steam achievement uploads; normal Workshop players retain normal progression.

## 0.1.5 - October 30, 2026

- Added **Devour**: exhaust a Curse from your hand to activate a bonus. Cards still resolve their base effects without a Curse; choose which Curse when multiple are available. Haste does not multiply Devour, and normal Curse-exhaust rewards still apply.
- Eight Devour cards: Tentacle Slam refunds Energy actually spent; Unfurling Darkness spreads Touch to other enemies; Mind Trauma applies 6 additional Madness; Shadow Mend avoids this cast's debt; Purify Disease grants 1 Artifact; Focused Will gains 7 additional base Block; Twin Suns gains 2 Haste; Void Ray retains its permanent damage growth with the new keyword. Purify Disease's upgrade now grants Retain instead of unconditional Artifact.
- Shadow Word: Madness deals 9/13 damage and adds a Mind Decay to hand. Madness Overwhelming applies 10/15 initial Madness plus the existing 5/8 per Power played, and adds a Mind Decay to discard. Generated Curses last only for combat.
- Reworked **Surrender to Madness**: costs 0 and is playable only after leaving Voidform twice this combat, with an inline progress counter. Enter permanent Voidform and gain 2/3 Strength and Mastery immediately and at each subsequent turn start. Die at the end of your fifth turn, including the casting turn. Win before the deadline; final-turn DoT lethal counts. This death bypasses revival and damage prevention. Surrender no longer doubles incoming damage or makes every Attack free; it retains Voidform's normal one-energy Attack discount.
- Added gold card borders and conditional text highlights for Devour, Bulwark, Piety, Dispel Magic, Mass Dispel, Purify Disease, Twin Suns, Shadow Word: Death's predicted direct lethal, and Surrender's unlock. Target-dependent highlights follow the hovered enemy.

Restart the game after Steam downloads this update. Start a new run to try the full package; existing cards use the revised rules. First local playthrough feedback was positive; beta balance testing continues.

## 0.1.4 - October 30, 2026

- Fixed saved Details runs disappearing from history when an unattributed effect had no source ID. Existing affected reports now load without changing their files or damage totals; new sources receive a stable fallback ID.
- Details now credits queued potion damage, Block, and healing by potion name, including Fire, Explosive, Block, and Blood Potions. Fairy in a Bottle revival healing is also attributed correctly. Ongoing power effects retain their own source names.
- Existing unattributed entries remain labeled as such; historical potion identities are not guessed. No card balance changes.

## 0.1.3 - October 29, 2026

- More Curse support: Blood Magic deals 15/20 damage, applies 3/5 Touch and Pain, and adds a Pain Curse to discard. Mental Fortitude replaces its Strength penalty with a Doubt in hand; its upgrade costs 1. Purify Disease can exhaust a Curse from hand and still works without one; its upgrade grants 1 Artifact. Malady of the Mind costs 0 and adds a Mind Decay to discard, even if its Madness multiplication is blocked.
- Mind Trauma deals 6/9 damage and applies 6/9 Madness, retaining its additional 10 Insanity. Shadow Word: Torment applies 3/5 Madness as well as Pain. Encroaching Shadows applies 5/7 Madness.
- Mind Blast grants an additional 10 Insanity per unique DoT on its target. Shadow Word: Death returns to hand on a direct kill; each hit that removes HP without killing the target costs 2 HP.
- Bulwark repeats its Block and Blur once when played below 35% HP. Mind Soothe affects all enemies. Serendipity has Retain in both versions.
- Surrender to Madness makes fixed-cost Attacks free for combat. Both versions cost 3 and Exhaust; the upgrade draws 2 cards. X-cost Attacks still spend energy normally, and incoming Attack damage remains doubled.
- Details includes damage absorbed by enemy Block and resolved overkill, with a lighter bar segment for absorbed damage and a checkbox to exclude it. Added actual Healing Done and Insanity Gained with source breakdowns, Overall totals, and saved history. Older reports identify unavailable metrics.
- Details now appears as a native main-menu button below Play (or the Continue/Abandon group).
- Shadow Weaving grants flat end-turn Block without Dexterity or Frail modifiers. Mind Decay now costs 1 HP at turn start and retains its combat-end safeguard.
- Singularity shows damage first and has new effects that scale at 50 and 100 damage. X-value previews are hidden outside combat. Cleaned up Idol of N'Zoth and Tithe Evasion wording and fixed Idol of Yogg-Saron's third energy icon.

Restart the game after Steam downloads this update. Existing runs use the new card rules; balance testing continues.

## 0.1.2 â€” October 28, 2026

- Haste now increases explicit card repetitions, including Mind Flay, Penance, Shadow Word: Death+ and base-game cards such as Dagger Spray. Modified counts are green. X receives Haste once; for-each effects, Halo duration and Dragonwrath replay triggers are unchanged.
- Power Infusion protects all Haste from decay for two turns; additional casts refresh the protection.
- Singularity now only deals damage from consumed DoTs. Its initial hit has been removed.
- Catharsis stores damage from HP lost during the current combat: 3 per HP, or 5 while that copy is in hand. Healing preserves its stored damage. Still Exhausts; its upgrade grants Retain.
- Tithe Evasion stacks and reduces HP loss from every damage source during your turn, after Block is spent. Prevented HP loss grants neither Insanity nor Catharsis damage.
- Void Bolt and Void Volley now deal 10/13 damage per hit. Void Volley automatically plays for free when already in hand as you enter Voidform; it remains Uncommon.
- Encroaching Shadows now grants 6/9 Block. Halo pulses for 4/6 damage. Searing Nightmare applies 2 Madness per hit.
- Protected Channel's triggered Block no longer scales with Dexterity; its initial Block still does.
- Mass Dispel+ now cleanses your debuffs instead of reducing its cost. Petrifying Scream+ stuns all enemies instead of reducing its cost. Both remain 2 energy.
- Surrender to Madness's damage penalty can no longer be prevented by Artifact or removed by debuff cleanses.
- More formula cards show calculated results in parentheses, including target-specific hover previews.

Restart the game after Steam downloads this update. Existing runs use the new card rules; balance testing continues.

## 0.1.1 â€” October 27, 2026

- Enemy health bars now show upcoming Touch, Pain, and Madness damage in purple. A fully purple remaining bar indicates enough DoT damage to deplete that HP.
- Preview updates with current stacks and Mastery, including negative Mastery, and accounts for Intangible, Buffer, and the Heart's remaining damage cap.
- Normal Poison keeps its green preview.
- Preview covers the next natural DoT ticks, not Halo or future card plays. Enemy revivals and phase transitions still work normally.

Quality-of-life update only; no card or balance changes. Restart after Steam downloads the update. Validated with 31 unit tests and 24 focused game checks; see [preview rules](docs/dot-health-preview.md).

## 0.1.0 â€” October 27, 2026

Initial public beta: 75 collectible cards, 11 class relics, custom character artwork and combat effects, and Combat Details with saved-run history.
