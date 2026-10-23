# Shadow Priest open questions

These choices are unresolved. Suggestions from the discussion are recorded as proposals, not accepted behavior.

## Starting deck

| ID | Question | Proposal or context |
| --- | --- | --- |
| Q001 | What do Power Word: Shield, Vampiric Touch, and Shadow Word: Pain cost? | 1 energy each was suggested, but not confirmed. |
| Q002 | What are the starting quantities? | Proposed: four Mind Spikes, four Shields, one Touch, and one Pain. |
| Q003 | What card types should Shield and the two DoT cards have? | This matters because Voidform explicitly discounts and strengthens Attack cards. |
| Q004 | What are the upgraded versions? | No upgrade values selected. |
| Q005 | What are the character's starting and maximum HP, starting gold, and other character defaults? | No values selected. |

## DoT timing and interactions

| ID | Question | Proposal or context |
| --- | --- | --- |
| Q006 | When does a DoT tick, and when is its duration reduced? | Proposed: tick at the start of the afflicted enemy's turn, then reduce remaining duration by one. Application-turn behavior must be explicit. |
| Q007 | Does reapplication only restore the duration to three turns? | Proposed: reset to three without an immediate damage tick. |
| Q008 | How do DoTs interact with Block, Vulnerable, Intangible, Artifact, and other defenses or modifiers? | Strength independence is accepted; the other interactions are not. |
| Q009 | Which effects count as unique DoTs for this character? | Touch and Pain count. Poison, effects from other mods, and future afflictions need a scope rule. |

## Insanity generation

| ID | Question | Proposal or context |
| --- | --- | --- |
| Q010 | Does Insanity persist between turns? | Proposed: yes. Otherwise the starter deck may struggle to reach 10 in one turn. |
| Q011 | When does Insanity reset or get spent? | Proposed: reset after combat and when Voidform ends. Threshold consumption and overflow are undecided. |
| Q012 | Does each hit of a multihit card generate Insanity separately? | Proposed: yes. A three-hit attack against two DoTs would generate 6. |
| Q013 | What does direct damage include beyond a basic attack? | Decide treatment of fully blocked or zero-damage hits, HP loss, relics, retaliation, and damage from other effects. DoT ticks were proposed to be excluded. |
| Q014 | How do attacks against multiple enemies generate Insanity? | Decide whether each target contributes using its own unique-DoT count. |
| Q015 | When is the target's DoT count sampled? | Important for attacks that also apply, consume, or remove DoTs and for killing hits. |

## Voidform details

| ID | Question | Proposal or context |
| --- | --- | --- |
| Q016 | Does the hit that reaches 10 Insanity gain the damage bonus? | Resolve event ordering, including later hits of the same card. |
| Q017 | Can Insanity accumulate during Voidform or cause another activation in the same turn? | No rule selected. |
| Q018 | How does the discount work with zero-cost attacks, X-cost attacks, and other cost changes? | Define the cost floor and restore only this relic's discount on expiry. |
| Q019 | How does the 50% bonus round and combine with Strength, Weak, Vulnerable, and other modifiers? | Base Mind Spike is 6 to 9; less straightforward cases need a rule. |
| Q020 | What happens if Voidform activates after the player has spent their useful hand? | Test automatic activation as designed. Drawing one card on entry is an optional adjustment suggested in discussion, not an accepted relic effect. |

## Balance questions for playtests

- Is 15 delayed base damage per DoT application appropriate at the selected energy cost?
- Do two mechanically identical starter DoTs create useful sequencing choices through their distinct identities?
- Does refreshing a DoT feel useful often enough to justify drawing it again?
- Does the 10-Insanity threshold produce satisfying burst turns without excessive multihit or multi-target acceleration?
- Does ending Voidform at turn end frequently waste the activation?

## Project choices for implementation

Choose a mod template and compatible Java/Maven setup when implementation begins. Art, animation, upgrades, additional cards, licensing, and distribution details have not been chosen. Placeholder assets are an option for the prototype.
