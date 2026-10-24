# Shadow Priest Card Designs

Cards are grouped by rarity, then type. Upgrades change only the listed values or effects; everything else stays the same.

A trailing `?` on a card name marks an unreviewed design. Removing it means the design is agreed, not necessarily implemented.

## Basic

### Attacks

#### Mind Spike

- **Cost:** 1 energy
- **Effect:** Deal 6 damage.
- **Upgrade:** Deal 9 damage.

### Skills

#### Power Word: Shield

- **Cost:** 1 energy
- **Effect:** Gain 5 Block.
- **Upgrade:** Gain 8 Block.

#### Vampiric Touch

- **Cost:** 1 energy
- **Effect:** Apply a DoT to your target that deals 5 damage for 3 turns. If played, return this card to your hand at the end of your turn.
- **Upgrade:** The DoT deals 7 damage for 3 turns.

#### Shadow Word: Pain

- **Cost:** 1 energy
- **Effect:** Apply a DoT to your target that deals 3 damage for 5 turns. If played, return this card to your hand at the end of your turn.
- **Upgrade:** The DoT deals 5 damage for 5 turns.

## Common

### Attacks

#### Mind Flay

- **Cost:** 1 energy
- **Effect:** Deal 2 damage 3 times.
- **Upgrade:** Deal damage 4 times.

#### Shadow Word: Death

- **Cost:** 0 energy
- **Effect:** Deal 10 damage. If the target is above 20% health, lose 3 HP each time this card deals damage.
- **Upgrade:** Deal 10 damage twice. Increase the health threshold to 30%.

#### Mind Blast

- **Cost:** 1 energy
- **Effect:** Deal 8 damage, plus 2 damage for each DoT on the target.
- **Upgrade:** Deal 10 damage, plus 3 damage for each DoT on the target.

### Skills

#### Bulwark

- **Cost:** 2 energy
- **Effect:** Gain 10 Block and 1 Blur.
- **Upgrade:** Gain 15 Block.

#### Shadow Mend

- **Cost:** 1 energy
- **Effect:** Heal yourself for 6 HP. Increase this card's cost by 1.
- **Upgrade:** Heal for 9 HP.

#### Fade

- **Cost:** 1 energy
- **Effect:** Until your next turn, whenever you would lose HP, lose 1 less, like Tungsten Rod.
- **Upgrade:** Lose 2 less HP instead.

### Powers

#### Power Word: Fortitude

- **Cost:** 0 energy
- **Effect:** Gain 2 Dexterity. This card's benefit does not stack. Unplayable if Fortitude has already granted at least this much Dexterity this combat.
- **Upgrade:** The benefit becomes 3 Dexterity.
- **Rules:** Track the highest benefit granted by Fortitude separately from other Dexterity sources. Playing the upgrade after the base version grants only 1 additional Dexterity. Equal or weaker copies become unplayable. Losing Dexterity does not allow reapplication. Reset this tracking after combat.

#### Reaping Madness

- **Cost:** 1 energy
- **Effect:** At the end of your turn, gain 1 Insanity.
- **Upgrade:** Gain 2 Insanity instead.
- **Rules:** Multiple copies stack. All Insanity gain follows the Voidform relic's restrictions, including its recovery turn.

## Uncommon

### Attacks

#### Mind Flay: Insanity

- **Cost:** X energy
- **Effect:** Increase the duration of all DoTs on the target by X turns. Deal 4 damage X times.
- **Upgrade:** Increase DoT durations by X + 1 turns and deal damage X + 1 times.

#### Shadow Word: Madness

- **Cost:** 1 energy
- **Effect:** Apply 5 Madness to an enemy. Deal damage equal to its Madness.
- **Upgrade:** Apply 5 Madness to ALL enemies. Deal damage to the selected enemy equal to its Madness.
- **Rules:** Apply stacks before calculating the single hit. Madness stacks add together, persist on the enemy, and never tick or decay. Any positive number of stacks counts as one unique DoT for synergies. Madness has no duration to extend. The immediate hit is normal Attack damage, including ordinary modifiers and Insanity generation. The upgrade spreads only the application; damage remains single-target.

#### Mind Sear

- **Cost:** X energy
- **Effect:** Deal 3 damage to all enemies X times.
- **Upgrade:** Deal damage X + 1 times.

### Skills

#### Halo

- **Cost:** 1 energy
- **Effect:** Gain a Halo aura. At the end of your turn, deal 3 damage to ALL enemies for 3 turns.
- **Upgrade:** Each pulse deals 5 damage instead.
- **Rules:** The first pulse occurs at the end of the casting turn, followed by two more pulses. Each cast creates a separate aura with its own damage and remaining duration. Pulses hit enemies present when they trigger, respect Block, ignore Strength and Voidform's damage bonus, and generate no Insanity. Halo is not a DoT and does not trigger Psychic Link. As a Skill, it receives no Voidform cost discount.

#### Dispersion

- **Cost:** 2 energy
- **Effect:** Gain 1 Intangible. End your turn. Exhaust.
- **Upgrade:** Gain Retain.

#### Desperate Prayer

- **Cost:** 1 energy
- **Effect:** Before your next turn, the next time an enemy attack causes you to lose HP, heal that much afterward.
- **Upgrade:** Cost becomes 0 energy.

#### Foresight

- **Cost:** 0 energy
- **Effect:** Select and draw 1 Power from your draw pile.
- **Upgrade:** Play the selected Power.

### Powers

#### Psychic Link

- **Cost:** 2 energy
- **Effect:** Each Attack card played triggers Vampiric Touch's damage on all enemies.
- **Upgrade:** Cost becomes 1 energy.

## Agreed relic update: Voidform

- **Additional rule:** You cannot gain Insanity the turn after entering Voidform.
- **Timing:** Enter Voidform and burst on turn 1; all Insanity gain is blocked throughout turn 2, including end-of-turn effects; generation resumes on turn 3.
- **Existing rules:** Insanity still resets when Voidform ends or combat ends. No gain while Voidform is ready or active. The recovery restriction applies to every source, including attacks and Reaping Madness.
- **Status:** Implemented alongside Fortitude, Reaping Madness, Shadow Word: Madness, and Halo.
