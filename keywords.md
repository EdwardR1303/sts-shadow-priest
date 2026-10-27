# Keywords

Shared design definitions for Shadow Priest cards and relics. Card text uses these terms without repeating their rules. These definitions describe the current playable build. Misery, Inescapable Torment, and the Shop Dark Void relic remain unimplemented drafts; references to them describe intended future interactions.

### DoT

An enemy debuff that deals damage at the end of your turn, before enemies act. Touch, Pain, and Madness are the three DoTs; each counts as one unique DoT while it has positive stacks.

DoT damage ignores Block and is not Attack damage. Strength, Weak, Vulnerable, and Voidform do not modify it. Every natural or triggered DoT tick generates 1 Insanity, including Psychic Link's extra Touch ticks; a zero-damage tick still counts. Mastery modifies each tick. Other explicit damage prevention still applies. Halo is not a DoT.

### Apply

Add the stated number of stacks of a debuff, creating it if absent. Respect its stack cap. Artifact can prevent each debuff application separately. Applying a DoT does not immediately trigger its damage unless the effect explicitly says so.

### Extend

Add the stated number of stacks to existing Touch and/or Pain, up to their caps. Never create a missing debuff. This duration maintenance does not trigger effects that require a fresh application, such as Misery, and does not consume Artifact. Madness has no separate duration to extend; an effect must explicitly add Madness stacks to increase it.

### Mastery

Each point increases the damage of every DoT tick by 1. Mastery can be negative; final tick damage cannot fall below zero. Use current Mastery when each tick resolves. Mastery never changes the number of stacks removed, including when damage is prevented or reduced to zero.

### Atonement

An enemy debuff that grants fixed Block equal to its amount whenever your direct Attack hits that enemy, including fully blocked hits. It lasts for combat and is not a DoT. Applications do not stack: keep the higher value, so an upgraded application replaces 2 with 3 but never adds them or reduces an existing 3. Artifact can block application. Atonement's damage happens before its application, so subsequent casts trigger the existing benefit. Dexterity does not modify this Block. DoT ticks, Halo, and retaliation do not trigger it; Block gained does not increase any remaining Barrier reserve.

### Insanity

The Shadow Priest resource, capped at 100. Each direct Attack hit generates 10 Insanity, independent of DoTs, energy spent, or damage amount. Fully blocked and zero-damage hits count. Multi-hit Attacks count each actual hit; area Attacks count each enemy hit. A killing hit counts, but later hits skipped because the target is dead do not. Free attacks and Haste/Chemical X repetitions count normally. Halo, healing, and retaliation do not generate attack-based Insanity.

Each natural or triggered DoT tick grants 1 Insanity, including Psychic Link. Count the tick once, even when its damage is zero; skipped ticks against dead enemies grant nothing. This is separate from the ten-per-Attack-hit rule and does not itself trigger Attack-hit effects. Explicit gains such as Mind Spike's additional 10 remain additive.

Whenever the priest loses HP from a damage event during combat, gain 5 Insanity. Count each event once, regardless of damage amount or source: enemy hits, self-damage, and damaging Curses/Statuses qualify. Fully blocked, dodged, or otherwise prevented damage grants nothing. Apply this after damage prevention and HP-loss modifiers. Each damaging hit of a multihit attack counts separately; Shadow Mend's combined debt tick counts once. This does not require a DoT on an enemy and does not trigger from out-of-combat HP loss or changes to maximum HP.

Reaching 100 grants one 0-cost, Retain, Exhaust **Enter Voidform** card; entering Voidform is a choice. A full hand delays delivery until space is available. Insanity persists between turns but resets when Voidform ends or combat ends. No Insanity can be gained while an activation is ready, during Voidform, or during Recharge. These restrictions also apply to explicit card and relic gains.

### Voidform

A class stance. Attacks deal 50% more damage and cost 1 less energy, to a minimum of 0. X-cost cards receive the Attack damage bonus but no energy discount. Ordinary Voidform ends at the end of your turn, resets Insanity to 0, and causes Recharge. Improved Voidform carries it across turns until at least three Attack cards have been played during that activation, then ends it at that turn's end. Recharge begins only when the stance actually ends. Surrender to Madness remains permanent.

### Recharge

A class stance that prevents all Insanity gain. Void Entropy explicitly bypasses Recharge to enter Voidform immediately; it generates no activation card. When that new form ends, normal Recharge begins again. This exception does not permit Insanity gain during Recharge.

It covers the remainder of the turn in which Voidform ends, if any, and the entire following player turn, including its end-of-turn effects. It does not expire at the same end-of-turn boundary that created it.

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

Effective X = the card's energy value + Haste + Chemical X's bonus. Evaluate this once per play and use it for all of that card's X-based effects. An upgraded card's printed X+1 is added afterward and does not increase effective X for other effects. At the end of each player turn, lose half your Haste, rounding the loss up (equivalently, halve the remaining amount and round down). Examples: 5 -> 2 -> 1 -> 0; 2 -> 1 -> 0. All Haste sources combine into one total with this decay. Power Infusion grants 2 Haste; Xal'atath does not grant Haste. There is no permanent Haste portion; Chemical X is a separate relic bonus and does not decay.

Protected Channel explicitly uses effective X, including Haste and Chemical X. Bonuses do not represent actual energy consumed for other effects such as Idol of Y'Shaarj. Free plays preserve energy; an explicit repeat that preserves X reuses the original effective X without adding its bonuses twice.

### Barrier

A tracked portion of your total Block that persists between turns and absorbs damage before ordinary Block. Its buff counter shows the remaining reserve. Additional Block does not increase this reserve and expires normally. Recasting refreshes to the modified card value without stacking or reducing a stronger reserve. Retention does not count as gaining Block. At zero reserve, remove the buff. Independent Blur, Barricade, and Val'anyr can still retain ordinary Block; Calipers removes only ordinary Block.
