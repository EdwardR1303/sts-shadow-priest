# Shadow Priest Proposed Card Designs

Discussion draft, October 25, 2026. Nothing in this file is approved or implemented. A trailing `?` marks every proposed card revision, new card, or mechanic. The accepted roster remains in [cards.md](cards.md); current shared rules remain in [keywords.md](keywords.md). New names are working names; ability references and art can be settled after design review.

Cards follow the same rarity/type layout and Cost / Effect / Upgrade / Rules format as cards.md. **Revision** replaces an existing design; **New** adds a collectible. Do not add revisions as duplicate cards. Header X/Y/Z counts describe the proposals shown here, not the resulting full roster.

The priorities are reliable common DoT application, defense that works before setup is complete, and an earlier first Voidform. These are a menu of starting points, not a recommendation to adopt every buff at once. No new Vulnerable sources are proposed; Insidious Ire remains the existing exception. No additional healing is proposed: blocking lethal damage and funding setup need their own answers.

Current ordinary rewards contain **51 cards: 18 Attacks / 23 Skills / 10 Powers**. There are **58 collectible designs** including four Basics and three Legendaries; the generated Enter Voidform token is a separate 59th catalog entry. Blood Magic's DoT draft is not yet the playable implementation.

| Rarity | Current A/S/P | New cards here A/S/P | If all eight new cards were added A/S/P |
|---|---:|---:|---:|
| Basic | 1/3/0 | 0/0/0 | 1/3/0 |
| Common | 9/9/3 | 2/3/1 | 11/12/4 |
| Uncommon | 5/10/4 | 0/1/1 | 5/11/5 |
| Rare | 4/4/3 | 0/0/0 | 4/4/3 |
| Legendary | 0/0/3 | 0/0/0 | 0/0/3 |
| Collectible total | 19/26/13 | 2/4/2 | 21/30/15 |

Ordinary rarity totals would become **27 Common / 21 Uncommon / 11 Rare**, approximately **46% / 36% / 19%**, versus the current 41% / 37% / 22% (rounded). Their type split would be **20 Attacks / 27 Skills / 12 Powers**, approximately 34% / 46% / 20%, close to the current 35% / 45% / 20%. The full collectible pool would be 66, leaving nine slots if 75 remains the target. These are pool proportions, not the game's reward rarity probabilities. No rarity moves are necessary for this pass.

The 12 saved normal-profile runs contain these recorded reward choices: Unfurling Darkness **0/10**, Blood Magic **1/18**, Fade **2/18**, Mind Soothe **2/16**, Mind Blast **4/22**, Reaping Madness **4/21**, Protected Channel **2/7**. Shadow Word: Madness was selected **11/13** times. Counts mean chosen/offered; skips count as declined offers, shops and event gains are excluded, and upgrade suffixes are combined. This small sample spans changing builds and Ascensions 0–2; it suggests review targets, not proven balance rankings. We do not have reliable historical draw/play or Voidform-use rates.

There are currently two non-Basic common Touch starters (Unfurling Darkness and Tentacle Slam), one independent Madness starter (Shadow Word: Madness), and **zero Pain starters at common**. Existing-only extension is not application. Rare Void Blast can spread a DoT that exists elsewhere but cannot seed one from nothing. If the application proposals below were all accepted, common starters would be Touch **3**, Pain **3**, and Madness **3**, distributed across eight distinct cards; Blood Magic supplies two types.

## Common (6/7/2)

### Attacks

#### Unfurling Darkness ?

- **Proposal:** Revision; priority A. Keeps its cheap Touch role and adds resource generation.
- **Cost:** 1 energy
- **Effect:** Deal 5 damage. Apply 3 Touch. Gain 10 Insanity.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Damage, then application on a surviving target, then explicit Insanity. Gain the explicit Insanity even if the target dies or Artifact blocks Touch. The hit counts only DoTs already present when it lands. All normal Insanity restrictions apply.
- **Why:** The 0/10 recorded pick rate suggests that a standalone Touch application competing with the returning starter card is not attractive enough. This gives it a distinct early-resource role without raising Touch's damage or cap.
- **Watch:** Free upgraded copies plus Psychic Link; the benefit must not become an automatic pick for every deck.

#### Blood Magic ?

- **Proposal:** Alternative revision to the existing unaccepted draft; priority B. This does not overwrite the author's 18/24-damage draft.
- **Cost:** 2 energy
- **Effect:** Deal 14 damage. Apply 2 Touch and 2 Pain.
- **Upgrade:** Deal 18 damage and apply 3 Touch and 3 Pain instead.
- **Rules:** Resolve damage, then Touch, then Pain on the surviving target. Each application independently respects Artifact. No HP-loss requirement for application and no self-damage in this proposal. Damage happens before its new DoTs can generate hit-based Insanity. Touch/Pain caps and tick damage are unchanged.
- **Why:** Both base and upgrade become a two-DoT setup card. The lower direct damage pays for adding Pain to the base version. This can replace starter DoT cards rather than merely adding another payoff that needs them.
- **Watch:** Two applications in one card interact strongly with Psychic Link and the proposed application-to-Block engine. Compare this version against the existing draft, not against an assumption that the draft is already live.

#### Mind Blast ?

- **Proposal:** Revision; priority B. A single-hit resource option.
- **Cost:** 1 energy
- **Effect:** Deal 10 damage, plus 2 for each unique DoT on the target. Gain 10 Insanity.
- **Upgrade:** Deal 12 base damage and gain 4 damage per unique DoT; explicit Insanity stays 10.
- **Rules:** Snapshot DoTs before damage. Resolve the explicit gain after the hit, in addition to normal per-DoT generation, even if the hit kills. Preserve all Insanity restrictions.
- **Why:** Gives the 4/22-picked card a role alongside multi-hit Mind Flay. A resource deck should not require multi-hit/X cards to reach its signature stance.
- **Watch:** This is deliberately a better reward-card Strike, but should not displace every other direct Attack. Test after the setup improvements before adopting a global generation buff too.

#### Atonement ?

- **Proposal:** Revision; priority A. Give it a defensive floor.
- **Cost:** 1 energy
- **Effect:** Deal 5 damage. Gain 4 Block, plus 2 for each unique DoT on the target.
- **Upgrade:** Deal 7 damage. Gain 6 Block, plus 2 per unique DoT.
- **Rules:** Snapshot the DoT count before damage. Grant one combined Block amount after the hit, including on a kill; apply Dexterity and ordinary Block modifiers once. At zero/two/three DoTs, base Block is 4/8/10; upgraded Block is 6/10/12.
- **Why:** Current Atonement gives no Block before setup. This improves setup turns while keeping the upgraded three-DoT ceiling at its current 12 Block.

#### Shadow Word: Torment ?

- **Proposal:** New; priority A. Common Pain application on an Attack.
- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Apply 3 Pain.
- **Upgrade:** Deal 9 damage and apply 5 Pain instead.
- **Rules:** Damage first, then apply to the surviving target. Application does not require HP loss; Artifact can prevent it. No immediate DoT tick.
- **Why:** Establishes an ordinary common alternative to the starter Pain card. It offers immediate damage and application rather than requiring Pain to already exist.

#### Mind Trauma ?

- **Proposal:** New; priority A. Reliable Madness application plus a little resource generation.
- **Cost:** 1 energy
- **Effect:** Deal 4 damage. Apply 4 Madness. Gain 10 Insanity.
- **Upgrade:** Deal 7 damage and apply 6 Madness; explicit Insanity stays 10.
- **Rules:** Damage, then application on a surviving target, then explicit Insanity. Madness application does not require HP loss and can be blocked by Artifact. The direct hit counts only prior DoTs. Explicit Insanity follows the same rules as the Unfurling Darkness proposal.
- **Why:** Shadow Word: Madness can scale with attack damage, while this card reliably establishes Madness through enemy Block. Its lower direct damage buys reliability and resource gain.
- **Watch:** Added Madness accumulates without a cap. Check sustained scaling and Mastery rather than assessing only the first tick.

### Skills

#### Bulwark ?

- **Proposal:** Revision; priority B. A modest dependable defense improvement.
- **Cost:** 2 energy
- **Effect:** Gain 12 Block and 1 Blur.
- **Upgrade:** Gain 16 Block instead.
- **Rules:** Dexterity applies once to the Block grant. Blur is unchanged; no hand retention.
- **Why:** Gives a two-energy defensive commitment more immediate protection than the current 10/15 without making it an engine on its own.
- **Watch:** Keep this secondary to filling common application and setup-defense gaps; its 4/11 pick count is not evidence of a dead card.

#### Fade ?

- **Proposal:** Revision; priority A. Add immediate Block to its per-hit protection.
- **Cost:** 1 energy
- **Effect:** Gain 4 Block. Until your next turn, lose 1 less HP whenever you would lose HP.
- **Upgrade:** Gain 6 Block and reduce each HP-loss event by 2 instead.
- **Rules:** Dexterity affects only the one Block grant. Preserve the existing reduction timing, stacking, expiry, and HP-loss coverage. Fully prevented damage still gives no damage-taken Insanity.
- **Why:** Current Fade is excellent against many small hits but weak against a single large attack. A small upfront grant makes it usable in both situations without adding healing.
- **Watch:** Multihit encounters and Surrender to Madness; the upgrade already provides valuable per-event reduction.

#### Mind Soothe ?

- **Proposal:** Revision; priority B. Remove its zero-DoT dead-draw case.
- **Cost:** 0 energy
- **Effect:** Until your next turn, the target deals 1 less Attack damage, plus 1 less per unique DoT on it.
- **Upgrade:** Base reduction becomes 2; the per-DoT reduction stays 1.
- **Rules:** Snapshot unique DoTs on play. Reduction applies per Attack hit, floors damage at zero, stacks with other copies for this turn, and is blocked by Artifact as before. Base reduction ranges from 1 to 4; upgraded from 2 to 5.
- **Why:** Makes it help while establishing DoTs, when the class currently needs defense most.
- **Watch:** Heart-style multihits. Do not combine this automatically with a large cost reduction on another defensive engine.

#### Void Shield ?

- **Proposal:** Revision; priority B. A floor, not a higher ceiling.
- **Cost:** 1 energy
- **Effect:** Gain 1 Block per 5 Insanity, with a minimum of 5 Block.
- **Upgrade:** Gain 20 Insanity first.
- **Rules:** Calculate max(5, floor(effective Insanity / 5)), then apply Dexterity and normal Block modifiers once. Voidform counts as 100. The upgrade respects all resource restrictions. The floor is a base value before Frail or negative Dexterity, not guaranteed final Block.
- **Why:** Helps at combat start and after Voidform empties the meter. The existing maximum remains 20 before modifiers.
- **Watch:** Compare with buffing Atonement/Fade first. Multiple floor fixes may make basic Shield removal too obvious too early.

#### Void Tendrils ?

- **Proposal:** New; priority A. Common Pain application while defending.
- **Cost:** 1 energy
- **Effect:** Gain 6 Block. Apply 2 Pain to an enemy.
- **Upgrade:** Gain 9 Block and apply 3 Pain instead.
- **Rules:** Block resolves first with Dexterity once. Application respects Artifact; blocked application does not cancel Block. No direct damage or hit-based Insanity.
- **Why:** Provides the defensive alternative to Shadow Word: Torment. Both add access to Pain, but one preserves tempo through damage and the other through protection.

#### Encroaching Shadows ?

- **Proposal:** New; priority A. Common Madness application while defending.
- **Cost:** 1 energy
- **Effect:** Gain 5 Block. Apply 3 Madness to an enemy.
- **Upgrade:** Gain 8 Block and apply 5 Madness instead.
- **Rules:** Block resolves first with Dexterity once. Artifact can prevent Madness but not the Block. Never trigger an immediate Madness tick.
- **Why:** Adds a Madness source independent of attack damage, Strength, Voidform, and enemy Block. This is the defensive counterpart to Mind Trauma and the scaling Attack Shadow Word: Madness.
- **Watch:** Repeatable Madness application can become long-fight damage scaling; these numbers should be tested against ordinary enemies as well as bosses.

#### Focused Will ?

- **Proposal:** New; priority B. Resource generation for defending successfully.
- **Cost:** 1 energy
- **Effect:** Gain 7 Block. Before your next turn, the next enemy Attack hit grants you 10 Insanity.
- **Upgrade:** Gain 10 Block and grant 15 Insanity instead.
- **Rules:** Dexterity applies once. Trigger once after the next non-dodged enemy Attack hit, even if Block or Intangible prevents HP loss. Do not trigger from self-damage, DoTs, Beat of Death, or HP-loss effects. Expire unused at the start of your next turn. Multiple casts add the promised Insanity into one pending trigger, rather than creating repeated triggers for a multihit attack. Actual HP loss can also grant its usual 5 Insanity. Normal ready/active/Recharge restrictions apply and the trigger is consumed even if those restrictions prevent the gain.
- **Why:** Creates an explicit way to earn Insanity while preventing damage. This addresses the tension between the class's damage-taken generation and its need to survive.
- **Watch:** Prefer this contained card experiment to changing every blocked hit into a global resource source immediately.

### Powers

#### Reaping Madness ?

- **Proposal:** Revision; priority A. Give the resource engine an immediate return.
- **Cost:** 1 energy
- **Effect:** Gain 20 Insanity. At the end of your turn, gain 10 Insanity.
- **Upgrade:** End-of-turn gain becomes 20; immediate gain stays 20.
- **Rules:** Immediate gain happens on play, not again each turn. Copies stack their ongoing gain. All gains obey the existing restrictions; end-of-turn threshold crossings deliver the retained activation card when the next legal opportunity occurs.
- **Why:** Base Reaping currently takes several turns to matter. This changes first-turn generation from 10 to 30, or from 20 to 40 upgraded, while preserving the ongoing values.
- **Watch:** Test without a simultaneous threshold reduction. More frequent availability should still require choosing when to activate.

#### Shadow Weaving ?

- **Proposal:** New; priority A. A common application-to-Block engine.
- **Cost:** 1 energy
- **Effect:** Whenever you directly apply a DoT, gain 3 Block. Triggers up to 3 times each turn.
- **Upgrade:** Gain 4 Block per trigger instead.
- **Rules:** Qualifying effects must be able to create that DoT if absent; use the same direct-application distinction as Misery. Refreshing capped Touch/Pain qualifies if the application succeeds. Artifact-blocked applications do not. Existing-only maintenance from Mind Flay, Mind Flay: Insanity, Void Bolt, or Void Volley does not qualify. Each enemy/DoT application is a potential trigger, up to the shared three-trigger limit; a two-DoT card can trigger twice, and AoE cannot exceed the limit. Apply Dexterity once per Block grant. Copies add Block per trigger but share the three-trigger limit, reset at the start of each player turn. Barrier's Block lock still prevents the gain.
- **Why:** Lets the player defend by doing the class's basic setup and upkeep, without needing an uncommon X engine. Returning starter DoTs gain a defensive role rather than being mandatory solely for damage access.
- **Watch:** Zero-cost upgraded starter DoTs, Mastery decks, and future Misery applications. The trigger cap is essential; do not start with unlimited per-enemy triggers.

## Uncommon (1/1/2)

### Attacks

#### Mind Sear ?

- **Proposal:** Revision; priority B. An uncommon AoE Madness starter.
- **Cost:** X energy
- **Effect:** Deal 5 damage to ALL enemies X times. After the first repetition, apply 2 Madness to ALL enemies.
- **Upgrade:** Repeat the damage X+1 times instead.
- **Rules:** Use effective X, including Haste and Chemical X. Apply Madness once per card resolution, after the first set of hits and before later repetitions, to surviving enemies regardless of HP loss. If there are zero repetitions, do not apply Madness. Artifact can block each application. Dragonwrath's separate repeated resolution can apply it once again. The first hits use prior DoTs; later hits can count newly applied Madness.
- **Why:** Gives Mind Sear an identity beyond area X damage and puts AoE Madness below Rare. Searing Nightmare retains its much larger per-repetition Madness scaling.
- **Watch:** Additional Insanity from later hits across multiple enemies. This may reduce the need for a global generation buff substantially.

### Skills

#### Dark Revelation ?

- **Proposal:** New; priority B. A setup tutor.
- **Cost:** 1 energy
- **Effect:** Choose a card from your draw pile that can apply a DoT. Put it into your hand. It costs 0 this turn. Exhaust.
- **Upgrade:** Cost becomes 0 energy.
- **Rules:** Search only the current draw pile; no reshuffle. Eligible cards can directly apply Touch, Pain, or Madness when absent, including conditional applications such as Shadow Word: Madness. Existing-only extension cards are ineligible. Void Blast is eligible because it can apply a missing DoT to recipients, but still needs its own seeded source. The selected card keeps its normal conditions; tutoring does not guarantee its application succeeds. Put into hand rather than treating this as a normal draw. Preserve normal hand limits. A selected X-cost card is played without spending energy but uses the normal free-X rules; do not turn its X into a literal zero or add bonus X.
- **Why:** More application cards improve draft access; a tutor improves combat access as the deck grows. This is a supplement to common starters, not a replacement for them.
- **Watch:** Free Searing Nightmare, Blood Magic, and other expensive setup cards. If this dominates, restrict eligibility to non-X cards or change the discount to 1 energy rather than buffing its draw.

### Powers

#### Protected Channel ?

- **Proposal:** Revision; priority B. Reduce the danger of playing the engine itself.
- **Cost:** 2 energy
- **Effect:** Gain 6 Block. Whenever you play an X-cost card, gain 3 Block X times.
- **Upgrade:** Ongoing Block becomes 5 per instance; upfront Block stays 6.
- **Rules:** Apply Dexterity once to the upfront Block grant; retain the existing per-instance Dexterity and effective-X rules for later triggers. The Power's initial play is not an X-card trigger. Barrier's lock applies to both immediate and ongoing Block.
- **Why:** The existing engine has a setup tax of two energy and no immediate protection. Keep its specialization and payoff, but help it survive the turn it enters play.
- **Watch:** Do not lower its cost and add upfront Block simultaneously. Haste already supports it on an additional turn now.

#### Protective Shadows ?

- **Proposal:** New; priority A. Steady defense for maintaining different DoTs.
- **Cost:** 1 energy
- **Effect:** At the end of your turn, gain 2 Block for each different DoT type affecting enemies.
- **Upgrade:** Gain 3 Block per type instead.
- **Rules:** Count Touch, Pain, and Madness at most once each across all living enemies, not once per enemy. Snapshot just before natural DoT ticks and grant the combined Block once. A one-stack DoT counts for its final tick. Dexterity applies once to the total; with no DoTs, grant no Block and do not create a Dexterity-only gain. Copies add the per-type coefficient before the single grant. Barrier's gain lock applies. Maximum base output per copy is 6, or 9 upgraded, before modifiers.
- **Why:** Offers a non-X defensive engine and rewards broad DoT coverage in both single-target and multi-enemy fights. It does not need Voidform to be available first.
- **Watch:** Multiple copies, Fortitude/Mental Fortitude, Blur, and Val'anyr. Check boss sustain without making ordinary encounters automatic stalls.

## Rare (0/0/0)

No new Rare cards or Rare buffs proposed in this pass. The missing reliable setup should exist before Rare rewards. Improve access to existing Madness and Voidform payoffs before raising their ceilings.

## Generated (0/1/0)

### Skills

#### Enter Voidform ?

- **Proposal:** Revision to the generated token; optional priority C, not a new collectible.
- **Cost:** 0 energy
- **Effect:** Enter Voidform. Draw 1 card. Retain. Exhaust.
- **Upgrade:** None; remains unupgradable.
- **Rules:** Draw only after a valid activation succeeds. Keep the one-charge guard, full-hand delivery protection, temporary-copy restrictions, and Recharge unchanged. No energy grant. Cannot draw from stale or invalid tokens. Surrender to Madness does not inherit this token-specific draw.
- **Why:** Reaching 100 late in a turn can leave few cards to use the stance. One draw gives activation a small immediate payoff without changing the damage multiplier or Attack discount.
- **Watch:** Retain already lets the player wait. Test whether the problem is earning the token too late in combat, rather than merely having a weak activation hand, before adding this.

## Mechanic proposals

These are alternative experiments, not card counts or accepted keyword changes. Keep the 100-point meter, on-demand retained activation, +50% Attack damage, cost discount, and Recharge unless a proposal explicitly says otherwise. Do not implement every resource experiment together.

Current arithmetic explains the ramp problem: with Touch and Pain already present, a Mind Spike earns 20 Insanity (10 explicit + 10 from the hit), while a three-hit Mind Flay earns 30. Playing the two starter application Skills costs two energy and generates no Insanity itself. Three subsequent Mind Spikes still total only 60. Damage-taken generation adds just 5 per HP-damaging event, whether that event costs 1 HP or 30 HP. These rules can delay the first activation in a short fight without requiring a threshold or delivery bug; actual activation frequency still needs turn-level logging. Recharge affects repeat activations, so shortening it would not address a missing first Voidform.

#### First application bonus ?

- **Change:** Once per enemy per combat, successfully applying each of Touch, Pain, and Madness for the first time grants 10 Insanity.
- **Rules:** Track enemy identity and DoT type, not current debuff presence. Refreshing, extending, or allowing a DoT to expire and reapplying cannot earn the bonus again. Artifact-blocked applications do not count. Spread and direct applications qualify when they first establish the type; summons are new enemies. All gain restrictions apply, and an establishment during ready/active/Recharge still spends that enemy/type's opportunity. Natural ticks do not generate Insanity.
- **Why:** Rewards setup immediately. Establishing Touch and Pain on a single target contributes 20 toward the first Voidform; three types contribute 30. It favors application rather than further amplifying multi-hit cards.
- **Watch:** AoE can award 60–90 across three enemies. This is the preferred global experiment only if card-level changes are insufficient; test it separately from the opening-reserve proposal and the recommended new common relic implementation.

#### Opening Insanity reserve ?

- **Change:** Start every combat with 20 Insanity after the normal reset.
- **Rules:** This is a fixed reset value, not carryover from the preceding combat. Use ordinary gain/delivery rules for any additional relic bonus. Shadowy Preparation, once implemented, would add its 10 per starting enemy on top; that combination needs separate review.
- **Why:** A clean alternative for shortening the first ramp without increasing damage or benefiting every subsequent multihit loop. Keep the threshold at 100 and the resource's thematic scale intact.
- **Watch:** An eventual Shadowy Preparation plus this change starts single-target fights at 30 and three-enemy fights at 50. Do not adopt both automatically. This is an alternative to first-application generation, not an extra baseline recommendation.

#### Minimum Insanity per direct hit ?

- **Change:** Each direct Attack hit grants max(5, 5 × unique DoTs) Insanity, rather than zero when no DoTs are present.
- **Rules:** One total grant per hit, not a new bonus added on top. A no-DoT or one-DoT hit grants 5; two DoTs grant 10; three grant 15. Mind Spike's explicit 10 remains additional. Preserve existing blocked-hit, kill, AoE, ready/active, and Recharge rules. DoT ticks and Halo remain excluded.
- **Why:** The simplest fallback if drawing no application cards continues to disable the signature mechanic entirely.
- **Watch:** This disproportionately buffs multihit/X cards and makes DoT-free builds more viable. Lower priority than rewarding first applications or improving individual commons; do not raise the per-DoT coefficient globally in the same pass.

## Suggested first review batch

Start with **Unfurling Darkness, Atonement, Fade, and Reaping Madness revisions**, plus **Shadow Word: Torment, Encroaching Shadows, and one of Shadow Weaving / Protective Shadows**. This supplies common Pain, a defensive Madness source, useful pre-setup defense, and a resource bridge. Choose the preferred Blood Magic direction separately; its current draft and this alternative are mutually exclusive.

Then evaluate Mind Trauma, Void Tendrils, and the Mind Sear revision if independent application still feels scarce. Leave global Insanity experiments and token draw until this smaller package has had a few runs. No new healing, Rare payoff, or Legendary is needed to diagnose these gaps.

For playtests, record the turn of first successful DoT application, first 100 Insanity, and first Voidform activation; fights ending with an unused token; damage taken before setup; and defensive cards drawn without their prerequisites. A provisional target worth discussing is a meaningful first activation around turns 2–3 in an ordinary 3–5-turn fight, not compulsory activation in every short fight. This is a balance hypothesis, not measured current behavior. The proposed Details-style logger would make these comparisons much more reliable.

## Relic investigation

Only the Voidform starter relic is implemented and registered. The **10 non-starter designs** in relics.md are absent from code and both inspected game JARs, so none can be offered. This is unfinished implementation, not evidence of bad luck or incorrect rarity wiring. Legendary weapons are cards and do not fill relic pools. See the [relic implementation audit](docs/relic-implementation-audit.md) for evidence, category counts, and implementation priorities. Their future effects—especially Shadowy Preparation and direct-application support—must be considered before committing to global resource buffs.
