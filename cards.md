# Shadow Priest Card Designs

Cards are grouped by rarity, then type. Upgrades change only the listed values or effects; everything else stays the same.

A trailing `?` on a card name marks an unreviewed design. Removing it means the design is agreed, not necessarily implemented.

The structured [card catalog](design/cards.json) contains 65 collectible designs plus the generated Enter Voidform card. Shared mechanics are defined in [keywords.md](keywords.md). The agreed cards are implemented, including the [setup and defense pass](docs/card-batch-08.md). Full-run balance testing remains.

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

## Common (10/12/4)

### Attacks

#### Mind Flay

- **Cost:** 1 energy
- **Effect:** Deal 2 damage 3 times. After each hit, extend the target's Touch and Pain by 1.
- **Upgrade:** Deal damage 4 times; each hit still extends by 1.
- **Rules:** Resolve each hit followed by its extension before the next hit. Extend only existing Touch and Pain, up to 5 each; do not affect Madness or trigger Misery. Extension does not require HP loss. Stop if the target dies. Each direct hit generates Insanity normally; Psychic Link triggers once per card play.
- **Status:** Implemented.

#### Blood Magic

- **Cost:** 2 energy
- **Effect:** Deal 14 damage. Apply 2 Touch and 2 Pain.
- **Upgrade:** Deal 18 damage; apply 3 Touch and 3 Pain.
- **Rules:** Damage first, then independent Touch and Pain applications on a surviving target. Each can consume Artifact. No self-damage. Replaces both the old implementation and the earlier 18/24-damage draft.
- **Status:** Implemented; full-run balance testing remains.

#### Shadow Word: Death

- **Cost:** 0 energy
- **Effect:** Deal 10 damage. If the target is above 20% health, lose 3 HP each time this card deals damage.
- **Upgrade:** Deal 10 damage twice. Increase the health threshold to 30%.

#### Mind Blast

- **Cost:** 1 energy
- **Effect:** Deal 10 damage, plus 2 per unique DoT on the target. Gain 10 Insanity.
- **Upgrade:** Base damage becomes 12; per-DoT damage becomes 4.
- **Rules:** Snapshot unique DoTs before the hit. Explicit Insanity follows damage even if the target dies, in addition to the universal ten per hit.
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
- **Effect:** Deal 5 damage. Apply 3 Touch. Gain 10 Insanity.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Damage, then apply Touch to the surviving target, then explicit Insanity. Explicit gain does not require HP loss or a successful application.
- **Status:** Implemented; full-run balance testing remains.

#### Shadow Word: Madness

- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Apply Madness equal to HP removed.
- **Upgrade:** Deal 10 damage.
- **Rules:** Use only actual enemy HP removed by this direct hit, capped at HP before the hit. Exclude Block absorption, overkill, DoTs, and Psychic Link. Apply Madness afterward if the target survives; Artifact can block it. Do not trigger an immediate Madness tick. The initial hit counts only DoTs already present when it hits.
- **Status:** Implemented.

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

- **Cost:** 0 energy
- **Effect:** Until your next turn, lose 1 less HP whenever you would lose HP.
- **Upgrade:** Reduce each HP-loss event by 2 instead.
- **Rules:** Preserves existing stacking and HP-loss coverage, including self-damage. No upfront Block. Fully prevented damage generates no damage-taken Insanity.
- **Status:** Implemented; full-run balance testing remains.

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
- **Effect:** Gain 5 Block, plus 1 Block per 10 Insanity. Voidform counts as 100 Insanity.
- **Upgrade:** Base Block becomes 8; gain 1 additional Block per 5 Insanity instead.
- **Rules:** Round the Insanity quotient down. Add Dexterity once through the base component, with no second Dexterity application to the resource bonus. Grant combined Block once with normal Block modifiers. No Insanity gain. Ready or active Voidform uses 100; Recharge keeps the base Block. At 0/50/100 Insanity, base values are 5/10/15 or upgraded 8/18/28.
- **Status:** Implemented; full-run balance testing remains.

#### Shadowy Insight

- **Cost:** 0 energy
- **Effect:** Draw 1 card per unique DoT on the target. Only one Shadowy Insight can be played each turn. Exhaust.
- **Upgrade:** Remove Exhaust; the shared once-per-turn limit remains.
- **Rules:** Snapshot the target's unique DoTs when played, then draw normally. A successful play locks all copies, upgraded or not, for the rest of the turn, even if it draws zero cards. Reset at the start of your next turn and on combat reset. The limit is shared, not per copy.
- **Status:** Implemented.

#### Mind Soothe

- **Cost:** 0 energy
- **Effect:** Until your next turn, the target deals 1 less Attack damage, plus 1 less per unique DoT on it.
- **Upgrade:** Base reduction becomes 2.
- **Rules:** Snapshot unique DoTs when played. Reduction applies per Attack hit, floors damage at zero, stacks for this turn, and is blocked by Artifact.
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

#### Power Word: Fortitude

- **Cost:** 0 energy
- **Effect:** Gain 2 Dexterity. This card's benefit does not stack. Unplayable if Fortitude has already granted at least this much Dexterity this combat.
- **Upgrade:** The benefit becomes 3 Dexterity.
- **Rules:** Track the highest benefit granted by Fortitude separately from other Dexterity sources. Playing the upgrade after the base version grants only 1 additional Dexterity. Equal or weaker copies become unplayable. Losing Dexterity does not allow reapplication. Reset this tracking after combat.

#### Reaping Madness

- **Cost:** 1 energy
- **Effect:** Gain 20 Insanity. At the end of your turn, gain 10 Insanity.
- **Upgrade:** Ongoing gain becomes 20; immediate gain stays 20.
- **Rules:** Immediate gain occurs on play, not each turn. Copies stack ongoing gain. Threshold crossings deliver the retained activation at the next legal opportunity.
- **Status:** Implemented; full-run balance testing remains.

#### Tithe Evasion

- **Cost:** 1 energy
- **Effect:** Whenever Shadow Word: Death or Shadow Mend causes you to lose HP, lose 1 less. This benefit does not stack.
- **Upgrade:** Lose 2 less HP instead.
- **Rules:** Only the two named sources qualify, including Shadow Mend's combined debt tick. Exclude enemy Attacks, Beat of Death, Curses, Statuses, relics, and other sources. Floor each qualifying loss at zero. Track the highest benefit granted this combat: equal or weaker copies are unplayable, and an upgraded copy replaces the base benefit. Reset tracking after combat. Blood Magic's current draft no longer causes HP loss.
- **Status:** Implemented.

#### Shadow Weaving

- **Cost:** 1 energy
- **Effect:** At the end of your turn, gain 2 Block per active DoT across ALL enemies.
- **Upgrade:** Gain 3 Block per active DoT instead.
- **Rules:** Count Touch, Pain, and Madness once each per living enemy before natural ticks, including final one-stack ticks. Combine the total into one Block grant with Dexterity once. No DoTs means no Block, including no Dexterity-only grant. Copies add coefficients before the one grant. Barrier prevents the gain.
- **Status:** Implemented; full-run balance testing remains.

## Uncommon (6/11/4)

### Attacks

#### Atonement

- **Cost:** 1 energy
- **Effect:** Deal 5 damage. Apply 2 Atonement.
- **Upgrade:** Deal 7 damage and apply 3 Atonement.
- **Rules:** Atonement is a combat-long, Artifact-blockable debuff, not a DoT. Each direct Attack hit grants its fixed Block value, including fully blocked hits, without Dexterity scaling. Damage happens before application: replaying Atonement triggers the existing benefit. Applications do not stack; use the higher value (2 becomes 3, never 5 or a downgrade). No Exhaust. Equal or weaker reapplications do not consume Artifact. DoTs, Halo, and retaliation do not trigger it.
- **Status:** Implemented; full-run balance testing remains.

#### Mind Flay: Insanity

- **Cost:** X energy
- **Effect:** Add X stacks to each existing DoT. Deal 6 damage X times.
- **Upgrade:** Add X+1 stacks to each existing DoT and deal damage X+1 times.
- **Rules:** Add stacks only to existing Touch, Pain, and Madness before the hits. Touch/Pain cap at 5; their extension bypasses Artifact and does not trigger Misery. Additional Madness follows normal application rules, including Artifact. Never create a missing DoT. Use effective X from the Haste definition, adding the upgraded extra repetition afterward.
- **Status:** Implemented with shared Haste support.
- **Playtest feedback:** Raised damage per hit from 4 to 6 after the completed run; evaluate this baseline in further runs.

#### Mind Sear

- **Cost:** X energy
- **Effect:** Deal 6 damage to ALL enemies X times.
- **Upgrade:** Repeat the damage X+1 times.
- **Rules:** Uses effective X with Haste and Chemical X. No built-in Madness application. Each actual enemy hit grants ten Insanity; dead targets generate no additional hits.
- **Status:** Implemented; full-run balance testing remains.

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
- **Effect:** Gain 30 Block. Retain Block until depleted. You cannot gain additional Block while this barrier lasts.
- **Upgrade:** Gain 40 Block instead.
- **Rules:** Keep existing Block and gain the printed amount with Dexterity and ordinary Block modifiers applied once. Retain all Block until depleted. While active, prevent all additional Block gains and make other Barriers unplayable. At zero Block, remove only this barrier; independent Barricade, Blur, and Val'anyr effects retain their own rules. Do not activate if no Block remains after the initial gain. No hand retention.
- **Status:** Implemented; full-run balance testing remains.

#### Power Infusion

- **Cost:** 1 energy
- **Effect:** Gain 2 Haste. Exhaust.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Copies add their Haste. At the end of each player turn, lose half the combined Haste, rounding the loss up. Haste is not consumed by X-card plays and works at zero energy.
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

#### Dark Revelation

- **Cost:** 1 energy
- **Effect:** Choose a DoT application card from your draw pile. Put it into your hand. It costs 0 this turn. Exhaust.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Choose visibly even with one option. Direct applications include conditional Shadow Word: Madness, Dark Void, and Void Blast; the chosen card retains its normal application conditions. Existing-only extension and enabling Powers such as Searing Nightmare are excluded. Search only the draw pile; no shuffle or on-draw triggers. A full hand sends the chosen card to discard. Cost resets normally after this turn. The accepted roster currently has no eligible X-cost application cards.
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
- **Effect:** Gain 6 Block. Whenever you play an X-cost card, gain 3 Block X times.
- **Upgrade:** Ongoing Block becomes 5 per instance; upfront stays 6.
- **Rules:** Dexterity applies once to the upfront grant and separately to each later instance. Use effective X including Haste and Chemical X, but not printed X+1 upgrades. Trigger once per X-card play after its effects. Free repeats preserve X. Barrier blocks all gains.
- **Status:** Implemented; full-run balance testing remains.

## Rare (3/4/4)

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

#### Searing Nightmare

- **Cost:** 2 energy
- **Effect:** Your direct Attack hits apply 1 Madness to the enemy hit.
- **Upgrade:** Cost becomes 1 energy.
- **Rules:** Replaces the X-cost Attack. Each direct hit applies one Madness to the surviving target, including fully blocked hits. AoE checks each enemy and multihits trigger per hit. Artifact can block each application. Copies stack their coefficient. DoT ticks, Psychic Link, Halo, and retaliation do not trigger applications. The Power itself deals no damage and applies no immediate DoT.
- **Status:** Implemented; full-run balance testing remains.

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
- **Effect:** Randomly gain 2 Strength, 2 Dexterity, or 2 Mastery, 4 times.
- **Upgrade:** Roll 5 times instead.
- **Rules:** Each roll independently has a one-third chance for Strength, Dexterity, or Mastery. Roll only at combat start; rolls can all select the same stat. All gains last for combat. Haste is excluded. Equipment activation is not a Power play.
- **Art:** The actual WoW dagger model render floats beside the priest; the card has an orange Legendary frame.
- **Status:** Implemented; full-run balance testing remains.

#### Val'anyr, Hammer of the Ancient Kings

- **Cost:** Unplayable; equips automatically before the opening hand.
- **Effect:** Gain 5 Block. Retain Block until it is depleted. Once per combat, lethal damage leaves you at 1 HP.
- **Upgrade:** Start with 10 Block instead.
- **Rules:** Starting Block is flat. Reaching exactly zero Block permanently breaks this weapon's retention for the combat, even if you later regain Block; other Blur or Barricade effects remain. The lethal rescue is independent of the retention and can prevent lethal HP loss. It does not protect against a following hit. Equipment activation is not a Power play.
- **Buff display:** Val'anyr displays two independent buffs: Barricade (Val'anyr), using the base-game Barricade icon, and Guardian Angel, using WoW's Guardian Spirit icon. The first disappears when Block is depleted; Guardian Angel remains until its once-per-combat lethal rescue is spent. Neither effect refreshes the other.
- **Art:** The actual WoW mace model render floats beside the priest; the card has an orange Legendary frame.
- **Status:** Implemented; full-run balance testing remains.

## Agreed relic update: Voidform

- **Additional rule:** Direct Attack hits generate 10 Insanity. Each natural or triggered DoT tick generates 1 Insanity, including Psychic Link. When Voidform ends, reset Insanity and enter Recharge as defined in [keywords.md](keywords.md).
- **Timing:** If ordinary Voidform ends on turn 1, all Insanity gain is blocked throughout turn 2, including end-of-turn effects; generation resumes on turn 3.
- **Existing rules:** Insanity still resets when Voidform ends or combat ends. No gain while Voidform is ready or active. The recovery restriction applies to every source, including attacks, damage taken, and Reaping Madness.
- **Status:** Implemented with revised generation and shared keyword timing.

## Class design rules

- Legendary cards use the agreed [boss-reward and uniqueness rules](docs/legendary-cards.md): only one Legendary may be in the permanent deck at a time. Owning one excludes all Legendaries from rewards and further acquisition. Each named Legendary can be acquired only once per run and cannot be duplicated into the permanent deck. Equipment activates once per combat before the opening hand, without a Power play or drawable copy. Temporary copies and forced plays cannot activate another Legendary. Legendary support is implemented.
- Shared DoT, Mastery, Madness, Insanity, Voidform, Recharge, application, extension, and Haste rules are defined in [keywords.md](keywords.md). The agreed keyword revision is implemented.
- Touch and Pain cap at 5 stacks. Madness has no cap, deals half its stacks rounded up plus Mastery each natural tick, and loses exactly 1 stack. None of these applications triggers an immediate tick unless explicitly stated.
- Insidious Ire is the agreed class-card exception that applies Vulnerable after a start-of-turn three-DoT check. Access through events, relics, colorless cards, or other outside sources remains allowed.
- Sustain is an intended reward for building and setting up the Touch, Psychic Link, and Embrace engine. Validate it against the A20 Heart as well as ordinary encounters; controlled mechanics checks do not establish full-run balance.
