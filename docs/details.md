# Combat Details

Click the purple **bar-chart button in the top bar** to inspect the current or previous combat. It is available during combat, on the map, at combat rewards, and on the death/victory screens. Finish card selections before opening it. Close the report with **Close**, **Escape**, or the same top-bar button.

Choose **Damage dealt**, **Damage taken**, or **Block generated**, then **All turns** or an individual turn. Sources appear in descending order with their amount and percentage of the selected total. The three tiles show combat totals and averages per player turn. Arrow buttons move between encounters. Scroll or use the paging buttons for long fights and long source lists.

Click **Overall** at the top to aggregate every recorded combat in the current run, including the current fight. The metric tiles show run totals and averages calculated as total amount divided by total recorded player turns (including zero-event turns). Source bars combine matching sources across combats, with percentages of the selected run total. The left column lists combats and their totals for the selected metric; click one to inspect its turns, or use **Combat** to return to the selected encounter. Overall shows total Voidform entries, entries per recorded combat, and charges earned instead of combat-local first-entry timing.

Overall is calculated from the existing reports, so saved combat history is included after resuming. Replayed attempts already replaced by the recording system are not counted twice. Combats from before Details recording began remain unavailable. The aggregate is a derived view, never another stored combat.


The footer also shows Voidform entry count, first readiness turn, and first entry turn. Readiness means earning a charge; entering through Surrender to Madness can happen without a charge. Keeping an existing Voidform active does not count as another entry.

## What the numbers mean

- Damage dealt counts HP actually removed from enemies by player-owned damage. It excludes enemy Block, prevention, and overkill. Attacks, natural DoT ticks, Psychic Link's triggered Touch ticks, and Halo have distinct source rows.
- Damage taken counts HP actually removed from the player, after Block and damage prevention. Self-damage appears under its card or debuff. Guardian Angel's prevented damage is excluded. Healing does not subtract from damage taken; this is not net HP change.
- Block generated counts Block actually added after modifiers and the 999 cap. Refreshing Barrier counts only the new Block added. Retaining existing Block does not generate more Block.
- A turn includes the player turn, its end-of-turn effects, and the following enemy actions. Start-of-turn effects belong to the new player turn. Zero-event turns count toward the average. Combat-start effects belong to turn 1.
- Card sources combine copies of the same card, but separate upgraded cards. Repeated attacks, including Dragonwrath repeats, are credited to the repeated card. The ledger retains individual events, target names, and triggered-tick attribution.
- Enemy attacks are grouped by enemy type/name; power and relic effects have separate rows when their queued actions expose the source. Effects without an identifiable source are explicitly labeled **Unattributed effect** or **Unattributed player effect**. External mods that bypass the engine's damage/Block methods are outside this observer's coverage.

This is a combat meter. Card reward offers, pick rates, draw/play ratios, and win rates per card are not collected in this version.

## Saved reports

Reports are local JSON files in `details/<run-id>.json`, relative to the game's working directory:

- Desktop playtest launcher: `.playtest/details/` in the repository.
- Automated checks: `.smoketest/details/`.
- Ordinary Steam/ModTheSpire launch: `details/` in the game working directory.

The file contains a schema version, run ID, seed, ascension, character, start timestamp, encounter/floor/act, outcome, turn count, Voidform timing, and individual damage/Block events. Source IDs and names are retained alongside amounts. No network upload occurs.

Snapshots are written in the background, at most every two seconds during combat, and at turn/combat/save/exit boundaries. Writes replace files atomically where supported. An abrupt process termination can lose the most recent unsaved events; normal exit flushes the writer. A write error is displayed in the report footer and logged.

The run ID is saved with the game. Resuming restores prior reports; replaying the same encounter on a saved floor replaces that encounter's previous report and increments its attempt number. This prevents save/reload attempts from inflating run totals. Old attempts are not kept as separate fights. Each new run gets a new file. Old files remain available for analysis, while the in-game panel shows the current run.

Existing saves can use the feature from their next combat after relaunch. Historical combat totals cannot be reconstructed from the old console/run logs.

## Development checks

```powershell
./scripts/build.ps1
./scripts/playtest.ps1 -SkipBuild -DetailsOnly -Width 1920 -Height 1080 -Windowed
```

`-DetailsOnly` runs focused observation and screen checks in the real patched game, using isolated test saves. It exports report screenshots to `.smoketest/card-review/` and results to `.smoketest/smoke-results.txt`. It does not simulate a full run. Unit tests cover aggregation, sorting, turn filters/averages, overkill, JSON round-tripping, and reload replacement.


For a short Overall UI review without replaying combat scenarios, use `-DetailsOverallOnly`. It renders an isolated two-combat fixture, checks the Overall/Combat buttons and combat drill-down, and exports `combat-details-overall*.png`. Unit tests verify weighted averages, source totals/percentages, current-combat updates, zero-event turns, and restored/replayed history. This mode does not simulate a run.
