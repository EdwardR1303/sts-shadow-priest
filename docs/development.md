# Shadow Priest development and testing

The project targets Java 8 and builds `target/shadowpriest.jar` against the installed game and Workshop libraries. Windows scripts keep machine paths, tools, and test saves out of Git.

## Setup and build

```powershell
./scripts/setup.ps1
./scripts/build.ps1
```

If discovery fails, pass `-SteamApps 'S:/SteamLibrary/steamapps'` to setup with your actual path. This initial script expects Slay the Spire, ModTheSpire, BaseMod, and StSLib in the same Steam library.

Setup installs a portable Eclipse Temurin Java 8 JDK and Maven 3.9.11 if needed, with checksum verification. It writes ignored local configuration in `.local/`, `.mvn/maven.config`, and `.vscode/settings.json`. Install VS Code's recommended Extension Pack for Java. Global PATH settings are unchanged.

Build runs Maven `clean verify`, including unit tests for persistent accumulation, threshold capping, single-use charge validation, combat reset, full-turn recovery, and cost edge cases. It does not deploy automatically.

## Interactive playtest

```powershell
./scripts/playtest.ps1 -SkipBuild
```

This starts only BaseMod, StSLib, and Shadow Priest in `.playtest/`, with a local game-JAR copy and separate saves/preferences. It does not change the regular ModTheSpire mod selection. Close the test game before rebuilding and relaunching.

The display defaults to 3840 x 2160 borderless, 60 FPS, VSync. Override with `-Width 1920 -Height 1080 -Windowed` if desired. Select The Shadow Priest in a Standard run. The class has its custom ten-card deck and Voidform relic; the class supplies 58 ordinary reward cards: 26 Commons, 21 Uncommons, and 11 Rares, plus three boss-only Legendaries. All reward pools are native, including four-choice boss rewards.

Combat Details is available from the purple bar-chart button in the top bar. It records damage dealt/taken, actual Block gains, and Voidform timing, with per-turn source breakdowns. Reports persist in `.playtest/details/`; see [Combat Details](details.md).

Logs are `.playtest/console.log` and `.playtest/error.log`. The isolated game may report unavailable Steam integration; local combat can still run without achievements or cloud saves. BaseMod's console can be enabled through its in-game settings for manual card and encounter setup.

## Automated combat checks

```powershell
./scripts/playtest.ps1 -SkipBuild -SmokeTest
```

This uses a separate `.smoketest/` session, starts a seeded run and controlled encounter, and exercises actual game actions. Leave the test window alone while it runs. The driver changes HP, hands, and powers in this disposable session, writes `.smoketest/smoke-results.txt`, and closes the game. `RUNNING` is not a successful result. See that directory's console/error logs if a check fails or the game crashes.

These checks need the installed game and a working graphics session, so they are separate from ordinary Maven tests. The driver only runs with the `shadowpriest.smoketest` JVM property. Normal play never automatically starts a run or manipulates combat.

Prefer focused checks for the code being changed; do not run long simulation suites for routine edits. Current focused modes include `-SetupOnly` for the accepted setup/defense/Insanity redesign `-DetailsOnly` for the combat meter, and `-BarrierOnly` for the persistent Barrier reserve. See [test results](playtest-results.md) and [Combat Details](details.md).

Older broad suites contain historical numerical assertions and require migration before being used as current balance acceptance checks. The legacy reward-only mode is:

```powershell
./scripts/playtest.ps1 -SkipBuild -CardBatchOnly -Width 1920 -Height 1080 -Windowed
```

`-CardBatchOnly` implies `-SmokeTest` and uses the same disposable session and result file. Both suites exercise base/upgraded cards, real turn boundaries, card selection, multi-hit and area damage, and lethal damage handling.

For focused sixth-batch development, use `-LatestBatchOnly` instead. It implies the isolated smoke-test modes and runs checks including startup, healing/draw cards, and Legendary mechanics. It exports base/upgraded card images, separate weapon previews, and actual combat screenshots into `.smoketest/card-review/`. The final sequence visibly equips each of the three weapons through real combat startup and pauses briefly on each. For shared combat changes, run focused checks that exercise the affected interactions; the older full suite needs its historical expectations updated first.

For focused keyword/card rework checks, use `-ReworkOnly`. It tests the new mechanics through actual game actions and exports upgraded card renders. The full suite also includes these scenarios. See [the rework notes](card-batch-07.md).

## Debug and deploy

Run `./scripts/playtest.ps1 -SkipBuild -Debug`, then use **Attach to Shadow Priest playtest** in VS Code. The JVM debugger uses port 5005.

To install into the ordinary Steam game:

```powershell
./scripts/build.ps1 -Deploy
```

This copies only `shadowpriest.jar` into the game's `mods/` folder. Start the game with mods and enable Shadow Priest plus BaseMod and StSLib. That launch uses ordinary game saves; use the isolated launcher for disposable tests.

## References and assets

- [BasicMod character template](https://github.com/Alchyr/BasicMod/tree/character), commit `412662e8dc391e6aad517704ede09f2b1f5db221`, provides adapted character scaffolding and placeholder assets.
- [BasicMod setup](https://github.com/Alchyr/BasicMod/wiki/BasicMod-Setup) documents the game and Workshop dependencies.
- [ModTheSpire launch options](https://github.com/kiooeht/ModTheSpire/wiki/Command-Line-Arguments) document the explicit mod list and skipped intro.
- The [BasicMod MIT notice](../licenses/BasicMod-MIT.txt) is preserved in source and packaged in the mod.

Game JARs, locally decompiled references, JDK, Maven, dependencies, and test saves are neither committed nor bundled into the mod.
