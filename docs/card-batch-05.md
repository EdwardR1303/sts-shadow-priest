# Fifth reward-card batch and Mastery

Eight approved cards enter the reward pool: four Commons, two Uncommons, and two Rares. The playable pool is now 32 rewards (14 Common, 11 Uncommon, 7 Rare), plus four unique starters and the generated Enter Voidform token. Mind Soothe and Mental Fortitude remain drafts and are not registered.

| Card | Rarity / type | Base | Upgrade |
| --- | --- | --- | --- |
| Penance | Common Attack | 1 energy; 6 damage; convert actual enemy HP removed to Block | 8 damage |
| Atonement | Common Attack | 1 energy; 5 damage; 3 Block per unique DoT | 7 damage; 4 Block per unique DoT |
| Purify Disease | Common Skill | 0 energy; remove Weak, Frail, Vulnerable; Exhaust | Retain |
| Dispel Magic | Common Skill | 0 energy; remove target Block | Also remove all Artifact |
| Body and Soul | Uncommon Power | 2 energy; 20% miss chance per enemy Attack hit | 30% |
| Insidious Ire | Uncommon Power | 2 energy; each hit against three DoTs gets max(0, 1 + Mastery) bonus damage | max(0, 2 + Mastery) |
| Petrifying Scream | Rare Skill | 2 energy; stun target for one turn; Exhaust | 1 energy |
| Shadow Committal | Rare Power | 3 energy; during Voidform, Attacks grant Strength, Skills Dexterity, Powers Mastery | 2 energy |

## Damage conversion and Block

Penance measures the actual HP difference caused by its own direct hit, capped at remaining HP. Enemy Block, overkill, Psychic Link, and DoT damage never count. Strength and ordinary Attack modifiers affect the hit first. The conversion itself does not apply Dexterity or Frail again: two HP removed grants two Block. The conversion resolves with the hit, including a killing blow.

Atonement snapshots Touch, Pain, and Madness before damage. Each counts once regardless of stacks; Halo does not count. It grants Block once, applying ordinary Dexterity/Frail modifiers once to the total. A killing blow does not erase the snapshotted benefit.

## Cleansing and dodge

Purify removes only its named debuffs. It does not remove Surrender exposure, negative stats, or other debuffs. Dispel removes current Block rather than Block-generating powers; its upgrade removes all Artifact charges without treating removal as a debuff application.

Body and Soul rolls independently per incoming enemy Attack hit, using the game's seeded miscellaneous RNG. A miss preserves Block and prevents that hit's damage; it does not cancel separate non-damage effects of the enemy move. HP-loss and non-Attack damage do not roll. It tracks the highest granted chance for combat, so copies cannot accumulate immunity. Equal/weaker copies are unplayable even if the Power is removed. The benefit resets at the next battle, including without the starter relic.

## Mastery and Insidious Ire

Mastery uses the game's Strength icon silhouette recolored purple at runtime. Positive Mastery is a buff; negative Mastery is a debuff and can be blocked by Artifact or cleansed under ordinary debuff rules. It stacks through zero. Combat cleanup removes it.

Touch/Pain damage is max(0, base damage + current Mastery), with bases 5 and 3. Natural and Psychic Link ticks both use Mastery; zero-damage natural ticks still spend one duration stack. Intangible caps a positive tick at one but cannot turn a zero tick into damage. Zero-damage Touch never triggers Embrace healing. Madness remains nonticking and Halo is unaffected.

Ire uses the current Mastery bonus in normal Attack calculations, before ordinary power damage modifiers and Voidform amplification. It applies to each hit and checks each target's three unique DoTs. X costs are unchanged. DoTs, Halo, and Link do not receive Ire's bonus. Base and upgraded copies stack their contributions, including one Mastery coefficient per copy; no contribution can be negative. The upgrade increases the base bonus, not Mastery itself. Costs and high-Mastery burst balance remain playtest values.

## Stun and persistent scaling

Scream uses StSLib's Artifact-blockable stun. Natural DoT ticks still resolve on a stunned enemy's turn. Multiple copies may buy consecutive turns; there is no new boss immunity rule.

Committal checks Voidform when each card is played and queues the stat gain after that card's effects. It triggers once per card, not per hit. Stats remain when ordinary Voidform expires and persist only for combat. Copies stack. A newly played copy does not reward itself, although an already active copy can reward that Power play. An activation card played outside Voidform does not retroactively qualify just because it enters the form. Surrender enables continued scaling on subsequent turns.

## Artwork

Penance, Atonement, Purify Disease, Dispel Magic, Body and Soul, Insidious Ire, and Petrifying Scream use their actual WoW ability icons, verified through each named spell's tooltip metadata. Source files and spell IDs are recorded in [Warcraft attribution](../licenses/Warcraft-artwork.txt). Each source icon was visually inspected and uses the existing shared frame.

Shadow Committal uses original artwork generated with the built-in image generation tool, saved as [shadow_committal.png](../src/main/resources/shadowpriest/images/cards/icons/shadow_committal.png). Prompt:

> Use case: stylized-concept. Asset type: square fantasy RPG ability icon for the original card Shadow Committal in a shadow-priest Slay the Spire mod. Paint a close-up of a dark hooded priest with luminous violet eyes, hands clasped around a brilliant purple void flame at the chest, shadow tendrils curling around the silhouette. Strong simple readable silhouette and dramatic violet/silver highlights against near-black indigo. Hand-painted Warcraft-like ability-icon aesthetic, not a screenshot. Centered composition readable at 64 pixels, square image, no text, no letters, no border, no watermark.

## Validation

Actual-game coverage is in `FifthBatchSmokeTest`. Run just this batch with `./scripts/playtest.ps1 -SkipBuild -LatestBatchOnly -Width 1920 -Height 1080 -Windowed`, or run the complete regression suite with `-SmokeTest`. Completed results belong in [playtest results](playtest-results.md). These controlled assertions do not establish full-run or A20 Heart balance.
