# Shadow Priest Card Designs

Cards are grouped by rarity, then type. Upgrades change only the listed values or effects; everything else stays the same.

A trailing `?` on a card name marks an unreviewed design. Removing it means the design is agreed, not necessarily implemented.

The structured [card catalog](design/cards.json) contains 75 collectible designs plus the generated Enter Voidform card. Shared mechanics are defined in [keywords.md](keywords.md). The class roster is implemented, including the [setup and defense pass](docs/card-batch-08.md); individual Status fields identify agreed revisions still awaiting implementation and catalog synchronization. Full-run balance testing remains.

<!-- card-tallies:start -->
X/Y/Z = **Attacks/Skills/Powers**. Counts beside each rarity heading include drafts and unreviewed designs in this file; upgrades and repeated starting copies do not count separately.
<!-- card-tallies:end -->

## Basic (1/3/0)

### Attacks

#### Mind Spike

- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Gain 10 Insanity.
- **Upgrade:** Deal 9 damage; the explicit Insanity gain remains 10.
- **Rules:** Resolve the explicit 10 Insanity after the hit, in addition to normal direct-hit generation. It does not require a DoT or enemy HP loss. All Insanity gains obey the shared cap and ready, Voidform, and Recharge restrictions.
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

## Common (11/13/4)

### Attacks

#### Mind Flay
- **Cost:** 1 energy
- **Effect:** Deal 3 damage 3 times. After each hit, extend the target's Touch and Pain by 1.
- **Upgrade:** Deal damage 4 times; each hit still extends by 1.
- **Rules:** Resolve each hit and its extension before the next. Extend only existing Touch and Pain, up to 5; do not affect Madness or trigger Misery. No HP loss required. Stop on death. Haste adds repetitions once, with a green count. Each hit generates Insanity; Psychic Link triggers once per card play.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Blood Magic

- **Cost:** 2 energy
- **Effect:** Deal 15 damage. Apply 3 Touch and 3 Pain. Add a Pain curse to your discard pile.
- **Upgrade:** Deal 20 damage; apply 5 Touch and 5 Pain. Add a Pain curse to your discard pile.
- **Rules:** Damage first, then independent Touch and Pain applications on a surviving target, then add one combat-only Pain Curse to your discard pile. Each debuff application can consume Artifact. Curse creation does not depend on either application succeeding. The card causes no immediate self-damage; the generated Curse retains its normal effect when later drawn. Creation is not a draw and does not change the permanent deck.
- **Status:** Implemented locally for 0.1.3; full-run balance testing pending.

#### Shadow Word: Death

- **Cost:** 0 energy
- **Effect:** Deal 10 damage. If the target loses HP and survives, lose 2 HP. If this kills, return it to your hand.
- **Upgrade:** Deal 10 damage twice. Check backlash after each hit; the kill return is unchanged.
- **Rules:** Check survival after each direct hit. Preserve the existing HP-damage condition: fully blocked or prevented hits cause no backlash. A killing hit causes no backlash, stops remaining hits, and returns the actual played card once through normal card cleanup. Reset the return flag on each play. A kill from Psychic Link or another separate effect does not qualify. Haste adds hits only to the upgraded version. Purged replays cannot leave a card in hand; normal forced Exhaust or Rebound still takes precedence. Backlash uses normal HP-loss prevention and Insanity reactions.
- **Status:** Implemented locally October 29, 2026; full-run balance testing pending.

#### Mind Blast

- **Cost:** 1 energy
- **Effect:** Deal 10 damage, plus 3 per unique DoT on the target. Gain 10 Insanity per unique DoT on the target.
- **Upgrade:** Base damage becomes 14; per-DoT damage becomes 5.
- **Rules:** Snapshot unique DoTs before the hit. Explicit Insanity follows damage even if the target dies, in addition to the universal ten per hit: 10/20/30/40 total for zero/one/two/three DoTs. All gains obey the shared cap and ready, active, and Recharge restrictions. Hovering a target previews damage and explicit bonus Insanity.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Tentacle Slam

- **Cost:** 2 energy
- **Effect:** Deal 8 damage to an enemy. Apply 3 Touch to it and all enemies between you and it. Devour: Stun the target for 1 turn.
- **Upgrade:** Base damage becomes 10; cost becomes 1 energy.
- **Rules:** Consume one Curse before resolving the card. Deal damage, apply Touch to surviving eligible enemies, then, if Devour succeeded, stun the surviving selected target for one turn. Touch applications and the stun independently respect Artifact, in that order. Devour neither refunds Energy nor repeats the card. Touch spread can still resolve if the selected target dies while other enemies remain.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Void Bolt
- **Cost:** 1 energy
- **Effect:** Deal 11 damage. Add 1 stack to each existing DoT on the target.
- **Upgrade:** Deal 15 damage and add 2 stacks instead.
- **Rules:** Damage happens first. Extend existing Touch/Pain up to 5 and add Madness only if already present. Never create a missing DoT or increase future applications. Duration extension follows Mind Flay: Insanity; additional Madness is an application that Artifact can block.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Unfurling Darkness

- **Cost:** 1 energy
- **Effect:** Deal 7 damage. Apply 3 Touch. Gain 10 Insanity. Devour: Affect ALL enemies.
- **Upgrade:** Cost becomes 0 energy; effects remain unchanged.
- **Rules:** Consume one Curse before resolving effects. Without Devour, hit the selected enemy once and apply Touch if it survives. With Devour, hit each living enemy once and apply Touch to each survivor; the original target is not hit a second time. Touch follows normal Artifact and Misery rules. Gain the explicit 10 Insanity once total per card play, in addition to normal Insanity from each direct hit. This remains one Attack play, so Psychic Link and other card-play effects trigger once. Haste does not repeat Devour. The AoE can continue after the original target dies while other enemies remain.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Shadow Word: Madness

- **Cost:** 1 energy
- **Effect:** Deal 10 damage. Apply Madness equal to HP removed. Add a Mind Decay to your hand.
- **Upgrade:** Deal 15 damage instead; application and Curse generation remain unchanged.
- **Rules:** Preserve the existing HP-removed calculation: count only actual enemy HP removed by this direct hit, excluding Block absorption, overkill, DoTs, and Psychic Link. Apply Madness afterward to a surviving target; Artifact can block it. Generate one combat-only Mind Decay before the hit so lethal damage cannot skip creation, regardless of whether Madness is applied. A full hand sends the Curse to discard. No immediate Madness tick.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Shadow Word: Torment

- **Cost:** 1 energy
- **Effect:** Deal 7 damage. Apply 3 Pain and 3 Madness.
- **Upgrade:** Deal 10 damage and apply 5 Pain and 5 Madness.
- **Rules:** Damage first, then Pain, then Madness on a surviving target. Neither application requires HP loss. Each application independently respects Artifact; Pain keeps its five-stack cap.
- **Status:** Implemented locally October 29, 2026; full-run balance testing pending.

#### Mind Trauma

- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Apply 6 Madness. Gain 10 Insanity. Devour: Apply 6 additional Madness.
- **Upgrade:** Deal 9 damage and apply 9 base Madness; explicit Insanity remains 10. Devour bonus remains 6.
- **Rules:** Resolve Devour before applying Madness and combine the application into 12/15 stacks when paid, with one Artifact check. Damage remains 6/9. The additional Madness causes no immediate tick or extra direct hit. Explicit and hit-based Insanity follow their existing rules.
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Void Surge

- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Gain 40 Insanity.
- **Upgrade:** Deal 9 damage. Gain 50 Insanity.
- **Rules:** Explicit Insanity follows the hit and adds to the normal 10 per direct hit: 50/60 total. Normal ready, active, and Recharge restrictions apply.
- **Status:** Implemented; full-run balance testing remains.


### Skills

#### Bulwark

- **Cost:** 2 energy
- **Effect:** Gain 12 Block and 1 Blur. Below 35% HP, repeat this effect once.
- **Upgrade:** Gain 16 Block per grant instead.
- **Rules:** Check current HP once when played, strictly below 35% of maximum HP. At low HP, grant Block and Blur twice: 24/32 Block before modifiers and 2 Blur. Dexterity and other normal card Block modifiers apply to each grant. This is one card play with one conditional repeat, unaffected by Haste and incapable of recursively repeating. At or above 35%, grant only 12/16 Block and 1 Blur. Blur retains Block, not cards. Preview the current total Block and Blur in combat.
- **Status:** Implemented locally October 29, 2026; full-run balance testing pending.

#### Flash Heal

- **Cost:** 1 energy
- **Effect:** Heal 6 HP. Increase this card's cost by 1 this combat.
- **Upgrade:** Heal for 9 HP.
- **Rules:** The cost increase affects only the played copy and resets after combat.
- **Status:** Implemented; full-run balance testing remains.

#### Shadow Mend

- **Cost:** 1 energy
- **Effect:** Heal 10 HP. Add 2 to your Shadow Mend debt. Devour: Do not gain Shadow Mend debt.
- **Upgrade:** Heal 14 HP; still add 2 debt unless Devour prevents it.
- **Rules:** Choose and exhaust one Curse before deciding whether this cast adds debt. Successful Devour prevents only this cast's debt; it does not clear existing debt. Both versions add 2 debt without Devour. Do not briefly apply and remove prevented debt or consume Artifact for it. Combined debt causes HP loss at each subsequent player turn start and lasts for combat. Healing obeys normal restrictions.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Fade

- **Cost:** 0 energy
- **Effect:** Until your next turn, lose 2 less HP whenever you would lose HP.
- **Upgrade:** Reduce each HP-loss event by 4 instead.
- **Rules:** Preserves existing stacking and HP-loss coverage, including self-damage. No upfront Block. Fully prevented damage generates no damage-taken Insanity.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Purify Disease

- **Cost:** 0 energy
- **Effect:** Remove Weak, Frail, and Vulnerable from yourself. Devour: Gain 1 Artifact. Exhaust.
- **Upgrade:** Gain Retain; all other effects remain unchanged.
- **Rules:** Cleanse first, then exhaust a Curse if available and grant Artifact only when Devour succeeds. Playable without a Curse or the named debuffs. Choose when multiple Curses are in hand. Do not remove Shadow Mend debt or Surrender's deadline. The upgrade no longer grants unconditional Artifact. Both versions Exhaust; upgrading only improves timing.
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Dispel Magic

- **Cost:** 0 energy
- **Effect:** Remove ALL Block from the target. If any was removed, gain 1 Dexterity.
- **Upgrade:** Also remove ALL Artifact; gain 1 Mastery if any Artifact was removed.
- **Rules:** Check Block and Artifact separately. Each condition grants its stat at most once per cast, regardless of the amount removed. Stats last for combat. Removing Block or Artifact is not a debuff application and is not prevented by Artifact.
- **Status:** Implemented.

#### Void Shift
- **Cost:** 1 energy
- **Effect:** Remove ALL Block from the target enemy. Gain that much Block.
- **Upgrade:** Gain Retain.
- **Rules:** Remove the target's current Block and grant the amount actually removed as flat Block to the player. Dexterity, Frail, and other card Block modifiers do not alter the transfer; the normal player Block cap still applies. Removal is not damage or a debuff and bypasses Artifact. With no enemy Block, remove and gain zero. Display the current transfer amount when hovering a target. Gained Block is ordinary Block, not Barrier. This replaces Void Shield; preserve its internal card ID for saves.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Shadowy Insight

- **Cost:** 0 energy
- **Effect:** Draw 1 card per unique DoT on the target. Only one Shadowy Insight can be played each turn. Exhaust. Devour: Do not Exhaust this card.
- **Upgrade:** Draw 1 additional card.
- **Rules:** Lock all copies for the turn when this card is played, including when the draw count is zero. Snapshot unique DoTs, then choose and exhaust a Curse already in hand before drawing. A Curse drawn by this card cannot pay for its own Devour. Draw the snapshot count, plus one if upgraded. Successful Devour prevents this play's printed Exhaust only; both versions otherwise Exhaust. Other forced Exhaust or card-removal effects retain their normal behavior. Reset the shared lock at the next player turn and combat reset. Haste does not increase this for-each draw count or Devour.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Mind Soothe

- **Cost:** 0 energy
- **Effect:** Until your next turn, ALL enemies deal 2 less Attack damage, plus 1 less per unique DoT on them.
- **Upgrade:** Base reduction becomes 3; reduction per unique DoT becomes 2.
- **Rules:** Snapshot unique DoTs separately for each living enemy when played. Each enemy receives its own debuff application, independently blocked by Artifact. Reduction applies per Attack hit, floors damage at zero, stacks for this turn, and expires after the enemy turn. The combat preview shows the current reduction, or a range when enemies differ.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Void Tendrils

- **Cost:** 1 energy
- **Effect:** Gain 6 Block for each enemy. Apply 2 Pain to ALL attacking enemies.
- **Upgrade:** Gain 8 Block per enemy and apply 3 Pain.
- **Rules:** Count living enemies. Grant Block separately per enemy, applying Dexterity and other Block modifiers to every grant. Check attack intents when played, including attack/buff, attack/debuff, and attack/defend. Only those enemies receive Pain. Artifact blocks application, not Block.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Encroaching Shadows
- **Cost:** 1 energy
- **Effect:** Gain 8 Block. Apply 8 Madness.
- **Upgrade:** Gain 11 Block and apply 11 Madness.
- **Rules:** Block resolves first with Dexterity once. Artifact can prevent Madness but not Block. No immediate DoT tick.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Focused Will

- **Cost:** 1 energy
- **Effect:** Gain 8 Block. Gain 10 Insanity. Devour: Convert all your Block to Barrier.
- **Upgrade:** Gain 11 Block and 15 Insanity instead.
- **Rules:** Consume one Curse before resolving the card. Grant the card's Block with Dexterity and normal Block modifiers once, then gain Insanity under the usual restrictions. If Devour succeeded, set the Barrier reserve to the player's current total Block, including this card's grant and any existing Barrier. Conversion does not add Block, reapply modifiers, or count as another Block-gain event. At zero Block, create no Barrier. This is one card play with one Devour, no repeats, and no recursion. Future ordinary Block gains do not increase the reserve.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Madness Overwhelming

- **Cost:** 1 energy
- **Effect:** Apply 10 Madness, plus 5 for each Power played this combat. Add a Mind Decay to your hand.
- **Upgrade:** Apply 15 Madness, plus 8 for each Power played this combat. Curse generation remains unchanged.
- **Rules:** Count actual Power plays this combat, including free Power plays through Foresight+ and Shadowy Committal+, but excluding automatic Legendary equipment. Display the current calculated Madness application. Artifact can block the combined application; it causes no immediate tick. Generate one combat-only Mind Decay in hand regardless of whether Madness succeeds. A full hand sends the Curse to discard. Creation is not a draw; a Curse successfully placed in hand is immediately available to later Devour cards.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

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
- **Effect:** During your turn, lose 2 less HP from each damage source. Stacks.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Each copy adds 2 HP-loss prevention during the player turn, including start-of-turn, card-play and end-of-turn effects. Covers damaging Curses, self-damage, Thorns and Beat of Death. Block is consumed normally before reducing remaining HP loss, floored at zero. Prevented HP loss grants neither damage-taken Insanity nor Catharsis damage. Provides no protection during the enemy phase, generates no healing, and cannot prevent Surrender's death deadline. Both versions have the same prevention; upgrading changes only cost.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Shadow Weaving

- **Cost:** 1 energy
- **Effect:** At the end of your turn, gain 2 Block per active DoT across ALL enemies.
- **Upgrade:** Gain 3 Block per active DoT instead.
- **Rules:** Count Touch, Pain, and Madness once each per living enemy before natural ticks, including final one-stack ticks. Combine the total into one flat Block grant, like Orichalcum: Dexterity, Frail, and card Block modifiers do not change it. No DoTs means no Block. Copies add coefficients before the one grant. Barrier allows this ordinary Block gain without increasing its reserve.
- **Status:** Implemented; full-run balance testing remains.

## Uncommon (7/14/5)

### Attacks

#### Atonement

- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Apply 3 Atonement. Exhaust.
- **Upgrade:** Deal 9 damage and apply 5 Atonement instead.
- **Rules:** Atonement is a combat-long debuff, not a DoT. Applications add their amounts; every application independently respects Artifact. Each direct Attack hit on the afflicted enemy grants fixed Block equal to its current Atonement, including fully blocked hits, without Dexterity scaling. Damage happens before application, so another Atonement card triggers any existing benefit before adding stacks. Both versions Exhaust. DoTs, Halo and retaliation do not trigger Atonement. Its Block is ordinary Block and does not increase an existing Barrier reserve.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Mind Flay: Insanity

- **Cost:** X energy
- **Effect:** Add X stacks to each existing DoT on the target. Deal 6 damage X times. Trigger its Touch and Pain X times.
- **Upgrade:** Add X+1 stacks, deal damage X+1 times, and trigger its Touch and Pain X+1 times.
- **Rules:** Calculate effective X once, including Haste and Chemical X, then add the upgraded extra repetition once. First add that many stacks to each existing DoT; never create a missing DoT. Touch/Pain extension respects their caps, bypasses Artifact and does not trigger Misery. Additional Madness follows normal application rules, including Artifact. Resolve the Attack hits, then the rounds of bonus Touch and Pain ticks against a surviving target. Never trigger bonus Madness ticks; its stacks still increase with the other existing DoTs and its natural turn-end tick is unchanged. Bonus ticks use current Mastery, remove no stacks, and do not add further stacks. They grant normal tick-based Insanity and eligible Touch healing, but do not count as Attack hits or extra card plays. Psychic Link triggers once for the card play. Skip further damage and ticks once the target is dead.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Mind Sear

- **Cost:** X energy
- **Effect:** Deal 6 damage to ALL enemies X times. After each hit, apply 2 Madness.
- **Upgrade:** Repeat the damage X+1 times.
- **Rules:** Calculate effective X once using Haste and Chemical X; the upgrade adds one damage wave afterward. Each wave hits every living enemy once. After each hit, apply 2 Madness to that enemy if it survives, even if the hit was fully blocked. Artifact independently checks each application. Each actual enemy hit grants normal hit-based Insanity; dead targets generate no additional hits or applications. Printed Madness application and Searing Nightmare are separate effects. Protected Channel counts waves, not enemies hit or Madness applications.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Void Volley
- **Cost:** X energy
- **Effect:** Hurl X Void Bolts at random enemies. Each deals 11 damage, then adds 1 stack to each existing DoT on its target. When you enter Voidform with this in hand, play it for free.
- **Upgrade:** Each bolt deals 15 damage and adds 2 stacks.
- **Rules:** Use the same 11/15 damage and 1/2-stack extension values as Void Bolt. Each copy in hand at actual Voidform entry is eligible once for the shared left-to-right automatic-play sequence with Shadowy Committal+. Finish each card before starting the next; cards drawn afterward do not join the sequence. If still in hand when its turn arrives, play it for free using current Energy plus Haste and Chemical X without spending Energy. It counts as one Attack play and discards normally. Extending an existing form does not trigger it. Psychic Link triggers once; each actual hit follows normal Insanity rules. Select a living target for each bolt and resolve its damage and extension before selecting the next. Never create missing DoTs; extension follows Void Bolt's cap and Artifact rules. At zero Energy, only external X bonuses generate bolts. Voidform amplifies damage without discounting X.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Dark Void

- **Cost:** 1 energy
- **Effect:** Deal 6 damage to ALL enemies. Apply 3 Pain to each enemy that loses HP. Add a Mind Decay to your hand.
- **Upgrade:** Deal 10 damage and apply 5 Pain instead.
- **Rules:** Generate one combat-only Mind Decay before damage so a combat-ending hit cannot skip creation. A full hand sends it to discard; creation is not a draw. Generate one Curse per card play regardless of enemy count or successful Pain applications. Check actual HP removed by this card's direct hit separately for each enemy. Apply Pain only to surviving targets that lost HP; Artifact can block each application. Psychic Link and other incidental damage do not satisfy the condition.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Penance
- **Cost:** 1 energy
- **Effect:** Choose an enemy: deal 3 damage 3 times. Or choose yourself: heal 3 HP 3 times. Exhaust.
- **Upgrade:** Repeat the chosen damage or healing effect 4 times instead of 3.
- **Rules:** Choose one mode for the whole play. Haste adds repetitions once in either mode; display the modified count in green. Remains an Attack and Exhausts in both modes. Strength and Voidform modify only damage. Healing obeys normal restrictions and generates no hit-based Insanity. Attack-play hooks apply once per play. Dragonwrath preserves the selected mode and does not gain additional replay triggers from Haste.
- **Status:** Implemented in 0.1.2; full-run balance testing remains.

#### Singularity
- **Cost:** 2 energy
- **Effect:** Deal 3 damage per DoT stack on the target, increased by Mastery. Remove all its DoTs before dealing damage.
- **Upgrade:** Deal 5 damage per DoT stack on the target, increased by Mastery. Remove all its DoTs before dealing damage.
- **Rules:** Consume all current Touch, Pain and Madness. Resolve one hit for stacks multiplied by max(0, coefficient + Mastery). Strength does not modify it. Normal Attack modifiers, including Voidform, Weak and Vulnerable, apply. Block applies normally. No stacks means no hit or hit-based Insanity; otherwise one hit grants 10 Insanity. Other debuffs are unchanged. Hit-triggered effects may reapply Madness after consumption. Hovering the target shows calculated damage beside the first sentence. Singularity FX uses the calculated hit: below 50 small, 50–99 medium, and 100+ dramatic.
- **Status:** Implemented in 0.1.2; full-run balance testing remains.
- **Notes:** This is meant to be a late-run pick when all DoT set up has been solved.


### Skills

#### Piety

- **Cost:** 0 energy
- **Effect:** If your hand has no Attacks, draw 2 Attacks. If your hand has no Skills other than Piety, draw 2 Skills. Exhaust.
- **Upgrade:** Remove Exhaust.
- **Rules:** After the played card leaves the hand, snapshot both conditions before drawing. Ignore all copies of Piety, upgraded or not, when checking for Skills. Both effects can trigger; resolve Attack draws first. Draw random matching cards from the draw pile only, without reshuffling, subject to hand capacity and available matches. Piety remains a Skill eligible to be drawn; its exclusion applies only to the hand check.
- **Status:** Implemented; full-run balance testing remains.

#### Power Word: Barrier

- **Cost:** 3 energy
- **Effect:** Refresh your Barrier to 30.
- **Upgrade:** Refresh your Barrier to 40 instead.
- **Rules:** Barrier is a tracked portion of total Block, spent before ordinary Block. Damage reduces its counter first. At turn start only ordinary Block expires; do not re-grant retained Block or reapply Dexterity. Dexterity and ordinary Block modifiers apply once on cast. Recasts refill only the missing reserve to the modified card value, never stack or reduce a stronger reserve, and preserve ordinary Block. The 999 Block cap limits the reserve to Block actually added. Other Block gains do not change the reserve. Remove the buff at zero. Explicit Block removal consumes Barrier first; HP loss bypasses it. Independent Barricade, Blur, and Val'anyr can retain ordinary Block; Calipers removes up to 15 ordinary Block. No hand retention.
- **Status:** Implemented; full-run balance testing remains.

#### Power Infusion
- **Cost:** 0 energy
- **Effect:** Gain 2 Haste. Haste does not decay for 2 turns. Exhaust.
- **Upgrade:** Prevent Haste decay for 3 turns instead.
- **Rules:** Copies add 2 Haste. Skip the next two end-of-turn Haste decays, or three when upgraded, including the casting turn. Recasts set protection to the greater of its remaining duration and the new cast's duration; they neither add durations nor shorten existing protection. This protects the entire combined Haste pool. With no other sources, a base cast keeps 2 Haste for the casting turn and the next two turns, then decays to 1 at the third turn end; an upgraded cast delays that first decay until the fourth turn end. Haste is not consumed by plays and works at zero Energy. Both versions cost 0 and Exhaust.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Shadow Crash

- **Cost:** 0 energy
- **Effect:** At the start of your next turn, apply 5 Touch to ALL enemies.
- **Upgrade:** Apply Touch immediately instead of at the start of your next turn.
- **Rules:** The base version schedules one application to each living enemy present when it resolves; it does not snapshot the original group. Each application follows Artifact and Misery rules. Neither version triggers an immediate Touch tick. Pending applications end with combat.
- **Status:** Implemented.

#### Halo
- **Cost:** 1 energy
- **Effect:** Gain a Halo aura. At the end of your turn, heal 2 HP and deal 4 damage to ALL enemies for 3 turns.
- **Upgrade:** Each pulse heals 3 HP and deals 6 damage instead.
- **Rules:** The first pulse occurs at the end of the casting turn, followed by two more pulses. Each cast creates a separate aura with its own damage, healing and duration. Heal once per aura pulse, not once per enemy, immediately before that pulse's damage so lethal damage cannot discard the heal. Healing obeys normal restrictions and stops when combat ends; Details counts only actual HP restored. Damage hits enemies present at the pulse, respects Block, ignores Strength and Voidform's damage bonus, and generates no Insanity. Halo is not a DoT, does not trigger Psychic Link or Vampiric Embrace, and gains no extra pulses from Haste. As a Skill, it receives no Voidform cost discount.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Dispersion

- **Cost:** 2 energy
- **Effect:** Gain 1 Intangible. End your turn. Exhaust.
- **Upgrade:** Gain Retain.

#### Crystalline Reflection

- **Cost:** X energy
- **Effect:** Gain 6 Block X times. Gain X Thorns until your next turn.
- **Upgrade:** Gain 8 Block per instance instead.
- **Rules:** Use effective X for both Block instances and temporary Thorns. Apply Dexterity and ordinary Block modifiers separately to each Block instance. Thorns retaliates against enemy Attack hits, including hits fully absorbed by Block; it does not trigger on enemy card plays, DoT ticks, or HP loss. At the start of your next turn, remove only these temporary Thorns, preserving permanent sources. Expiry is a buff-side duration rule, not an Artifact-blockable debuff.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

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
- **Effect:** Gain 2 Dexterity and 2 Mastery. Add a Doubt Curse to your hand.
- **Upgrade:** Cost becomes 1 energy; effect remains unchanged.
- **Rules:** Repeatable; does not Exhaust. Stats last for combat. Each play generates one combat-only Doubt; it no longer reduces Strength. Curse creation is not a debuff application and cannot be prevented by Artifact. Creation is not a draw and does not trigger Dark Reinforcement. Full hands send the Curse to discard. The permanent deck is unchanged, so generated Curses do not increase Inescapable Torment's starting bonus.
- **Status:** Implemented locally for 0.1.3; full-run balance testing pending.

#### Dark Revelation

- **Cost:** 1 energy
- **Effect:** Choose a DoT application card from your draw pile. Put it into your hand. It costs 0 this turn. Exhaust.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Choose visibly even with one option. Direct applications include conditional Shadow Word: Madness, Dark Void, and Void Blast; the chosen card retains its normal application conditions. Existing-only extension and enabling Powers such as Searing Nightmare are excluded. Search only the draw pile; no shuffle or on-draw triggers. A full hand sends the chosen card to discard. Cost resets normally after this turn. Mind Sear qualifies as a direct DoT application after its accepted rework. If retrieved, its X cost remains X; setting a retrieved card's cost to zero does not grant free repetitions or change effective X.
- **Status:** Implemented; full-run balance testing remains.

#### Twin Suns

- **Cost:** 1 energy
- **Effect:** If the target has positive Strength, gain that much Haste. Devour: Gain 2 Haste.
- **Upgrade:** If the target has positive Strength, gain that much Haste plus 1. Devour bonus remains 2.
- **Rules:** The Devour bonus is independent of enemy Strength and works at zero or negative Strength. With positive Strength, add both rewards. The upgrade's existing extra 1 still requires positive enemy Strength. Does not Exhaust or reduce enemy Strength. Apply normal Haste decay; Void Vengeance's separate reward can also trigger.
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Malady of the Mind

- **Cost:** 0 energy
- **Effect:** Double the target's Madness. Add a Mind Decay to your discard pile. Exhaust.
- **Upgrade:** Triple the target's Madness. Add a Mind Decay to your discard pile. Exhaust.
- **Rules:** Add stacks equal to current Madness times (multiplier minus one) when the effect resolves. Artifact blocks the addition. Creates no missing Madness and does not immediately tick it. Generate one combat-only Mind Decay regardless of whether Madness increased, including when the target has no Madness or Artifact blocks the addition. Creation is not a draw and does not change the permanent deck. Mind Decay is the retained Curse defined below; Mental Decay is a separate rare Skill.
- **Status:** Implemented locally for 0.1.3; full-run balance testing pending.

#### Void Entropy

- **Cost:** 2 energy
- **Effect:** Enter Voidform. Draw 2 cards.
- **Upgrade:** Enter Voidform. Draw 3 cards.
- **Rules:** Directly enter at 100 Insanity without generating an activation token. Direct entry clears current Recharge; ending the new form causes normal Recharge again. Consume an existing ready charge and remove its tokens. Already-active Voidform only gets the draw, without restarting its Attack counter, triggering entry relics, or weakening permanent Surrender. Successful entry triggers normal relics and Details. Draw exactly the printed amount; no extra activation-card draw.
- **Status:** Implemented; full-run balance testing remains.


### Powers

#### Body and Soul

- **Cost:** 2 energy
- **Effect:** Each incoming Attack hit has a 20% chance to miss.
- **Upgrade:** Increase the chance to 30%.
- **Rules:** Each copy independently contributes a 20% miss chance, or 30% when upgraded, for each incoming Attack hit. Combined miss chance is 1 minus the product of each copy's hit chance: two upgrades give 51%; two upgrades and one base copy give 60.8%. Roll independently per incoming hit, including multihits. A miss prevents damage before consuming Block. Additional copies remain playable and preserve their individual upgrade values. The effect does not prevent DoTs or HP-loss effects. Reset the accumulated benefits after combat.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

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
- **Cost:** 1 energy
- **Effect:** Gain 6 Block. Whenever you play an X-cost card, gain 3 Block per X-based tick.
- **Upgrade:** Ongoing Block becomes 5 per tick; upfront Block stays 6.
- **Rules:** A tick is one repetition or unit of the card's primary X-based effect, including non-damaging effects. Compute effective X once from Energy, Haste and Chemical X, then include a printed extra primary-effect unit such as X+1 once. Mind Sear counts damage waves, regardless of enemy count. Mind Flay: Insanity counts its primary repetitions once, not its separate stack additions and DoT ticks. Crystalline Reflection counts its Block repetitions, not its additional Thorns. Dark Evangelism counts its X-based Mastery units, even when granted together. Thus effective X=3 gives three grants for each base card; Mind Sear+, Mind Flay: Insanity+ and Dark Evangelism+ give four, while Crystalline Reflection+ still gives three. Dexterity applies once to the upfront Block and never to triggered Block; other ordinary Block modifiers still apply. Copies stack their ongoing values. Free X plays work at zero Energy using the same bonuses; preserved-X replays do not add bonuses again. Triggered Block is ordinary Block and does not increase Barrier.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Dark Reinforcement

- **Cost:** 1 energy
- **Effect:** Whenever you draw a Curse, gain 1 Energy and draw 1 card.
- **Upgrade:** Also trigger when you Devour a Curse.
- **Rules:** Each copy grants 1 Energy and draws 1 card on an actual Curse draw. Upgraded copies also grant those benefits when a Curse is successfully Devoured. Drawing and later Devouring the same Curse are separate qualifying events. Base copies do not reward Devour. Creation, retention, tutors that put cards into hand, and Necronomicurse returning are not draws. Ordinary Curse exhaustion is not Devour. Copies stack the benefits for events they support. No Mastery is granted. Additional draws use normal draw, shuffle and hand-limit rules.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.


## Rare (4/5/5)

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
- **Effect:** For every unblocked point of damage taken this combat, deal 3 damage. Increase to 5 if damage was taken while this card is in your hand instead. Exhaust.
- **Upgrade:** Gain Retain.
- **Rules:** Track each combat copy independently, starting at zero before Strength. Actual HP lost adds 5 damage per HP while this copy is in hand, or 3 otherwise. Ignore blocked/prevented loss and out-of-combat damage. Healing does not reduce stored damage. Normal Attack modifiers apply to the resulting hit. Exhaust; upgraded Retain does not freeze the counter. Temporary equivalent copies inherit the current counter; fresh cards start at zero. No permanent-deck growth.
- **Status:** Implemented in 0.1.2; full-run balance testing remains.
- **Notes:** Catharsis should reward self healing and long sustain in a drawn-out battle. At its previous implementation, it rewarded being low hp going into new combats.

#### Void Ray

- **Cost:** 1 energy
- **Effect:** Deal 15 damage. Devour: Permanently increase this card's damage by 5. Exhaust.
- **Upgrade:** Deal 23 damage; Devour permanently increases damage by 8 instead.
- **Rules:** Wording conversion only. Preserve the current visible Curse selection and lethal-hit handling. Deal damage before applying permanent growth; the bonus does not increase this hit. Exhaust the selected Curse only for combat, trigger normal exhaust reactions, and preserve existing permanent-card identity and copy rules. The final Exhaust applies to Void Ray itself.
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

### Skills

#### Surrender to Madness

- **Cost:** 0 energy
- **Effect:** Playable only after leaving Voidform twice this combat (0/2). Enter permanent Voidform. Gain 2 Strength and 2 Mastery now and at the start of each turn. Die at the end of your fifth turn. Exhaust.
- **Upgrade:** Gain 3 Strength and 3 Mastery immediately and at each subsequent turn start instead.
- **Rules:** Track completed Voidform activations per combat, advancing only when an active Voidform actually ends. Entering or refreshing Voidform does not count. Show progress inline as (0/2), (1/2), or (2/2), capped for display; reset it each combat. Once unlocked, Surrender can bypass Recharge. Grant the stats immediately on successful activation, then at the start of each subsequent player turn. Permanent Voidform retains its normal Attack damage bonus and 1-energy discount; this proposal does not make all Attacks free. The casting turn is turn one of five. Show a visible countdown starting at 5; resolve friendly end-of-turn effects, including DoTs, before the final death check. Winning combat cancels the countdown. Do not allow another Surrender to restart the timer or repeat its activation reward while already surrendered. Stats and countdown do not carry between combats. The playability check blocks both ordinary play and forced use before two exits. The deadline bypasses all revival effects and damage prevention.
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Mass Dispel
- **Cost:** 1 energy
- **Effect:** Remove all enemies' Block, Artifact, and positive Strength.
- **Upgrade:** Removes all negative effects from yourself as well.
- **Rules:** Remove enemy Block, Artifact and positive Strength while preserving negative Strength and powers that generate future Strength or Block. Removal bypasses Artifact. The upgrade additionally removes removable player Debuffs, including negative Strength, Dexterity and Mastery. It never removes protected mechanics such as Surrender's deadline. Both versions can be played when no eligible effects are present and do not Exhaust.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Petrifying Scream
- **Cost:** 2 energy
- **Effect:** Stun the target for 1 turn. Exhaust.
- **Upgrade:** Stun all enemies for 1 turn. Exhaust.
- **Rules:** Artifact can block the stun. Natural DoT ticks still resolve during the skipped enemy action. Multiple copies can stun on consecutive turns; validate this interaction during playtesting.
- **Status:** Implemented in 0.1.2; full-run balance testing remains.

#### Serendipity

- **Cost:** 0 energy
- **Effect:** Retain. Choose one: Discard all Skills and draw that many Attacks; or discard all Attacks and draw that many Skills. Exhaust.
- **Upgrade:** Remove Exhaust; keep Retain.
- **Rules:** Retain in both versions. Exclude the played Serendipity from the hand and discard count. Count cards actually discarded. Draw random matching cards from the draw pile only, without reshuffling, subject to hand capacity and available matches. Other Serendipity copies are ordinary Skills for this effect.
- **Status:** Implemented locally October 29, 2026; full-run balance testing pending.

#### Mental Decay

- **Cost:** 1 energy
- **Effect:** Apply 10 Madness to ALL enemies. Add a Mind Decay to your hand.
- **Upgrade:** Apply 15 Madness to ALL enemies. Add a Mind Decay to your hand.
- **Rules:** Generate one combat-only Mind Decay: an Unplayable Curse with Retain that causes 1 HP loss at the start of your turn. Its penalty is not a card play. Creation is not a draw. Full hands send the Curse to discard; Retain survives redraw and copying. Artifact blocks each enemy's Madness application separately.
- **Status:** Implemented; full-run balance testing remains.


### Powers

#### Searing Nightmare

- **Cost:** 1 energy
- **Effect:** Your direct Attack hits apply 2 Madness to the enemy hit.
- **Upgrade:** Apply 4 Madness instead.
- **Rules:** Each direct Attack hit applies 2 Madness, or 4 when upgraded, to a surviving enemy, including fully blocked hits. AoE checks each enemy and multihits trigger per hit. Artifact checks each application. Copies add their coefficients. DoTs, Psychic Link, Halo and retaliation do not trigger it. Playing this Power deals no damage and applies no DoT itself. Both versions cost 1; upgrading changes the application amount.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Shadowy Committal

- **Cost:** 3 energy
- **Effect:** During Voidform, playing an Attack grants 1 Strength, a Skill grants 1 Dexterity, and a Power grants 1 Mastery. These stats last for the rest of combat.
- **Upgrade:** When you enter Voidform with this in hand, play it for free.
- **Rules:** Resolve stat gains after the triggering card. This Power's own initial play does not trigger its newly applied effect, but already active copies can reward later Power plays. Copies stack. Sample Voidform eligibility when a card is played. The upgrade retains its 3-energy manual cost and auto-plays free only if it was in hand when Voidform was entered. Snapshot eligible Shadowy Committal+ and Void Volley cards together in visible left-to-right hand order; finish each card before the next. Play each eligible card at most once if still in hand. Newly drawn cards do not join the sequence, and extending an existing form does not retrigger it. Automatic plays count as normal card plays; only subsequently played cards benefit from a newly resolved Committal. Rename the display name while preserving Shadow Committal's internal ID.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Vampiric Embrace

- **Cost:** 2 energy
- **Effect:** Whenever Vampiric Touch removes enemy HP, heal 1 HP. Whenever you Devour a Curse, heal 1 HP.
- **Upgrade:** Heal 2 HP on both conditions instead.
- **Rules:** Touch healing includes natural ticks and bonus ticks from Psychic Link or Mind Flay: Insanity, separately for each enemy. A fully prevented tick grants no healing; an actual HP-removing Touch tick grants the fixed heal regardless of damage amount. Separately, each successfully Devoured Curse grants the fixed heal once; ordinary Curse exhaustion does not qualify. Multiple Embrace copies add their healing for both conditions. No per-turn cap. Pain, Madness, Halo and direct Attack damage do not trigger Touch healing. Normal healing restrictions apply.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Dark Evangelism

- **Cost:** X energy
- **Effect:** Gain X Mastery.
- **Upgrade:** Gain 1 additional Mastery.
- **Rules:** Use effective X, including Haste and Chemical X even at zero energy. Add the upgrade's extra Mastery afterward. The granted Mastery lasts for combat; this is a Power and resolves as a normal Power play.
- **Status:** Implemented.

#### Event Horizon

- **Cost:** 2 energy
- **Effect:** Whenever you play a Power, gain 2 Haste.
- **Upgrade:** Whenever you play a Power, gain 3 Haste.
- **Rules:** Copies stack. A newly played copy does not trigger itself; existing copies trigger when another is played. Actual free Power plays qualify; automatically equipped Legendaries do not. Haste uses its usual shared decay.
- **Status:** Implemented; full-run balance testing remains.


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
- **Upgrade:** Each of the 4 rolls grants 3 of the chosen stat instead of 2.
- **Rules:** Both versions roll exactly 4 times. Each roll independently has a one-third chance for Strength, Dexterity, or Mastery and grants 2 of the chosen stat (3 upgraded), for 8/12 total stat points. Roll only at combat start; rolls can all select the same stat. All gains last for combat. Haste is excluded. Equipment activation is not a Power play.
- **Art:** The actual WoW dagger model render floats beside the priest; the card has an orange Legendary frame.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

#### Val'anyr, Hammer of the Ancient Kings

- **Cost:** Unplayable; equips automatically before the opening hand.
- **Effect:** Gain Barricade for combat. Once per combat, lethal damage leaves you at 1 HP.
- **Upgrade:** Also start with 10 Block.
- **Rules:** Base grants no opening Block; upgraded opening Block is a flat 10. Barricade lasts for the entire combat and survives reaching zero Block, so subsequently gained Block is still retained; other Blur or Barricade effects remain independent. The lethal rescue is independent of the retention and can prevent lethal HP loss. It does not protect against a following hit. Equipment activation is not a Power play.
- **Buff display:** Val'anyr displays two independent buffs: Barricade (Val'anyr), using the base-game Barricade icon, and Guardian Angel, using WoW's Guardian Spirit icon. Barricade remains for the entire combat, including at zero Block; Guardian Angel remains until its once-per-combat lethal rescue is spent. Neither effect refreshes the other.
- **Art:** The actual WoW mace model render floats beside the priest; the card has an orange Legendary frame.
- **Status:** Released in 1.0.1 after local playtesting; structured catalog synchronized.

## Generated Curses (excluded from collectible tallies)

#### Mind Decay

- **Type:** Curse; generated by Shadow Word: Madness, Madness Overwhelming, Dark Void, Mental Decay, and Malady of the Mind, never a reward or random event Curse.
- **Cost:** Unplayable.
- **Effect:** Retain. At the start of your turn, lose 1 HP.
- **Upgrade:** None.
- **Rules:** The penalty bypasses Block and does not scale with Strength, Weak, or Vulnerable. Only copies already in hand at turn start trigger, before the normal draw. The penalty is not a card play or draw, and cannot resolve after combat ends. Ordinary and retained Block cannot absorb this HP loss. Normal HP-loss reactions apply only if damage removes HP. Blue Candle and Void Vengeance can play/exhaust it normally; other Curse/exhaust synergies recognize it. Full hands send it to discard, and copies/redraw retain its identity and Retain. Generated copies last only for combat.
- **Status:** Implemented; replaces the Retained Decay prototype.

## Agreed relic update: Voidform

- **Additional rule:** Direct Attack hits generate 10 Insanity. Each natural or triggered DoT tick generates 1 Insanity, including Psychic Link. When Voidform ends, reset Insanity and enter Recharge as defined in [keywords.md](keywords.md).
- **Timing:** If ordinary Voidform ends on turn 1, all Insanity gain is blocked throughout turn 2, including end-of-turn effects; generation resumes on turn 3.
- **Existing rules:** Insanity still resets when Voidform ends or combat ends. No gain while Voidform is ready or active. The recovery restriction applies to every source, including attacks, damage taken, and Reaping Madness.
- **Status:** Implemented with revised generation and shared keyword timing.

## Class design rules

- Legendary cards use the agreed [boss-reward and uniqueness rules](docs/legendary-cards.md): only one Legendary may be in the permanent deck at a time. Owning one excludes all Legendaries from rewards and further acquisition. Each named Legendary can be acquired only once per run and cannot be duplicated into the permanent deck. Equipment activates once per combat before the opening hand, without a Power play or drawable copy. Temporary copies and forced plays cannot activate another Legendary. Legendary support is implemented.
- Shared DoT, Mastery, Madness, Insanity, Voidform, Recharge, application, extension, and Haste rules are defined in [keywords.md](keywords.md). November 4 keyword revisions are implemented locally; full-run balance testing remains.
- Touch and Pain cap at 5 stacks. Madness has no cap, deals half its stacks rounded up plus Mastery each natural tick, and loses exactly 1 stack. None of these applications triggers an immediate tick unless explicitly stated.
- Insidious Ire is the agreed class-card exception that applies Vulnerable after a start-of-turn three-DoT check. Access through events, relics, colorless cards, or other outside sources remains allowed.
- Sustain is an intended reward for building and setting up the Touch, Psychic Link, and Embrace engine. Validate it against the A20 Heart as well as ordinary encounters; controlled mechanics checks do not establish full-run balance.

### Formula previews

During combat, formula cards show their currently calculated output in parentheses. Target-dependent results update when hovering a target. Haste-modified repetition counts and effective X appear in green. Conditional future outcomes remain conditional; previews do not promise future triggers or damage through prevention.

### Conditional highlights

Gold borders indicate an available conditional benefit: Devour with a Curse in hand, Bulwark below 35% HP, either Piety draw condition, useful cleansing/dispelling, Twin Suns against positive Strength, Shadow Word: Death lethal direct hits, and Surrender after two completed Voidforms. Targeted borders use the hovered enemy, or any qualifying enemy when no target is hovered. Relevant clauses turn gold; target-specific text waits for a hovered enemy. Formula-only scaling keeps its numerical previews. Death previews account for hit count, Haste, Block, Intangible, Buffer, and Invincible; Malleable is conservatively excluded rather than promising lethal through changing Block.
