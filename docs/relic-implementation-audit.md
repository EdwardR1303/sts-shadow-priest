# Relic implementation audit — October 25, 2026

## Finding

Only **Voidform**, the starter relic, exists in the playable implementation. The ten non-starter entries in [relics.md](../relics.md) are designs, not active reward content. There is no non-starter registration or reward-pool wiring to repair yet. This investigation does not implement any relic or approve draft mechanics.

## Evidence

- [ShadowPriestMod.java](../src/main/java/shadowpriest/ShadowPriestMod.java), `receiveEditRelics()`, registers only `new VoidformRelic()` through `BaseMod.addRelicToCustomPool` for the custom card color.
- [VoidformRelic.java](../src/main/java/shadowpriest/relics/VoidformRelic.java) is the only Java class in `shadowpriest/relics`; its tier is `STARTER`. [ShadowPriest.java](../src/main/java/shadowpriest/character/ShadowPriest.java), `getStartingRelics()`, supplies it to the character.
- [RelicStrings.json](../src/main/resources/shadowpriest/localization/eng/RelicStrings.json) contains only `shadowpriest:Voidform`.
- Inspected `target/shadowpriest.jar` and `.playtest/mods/shadowpriest.jar`: both contain only `VoidformRelic.class` and its nested art helper under `shadowpriest/relics/`. The normal playtest copy predates the newest build, but both lack the same designed relics; this is not explained by a stale JAR alone.
- The 12 saved normal-profile run histories list only `shadowpriest:Voidform` among class relic IDs. This corroborates the code/package audit; the histories alone would not prove absence from reward pools.
- Dragonwrath, Xal'atath, and Val'anyr are automatically equipped Legendary **cards**, handled by the separate Legendary equipment system. They do not register as relic rewards.

## Design inventory versus playable inventory

| Category | Designs | Implemented | Names |
|---|---:|---:|---|
| Starter | 1 | 1 | Voidform |
| Common | 1 | 0 | Shadowy Preparation |
| Uncommon | 2 | 0 | Misery ?, Idol of C'Thun |
| Rare | 3 | 0 | Idol of Y'Shaarj, Idol of N'Zoth, Inescapable Torment ? |
| Boss | 3 | 0 | Idol of Yogg-Saron, Improved Voidform ?, Void Vengeance |
| Shop | 1 | 0 | Dark Void ? |
| Total | 11 | 1 | 10 non-starters missing |

Question marks are copied from the current design file. Entries without a question mark can still lack implementation. Several brief relic entries also need timing/stacking details before their behavior is fully specified.

## Suggested implementation order

1. **Shadowy Preparation:** its agreed 10 Insanity per starting enemy directly helps the first Voidform. Implement reset/trigger ordering and normal charge restrictions. Reassess any proposed universal opening Insanity bonus alongside it.
2. **Misery and Idol of C'Thun:** support application consistency. Misery is currently marked unreviewed, so settle its current rules before coding. C'Thun now has an agreed free Tentacle Slam; the discount duration is awaiting clarification in relics.md. Opening-hand ordering and hand-capacity handling still need implementation.
3. **Idol of N'Zoth and Idol of Y'Shaarj:** archetype scaling and Voidform payoff. N'Zoth has since been clarified to grant Mastery on both actual Power plays and successful automatic Legendary equipment activation. This exception must not make equipment count as a global card play. Y'Shaarj's actual-energy-spent rule is already documented.
4. **Boss and Shop content:** establish the class-specific alternatives at those reward sources. Improved Voidform remains a major unreviewed mechanic change; the Shop Dark Void proposal also remains unreviewed. Inescapable Torment's rarity/value still needs acceptance.

Each implemented relic needs an ID, tier, effect class, descriptions, matching art, and registration to the custom pool. Verification should inspect the initialized Common/Uncommon/Rare/Boss/Shop pools for the Shadow Priest, verify eligibility/exclusion on other characters, and exercise appropriate reward/shop selection—not merely force-spawn the relic. Implementation of those designs is a separate follow-up; no pool odds, relic designs, or combat behavior were changed by this audit.
