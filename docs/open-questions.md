# Shadow Priest open questions

The first playable prototype now has explicit rules for every core mechanic. See [prototype rules](prototype-rules.md) and decisions D019-D021 for the authorized baseline. The values below are future design work, not blockers to testing.

## Resolved for the prototype

- Starting deck: four 1-energy Mind Spikes, four 1-energy Shields, one Touch and one Pain. Shield and both DoTs are Skills.
- Character: 70 HP, 99 gold, 3 energy, 5-card draw. Upgrades: 9 damage, 8 Block, Touch 7 damage for 3 turns, and Pain 5 damage for 5 turns. Played DoTs return at turn end.
- DoTs: tick before the enemy acts, replace damage and duration on reapplication without an immediate tick, bypass Block and offensive modifiers, respect Intangible and Artifact. Only Touch and Pain count toward Insanity.
- Insanity: each player-owned normal hit counts each target's distinct DoTs, including blocked hits. Multihit and area attacks count per hit and target. Meter caps at 10 with one charge; no generation while ready or active.
- Activation: a retained, zero-cost, exhausting Skill. Full hands defer delivery. Charge identifiers prevent copied or recovered cards from spending a charge twice.
- Voidform: discount positive Attack energy costs by one without changing card cost fields; leave X costs unchanged. Apply 1.5x final outgoing Attack damage, using normal rounding and defenses. Reset at turn end and combat end.
- Toolchain: Java 8, Maven, ModTheSpire, BaseMod, StSLib, VS Code tasks. BasicMod assets are temporary.

## Balance questions for human playtests

- Is 15 delayed damage per 1-energy DoT too strong, especially when it bypasses Block?
- Do shorter Touch maintenance and longer Pain duration create useful sequencing choices?
- How often does refreshing an existing DoT feel useful?
- Does 10 Insanity allow satisfying burst turns without making multihit or area attacks dominant?
- Can the player survive setup reliably? Does waiting become too safe or repetitive?
- Does the hand contain enough attacks when Voidform is ready, or do we need draw, retention, or preparation cards?
- How often is holding the activation better than playing it immediately?
- Do the tentative upgrades and starting HP suit this slower combat loop?

## Next design choices

The first batch adds six commons and six uncommons. Design rares to replace the remaining Ironclad rare pool. Evaluate Shadow Mend sustain, reusable Foresight, multihit Insanity generation, and Psychic Link before expanding the pool. Shadow Word: Madness remains an unimplemented concept pending exact numbers and duration rules.

The combat sprite uses generated Transcendence-inspired artwork with a Voidform glow. Starter cards use framed retail WoW icons; the starter relic uses a transparent adaptation of the Voidform icon. Remaining character-select, rest-site, death, Enter Voidform, and future card art, fuller animation, sound, final naming, project licensing, and Workshop publication remain undecided. No public release or balance claim is implied by the playable prototype.
