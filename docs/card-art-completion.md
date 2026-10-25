# Card artwork completion

The first human run exposed generic portraits on earlier cards. On October 24, 2026, the remaining twelve card mappings were filled in. This changes presentation only.

| Card | Artwork |
| --- | --- |
| Mind Flay | WoW Mind Flay, spell 15407 |
| Mind Flay: Insanity | WoW Mind Flay: Insanity, spell 391403 |
| Mind Blast | WoW Mind Blast, spell 8092 |
| Mind Sear | Archived WoW Mind Sear, spell 48045; verified through Cataclysm tooltip metadata because the retail endpoint no longer returns it |
| Shadow Word: Death | WoW Shadow Word: Death, spell 32379 |
| Fade | WoW Fade, spell 586 |
| Dispersion | WoW Dispersion, spell 47585 |
| Desperate Prayer | WoW Desperate Prayer, spell 19236 |
| Psychic Link | WoW Psychic Link, spell 199484 |
| Bulwark | Related Priest ability Angelic Bulwark, spell 108945 |
| Enter Voidform | Existing unmodified WoW Voidform source icon, spell 228264 |
| Foresight | Original generated prophetic-eye/grimoire icon |

Downloaded icons are stored unchanged. The existing renderer adds the shared violet card frame at both normal and enlarged portrait sizes. Sources and metadata URLs are recorded in [Warcraft artwork attribution](../licenses/Warcraft-artwork.txt). Foresight remains an original interpretation of this custom card, rather than borrowing the unrelated Norgannon item effect.

All 49 collectible designs and generated Enter Voidform now have explicit artwork mappings. The two Serendipity chooser cards reuse Serendipity's art. Future unmapped cards still have the existing generic fallback.

## Foresight generation

Generated with the built-in image-generation tool, inspected, and copied unchanged into `src/main/resources/shadowpriest/images/cards/icons/foresight.png`.

Prompt:

> Use case: stylized-concept. Asset type: square fantasy MMORPG spell icon for a custom Shadow Priest card named Foresight in a Slay the Spire mod. Primary request: original hand-painted ability icon with the bold shapes and luminous brushwork of classic World of Warcraft spell icons. Subject: a single luminous violet prophetic eye floating over a shadowy open grimoire, a curling arc of pale lavender psychic energy revealing a future spell. Deep midnight-indigo background, strong readable eye silhouette, rich purple with a few pale gold highlights. Full-bleed square composition, crisp major shapes legible at 64 pixels, restrained detail. No card frame, no border, no lettering, no text, no watermark. One image.

## First-run evidence and logs

Run `1792838783`, seed `6219600539517761403`: A0, death to Awakened One on floor 50, 35-card final deck. The player reported reaching phase two with under 100 boss HP remaining; that remaining HP is not present in the run summary.

The saved `.run` file contains the final deck, relics, card reward choices, purchases, event choices, campfire choices, per-floor HP, and encounter damage/turn totals. `.playtest/console.log` also records card-use order and turn energy recharges, but is not a complete replay: hands, targets, energy spent on each X card, and individual damage results are not fully recorded. `.playtest/error.log` captures stderr. The desktop-launcher follow-up now archives both log files under `.playtest/logs/<timestamp>/` before the next normal launch replaces the active files.

This run's summary and both logs were preserved locally under `.local/run-archives/1792838783/`, outside Git. Automated test launches use the separate `.smoketest/` directory.

In the Awakened One encounter, the first recorded plays of Touch, Pain, and Madness occur on turn 4; Enter Voidform first appears on turn 5. Psychic Link was played on turn 2. This supports investigating delayed setup and DoT access, but does not establish when cards were drawn or why plays were delayed.

Chemical X was bought on floor 28. With no other modifiers, an upgraded Void Torrent at 3 energy deals 5+10+15=30 damage; Chemical X adds two hits for 5+10+15+20+25=75. This illustrates a large benefit, not a reconstruction of actual damage in this run. Balance without Chemical X remains untested by this single run.
