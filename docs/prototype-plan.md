# Shadow Priest prototype milestone

Version 0.1.0 implements the first playable milestone: a selectable character, starting deck, two distinct refreshing DoTs, Insanity meter, and on-demand single-turn Voidform.

## Implemented

- Java 8/Maven project with VS Code setup, build, playtest, deploy, and debugger support.
- Ten-card starter deck, provisional upgrades, custom relic, and generated activation card.
- DoT timing, refresh, duration, Artifact, and Intangible behavior.
- Persistent Insanity, per-hit/per-target generation, threshold cap, retained charge, full-hand delivery, and copied-card protection.
- Attack energy discount and damage bonus, with turn-end reset.
- Isolated saves, a 4K borderless launcher, state unit tests, and opt-in tests in the actual game.
- Second reward batch: Fortitude, Reaping Madness, permanent nonticking Madness, independent Halo auras, and a full Voidform recovery turn.
- First reward batch: six commons and six uncommons with upgrades, revised starter DoTs, and automated combat checks. See [the batch rules](card-batch-01.md).

See [development and testing](development.md) for commands, [prototype rules](prototype-rules.md) for exact mechanics, and [playtest results](playtest-results.md) for validation and its limits.

## Human playtesting next

Play ordinary single-enemy and multi-enemy encounters. Record turns to apply both DoTs and earn Voidform, damage taken during setup, turns spent holding the ready card, and burst-turn damage. Note whether the hand has enough attacks to exploit the discount.

Evaluate the survival-then-burst feel before tuning one variable at a time. Controlled engine checks establish correctness; they do not establish balance or enjoyment. Placeholder visuals and the small native rare pool still limit how closely a full run reflects the intended class.

## Playtest note format

- Build or commit, encounter, and relevant cards/relics.
- Expected and observed behavior.
- Whether the finding is a rules bug, unclear feedback, or a balance concern.
- Proposed adjustment and the next comparison to run.
