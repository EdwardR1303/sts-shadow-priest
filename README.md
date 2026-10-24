# Shadow Priest for Slay the Spire

A custom Slay the Spire 1 character inspired by World of Warcraft's Shadow Priest archetype.

Survive while applying distinct damage-over-time debuffs and attacking afflicted enemies to generate Insanity. At 10 Insanity, gain a retained activation card and choose when to unleash a temporary Voidform with cheaper, stronger Attack cards.

The [Voidform activation card](docs/voidform-activation.md) costs 0 energy, has Retain, and exhausts when played. The prototype holds one charge at a time.

## Project status

Version 0.1.0 is a playable prototype for VS Code: a selectable character, four starters, 23 reward cards, fixed-damage DoTs with a five-tick cap, an Insanity bar beneath the energy orb, and on-demand Voidform. The combat priest wears Transcendence-inspired regalia and glows purple during Voidform. Starter cards use framed retail WoW ability icons, Touch/Pain use matching debuff icons, and the Voidform relic uses a transparent cutout adapted from its WoW icon. Fortitude, Madness, and Halo also use their WoW icons; Reaping Madness has original thematic artwork. Earlier reward-card portraits and other unfinished artwork still use placeholders. Voidform now enforces a full recovery turn with no Insanity gain. All reward pools are native: ten Commons, nine Uncommons, and four Rares. Void Torrent supplies the fourth rare, so the temporary Ironclad supplements are no longer used. A broader rare pool and full-run balance pass remain future work.

## Build and play on Windows

Install the Steam game and subscribe to ModTheSpire, BaseMod, and StSLib, then run:

```powershell
./scripts/setup.ps1
./scripts/build.ps1
./scripts/playtest.ps1 -SkipBuild
```

Setup discovers the game and installs checksum-verified portable Java 8 and Maven under local app data, without global PATH changes. Playtesting defaults to **3840 x 2160 borderless** and uses separate saves in `.playtest/`. Choose **The Shadow Priest** in a Standard run.

For a smaller window, add `-Width 1920 -Height 1080 -Windowed`. VS Code also has setup, build, playtest, and deploy tasks. See [development and testing](docs/development.md) for details.

## Design documents

- [Relic designs](relics.md): plain-English class relic ideas, grouped by rarity, with a quick template and `?` review markers.
- [Card design guide](docs/card-design-guide.md): start designing cards in [the JSON catalog](design/cards.json), with [a reusable template](design/card-template.json), complete upgrades, and VS Code validation.
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
