# Shadow Priest Card Designs

Cards are grouped by rarity, then type. Upgrades change only the listed values or effects; everything else stays the same.

A trailing `?` on a card name marks an unreviewed design. Removing it means the design is agreed, not necessarily implemented.

The structured [card catalog](design/cards.json) contains 58 collectible designs plus the generated Enter Voidform card. Shared mechanics are defined in [keywords.md](keywords.md). The agreed cards and shared keywords are implemented in the current Java build. Blood Magic retains its prior 2-energy, 18/24-damage, 3-HP-loss version while its new design remains under review. Tithe Evasion now excludes Blood Magic as agreed. Historical batch notes describe previous builds. Full-run balance testing remains.

<!-- card-tallies:start -->
X/Y/Z = **Attacks/Skills/Powers**. Counts beside each rarity heading include drafts and unreviewed designs in this file; upgrades and repeated starting copies do not count separately.
<!-- card-tallies:end -->

## Basic (1/3/0)

### Attacks

#### Mind Spike

- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Gain 10 Insanity.
- **Upgrade:** Deal 9 damage; the explicit Insanity gain remains 10.
- **Rules:** Resolve the explicit 10 Insanity after the hit, in addition to normal per-DoT hit generation. It does not require a DoT or enemy HP loss. All Insanity gains obey the shared cap and ready, Voidform, and Recharge restrictions.
- **Status:** Implemented.

### Skills

#### Power Word: Shield

- **Cost:** 1 energy
- **Effect:** Gain 5 Block.
- **Upgrade:** Gain 8 Block.

#### Vampiric Touch

- **Cost:** 1 energy
- **Effect:** Apply 3 Touch. If played, return to your hand at turn end.
- **Upgrade:** Cost becomes 0 energy; application remains 3 Touch.
- **Rules:** Return after the normal end-of-turn discard. An unplayed copy discards normally. The upgrade changes cost, not damage or stack count.
- **Status:** Implemented.

#### Shadow Word: Pain

- **Cost:** 1 energy
- **Effect:** Apply a DoT to your target that deals 3 damage for 5 turns. If played, return this card to your hand at the end of your turn.
- **Upgrade:** Cost becomes 0 energy; damage remains 3 per tick.
- **Rules:** Apply 5 stacks, restoring remaining ticks to the cap of 5. Each natural tick removes one stack. Base damage is 3, modified by current Mastery but independent of stack count or application source.

## Common (9/9/3)

### Attacks

#### Mind Flay

- **Cost:** 1 energy
- **Effect:** Deal 2 damage 3 times. After each hit, extend the target's Touch and Pain by 1.
- **Upgrade:** Deal damage 4 times; each hit still extends by 1.
- **Rules:** Resolve each hit followed by its extension before the next hit. Extend only existing Touch and Pain, up to 5 each; do not affect Madness or trigger Misery. Extension does not require HP loss. Stop if the target dies. Each direct hit generates Insanity normally; Psychic Link triggers once per card play.
- **Status:** Implemented.

#### Blood Magic ?

- **Cost:** 2 energy
- **Effect:** Deal 18 damage. Apply 3 Touch.
- **Upgrade:** Deal 24 damage and also apply 5 Pain.
- **Rules:** Resolve damage, then Touch, then upgraded Pain on the surviving target. Applications do not require HP loss. This revision has no self-damage. The draft retains the author's 24-damage upgrade; the proposed lower value has not been adopted.
- **Status:** Revised draft; review pending. The previous implementation does not match this draft.

#### Shadow Word: Death

- **Cost:** 0 energy
- **Effect:** Deal 10 damage. If the target is above 20% health, lose 3 HP each time this card deals damage.
- **Upgrade:** Deal 10 damage twice. Increase the health threshold to 30%.

#### Mind Blast

- **Cost:** 1 energy
- **Effect:** Deal 10 damage, plus 2 for each unique DoT on the target.
- **Upgrade:** Deal 12 base damage and gain 4 damage per unique DoT.
- **Rules:** Count each of Touch, Pain, and Madness once before the hit, regardless of stacks. Apply normal Attack damage modifiers to the total.
- **Status:** Implemented.

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

#### Unfurling Darkness

- **Cost:** 1 energy
- **Effect:** Deal 5 damage. Apply 3 Touch.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Deal damage before applying Touch to the surviving target. Touch application does not require HP loss; Artifact can prevent it.
- **Status:** Implemented.

#### Shadow Word: Madness

- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Apply Madness equal to HP removed.
- **Upgrade:** Deal 10 damage.
- **Rules:** Use only actual enemy HP removed by this direct hit, capped at HP before the hit. Exclude Block absorption, overkill, DoTs, and Psychic Link. Apply Madness afterward if the target survives; Artifact can block it. Do not trigger an immediate Madness tick. The initial hit counts only DoTs already present when it hits.
- **Status:** Implemented.

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
- **Effect:** Remove ALL Block from the target. If any was removed, gain 1 Dexterity.
- **Upgrade:** Also remove ALL Artifact; gain 1 Mastery if any Artifact was removed.
- **Rules:** Check Block and Artifact separately. Each condition grants its stat at most once per cast, regardless of the amount removed. Stats last for combat. Removing Block or Artifact is not a debuff application and is not prevented by Artifact.
- **Status:** Implemented.

#### Void Shield

- **Cost:** 1 energy
- **Effect:** Gain 1 Block for each 5 Insanity. Voidform counts as having 100 Insanity.
- **Upgrade:** Gain 20 Insanity first.
- **Rules:** Resolve Insanity gain before calculating Block. All Insanity gain obeys the normal cap, ready/active restrictions, and recovery turn. Both ordinary and permanent Voidform count as 100. Grant Block once; Dexterity and ordinary Block modifiers apply once to the total.
- **Status:** Implemented; full-run balance testing remains.

#### Shadowy Insight

- **Cost:** 0 energy
- **Effect:** Draw 1 card per unique DoT on the target. Only one Shadowy Insight can be played each turn. Exhaust.
- **Upgrade:** Remove Exhaust; the shared once-per-turn limit remains.
- **Rules:** Snapshot the target's unique DoTs when played, then draw normally. A successful play locks all copies, upgraded or not, for the rest of the turn, even if it draws zero cards. Reset at the start of your next turn and on combat reset. The limit is shared, not per copy.
- **Status:** Implemented.

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
- **Effect:** At the end of your turn, gain 10 Insanity.
- **Upgrade:** Gain 20 Insanity instead.
- **Rules:** Multiple copies stack. All Insanity gain follows the Voidform relic's restrictions, including its recovery turn.

#### Tithe Evasion

- **Cost:** 1 energy
- **Effect:** Whenever Shadow Word: Death or Shadow Mend causes you to lose HP, lose 1 less. This benefit does not stack.
- **Upgrade:** Lose 2 less HP instead.
- **Rules:** Only the two named sources qualify, including Shadow Mend's combined debt tick. Exclude enemy Attacks, Beat of Death, Curses, Statuses, relics, and other sources. Floor each qualifying loss at zero. Track the highest benefit granted this combat: equal or weaker copies are unplayable, and an upgraded copy replaces the base benefit. Reset tracking after combat. Blood Magic's current draft no longer causes HP loss.
- **Status:** Implemented.

## Uncommon (5/10/4)

### Attacks

#### Mind Flay: Insanity

- **Cost:** X energy
- **Effect:** Extend the target's Touch and Pain by X. Deal 4 damage X times.
- **Upgrade:** Extend Touch and Pain by X+1 and deal damage X+1 times.
- **Rules:** Extend only existing Touch and Pain before the hits; do not add Madness stacks or trigger Misery. Use effective X from the Haste definition, adding the upgraded extra repetition afterward.
- **Status:** Implemented with shared Haste support.
- **Playtest feedback:** Feels too weak in the completed run; damage tuning is pending.

#### Mind Sear

- **Cost:** X energy
- **Effect:** Deal 3 damage to all enemies X times.
- **Upgrade:** Deal damage X + 1 times.
- **Status:** Implemented with shared Haste support.
- **Playtest feedback:** Feels underpowered compared with Whirlwind; damage tuning is pending.

#### Void Volley

- **Cost:** X energy
- **Effect:** Hurl X Void Bolts at random enemies. Each deals 8 damage, then adds 1 stack to each existing DoT on its target.
- **Upgrade:** Each bolt deals 10 damage and adds 2 stacks.
- **Rules:** One Attack card play: Psychic Link triggers once, while each hit generates Insanity normally. Choose a living target separately for each bolt, after resolving the previous bolt and its stacks. Never create missing DoTs; Touch/Pain cap at 5. At zero energy, fire no bolts unless an external effect increases X. Haste and Chemical X increase effective X, including at zero energy. Free play preserves energy; Voidform amplifies damage without discounting X.
- **Status:** Implemented with shared Haste support.

#### Dark Void

- **Cost:** 1 energy
- **Effect:** Deal 3 damage to ALL enemies. Apply 3 Pain to each enemy that loses HP.
- **Upgrade:** Deal 5 damage and apply 5 Pain instead.
- **Rules:** Check actual HP removed by this card's direct hit separately for each enemy. Do not apply Pain if Block or damage reduction prevents all HP loss. Apply to surviving qualifying targets; Artifact can block the application. Psychic Link and other incidental damage do not satisfy the condition.
- **Status:** Implemented.

#### Penance

- **Cost:** 1 energy
- **Effect:** Choose an enemy: deal 3 damage 3 times. Or choose yourself: heal 3 HP 3 times. Exhaust.
- **Upgrade:** Repeat the chosen damage or healing effect 4 times instead of 3.
- **Rules:** Choose one mode for the whole play; never both. Remains an Attack in either mode and Exhausts in either mode. Attack cost discounts and Attack-play hooks still apply, including Psychic Link once per play. Strength and Voidform modify only enemy damage, not healing. Healing obeys normal healing restrictions and generates no hit-based Insanity. Stop enemy hits when the target dies. A Dragonwrath repeat preserves the chosen mode; healing repeats still target yourself.
- **Status:** Implemented.

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

#### Power Infusion

- **Cost:** 1 energy
- **Effect:** Gain 2 Haste this turn. Exhaust.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Copies add their Haste. Remove only the Haste granted for this turn at turn end. Haste is not consumed by X-card plays and works at zero energy.
- **Status:** Implemented.

#### Shadow Crash

- **Cost:** 0 energy
- **Effect:** At the start of your next turn, apply 5 Touch to ALL enemies.
- **Upgrade:** Apply Touch immediately instead of at the start of your next turn.
- **Rules:** The base version schedules one application to each living enemy present when it resolves; it does not snapshot the original group. Each application follows Artifact and Misery rules. Neither version triggers an immediate Touch tick. Pending applications end with combat.
- **Status:** Implemented.

#### Halo

- **Cost:** 1 energy
- **Effect:** Gain a Halo aura. At the end of your turn, deal 3 damage to ALL enemies for 3 turns.
- **Upgrade:** Each pulse deals 5 damage instead.
- **Rules:** The first pulse occurs at the end of the casting turn, followed by two more pulses. Each cast creates a separate aura with its own damage and remaining duration. Pulses hit enemies present when they trigger, respect Block, ignore Strength and Voidform's damage bonus, and generate no Insanity. Halo is not a DoT and does not trigger Psychic Link. As a Skill, it receives no Voidform cost discount.

#### Dispersion

- **Cost:** 2 energy
- **Effect:** Gain 1 Intangible. End your turn. Exhaust.
- **Upgrade:** Gain Retain.

#### Crystalline Reflection

- **Cost:** X energy
- **Effect:** Gain 5 Block X times. Gain X Thorns until your next turn.
- **Upgrade:** Gain 7 Block per instance instead.
- **Rules:** Use effective X for both Block instances and temporary Thorns. Apply Dexterity and ordinary Block modifiers separately to each Block instance. Thorns retaliates against enemy Attack hits, including hits fully absorbed by Block; it does not trigger on enemy card plays, DoT ticks, or HP loss. At the start of your next turn, remove only these temporary Thorns, preserving permanent sources. Expiry is a buff-side duration rule, not an Artifact-blockable debuff.
- **Status:** Implemented.

#### Desperate Prayer

- **Cost:** 1 energy
- **Effect:** Before your next turn, the next time an enemy attack causes you to lose HP, heal that much afterward.
- **Upgrade:** Cost becomes 0 energy.

#### Foresight

- **Cost:** 0 energy
- **Effect:** Select and draw 1 Power from your draw pile.
- **Upgrade:** Play the selected Power.
- **Rules:** Choose from the current draw pile, even if only one Power is available. The upgrade visibly presents the chosen card before playing it for free through normal card-play hooks. No eligible Power means no effect; this does not search the discard pile or shuffle.

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
- **Effect:** At the start of your turn, apply 1 Vulnerable to each enemy with Touch, Pain, and Madness.
- **Upgrade:** Cost becomes 1 energy.
- **Rules:** Check each living enemy at the start of the player turn. All three debuffs must have positive stacks then. Artifact can block each application. This is the agreed exception to the class's usual restriction on applying Vulnerable.
- **Status:** Implemented.

#### Psychic Link

- **Cost:** 2 energy
- **Effect:** Each Attack card played triggers Vampiric Touch's damage on all enemies.
- **Upgrade:** Cost becomes 1 energy.

#### Protected Channel

- **Cost:** 2 energy
- **Effect:** Whenever you play an X-cost card, gain 3 Block X times.
- **Upgrade:** Gain 5 Block per instance instead.
- **Rules:** Use that card's effective X, including Haste and Chemical X even at zero energy. A printed X+1 upgrade adds no extra Protected Channel instance. Apply Dexterity and ordinary Block modifiers separately to each instance. Trigger once per qualifying card play and resolve after its effects. Free repeats use their preserved effective X without charging energy or adding bonuses twice.
- **Status:** Implemented.

## Rare (4/4/3)

### Attacks

#### Void Torrent

- **Cost:** X energy
- **Effect:** Deal damage X times to one enemy. Base damage starts at 4 and increases by 4 each hit: 4, 8, 12, and so on.
- **Upgrade:** Start at 5 and increase by 5 each hit.
- **Rules:** Every play starts a new sequence. Calculate normal damage modifiers and rounding separately for each hit. Generate Insanity per hit and trigger Psychic Link once per card. Stop when the selected target dies; never redirect remaining hits. Zero energy gives no hits unless an external effect increases X. Haste and Chemical X increase effective X, including at zero energy. Free play preserves energy; Voidform amplifies damage without discounting X.
- **Status:** Implemented with shared Haste support.

#### Void Blast

- **Cost:** 1 energy
- **Effect:** Deal 10 damage to an enemy. Add its DoT stacks to all other enemies.
- **Upgrade:** Deal 15 damage.
- **Rules:** Capture Touch, Pain, and Madness stacks before the hit, so a killing blow still spreads them. Leave the source unchanged. Add stacks on recipients: Touch and Pain cap at 5, while Madness accumulates normally. Artifact blocks each application separately, in Touch, Pain, Madness order. Do not spread unrelated debuffs or Halo.

#### Catharsis

- **Cost:** 2 energy
- **Effect:** Deal damage equal to your missing HP. Exhaust.
- **Upgrade:** Gain Retain.
- **Rules:** Use maximum HP minus current HP when the hit resolves as base damage, then apply normal Attack damage modifiers. Retain does not lock in a previous missing-HP value.
- **Status:** Implemented.

#### Searing Nightmare

- **Cost:** X energy
- **Effect:** X times: deal 5 damage to ALL enemies, then apply 5 Madness to ALL enemies.
- **Upgrade:** Repeat X+1 times instead of X.
- **Rules:** For each repetition, finish the area Attack hits, then apply Madness to surviving enemies before starting the next repetition. Applications do not require HP loss, but Artifact can block each separately. Madness does not tick immediately. Later hits can generate Insanity from Madness applied by earlier repetitions. Use effective X, including Haste and Chemical X; add the upgrade's extra repetition afterward. Psychic Link triggers once per card play.
- **Status:** Implemented.

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

#### Dark Evangelism

- **Cost:** X energy
- **Effect:** Gain X Mastery.
- **Upgrade:** Gain 1 additional Mastery.
- **Rules:** Use effective X, including Haste and Chemical X even at zero energy. Add the upgrade's extra Mastery afterward. The granted Mastery lasts for combat; this is a Power and resolves as a normal Power play.
- **Status:** Implemented.

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
- **Effect:** Randomly gain 2 Strength, 2 Dexterity, 2 Mastery, or 2 Haste, 4 times.
- **Upgrade:** Roll 5 times instead.
- **Rules:** Each roll independently has a 25% chance for each of the four stats. Rolls occur again each combat and can all select the same stat. All gains last for combat, including Haste. Temporary Haste from Power Infusion stacks with this bonus and expires separately. Equipment activation is not a Power play.
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

- **Additional rule:** Direct Attack hits generate 5 Insanity per unique DoT. When Voidform ends, reset Insanity and enter Recharge as defined in [keywords.md](keywords.md).
- **Timing:** Enter Voidform and burst on turn 1; all Insanity gain is blocked throughout turn 2, including end-of-turn effects; generation resumes on turn 3.
- **Existing rules:** Insanity still resets when Voidform ends or combat ends. No gain while Voidform is ready or active. The recovery restriction applies to every source, including attacks and Reaping Madness.
- **Status:** Implemented with revised generation and shared keyword timing.

## Class design rules

- Legendary cards use the agreed [boss-reward and uniqueness rules](docs/legendary-cards.md): only one Legendary may be in the permanent deck at a time. Owning one excludes all Legendaries from rewards and further acquisition. Each named Legendary can be acquired only once per run and cannot be duplicated into the permanent deck. Equipment activates once per combat before the opening hand, without a Power play or drawable copy. Temporary copies and forced plays cannot activate another Legendary. Legendary support is implemented.
- Shared DoT, Mastery, Madness, Insanity, Voidform, Recharge, application, extension, and Haste rules are defined in [keywords.md](keywords.md). The agreed keyword revision is implemented.
- Touch and Pain cap at 5 stacks. Madness has no cap, deals half its stacks rounded up plus Mastery each natural tick, and loses exactly 1 stack. None of these applications triggers an immediate tick unless explicitly stated.
- Insidious Ire is the agreed class-card exception that applies Vulnerable after a start-of-turn three-DoT check. Access through events, relics, colorless cards, or other outside sources remains allowed.
- Sustain is an intended reward for building and setting up the Touch, Psychic Link, and Embrace engine. Validate it against the A20 Heart as well as ordinary encounters; controlled mechanics checks do not establish full-run balance.
