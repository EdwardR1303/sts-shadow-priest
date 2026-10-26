# Shadow Priest for Slay the Spire

A custom Slay the Spire 1 character inspired by World of Warcraft's Shadow Priest archetype.

Survive while applying distinct damage-over-time debuffs. Attack hits and DoT ticks generate Insanity. At 100 Insanity, gain a retained activation card and choose when to unleash a temporary Voidform with cheaper, stronger Attack cards.

The [Voidform activation card](docs/voidform-activation.md) costs 0 energy, has Retain, and exhausts when played. The prototype holds one charge at a time.

## Project status

Version 0.1.0 is a playable prototype with 65 collectible cards: four starters, 58 ordinary reward cards (26 Common, 21 Uncommon, 11 Rare), and three automatically equipped Legendaries. The latest pass adds seven cards, more common DoT applications, defensive engines, a setup tutor, and revised Insanity generation. Touch and Pain cap at five stacks; Madness has no cap and ticks for half its stacks rounded up. All three tick at player turn end and scale with Mastery. Direct Attack hits generate 10 Insanity independently of DoTs; each natural or triggered DoT tick grants 1. Mind Spike also grants 10 explicitly. Each damage event that removes the priest's HP in combat grants another 5 Insanity, including self-damage.

The priest wears Transcendence-inspired regalia, glows purple in Voidform, and displays Insanity beneath the energy orb. Card portraits use matching WoW icons or thematic original art; Protected Channel currently reuses the original Shadow Committal illustration. Ordinary Voidform ends with a full Recharge turn. Surrender to Madness can sustain it for combat at the cost of a preventable or cleanseable damage penalty. Seven class reward relics are implemented, including Improved Voidform's carryover until three Attack plays. The three relics marked `?` remain drafts. Full-run balance testing remains necessary. See the [setup and defense revision](docs/card-batch-08.md) and [relic implementation](docs/relic-implementation.md) for implementation and validation details.

## Build and play on Windows

Install the Steam game and subscribe to ModTheSpire, BaseMod, and StSLib, then run:

```powershell
./scripts/setup.ps1
./scripts/build.ps1
./scripts/playtest.ps1 -SkipBuild
```

Setup discovers the game and installs checksum-verified portable Java 8 and Maven under local app data, without global PATH changes. Playtesting defaults to **3840 x 2160 borderless** and uses separate saves in `.playtest/`. Choose **The Shadow Priest** in a Standard run.

For a smaller window, add `-Width 1920 -Height 1080 -Windowed`. VS Code also has setup, build, playtest, and deploy tasks. See [development and testing](docs/development.md) for details.

For a desktop launcher, run `./scripts/install-desktop-shortcut.ps1` once, then double-click **Shadow Priest Playtest**. It rebuilds the current source and launches in 4K borderless without a terminal window. Existing playtest saves are kept, and another click will not start a second normal game instance. Startup failures display an error with the log location. Launcher logs live in `.local/launcher-logs/`; prior game logs are preserved under `.playtest/logs/`, and run summaries remain in `.playtest/runs/`.

## Design documents

- [Setup and defense revision](docs/card-batch-08.md): seven new cards, per-hit/tick Insanity, non-stacking Atonement, Searing Nightmare's Power redesign, and focused validation.
- [Keyword and card rework](docs/card-batch-07.md): nine new cards, Haste, decaying Madness, revised Insanity, and regression coverage.
- [Next design priorities](docs/next-design-priorities.md): focused gaps and a full-run playtest checklist.
- [Sixth card batch](docs/card-batch-06.md): healing/draw reworks, automatic Legendary equipment, and artwork.
- [Legendary rules](docs/legendary-cards.md): boss rewards, uniqueness, and combat activation.
- [Relic designs](relics.md): plain-English class relic ideas, grouped by rarity, with a quick template and `?` review markers.
- [Card design guide](docs/card-design-guide.md): start designing cards in [the JSON catalog](design/cards.json), with [a reusable template](design/card-template.json), complete upgrades, and VS Code validation.
- [Barrier reserve](docs/barrier-reserve.md): persistent Block that absorbs damage first and allows ordinary Block gains.
- [Combat Details](docs/details.md): top-bar damage/Block meter, Overall run aggregates, per-turn source breakdowns, Voidform timing, and persistent JSON reports.
- [Surrender to Madness](docs/surrender-to-madness.md): permanent combat Voidform, a removable exposure debuff, and Artifact/Orange Pellets interactions.
- [Fifth reward-card batch](docs/card-batch-05.md): eight cards, positive/negative Mastery, dodge, stun, and artwork sources.
- [Fourth reward-card batch](docs/card-batch-04.md): Void Bolt, Void Volley, escalating Void Torrent, and fully native rare rewards.
- [Third reward-card batch](docs/card-batch-03.md): capped fixed-damage DoTs, Tentacle Slam, additive Void Blast, Mass Dispel, and stacking Embrace.
- [Second reward-card batch](docs/card-batch-02.md): Fortitude, Reaping Madness, Madness, Halo, and Voidform recovery; [artwork and source prompt](docs/card-batch-02-art.md).
- [First reward-card batch](docs/card-batch-01.md): implemented designs, upgrades, and interaction rules.
- [Character design](docs/character-design.md): starting cards, DoTs, Insanity, and the starting relic.
- [Decision log](docs/decisions.md): accepted choices and the earlier concepts they replace.
- [Open questions](docs/open-questions.md): rules that still need decisions, with suggestions clearly marked.
- [Prototype plan](docs/prototype-plan.md): completed milestone and remaining playtest questions.
- [Prototype rules](docs/prototype-rules.md): exact mechanics and provisional defaults implemented in 0.1.0.
- [Legendary and character art pass](docs/art-pass-02.md): weapon paintings, skull selection button, rear-view campfire animation, and Heart-victory scene.
- [Character artwork](docs/character-art.md): generated combat sprite, Voidform presentation, and source prompt.
- [Card artwork completion](docs/card-art-completion.md): remaining portraits, sources, and first-run log findings.
- [Starter card artwork](docs/starter-card-art.md): original ability icons, shared frame, and generation prompt.
- [Voidform relic artwork](docs/relic-art.md): transparent icon adaptation and generation prompt.

## Recording changes

Update the character design when a choice is accepted, record the change in the decision log, and resolve the corresponding open question. Suggestions remain proposals until accepted. Initial values are a playtest baseline and may change after testing.

## Development references

Implementation references:

- [BasicMod template and tutorial](https://github.com/Alchyr/BasicMod)
- [Adding a character](https://github.com/Alchyr/BasicMod/wiki/Adding-a-Character)
- [ModTheSpire](https://github.com/kiooeht/ModTheSpire)
- [BaseMod](https://github.com/daviscook477/BaseMod)
- [StSLib](https://github.com/kiooeht/StSLib)

Character scaffolding and placeholder assets are adapted from BasicMod's `character` branch at `412662e8dc391e6aad517704ede09f2b1f5db221`. The [MIT notice](licenses/BasicMod-MIT.txt) is preserved and included in the JAR. Game files and dependency JARs are not committed or bundled into the mod.

The World of Warcraft ability icons are credited separately in [Warcraft artwork](licenses/Warcraft-artwork.txt), also included in the JAR.
