# Designing cards

Start in [design/cards.json](../design/cards.json). It contains the four implemented starters, 16 reward cards, and the generated Enter Voidform card as worked examples. The owner can also write ideas in plain English in their working cards.md file; reviewed batches are translated into this catalog before implementation. Copy the object inside `cards` from [card-template.json](../design/card-template.json), paste it at the end of the catalog's `cards` array, and change its ID and name. The template's Block values are examples, not an approved new card.

These files are design documents. They do not automatically add cards to the game; approved designs still need Java implementation, localization, art, and testing. The implemented examples describe the current prototype, while new entries start as `DRAFT`.

In the working `cards.md`, a trailing `?` marks an unreviewed card. Remove it after agreement and record the design as `APPROVED` in the catalog. Fortitude, Reaping Madness, Shadow Word: Madness, and Halo have completed review and implementation. Existing WoW abilities should use their actual ability artwork; invented abilities should receive matching original thematic artwork.

## Fields

| Field | What to write |
| --- | --- |
| `id` | Unique stable ID, e.g. `shadowpriest:MindBlast`. No spaces. Keep it unchanged when renaming the display name. |
| `name` | Display name without the upgrade's `+`. |
| `status` | `DRAFT`, `READY_FOR_REVIEW`, `APPROVED`, `IMPLEMENTED`, or `CUT`. |
| `type` | `ATTACK`, `SKILL`, `POWER`, `STATUS`, or `CURSE`. |
| `rarity` | `BASIC`, `COMMON`, `UNCOMMON`, `RARE`, `SPECIAL`, or `CURSE`. Starting cards are Basic; generated cards are usually Special. |
| `sources` | Where it comes from: `STARTER_DECK`, `REWARD_POOL`, `GENERATED`, `EVENT`, or `SHOP_ONLY`. Multiple sources are allowed. Reward-pool cards can also appear in ordinary shops; use Shop Only for an exclusive design. |
| `startingCopies` | Number in the starting deck, otherwise `0`. |
| `tags` | Optional design/implementation labels, e.g. `DOT`, `BURST`, `DEFENSE`, `STRIKE`. Free text; labels do not grant effects. |
| `designIntent` | The choice this card adds and its role in setup, survival, or burst. |
| `base` | The complete unupgraded version; see below. |
| `upgrade` | `null` if it cannot upgrade. Otherwise a `summary` and a complete upgraded `card`. |
| `rulesNotes` | Timing, refresh rules, scaling, unusual interactions, and unresolved questions. |
| `balanceNotes` | Cost/rarity rationale and what to observe in a playtest. |
| `art` | WoW ability name, icon filename or source URL, and visual direction. Empty strings are fine while drafting. |

## Base and upgraded versions

Both `base` and `upgrade.card` use the same structure:

- `cost`: a nonnegative energy number, `"X"`, or `"UNPLAYABLE"`.
- `target`: `ENEMY`, `ALL_ENEMY`, `SELF`, `NONE`, `SELF_AND_ENEMY`, or `ALL`.
- `text`: the text a player should read, with actual numbers.
- `stats`: named numbers such as `damage`, `block`, `draw`, `tickDamage`, `duration`, or `hits`. Add new names when needed; use `{}` when there are no numeric values.
- `keywords`: any of `RETAIN`, `EXHAUST`, `ETHEREAL`, and `INNATE`. Use `[]` when absent. Describe other mechanics in the effects/rules notes.
- `effects`: ordered plain-language rules. These are readable design instructions, not an executable scripting language.

Write the **full upgraded result**, including unchanged values. There is no inheritance or automatic addition: a DoT upgrade specifies `tickDamage: 6` and `duration: 4`, not increments of `1`. If an upgrade removes Exhaust, omit it from the upgraded keywords. Keep text, stats, keywords, and effects consistent. One upgrade is supported by this initial design format; record any multi-upgrade idea in rules notes for review.

For conditional or scaling cards, describe the formula and its timing in `effects`, put constants in `stats`, and explain edge cases in `rulesNotes`. For example: "Deal damage plus bonusPerDot for each unique DoT currently on the target." Clarify whether this is one hit or multiple hits, because that affects Insanity.

## Editing in VS Code

The local [JSON Schema](../design/cards.schema.json) supplies completion and validation through each file's `$schema` reference. Use Ctrl+Space for choices. JSON requires double quotes, commas between entries, and no trailing commas or comments; use the note fields for discussion. The schema catches missing fields and invalid choices, but it cannot judge balance, unique IDs, or consistency between prose and numeric values.

When ready, change your entry to `READY_FOR_REVIEW`. We can review it together, then mark an accepted design `APPROVED` before implementation. Keep the [prototype rules](prototype-rules.md) and implemented entries synchronized when a change ships.
