# Shadow Priest Card Proposals

Current review: introduce Devour through existing cards, strengthen Madness applications in exchange for generated Curses, and give Surrender to Madness a stronger immediate payoff. Previous low-pick proposals are preserved in [the review archive](docs/proposed-cards-review-2026-10-30.md).

These are revisions to existing cards, not additions to the 75-card roster. Rarities, types, and costs remain unchanged unless explicitly stated. [cards.md](cards.md) and [the catalog](design/cards.json) continue to describe the implemented versions. This batch is codified in 0.1.5 after focused verification and a positive first local playthrough. These accepted entries are retained for reference; ongoing balance feedback can go in Suggested Edit.

A trailing `?` marks a proposal still needing agreement. The Devour batch, Madness generators, and Surrender deadline rules are accepted. Blank **Suggested Edit:** fields remain available for playtest revisions.

### Devour

- **Card wording:** Devour: [bonus effect].
- **Tooltip:** Exhaust a Curse from your hand to trigger this bonus effect.
- **Agreed behavior:** The card remains playable without a Curse, but receives no Devour bonus. One Curse pays for one activation of the bonus; choose which Curse when multiple are available. Exhaustion is combat-only, not permanent removal, and triggers normal Curse-exhaust synergies such as Void Vengeance.
- **Resolution rules:** Resolve each Devour bonus once per successful consumption. Haste does not multiply Devour activations. Resolve consumption before a bonus that changes this card's damage, Block, or drawback; Void Ray's permanent growth still occurs after its hit. Card-specific resolution order is recorded below.
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

### Madness and Curse generation

- **Direction:** Limit new Curse-generating revisions in this pass to cards that apply Madness. Inflicting Madness on enemies risks afflicting your own mind. Devour payoffs can still support other parts of the class.
- **Candidates:** Shadow Word: Madness and Madness Overwhelming, detailed below. Values accepted for local playtesting.
- **Existing support:** Mental Decay and Malady of the Mind already generate Mind Decay; no additional changes proposed here. Keep Mind Trauma as a Devour spender and Shadow Word: Torment as a straightforward application option without a Curse.
- **Curse rules:** Generated Curses last only for combat. Creation is not a draw, does not trigger Dark Reinforcement, and does not increase Inescapable Torment's permanent-deck Curse count. Mind Decay retains its existing Retain and start-of-turn 1 HP-loss behavior.
- **Set aside:** Earlier Curse-generating suggestions for Void Surge, Void Shield, Shadow Crash, Power Infusion, Vampiric Embrace, and Void Bolt are outside this narrowed pass.

<!-- card-tallies:start -->
X/Y/Z = **Attacks/Skills/Powers**. Counts beside each rarity heading include drafts and unreviewed designs in this file; upgrades and repeated starting copies do not count separately.
<!-- card-tallies:end -->

## Common (4/4/0)

### Attacks

#### Tentacle Slam

- **Rarity:** Common
- **Type:** Attack
- **Cost:** 2 energy
- **Effect:** Deal 8 damage to an enemy. Apply 3 Touch to it and all enemies between you and it. Devour: Refund energy spent on this cast.
- **Upgrade:** Base damage becomes 10; cost becomes 1 energy. Devour still refunds only Energy actually spent on this cast.
- **Rules:** Consume the Curse before the hit, then refund the Energy actually paid for this play. Free plays refund zero; Voidform and other cost discounts reduce the refund. Refund before damage so a final killing hit cannot skip it. Damage remains 8/10. Keep existing Touch targeting and application order. Haste does not repeat the refund.
- **Design Intent:** Feed the tentacle to make this a repeatable offensive Curse spender.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Unfurling Darkness

- **Rarity:** Common
- **Type:** Attack
- **Cost:** 1 energy
- **Effect:** Deal 5 damage. Apply 3 Touch. Gain 10 Insanity. Devour: Apply 3 Touch to ALL other enemies.
- **Upgrade:** Cost becomes 0 energy; effects remain unchanged.
- **Rules:** Preserve damage, target Touch application, and explicit Insanity. The bonus applies Touch to other living enemies without dealing additional direct damage. Each application follows the normal Artifact and Misery rules. The spread can still resolve if the direct hit kills the selected target while other enemies remain.
- **Design Intent:** Consume a Curse to turn single-target setup into multi-target setup.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Mind Trauma

- **Rarity:** Common
- **Type:** Attack
- **Cost:** 1 energy
- **Effect:** Deal 6 damage. Apply 6 Madness. Gain 10 Insanity. Devour: Apply 6 additional Madness.
- **Upgrade:** Deal 9 damage and apply 9 base Madness; explicit Insanity remains 10. Devour bonus remains 6.
- **Rules:** Resolve Devour before applying Madness and combine the application into 12/15 stacks when paid, with one Artifact check. Damage remains 6/9. The additional Madness causes no immediate tick or extra direct hit. Explicit and hit-based Insanity follow their existing rules.
- **Design Intent:** Turn your own affliction into the enemy's Madness, linking the two packages.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Shadow Word: Madness

- **Rarity:** Common
- **Type:** Attack
- **Cost:** 1 energy
- **Effect:** Deal 9 damage. Apply Madness equal to HP removed. Add a Mind Decay to your hand.
- **Upgrade:** Deal 13 damage instead; application and Curse generation remain unchanged.
- **Rules:** Preserve the existing HP-removed calculation: count only actual enemy HP removed by this direct hit, excluding Block absorption, overkill, DoTs, and Psychic Link. Apply Madness afterward to a surviving target; Artifact can block it. Generate one combat-only Mind Decay before the hit so lethal damage cannot skip creation, regardless of whether Madness is applied. A full hand sends the Curse to discard. No immediate Madness tick.
- **Design Intent:** Inflict Madness at the expense of your own mind. Increasing damage from 6/10 to 9/13 also strengthens the Madness application, while a retained Curse provides immediate or future Devour fuel.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

### Skills

#### Shadow Mend

- **Rarity:** Common
- **Type:** Skill
- **Cost:** 1 energy
- **Effect:** Heal 10 HP. Add 2 to your Shadow Mend debt. Devour: Do not gain Shadow Mend debt.
- **Upgrade:** Add only 1 debt when Devour does not activate; healing remains 10.
- **Rules:** Choose and exhaust the Curse before deciding whether this cast adds debt. Successful Devour prevents only this cast's debt; it does not clear existing debt. Do not briefly apply and remove debt or consume Artifact for debt that was prevented. Existing debt timing and healing restrictions remain unchanged.
- **Design Intent:** Sacrifice an affliction to heal without creating another. The bonus prevents only the new debt from this cast.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Purify Disease

- **Rarity:** Common
- **Type:** Skill
- **Cost:** 0 energy
- **Effect:** Remove Weak, Frail, and Vulnerable from yourself. Devour: Gain 1 Artifact. Exhaust.
- **Upgrade:** Gain Retain; all other effects remain unchanged.
- **Rules:** Cleanse first, then exhaust a Curse if available and grant Artifact only when Devour succeeds. Playable without a Curse or the named debuffs. Choose when multiple Curses are in hand. Do not remove Shadow Mend debt or Surrender's deadline. The upgrade no longer grants unconditional Artifact. Both versions Exhaust; upgrading only improves timing.
- **Design Intent:** A useful cleanse without setup, with Curse consumption providing future debuff protection. Retain helps align both uses.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Focused Will

- **Rarity:** Common
- **Type:** Skill
- **Cost:** 1 energy
- **Effect:** Gain 7 Block. Gain 10 Insanity. Devour: Gain 7 additional Block.
- **Upgrade:** Base Block becomes 10 and Insanity becomes 15; Devour bonus remains 7.
- **Rules:** Consume before the Block grant and add the bonus to base Block, for 14/17 before modifiers. Apply Dexterity and normal Block modifiers once to the combined grant. Insanity remains unconditional and obeys the usual restrictions. Does not Exhaust itself.
- **Design Intent:** Turn intrusive thoughts into resolve. Establish a repeatable Common defensive spender.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

#### Madness Overwhelming

- **Rarity:** Common
- **Type:** Skill
- **Cost:** 1 energy
- **Effect:** Apply 10 Madness, plus 5 for each Power played this combat. Add a Mind Decay to your discard pile.
- **Upgrade:** Apply 15 Madness, plus 8 for each Power played this combat. Curse generation remains unchanged.
- **Rules:** Preserve the existing Power-play count, including free Power plays through Foresight+ and excluding automatic Legendary equipment. Display the current calculated application. Artifact can block the combined Madness application. Generate one combat-only Mind Decay regardless of whether the application succeeds; no immediate Madness tick. Creating the Curse in discard delays access until it is drawn or otherwise retrieved.
- **Design Intent:** Raise the initial application from 5/8 to 10/15 while retaining the existing 5/8-per-Power scaling. Make the card more useful before Powers are established, with a delayed affliction as the price.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

## Uncommon (0/1/0)

### Skills

#### Twin Suns

- **Rarity:** Uncommon
- **Type:** Skill
- **Cost:** 1 energy
- **Effect:** If the target has positive Strength, gain that much Haste. Devour: Gain 2 Haste.
- **Upgrade:** If the target has positive Strength, gain that much Haste plus 1. Devour bonus remains 2.
- **Rules:** The Devour bonus is independent of enemy Strength and works at zero or negative Strength. With positive Strength, add both rewards. The upgrade's existing extra 1 still requires positive enemy Strength. Does not Exhaust or reduce enemy Strength. Apply normal Haste decay; Void Vengeance's separate reward can also trigger.
- **Design Intent:** Feed the Void for a burst of speed. Give this situational card a Curse-fueled use against enemies without Strength.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

## Rare (1/1/0)

### Attacks

#### Void Ray

- **Rarity:** Rare
- **Type:** Attack
- **Cost:** 1 energy
- **Effect:** Deal 15 damage. Devour: Permanently increase this card's damage by 5. Exhaust.
- **Upgrade:** Deal 23 damage; Devour permanently increases damage by 8 instead.
- **Rules:** Wording conversion only. Preserve the current visible Curse selection and lethal-hit handling. Deal damage before applying permanent growth; the bonus does not increase this hit. Exhaust the selected Curse only for combat, trigger normal exhaust reactions, and preserve existing permanent-card identity and copy rules. The final Exhaust applies to Void Ray itself.
- **Design Intent:** Keep the Rare long-term Devour payoff while Common and Uncommon cards provide immediate rewards.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.

### Skills

#### Surrender to Madness

- **Rarity:** Rare
- **Type:** Skill
- **Cost:** 0 energy
- **Effect:** Playable only after leaving Voidform twice this combat (0/2). Enter permanent Voidform. Gain 2 Strength and 2 Mastery now and at the start of each turn. Die at the end of your fifth turn. Exhaust.
- **Upgrade:** Gain 3 Strength and 3 Mastery immediately and at each subsequent turn start instead.
- **Rules:** Track completed Voidform activations per combat, advancing only when an active Voidform actually ends. Entering or refreshing Voidform does not count. Show progress inline as (0/2), (1/2), or (2/2), capped for display; reset it each combat. Once unlocked, Surrender can bypass Recharge. Grant the stats immediately on successful activation, then at the start of each subsequent player turn. Permanent Voidform retains its normal Attack damage bonus and 1-energy discount; this proposal does not make all Attacks free. The casting turn is turn one of five. Show a visible countdown starting at 5; resolve friendly end-of-turn effects, including DoTs, before the final death check. Winning combat cancels the countdown. Do not allow another Surrender to restart the timer or repeat its activation reward while already surrendered. Stats and countdown do not carry between combats. The playability check blocks both ordinary play and forced use before two exits. The deadline bypasses all revival effects and damage prevention.
- **Design Intent:** Earn a zero-cost all-in transformation through two completed Voidforms. Usually unavailable in short hallway fights, situational in longer elite fights, and a powerful late-fight boss option with immediate action available.
- **Review Notes:** Replaces the previous full-hand draw/Innate proposal and removes the doubled incoming damage penalty. The upgrade improves stat gains only. The deadline is unavoidable death: bypass Fairy in a Bottle, Lizard Tail, Guardian Angel, Block, and damage prevention. Playtesting should check whether two completed Voidforms unlock the card early enough in boss fights.
- **Suggested Edit:**
- **Status:** Implemented in 0.1.5; focused engine checks passed and first local playthrough reviewed. Ongoing balance testing.
