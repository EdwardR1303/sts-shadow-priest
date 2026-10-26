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

- **Effect:** At the start of combat, add an unupgraded Tentacle Slam to your hand. It costs 0 for the entire combat.
- **Rules:** Generate one combat-only copy, separate from the permanent deck. Its cost is 0 for the entire combat, including after discard and redraw; the discount is not consumed by its first play. Other copies of Tentacle Slam are unaffected.
- **Status:** Agreed; not implemented.


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

### Improved Voidform

- **Effect:** Voidform persists across turns until you have played at least 3 Attack cards during that activation, then ends at the end of that turn.
- **Rules:** Reset the Attack counter on each successful Voidform entry. Count Attack card plays cumulatively during that activation, not hits; a multi-hit card counts once. With fewer than 3, carry Voidform through the enemy turn and into the following player turn. Reaching 3 does not end it immediately: all further cards that turn still benefit, and ordinary Voidform ends at that player turn's end. Skills and Powers do not advance the counter. Surrender to Madness remains permanent. Insanity stays locked while active; reset it and begin the usual Recharge only when ordinary Voidform actually ends. Combat end clears the counter and stance. This replaces the old Insanity-drain draft.
- **Balance note:** The accepted timing has no separate turn limit. Playing fewer than 3 Attacks can preserve Voidform while using Skills/DoTs, including maximum Void Shield and Shadow Committal benefits. Evaluate that Boss-relic interaction during playtesting; do not silently impose a carryover limit.
- **Status:** Timing and Surrender interaction agreed; not implemented.

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
