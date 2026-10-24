# Shadow Priest prototype rules

Version 0.1.0 uses these playtest defaults. The owner authorized the first-playable milestone and suggested defaults on October 23, 2026. Edge-case choices and upgrade numbers remain adjustable after testing.

## Character and deck

Template defaults: 70 HP, 3 energy, 5 cards drawn each turn, 99 gold, no orb slots.

| Card | Copies | Type | Cost | Effect | Provisional upgrade |
| --- | --- | --- | --- | --- | --- |
| Mind Spike | 4 | Attack | 1 | Deal 6 damage. | 9 damage |
| Power Word: Shield | 4 | Skill | 1 | Gain 5 Block. | 8 Block |
| Vampiric Touch | 1 | Skill | 1 | Touch: 5 damage per tick; apply 3 stacks. | Apply 4 stacks; fixed 5 damage |
| Shadow Word: Pain | 1 | Skill | 1 | Pain: 3 damage per tick; apply 5 stacks. | Cost 0; fixed 3 damage |
| Enter Voidform | Generated | Skill | 0 | Retain. Enter Voidform. Exhaust. | Cannot upgrade |

Neither DoT inherently heals; Vampiric Embrace rewards damaging Touch ticks with healing. Played DoT cards return to hand after end-turn discard; unplayed cards discard normally. The class supplies ten Commons, nine Uncommons, and four Rares. All reward pools are native; the fourth rare allows four distinct boss choices without Ironclad supplements. See [the first reward-card batch](card-batch-01.md) for the first 12 rewards, and [the second batch](card-batch-02.md) for the four additions and recovery rules. [The third batch](card-batch-03.md) adds four more cards and supersedes the old DoT refresh and upgrade values. [The fourth batch](card-batch-04.md) adds Void Bolt, Void Volley, and Void Torrent.

## Damage over time

- Touch and Pain coexist with independent duration counters.
- Touch always deals 5 HP-loss damage and applies 3/4 stacks; Pain always deals 3 and applies 5 stacks, with its upgrade costing 0. Each ticks before the enemy acts, then loses one remaining turn. The first tick is on the next enemy turn, not immediately on play. Each uninterrupted base application totals 15 damage; Touch+ totals 20 and Pain+ totals 15 for zero energy.
- Applications add remaining ticks, capped at 5, without an immediate tick. Damage is fixed by debuff type. All duration extensions, including Mind Flay: Insanity, obey the same cap.
- Damage bypasses Block and ignores Strength, Weak, Vulnerable, and Voidform. Enemy damage hooks, including Intangible, still apply.
- Artifact blocks application or refresh.
- Touch, Pain, and positive Madness stacks count toward the unique-DoT total. Madness counts once, never ticks or decays, and has no duration to extend. Halo is a player aura, not a DoT. Poison and other mods' debuffs do not count.

## Insanity and activation

Each player-owned normal attack damage event gains 1 Insanity per unique DoT on its target. Each hit of a multihit attack counts independently, and multi-target attacks use each enemy's own DoTs. Fully blocked and zero-damage normal hits count. Killing blows count before debuff cleanup. HP-loss and retaliation damage do not count.

Insanity persists between turns and caps at 10. There is no random generation roll; draw order affects setup. At 10, one activation becomes ready and generates Enter Voidform. A full hand defers delivery until a slot opens. No further Insanity is gained while ready, active, or recovering. Reaping Madness provides 1/2 Insanity per copy at player turn end under these same restrictions.

The card is retained. If discarded, it can be drawn again. If the unused card is lost entirely, the ready charge can deliver a replacement. Each charge has a serial number: copied or recovered activation cards cannot spend a used charge or an unrelated later charge.

Playing the card enters Voidform and exhausts it. The form lasts until the player's turn ends:

- Attack energy costs are reduced by 1, with a floor of zero. X-cost and other negative costs are unchanged. The discount is an overlay; it does not overwrite existing temporary cost changes.
- Attack-card damage is multiplied by 1.5 in the final outgoing-damage calculation. Normal final rounding and target defenses still apply.
- Attacks drawn or generated during the form receive its benefits too.

The attack that earns the card does not itself activate Voidform. Turn-end expiry and combat completion reset Insanity to zero. The entire turn after entering Voidform blocks all Insanity gain, including end-of-turn effects. Generation resumes at the following player turn start. Combat reset clears recovery. The generated activation is not part of the permanent deck or reward pool.

Native class cards do not apply Vulnerable; external access is allowed. Vampiric Embrace copies stack uncapped healing from Touch events that actually remove enemy HP, including Psychic Link ticks. See the third batch for exact spread, targeting, and healing rules.

## Presentation

The relic counter and a labeled purple bar beneath the energy orb show building, ready, active, and recovering states. Touch and Pain display their original World of Warcraft spell icons, with remaining turns overlaid by the game. All four starter card portraits use the original retail ability icons with a shared violet-and-gold frame. The combat character uses generated Transcendence-inspired artwork, with a shadow tint, subtle idle bob, and pulsing purple glow while Voidform is active. The Voidform relic uses a transparent, irregular cutout adapted from its WoW icon. Character-select, rest-site, death, and Enter Voidform images remain BasicMod placeholders. See [character artwork](character-art.md), [starter card artwork](starter-card-art.md), and [relic artwork](relic-art.md) for assets and generation prompts. The launcher defaults to 4K borderless as requested.
