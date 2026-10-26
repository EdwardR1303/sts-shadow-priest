# Keywords

Shared design definitions for Shadow Priest cards and relics. Card text uses these terms without repeating their rules. These definitions describe the current playable build. References to draft relics describe their intended future interactions; those relics are not yet implemented.

### DoT

An enemy debuff that deals damage at the end of your turn, before enemies act. Touch, Pain, and Madness are the three DoTs; each counts as one unique DoT while it has positive stacks.

DoT damage ignores Block and is not Attack damage. Strength, Weak, Vulnerable, and Voidform do not modify it, and it does not generate Insanity. Mastery modifies each tick. Other explicit damage prevention still applies. Halo is not a DoT.

### Apply

Add the stated number of stacks of a debuff, creating it if absent. Respect its stack cap. Artifact can prevent each debuff application separately. Applying a DoT does not immediately trigger its damage unless the effect explicitly says so.

### Extend

Add the stated number of stacks to existing Touch and/or Pain, up to their caps. Never create a missing debuff. This duration maintenance does not trigger effects that require a fresh application, such as Misery, and does not consume Artifact. Madness has no separate duration to extend; an effect must explicitly add Madness stacks to increase it.

### Mastery

Each point increases the damage of every DoT tick by 1. Mastery can be negative; final tick damage cannot fall below zero. Use current Mastery when each tick resolves. Mastery never changes the number of stacks removed, including when damage is prevented or reduced to zero.

### Insanity

The Shadow Priest resource, capped at 100. Each direct Attack hit generates 5 Insanity per unique DoT on its target, including fully blocked or zero-damage hits. Multi-hit Attacks count each hit; area Attacks count each enemy hit. A killing hit uses the DoTs present for that hit. DoT ticks, Halo, healing, and retaliation do not generate Insanity.

Reaching 100 grants one 0-cost, Retain, Exhaust **Enter Voidform** card; entering Voidform is a choice. A full hand delays delivery until space is available. Insanity persists between turns but resets when Voidform ends or combat ends. No Insanity can be gained while an activation is ready, during Voidform, or during Recharge. These restrictions also apply to explicit card and relic gains.

### Voidform

A class stance. Attacks deal 50% more damage and cost 1 less energy, to a minimum of 0. X-cost cards receive the Attack damage bonus but no energy discount. Ordinary Voidform ends at the end of your turn, resets Insanity to 0, and causes Recharge. Explicit effects such as Surrender to Madness can override its duration.

### Recharge

A class stance that prevents all Insanity gain. It covers the remainder of the turn in which Voidform ends, if any, and the entire following player turn, including its end-of-turn effects. It does not expire at the same end-of-turn boundary that created it.

Example: ordinary Voidform ends on turn 1; Insanity cannot be gained on turn 2 and can be gained again on turn 3. Insanity was already reset when Voidform ended.

### Touch

At the end of your turn, deal 5 damage plus Mastery to the afflicted enemy, then remove 1 Touch stack. Counts as a DoT. Applications add stacks up to a maximum of 5; stack count does not change damage per tick.

### Pain

At the end of your turn, deal 3 damage plus Mastery to the afflicted enemy, then remove 1 Pain stack. Counts as a DoT. Applications add stacks up to a maximum of 5; stack count does not change damage per tick.

### Madness

At the end of your turn, deal damage equal to half the enemy's current Madness stacks, rounded up, plus Mastery. Then remove exactly 1 Madness stack. Counts as a DoT and has no stack cap. Applications add stacks without triggering an immediate tick.

Mastery is added after halving and rounding. Without Mastery or additional applications, 6 Madness deals 3, 3, 2, 2, 1, and 1 damage across six turns. With 2 Mastery, those ticks deal 5, 5, 4, 4, 3, and 3 damage; decay is unchanged.

### Haste

Uses a yellow version of the base-game Dexterity icon.

Each stack adds 1 to the effective X of every X-cost card, including cards played with 0 energy. It modifies X in the same way as Chemical X, which adds 2. Bonuses stack additively and are not consumed when a card is played.

Effective X = the card's energy value + Haste + Chemical X's bonus. Evaluate this once per play and use it for all of that card's X-based effects. An upgraded card's printed X+1 is added afterward and does not increase effective X for other effects. Haste lasts for the duration specified by its source; Power Infusion grants it only for the current turn; Xal'atath grants it for the entire combat. Both sources share one Haste total and icon, but only temporary stacks expire at turn end.

Protected Channel explicitly uses effective X, including Haste and Chemical X. Bonuses do not represent actual energy consumed for other effects such as Idol of Y'Shaarj. Free plays preserve energy; an explicit repeat that preserves X reuses the original effective X without adding its bonuses twice.
