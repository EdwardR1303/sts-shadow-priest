# Shadow Priest Relic Designs

Relics are grouped by rarity or acquisition category. All designs here are class-specific to the Shadow Priest unless stated otherwise.

Shared terms use [keywords.md](keywords.md). Agreed keyword changes and revised relic text may still be awaiting implementation.

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

- **Effect:** Each direct Attack hit generates 5 Insanity per unique DoT on its target. At 100 Insanity, receive Enter Voidform.
- **Rules:** Use the Insanity, Voidform, and Recharge definitions in keywords.md. Enter Voidform costs 0, has Retain and Exhaust, and is an on-demand activation. Ordinary Voidform discounts Attacks by 1 and increases their damage by 50% until turn end. Explicit Insanity gains, including Mind Spike's additional 10, follow the same cap and generation restrictions. X costs are unchanged; Haste modifies effective X separately.
- **Art:** WoW Voidform icon adapted into an organic relic silhouette with transparent padding.
- **Status:** Existing starter implemented; the revised 5-Insanity hit generation and shared keyword changes are agreed but awaiting implementation.

## Common

### Shadowy Preparation

- **Effect:** At the start of each combat, gain 10 Insanity for each enemy.
- **Rules:** Count enemies present at combat start and grant the bonus once, after the combat's Insanity reset. Later summons do not grant additional Insanity. Use the normal Insanity cap and charge rules; reaching 100 grants the activation card rather than automatically entering Voidform.
- **Status:** Agreed; not implemented.

## Uncommon

### Misery ?

- **Effect:** Whenever you apply Touch to an enemy, also apply that many Pain stacks.
- **Rules:** Use Apply versus Extend as defined in keywords.md. Qualifying effects must be capable of applying Touch when it is absent. Includes Vampiric Touch, Tentacle Slam, Unfurling Darkness, Shadow Crash, Blood Magic's current draft, and Void Blast's Touch applications. Excludes existing-only maintenance from Mind Flay, Mind Flay: Insanity, Void Bolt, and Void Volley. Use the incoming Touch application amount, not the target's total stacks or the amount actually gained at the cap. If Artifact prevents Touch, Misery does not trigger; resulting Pain follows normal application rules.

### Idol of C'Thun
- **Effect:** At the start of combat, add a Tentacle Slam card to your hand


## Rare

### Idol of Y'Shaarj

- **Effect:** During Voidform, whenever you play a card that spends energy, draw 1 card.
- **Rules:** Trigger once per paid card, regardless of how much energy it spends, and resolve the draw after that card's effects. Count actual energy consumed, not printed cost or effective X. Zero-cost cards, cards discounted to zero, and free plays do not trigger it. An X-cost card triggers once only if it consumes positive energy; Haste and Chemical X bonuses do not count as energy spent. Applies to Attacks, Skills, and Powers.
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

### Improved Voidform ?
- **Effect:** Entering Voidform no longer expires at the end of turn. Instead, each card played reduces insanity by 10. When reaching 0, leave Voidform.

### Void Vengeance

- **Effect:** At the start of each combat, replace all Curses in your combat deck with Void Bolts.
- **Rules:** Replace each starting combat copy of a Curse with one unupgraded Void Bolt. Do not alter the permanent deck. The replacements last only for that combat; Curses added later in combat are not converted. Status cards and unplayable non-Curse cards do not qualify. Inescapable Torment still counts Curses in the permanent deck for its starting Mastery, regardless of relic acquisition or trigger order.
- **Replaces:** None; supplements the Voidform starter relic.
- **Status:** Mechanics agreed; final name pending. Not implemented.

## Shop

### Dark Void ?

- **Effect:** At the start of each combat, apply 3 Pain to all enemies.


## Special / Event

_No designs yet._
