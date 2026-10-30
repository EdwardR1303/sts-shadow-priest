# Surrender to Madness

Current 0.1.5 design, accepted October 30, 2026. Supersedes the prior free-Attack/doubled-damage bargain.

| Property | Base | Upgrade |
| --- | --- | --- |
| Cost | 0 energy | Unchanged |
| Type / rarity | Skill / Rare | Unchanged |
| Requirement | Leave Voidform twice this combat | Unchanged |
| Benefit | Permanent Voidform; gain 2 Strength and 2 Mastery immediately and at each subsequent turn start | Gain 3 of each instead |
| Deadline | Die at the end of your fifth turn, including the casting turn | Unchanged |
| Exhaust | Yes | Yes |

## Unlock and lifecycle

Show completed exits inline as `(0/2)`, `(1/2)`, or `(2/2)`. Entering or refreshing an active Voidform does not count. Improved Voidform counts once when its extended activation actually ends. Progress resets each combat. Both ordinary card play and forced use are blocked until two exits have occurred. Additional Surrenders cannot be played or refresh an existing bargain.

Once unlocked, Surrender can enter during Recharge or extend an existing active form. Entering triggers the usual entry effects; extending does not trigger them again. Invalidate ready activation cards. Voidform keeps its normal 50% Attack damage bonus and one-energy Attack discount; fixed-cost Attacks are no longer universally free, and X costs remain unchanged. The completed-exit tracker lives in the Voidform relic state; without that mechanic there is no progress to unlock Surrender.

## Deadline

The Surrender buff shows five remaining turn endings when played. Grant the initial stats immediately, then grant them at the start of each subsequent player turn. Count the casting turn as turn one. At the fifth turn ending, allow friendly end-of-turn effects, including natural DoT ticks, to resolve before checking the deadline. Winning combat cancels it.

The deadline is unavoidable death, not damage or HP loss. Block, Intangible, Artifact, debuff cleansing, Fairy in a Bottle, Lizard Tail, and Guardian Angel cannot prevent it. The revival effects are bypassed, not consumed. The bargain does not double incoming damage. All stats, progress, and countdown state are combat-only.

## Validation

The focused engine suite verifies the ordinary and forced-use gate, entry versus exit counting, Recharge, immediate and next-turn stats, normal Attack discount, duplicate protection, combat reset, all five turn endings, death despite revival effects, and a real final-turn DoT victory before the deadline. Card renders verify locked and ready text. See [Devour batch notes](devour-batch-2026-10-30.md).

These checks establish mechanics, not boss-fight balance. Full-run testing should check whether two completed Voidforms unlock the card early enough and whether five turns offer a useful but demanding finishing window.

## Artwork

The card and countdown use the existing archived WoW Surrender to Madness icon, credited in [Warcraft artwork](../licenses/Warcraft-artwork.txt).
