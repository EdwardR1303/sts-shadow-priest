# Shadow Priest for Slay the Spire

A custom Slay the Spire 1 character inspired by World of Warcraft's Shadow Priest archetype.

Survive while applying distinct damage-over-time debuffs and attacking afflicted enemies to generate Insanity. At 10 Insanity, gain a retained activation card and choose when to unleash a temporary Voidform with cheaper, stronger Attack cards.

The [Voidform activation card](docs/voidform-activation.md) costs 0 energy, has Retain, and exhausts when played. The prototype holds one charge at a time.

## Project status

Version 0.1.0 is a starting-deck prototype for VS Code: a selectable character, four starter cards, separate refreshing DoTs, an Insanity bar beneath the energy orb, and on-demand Voidform. The combat priest wears Transcendence-inspired regalia and glows purple during Voidform. Touch and Pain use their World of Warcraft debuff icons; other artwork uses BasicMod placeholders. Rewards temporarily use the Ironclad pool; a complete Shadow Priest reward pool and balance pass are future work.

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

- [Character design](docs/character-design.md): starting cards, DoTs, Insanity, and the starting relic.
- [Decision log](docs/decisions.md): accepted choices and the earlier concepts they replace.
- [Open questions](docs/open-questions.md): rules that still need decisions, with suggestions clearly marked.
- [Prototype plan](docs/prototype-plan.md): completed milestone and remaining playtest questions.
- [Prototype rules](docs/prototype-rules.md): exact mechanics and provisional defaults implemented in 0.1.0.
- [Character artwork](docs/character-art.md): generated combat sprite, Voidform presentation, and source prompt.

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

The two World of Warcraft debuff icons are credited separately in [Warcraft artwork](licenses/Warcraft-artwork.txt), also included in the JAR.
