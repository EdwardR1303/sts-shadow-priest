# Combat Details

From the main menu, click **Details** beneath Play (or beneath the Continue/Abandon Run group when a save exists) to browse saved runs. Runs are listed newest first with local date/time, character, Ascension, seed, and the last recorded fight. Select a run to open its **Overall** report, then use the existing combat, turn, and metric controls. **Back** or Escape returns to the list; Close or Escape from the list returns to the menu. **Refresh** reloads the archive.

History is read-only: it does not load a game save or replace the current run's tracker. A last-combat Victory means that fight was won, not necessarily the whole run. Empty runs are omitted; unreadable or unsupported reports are skipped with a count. Listing and report loading happen in the background.

Click the purple **bar-chart button in the top bar** to inspect the current or previous combat. It is available during combat, on the map, at combat rewards, and on the death/victory screens. Finish card selections before opening it. Close the report with **Close**, **Escape**, or the same top-bar button.

Details renders above the game's Victory, Defeat, and Spoils banners and other dungeon overlays. The underlying banner is preserved and becomes visible again when the report closes.

Choose **Damage dealt**, **Damage taken**, **Block generated**, **Healing done**, or **Insanity gained**, then **All turns** or an individual turn. Sources appear in descending order with their amount and percentage of the selected total. The five tiles show combat totals and averages per player turn. Arrow buttons move between encounters. Scroll or use the paging buttons for long fights and long source lists.

Click **Overall** at the top to aggregate every recorded combat in the current run, including the current fight. The metric tiles show run totals and averages calculated as total amount divided by total recorded player turns (including zero-event turns). Source bars combine matching sources across combats, with percentages of the selected run total. The left column lists combats and their totals for the selected metric; click one to inspect its turns, or use **Combat** to return to the selected encounter. Overall shows total Voidform entries, entries per recorded combat, and charges earned instead of combat-local first-entry timing.

Overall is calculated from the existing reports, so saved combat history is included after resuming. Replayed attempts already replaced by the recording system are not counted twice. Combats from before Details recording began remain unavailable. The aggregate is a derived view, never another stored combat.


The footer also shows Voidform entry count, first readiness turn, and first entry turn. Readiness means earning a charge; entering through Surrender to Madness can happen without a charge. Keeping an existing Voidform active does not count as another entry.

## What the numbers mean

- Damage dealt includes actual enemy HP lost, damage absorbed by enemy Block, and overkill from resolved hits. The lighter section of a source bar shows the absorbed portion. **Exclude blocked damage** removes that portion from totals, shares, averages and sorting without altering the saved data. Hover a damage row to see HP damage, absorbed damage, and overkill. Buffer, Intangible, and Invincible prevention are not overkill. Attacks, natural DoT ticks, Psychic Link's triggered Touch ticks, and Halo have distinct source rows.
- Damage taken counts HP actually removed from the player, after Block and damage prevention. Self-damage appears under its card or debuff. Guardian Angel's prevented damage is excluded. Healing does not subtract from damage taken; this is not net HP change.
- Insanity gained counts points actually added to the bar, after the 100-point cap and ready/active/Recharge restrictions. It excludes overflow and forced Voidform entry. Sources include card hits and explicit card gains, natural DoT ticks, Psychic Link ticks, powers, relics, and HP loss. Damage-taken sources have a separate labeled row, so an Attack and its self-damage can be distinguished. Turn, combat, Overall and archived views use the same source totals and percentages. Older combats without Insanity recording show a missing-data notice.
- Healing done counts actual HP restored to the player during recorded combat, excluding overhealing and prevented healing. Card and Power sources remain separate; Vampiric Embrace receives credit for its Touch-triggered heals. Healing never subtracts from damage taken.
- Block generated counts Block actually added after modifiers and the 999 cap. Refreshing Barrier counts only the new Block added. Retaining existing Block does not generate more Block.
- A turn includes the player turn, its end-of-turn effects, and the following enemy actions. Start-of-turn effects belong to the new player turn. Zero-event turns count toward the average. Combat-start effects belong to turn 1.
- Card sources combine copies of the same card, but separate upgraded cards. Repeated attacks, including Dragonwrath repeats, are credited to the repeated card. The ledger retains individual events, target names, and triggered-tick attribution.
- Enemy attacks are grouped by enemy type/name; power and relic effects have separate rows when their queued actions expose the source. Effects without an identifiable source are explicitly labeled **Unattributed effect** or **Unattributed player effect**. External mods that bypass the engine's damage/Block/healing methods are outside this observer's coverage.

This is a combat meter. Card reward offers, pick rates, draw/play ratios, and win rates per card are not collected in this version.

## Saved reports

Reports are local JSON files in `details/<run-id>.json`, relative to the game's working directory:

- Desktop playtest launcher: `.playtest/details/` in the repository.
- Automated checks: `.smoketest/details/`.
- Ordinary Steam/ModTheSpire launch: `details/` in the game working directory.

The file contains a schema version, run ID, seed, ascension, character, start timestamp, encounter/floor/act, outcome, turn count, Voidform timing, and individual damage/Block events. Source IDs and names are retained alongside amounts. No network upload occurs.

Snapshots are written in the background, at most every two seconds during combat, and at turn/combat/save/exit boundaries. Writes replace files atomically where supported. An abrupt process termination can lose the most recent unsaved events; normal exit flushes the writer. A write error is displayed in the report footer and logged.

The run ID is saved with the game. Resuming restores prior reports; replaying the same encounter on a saved floor replaces that encounter's previous report and increments its attempt number. This prevents save/reload attempts from inflating run totals. Old attempts are not kept as separate fights. Each new run gets a new file. Old files remain available through the main-menu browser, while the in-game top-bar panel shows the current run.

Existing saves can use the feature from their next combat after relaunch. Historical combat totals cannot be reconstructed from the old console/run logs.

## Development checks

```powershell
./scripts/build.ps1
./scripts/playtest.ps1 -SkipBuild -ObservationsOnly -Width 1920 -Height 1080 -Windowed
```

`-ObservationsOnly` runs the current focused observation and screen checks in the real patched game, using isolated test saves. It exports report screenshots to `.smoketest/card-review/` and results to `.smoketest/smoke-results.txt`. It does not simulate a full run. Unit tests cover aggregation, sorting, turn filters/averages, overkill, JSON round-tripping, and reload replacement.


For a short Overall UI review without replaying combat scenarios, use `-DetailsOverallOnly`. It renders an isolated two-combat fixture, checks the Overall/Combat buttons and combat drill-down, and exports `combat-details-overall*.png`. Unit tests verify weighted averages, source totals/percentages, current-combat updates, zero-event turns, and restored/replayed history. This mode does not simulate a run.

For a menu-only archive review, use `scripts/playtest.ps1 -SkipBuild -HistoryOnly -Windowed -Width 1920 -Height 1080`. It creates two temporary reports in the isolated smoke-test profile, reviews the menu/list/Overall/turn views, checks archive isolation and Back/Escape navigation, and removes its fixtures. It does not start a dungeon or simulate combat.

Reports recorded before the October 29 local revision retain their original HP-only damage totals. Missing absorbed damage, overkill and healing cannot be reconstructed; these reports, and Overall views containing them, show a legacy-data notice. New combats record the additional fields even when resuming an older run.

For the narrow Insanity attribution and Yogg-Saron tooltip review, run `scripts/playtest.ps1 -SkipBuild -InsanityDetailsOnly -Windowed -Width 1600 -Height 900`.

## Missing-source archive compatibility fix (0.1.4)

An engine damage owner can have no creature ID, notably the player. Before this fix, an unattributed player effect could serialize without a source ID; the archive then rejected the entire otherwise-valid run. The source constructor now assigns a stable fallback identity, and the history loader repairs older missing/blank IDs in memory without rewriting reports or inferring a card attribution. Missing source objects and other malformed data still fail validation.

Verified October 30, 2026 against the live archive using both the published 0.1.3 JAR and fixed classes: 10 visible/4 skipped became 14 visible/0 skipped. The latest A5 report retained all 31 combats and 5,055 total damage, including 2,291 for Void Torrent+ and 86 for Void Torrent. The fix passed 38 unit tests, including previously failing regression cases for new sources and old reports. No engine simulation was needed for this archive-loading fix. Published in 0.1.4 on October 30, 2026.

The full audit covered 33 user reports: 14 live-game and 19 local playtest reports. The old loader hid 4 live reports (A1, A2, A3, A5) and 9 local reports; the corrected loader lists all 33 with zero skipped. File hashes confirm that the audit and loader did not rewrite any report.

Potion actions queued through the standard potion helpers now carry a potion source; nested actions inherit it. Fairy in a Bottle direct revival healing has a scoped potion context. Recurring power effects (such as Regeneration) retain power attribution, and potion buffs do not take credit from subsequent card hits. Historical unknown effects retain their honest label rather than being guessed as a particular potion.

Validation: 38 unit tests and 17 focused real-engine assertions passed, including Fire/Explosive damage, absorbed Block and overkill, Block and Blood Potions, Fairy revival, nested action inheritance, archive reload, and combat-ending Fire Potion damage. Run `scripts/playtest.ps1 -SkipBuild -PotionDetailsOnly -Windowed -Width 1600 -Height 900` to reproduce the focused checks.
