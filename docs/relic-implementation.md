# Playable Shadow Priest relics

Seven settled non-starter relics are now implemented and registered with BaseMod's Shadow Priest relic pool. They use normal reward selection; no appearance rates are forced. Start a new run to build fresh relic pools. Resuming a run preserves its saved pools, which may predate these additions.

## Inventory

| Category | Implemented | Relics |
|---|---:|---|
| Starter | 1 | Voidform |
| Common | 1 | Shadowy Preparation |
| Uncommon | 1 | Idol of C'Thun |
| Rare | 2 | Idol of Y'Shaarj, Idol of N'Zoth |
| Boss | 3 | Idol of Yogg-Saron, Improved Voidform, Void Vengeance |
| Shop | 0 | None yet |
| Total | 8 | Starter plus seven reward relics |

Misery, Inescapable Torment, and the Shop Dark Void relic remain unimplemented drafts. The proposed card file is unchanged by this implementation. See [relics.md](../relics.md) for accepted effects and [the original audit](relic-implementation-audit.md) for why earlier runs never offered class relics.

## Timing and interactions

- **Preparation:** grant 10 Insanity per living starting enemy after the starter resets. Later summons do not trigger it. Preparation and Improved Voidform require the starter relic to be present when selected as a reward.
- **C'Thun:** add an extra unupgraded Tentacle Slam after the opening draw, using the normal full-hand discard fallback. Its base and current cost are zero; a card field preserves that discount through stat-equivalent copying and an in-combat upgrade. Other Slams keep their normal cost.
- **Y'Shaarj:** scope each card play and observe energy actually deducted through `EnergyPanel.useEnergy`. A positive payment during Voidform queues exactly one draw after the card's queued effects. Haste, Chemical X, the Voidform discount, and free plays do not themselves count as payment. Attack, Skill, Power, and X-cost plays are eligible. Entering Voidform as a card's effect does not retroactively qualify that card.
- **N'Zoth:** ordinary Power plays queue one Mastery after their effects. Successful automatic Legendary equipment calls a separate once-per-combat hook. It does not count as a card play for the Awakened One or other card-play effects.
- **Yogg-Saron:** successful normal entry or Surrender from outside Voidform grants three energy. Surrender extending an active form and invalid activation attempts do not retrigger it.
- **Improved Voidform:** count Attack card plays, not damage hits. The relic counter shows progress to three. Preserve the stance across turns below three, then end it at the end of the turn reaching three. Further attacks that turn still benefit. No additional turn limit. Recharge starts only when the form actually ends and covers the next full player turn. Surrender remains permanent; combat boundaries clear progress.
- **Void Vengeance:** one free Curse play per turn, exhausted on use; independently, every actual Curse exhaust grants 2 Haste. The counter shows the remaining free play (1 or 0). Curses remain in the combat deck. Normality and other play restrictions still apply. Blue Candle retains its independent permission and HP cost, including after the allowance is spent. Repeated Necronomicurse exhausts intentionally keep granting Haste. Ethereal and external exhaust effects qualify without spending the play allowance; Status exhausts do not. Strange Spoon only grants Haste if the card actually exhausts.

## Art

All seven use actual WoW artwork inside transparent, circular relic medallions rendered at the game's 128/256 texture sizes. Padding keeps the visible icon within its top-bar slot. No square icon background extends beyond the medallion.

| Relic | Source artwork |
|---|---|
| Shadowy Preparation | Existing Shadowy Insight icon |
| Idol of C'Thun | `achievement_boss_cthun` |
| Idol of Y'Shaarj | `achievement_raid_terraceofendlessspring04` |
| Idol of N'Zoth | `achievement_nzothraid_nzoth` |
| Idol of Yogg-Saron | `achievement_boss_yoggsaron_01` |
| Improved Voidform | Existing Voidform source icon |
| Void Vengeance | Existing Void Bolt icon |

Source images remain unchanged. Attribution and download URLs are in [Warcraft-artwork.txt](../licenses/Warcraft-artwork.txt).

## Verification

October 26 Void Vengeance redesign: **25 unit tests** and **34 focused real-game checks** passed using `-VengeanceOnly`. See [results and coverage](playtest-results.md#void-vengeance-redesign-october-26-2026). The original Curse conversion has been removed; the historical initial-batch checks below predate that redesign.

- `scripts/build.ps1`: 16 unit tests pass, including ordinary Recharge, carryover below three Attacks, ending after the threshold, and permanent Surrender.
- `scripts/playtest.ps1 -SkipBuild -RelicOnly -Width 1920 -Height 1080 -Windowed`: 80 checks passed in the patched game. Covers initialized pools, real reward-factory selection of each relic, exclusion from Ironclad pools, both texture sizes, combat-start order, generated-card replay and upgrade, Curse conversion, Legendary activation, actual energy payment, multi-hit counting, real turn transitions, Recharge, and Surrender.
- `-RelicArtOnly` captures the top bar without running the combat interaction suite. Visual inspection caught oversized medallions; their padding was corrected before the final capture.

The focused suite uses an isolated smoke-test profile. It does not alter normal saves. The broader simulation suites were not run for this batch. Balance still needs normal playtesting; in particular, Improved Voidform deliberately allows extended Skill/DoT turns below three Attack plays.
