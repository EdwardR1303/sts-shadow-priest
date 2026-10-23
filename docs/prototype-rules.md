# Shadow Priest prototype rules

Version 0.1.0 uses these playtest defaults. The owner authorized the first-playable milestone and suggested defaults on October 23, 2026. Edge-case choices and upgrade numbers remain adjustable after testing.

## Character and deck

Template defaults: 70 HP, 3 energy, 5 cards drawn each turn, 99 gold, no orb slots.

| Card | Copies | Type | Cost | Effect | Provisional upgrade |
| --- | --- | --- | --- | --- | --- |
| Mind Spike | 4 | Attack | 1 | Deal 6 damage. | 9 damage |
| Power Word: Shield | 4 | Skill | 1 | Gain 5 Block. | 8 Block |
| Vampiric Touch | 1 | Skill | 1 | Touch: 5 damage per tick for 3 turns. | 4 turns |
| Shadow Word: Pain | 1 | Skill | 1 | Pain: 5 damage per tick for 3 turns. | 4 turns |
| Enter Voidform | Generated | Skill | 0 | Retain. Enter Voidform. Exhaust. | Cannot upgrade |

Simple upgrades keep campfires and events usable during testing. Neither DoT heals. Shop and reward cards temporarily use the Ironclad pool so the prototype can continue beyond the first fight. These are not the final class rewards.

## Damage over time

- Touch and Pain coexist with independent duration counters.
- Each deals 5 HP-loss damage before the enemy acts, then loses one remaining turn. The first tick occurs on the next enemy turn after application, not immediately on play.
- Reapplication sets duration to the played card's duration. It does not stack damage or duration or trigger a tick. A base application can refresh an upgraded four-turn effect to three turns.
- Damage bypasses Block and ignores Strength, Weak, Vulnerable, and Voidform. Enemy damage hooks, including Intangible, still apply.
- Artifact blocks application or refresh.
- Only Touch and Pain count toward the unique-DoT total. Poison and other mods' debuffs do not count.

## Insanity and activation

Each player-owned normal attack damage event gains 1 Insanity per unique DoT on its target. Each hit of a multihit attack counts independently, and multi-target attacks use each enemy's own DoTs. Fully blocked and zero-damage normal hits count. Killing blows count before debuff cleanup. HP-loss and retaliation damage do not count.

Insanity persists between turns and caps at 10. There is no random generation roll; draw order affects setup. At 10, one activation becomes ready and generates Enter Voidform. A full hand defers delivery until a slot opens. No further Insanity is gained while ready or active.

The card is retained. If discarded, it can be drawn again. If the unused card is lost entirely, the ready charge can deliver a replacement. Each charge has a serial number: copied or recovered activation cards cannot spend a used charge or an unrelated later charge.

Playing the card enters Voidform and exhausts it. The form lasts until the player's turn ends:

- Attack energy costs are reduced by 1, with a floor of zero. X-cost and other negative costs are unchanged. The discount is an overlay; it does not overwrite existing temporary cost changes.
- Attack-card damage is multiplied by 1.5 in the final outgoing-damage calculation. Normal final rounding and target defenses still apply.
- Attacks drawn or generated during the form receive its benefits too.

The attack that earns the card does not itself activate Voidform. Turn-end expiry and combat completion reset Insanity to zero. The generated activation is not part of the permanent deck or reward pool.

## Presentation

The relic counter and a labeled purple bar beneath the energy orb show building, ready, and active states. Touch and Pain display their original World of Warcraft spell icons, with remaining turns overlaid by the game. The combat character uses generated Transcendence-inspired artwork, with a shadow tint, subtle idle bob, and pulsing purple glow while Voidform is active. Character-select, rest-site, death, card, and relic images remain BasicMod placeholders. See [character artwork](character-art.md) for the asset and generation prompt. The launcher defaults to 4K borderless as requested.
