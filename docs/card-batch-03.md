# Third reward batch and fixed-damage DoTs

This records the third batch. [The fourth batch](card-batch-04.md) expands rewards and ends the temporary Ironclad rare supplements.

This batch adds Tentacle Slam, Void Blast, Mass Dispel, and Vampiric Embrace, and replaces variable-damage DoT applications with fixed damage and a five-tick cap. The class now has nine Commons, eight Uncommons, and three Rares. The rare reward pool temporarily includes Ironclad rares alongside the new cards: Question Card can request four distinct boss rares, so three native rares alone would exhaust the game's duplicate-rejection loop. The fallback is removed automatically once at least four native rares exist.

## Touch and Pain

Touch always deals **5** damage per tick; Pain always deals **3**. Their counters represent remaining ticks, never damage intensity. Applications add remaining ticks up to **5**, without an immediate tick. Every natural tick removes one stack. Psychic Link's bonus ticks consume no stacks. Artifact blocks applications normally.

- Touch costs 1 and applies 3 stacks; its upgrade applies 4 at the same cost and damage.
- Pain costs 1 and applies 5 stacks; its upgrade costs 0 at the same damage and application.
- Played starter DoT cards still return to hand at turn end under the existing hand-space and Exhaust rules.
- Every duration extension, including Mind Flay: Insanity, obeys the five-tick cap.
- Madness remains a separate permanent, additive debuff with no ticking damage or duration. Its stacks do not use the five-tick cap.

This supersedes the first batch's variable-damage upgrades and replacement refresh behavior. There is no stronger or weaker version of Touch or Pain to overwrite when another card applies or spreads it.

## Reviewed cards

| Card | Type / rarity | Base | Upgrade |
| --- | --- | --- | --- |
| Tentacle Slam | Attack / Common | 2 energy; 8 damage; apply 3 Touch to target and intervening enemies | 1 energy; 10 damage |
| Void Blast | Attack / Rare | 1 energy; 10 damage; add source DoT stacks to all other enemies | 15 damage |
| Mass Dispel | Skill / Rare | 2 energy; remove all enemy Block, Artifact, and positive Strength | 1 energy |
| Vampiric Embrace | Power / Rare | 3 energy; heal 1 whenever Touch removes enemy HP | Heal 2 |

Tentacle Slam damages only its selected target, then applies Touch to it and living enemies whose horizontal centers lie between the player and the target. This works in either direction. Newly applied Touch does not generate Insanity retroactively for the hit, but Psychic Link can trigger it afterward. A slain selected target does not prevent application to intervening survivors.

Void Blast captures Touch, Pain, and Madness before its hit, preserving spread on a killing blow. The source is excluded from applications and its stacks remain unchanged. Recipients **add** captured stacks rather than copying or replacing them. Touch/Pain cap at five; Madness accumulates normally. Alternating sources can therefore grow Madness across enemies; this is intentional. Artifact checks each application separately, in Touch, Pain, Madness order. Unrelated debuffs and Halo do not spread.

Mass Dispel removes current Block and Artifact and removes Strength only when positive. It preserves negative Strength and powers such as Ritual or Metallicize that can grant future buffs. Removal is not blocked by Artifact. The card does not Exhaust.

Embrace copies add their healing amounts with **no per-turn cap**. Natural Touch ticks and Psychic Link ticks each qualify, separately per enemy, only when enemy HP actually falls. A reduced but damaging tick grants the fixed healing amount; a fully prevented tick grants none. Pain, Madness, Halo, and direct attacks do not qualify. Healing follows the damage, respects normal healing modifiers/restrictions, and cannot resurrect the player.

## Design constraints and testing

Native Shadow Priest cards do not apply Vulnerable. Access through outside cards, events, relics, or other external sources remains allowed.

Sustain is an intended payoff for setting up the Touch/Link/Embrace engine. Controlled checks include the Heart's Invincible power at a 200-damage budget, but this is not an A20 Heart victory or proof of full-run balance. Test setup cost, recovery turns, attack survival, repeatable healing, and Madness growth through actual runs.

## Artwork

All four use their actual WoW ability icons, verified against spell tooltip data and stored unchanged under `src/main/resources/shadowpriest/images/cards/icons/`. The existing card renderer supplies both portrait sizes and the shared frame. Embrace also uses its icon for the player Power.

| Card | Spell reference | Icon / local filename |
| --- | --- | --- |
| Tentacle Slam | [1227621](https://www.wowhead.com/spell=1227621/tentacle-slam) | `inv12_ability_priest_tentacleslam` / `tentacle_slam.jpg` |
| Void Blast | [450405](https://www.wowhead.com/spell=450405/void-blast) | `inv_cosmicvoid_missile` / `void_blast.jpg` |
| Mass Dispel | [32375](https://www.wowhead.com/spell=32375/mass-dispel) | `spell_arcane_massdispel` / `mass_dispel.jpg` |
| Vampiric Embrace | [15286](https://www.wowhead.com/spell=15286/vampiric-embrace) | `spell_shadow_unsummonbuilding` / `vampiric_embrace.jpg` |

Source credits are packaged in [Warcraft artwork](../licenses/Warcraft-artwork.txt). No image-generation step was needed for this batch.
