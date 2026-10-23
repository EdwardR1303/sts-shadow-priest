# Shadow Priest character design

The character focuses on surviving a setup phase and then committing to a massive burst turn. Distinct damage-over-time effects provide sustained damage and make subsequent direct damage sources generate more Insanity. At 10 Insanity, the starting relic generates a retained activation card so the player chooses the Voidform turn.

The rules below capture the owner's design and confirmed clarifications on October 23, 2026. Unspecified details are tracked in [Open questions](open-questions.md). The [on-demand activation card](voidform-activation.md) replaces automatic Voidform activation.

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

Upon reaching **10 Insanity**, receive a generated card that costs **0 energy**, has **Retain**, and reads **"Enter Voidform. Exhaust."** The player enters Voidform by playing this card, not by reaching the threshold alone. The card's working name is Enter Voidform; its formal card type remains undecided.

While Voidform is active:

- All Attack cards cost 1 less energy.
- All Attack cards deal 50% more damage.
- Voidform ends at the end of the turn.

For a Mind Spike played while Voidform is active, the straightforward base case is 0 energy and 9 damage, before other modifiers. The attack that earns the activation card does not itself activate the bonus; the activation card must first be played.

Cost floors, X-cost attacks, damage rounding, modifier ordering, overflow Insanity, and further Insanity generation during Voidform need explicit rules before implementation.

## Intended play pattern

Apply different DoTs, maintain them without stacking duplicates, then use direct attacks to build toward a Voidform burst. Identical initial DoT damage is intentional in the current baseline: their separate identities already matter to Insanity generation and future synergies.

The owner's intended feel is the coordinated raid burst: line up a damage amplifier, cooldowns, and consumables, then unload. This is the design inspiration, not a commitment to implement Bloodlust or any particular cooldown or potion as a card.

The class should have tools to survive while assembling that window. Draw order creates uncertainty in when DoTs and direct attacks become available; Insanity generation itself currently has no random roll. Retaining the activation card lets the player respond to that uncertainty and decide when their hand is ready.

## Future card design directions

Suggested roles for later cards, not accepted card effects:

- Defense that helps maintain the setup while leaving some opportunity to apply DoTs and attack.
- Hand preparation through selective draw, retention, or similar effects to assemble a useful burst hand.
- Payoffs for having several unique DoTs active when the player commits to Voidform.

The design tension is how long to prepare before committing. Playtests should check that defense buys time without making indefinite waiting the obvious best choice, and that a prepared burst feels substantially stronger than an ordinary attack turn.
