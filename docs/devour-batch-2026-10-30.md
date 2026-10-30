# Devour and conditional highlights — local playtest batch

Accepted October 30, 2026. Approved for release as 0.1.5 following the first local playthrough, reported as more combo-focused.

## Cards and keyword

Devour exhausts one actual hand Curse for the printed bonus. The card still works without a Curse. Multiple Curses open a selection screen; one Curse pays for one activation, regardless of Haste. Normal exhaust reactions, including Void Vengeance, still apply.

| Card | Change |
| --- | --- |
| Tentacle Slam | Devour refunds only the Energy actually spent on this play, including cost discounts/free plays. Keeps 8/10 damage. |
| Unfurling Darkness | Devour applies 3 Touch to all other enemies. |
| Mind Trauma | Devour adds 6 Madness to the same application, with one Artifact check. |
| Shadow Mend | Devour prevents this cast's debt; existing debt stays. |
| Purify Disease | Devour grants 1 Artifact; upgrade grants Retain. No unconditional Artifact. |
| Focused Will | Devour adds 7 base Block to one combined grant, with Dexterity/Frail applied once. |
| Twin Suns | Devour grants 2 Haste independently of enemy Strength. |
| Void Ray | Devour wording; preserves existing post-hit permanent growth and lethal-hit handling. |
| Shadow Word: Madness | 9/13 damage; generates Mind Decay in hand before the hit so creation cannot be skipped by lethal damage. |
| Madness Overwhelming | 10/15 initial Madness, plus the unchanged 5/8 per Power played; generates Mind Decay in discard. |
| Surrender to Madness | Zero cost, two completed Voidforms required, permanent normal Voidform, 2/3 immediate and start-of-turn Strength/Mastery, unavoidable death after five turn endings. |

The resulting card package has seven Curse generators and eight consumers. Existing Blood Magic, Dark Void, Mental Fortitude, Malady of the Mind, and Mental Decay generation remains unchanged. Generated Curses are combat-only.

## Conditional highlights

Gold borders and relevant gold clauses cover Devour, Bulwark, Piety, Dispel Magic, Mass Dispel, Purify Disease, Twin Suns, Shadow Word: Death, and Surrender. Conditions update as the hand, HP, buffs, target, and Voidform progress change. Target-dependent text requires a hovered enemy; an untargeted border can signal that at least one enemy qualifies.

Shadow Word: Death predicts direct-hit lethal using the damage calculation, repetitions/Haste, Block, Intangible, Buffer, and Invincible. It does not include unrelated DoTs, Psychic Link, or random Dragonwrath repeats. Changing Block from Malleable is conservatively excluded from the lethal signal. Arbitrary third-party damage reactions are not simulated.

## Validation

- Incremental Maven build and 39 unit tests passed, including a new completed-Voidform lifecycle regression.
- `scripts/playtest.ps1 -DevourOnly`: 83 checks passed in the patched game, including card effects, multiple-Curse selection, free/discounted refunds, highlights, Surrender's ordinary and forced-use lock, stat timing, and unavoidable fifth-turn death despite Fairy/Lizard Tail/Guardian Angel.
- `scripts/playtest.ps1 -DevourVictoryOnly`: 82 checks passed, including a real final-turn natural-DoT victory that cancels the deadline.
- Rendered card images inspected for gold clauses, readable descriptions, and Surrender's progress counter. Renders are in the ignored `.smoketest/card-review` directory.
- Card catalog validates against its JSON schema; Devour is now an allowed keyword.

Results are saved locally in `.local/devour-deadline-results.txt` and `.local/devour-victory-results.txt`. These focused checks establish behavior, not run balance. The first local playthrough received positive feedback; ongoing run balance testing continues. The older batch smoke suites contain historical balance expectations; the focused Devour switches are the current regression checks for these revisions.
