# Shadow Priest for Slay the Spire

A custom Slay the Spire 1 character inspired by World of Warcraft's Shadow Priest archetype.

Survive while applying distinct damage-over-time debuffs and attacking afflicted enemies to generate Insanity. At 100 Insanity, gain a retained activation card and choose when to unleash a temporary Voidform with cheaper, stronger Attack cards.

The [Voidform activation card](docs/voidform-activation.md) costs 0 energy, has Retain, and exhausts when played. The prototype holds one charge at a time.

## Project status

Version 0.1.0 is a playable prototype for VS Code: a selectable character, four starters, 42 ordinary reward cards, three Legendary equipment cards, Mastery-scaled DoTs with a five-tick cap, an Insanity bar beneath the energy orb, and on-demand Voidform. The combat priest wears Transcendence-inspired regalia and glows purple during Voidform. All 49 collectible cards and generated Enter Voidform have dedicated portraits: existing abilities use framed WoW icons, while invented abilities use original thematic artwork. Touch/Pain use matching debuff icons, and the Voidform relic uses a transparent cutout adapted from its WoW icon. Other unfinished presentation still uses placeholder assets. Ordinary Voidform enforces a full recovery turn with no Insanity gain. Surrender to Madness is a rare Skill that grants combat-long Voidform with a separate, preventable or cleanseable double-Attack-damage penalty. All reward pools are native: twenty Commons, fourteen Uncommons, and eight Rares. The fifth batch adds defensive Attacks, cleanse/dispel utility, dodge, stun, per-hit damage scaling, and persistent stats earned during Voidform. The sixth batch adds healing debt, typed draw, hand retention, and three automatically equipped Legendaries with boss-only acquisition and permanent uniqueness. Full-run balance remains future work.

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

- [Next design priorities](docs/next-design-priorities.md): focused gaps and a full-run playtest checklist.
- [Sixth card batch](docs/card-batch-06.md): healing/draw reworks, automatic Legendary equipment, and artwork.
- [Legendary rules](docs/legendary-cards.md): boss rewards, uniqueness, and combat activation.
- [Relic designs](relics.md): plain-English class relic ideas, grouped by rarity, with a quick template and `?` review markers.
- [Card design guide](docs/card-design-guide.md): start designing cards in [the JSON catalog](design/cards.json), with [a reusable template](design/card-template.json), complete upgrades, and VS Code validation.
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
