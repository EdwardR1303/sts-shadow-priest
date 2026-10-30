# Archived proposals: low-pick review

Archived October 30, 2026 before starting the Devour review. This is historical discussion, not the current design specification. Current proposals are in [proposed_cards.md](../proposed_cards.md); implemented designs are in [cards.md](../cards.md).

# Shadow Priest Card Proposals

## Review scope

These **14 existing cards** combine the ten lowest combined reward pick rates with the seven cards never selected in the latest ten runs; overlapping cards appear once. Previously implemented proposals outside this list have been cleared. Earlier drafts remain in [the review archive](docs/proposed-cards-review-2026-10-27.md).

These fourteen proposals were reviewed on October 29. Nine revisions are implemented locally in [cards.md](cards.md); five cards retain their current implementation. Original suggested edits remain below for context. New edits should receive a trailing `?` for review.

### Pick-rate context

- **Sample:** 45 saved Shadow Priest runs, October 24-29, 2026: 33 desktop playtest runs and 12 Steam runs, containing 782 recorded card choices.
- **Pick rate:** Picks divided by recorded offers, combining base and upgraded copies. Shop purchases are counted separately; unbought shop inventory is unavailable.
- **Recent sample:** The latest 10 saved runs, October 28 at 22:28 through October 29 at 12:58, using game-recorded local times.
- **Interpretation:** These runs span multiple balance versions. Low selection rates identify review candidates, not proof that the current card is weak. None of these cards has zero lifetime picks.

X/Y/Z = **Attacks/Skills/Powers** among the review candidates below, not the full class.

## Common (3/5/1)

### Attacks

#### Mind Blast

- **Rarity:** Common
- **Type:** Attack
- **Cost:** 1 energy
- **Effect:** Deal 10 damage, plus 2 per unique DoT on the target. Gain 10 Insanity per unique DoT on target.
- **Upgrade:** Base damage becomes 12; per-DoT damage becomes 4.
- **Rules:** Snapshot unique DoTs before the hit. Explicit Insanity follows damage even if the target dies, in addition to the universal ten per hit: 10/20/30/40 total for zero/one/two/three DoTs. All gains obey the shared cap and ready, active, and Recharge restrictions. Hovering a target previews damage and explicit bonus Insanity.
- **Pick Rate:** 5/56 (8.9%); offered across 28 runs.
- **Recent Picks:** 0/7 in the latest 10 runs.
- **Shop Purchases:** 0 across all 45 runs.
- **Suggested Edit:** Makes it a better insanity generator.
- **Status:** Reviewed and implemented locally; full-run playtesting pending.

#### Shadow Word: Torment

- **Rarity:** Common
- **Type:** Attack
- **Cost:** 1 energy
- **Effect:** Deal 7 damage. Apply 3 Pain and 3 Madness.
- **Upgrade:** Deal 10 damage and apply 5 Pain and 5 Madness.
- **Rules:** Damage first, then Pain, then Madness on a surviving target. Neither application requires HP loss. Each application independently respects Artifact; Pain keeps its five-stack cap.
- **Pick Rate:** 4/29 (13.8%); offered across 15 runs.
- **Recent Picks:** 1/9 in the latest 10 runs.
- **Shop Purchases:** 3 across all 45 runs.
- **Suggested Edit:** Adding madness gives it a better identity.
- **Status:** Reviewed and implemented locally; full-run playtesting pending.

#### Shadow Word: Death

- **Rarity:** Common
- **Type:** Attack
- **Cost:** 0 energy
- **Effect:** Deal 10 damage. If the target survives, lose 2 HP each time this card deals damage. If the target dies, return Shadow Word: Death to your hand.
- **Upgrade:** Deal 10 damage twice. Same other effects
- **Rules:** Check survival after each direct hit. Preserve the existing HP-damage condition: fully blocked or prevented hits cause no backlash. A killing hit causes no backlash, stops remaining hits, and returns the actual played card once through normal card cleanup. Reset the return flag on each play. A kill from Psychic Link or another separate effect does not qualify. Haste adds hits only to the upgraded version. Purged replays cannot leave a card in hand; normal forced Exhaust or Rebound still takes precedence. Backlash uses normal HP-loss prevention and Insanity reactions.
- **Pick Rate:** 7/45 (15.6%); offered across 29 runs.
- **Recent Picks:** 1/8 in the latest 10 runs.
- **Shop Purchases:** 3 across all 45 runs.
- **Suggested Edit:** This keeps its identity as a "finisher" card.
- **Status:** Reviewed and implemented locally; full-run playtesting pending.

### Skills

#### Purify Disease

- **Rarity:** Common
- **Type:** Skill
- **Cost:** 0 energy
- **Effect:** Remove Weak, Frail, and Vulnerable from yourself. Exhaust.
- **Upgrade:** Gain 1 Artifact as well.
- **Rules:** Remove only the three named debuffs, not Surrender to Madness's penalty or Shadow Mend debt. The upgrade grants Artifact after the cleanses, even if none were present. Artifact can prevent a subsequent debuff application, including a new Shadow Mend debt or Mental Fortitude's Strength loss.
- **Pick Rate:** 2/44 (4.5%); offered across 25 runs.
- **Recent Picks:** 0/4 in the latest 10 runs.
- **Shop Purchases:** 0 across all 45 runs.
- **Suggested Edit:** This card was weak because drawn on the wrong turn makes it a dead draw. Gaining artifact improves the payoff. It becomes more desirable if offered upgraded.
- **Status:** Reviewed and implemented locally; full-run playtesting pending.

#### Shadow Mend

- **Rarity:** Common
- **Type:** Skill
- **Cost:** 1 energy
- **Effect:** Heal 10 HP. Add 2 to your Shadow Mend debt. At the start of each turn, lose HP equal to your debt.
- **Upgrade:** Add 1 debt instead of 2; healing remains 10 HP.
- **Rules:** Each cast adds its debt to one debuff lasting this combat. Starting next turn, resolve the combined debt as one HP-loss event each turn; it does not decay. Artifact can prevent a debt application and debuff cleansing can remove accumulated debt, without undoing healing. Tithe Evasion reduces the combined event once, not once per cast. Purify Disease does not remove this debt.
- **Pick Rate:** 8/48 (16.7%); offered across 28 runs.
- **Recent Picks:** 0/9 in the latest 10 runs.
- **Shop Purchases:** 1 across all 45 runs.
- **Suggested Edit:** The card's numbers seem fine, probably just not being offered at the right time?
- **Status:** Reviewed; keep current implementation unchanged. Revisit after package playtesting.

#### Bulwark

- **Rarity:** Common
- **Type:** Skill
- **Cost:** 2 energy
- **Effect:** Gain 12 Block and 1 Blur. Cast again if used at below 35% health.
- **Upgrade:** Gain 16 Block instead.
- **Rules:** Check current HP once when played, strictly below 35% of maximum HP. At low HP, grant Block and Blur twice: 24/32 Block before modifiers and 2 Blur. Dexterity and other normal card Block modifiers apply to each grant. This is one card play with one conditional repeat, unaffected by Haste and incapable of recursively repeating. At or above 35%, grant only 12/16 Block and 1 Blur. Blur retains Block, not cards. Preview the current total Block and Blur in combat.
- **Pick Rate:** 7/39 (17.9%); offered across 27 runs.
- **Recent Picks:** 0/11 in the latest 10 runs.
- **Shop Purchases:** 2 across all 45 runs.
- **Suggested Edit:** Adds the flavor of being a prevent death card.
- **Status:** Reviewed and implemented locally; full-run playtesting pending.

#### Mind Soothe

- **Rarity:** Common
- **Type:** Skill
- **Cost:** 0 energy
- **Effect:** Until your next turn, all enemies deal 1 less Attack damage, plus 1 less per unique DoT on them.
- **Upgrade:** Base reduction becomes 2.
- **Rules:** Snapshot unique DoTs separately for each living enemy when played. Each enemy receives its own debuff application, independently blocked by Artifact. Reduction applies per Attack hit, floors damage at zero, stacks for this turn, and expires after the enemy turn. The combat preview shows the current reduction, or a range when enemies differ.
- **Pick Rate:** 8/41 (19.5%); offered across 25 runs.
- **Recent Picks:** 0/5 in the latest 10 runs.
- **Shop Purchases:** 0 across all 45 runs.
- **Suggested Edit:** At worst an AoE artifact remover, at best, a massive damage reduction after full set up
- **Status:** Reviewed and implemented locally; full-run playtesting pending.

#### Focused Will

- **Rarity:** Common
- **Type:** Skill
- **Cost:** 1 energy
- **Effect:** Gain 7 Block. Gain 10 Insanity.
- **Upgrade:** Gain 10 Block and 15 Insanity.
- **Rules:** Block with Dexterity once, then immediate explicit Insanity. No enemy Attack prerequisite or pending trigger.
- **Pick Rate:** 7/35 (20.0%); offered across 18 runs.
- **Recent Picks:** 0/6 in the latest 10 runs.
- **Shop Purchases:** 0 across all 45 runs.
- **Suggested Edit:** Numbers are passable
- **Status:** Reviewed; keep current implementation unchanged. Revisit after package playtesting.

### Powers

#### Tithe Evasion

- **Rarity:** Common
- **Type:** Power
- **Cost:** 1 energy
- **Effect:** During your turn, lose 1 less HP from each damage source.
- **Upgrade:** Reduce 2 instead.
- **Rules:** Add benefits from all copies. Covers start-of-turn, card-play and end-of-turn damage/HP loss, including Thorns, Beat of Death, Curses and self-damage. Damage consumes Block normally before this reduces remaining HP loss, floored at zero. No protection during the enemy phase. Fully prevented HP loss grants neither Insanity nor Catharsis damage.
- **Pick Rate:** 2/44 (4.5%); offered across 27 runs.
- **Recent Picks:** 1/11 in the latest 10 runs.
- **Shop Purchases:** 2 across all 45 runs.
- **Suggested Edit:** Seems situational, but balanced
- **Status:** Reviewed; keep current implementation unchanged. Revisit after package playtesting.

## Uncommon (0/2/1)

### Skills

#### Mental Fortitude

- **Rarity:** Uncommon
- **Type:** Skill
- **Cost:** 2 energy
- **Effect:** Gain 2 Dexterity and 2 Mastery. Lose 2 Strength.
- **Upgrade:** Costs 1 energy
- **Rules:** Repeatable; does not Exhaust. Stats last for combat. The Strength loss follows normal stat-debuff rules, including Artifact. The upgrade reduces cost only.
- **Pick Rate:** 3/31 (9.7%); offered across 20 runs.
- **Recent Picks:** 0/6 in the latest 10 runs.
- **Shop Purchases:** 0 across all 45 runs.
- **Suggested Edit:** Reducing its cost makes a smaller tradeoff to play on a turn that requires defensive actions
- **Status:** Reviewed and implemented locally; full-run playtesting pending.

#### Malady of the Mind

- **Rarity:** Uncommon
- **Type:** Skill
- **Cost:** 1 energy
- **Effect:** Double the target's Madness. Exhaust.
- **Upgrade:** Triple the target's Madness. Exhaust.
- **Rules:** Add stacks equal to current Madness times (multiplier minus one) when the effect resolves. Artifact blocks the addition. Creates no missing Madness and does not immediately tick it.
- **Pick Rate:** 4/23 (17.4%); offered across 13 runs.
- **Recent Picks:** 1/10 in the latest 10 runs.
- **Shop Purchases:** 0 across all 45 runs.
- **Suggested Edit:** Only usable in decks that heavily commit to the madness package. Seems fine
- **Status:** Reviewed; keep current implementation unchanged. Revisit after package playtesting.

### Powers

#### Dark Reinforcement

- **Rarity:** Uncommon
- **Type:** Power
- **Cost:** 1 energy
- **Effect:** Whenever you draw a Curse, gain 1 Energy and draw 1 card.
- **Upgrade:** Whenever you draw a Curse, gain 1 Energy, draw 1 card, and gain 1 Mastery.
- **Rules:** Actual draws only. Creation, retention, tutors that put cards into hand, and Necronomicurse returning do not trigger. Copies stack energy/draw; only upgraded copies add Mastery. Additional draws use normal draw, shuffle, and hand-limit rules.
- **Pick Rate:** 4/15 (26.7%); offered across 14 runs.
- **Recent Picks:** 0/5 in the latest 10 runs.
- **Shop Purchases:** 0 across all 45 runs.
- **Suggested Edit:** With Mind Decay being a retained card, it becomes one less source of curse synergy for this purpose. Perhaps this card becomes more desirable if other cards are buffed and handed a curse as an offsent.
- **Status:** Reviewed; keep current implementation unchanged. Revisit after package playtesting.

## Rare (0/2/0)

### Skills

#### Serendipity

- **Rarity:** Rare
- **Type:** Skill
- **Cost:** 0 energy
- **Effect:** Retain. Choose one: Discard all Skills and draw that many Attacks; or discard all Attacks and draw that many Skills. Exhaust.
- **Upgrade:** Remove Exhaust.
- **Rules:** Retain in both versions. Exclude the played Serendipity from the hand and discard count. Count cards actually discarded. Draw random matching cards from the draw pile only, without reshuffling, subject to hand capacity and available matches. Other Serendipity copies are ordinary Skills for this effect.
- **Pick Rate:** 3/23 (13.0%); offered across 17 runs.
- **Recent Picks:** 1/5 in the latest 10 runs.
- **Shop Purchases:** 0 across all 45 runs.
- **Suggested Edit:** This card gets significantly worse if drawn in the backend of your deck. Retain makes it more controlled when you wish to trigger its effect
- **Status:** Reviewed and implemented locally; full-run playtesting pending.

#### Surrender to Madness

- **Rarity:** Rare
- **Type:** Skill
- **Cost:** 3 energy
- **Effect:** Enter Voidform. For the rest of combat: Stay in void form, all attacks are reduced to 0 energy cost, take double damage from all enemy attacks.
- **Upgrade:** Draw 2 cards.
- **Rules:** Bypass Insanity and Recharge. Extend an existing ordinary Voidform rather than entering again. Invalidate ready activation cards. Fixed-cost playable Attacks cost zero for the rest of combat, including cards drawn later; X-cost Attacks keep their normal energy spending, Haste, and Chemical X behavior. Do not mutate underlying card costs. Additional Surrenders are unplayable once permanent form is active. Treat the doubled incoming Attack damage as a buff inseparable from the bargain: Artifact does not prevent it, and Mass Dispel or Orange Pellets cannot cleanse it. Double normal incoming Attack damage before Block; non-Attack and HP-loss damage are unchanged. The upgraded draw occurs after activation and only on successful Surrender. Works without the starter relic.
- **Pick Rate:** 3/22 (13.6%); offered across 17 runs.
- **Recent Picks:** 0/2 in the latest 10 runs.
- **Shop Purchases:** 2 across all 45 runs.
- **Suggested Edit:** This card is rarely ever worth the huge penalty. Its meant to resolve combat the turn its played. At 3 energy, it made attacks that cost more than 1 not possible to play the same turn, making the card too dangerous. If we keep the card at 3, then attacks being free are no longer a limiting factor. The upgrade makes it even more likely to end combat on the turn its played.
- **Status:** Reviewed and implemented locally; full-run playtesting pending.
