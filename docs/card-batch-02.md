# Second reward-card batch

This records the second batch. See [the third batch](card-batch-03.md) for the current reward pool and revised Touch/Pain stacking rules.

Four reviewed designs add two Common Powers, one Uncommon Attack, and one Uncommon Skill. Common and uncommon reward pools now each contain eight Shadow Priest cards. Rares remain temporarily Ironclad.

| Card | Cost | Base | Upgrade |
| --- | --- | --- | --- |
| Power Word: Fortitude | 0 | Nonstacking benefit of 2 Dexterity; equal or weaker copies become unplayable | Benefit of 3 Dexterity |
| Reaping Madness | 1 | Gain 10 Insanity at end of turn | Gain 20 |
| Shadow Word: Madness | 1 | Apply 5 Madness, then hit the selected enemy for its total stacks | Apply 5 to all enemies, then hit only the selected enemy |
| Halo | 1 | Separate aura dealing 3 damage to all enemies at end of turn, three times | 5 damage per pulse |

## Fortitude and passive generation

Fortitude tracks its highest granted benefit per player and combat, separately from actual Dexterity. Base followed by upgraded grants 2, then 1 additional Dexterity. Upgraded followed by either version is unplayable. Other Dexterity sources and later Dexterity losses do not alter eligibility. An explicitly forced duplicate use cannot grant additional benefit. Combat completion and a fresh combat reset the tracking.

Reaping Madness copies stack their end-of-turn gain. All gain passes through the same Insanity state as attacks, keeping the relic counter, threshold, and charge delivery consistent. End-of-turn threshold activation becomes available on the next player turn, subject to hand space.

## Voidform recovery

The relic now states: **You cannot gain Insanity the turn after entering Voidform.**

Enter Voidform and burst on turn 1; the form ends and Insanity resets to zero. All gain is blocked throughout turn 2, including Reaping Madness at turn end. Generation resumes at turn 3's start. The meter reads **RECOVERING** while blocked. Existing no-gain rules while ready or active remain. Combat reset clears recovery.

## Madness

Madness is a permanent, additive enemy debuff with no tick or duration. Positive stacks count as one unique DoT for Insanity and Mind Blast, alongside Touch and Pain. Mind Flay: Insanity cannot extend or increase Madness. Halo, Poison, and unrelated debuffs do not contribute to this count.

Shadow Word: Madness applies stacks before resolving one normal Attack hit. Strength, Weak, Vulnerable, Voidform, Block, and normal damage hooks affect that hit; it can generate Insanity and trigger Psychic Link once. The upgrade still targets one enemy for damage. Artifact blocks a stack application, but the hit still uses any stacks already present. Targeted damage previews account for existing stacks and Artifact.

## Halo

Each cast creates a separately identified player aura with its own icon, damage, and remaining pulse count. The first pulse is at the end of the casting turn; the next two occur at subsequent turn ends. Mixed base/upgraded casts retain their individual values and expire independently.

Each pulse hits living enemies present when it resolves, including later summons. Damage respects Block and Intangible, ignores Strength, Weak, Vulnerable, and Voidform's damage bonus, and does not generate Insanity. Halo is a Skill, so it receives no Attack discount and triggers no Psychic Link. It is not an enemy DoT.

## Artwork and validation

The three existing WoW abilities use their ability icons; Reaping Madness uses original generated artwork. All four share the existing card frame, with matching new power/debuff icons where applicable. See [batch artwork](card-batch-02-art.md) for provenance and the generation prompt, and [playtest results](playtest-results.md) for validation.

These values remain a balance baseline. Full-run balance, interactions with unrelated mods, and mid-combat save/reload are not established by the controlled tests.
