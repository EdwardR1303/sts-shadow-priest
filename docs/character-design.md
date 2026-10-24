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

Four Mind Spikes, four Power Word: Shields, one Vampiric Touch, and one Shadow Word: Pain form the ten-card prototype deck. Mind Spike upgrades to 9 damage; Shield upgrades to 8 Block. Touch upgrades to applying 4 remaining ticks of fixed 5 damage; Pain upgrades to costing 0 while applying 5 ticks of fixed 3 damage. Only played DoT cards return to hand after end-turn discard.

Vampiric Touch has no agreed healing or Block effect. Its current effect is solely the DoT specified above.

## Damage over time

- Vampiric Touch and Shadow Word: Pain are separate debuffs that can coexist on one enemy.
- Touch always deals 5 damage and applies 3/4 ticks; Pain always deals 3 and applies 5 ticks for 1/0 energy.
- Strength does not modify this DoT damage.
- Reapplication adds remaining ticks up to a universal cap of 5. Duration extension obeys the same cap. Damage is fixed by DoT type.
- Future cards will synergize with the number of unique DoTs on a target.

Each uninterrupted base application totals 15 damage. Upgraded Touch totals 20 over 4 ticks; upgraded Pain totals 15 over 5 ticks at zero energy. While both are active, they always supply 8 damage per tick cycle. Ticks occur before the enemy acts, starting on its next turn after application. They bypass Block and ignore offensive modifiers; enemy damage hooks such as Intangible still apply. Artifact blocks applications and their duration additions.

## Starting relic and Insanity

The starting relic is named **Voidform**. It provides a custom Insanity bar and the transformation mechanic.

Each direct damage source generates 1 Insanity per unique DoT on its target. For the owner's example, an enemy with two DoTs grants 2 Insanity for each Mind Spike. Three Mind Spikes against that enemy generate 6 Insanity in total.

Insanity persists between turns. It resets to zero after combat and when Voidform ends.

The prototype counts each player-owned normal attack damage event, including blocked hits and killing blows. Multihit and multi-target attacks count each target hit separately. Touch, Pain, and positive Madness stacks each contribute one to the count. Madness never ticks or decays, while Halo is a player aura that does not count as a DoT. The meter caps at 10 and stops gaining while ready, active, or recovering; there is no overflow or banked second charge.

## Voidform

Upon reaching **10 Insanity**, receive a generated Skill that costs **0 energy**, has **Retain**, and reads **"Enter Voidform. Exhaust."** The player enters Voidform by playing this card, not by reaching the threshold alone. Its working name is Enter Voidform.

While Voidform is active:

- All Attack cards cost 1 less energy.
- All Attack cards deal 50% more damage.
- Voidform ends at the end of the turn.
- All Insanity generation is blocked for the entire following turn, including Reaping Madness at turn end. It resumes at the next turn start.

For a Mind Spike played while Voidform is active, the straightforward base case is 0 energy and 9 damage, before other modifiers. The attack that earns the activation card does not itself activate the bonus; the activation card must first be played.

The discount has a floor of zero and leaves X-cost cards unchanged. It overlays existing cost changes without permanently altering them. The damage bonus uses the game's final outgoing-damage calculation and normal rounding. Delivery and charge validation are specified in [Prototype rules](prototype-rules.md).

## Intended play pattern

Apply different DoTs, maintain them without stacking duplicates, then use direct attacks to build toward a Voidform burst. Touch supplies faster damage and Psychic Link synergy, while Pain supplies longer-lasting Insanity setup. Played cards return to help maintain and spread them.

The owner's intended feel is the coordinated raid burst: line up a damage amplifier, cooldowns, and consumables, then unload. This is the design inspiration, not a commitment to implement Bloodlust or any particular cooldown or potion as a card.

The class should have tools to survive while assembling that window. Draw order creates uncertainty in when DoTs and direct attacks become available; Insanity generation itself currently has no random roll. Retaining the activation card lets the player respond to that uncertainty and decide when their hand is ready.

## Reward-card design

**Second implemented batch:** Fortitude, Reaping Madness, Shadow Word: Madness, and Halo are specified in the [card catalog](../design/cards.json) and decisions D033-D036. Madness adds a permanent, nonticking debuff that counts as one unique DoT; Halo is a separate player aura per cast, not a DoT. The Voidform relic update blocks every source of Insanity for the entire turn after entering Voidform. See [the second batch rules](card-batch-02.md) for exact interactions and artwork.

The four implemented reward batches now supply 23 cards across these roles. [The latest batch](card-batch-04.md) adds Void Bolt, Void Volley, and Void Torrent, with ten Commons, nine Uncommons, and four Rares in total. Every reward pool is native:

- Defense that helps maintain the setup while leaving some opportunity to apply DoTs and attack.
- Hand preparation through selective draw, retention, or similar effects to assemble a useful burst hand.
- Payoffs for having several unique DoTs active when the player commits to Voidform.

The design tension is how long to prepare before committing. Playtests should check that defense buys time without making indefinite waiting the obvious best choice, and that a prepared burst feels substantially stronger than an ordinary attack turn.

Native class cards do not apply Vulnerable; external access remains valid. Stacking Embrace healing is an intended setup payoff, with actual Touch HP loss required. Void Blast adds captured stacks to other enemies rather than replacing them.
