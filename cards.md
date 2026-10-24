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
- **Upgrade:** Apply 4 stacks instead of 3; damage remains 5 per tick.
- **Rules:** Applications add remaining ticks, capped at 5. Each natural tick removes one stack. Damage is always 5, independent of stack count or application source.

#### Shadow Word: Pain

- **Cost:** 1 energy
- **Effect:** Apply a DoT to your target that deals 3 damage for 5 turns. If played, return this card to your hand at the end of your turn.
- **Upgrade:** Cost becomes 0 energy; damage remains 3 per tick.
- **Rules:** Apply 5 stacks, restoring remaining ticks to the cap of 5. Each natural tick removes one stack. Damage is always 3, independent of stack count or application source.

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

#### Tentacle Slam

- **Cost:** 2 energy
- **Effect:** Deal 8 damage to an enemy. Apply 3 Vampiric Touch stacks to it and all enemies between you and it.
- **Upgrade:** Deal 10 damage, cost becomes 1 energy.
- **Rules:** Only the selected enemy takes direct damage, before Touch is applied. Applications add stacks up to 5. Position is measured between the player and selected enemy, in either direction. Psychic Link can trigger the newly applied Touch after this Attack.

#### Void Bolt

- **Cost:** 1 energy
- **Effect:** Deal 8 damage. Add 1 stack to each existing DoT on the target.
- **Upgrade:** Deal 10 damage and add 2 stacks instead.
- **Rules:** Damage happens first. Extend existing Touch/Pain up to 5 and add Madness only if already present. Never create a missing DoT or increase future applications. Duration extension follows Mind Flay: Insanity; additional Madness is an application that Artifact can block.

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
- **Rules:** Touch and Pain cannot exceed 5 remaining ticks. Madness has no duration to extend.

#### Shadow Word: Madness

- **Cost:** 1 energy
- **Effect:** Apply 5 Madness to an enemy. Deal damage equal to its Madness.
- **Upgrade:** Apply 5 Madness to ALL enemies. Deal damage to the selected enemy equal to its Madness.
- **Rules:** Apply stacks before calculating the single hit. Madness stacks add together, persist on the enemy, and never tick or decay. Any positive number of stacks counts as one unique DoT for synergies. Madness has no duration to extend. The immediate hit is normal Attack damage, including ordinary modifiers and Insanity generation. The upgrade spreads only the application; damage remains single-target.

#### Mind Sear

- **Cost:** X energy
- **Effect:** Deal 3 damage to all enemies X times.
- **Upgrade:** Deal damage X + 1 times.

#### Void Volley

- **Cost:** X energy
- **Effect:** Hurl X Void Bolts at random enemies. Each deals 8 damage, then adds 1 stack to each existing DoT on its target.
- **Upgrade:** Each bolt deals 10 damage and adds 2 stacks.
- **Rules:** One Attack card play: Psychic Link triggers once, while each hit generates Insanity normally. Choose a living target separately for each bolt, after resolving the previous bolt and its stacks. Never create missing DoTs; Touch/Pain cap at 5. At zero energy, fire no bolts unless an external effect increases X. Chemical X adds two bolts. Free play preserves energy; Voidform amplifies damage without discounting X.

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


## Rare

### Attacks

#### Void Torrent

- **Cost:** X energy
- **Effect:** Deal damage X times to one enemy. Base damage starts at 4 and increases by 4 each hit: 4, 8, 12, and so on.
- **Upgrade:** Start at 5 and increase by 5 each hit.
- **Rules:** Every play starts a new sequence. Calculate normal damage modifiers and rounding separately for each hit. Generate Insanity per hit and trigger Psychic Link once per card. Stop when the selected target dies; never redirect remaining hits. Zero energy gives no hits unless an external effect increases X. Chemical X adds two hits. Free play preserves energy; Voidform amplifies damage without discounting X.

#### Void Blast

- **Cost:** 1 energy
- **Effect:** Deal 10 damage to an enemy. Add its DoT stacks to all other enemies.
- **Upgrade:** Deal 15 damage.
- **Rules:** Capture Touch, Pain, and Madness stacks before the hit, so a killing blow still spreads them. Leave the source unchanged. Add stacks on recipients: Touch and Pain cap at 5, while Madness accumulates normally. Artifact blocks each application separately, in Touch, Pain, Madness order. Do not spread unrelated debuffs or Halo.

### Skills

#### Surrender to Madness

- **Cost:** 3 energy
- **Effect:** Enter Voidform for the rest of combat. Take double damage from enemy Attacks for the rest of combat. Exhaust.
- **Upgrade:** Cost becomes 2 energy; the benefit and damage penalty are unchanged.
- **Rules:** Bypass the Insanity threshold and recovery restriction. If already in ordinary Voidform, extend that form instead of entering again or stacking its bonuses. Consume/invalidate any ready activation charge. Further copies are unplayable after surrendering, even if the penalty is cleansed. Apply the damage penalty as a separate debuff: Artifact can prevent it and Orange Pellets can remove it without ending Voidform. Multiply normal incoming Attack damage before Block; HP-loss and non-Attack damage are unchanged. Both effects last only for this combat. X costs are unchanged. Works without the starter relic.
- **Status:** Implemented.

#### Mass Dispel

- **Cost:** 2 energy
- **Effect:** Remove all enemies' Block, Artifact, and positive Strength.
- **Upgrade:** Cost becomes 1 energy.
- **Rules:** Preserve negative Strength and powers that generate future Strength or Block. Removal is not blocked by Artifact. Does not Exhaust.

### Powers

#### Vampiric Embrace

- **Cost:** 3 energy
- **Effect:** Whenever Vampiric Touch removes enemy HP, heal 1 HP.
- **Upgrade:** Heal 2 HP instead.
- **Rules:** Includes natural and Psychic Link ticks, separately for each enemy. Multiple Embrace copies add their healing. No per-turn cap. A fully prevented tick grants no healing; a damaging tick grants the fixed heal regardless of damage amount. Pain, Madness, Halo, and direct attacks do not trigger it. Normal healing restrictions still apply.


## Agreed relic update: Voidform

- **Additional rule:** You cannot gain Insanity the turn after entering Voidform.
- **Timing:** Enter Voidform and burst on turn 1; all Insanity gain is blocked throughout turn 2, including end-of-turn effects; generation resumes on turn 3.
- **Existing rules:** Insanity still resets when Voidform ends or combat ends. No gain while Voidform is ready or active. The recovery restriction applies to every source, including attacks and Reaping Madness.
- **Status:** Implemented.

## Class design rules

- Touch and Pain deal fixed damage and cap at 5 remaining ticks from every source, including duration extension and spreading. Madness is the separate accumulating mechanic.
- Shadow Priest class cards do not apply Vulnerable. Access through events, relics, colorless cards, or other outside sources is allowed.
- Sustain is an intended reward for building and setting up the Touch, Psychic Link, and Embrace engine. Validate it against the A20 Heart as well as ordinary encounters; controlled mechanics checks do not establish full-run balance.
