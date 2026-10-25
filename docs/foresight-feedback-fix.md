# Foresight selection and play feedback

The player reported that Foresight+ appeared to apply a Power secretly rather than letting them choose and see it cast.

Code review found two independent causes:

- ForesightAction bypassed the chooser when the draw pile contained exactly one Power.
- The upgraded version placed the chosen card below the screen and immediately queued UnlimboAction, with no visible pause or complete portrait presentation setup. Its effect still used the ordinary card-play queue, so effect-only checks could pass while the player saw no card.

Both versions now open the Power-only chooser for every nonempty eligible pool, including a single card. The upgrade places the chosen Power visibly at screen center with its angle, scale, and lighting reset, holds it for 0.45 seconds in Fast Mode or 0.75 seconds normally, then follows the base game's Unlimbo/NewQueue sequence. The custom hold avoids WaitAction's 0.1-second Fast Mode cap. The Power still plays for free with ordinary play hooks, costs no hand slot, and leaves the card cycle normally. Base Foresight still puts the selected card in hand (or discard if the hand is full). Neither version searches discard, reshuffles, or produces ordinary draw hooks.

The related-action audit covered Serendipity, Piety/TypedDrawAction, and Dragonwrath repeats. Serendipity opens a real choice screen. Typed draws are deliberately random matching draws, and Dragonwrath deliberately repeats an Attack automatically. No equivalent skipped-choice branch was found in those actions. This source review does not establish that all cards are bug-free.

The existing in-game regression suite was updated to require the single-Power choice, observe the upgraded Power visibly in limbo before it applies, verify a readable preview in Fast Mode, and cover the upgraded single-Power/full-hand case. These checks were added for the next test run, not executed during the player's active session. This change received a compile/package check with tests skipped; no game was launched or interrupted.
