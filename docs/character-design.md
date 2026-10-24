# Shadow Priest character design

The character focuses on surviving a setup phase and then committing to a massive burst turn. Distinct damage-over-time effects provide sustained damage and make subsequent direct damage sources generate more Insanity. At 10 Insanity, the starting relic generates a retained activation card so the player chooses the Voidform turn.

The rules below capture the owner's design and prototype defaults on October 23, 2026. [Prototype rules](prototype-rules.md) specify the implementation edge cases and provisional upgrades; [Open questions](open-questions.md) tracks future design work. The [on-demand activation card](voidform-activation.md) replaces automatic Voidform activation.

## Starting deck

| Card | Role | Energy cost | Base effect | Copies |
| --- | --- | --- | --- | --- |
| Mind Spike | Basic Attack replacing Strike | 1 | Deal 6 damage. | 4 |
| Power Word: Shield | Basic Skill replacing Defend | 1 | Gain 5 Block. | 4 |
| Vampiric Touch | Skill applying a distinct DoT | 1 | Apply Vampiric Touch: 5 damage each turn for 3 turns. | 1 |
| Shadow Word: Pain | Skill applying a distinct DoT | 1 | Apply Shadow Word: Pain: 3 damage each turn for 5 turns. | 1 |

Four Mind Spikes, four Power Word: Shields, one Vampiric Touch, and one Shadow Word: Pain form the ten-card prototype deck. Mind Spike upgrades to 9 damage; Shield upgrades to 8 Block. Touch upgrades to 7 damage for 3 turns; Pain upgrades to 5 damage for 5 turns. Only played DoT cards return to hand after end-turn discard.

Vampiric Touch has no agreed healing or Block effect. Its current effect is solely the DoT specified above.

## Damage over time

- Vampiric Touch and Shadow Word: Pain are separate debuffs that can coexist on one enemy.
- Touch deals 5/7 damage for 3 turns; Pain deals 3/5 damage for 5 turns. Upgrades change damage, not duration.
- Strength does not modify this DoT damage.
- Reapplying the same DoT replaces its damage and duration with the new card's values; neither value stacks.
- Future cards will synergize with the number of unique DoTs on a target.

Each uninterrupted base application totals 15 damage. Upgraded Touch totals 21 over 3 turns; upgraded Pain totals 25 over 5 turns. While both are active, they supply 8 damage per tick cycle, or 12 when both are upgraded. Ticks occur before the enemy acts, starting on its next turn after application. They bypass Block and ignore offensive modifiers; enemy damage hooks such as Intangible still apply. Artifact blocks both damage and duration changes on application or refresh.

## Starting relic and Insanity

The starting relic is named **Voidform**. It provides a custom Insanity bar and the transformation mechanic.

Each direct damage source generates 1 Insanity per unique DoT on its target. For the owner's example, an enemy with two DoTs grants 2 Insanity for each Mind Spike. Three Mind Spikes against that enemy generate 6 Insanity in total.

Insanity persists between turns. It resets to zero after combat and when Voidform ends.

The prototype counts each player-owned normal attack damage event, including blocked hits and killing blows. Multihit and multi-target attacks count each target hit separately. Only Touch and Pain contribute to the count. The meter caps at 10 and stops gaining while ready or active; there is no overflow or banked second charge.

## Voidform

Upon reaching **10 Insanity**, receive a generated Skill that costs **0 energy**, has **Retain**, and reads **"Enter Voidform. Exhaust."** The player enters Voidform by playing this card, not by reaching the threshold alone. Its working name is Enter Voidform.

While Voidform is active:

- All Attack cards cost 1 less energy.
- All Attack cards deal 50% more damage.
- Voidform ends at the end of the turn.

For a Mind Spike played while Voidform is active, the straightforward base case is 0 energy and 9 damage, before other modifiers. The attack that earns the activation card does not itself activate the bonus; the activation card must first be played.

The discount has a floor of zero and leaves X-cost cards unchanged. It overlays existing cost changes without permanently altering them. The damage bonus uses the game's final outgoing-damage calculation and normal rounding. Delivery and charge validation are specified in [Prototype rules](prototype-rules.md).

## Intended play pattern

Apply different DoTs, maintain them without stacking duplicates, then use direct attacks to build toward a Voidform burst. Touch supplies faster damage and Psychic Link synergy, while Pain supplies longer-lasting Insanity setup. Played cards return to help maintain and spread them.

The owner's intended feel is the coordinated raid burst: line up a damage amplifier, cooldowns, and consumables, then unload. This is the design inspiration, not a commitment to implement Bloodlust or any particular cooldown or potion as a card.

The class should have tools to survive while assembling that window. Draw order creates uncertainty in when DoTs and direct attacks become available; Insanity generation itself currently has no random roll. Retaining the activation card lets the player respond to that uncertainty and decide when their hand is ready.

## Reward-card design

The [first implemented reward-card batch](card-batch-01.md) now supplies 12 cards across these roles:

- Defense that helps maintain the setup while leaving some opportunity to apply DoTs and attack.
- Hand preparation through selective draw, retention, or similar effects to assemble a useful burst hand.
- Payoffs for having several unique DoTs active when the player commits to Voidform.

The design tension is how long to prepare before committing. Playtests should check that defense buys time without making indefinite waiting the obvious best choice, and that a prepared burst feels substantially stronger than an ordinary attack turn.
