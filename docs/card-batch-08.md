# Setup, defense, and Insanity revision

Implemented October 26, 2026 after reviewing the author's Suggested Edit annotations. [cards.md](../cards.md), [keywords.md](../keywords.md), and [the JSON catalog](../design/cards.json) contain the accepted definitions. [proposed_cards.md](../proposed_cards.md) now records the adopted designs; [the original review](proposed-cards-review-2026-10-25.md) preserves the earlier numbers and author comments.

## Implemented changes

- Direct Attack hits generate 10 Insanity without a DoT or energy prerequisite. Each actual enemy hit counts, including blocked, zero-damage, free, Haste, and Chemical X hits. Skipped hits after death do not count.
- Natural and triggered DoT ticks generate 1 Insanity, including Psychic Link; zero-damage ticks count. Halo, retaliation, and healing do not count as either source. Keep 5 per actual HP-loss damage event, explicit card gains, the 100 cap, and ready/active/Recharge lockouts.
- Seven new cards: Shadow Word: Torment, Mind Trauma, Void Tendrils, Encroaching Shadows, Focused Will, Shadow Weaving, and Dark Revelation.
- Atonement moves to Uncommon. Its debuff grants fixed Block per direct hit, has no Dexterity scaling, and keeps the highest applied value. The card does not Exhaust. Redundant equal/weaker applications do not consume Artifact. Damage precedes application, so recasts benefit from the existing debuff.
- Searing Nightmare becomes a 2-energy Rare Power: Attack hits apply 1 Madness. Upgrading reduces cost to 1. It does not directly damage or apply a DoT on play. Copies stack; ticks and retaliation cannot recursively trigger it.
- Blood Magic now deals 14/18 and applies 2/3 of both Touch and Pain, with no self-damage. It replaces the earlier unaccepted 18/24-damage draft as well as the old self-damaging implementation.
- Void Shield grants base 5/8 Block plus floor(Insanity / 10 or 5). Dexterity applies once to the combined grant; ordinary Block modifiers apply normally. It no longer grants Insanity. Voidform counts as 100; Recharge preserves the base floor.
- Void Tendrils grants 4/5 Block separately per living enemy, applying Dexterity per enemy. It applies 2/3 Pain only to enemies showing an Attack intent when played.
- Shadow Weaving snapshots all active enemy/DoT pairs before natural ticks. Its 2/3-per-pair total is one Block grant with Dexterity once, including final-stack DoTs. Copies combine their coefficient. No DoTs means no Dexterity-only Block.
- Fade becomes free without an added Block grant. Focused Will awards Insanity immediately. Unfurling Darkness and Mind Blast gain explicit Insanity, Reaping Madness gains 20 on play, and Protected Channel grants 6 upfront Block. Bulwark, Mind Soothe, and Mind Sear receive the agreed numerical improvements.
- Enter Voidform draws one only after a valid activation. Stale tokens and Surrender gain no extra draw.
- Dark Revelation visibly tutors a direct DoT application card, sets its cost to zero for this turn, and Exhausts. Conditional applications remain conditional; the Power Searing Nightmare and existing-only maintenance cards are excluded. No currently eligible card has an X cost after this rework.

Protective Shadows and the alternative energy-spend, first-application, opening-reserve, and minimum-hit resource proposals were not adopted.

## Roster

| Rarity | Attacks / Skills / Powers | Total |
|---|---:|---:|
| Basic | 1 / 3 / 0 | 4 |
| Common | 10 / 12 / 4 | 26 |
| Uncommon | 6 / 11 / 4 | 21 |
| Rare | 3 / 4 / 4 | 11 |
| Legendary | 0 / 0 / 3 | 3 |
| Collectible total | 20 / 30 / 15 | 65 |

Ordinary rewards contain 58 cards: 19 Attacks, 27 Skills, and 12 Powers. Enter Voidform is a separate generated catalog entry. Existing card IDs are preserved, including Searing Nightmare's changed type. Restart the game to load the build; use a new run for balance comparisons with fresh card pools.

## Verification

- Maven build and all 16 unit tests passed.
- The focused `-SetupOnly` real-game suite passed 129 checks. It verifies actual rarity/type pools, art and upgraded copies, all Insanity sources, kill/blocked-hit handling, zero-energy Haste/Chemical X, Psychic Link, Recharge, valid/stale token draws, Atonement replacement/Artifact/no-Exhaust, both Dexterity rules, Barrier locks, Nightmare application/Artifact/non-recursion, the new common applications, and tutor selection/expiry/full-hand/no-target behavior.
- Catalog schema and rarity-header tallies were validated. Base/upgraded game-rendered cards were inspected for artwork and readability.
- Full-run balance simulations were not run. Older batch smoke scenarios retain historical numerical expectations; this focused suite is the current acceptance suite for the changed cards and rules.

```powershell
./scripts/build.ps1
./scripts/playtest.ps1 -SkipBuild -SetupOnly -Width 1920 -Height 1080 -Windowed
python scripts/update-card-tallies.py --check
```

## Artwork

Six new cards use matching WoW icons: Mind Trauma, Void Tendrils, Focused Will, Encroaching Shadows, Dark Revelation (the Shadow of Zul spell), and archived Shadow Weaving. Download URLs and spell IDs are in [Warcraft-artwork.txt](../licenses/Warcraft-artwork.txt). The game applies its existing frame without altering source images.

Shadow Word: Torment uses the built-in image-generation tool. Saved project asset: `src/main/resources/shadowpriest/images/cards/icons/shadow_word_torment.png`. It is original generated art, not an official WoW ability icon. The final prompt is recorded in [card-batch-08-art-prompts.json](card-batch-08-art-prompts.json).

## Next playtest observations

Compare activations per combat, turn of first readiness and activation, fights ending without readiness, and unused ready tokens, separating normal fights, elites, and bosses. Run length biases activations-per-run averages. This batch does not implement a new telemetry collector; historical run files do not contain reliable per-combat Voidform activation counts. Normal playtesting or a future explicit logging pass is still needed to measure these values.
