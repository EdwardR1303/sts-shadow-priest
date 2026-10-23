# Shadow Priest character design

The character combines distinct damage-over-time effects with direct attacks. DoTs provide sustained damage and make each subsequent direct damage source generate more Insanity. At 10 Insanity, the starting relic activates Voidform for the remainder of the turn.

The rules below capture the owner's design and confirmed clarifications on October 23, 2026. Unspecified details are tracked in [Open questions](open-questions.md). An [on-demand activation card](voidform-activation-proposal.md) is proposed as a replacement for automatic Voidform activation.

## Starting deck

| Card | Role | Energy cost | Base effect | Copies |
| --- | --- | --- | --- | --- |
| Mind Spike | Basic attack replacing Strike | 1 | Deal 6 damage. | Undecided |
| Power Word: Shield | Basic defense replacing Defend | Undecided | Gain 5 Block. | Undecided |
| Vampiric Touch | Apply a distinct DoT | 1 | Apply Vampiric Touch: 5 damage each turn for 3 turns. | 1 proposed |
| Shadow Word: Pain | Apply a distinct DoT | 1 | Apply Shadow Word: Pain: 5 damage each turn for 3 turns. | 1 proposed |

Four Mind Spikes, four Power Word: Shields, one Vampiric Touch, and one Shadow Word: Pain form the proposed ten-card starting deck. Exact quantities are not yet confirmed. Upgrades and the formal card types of the defense and DoT cards are also undecided.

Vampiric Touch has no agreed healing or Block effect. Its current effect is solely the DoT specified above.

## Damage over time

- Vampiric Touch and Shadow Word: Pain are separate debuffs that can coexist on one enemy.
- Each deals 5 damage each turn for 3 turns.
- Strength does not modify this DoT damage.
- Reapplying the same DoT refreshes its duration rather than increasing its damage or stacking its duration.
- Future cards will synergize with the number of unique DoTs on a target.

One uninterrupted application supplies three ticks totaling 15 base damage. Both debuffs supply 10 base damage per tick cycle while they coexist. Tick timing, interactions with modifiers other than Strength, and what happens on the application turn remain undecided.

## Starting relic and Insanity

The starting relic is named **Voidform**. It provides a custom Insanity bar and the transformation mechanic.

Each direct damage source generates 1 Insanity per unique DoT on its target. For the owner's example, an enemy with two DoTs grants 2 Insanity for each Mind Spike. Three Mind Spikes against that enemy generate 6 Insanity in total.

Insanity persists between turns. It resets to zero after combat and when Voidform ends.

The exact definition of a direct damage source remains open, including multihit attacks, attacks against several enemies, fully blocked hits, and non-card damage. Threshold consumption, overflow, and generation during Voidform remain open.

## Voidform

The original accepted trigger is automatic activation upon reaching **10 Insanity**. The proposed retained activation card would replace this trigger; it has not yet been finalized. Either trigger uses these accepted Voidform effects:

- All Attack cards cost 1 less energy.
- All Attack cards deal 50% more damage.
- Voidform ends at the end of the turn.

For a later Mind Spike played while Voidform is active, the straightforward base case is 0 energy and 9 damage, before other modifiers. Whether the attack that reaches the threshold itself receives the damage bonus is undecided.

Cost floors, X-cost attacks, damage rounding, modifier ordering, overflow Insanity, and further Insanity generation during Voidform need explicit rules before implementation.

## Intended play pattern

Apply different DoTs, maintain them without stacking duplicates, then use direct attacks to build toward a Voidform burst. Identical initial DoT damage is intentional in the current baseline: their separate identities already matter to Insanity generation and future synergies.
