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
| D010 | Automatically enter Voidform at 10 Insanity. | Activation is triggered by the meter rather than an activation card. |
| D011 | In Voidform, all Attack cards cost 1 less energy and deal 50% more damage. | The burst reward applies to Attack cards; edge cases remain open. |
| D012 | Voidform ends at the end of the turn. | The remaining hand and energy affect how much value the form provides. |
| D013 | Establish a repository and record the design before gameplay implementation. | This initial commit contains documentation and repository housekeeping only. |
| D014 | Use a public GitHub repository named sts-shadow-priest. | The repository is intended to be publicly readable. |

## Earlier concepts replaced by this baseline

The initial brainstorming suggested entering Voidform through a card and sustaining it through increasing Insanity drain. The owner's later threshold of 10 and end-of-turn expiry replace that suggestion.

Mind Blast as a starter and vampiric conversion into Block were brainstorming ideas. Neither is part of the current starting deck or accepted card effects.

## Future decision entries

For each new choice, record its date, the rule accepted, and any earlier choice it replaces. Keep the character design synchronized so readers do not need to reconstruct the current rules from this log.
