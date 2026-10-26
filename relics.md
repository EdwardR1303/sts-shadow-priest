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

- **Effect:** Each direct Attack hit generates 5 Insanity per unique DoT on its target. Each damage event that removes your HP in combat grants 5 Insanity. At 100 Insanity, receive Enter Voidform.
- **Rules:** Use the Insanity, Voidform, and Recharge definitions in keywords.md. Enter Voidform costs 0, has Retain and Exhaust, and is an on-demand activation. Ordinary Voidform discounts Attacks by 1 and increases their damage by 50% until turn end. Explicit Insanity gains, including Mind Spike's additional 10 and damage-taken gains, follow the same cap and generation restrictions. X costs are unchanged; Haste modifies effective X separately.
- **Art:** WoW Voidform icon adapted into an organic relic silhouette with transparent padding.
- **Status:** Implemented, including attack-hit and damage-taken generation.

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

- **Effect:** At the start of combat, add a free Tentacle Slam to your hand.
- **Rules:** Generate a combat-only copy, separate from the permanent deck. The free cost is agreed; whether it lasts for the opening turn or the entire combat is pending clarification. Proposed copy is unupgraded.
- **Status:** Free generated card agreed; duration clarification pending. Not implemented.


## Rare

### Idol of Y'Shaarj

- **Effect:** During Voidform, whenever you play a card that spends energy, draw 1 card.
- **Rules:** Trigger once per paid card, regardless of how much energy it spends, and resolve the draw after that card's effects. Count actual energy consumed, not printed cost or effective X. Zero-cost cards, cards discounted to zero, and free plays do not trigger it. An X-cost card triggers once only if it consumes positive energy; Haste and Chemical X bonuses do not count as energy spent. Applies to Attacks, Skills, and Powers.
- **Status:** Agreed; not implemented.

### Idol of N'Zoth

- **Effect:** Whenever you play a Power or equip your Legendary at combat start, gain 1 Mastery.
- **Rules:** Each actual Power card play grants 1 Mastery after its effects. A successful automatic Legendary equipment activation also grants 1 Mastery, once per combat. Temporary copies and failed/duplicate equipment attempts do not qualify. This equipment interaction is specific to N'Zoth; it does not turn automatic equipment into a card play for other effects. Mastery lasts for combat.
- **Status:** Legendary interaction agreed; not implemented.

### Inescapable Torment ?

- **Effect:** At the start of each combat, gain 1 Mastery for each Curse in your deck.
- **Rules:** Count each Curse card in the permanent deck at combat start, including duplicate copies. Status cards and unplayable non-Curse cards do not count. Exhausting or otherwise removing a Curse during combat does not remove the granted Mastery; Curses added during combat do not increase this starting bonus. The granted Mastery lasts for that combat.
- **Status:** Name chosen; Rare placement and 1 Mastery per Curse are proposed starting values. Not implemented.

## Boss

### Idol of Yogg-Saron
- **Effect:** Entering Voidform grants 3 energy

### Improved Voidform ?

- **Effect:** Voidform lasts until at least 3 Attacks have been played, then ends at the end of the turn.
- **Rules:** Replaces the previous 10-Insanity-per-card draft. Proposed interpretation: count Attack card plays during the current activation across turns; fewer than 3 lets Voidform carry into the next turn, and reaching 3 allows it to end at that turn's end rather than immediately. Multi-hit cards count once. This interpretation and the Surrender interaction are awaiting clarification.
- **Balance note:** Under that interpretation, playing fewer than 3 Attacks can preserve Voidform indefinitely while using Skills/DoTs, including maximum Void Shield and Shadow Committal benefits. Decide whether that is intentional or whether carryover needs a limit.
- **Status:** New direction proposed; exact timing unreviewed. Not implemented.

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
