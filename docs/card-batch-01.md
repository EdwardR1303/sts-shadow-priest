# First reward-card implementation

Implemented from the owner's 16-card draft snapshot on October 23, 2026. The working `cards.md` remains the owner's scratchpad; later edits are not automatically included in this batch. [The JSON catalog](../design/cards.json) records the implemented base and upgraded versions.

This document preserves the first batch's scope. [The second batch](card-batch-02.md) subsequently adds four rewards, permanent nonticking Madness, and Voidform recovery; see it for the current additions to these rules.

## Cards

| Rarity | Card | Cost | Base | Upgrade |
| --- | --- | --- | --- | --- |
| Basic | Mind Spike | 1 | 6 damage | 9 damage |
| Basic | Power Word: Shield | 1 | 5 Block | 8 Block |
| Basic | Vampiric Touch | 1 | 5 damage for 3 turns; returns after being played | 7 damage; still 3 turns |
| Basic | Shadow Word: Pain | 1 | 3 damage for 5 turns; returns after being played | 5 damage; still 5 turns |
| Common | Bulwark | 2 | 10 Block, 1 Blur | 15 Block; still 1 Blur |
| Common | Shadow Mend | 1 initially | Heal 6; this copy costs 1 more each use this combat | Heal 9 |
| Common | Fade | 1 | Until next turn, reduce each HP loss by 1 | Reduce by 2 |
| Common | Mind Flay | 1 | 2 damage three times | Four hits |
| Common | Shadow Word: Death | 0 | 10 damage; 3 HP backlash if target was above 20% HP and the hit removed HP | Two hits; 30% threshold |
| Common | Mind Blast | 1 | 8 damage plus 2 per distinct DoT | 10 plus 3 per DoT |
| Uncommon | Dispersion | 2 | 1 Intangible; end turn; Exhaust | Retain |
| Uncommon | Desperate Prayer | 1 | Recover HP lost to the next enemy Attack before next turn, if nonlethal | Cost 0 |
| Uncommon | Foresight | 0 | Choose a Power in the draw pile and put it in hand | Play it for free instead |
| Uncommon | Mind Flay: Insanity | X | Extend each target DoT by X turns; 4 damage X times | X+1 extension and hits |
| Uncommon | Mind Sear | X | 3 damage to all enemies X times | X+1 hits |
| Uncommon | Psychic Link | 2 | Each Attack card triggers Touch damage on all afflicted enemies | Cost 1 |

The generated Enter Voidform card remains unchanged and is not included in these 16 designs. Shadow Word: Madness is still a concept, without finalized application values; it is not implemented in this batch.

## Rules used for testing

- Only DoT card instances actually played that turn return, after ordinary end-turn discard and retained-card restoration. A card already in hand is not duplicated. Exhausted cards stay exhausted. If the hand is full, the returning card remains in discard; there is no deferred return. Return tracking resets after each turn and combat.
- Touch and Pain still refresh by replacing damage and duration. Reapplication can shorten a duration extended by Mind Flay: Insanity. Extensions change only duration and do not count as a new application for Artifact.
- Shadow Mend increases the played combat copy's cost after its current cost is paid. The increase persists through discards and shuffles, but does not change the permanent deck. Combat copies inherit the current cost; fresh generated copies start at 1. Ordinary cost-modifying effects still work.
- Fade applies after Block and to self-inflicted HP loss. Multiple applications add their reductions until the next player turn. It combines with Intangible and other mitigation.
- Death checks the target's health before each hit. Backlash happens only if that hit actually removes enemy HP, including a killing blow. A second hit against an already dead enemy does nothing. Block prevents backlash when it prevents all HP loss. Fade can reduce the self-damage.
- Desperate Prayer only responds to normal enemy Attack damage that removes HP. It consumes itself on the first such hit, heals afterward, and expires at the next player turn. It cannot avert a lethal hit; self-damage and fully blocked attacks do not consume it. Reapplying refreshes one pending recovery rather than adding charges. Normal healing modifiers still apply.
- Foresight is deliberately reusable and does not Exhaust. No eligible Power means no effect. One eligible card is selected automatically; multiple Powers open a choice. This is a tutor, so it does not shuffle or trigger ordinary draw effects. A full hand sends the base version's selected card to discard. The upgraded version plays directly without needing a hand slot, through the normal card-play pipeline. Neither version grants Retain.
- X cards use energy available for the play, consume it once unless free to play, and receive Chemical X's +2. Upgraded cards at X=0 still provide their +1 effect. Voidform does not discount X; its Attack damage bonus applies normally.
- Psychic Link triggers once per Attack card played, including area and X-cost cards, rather than once per hit. It deals each living affected enemy's current Touch damage, including the original attack target if still alive. Bonus ticks do not consume duration or generate Insanity. They bypass Block and ignore Strength, Weak, Vulnerable, and Voidform, while respecting Intangible. Additional Psychic Link copies add one bonus trigger per Attack. There is no recursive linking.

## Reward pools and presentation

Common and uncommon rewards now use the six Shadow Priest cards of each rarity. Until the owner designs rare cards, the rare pool remains Ironclad so boss rewards can be generated. No proposed card has been silently promoted to Rare to fill that gap.

The new reward cards use temporary portraits and the new powers use existing game icons. The four starters retain their WoW icon artwork. Final artwork, full-run balance, and interactions with unrelated mods remain later work.
