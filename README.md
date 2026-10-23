# Shadow Priest for Slay the Spire

A custom Slay the Spire 1 character inspired by World of Warcraft's Shadow Priest archetype.

Apply distinct damage-over-time debuffs, attack afflicted enemies to generate Insanity, and reach 10 Insanity to trigger a temporary Voidform with cheaper, stronger Attack cards.

Under consideration: replace automatic activation with a zero-cost retained card generated at 10 Insanity, so the player chooses when to enter Voidform. See the [activation card proposal](docs/voidform-activation-proposal.md).

## Project status

Design stage. This repository contains the initial design and decision records; there is no playable mod or build setup yet. VS Code is the intended editor. The character and project names are working names.

## Design documents

- [Character design](docs/character-design.md): starting cards, DoTs, Insanity, and the starting relic.
- [Decision log](docs/decisions.md): accepted choices and the earlier concepts they replace.
- [Open questions](docs/open-questions.md): rules that still need decisions, with suggestions clearly marked.
- [Prototype plan](docs/prototype-plan.md): proposed implementation order and playtest questions.

## Recording changes

Update the character design when a choice is accepted, record the change in the decision log, and resolve the corresponding open question. Suggestions remain proposals until accepted. Initial values are a playtest baseline and may change after testing.

## Development references

Candidate starting points for the later implementation:

- [BasicMod template and tutorial](https://github.com/Alchyr/BasicMod)
- [Adding a character](https://github.com/Alchyr/BasicMod/wiki/Adding-a-Character)
- [ModTheSpire](https://github.com/kiooeht/ModTheSpire)
- [BaseMod](https://github.com/daviscook477/BaseMod)
- [StSLib](https://github.com/kiooeht/StSLib)

Template choice and toolchain versions have not been selected.
