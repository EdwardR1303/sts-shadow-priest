# Shadow Priest Card Designs

Cards are grouped by rarity, then type. Upgrades change only the listed values or effects; everything else stays the same.

A trailing `?` on a card name marks an unreviewed design. Removing it means the design is agreed, not necessarily implemented.

The structured [card catalog](design/cards.json) includes complete base and upgraded versions. All 49 collectible designs are implemented: 4 Basics, 20 Commons, 14 Uncommons, 8 Rares, and 3 Legendaries. Enter Voidform is an additional generated card. See [sixth-batch rules](docs/card-batch-06.md) and [next design priorities](docs/next-design-priorities.md). Full-run balance testing remains.

<!-- card-tallies:start -->
X/Y/Z = **Attacks/Skills/Powers**. Counts beside each rarity heading include drafts and unreviewed designs in this file; upgrades and repeated starting copies do not count separately.
<!-- card-tallies:end -->

## Basic (1/3/0)

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
- **Rules:** Applications add remaining ticks, capped at 5. Each natural tick removes one stack. Base damage is 5, modified by current Mastery but independent of stack count or application source.

#### Shadow Word: Pain

- **Cost:** 1 energy
- **Effect:** Apply a DoT to your target that deals 3 damage for 5 turns. If played, return this card to your hand at the end of your turn.
- **Upgrade:** Cost becomes 0 energy; damage remains 3 per tick.
- **Rules:** Apply 5 stacks, restoring remaining ticks to the cap of 5. Each natural tick removes one stack. Base damage is 3, modified by current Mastery but independent of stack count or application source.

## Common (8/9/3)

### Attacks

#### Mind Flay

- **Cost:** 1 energy
- **Effect:** Deal 2 damage 3 times.
- **Upgrade:** Deal damage 4 times.

#### Blood Magic

- **Cost:** 2 energy
- **Effect:** Deal 18 damage. Lose 3 HP.
- **Upgrade:** Deal 24 damage; HP loss remains 3.
- **Status:** Implemented; full-run balance testing remains.

#### Shadow Word: Death

- **Cost:** 0 energy
- **Effect:** Deal 10 damage. If the target is above 20% health, lose 3 HP each time this card deals damage.
- **Upgrade:** Deal 10 damage twice. Increase the health threshold to 30%.

#### Mind Blast

- **Cost:** 1 energy
- **Effect:** Deal 8 damage, plus 2 damage for each DoT on the target.
- **Upgrade:** Deal 10 damage, plus 3 damage for each DoT on the target.

#### Penance

- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Gain Block equal to the enemy HP removed by this hit.
- **Upgrade:** Deal 8 damage.
- **Rules:** Count only actual HP removed by Penance's direct hit, capped at the target's HP before the hit. Exclude damage absorbed by Block, overkill, DoTs, and Psychic Link. Neither Dexterity nor Frail recalculates this exact HP-to-Block conversion; Strength and other Attack damage modifiers can increase it through the hit's damage.
- **Status:** Implemented; full-run balance testing remains.

#### Atonement

- **Cost:** 1 energy
- **Effect:** Deal 5 damage. Gain 3 Block for each unique DoT on the target.
- **Upgrade:** Deal 7 damage and gain 4 Block per unique DoT.
- **Rules:** Count Touch, Pain, and Madness once each, regardless of stacks. Snapshot the count before damage so a killing blow still grants Block. Grant Block once; Dexterity and other ordinary Block modifiers apply once to the total.
- **Status:** Implemented; full-run balance testing remains.

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

#### Flash Heal

- **Cost:** 1 energy
- **Effect:** Heal 6 HP. Increase this card's cost by 1 this combat.
- **Upgrade:** Heal for 9 HP.
- **Rules:** The cost increase affects only the played copy and resets after combat.
- **Status:** Implemented; full-run balance testing remains.

#### Shadow Mend

- **Cost:** 1 energy
- **Effect:** Heal 10 HP. Add 2 to your Shadow Mend debt. At the start of each turn, lose HP equal to your debt.
- **Upgrade:** Add 1 debt instead of 2; healing remains 10 HP.
- **Rules:** Each cast adds its debt to one debuff lasting this combat. Starting next turn, resolve the combined debt as one HP-loss event each turn; it does not decay. Artifact can prevent a debt application and debuff cleansing can remove accumulated debt, without undoing healing. Tithe Evasion reduces the combined event once, not once per cast. Purify Disease does not remove this debt.
- **Status:** Implemented; full-run balance testing remains.

#### Fade

- **Cost:** 1 energy
- **Effect:** Until your next turn, whenever you would lose HP, lose 1 less, like Tungsten Rod.
- **Upgrade:** Lose 2 less HP instead.

#### Purify Disease

- **Cost:** 0 energy
- **Effect:** Remove Weak, Frail, and Vulnerable from yourself. Exhaust.
- **Upgrade:** Gain Retain.
- **Rules:** Remove only the three named debuffs, not Surrender to Madness's damage penalty or other debuffs.
- **Status:** Implemented; full-run balance testing remains.

#### Dispel Magic

- **Cost:** 0 energy
- **Effect:** Remove all Block from the target.
- **Upgrade:** Also remove all Artifact from the target.
- **Status:** Implemented; full-run balance testing remains.

#### Void Shield

- **Cost:** 1 energy
- **Effect:** Gain 2 Block for each Insanity. Voidform counts as having 10 Insanity.
- **Upgrade:** Gain 2 Insanity first.
- **Rules:** Resolve Insanity gain before calculating Block. All Insanity gain obeys the normal cap, ready/active restrictions, and recovery turn. Both ordinary and permanent Voidform count as 10. Grant Block once; Dexterity and ordinary Block modifiers apply once to the total.
- **Status:** Implemented; full-run balance testing remains.

#### Insight

- **Cost:** 0 energy
- **Effect:** Draw 2 cards. Next turn, draw 1 fewer card.
- **Upgrade:** Draw 3 cards.
- **Rules:** Multiple plays add their next-turn draw penalties. The penalty affects the next turn's normal draw, not every draw effect, and then expires. The upgrade retains the same penalty.
- **Status:** Implemented; full-run balance testing remains.

#### Mind Soothe

- **Cost:** 0 energy
- **Effect:** Until your next turn, the target deals 1 less Attack damage per unique DoT on it.
- **Upgrade:** Reduce its Attack damage by 1 additional point.
- **Rules:** Snapshot the unique DoT count when played. Reduce each Attack hit from that target before Block, with a minimum of zero. Copies add their reduction for this turn. Artifact can block each application. Expires after the enemy turn. With no DoTs, the base card does nothing and the upgrade reduces damage by 1.
- **Status:** Implemented; full-run balance testing remains.


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

#### Tithe Evasion

- **Cost:** 1 energy
- **Effect:** Whenever Blood Magic, Shadow Word: Death, or Shadow Mend causes you to lose HP, lose 1 less. This benefit does not stack.
- **Upgrade:** Lose 2 less instead.
- **Rules:** Only the three named sources qualify, including Shadow Mend's combined debt tick. Exclude all other sources, including enemy Attacks, Beat of Death, Curses, Statuses, relics, and other classes' cards. Floor each qualifying HP loss at zero. Track the highest benefit granted this combat: equal or weaker copies are unplayable; an upgraded copy replaces the base benefit. Reset tracking after combat.
- **Status:** Implemented; full-run balance testing remains.

## Uncommon (4/7/3)

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

#### Piety

- **Cost:** 0 energy
- **Effect:** If your hand has no Attacks, draw 2 Attacks. If your hand has no Skills other than Piety, draw 2 Skills. Exhaust.
- **Upgrade:** Remove Exhaust.
- **Rules:** After the played card leaves the hand, snapshot both conditions before drawing. Ignore all copies of Piety, upgraded or not, when checking for Skills. Both effects can trigger; resolve Attack draws first. Draw random matching cards from the draw pile only, without reshuffling, subject to hand capacity and available matches. Piety remains a Skill eligible to be drawn; its exclusion applies only to the hand check.
- **Status:** Implemented; full-run balance testing remains.

#### Power Word: Barrier

- **Cost:** 3 energy
- **Effect:** Retain your hand this turn. Gain 5 Block for each card in your hand. Dexterity applies per card.
- **Upgrade:** Gain 7 Block per card instead.
- **Rules:** Count cards remaining in hand when the Block effect resolves, excluding the played card. Calculate Block per card with Dexterity and ordinary Block modifiers, including normal rounding and a minimum of zero, then multiply by that hand size. Grant the total once without applying modifiers again. With four cards and 4 Dexterity, gain 36 Block, or 44 upgraded, before other modifiers. Retain the hand for this turn's end only; this does not retain Block or prevent ordinary Ethereal exhaustion.
- **Status:** Implemented; full-run balance testing remains.

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

#### Mental Fortitude

- **Cost:** 2 energy
- **Effect:** Gain 2 Dexterity and 2 Mastery. Lose 2 Strength.
- **Upgrade:** Gain 3 Dexterity and 3 Mastery instead; still lose 2 Strength.
- **Rules:** Repeatable; does not Exhaust. Stats last for combat. The Strength loss follows normal stat-debuff rules, including Artifact.
- **Status:** Implemented; full-run balance testing remains.

### Powers

#### Body and Soul

- **Cost:** 2 energy
- **Effect:** Each incoming Attack hit has a 20% chance to miss.
- **Upgrade:** Increase the chance to 30%.
- **Rules:** Roll independently for each hit, including each hit of a multihit Attack. A miss prevents that hit's damage before Block is consumed. Copies do not add their chances: track the highest benefit granted this combat, with equal or weaker copies unplayable, following Fortitude's rule. The upgrade replaces 20% with 30%. Reset after combat.
- **Status:** Implemented; full-run balance testing remains.

#### Insidious Ire

- **Cost:** 2 energy
- **Effect:** Each direct Attack hit against an enemy with Touch, Pain, and Madness deals 1 additional damage, plus your Mastery.
- **Upgrade:** Increase the base bonus to 2 additional damage, plus your Mastery; the upgrade does not grant Mastery.
- **Rules:** Apply the bonus per hit, including each hit of X-cost Attacks. Use current Mastery and check the target's three DoTs for each hit. The additional damage is max(0, 1 + Mastery), or max(0, 2 + Mastery) when upgraded. Negative Mastery can eliminate the bonus but never make Insidious Ire reduce Attack damage. DoT ticks and Halo do not receive this bonus. Copies stack their per-hit contributions; the bonus enters normal Attack calculations before Voidform amplification.
- **Status:** Implemented; full-run balance testing remains.

#### Psychic Link

- **Cost:** 2 energy
- **Effect:** Each Attack card played triggers Vampiric Touch's damage on all enemies.
- **Upgrade:** Cost becomes 1 energy.


## Rare (2/4/2)

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

#### Petrifying Scream

- **Cost:** 2 energy
- **Effect:** Stun the target for 1 turn. Exhaust.
- **Upgrade:** Cost becomes 1 energy.
- **Rules:** Artifact can block the stun. Natural DoT ticks still resolve during the skipped enemy action. Multiple copies can stun on consecutive turns; validate this interaction during playtesting.
- **Status:** Implemented; full-run balance testing remains.

#### Serendipity

- **Cost:** 0 energy
- **Effect:** Choose one: Discard all Skills and draw that many Attacks; or discard all Attacks and draw that many Skills. Exhaust.
- **Upgrade:** Remove Exhaust.
- **Rules:** Exclude the played Serendipity from the hand and discard count. Count cards actually discarded. Draw random matching cards from the draw pile only, without reshuffling, subject to hand capacity and available matches. Other Serendipity copies are ordinary Skills for this effect.
- **Status:** Implemented; full-run balance testing remains.

### Powers

#### Shadow Committal

- **Cost:** 3 energy
- **Effect:** During Voidform, playing an Attack grants 1 Strength, a Skill grants 1 Dexterity, and a Power grants 1 Mastery. These stats last for the rest of combat.
- **Upgrade:** Cost becomes 2 energy.
- **Rules:** Resolve the stat gain after the triggering card. This Power's own initial play does not trigger its newly applied effect. Copies stack. Sample Voidform eligibility when the card is played, so an activation card played outside Voidform does not retroactively qualify. Keep the proposed values for testing, including interactions with Surrender to Madness and the cost of setting up multiple Powers.
- **Status:** Implemented; full-run balance testing remains.

#### Vampiric Embrace

- **Cost:** 3 energy
- **Effect:** Whenever Vampiric Touch removes enemy HP, heal 1 HP.
- **Upgrade:** Heal 2 HP instead.
- **Rules:** Includes natural and Psychic Link ticks, separately for each enemy. Multiple Embrace copies add their healing. No per-turn cap. A fully prevented tick grants no healing; a damaging tick grants the fixed heal regardless of damage amount. Pain, Madness, Halo, and direct attacks do not trigger it. Normal healing restrictions still apply.

## Legendary (0/0/3)

### Powers

#### Dragonwrath: Tarecgosa's Rest

- **Cost:** Unplayable; equips automatically before the opening hand.
- **Effect:** Each Attack card played has a 20% chance to repeat for free.
- **Upgrade:** Increase the chance to 25%.
- **Rules:** One roll per ordinary Attack play, not per hit or target. Repeat the whole card, including its additional effects and HP costs, with the same X. Repeated/autoplay copies marked for removal cannot trigger Dragonwrath. If the original target dies, choose another living target when possible. Equipment activation is not a Power play.
- **Art:** The actual WoW staff model render floats beside the priest; the card has an orange Legendary frame.
- **Status:** Implemented; full-run balance testing remains.

#### Xal'atath's Blade

- **Cost:** Unplayable; equips automatically before the opening hand.
- **Effect:** Randomly gain 2 Strength, 2 Mastery, or 2 Dexterity, 4 times.
- **Upgrade:** Roll 5 times instead.
- **Rules:** Each roll is independent and equally likely to choose each stat. Rolls occur again each combat, and can all select the same stat. Stats last for combat. Equipment activation is not a Power play.
- **Art:** The actual WoW dagger model render floats beside the priest; the card has an orange Legendary frame.
- **Status:** Implemented; full-run balance testing remains.

#### Val'anyr, Hammer of the Ancient Kings

- **Cost:** Unplayable; equips automatically before the opening hand.
- **Effect:** Gain 5 Block. Retain Block until it is depleted. Once per combat, lethal damage leaves you at 1 HP.
- **Upgrade:** Start with 10 Block instead.
- **Rules:** Starting Block is flat. Reaching exactly zero Block permanently breaks this weapon's retention for the combat, even if you later regain Block; other Blur or Barricade effects remain. The lethal rescue is independent of the retention and can prevent lethal HP loss. It does not protect against a following hit. Equipment activation is not a Power play.
- **Art:** The actual WoW mace model render floats beside the priest; the card has an orange Legendary frame.
- **Status:** Implemented; full-run balance testing remains.

## Agreed relic update: Voidform

- **Additional rule:** You cannot gain Insanity the turn after entering Voidform.
- **Timing:** Enter Voidform and burst on turn 1; all Insanity gain is blocked throughout turn 2, including end-of-turn effects; generation resumes on turn 3.
- **Existing rules:** Insanity still resets when Voidform ends or combat ends. No gain while Voidform is ready or active. The recovery restriction applies to every source, including attacks and Reaping Madness.
- **Status:** Implemented.

## Class design rules

- Legendary cards use the agreed [boss-reward and uniqueness rules](docs/legendary-cards.md): only one Legendary may be in the permanent deck at a time. Owning one excludes all Legendaries from rewards and further acquisition. Each named Legendary can be acquired only once per run and cannot be duplicated into the permanent deck. Equipment activates once per combat before the opening hand, without a Power play or drawable copy. Temporary copies and forced plays cannot activate another Legendary. Legendary support is implemented.
- Mastery adds 1 damage per point to damaging DoT ticks and can be negative, reducing their damage to a minimum of zero. Negative Mastery does not remove DoT stacks or make Madness tick. Mastery is implemented, with a purple version of the Strength icon.
- Touch and Pain have fixed base damage, modified by current Mastery, and cap at 5 remaining ticks from every source, including duration extension and spreading. Stack count and application source do not change their base damage. Madness is the separate accumulating mechanic.
- Shadow Priest class cards do not apply Vulnerable. Access through events, relics, colorless cards, or other outside sources is allowed.
- Sustain is an intended reward for building and setting up the Touch, Psychic Link, and Embrace engine. Validate it against the A20 Heart as well as ordinary encounters; controlled mechanics checks do not establish full-run balance.
