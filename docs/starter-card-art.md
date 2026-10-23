# Starter card artwork

The four starting abilities use the original retail WoW icon identities: Mind Spike (`spell_priest_mindspike`), Power Word: Shield (`spell_holy_powerwordshield`), Vampiric Touch (`spell_holy_stoicism`), and Shadow Word: Pain (`spell_shadow_shadowwordpain`). The source icon images are stored unchanged. See [source attribution](../licenses/Warcraft-artwork.txt).

The game places each square icon over a shared painted violet-and-gold frame with a subtle violet rim. Cached 250 x 190 portraits serve normal cards, and 500 x 380 portraits serve enlarged inspection views. Styling is applied around the icons rather than repainting their subjects. Enter Voidform is generated rather than part of the starting deck and retains its existing art for now.

## Assets

- [Generated shadow frame](../src/main/resources/shadowpriest/images/cards/shadow_frame.png)
- [Mind Spike source icon](../src/main/resources/shadowpriest/images/cards/icons/mind_spike.jpg)
- [Power Word: Shield source icon](../src/main/resources/shadowpriest/images/cards/icons/power_word_shield.jpg)
- [Vampiric Touch source icon](../src/main/resources/shadowpriest/images/powers/vampiric_touch.jpg)
- [Shadow Word: Pain source icon](../src/main/resources/shadowpriest/images/powers/shadow_word_pain.jpg)

## Frame generation prompt

The frame was created with the built-in image-generation tool on October 23, 2026. No API/CLI fallback was used. Final prompt:

Use case: stylized-concept. Asset type: shared background for Slay the Spire Shadow Priest card art, behind original World of Warcraft ability icons composited separately by the game. Generate one landscape 4:3 illustration, no text. Dark ink-black and smoky indigo stone, delicate violet arcane filigree and aged muted gold corner ornaments, restrained amethyst glow at the edges, painterly dark fantasy style compatible with Slay the Spire. The CENTRAL 65 percent of the width and 85 percent of height must be clean almost-black unobstructed negative space, a quiet square icon well; no emblem, character, spell, lettering or focal object there. Decorative energy wisps and engraved angular shapes stay along the left and right outer edges. Keep the design subtle so a brightly colored square ability icon layered in the middle remains the focus. No complete trading-card UI, no banners, costs, labels, logos, watermark, or readable runes. Full bleed rectangular opaque background. This is a decorative surrounding plate, not a new ability icon.
