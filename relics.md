# Shadow Priest Relic Designs

Relics are grouped by rarity or acquisition category. All designs here are class-specific to the Shadow Priest unless stated otherwise.

A trailing `?` on a relic name marks an unreviewed design. Removing it means the design is agreed, not necessarily implemented. Plain English is enough; we can fill in timing and interaction details during review.

## Quick template

Copy this under the appropriate category. Only the name and effect are needed to start; the other fields are optional.

```markdown
### Relic Name ?

- **Effect:** What the relic does.
- **Rules:** Optional timing, limits, reset behavior, or interactions.
- **Replaces:** Optional starter relic replacement, usually for a Boss relic.
- **Art:** Optional WoW item or ability reference, or a thematic description.
```

There is no standard card-style upgrade field. If a relic replaces or improves another relic, describe it as a separate design and use **Replaces**.

## Starter

### Voidform

- **Effect:** Each direct Attack hit generates 1 Insanity per unique DoT on its target. At 10 Insanity, receive a 0-cost, Retain, Exhaust card: Enter Voidform. During Voidform, Attacks cost 1 less energy and deal 50% more damage until the end of the turn.
- **Rules:** Fully blocked direct hits still generate Insanity; DoT ticks and Halo do not. Touch, Pain, and Madness each count as one unique DoT regardless of stacks. Insanity persists between turns, caps at 10, and resets when Voidform or combat ends. Hold only one charge; gain no Insanity while ready or active. Gain no Insanity during the entire turn after entering Voidform. The cost discount has a floor of zero and leaves X costs unchanged. A full hand delays delivery of the activation card until space is available.
- **Art:** WoW Voidform icon adapted into an organic relic silhouette with transparent padding.
- **Status:** Implemented.

## Common

_No designs yet._

## Uncommon

### Misery

- **Effect:** Whenever you directly apply Vampiric Touch to an enemy, also apply that many Shadow Word: Pain stacks.
- **Rules:** Qualifying effects must be capable of applying Touch when it is absent. Includes Vampiric Touch, Tentacle Slam, and Void Blast's Touch applications. Excludes existing-only stack increases from Void Bolt, Void Volley, and Mind Flay: Insanity. Use the incoming Touch application amount, not the target's total stacks or the amount of duration actually gained at the cap. Both DoTs retain their five-stack cap. If Artifact prevents Touch from being applied, Misery does not trigger; the resulting Pain application follows normal debuff application rules.
- **Status:** Agreed; not implemented.

### Idol of C'Thun
- **Effect:** At the start of combat, add a Tentacle Slam card to your hand


## Rare

### Idol of Y'Shaarj

- **Effect:** During Voidform, whenever you play a card that spends energy, draw 1 card.
- **Rules:** Trigger once per paid card, regardless of how much energy it spends, and resolve the draw after that card's effects. Count actual energy consumed, not printed cost. Zero-cost cards, cards discounted to zero, and free plays do not trigger it. An X-cost card triggers once only if it consumes positive energy; Chemical X's extra effects do not count as energy spent. Applies to Attacks, Skills, and Powers.
- **Status:** Agreed; not implemented.

### Idol of N'Zoth
- **Effect:** Gain 1 Mastery after playing a power

### Inescapable Torment ?

- **Effect:** At the start of each combat, gain 1 Mastery for each Curse in your deck.
- **Rules:** Count each Curse card in the permanent deck at combat start, including duplicate copies. Status cards and unplayable non-Curse cards do not count. Exhausting or otherwise removing a Curse during combat does not remove the granted Mastery; Curses added during combat do not increase this starting bonus. The granted Mastery lasts for that combat.
- **Status:** Name chosen; Rare placement and 1 Mastery per Curse are proposed starting values. Not implemented.

## Boss

### Idol of Yogg-Saron
- **Effect:** Entering Voidform grants 3 energy

### Double DoT Ticks (name pending)

- **Effect:** Your DoTs tick twice naturally, but lose only 1 duration stack.
- **Rules:** Keep the existing timing before the afflicted enemy acts. Resolve two separate damage ticks, then remove one duration stack, including when only one stack remains. This produces two total natural ticks, not two extra ticks. Each tick uses current Mastery and normal damage prevention rules; each Touch tick that removes enemy HP can trigger Vampiric Embrace. Psychic Link's bonus ticks are unchanged. Madness does not tick, and Halo is an aura, so neither is affected.
- **Replaces:** None; supplements the Voidform starter relic.
- **Status:** Mechanics agreed; final name pending. Not implemented.

### Curse Conversion (name pending)

- **Effect:** At the start of each combat, replace all Curses in your combat deck with Void Bolts.
- **Rules:** Replace each starting combat copy of a Curse with one unupgraded Void Bolt. Do not alter the permanent deck. The replacements last only for that combat; Curses added later in combat are not converted. Status cards and unplayable non-Curse cards do not qualify. Inescapable Torment still counts Curses in the permanent deck for its starting Mastery, regardless of relic acquisition or trigger order.
- **Replaces:** None; supplements the Voidform starter relic.
- **Status:** Mechanics agreed; final name pending. Not implemented.

## Shop

_No designs yet._

## Special / Event

_No designs yet._
