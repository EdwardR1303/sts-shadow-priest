# Sixth card batch and Legendary equipment

Implemented October 24, 2026. The catalog contains 49 collectible designs (4 Basic, 20 Common, 14 Uncommon, 8 Rare, 3 Legendary), plus the generated Enter Voidform. All current card designs are implemented. Non-starter relic designs remain separate future work.

| Card | Base | Upgrade |
| --- | --- | --- |
| Flash Heal | Common Skill, 1 energy: heal 6; this copy costs 1 more this combat | Heal 9 |
| Blood Magic | Common Attack, 2 energy: 18 damage; lose 3 HP | 24 damage |
| Shadow Mend | Common Skill, 1 energy: heal 10; add 2 debt | Add 1 debt; heal remains 10 |
| Void Shield | Common Skill, 1 energy: 2 Block per 10 Insanity; Voidform counts as 100 | Gain 20 Insanity first |
| Insight | Common Skill, 0 energy: draw 2; draw 1 fewer next turn | Draw 3 now |
| Mind Soothe | Common Skill, 0 energy: target deals 1 less Attack damage per unique DoT this turn | Add 1 flat reduction |
| Tithe Evasion | Common Power, 1 energy: named self-damage sources lose 1 less HP | Lose 2 less HP |
| Piety | Uncommon Skill, 0 energy: draw missing card types; Exhaust | Remove Exhaust |
| Power Word: Barrier | Uncommon Skill, 3 energy: retain hand; 5 Block per remaining card | 7 Block per card |
| Mental Fortitude | Uncommon Skill, 2 energy: +2 Dexterity, +2 Mastery, -2 Strength | +3/+3/-2 |
| Serendipity | Rare Skill, 0 energy: discard Skills for Attacks or vice versa; Exhaust | Remove Exhaust |
| Dragonwrath | Legendary: 20% chance to repeat each ordinary Attack card | 25% |
| Xal'atath | Legendary: four independent rolls of +2 Strength/Mastery/Dexterity | Five rolls |
| Val'anyr | Legendary: 5 starting Block; retain until depleted; one lethal rescue | 10 starting Block |

## Healing and prevention

Flash Heal preserves the former Shadow Mend behavior with a new card identity. Costs rise only for the played copy and reset between combats. Old saved Shadow Mend cards now use the new debt design; use a new run for comparison testing.

Shadow Mend heals first, then adds to a single combat-long debuff. Starting next player turn, the aggregate debt causes one HP-loss event each turn, without decaying. Artifact blocks an application; cleansing removes accumulated debt. Purify Disease's specific whitelist does not include this debt.

Tithe Evasion only reduces Blood Magic, Shadow Word: Death, and Shadow Mend debt. It subtracts once per qualifying event, floored at zero. Enemy damage, Beat of Death, Curses, Statuses, relics, and other classes' effects do not qualify. Highest-benefit tracking makes equal/weaker copies unplayable even after cleansing; the upgrade replaces the earlier benefit.

Void Shield grants one total Block amount, applying Dexterity once. Its upgrade gains Insanity first and obeys readiness, recovery, and active-form restrictions. Both ordinary and permanent Voidform count as 100, including permanent form without the starter relic.

Mind Soothe snapshots Touch/Pain/Madness at play time. Each application is an Artifact-blockable debuff; copies add reduction for that enemy turn. Each normal Attack hit loses that amount in outgoing final damage calculation before Block, floored at zero. It expires after the enemy turn. Base with no DoTs does nothing; upgrade still reduces damage by one.

Mental Fortitude is repeatable, without Exhaust. Gains last for combat. Its Strength loss uses normal stat-debuff behavior, including Artifact.

## Draw and retention

Insight penalties add to next turn's normal opening-draw reduction, then expire. Excess penalty results in zero opening draws; later draw effects work normally. The penalty is a debuff and can be prevented or removed by ordinary effects.

Piety snapshots both hand conditions after leaving the hand, before drawing. Ignore every Piety copy when checking for Skills. If there are no Attacks, draw two Attacks; if there are no other Skills, draw two Skills. Both can trigger, with Attacks first. Piety itself remains eligible to be drawn as a Skill.

Serendipity presents two choices. Count cards actually discarded, excluding the played card; other Serendipities are ordinary Skills. Both cards draw random matching cards from the existing draw pile, without reshuffling, respecting No Draw and hand capacity. They use normal draw hooks. Serendipity invokes normal manual-discard hooks.

Barrier counts the remaining hand when its Block action resolves. Apply Dexterity, Frail, normal rounding, and the zero floor to each card's Block value, then multiply by hand size and grant once without reapplying modifiers. Four cards and four Dexterity give 36/44 Block. Retention lasts for this end of turn; it neither retains Block nor prevents Ethereal exhaustion.

## Legendary equipment

See [Legendary rules](legendary-cards.md) for reward probability, permanent uniqueness, save history, and automatic activation. These Powers equip before opening draw without counting as played cards and cannot be activated by temporary copies. They use short in-game names to fit their orange frames.

Dragonwrath repeats the entire Attack, including additional effects, HP costs, and ordinary play hooks. It preserves the original X without spending energy again. Purge-on-use repeats do not roll again. If its original target dies, the repeat chooses another living enemy when possible.

Xal'atath chooses independently and uniformly among its three stats on every roll; all rolls may land on the same stat. Val'anyr's flat starting Block ignores Dexterity. Exact depletion permanently breaks only its retention for that combat. Its separate rescue leaves one HP on lethal damage, including HP loss, and does not protect from subsequent hits.

## Artwork

All existing abilities use actual WoW icons, framed consistently with the earlier cards. Piety and Insight use Premonition of Piety/Insight. The three equipment portraits use their item icons, with transparent renders of the actual weapon models floating beside the character. These are animated sprites, not live 3D models. Source spell/item IDs and render URLs are recorded in [Warcraft attribution](../licenses/Warcraft-artwork.txt).

Blood Magic uses original generated artwork, produced with the built-in image generation tool. Final asset: [blood_magic.png](../src/main/resources/shadowpriest/images/cards/icons/blood_magic.png).

Prompt:

> Use case: stylized-concept. Asset type: square fantasy RPG ability icon for an original Slay the Spire Shadow Priest card named Blood Magic. A dark violet armored priest hand channels a brilliant crimson orb of life energy into a jagged violet shadow bolt. The orb has a few stylized red droplets, symbolic magic, no injury or gore. Warcraft-like hand-painted ability icon, chunky readable silhouette, dramatic red-violet contrast on near-black indigo, centered close-up readable at 64 pixels, polished painted texture. One square icon, no text, no lettering, no frame, no watermark.

## Validation

The focused sixth-batch suite exercises healing debt, Tithe's exact source whitelist, real turn boundaries, stacked draw penalties, both card-selection modes, Barrier's Dexterity/Frail math, Soothe, Mental Fortitude, Legendary effects, actual boss rewards, acquisition guards, Mirror/duplicator choices, JSON save-history roundtrip, and real preBattlePrep equipment activation before drawing. It exports base/upgraded card and equipped-character images for visual review. See [test results](playtest-results.md) for final counts and limitations.
