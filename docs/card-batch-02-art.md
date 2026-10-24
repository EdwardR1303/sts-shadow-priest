# Second batch artwork

Existing WoW abilities use their actual ability icons, stored unchanged and framed by the mod's shared violet-and-gold card artwork. Icons were verified against Wowhead spell tooltip data and retrieved October 23, 2026.

| Card | Source ability | Resource under `src/main/resources/shadowpriest/images/cards/icons/` |
| --- | --- | --- |
| Power Word: Fortitude | [Spell 21562](https://www.wowhead.com/spell=21562/power-word-fortitude), `spell_holy_wordfortitude` | `power_word_fortitude.jpg` |
| Shadow Word: Madness | [Spell 335467](https://www.wowhead.com/spell=335467/shadow-word-madness), `inv12_ability_priest_powerwordmadness_eye` | `shadow_word_madness.jpg` |
| Halo | [Spell 120517](https://www.wowhead.com/spell=120517/halo), `ability_priest_halo` | `halo.jpg` |
| Reaping Madness | Original custom ability artwork | `reaping_madness.png` |

Madness, Halo, and Reaping Madness reuse their corresponding card icons in the power row. Fortitude grants the game's ordinary Dexterity power. The card renderer caches 250 x 190 and 500 x 380 framed portraits; the source icons remain intact. See [Warcraft attribution](../licenses/Warcraft-artwork.txt).

## Reaping Madness generation

Created with the built-in image-generation tool; no CLI/API fallback. The generated result was inspected and copied into the repository at [reaping_madness.png](../src/main/resources/shadowpriest/images/cards/icons/reaping_madness.png). It shows a violet spectral hand gathering psychic energy and ghostly wisps. The source was retained unchanged. Final prompt:

Use case: stylized-concept. Asset type: square fantasy MMORPG ability icon for a custom Shadow Priest card named Reaping Madness. Create a polished hand-painted spell icon matching the bold readable silhouettes and luminous brushwork of World of Warcraft ability icons. A shadowy violet spectral hand reaps curling wisps of purple psychic energy into a bright amethyst orb in its palm, suggesting steadily gathering insanity. Deep black-indigo background, vivid lavender highlights, subtle ghostly faces within the wisps. One strong central silhouette readable at 64 pixels. Full-bleed square composition, no border, no text, no letters, no watermark. This is an original invented ability, not an existing official icon. Output one square image.
