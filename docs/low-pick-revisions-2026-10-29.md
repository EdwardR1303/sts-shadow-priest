# Low-pick card revisions: local playtest

Accepted October 29, 2026. These changes remain local; no release version bump, Git push, or Workshop publication.

| Card | Implemented change |
| --- | --- |
| Mind Blast | Explicit gain is 10 Insanity per unique DoT, snapshotted before the hit. Damage formula unchanged. |
| Shadow Word: Torment | 7/10 damage; applies 3/5 Pain and 3/5 Madness separately. |
| Shadow Word: Death | Surviving HP-damaging hits cost 2 HP. A direct kill returns the played card once; subsequent hits stop. Upgrade still adds a second hit and supports Haste. |
| Purify Disease | Upgrade grants 1 Artifact after cleansing, replacing Retain. |
| Bulwark | Below 35% HP, grant its Block and Blur twice. Dexterity applies to each grant; Haste does not add repeats. |
| Mind Soothe | Applies to all enemies, with independent DoT snapshots and Artifact checks. |
| Mental Fortitude | Upgrade costs 1 energy; stats remain +2 Dexterity, +2 Mastery, -2 Strength. |
| Serendipity | Both versions Retain; upgrade still removes Exhaust. |
| Surrender to Madness | Fixed-cost Attacks are free for the rest of combat. Upgrade draws 2 instead of reducing cost; both cost 3 and Exhaust. X-cost spending is unchanged. |

Shadow Mend, Focused Will, Tithe Evasion, Malady of the Mind, and Dark Reinforcement were reviewed and left unchanged. Existing WoW artwork is retained. The proposals file preserves the user's suggested-edit notes while marking reviewed entries; canonical rules and upgrades are in cards.md and design/cards.json.

Death retains its existing requirement that a hit remove enemy HP before charging backlash. Its card text now makes this explicit. Normal engine card cleanup handles the refund, so purged replays cannot generate extra cards; forced Exhaust and Rebound retain their normal precedence. A killing blow from a separate effect such as Psychic Link does not qualify.

Surrender uses the existing alternate-cost interface instead of changing cards' underlying costs. This covers newly drawn cards, survives turn boundaries, and works without the starter relic. The doubled incoming Attack damage remains an inseparable buff; Artifact cannot prevent it. Repeated Surrender attempts cannot draw again.

Validation: incremental Maven verify passed 35 unit tests. The `-LowPickOnly` isolated engine fixture passed 57 assertions, including card playability, before/after damage and resource values, separate Artifact applications, the exact low-HP boundary, per-hit backlash and real card returns, replay resets, purged copies, real-turn Retain, zero-energy fixed-cost Attacks, unchanged X spending with Haste/Chemical X, and starterless Surrender. Exported upgraded-card renderings were reviewed for descriptions and previews; Surrender's long title was reduced to fit. No full-run simulations were run.

Reproduce: `scripts/playtest.ps1 -SkipBuild -LowPickOnly -Windowed -Width 1600 -Height 900`. Results are in `.smoketest/smoke-results.txt`; card renderings are under `.smoketest/card-review/low-pick-*.png`.
