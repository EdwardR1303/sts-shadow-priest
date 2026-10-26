# Shadow Priest combat artwork

Added October 23, 2026 at the owner's request. The combat character is a shadowy priest in Tier 2 / Transcendence-inspired regalia. The tall halo, crescent shoulders, blue inlays, and gold trim remain readable at combat scale.

## Asset and rendering

- Asset: [shadow_priest.png](../src/main/resources/shadowpriest/images/character/shadow_priest.png), 1254 x 1254, with genuine alpha transparency.
- Created with the built-in image-generation tool, not the API/CLI fallback. The generated PNG is stored unchanged in the project.
- The combat renderer applies a dark violet tint and a gentle idle bob. This is a single painted sprite, not a skeletal animation.
- Playing Enter Voidform brings up a pulsing purple silhouette glow and brighter violet coloring. The glow fades over approximately a quarter second when the form ends. A ready activation card alone does not enable it.
- The Insanity meter follows the energy orb's position directly underneath it, and hides with modal screens and the orb panel.
- Subsequent passes replace card and relic artwork; the selection button, animated rest-site rear view, and Heart-victory ending are documented in [the second presentation pass](art-pass-02.md). The large character-selection portrait is now a matching priest illustration; the death sprite and Intangible mist are covered in the [defensive rework](defensive-rework.md).

## Generation prompt

Use case: stylized-concept. Asset type: transparent full-body 2D combat character sprite for a Slay the Spire mod. Create ONE iconic World of Warcraft-inspired shadow priest wearing classic Tier 2 Vestments of Transcendence-inspired regalia: tall ornate gold winged halo/circlet headdress, broad raised crescent shoulder pauldrons with cyan-blue enchanted inlays, layered floor-length ceremonial robes with angular blue/teal and aged gold trim. The character is already in a restrained shadowform: near-black indigo and smoky violet shading, spectral pale violet eyes, face mostly lost in shadow, distinct readable armor silhouette and muted golden edges. Full body, standing in a poised spellcasting stance, three-quarter side view facing RIGHT toward an enemy, feet near bottom center, hands held in a restrained casting gesture. No staff. Hand-painted dark fantasy game art with crisp outlined shapes, simplified readable detail at small scale, suited to Slay the Spire combat. Centered on a square 1024x1024 canvas, entire headgear, shoulders, hands and robe hem fully visible, character about 85 percent of canvas height. GENUINELY TRANSPARENT BACKGROUND with alpha: no scenery, ground plane, backdrop, square black fill, checkerboard, text, labels, watermark or UI. Keep all edges isolated. No large aura or excessive particles: a purple glow will be added dynamically by game code when Voidform is activated.

The generator returned a square 1254-pixel image; the renderer scales it to the combat layout. Reference for the armor silhouette: [Halo of Transcendence](https://www.wowhead.com/item=16921/halo-of-transcendence). The generated artwork is distinct from the original WoW spell icons credited in [Warcraft artwork](../licenses/Warcraft-artwork.txt).
