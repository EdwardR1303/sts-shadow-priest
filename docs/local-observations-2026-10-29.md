# October 29 local playtest revision

This revision follows 0.1.2 and is not published to Workshop. The release version remains unchanged pending local playtesting.

- Details includes enemy Block absorbed and resolved overkill in damage dealt. Absorbed damage is a lighter bar segment; the exclusion checkbox updates totals, averages, sorting and percentages. Damage prevented by Buffer, Intangible or the Heart's damage cap is excluded. Healing Done records actual HP restored, with Vampiric Embrace credited separately from Touch. Combat, turn, Overall and saved-run views share these metrics. Older reports show a missing-data notice rather than fabricated totals.
- The main menu uses a native **Details** button beneath Play, or beneath the Continue/Abandon group while a save exists. Existing controller navigation includes this button.
- Shadow Weaving grants flat end-turn Block, like Orichalcum, with no Dexterity/Frail calculation. Its preview uses the same calculation.
- Encroaching Shadows applies 5 Madness, or 7 upgraded; its Block remains 6/9.
- Mind Decay retains its start-of-turn timing and Retain, but now causes 1 HP loss that bypasses Block. It still cannot tick after combat ends. HP-loss prevention and reactions follow normal engine rules.
- Singularity places its damage sentence and evaluated preview before the removal text. The actual action order still consumes DoTs before hitting. Its inward violet filaments, collapsing rings and burst scale with the calculated hit: under 50, 50–99, and 100+.
- X previews appear only in combat. Idol of N'Zoth's text refers simply to Powers; Legendary equipment still grants its existing Mastery benefit. Tithe Evasion omits the redundant “after Block” text without changing mitigation.

Validation uses incremental Maven verification and short engine fixtures, with no full-run simulations. `-ObservationsOnly` checks damage components, prevention, actual healing, attribution, persistence, previews and the changed card values; `-HistoryOnly` checks native menu access and archive navigation. Game-rendered screenshots cover the damage/healing panels, Singularity's text and all three FX tiers. `-MindDecayOnly` checks real turn transitions, retained Block, multiple copies and end-of-turn DoT victory.

The desktop playtest launcher builds and copies this local JAR. Build scripts now use incremental `verify` so launching does not delete and recreate the OneDrive-synced build tree.

Completed validation: 34 unit tests, 26 focused observation assertions, 16 menu/history assertions, and 16 real-turn Mind Decay assertions passed. Reviewed the exported UI, card text and final FX tier images. These checks validate behavior, not full-run balance.

## Follow-up: Insanity Details and Yogg-Saron

Idol of Yogg-Saron separates the final energy token from punctuation so all three energy icons render. Details adds Insanity Gained as a fifth metric, with actual accepted gains, source percentages, turn/combat/Overall totals and saved history. Damage-taken gains have separate source rows. Overflow, blocked gains and forced Voidform entry do not count. Older reports are explicitly marked as lacking Insanity data.

Follow-up validation: 35 unit tests and 20 focused engine/UI assertions passed. Reviewed all three rendered energy icons and the five-metric Insanity breakdown.
