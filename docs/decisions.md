# Shadow Priest decision log

Accepted choices establish the current design baseline. Proposed defaults and unanswered questions belong in [Open questions](open-questions.md), rather than being silently treated as decisions.

## Accepted choices on October 23 2026

| ID | Choice | Consequence |
| --- | --- | --- |
| D001 | Make a Slay the Spire 1 character inspired by WoW's Shadow Priest. | Center the character on afflictions, Insanity, and Voidform. |
| D002 | Mind Spike replaces the basic Strike: 1 energy, 6 damage. | Provides a simple direct attack for the initial deck. |
| D003 | Power Word: Shield replaces the basic defense and grants 5 Block. | Its energy cost still needs a decision. |
| D004 | Include Vampiric Touch and Shadow Word: Pain as separate DoTs, each dealing 5 damage each turn for 3 turns. | Both can contribute to the unique-DoT count on a target. |
| D005 | DoT reapplication refreshes rather than amplifies the effect. | Repeated applications maintain a debuff; they do not stack its damage or duration. |
| D006 | Strength does not modify these DoTs. | Direct attack scaling and DoT scaling remain distinct. |
| D007 | Later cards will synergize with how many unique DoTs are on a target. | Distinct afflictions are a foundation for future card design. |
| D008 | The starting relic is Voidform and provides a custom Insanity bar. | The core resource mechanic is available at the start of a run. |
| D009 | Gain 1 Insanity per DoT on the target per direct damage source. | Two DoTs plus three Mind Spikes produce 6 Insanity. |
| D010 | Automatically enter Voidform at 10 Insanity. | Superseded by D017: the player now chooses activation by playing a generated card. |
| D011 | In Voidform, all Attack cards cost 1 less energy and deal 50% more damage. | The burst reward applies to Attack cards; edge cases remain open. |
| D012 | Voidform ends at the end of the turn. | The remaining hand and energy affect how much value the form provides. |
| D013 | Establish a repository and record the design before gameplay implementation. | This initial commit contains documentation and repository housekeeping only. |
| D014 | Use a public GitHub repository named sts-shadow-priest. | The repository is intended to be publicly readable. |
| D015 | Vampiric Touch and Shadow Word: Pain each cost 1 energy. | Both starter DoT costs are settled. Power Word: Shield's cost remains unconfirmed. |
| D016 | Insanity persists between turns and resets to zero after combat and when Voidform ends. | Progress can accumulate across turns, but does not survive combat or a completed Voidform. |
| D017 | At 10 Insanity, generate a zero-cost card with Retain that enters Voidform and exhausts when played. | Replaces D010. The player can hold the earned burst for a later turn. |
| D018 | Focus the class on stalling and surviving through setup, then unleashing a massive burst turn. | Coordinating a damage amplifier, cooldowns, and consumables in a raid is the intended inspiration. Specific support cards are not yet chosen. |
| D019 | Proceed with the complete first-playable milestone and suggested defaults. | Implement the 4/4/1/1 deck, 1-cost Shield, Skill types, enemy-start DoT ticks, per-hit generation, and one capped charge. |
| D020 | Use provisional edge-case rules to make the prototype testable. | Blocked hits count; DoTs bypass Block but respect enemy damage hooks; Artifact blocks application; X costs remain unchanged; template stats and simple upgrades are used. These implementation choices under D019 remain adjustable. See the prototype rules. |
| D021 | Use a 3840 x 2160 display for playtesting. | The launcher defaults to 4K borderless for readability; dimensions can be overridden explicitly. |
| D022 | Use the actual World of Warcraft icons for Touch and Pain debuffs (October 23, 2026). | Replace Poison/Vulnerable placeholders in the debuff row and larger power displays; preserve duration counters. Source attribution is recorded separately from the BasicMod license. |
| D023 | Move Insanity just beneath the energy orb (October 23, 2026). | Anchor the compact meter to the orb's live position, with building, ready, and active labels. |
| D024 | Use a shadowy priest in Tier 2-inspired regalia, glowing purple during Voidform (October 23, 2026). | Replace the combat placeholder with generated transparent artwork; add idle motion and an active-state-driven purple glow. |
| D025 | Starter card artwork uses actual retail WoW ability icons with some stylization (October 23, 2026). | Preserve the icon art and surround it with a shared violet-and-gold frame in normal and enlarged card views. |
| D026 | Upgrading either DoT adds 1 damage per tick and 1 turn (October 23, 2026). | Base 5 x 3 becomes 6 x 4. Reapplication replaces both values; Artifact blocks both changes. Supersedes duration-only prototype upgrades. |
| D027 | Use WoW's Voidform icon for the starting relic, with a relic-shaped silhouette (October 23, 2026). | Adapt the icon into a transparent organic cutout, with padding for normal and enlarged relic displays. |
| D028 | Provide a card-design template including rarity and upgrades (October 23, 2026). | Maintain a design-only JSON catalog, reusable template, local schema, and guide; complete base and upgraded states make proposed changes explicit. |

## Activation proposal outcome on October 23 2026

**P001: On-demand Voidform** became D017 following the owner's agreement that Exhaust is mandatory and that the class should prepare for a chosen burst turn. D019 subsequently authorized the first-playable defaults, including Skill as the card type, meter capping, one charge, and stopping generation during Voidform. [Prototype rules](prototype-rules.md) specify the implemented lifecycle.

## Earlier concepts replaced by this baseline

The initial brainstorming suggested entering Voidform through a card and sustaining it through increasing Insanity drain. The current design instead earns a retained activation card at 10 Insanity and ends Voidform at turn end; escalating drain is not part of it.

Mind Blast as a starter and vampiric conversion into Block were brainstorming ideas. Neither is part of the current starting deck or accepted card effects.

## Future decision entries

For each new choice, record its date, the rule accepted, and any earlier choice it replaces. Keep the character design synchronized so readers do not need to reconstruct the current rules from this log.
