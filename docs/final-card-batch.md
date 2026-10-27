# Final ten collectible cards

Accepted and implemented October 27, 2026. The roster now contains 75 collectible cards (23 Attacks / 35 Skills / 17 Powers), including three automatically equipped Legendary Powers. Enter Voidform is a separate generated card. Ordinary reward pools contain 28 Commons, 26 Uncommons, and 14 Rares. The authoritative designs and upgrades are in [cards.md](../cards.md) and [design/cards.json](../design/cards.json).

| Card | Rarity / type | Base -> upgrade |
|---|---|---|
| Void Surge | Common Attack | 1 energy; 6 damage + 40 Insanity -> 9 + 50 (50/60 total including the hit) |
| Madness Overwhelming | Common Skill | 1 energy; 5 + 5 per Power played -> 8 + 8 |
| Singularity | Uncommon Attack | 2 energy; 16 damage + consume DoTs for 3 per stack -> 22 + 5; Mastery adds per stack |
| Twin Suns | Uncommon Skill | 1 energy; positive enemy Strength as Haste -> that amount + 1; no Exhaust |
| Malady of the Mind | Uncommon Skill | 1 energy; double Madness -> triple; Exhaust |
| Void Entropy | Uncommon Skill | 2 energy; immediate Voidform, draw 2 -> 3; bypass Recharge |
| Dark Reinforcement | Uncommon Power | 1 energy; drawn Curses grant 1 Energy and draw 1 -> also 1 Mastery |
| Void Ray | Rare Attack | 1 energy; 15 damage + exhaust a hand Curse for permanent +5 -> 23 and +8; Exhaust |
| Mental Decay | Rare Skill | 1 energy; 10 Madness to all -> 15; add a retained Mind Decay to hand |
| Event Horizon | Rare Power | 2 energy; subsequent Power plays grant 2 Haste -> 3 |

## Timing and integration

- Void Entropy directly enters at 100 Insanity and invalidates/removes existing ready tokens. Recharge never blocks this card's entry, but ending the new form causes normal Recharge again. Replaying while active only draws; it does not reset Improved Voidform's attack count, retrigger entry relics, or weaken Surrender. Insanity gain remains blocked during Recharge. Details records successful entry normally.
- Count actual Power plays separately from equipped Legendary effects. Foresight+ counts. Newly installed Event Horizon does not trigger itself, but existing copies trigger on that play. Dark Revelation can find Madness Overwhelming and Mental Decay; Malady only increases existing Madness and is excluded.
- Singularity consumes current stacks after its first hit, preserves unrelated debuffs, and combines the bonus into one Attack hit. Strength affects only the initial hit; Mastery applies once per consumed stack, floored at zero per stack. Normal Attack multipliers and Block apply. No stacks means no second hit. Attack-triggered effects may subsequently reapply Madness.
- Dark Reinforcement's energy/draw coefficients stack independently from the upgraded-only Mastery coefficient. Only actual draws qualify; creation, retention, tutors, and Necronomicurse returning do not.
- Mental Decay now generates Mind Decay, a custom combat-only Curse with Retain and 2 blockable start-of-turn damage. Its penalty does not play/discard the card. Copies and redraw preserve Retain; full hands use the normal discard fallback. This supersedes the initial vanilla Decay subclass.
- Void Ray uses a visible Curse selection. Its hit then Curse exhaust/growth resolve in the same action, including lethal hits. Growth is stored in `misc` and synchronized only to matching UUID copies; temporary copies with new UUIDs cannot grow a permanent original. Save reconstruction restores both growth and upgraded base damage. The Curse remains in the permanent deck; its combat exhaust triggers ordinary effects such as Void Vengeance. Growth applies after the current hit.

## Artwork

Six cards use WoW icons: Void Surge, The Singularity (Rygelon), Malady of the Mind, Void Entropy's archived Priest icon, the retail Void Ray icon, and Mental Decay. Attribution/source URLs are in [Warcraft-artwork.txt](../licenses/Warcraft-artwork.txt). Void Entropy is removed from current spell metadata; its named legacy icon is still available on the asset CDN.

Madness Overwhelming, Twin Suns, Dark Reinforcement, and Event Horizon have original generated icons. [Prompts](final-card-art-prompts.json) are recorded. All ten have explicit art mappings and base/upgraded in-game renders. Long titles use reduced font sizes to clear the energy orb.

## Validation

See [the test report](playtest-results.md). Focused checks exercise the new mechanics in the patched game rather than simulating full runs. Balance remains for normal playtesting; cross-mod interactions are not exhaustively covered. Save restoration is tested through the game's card reconstruction API, not a process quit/resume cycle.
