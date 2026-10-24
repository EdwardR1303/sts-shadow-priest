# Fourth reward-card batch

Void Bolt maintains existing DoTs, Void Volley distributes repeated Bolt effects among living enemies, and Void Torrent rewards investing energy into one target. These are the three reviewed designs from `cards.md`; their question marks are removed and complete upgrades are recorded in [the catalog](../design/cards.json).

The class now has **23 native reward cards: 10 Commons, 9 Uncommons, and 4 Rares**. Torrent supplies the fourth rare, so the existing pool-size rule stops adding temporary Ironclad rares. Question Card can generate four distinct native boss choices. Broader rare variety and full-run balance remain future work.

| Card | Rarity / cost | Base | Upgrade |
| --- | --- | --- | --- |
| Void Bolt | Common / 1 | Deal 8 damage, then add 1 stack to each existing DoT on the target | 10 damage; add 2 stacks |
| Void Volley | Uncommon / X | Resolve X random Void Bolt effects | Each effect uses Void Bolt+ values |
| Void Torrent | Rare / X | Hit one enemy X times, with base damage 4, 8, 12, ... | Base damage 5, 10, 15, ... |

## Existing DoTs and application order

Bolt deals damage before maintaining DoTs. It extends only existing Touch and Pain, capped at five remaining ticks, and adds Madness only if already present. It never introduces a missing debuff or increases the size of future applications. It does not affect Halo, unrelated debuffs, or fixed tick damage.

Duration maintenance follows Mind Flay: Insanity: extend the existing Touch/Pain counters directly, without an Artifact check. Additional Madness uses the normal debuff application path and can be blocked by Artifact. No maintenance occurs on a slain target.

## Volley: one card, multiple effects

Each bolt chooses a random living enemy using the seeded card RNG, deals damage with that target's modifiers, and completes maintenance before the next target is chosen. A killing bolt allows later bolts to choose another survivor. Dead, dying, escaped, and half-dead enemies are excluded.

The bolts are effects of one Attack card, not generated or played cards. Each direct hit generates Insanity normally, including fully blocked hits. Psychic Link and other card-play triggers see one Volley. Link resolves after the bolts and does not consume the maintained duration. Embrace healing follows the existing Link/Touch rules; the bolts themselves do not heal.

## Torrent: independent escalating hits

Torrent locks onto the selected enemy and stops damaging when that enemy dies. Remaining hits never switch targets. Each play starts a new sequence, including replaying the same physical card. The original card's starting damage is not mutated by the sequence.

Each hit calculates its damage separately from its own increasing base, applying Strength, Weak, target modifiers, Voidform, and normal per-hit rounding. It does not multiply previously modified damage. With no modifiers:

| X | Base total | Upgraded total |
| --- | --- | --- |
| 1 | 4 | 5 |
| 2 | 12 | 15 |
| 3 | 24 | 30 |
| 4 | 40 | 50 |
| 5 | 60 | 75 |

At X=3 during Voidform, base Torrent hits for 6, 12, and 18 (36 total). Torrent+ hits for 7, 15, and 22 (44 total), because fractional damage is rounded for each hit. Insanity generation is per direct hit; Psychic Link triggers once per card.

## Shared X-cost behavior

Both new X cards use the existing X-cost energy rules. Chemical X adds two effects. Free play uses X without consuming energy. Voidform increases Attack damage but does not discount X or add effects. At X=0, both base and upgraded cards produce no hits unless an external effect increases X. Playing a zero-hit Attack still counts as a card play for Psychic Link.

## Artwork

All three use their actual WoW ability icons, downloaded unchanged and displayed through the shared card frame. Source icons were visually inspected; both generated portrait sizes are covered by actual-game loading checks. Credits are packaged in [Warcraft artwork](../licenses/Warcraft-artwork.txt).

| Card | Spell reference | Icon / local filename |
| --- | --- | --- |
| Void Bolt | [205448](https://www.wowhead.com/spell=205448/void-bolt) | `ability_ironmaidens_convulsiveshadows` / `void_bolt.jpg` |
| Void Volley | [1242173](https://www.wowhead.com/spell=1242173/void-volley) | `inv12_ability_priest_voidvolley` / `void_volley.jpg` |
| Void Torrent | [263165](https://www.wowhead.com/spell=263165/void-torrent) | `spell_priest_voidsear` / `void_torrent.jpg` |

See [playtest results](playtest-results.md) for completed validation. Controlled correctness checks do not establish full-run balance, especially at high energy or with repeated Madness maintenance and Embrace sustain.
