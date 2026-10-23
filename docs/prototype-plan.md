# Shadow Priest prototype plan

The first playable milestone should demonstrate the starting deck, two distinct DoTs, the Insanity meter, and a single-turn Voidform. The following phases are a proposed development sequence; this repository currently contains only design documents.

## Resolve the core rules

Choose Shield's cost, starter quantities, card types, DoT timing, and threshold/activation ordering. Both DoTs now cost 1 energy, and Insanity persistence and reset points are confirmed. Resolve whether the [retained activation card](voidform-activation-proposal.md) replaces automatic activation. Record the choices in the [decision log](decisions.md) and update the [character design](character-design.md).

## Establish a runnable character

Select the Java mod template and dependencies, configure building from VS Code, and launch a minimal custom character in the local game. Use placeholder visuals if needed to reach a playable prototype quickly.

## Add the starting deck and afflictions

Implement Mind Spike, Power Word: Shield, Vampiric Touch, and Shadow Word: Pain with the agreed card types and costs. Make each DoT's damage and remaining duration legible in game.

Verify that each effect produces exactly three intended ticks, reapplication refreshes rather than stacks, both debuffs coexist, and Strength does not increase their damage.

## Add the relic and transformation

Implement the custom Insanity bar, generation from direct damage, threshold activation, attack discounts, damage bonus, and expiry. The display should make it clear how close the player is to Voidform and whether it is active.

Verify the owner's example: three Mind Spikes against an enemy with two DoTs generate 6 Insanity. Once the unresolved rules are chosen, also check threshold crossing during multihit attacks, fully blocked hits, overflow, and restoration of card costs at expiry.

If the activation card proposal is accepted, also verify that the card can be held across turns, Voidform starts only on play, a full hand does not lose the earned activation, and the final charge rules prevent unintended duplicate activations.

## Playtest the combat loop

Test both single-enemy and multi-enemy fights. Record how often the player can apply both DoTs, how many turns it takes to enter Voidform, and how many attacks they can actually play after activation.

Tune one part of the design at a time. Expand the card pool after the starting deck demonstrates a satisfying setup and burst cycle.

## Playtest notes format

For each meaningful test, record:

- Build or commit, encounter, and relevant card/relic state.
- Expected behavior and observed behavior.
- Whether the finding is a rules bug, unclear feedback, or a balance concern.
- Proposed adjustment and the next comparison to run.
