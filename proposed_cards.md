# Shadow Priest Reviewed Proposals

Accepted October 26, 2026 and codified in [cards.md](cards.md), [keywords.md](keywords.md), and [design/cards.json](design/cards.json). The original proposal and your Suggested Edit annotations are preserved in [the review archive](docs/proposed-cards-review-2026-10-25.md). These are revisions and additions, not duplicate cards.

## Common (5/7/2)

### Attacks

#### Unfurling Darkness

- **Cost:** 1 energy
- **Effect:** Deal 5 damage. Apply 3 Touch. Gain 10 Insanity.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Damage, then apply Touch to the surviving target, then explicit Insanity. Explicit gain does not require HP loss or a successful application.
- **Status:** Implemented; full-run balance testing remains.

#### Blood Magic

- **Cost:** 2 energy
- **Effect:** Deal 14 damage. Apply 2 Touch and 2 Pain.
- **Upgrade:** Deal 18 damage; apply 3 Touch and 3 Pain.
- **Rules:** Damage first, then independent Touch and Pain applications on a surviving target. Each can consume Artifact. No self-damage. Replaces both the old implementation and the earlier 18/24-damage draft.
- **Status:** Implemented; full-run balance testing remains.

#### Mind Blast

- **Cost:** 1 energy
- **Effect:** Deal 10 damage, plus 2 per unique DoT on the target. Gain 10 Insanity.
- **Upgrade:** Base damage becomes 12; per-DoT damage becomes 4.
- **Rules:** Snapshot unique DoTs before the hit. Explicit Insanity follows damage even if the target dies, in addition to the universal ten per hit.
- **Status:** Implemented; full-run balance testing remains.

#### Shadow Word: Torment

- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Apply 3 Pain.
- **Upgrade:** Deal 9 damage and apply 5 Pain.
- **Rules:** Damage first, then Pain on the surviving target. Application requires no HP loss and respects Artifact.
- **Status:** Implemented; full-run balance testing remains.

#### Mind Trauma

- **Cost:** 1 energy
- **Effect:** Deal 4 damage. Apply 4 Madness. Gain 10 Insanity.
- **Upgrade:** Deal 7 damage and apply 6 Madness.
- **Rules:** Damage, then Madness on the surviving target, then explicit Insanity. Madness does not require HP loss; Artifact can block it. Explicit Insanity still resolves.
- **Status:** Implemented; full-run balance testing remains.

### Skills

#### Bulwark

- **Cost:** 2 energy
- **Effect:** Gain 12 Block and 1 Blur.
- **Upgrade:** Gain 16 Block instead.
- **Rules:** Dexterity applies once. Blur retains Block, not cards.
- **Status:** Implemented; full-run balance testing remains.

#### Fade

- **Cost:** 0 energy
- **Effect:** Until your next turn, lose 1 less HP whenever you would lose HP.
- **Upgrade:** Reduce each HP-loss event by 2 instead.
- **Rules:** Preserves existing stacking and HP-loss coverage, including self-damage. No upfront Block. Fully prevented damage generates no damage-taken Insanity.
- **Status:** Implemented; full-run balance testing remains.

#### Mind Soothe

- **Cost:** 0 energy
- **Effect:** Until your next turn, the target deals 1 less Attack damage, plus 1 less per unique DoT on it.
- **Upgrade:** Base reduction becomes 2.
- **Rules:** Snapshot unique DoTs when played. Reduction applies per Attack hit, floors damage at zero, stacks for this turn, and is blocked by Artifact.
- **Status:** Implemented; full-run balance testing remains.

#### Void Shield

- **Cost:** 1 energy
- **Effect:** Gain 5 Block, plus 1 Block per 10 Insanity. Voidform counts as 100 Insanity.
- **Upgrade:** Base Block becomes 8; gain 1 additional Block per 5 Insanity instead.
- **Rules:** Round the Insanity quotient down. Add Dexterity once through the base component, with no second Dexterity application to the resource bonus. Grant combined Block once with normal Block modifiers. No Insanity gain. Ready or active Voidform uses 100; Recharge keeps the base Block. At 0/50/100 Insanity, base values are 5/10/15 or upgraded 8/18/28.
- **Status:** Implemented; full-run balance testing remains.

#### Void Tendrils

- **Cost:** 1 energy
- **Effect:** Gain 4 Block for each enemy. Apply 2 Pain to ALL attacking enemies.
- **Upgrade:** Gain 5 Block per enemy and apply 3 Pain.
- **Rules:** Count living enemies. Grant Block separately per enemy, applying Dexterity and other Block modifiers to every grant. Check attack intents when played, including attack/buff, attack/debuff, and attack/defend. Only those enemies receive Pain. Artifact blocks application, not Block.
- **Status:** Implemented; full-run balance testing remains.

#### Encroaching Shadows

- **Cost:** 1 energy
- **Effect:** Gain 5 Block. Apply 3 Madness.
- **Upgrade:** Gain 8 Block and apply 5 Madness.
- **Rules:** Block resolves first with Dexterity once. Artifact can prevent Madness but not Block. No immediate DoT tick.
- **Status:** Implemented; full-run balance testing remains.

#### Focused Will

- **Cost:** 1 energy
- **Effect:** Gain 7 Block. Gain 10 Insanity.
- **Upgrade:** Gain 10 Block and 15 Insanity.
- **Rules:** Block with Dexterity once, then immediate explicit Insanity. No enemy Attack prerequisite or pending trigger.
- **Status:** Implemented; full-run balance testing remains.

### Powers

#### Reaping Madness

- **Cost:** 1 energy
- **Effect:** Gain 20 Insanity. At the end of your turn, gain 10 Insanity.
- **Upgrade:** Ongoing gain becomes 20; immediate gain stays 20.
- **Rules:** Immediate gain occurs on play, not each turn. Copies stack ongoing gain. Threshold crossings deliver the retained activation at the next legal opportunity.
- **Status:** Implemented; full-run balance testing remains.

#### Shadow Weaving

- **Cost:** 1 energy
- **Effect:** At the end of your turn, gain 2 Block per active DoT across ALL enemies.
- **Upgrade:** Gain 3 Block per active DoT instead.
- **Rules:** Count Touch, Pain, and Madness once each per living enemy before natural ticks, including final one-stack ticks. Combine the total into one Block grant with Dexterity once. No DoTs means no Block, including no Dexterity-only grant. Copies add coefficients before the one grant. Barrier prevents the gain.
- **Status:** Implemented; full-run balance testing remains.

## Uncommon (2/1/1)

### Attacks

#### Atonement

- **Cost:** 1 energy
- **Effect:** Deal 5 damage. Apply 2 Atonement.
- **Upgrade:** Deal 7 damage and apply 3 Atonement.
- **Rules:** Atonement is a combat-long, Artifact-blockable debuff, not a DoT. Each direct Attack hit grants its fixed Block value, including fully blocked hits, without Dexterity scaling. Damage happens before application: replaying Atonement triggers the existing benefit. Applications do not stack; use the higher value (2 becomes 3, never 5 or a downgrade). No Exhaust. Equal or weaker reapplications do not consume Artifact. DoTs, Halo, and retaliation do not trigger it.
- **Status:** Implemented; full-run balance testing remains.

#### Mind Sear

- **Cost:** X energy
- **Effect:** Deal 6 damage to ALL enemies X times.
- **Upgrade:** Repeat the damage X+1 times.
- **Rules:** Uses effective X with Haste and Chemical X. No built-in Madness application. Each actual enemy hit grants ten Insanity; dead targets generate no additional hits.
- **Status:** Implemented; full-run balance testing remains.

### Skills

#### Dark Revelation

- **Cost:** 1 energy
- **Effect:** Choose a DoT application card from your draw pile. Put it into your hand. It costs 0 this turn. Exhaust.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Choose visibly even with one option. Direct applications include conditional Shadow Word: Madness, Dark Void, and Void Blast; the chosen card retains its normal application conditions. Existing-only extension and enabling Powers such as Searing Nightmare are excluded. Search only the draw pile; no shuffle or on-draw triggers. A full hand sends the chosen card to discard. Cost resets normally after this turn. The accepted roster currently has no eligible X-cost application cards.
- **Status:** Implemented; full-run balance testing remains.

### Powers

#### Protected Channel

- **Cost:** 2 energy
- **Effect:** Gain 6 Block. Whenever you play an X-cost card, gain 3 Block X times.
- **Upgrade:** Ongoing Block becomes 5 per instance; upfront stays 6.
- **Rules:** Dexterity applies once to the upfront grant and separately to each later instance. Use effective X including Haste and Chemical X, but not printed X+1 upgrades. Trigger once per X-card play after its effects. Free repeats preserve X. Barrier blocks all gains.
- **Status:** Implemented; full-run balance testing remains.

## Rare (0/0/1)

### Powers

#### Searing Nightmare

- **Cost:** 2 energy
- **Effect:** Your direct Attack hits apply 1 Madness to the enemy hit.
- **Upgrade:** Cost becomes 1 energy.
- **Rules:** Replaces the X-cost Attack. Each direct hit applies one Madness to the surviving target, including fully blocked hits. AoE checks each enemy and multihits trigger per hit. Artifact can block each application. Copies stack their coefficient. DoT ticks, Psychic Link, Halo, and retaliation do not trigger applications. The Power itself deals no damage and applies no immediate DoT.
- **Status:** Implemented; full-run balance testing remains.

## Generated (0/1/0)

### Skills

#### Enter Voidform

- **Cost:** 0 energy
- **Effect:** Enter Voidform. Draw 1 card. Retain. Exhaust.
- **Upgrade:** None; unupgradable.
- **Rules:** Draw only after a valid activation succeeds. Preserve one-charge guards and full-hand delivery protection. Stale or copied invalid tokens cannot draw. Surrender does not receive this token-specific draw.
- **Status:** Implemented; full-run balance testing remains.

## Mechanic decisions

- Direct Attack hits generate 10 Insanity regardless of energy or DoTs, including blocked hits.
- Natural and triggered DoT ticks generate 1 Insanity each, including Psychic Link.
- Keep 5 Insanity per HP-damaging event and explicit card gains. Preserve the 100 cap, on-demand activation, and all gain lockouts.
- Reject energy-spend generation, first-application tracking, opening reserves, and per-DoT/minimum-hit alternatives.
- Drop Protective Shadows; Shadow Weaving fills the accepted role.
- The seven settled non-starter relics are implemented; see [relic verification](docs/relic-implementation.md).
