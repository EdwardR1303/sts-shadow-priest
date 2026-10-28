# Changelog

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
