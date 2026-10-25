# Keyword and card rework

## Implemented scope

Nine new cards: Unfurling Darkness, Dark Void, Power Infusion, Shadow Crash, Crystalline Reflection, Protected Channel, Catharsis, Searing Nightmare, and Dark Evangelism. The reward pools now contain 21 Commons, 19 Uncommons, and 11 Rares. Together with four starters and three Legendaries, there are 58 collectible cards plus generated Enter Voidform.

The authoritative definitions and upgrades are in [cards.md](../cards.md), [keywords.md](../keywords.md), and [the structured catalog](../design/cards.json).

- All three DoTs tick at player turn end before enemies act. Touch and Pain retain fixed base damage and a five-stack cap. Madness deals half its current stacks rounded up plus Mastery and loses one stack.
- Direct Attack hits generate 5 Insanity per unique DoT. Mind Spike adds 10 explicitly. Shared ready, active, and Recharge restrictions apply to every source.
- Vampiric Touch upgrades to zero energy. Mind Flay extends existing Touch/Pain after each hit. Mind Blast rewards distinct DoTs. Shadow Word: Madness applies stacks from its own actual HP damage after its hit, excluding Block, overkill, and Psychic Link.
- Penance chooses an enemy for damage or the priest for healing, ticks three times (four upgraded), and Exhausts. Healing does not inherit Attack damage modifiers. Dragonwrath preserves the chosen target and mode.
- Shadowy Insight draws for unique target DoTs, Exhausts unless upgraded, and shares a once-per-turn lock across all copies. Its stable internal ID remains `shadowpriest:Insight` for existing saves.
- Dispel Magic conditionally awards Dexterity for removing Block and, upgraded, Mastery for removing Artifact. Insidious Ire applies Vulnerable at player turn start to enemies that still have all three DoTs.
- Haste uses a yellow Dexterity icon and adds effective X, including at zero energy and on base-game X cards. It stacks with Chemical X. Explicit repeats preserve effective X without adding bonuses twice. Protected Channel uses this effective X; printed X+1 upgrade bonuses do not increase its Block instances.
- Crystalline Reflection's temporary Thorns are independent of permanent Thorns and expire without consuming Artifact.
- Tithe Evasion reduces only Shadow Mend and Shadow Word: Death HP loss. Blood Magic keeps its prior costs and damage while its replacement design remains under review; Tithe now excludes it.

Additional class relics remain drafts. The shared application/extension distinction is ready for Misery, but this build does not implement that relic.

## Artwork

New named abilities use matching WoW icons, including archived Searing Nightmare art. Shadowy Insight replaces the old Insight icon. [Warcraft-artwork.txt](../licenses/Warcraft-artwork.txt) records source URLs. Protected Channel reuses the original Shadow Committal channeling illustration; no new illustration was produced for it in this pass.

## Validation

The full in-game suite passed **545 checks**. Maven passed **12 unit tests**; catalog schema validation, rarity tallies, and whitespace checks also passed. Card renders were inspected, and long titles were reduced to fit beside their energy orbs. After the final yellow Haste icon and title adjustments, the focused suite passed **120 checks** and the final build again passed all 12 unit tests.

The focused suite drives real card plays, custom targeting, action queues, and turn transitions. It checks registration and upgrade copies, HP accounting, Artifact, Intangible, Mastery, Psychic Link, Dragonwrath, Haste/Chemical X at zero energy, off-color Whirlwind, temporary Thorns, delayed Touch, Searing Nightmare ordering, Ire timing, and Voidform/Recharge. The older suites are migrated to the new rules; replacement Madness, Insight, and Ire scenarios run as part of the full suite.

Commands:

```powershell
./scripts/build.ps1
./scripts/playtest.ps1 -SkipBuild -SmokeTest -Width 1920 -Height 1080 -Windowed
# Focus only on this rework:
./scripts/playtest.ps1 -SkipBuild -ReworkOnly -Width 1920 -Height 1080 -Windowed
```

Results are recorded in `.smoketest/smoke-results.txt`; card renders are written to `.smoketest/card-review/`. These isolated saves do not replace normal playtest runs. Mechanical regression checks do not establish full-run balance.
