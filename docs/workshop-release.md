# Steam Workshop release

Listing: [The Shadow Priest -- Beta](https://steamcommunity.com/sharedfiles/filedetails/?id=3828572814)

The first public beta is version 0.1.0. The Workshop item ID is committed in `workshop/config.json`; keep it unchanged so updates retain the listing and subscribers. Listing copy lives in `workshop/description.txt`. The preview is a JPEG encoding of the existing character-selection portrait.

## Publish an update

Requirements: the local Java/Maven setup, Python 3 on PATH, Slay the Spire installed, and Steam running under the publishing account.

1. Make and verify the changes. Update the version in `pom.xml` and `src/main/resources/ModTheSpire.json` together.
2. Edit `workshop/config.json` with the new change note. Edit the description or preview if needed.
3. Run `./scripts/publish-workshop.ps1` to build, run unit tests, and stage the release without uploading. It uses incremental `verify`, not `clean`, and stages only `shadowpriest.jar` as content.
4. Run `./scripts/publish-workshop.ps1 -Upload` to build and upload to the existing listing through the game's bundled uploader.
5. Check the listing and its change note. Steam can temporarily hide a new or updated item during automated content analysis. If Steam requests legal-agreement acceptance, complete that separately and verify visibility.
6. Commit/push the release metadata and tag the released source revision.

The temporary workspace is `.local/workshop/`. The helper preserves its item ID if an upload is interrupted, and refuses to proceed if tracked and staged IDs disagree. It refuses unexpected content files. Neither saves, Details reports, credentials, game JARs, nor dependency JARs belong in the uploaded content directory.

Steam's Required Items are configured on the listing separately from the uploader: ModTheSpire (1605060445), BaseMod (1605833019), and StSLib (1609158507). They are also linked in the description. Existing dependency links remain when uploading updates to the same item.

## First beta validation

Incremental Maven verification passed 31 unit tests. Upload completed successfully on October 27, 2026, followed by an update crediting Nishiki in the listing and mod launcher. The final uploaded JAR SHA256 was `AFFAA820785FB0976B15DC097D0A7BB0598A83861C701916EE7DE31A326700DB`; the preview was 332071 bytes. The public description, Subscribe control, and all three Required Items were checked while signed out. No broad simulation was run for packaging; the preceding focused game/UI checks are recorded in `playtest-results.md`.

Friends should subscribe to this item and all requirements, launch Steam's Play With Mods option, enable Shadow Priest/BaseMod/StSLib, and begin a Standard run. Developers should avoid loading both a local and Workshop copy of the same mod. Local playtest saves remain separate from the ordinary Steam launch profile.

## 0.1.1 release

Published October 27, 2026 with purple DoT health-bar previews. Both the Maven and mod-manifest versions are 0.1.1; patch notes are in `CHANGELOG.md` and the Workshop Change Notes tab. The upload completed successfully and the 0.1.1 notes were verified while signed out. The packaged version and preview class were verified. SHA256: `EF2833CC01B02C401BBACBB44D23DBD1E0D39EE36878EE01FC101B0BBC0A45CB`. Release packaging passed 31 unit tests; the feature's 24 focused game checks had already passed and were not repeated for the version change.

## 0.1.2 release

Published October 28, 2026 with the accepted Haste and card balance pass. The official uploader reported success for the existing public item 3828572814. Version 0.1.2 is present in Maven and the packaged mod manifest. SHA256: `BEEA2AFB0D72CEFFEE993DE05B8C1D3C7C6FF26AA33C0E7CCB86494CEC8E587F`. Validation: 31 unit tests, 57 focused real-engine assertions, catalog schema validation, and four visually reviewed card renders. No full-run simulation. The local Steam mods JAR was updated to the same artifact.

## 0.1.3 release

Published October 29, 2026 with expanded Details metrics, card balance and Curse support, and presentation fixes. The official uploader confirmed a successful update to item 3828572814. Maven and the packaged mod manifest both report 0.1.3. SHA256: `AC708C957563CADCA620EDF97956E0B8E56825E08E07E46AF6820113DD76D6DF`, unchanged from the tested local candidate. Release packaging passed 35 unit tests; the candidate previously passed 94 focused engine assertions and catalog validation. No additional full-run simulation was performed. The user authorized publication after candidate preparation.

## 0.1.4 release

Published October 30, 2026 as a Details hotfix. The official uploader confirmed a successful update to item 3828572814. SHA256: `2FEA17FEDA1830ED6E21B26D9436C9059D9AEA545E09FC15B0D5A47399F78F58`. Maven and the packaged manifest report 0.1.4. Validation passed 38 unit tests and 17 focused game assertions, plus a read-only audit of all 33 live/local reports. The fix restores 13 formerly hidden reports and adds potion attribution without balance changes.
