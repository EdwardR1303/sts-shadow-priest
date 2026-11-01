# Xal'atath presentation pass

October 31, 2026. Display name: **Xal'atath, the Shadow Priest**.
Internal character, card-color, relic, save, and run-history IDs remain unchanged.
Prepared and playtested locally, then accepted for the 1.0.0 release.

## Artwork

Five illustrations were generated with the built-in image-generation tool.
The exact prompts are preserved in [xalatath-prompts.json](xalatath-prompts.json).
The final facial-rune corrections are in [xalatath-rune-prompts.json](xalatath-rune-prompts.json),
using a close-up of Blizzard illustrator Bayard Wu's Xal'atath portrait as reference.
The in-game [Xal'atath model](https://www.wowhead.com/npc=230787/xalatath)
provided the outfit reference; Blizzard's [Midnight art showcase](https://news.blizzard.com/en-us/article/24272607/check-out-the-world-of-warcraft-midnight-art-blast)
and [commissioned cinematic art](https://mooncolony.co/project/world-of-warcraft-midnight)
were also consulted. These are generated fan illustrations, not extracted official character textures.

| Surface | Runtime asset under shadowpriest/images/character |
| --- | --- |
| Combat, Voidform, Dispersion transition | shadow_priest.png |
| Character selection | select/portrait.png |
| Rear view at rest sites | rest_priest.png |
| Defeat | fallen_priest.png |
| Heart victory | ending/heart_victory.png |

The transparent sprites preserve their generated alpha. The selection image is
resampled to the engine's required 1920 x 1200 source rectangle. Its JPEG export
also updates the workshop/image.jpg preview. The existing skull selection
button remains. Historical, unused Ironclad/SCML template files are not runtime assets.

## Animation

Xal'atath hovers above a soft ground shadow, with visible bare feet. Combat draws
the sprite mirrored so she faces the enemies. Two procedural void orbs bob with
independent phases over her shoulders. Voidform brightens the body and aura,
enlarges the orbs and accelerates their motion. Dispersion fades body, orbs and
ground shadow into the existing animated mist. Rest-site art retains subtle
breathing and firelight modulation. This is painted sprite animation, not a skeletal model.

## Local progression

Run scripts/unlock-playtest.ps1 with the local playtest closed to unlock the active
slot in .playtest/preferences. The script backs up the preferences under
.local/progression-backups before writing. It enables the Heart route, all base
card/relic unlock bundles, all characters, A20, Custom Mode and beta-art access.
It preserves existing run files and the last selected Ascension.

This uses verified game preference keys and never writes the live Steam save
directory or calls Steam achievement APIs. Keys still need to be collected normally;
start a new run to use the newly unlocked Heart route.
The playtest-only achievement guard also prevents the game's automatic local-achievement
sync from uploading those flags to Steam. Normal Workshop launches keep their usual behavior.

## Verification

Use scripts/playtest.ps1 -PresentationOnly for a short engine-rendered review
of normal combat, Voidform, Dispersion, rest, defeat and illustration loading.
Review images are saved under .smoketest/card-review/xalatath-surfaces.png and
.smoketest/card-review/combat-xalatath-*.png. This does not simulate a run.

Final verification: incremental build passed all 39 unit tests; the short engine
review passed and its normal/Voidform/Dispersion captures were inspected. The game
reported 9/9 unlocked cards for each base color and 24/24 unlocked relics in local
slot 2. All 63 progression IDs (including characters) were checked against
UnlockTracker bytecode. The local playtest JAR matches the final build.
