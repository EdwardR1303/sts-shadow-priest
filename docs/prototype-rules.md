# Shadow Priest prototype rules

Version 0.1.0 uses these playtest defaults, updated by the [keyword and card rework](card-batch-07.md). The owner authorized the first-playable milestone and suggested defaults on October 23, 2026. Edge-case choices and upgrade numbers remain adjustable after testing.

## Character and deck

Template defaults: 70 HP, 3 energy, 5 cards drawn each turn, 99 gold, no orb slots.

| Card | Copies | Type | Cost | Effect | Provisional upgrade |
| --- | --- | --- | --- | --- | --- |
| Mind Spike | 4 | Attack | 1 | Deal 6 damage. Gain 10 Insanity. | 9 damage; same Insanity |
| Power Word: Shield | 4 | Skill | 1 | Gain 5 Block. | 8 Block |
| Vampiric Touch | 1 | Skill | 1 | Touch: 5 damage per tick; apply 3 stacks. | Cost 0; same 3 stacks and base 5 damage |
| Shadow Word: Pain | 1 | Skill | 1 | Pain: 3 damage per tick; apply 5 stacks. | Cost 0; base 3 damage |
| Enter Voidform | Generated | Skill | 0 | Retain. Enter Voidform. Exhaust. | Cannot upgrade |

Neither DoT inherently heals; Vampiric Embrace rewards damaging Touch ticks with healing. Played DoT cards return to hand after end-turn discard; unplayed cards discard normally. The class supplies 21 Commons, 19 Uncommons, and 11 Rares. All reward pools are native; the fourth rare allows four distinct boss choices without Ironclad supplements. See [the first reward-card batch](card-batch-01.md) for the first 12 rewards, and [the second batch](card-batch-02.md) for the four additions and recovery rules. [The third batch](card-batch-03.md) adds four more cards and supersedes the old DoT refresh and upgrade values. [The fourth batch](card-batch-04.md) adds Void Bolt, Void Volley, and Void Torrent.

## Damage over time

- Touch and Pain coexist with independent duration counters.
- Touch deals base 5 damage and applies 3 stacks; Pain deals base 3 damage and applies 5 stacks. Both upgrade to cost 0. Natural ticks occur at player turn end before enemies act, then remove one stack. Applications do not tick immediately. Each uninterrupted starter application totals 15 damage before Mastery.
- Applications add remaining ticks, capped at 5, without an immediate tick. Base damage is fixed by debuff type; current Mastery modifies each tick, floored at zero. All duration extensions, including Mind Flay: Insanity, obey the same cap.
- Damage bypasses Block and ignores Strength, Weak, Vulnerable, and Voidform. Enemy damage hooks, including Intangible, still apply.
- Artifact blocks application or refresh.
- Touch, Pain, and positive Madness stacks count toward the unique-DoT total. Madness counts once, ticks for half its stacks rounded up plus Mastery, and removes one stack. It has no cap; effects must explicitly apply Madness to add stacks. Halo is a player aura, not a DoT. Poison and other mods' debuffs do not count.

## Insanity and activation

Each player-owned normal attack damage event gains 5 Insanity per unique DoT on its target. Each hit of a multihit attack counts independently, and multi-target attacks use each enemy's own DoTs. Fully blocked and zero-damage normal hits count. Killing blows count before debuff cleanup. Outgoing HP-loss and retaliation damage do not count toward this attack-based generation. Mind Spike grants an additional 10 explicit Insanity after its hit, subject to all shared gain restrictions.

Every incoming damage event that removes player HP in combat grants 5 Insanity after mitigation, including Blood Magic, each Shadow Word: Death backlash event, and Shadow Mend's combined debt tick. Fully blocked, dodged, or prevented damage grants nothing; damage amount does not change the gain. Out-of-combat HP loss does not count. The same ready, active, and Recharge restrictions apply.

Insanity persists between turns and caps at 100. There is no random generation roll; draw order affects setup. At 100, one activation becomes ready and generates Enter Voidform. A full hand defers delivery until a slot opens. No further Insanity is gained while ready, active, or recovering. Reaping Madness provides 10/20 Insanity per copy at player turn end under these same restrictions.

The card is retained. If discarded, it can be drawn again. If the unused card is lost entirely, the ready charge can deliver a replacement. Each charge has a serial number: copied or recovered activation cards cannot spend a used charge or an unrelated later charge.

Playing the card enters Voidform and exhausts it. The form lasts until the player's turn ends:

- Attack energy costs are reduced by 1, with a floor of zero. X-cost and other negative costs are unchanged. The discount is an overlay; it does not overwrite existing temporary cost changes.
- Attack-card damage is multiplied by 1.5 in the final outgoing-damage calculation. Normal final rounding and target defenses still apply.
- Attacks drawn or generated during the form receive its benefits too.

The attack that earns the card does not itself activate Voidform. Turn-end expiry and combat completion reset Insanity to zero. The entire turn after entering Voidform blocks all Insanity gain, including end-of-turn effects. Generation resumes at the following player turn start. Combat reset clears recovery. The generated activation is not part of the permanent deck or reward pool.

Insidious Ire applies Vulnerable at player turn start to enemies with all three DoTs. External access to Vulnerable is also allowed. Vampiric Embrace copies stack uncapped healing from Touch events that actually remove enemy HP, including Psychic Link ticks. See the third batch for exact spread, targeting, and healing rules.

## Surrender to Madness

This rare 3/2-energy Skill exhausts and makes Voidform last for the rest of combat, bypassing the threshold and recovery. It extends an already active form without stacking its benefits, invalidates ready charges, and prevents additional Surrender plays that combat. Normal Attack damage received doubles before Block through a separate debuff: Artifact can prevent it and Orange Pellets can remove it without ending Voidform. HP-loss and non-Attack damage remain unchanged. Combat cleanup resets the special state. It also works without the starter relic. See [the complete rules](surrender-to-madness.md).

## Mastery and fifth-batch cards

Mastery changes every DoT tick by one per point and can be negative. Zero-damage natural ticks still consume a stack; they cannot heal Embrace. Halo is unaffected. Mastery uses a purple Strength icon. Penance targets the priest for healing or an enemy for damage and Exhausts. Atonement grants Block for unique DoTs, Purify/Dispel remove specified effects, Body and Soul rolls a nonstacking per-hit dodge, Ire applies conditional Vulnerable, Scream stuns without stopping DoT ticks, and Committal grants lasting stats after cards played in Voidform. Historical [fifth-batch notes](card-batch-05.md) describe earlier Penance and Ire versions; [cards.md](../cards.md) gives current rules.

Haste adds effective X to all X-cost cards, including at zero energy and with Chemical X. Protected Channel uses effective X for its Block instances. [The seventh batch](card-batch-07.md) describes the new applications, defensive cards, and updated draw mechanics.

## Sixth batch and equipment

The [sixth batch](card-batch-06.md) implements the Flash Heal / Shadow Mend split, Blood Magic, Void Shield, Insight, Piety, Barrier, Tithe Evasion, Serendipity, Mental Fortitude, and Mind Soothe. Three [Legendary equipment cards](legendary-cards.md) can appear in Act 1/2 boss rewards. Only one may occupy the permanent deck; it equips automatically before the opening hand without being drawn or played.

## Presentation

The relic counter and a labeled purple bar beneath the energy orb show building, ready, active, recovering, and SURRENDERED states. Lasting Voidform keeps the bar filled and the character glow active. Touch and Pain display their original World of Warcraft spell icons, with remaining turns overlaid by the game. All four starter card portraits use the original retail ability icons with a shared violet-and-gold frame. The combat character uses generated Transcendence-inspired artwork, with a shadow tint, subtle idle bob, and pulsing purple glow while Voidform is active. The Voidform relic uses a transparent, irregular cutout adapted from its WoW icon. Character-select, rest-site, death, and Enter Voidform images remain BasicMod placeholders. See [character artwork](character-art.md), [starter card artwork](starter-card-art.md), and [relic artwork](relic-art.md) for assets and generation prompts. The launcher defaults to 4K borderless as requested.
