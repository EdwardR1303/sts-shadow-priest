# Changelog

## 0.1.3 — Local release candidate, not yet published

- More Curse support: Blood Magic deals 15/20 damage, applies 3/5 Touch and Pain, and adds a Pain Curse to discard. Mental Fortitude replaces its Strength penalty with a Doubt in hand; its upgrade costs 1. Purify Disease can exhaust a Curse from hand and still works without one; its upgrade grants 1 Artifact. Malady of the Mind costs 0 and adds a Mind Decay to discard, even if its Madness multiplication is blocked.
- Mind Trauma deals 6/9 damage and applies 6/9 Madness, retaining its additional 10 Insanity. Shadow Word: Torment applies 3/5 Madness as well as Pain. Encroaching Shadows applies 5/7 Madness.
- Mind Blast grants an additional 10 Insanity per unique DoT on its target. Shadow Word: Death returns to hand on a direct kill; each hit that removes HP without killing the target costs 2 HP.
- Bulwark repeats its Block and Blur once when played below 35% HP. Mind Soothe affects all enemies. Serendipity has Retain in both versions.
- Surrender to Madness makes fixed-cost Attacks free for combat. Both versions cost 3 and Exhaust; the upgrade draws 2 cards. X-cost Attacks still spend energy normally, and incoming Attack damage remains doubled.
- Details includes damage absorbed by enemy Block and resolved overkill, with a lighter bar segment for absorbed damage and a checkbox to exclude it. Added actual Healing Done and Insanity Gained with source breakdowns, Overall totals, and saved history. Older reports identify unavailable metrics.
- Details now appears as a native main-menu button below Play (or the Continue/Abandon group).
- Shadow Weaving grants flat end-turn Block without Dexterity or Frail modifiers. Mind Decay now costs 1 HP at turn start and retains its combat-end safeguard.
- Singularity shows damage first and has new effects that scale at 50 and 100 damage. X-value previews are hidden outside combat. Cleaned up Idol of N'Zoth and Tithe Evasion wording and fixed Idol of Yogg-Saron's third energy icon.

Local playtesting is pending. These changes are not yet on Steam Workshop.

## 0.1.2 — October 28, 2026

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

## 0.1.1 — October 27, 2026

- Enemy health bars now show upcoming Touch, Pain, and Madness damage in purple. A fully purple remaining bar indicates enough DoT damage to deplete that HP.
- Preview updates with current stacks and Mastery, including negative Mastery, and accounts for Intangible, Buffer, and the Heart's remaining damage cap.
- Normal Poison keeps its green preview.
- Preview covers the next natural DoT ticks, not Halo or future card plays. Enemy revivals and phase transitions still work normally.

Quality-of-life update only; no card or balance changes. Restart after Steam downloads the update. Validated with 31 unit tests and 24 focused game checks; see [preview rules](docs/dot-health-preview.md).

## 0.1.0 — October 27, 2026

Initial public beta: 75 collectible cards, 11 class relics, custom character artwork and combat effects, and Combat Details with saved-run history.
