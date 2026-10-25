# Legendary and character presentation artwork

Requested October 24, 2026. Created with the built-in image-generation tool using existing weapon model renders and the established combat priest as visual references. The complete prompt set is in [art-pass-02-prompts.json](art-pass-02-prompts.json).

## Legendary portraits

The three equipment cards now use full-portrait painted scenes instead of inset inventory icons:

- [Dragonwrath](../src/main/resources/shadowpriest/images/cards/legendary/dragonwrath.png): the actual staff silhouette in sapphire arcane currents.
- [Xal'atath](../src/main/resources/shadowpriest/images/cards/legendary/xalatath.png): the hooked blade and eye jewel surrounded by violet void energy.
- [Val'anyr](../src/main/resources/shadowpriest/images/cards/legendary/valanyr.png): the gold-and-turquoise hammer in warm sanctuary light.

The source paintings are stored unchanged. StarterCardArt uses a centered aspect-preserving crop for the 500 x 380 portrait and derives its 250 x 190 version. The normal orange Legendary card frame remains. The existing icon files remain available for power icons, and the floating combat weapon sprites are unchanged.

These paintings are generated adaptations of Blizzard weapon designs, not official Blizzard illustrations. Reference model sources remain credited in [Warcraft artwork](../licenses/Warcraft-artwork.txt).

## Character-selection button

[button.png](../src/main/resources/shadowpriest/images/character/select/button.png) replaces the blank template with a charcoal outline of the Shadow Word: Pain skull. Its pale blue bevel, transparent padding, and simple linework follow the base-class buttons. The generated result was mechanically downsampled to the same 200 x 200 asset size as the base classes, preserving alpha. Hover/selection highlighting remains the game's normal behavior.

## Rest sites

[rest_priest.png](../src/main/resources/shadowpriest/images/character/rest_priest.png) shows the same priest from behind, including the Transcendence-inspired headdress, pale hair, crescent shoulders, and blue/gold robes. The transparent sprite replaces the inherited Ironclad foreground in both ordinary and hidden-option campfire states.

ShadowPriest.renderShoulderImg positions the bust at the lower left, with the robe extending below the screen. It animates subtle breathing, slight sway, and a restrained warm light flicker. This is a moving painted sprite, not skeletal animation. The SpriteBatch color is restored after drawing.

## Heart victory

[heart_victory.png](../src/main/resources/shadowpriest/images/character/ending/heart_victory.png) depicts the priest shattering the Corrupt Heart with void magic. CustomPlayer's ending hooks now return a purple backdrop and this full-screen painting instead of the default Ironclad panels. It uses the game's normal fade-in, hold, click-to-advance, fade-out, and transition to the victory results. This is one complete tableau rather than three comic panels.

## Character-selection backdrop follow-up

The large question-mark placeholder was replaced with [portrait.png](../src/main/resources/shadowpriest/images/character/select/portrait.png): the established priest on the right, with dark space on the left for the character description and stats. The source image was created with the built-in image generator and mechanically resized to 1920 x 1200 because the installed game samples that exact source rectangle. The obsolete Ironclad-reward claim was removed from both the selection description and ModTheSpire metadata. The corpse sprite remains a separate unfinished asset.

Final generation prompt:

> Create a landscape 16:10 painted fantasy character-selection background, target 1920x1200. Use the attached character as the costume and appearance reference: violet-skinned elf spellcaster with long pale hair, tall gold circlet, curved gold-and-blue shoulder armor and flowing indigo ceremonial robes. Show the character standing on the RIGHT side of the composition, facing slightly left, from knees upward, with a small purple magical light hovering above one open hand. Preserve the recognizable outfit and face. Use a polished hand-painted game illustration style, with restrained purple lighting and ancient dim stone arches behind the character. Leave the LEFT 55 percent of the image very dark and mostly empty for game interface text. Keep the bottom edge dark and uncluttered for interface buttons. Entire image should be an opaque painted background. No text, symbols, logos, borders, user-interface elements, weapons, other characters, or question marks.

## Verification scope

Generated images were visually inspected and integration was checked against the installed game's rendering methods and BaseMod hooks. The package is compiled with tests explicitly skipped at the user's request. No automated game checks or game launches are part of this art pass; in-game placement and animation remain for the next human playtest.
