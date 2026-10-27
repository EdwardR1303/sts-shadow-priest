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

## First reward-card batch on October 23 2026

| ID | Choice | Consequence |
| --- | --- | --- |
| D029 | Implement and test the owner's reviewed 16-card Markdown draft while they continue designing. | Snapshot the current four basics and 12 reward cards; leave the working draft untouched. New rewards are six Common and six Uncommon cards; retain the temporary Ironclad rare pool until rares are designed. See [batch rules](card-batch-01.md). |
| D030 | Differentiate basic DoTs and return them only after being played. | Touch is 5/7 damage for 3 turns; Pain is 3/5 for 5 turns. Neither upgrade increases duration. Played instances return after end-turn discard, subject to hand space and Exhaust. Supersedes D026. |
| D031 | Keep Foresight reusable because its dead draws after Powers are gone are intentional. | Base tutors one Power for 0 energy; upgraded plays that Power for free. Neither version Exhausts or grants Retain. |
| D032 | Use the revised defense, multihit, self-damage, and Psychic Link designs at their proposed rarities and values for the first test. | Preserve Shadow Mend's rising cost, Death's per-hit backlash, Dispersion's Retain upgrade, and Link's per-Attack bonus Touch tick. Implementation edge rules are documented for review rather than silently changing balance. |

## Second reward-card designs approved on October 23 2026

These decisions are implemented in [the second reward-card batch](card-batch-02.md). Removing a trailing `?` in the working card draft marks review complete, not implementation complete.

| ID | Choice | Consequence |
| --- | --- | --- |
| D033 | Fortitude remains a Common, 0-energy Power granting a nonstacking 2/3 Dexterity benefit. | Track the highest Fortitude contribution this combat, independent of other Dexterity or later losses. Upgrade after base adds only 1. Equal or weaker copies are unplayable. |
| D034 | Reaping Madness is a Common, 1-energy Power granting 1/2 Insanity at end of turn; copies stack. Add a recovery turn to the Voidform relic. | Every Insanity source is blocked for the entire turn after entering Voidform, including end-of-turn effects. Existing ready/active restrictions and resets remain. |
| D035 | Madness does not tick or decay. Shadow Word: Madness is an Uncommon, 1-energy Attack applying 5 stacks before one hit equal to the selected target's total Madness. | Positive stacks count as one unique DoT; reapplication adds stacks. No duration extension applies. The upgrade applies 5 to all enemies but still damages only the selected target. This replaces the proposed 8-stack upgrade and earlier ticking concept. |
| D036 | Halo is an Uncommon, 1-energy Skill creating a separate player aura per cast. | Each aura deals 3/5 damage to all current enemies at the end of the casting turn and the next two turns. Pulses respect Block, ignore Strength and Voidform's damage bonus, and generate no Insanity. Halo is not a DoT or Attack and does not trigger Psychic Link. |

## Third batch decisions on October 23 2026

| ID | Choice | Consequence |
| --- | --- | --- |
| D037 | Touch and Pain have fixed tick damage of 5 and 3, respectively; all remaining-tick applications and extensions cap at 5. | Reapplication adds remaining ticks up to the cap. Touch+ applies 4 instead of 3; Pain+ costs 0. Supersedes variable damage and replacement refresh in D030. |
| D038 | Native Shadow Priest cards never apply Vulnerable. | Access through events, relics, colorless cards, or other outside sources remains allowed. |
| D039 | Implement Tentacle Slam at 8 damage/2 energy, upgraded 10/1, with 3 Touch applied after the hit to the target and intervening enemies. | Damage is single-target; position is relative to the player in either direction. Touch obeys the universal cap. |
| D040 | Void Blast adds the source's DoT stacks to other enemies rather than copying/replacing them. | Snapshot before the hit, preserve the source, cap Touch/Pain at 5, and add Madness normally. Artifact checks each debuff. Killing blows still spread. Damage is 10/15 for 1 energy. |
| D041 | Mass Dispel removes all enemy Block, Artifact, and positive Strength for 2/1 energy without Exhaust. | Preserve negative Strength and future buff generators; removal bypasses Artifact. |
| D042 | Vampiric Embrace costs 3 and heals 1/2 per damaging Touch event, with stacking copies and no per-turn cap. | Include natural and Psychic Link ticks per enemy. Actual HP loss is required; normal healing restrictions apply. Sustain rewards setup; validate Heart mechanics and full-run balance. |
| D043 | Add three native rares while retaining temporary Ironclad supplements until at least four native rares exist. | Engine review found that Question Card requests four distinct boss rares and the duplicate-rejection loop cannot terminate with only three. Common/uncommon rewards remain native. This compatibility fallback is covered by an actual reward-generation check. |

| D044 | Void Bolt adds 1/2 stacks to each existing DoT after dealing 8/10 damage for 1 energy. | Include existing Madness, never introduce missing effects; Touch/Pain extension caps at five and Madness application checks Artifact. |
| D045 | Void Volley resolves X random Void Bolt effects as one uncommon Attack card. | Base 8 damage/1 stack per bolt, upgraded 10/2. Reselect living targets per bolt, generate Insanity per hit, and trigger Psychic Link once per card. |
| D046 | Void Torrent is a rare X-cost Attack whose base hits escalate 4,8,12,... or upgraded 5,10,15,... on one target. | Reset each play, calculate modifiers per hit, stop when the target dies, and do not redirect. X costs remain undiscounted; both new X cards support Chemical X and free plays. Torrent supplies the fourth native rare, ending D043 supplements. |

| D047 | Misery is an Uncommon relic triggered by direct Touch applications, not existing-only DoT increases. | Qualifying sources can apply Touch to an unafflicted target. Mirror the incoming application amount into Pain, respecting duration caps and normal debuff application rules. Exclude Void Bolt, Void Volley, and Mind Flay: Insanity maintenance. Design agreed; implementation pending. |
| D048 | Idol of Y'Shaarj draws one card per card that actually spends energy during Voidform. | One draw regardless of energy amount, after the card resolves. Exclude zero-cost, discounted-to-zero, and free plays; X-cost cards qualify only with positive energy consumption. Design agreed; implementation pending. |
| D049 | Add a Boss relic that doubles natural DoT ticks while consuming only one duration stack. | Two separate ticks before the enemy acts, followed by one stack reduction, even on the last stack. Each tick benefits from Mastery and can trigger Embrace normally. Psychic Link, Madness, and Halo are unchanged. Name and implementation pending. |
| D050 | Add a Boss relic that replaces starting combat copies of Curses with unupgraded Void Bolts. | Preserve the permanent deck and its Curse count for Inescapable Torment, independent of relic order. Replacements last one combat; later-added Curses and Status cards are unaffected. Neither new Boss relic replaces Voidform. Name and implementation pending. |

| D051 | Shadowy Preparation is a Common relic granting 1 starting Insanity per enemy present at combat start. | Grant once after the combat reset, respecting the normal cap and activation-card rules. Later summons do not trigger additional gain. Design agreed; implementation pending. |

| D052 | Surrender to Madness is a Rare Skill costing 3/2 energy, Exhaust, entering or extending Voidform for the rest of combat. | Bypass threshold and recovery, invalidate ready charges, and block additional copies. Existing Voidform is extended without a second entry. Ordinary Insanity behavior returns after combat. |
| D053 | Surrender applies double incoming Attack damage as a separate normal debuff. | Artifact may prevent it; Orange Pellets may cleanse it while Voidform remains. This replaces the earlier inseparable-penalty proposal. Multiply before Block, preserve HP-loss and non-Attack damage, and keep the penalty unchanged on upgrade. |

## Fifth batch implementation on October 24 2026

- **D054:** Implement the eight approved defensive/scaling cards. Preserve Mind Soothe and Mental Fortitude as unregistered drafts. Penance converts actual direct HP removal without Dexterity/Frail recalculation; Atonement uses one ordinary Block grant and a pre-hit unique-DoT snapshot.
- **D055:** Mastery can be positive or negative and modifies natural/bonus damaging DoT ticks, floored at zero. Intangible cannot increase zero damage; zero ticks do not heal Embrace. Use the Strength icon recolored purple.
- **D056:** Ire contributes max(0, 1 + Mastery), upgraded max(0, 2 + Mastery), per qualifying Attack hit. Copies stack their contributions; the bonus participates in normal damage calculation before Voidform amplification. Committal samples Voidform at play time and grants stats after resolution; existing copies can reward a later copy, but a new copy never rewards itself.
- **D057:** Body and Soul uses independent seeded rolls per enemy Attack hit and the highest granted chance per combat. Misses preserve Block. Scream uses Artifact-blockable Stun while allowing natural DoT ticks and consecutive stuns from additional copies.
- **D058:** Maintain rarity tallies in `cards.md` as Attacks/Skills/Powers, counting draft designs but not upgrades or duplicate starting copies. Refresh from headings with `scripts/update-card-tallies.py`.

## Activation proposal outcome on October 23 2026

**P001: On-demand Voidform** became D017 following the owner's agreement that Exhaust is mandatory and that the class should prepare for a chosen burst turn. D019 subsequently authorized the first-playable defaults, including Skill as the card type, meter capping, one charge, and stopping generation during Voidform. [Prototype rules](prototype-rules.md) specify the implemented lifecycle.

## Earlier concepts replaced by this baseline

The initial brainstorming suggested entering Voidform through a card and sustaining it through increasing Insanity drain. The current design instead earns a retained activation card at 10 Insanity and ends Voidform at turn end; escalating drain is not part of it.

Mind Blast as a starter and vampiric conversion into Block were brainstorming ideas. Neither is part of the current starting deck or accepted card effects.

## Future decision entries

For each new choice, record its date, the rule accepted, and any earlier choice it replaces. Keep the character design synchronized so readers do not need to reconstruct the current rules from this log.

## Sixth batch decisions on October 24 2026

- **D059:** Implement Flash Heal as the former per-copy escalating-cost heal. Rework Shadow Mend to fixed ten healing with additive 2/1 combat-long debt, ticking once per turn at player turn start. Tithe Evasion reduces only Blood Magic, Shadow Word: Death, and the combined debt event.
- **D060:** Implement Piety and Serendipity as random matching draw-pile-only draws, respecting draw hooks and hand limits. Insight penalties stack for the next opening draw. Barrier retains the hand and applies Dexterity per remaining card.
- **D061:** Resolve the two drafts: Mental Fortitude costs two and grants +2/+2 Dexterity/Mastery, upgraded +3/+3, with -2 Strength and no Exhaust. Mind Soothe snapshots DoTs, stacks per-hit reduction for this turn, and can be blocked by Artifact. This supersedes D054's draft status.
- **D062:** Legendary equipment activates automatically once before opening draw, without a Power play or combat-deck copy. Temporary copies cannot activate. Dragonwrath repeats ordinary Attack cards with the same X, including normal play hooks, without recursive procs.
- **D063:** Implement Dragonwrath at 20/25%, Xal'atath at four/five independent +2-stat rolls, and Val'anyr at 5/10 starting Block with retention until exact depletion plus an independent once-per-combat lethal rescue. Use orange cards and floating sprites rendered from the actual WoW models.

The earlier agreed boss-only chance, one-Legendary permanent-deck limit, identity acquisition history, and permanent-copy restrictions are implemented as specified in [Legendary rules](legendary-cards.md).

## Insanity scale on October 24 2026

- **D064:** Multiply all Insanity resource values by ten without changing combat pacing. The cap/activation threshold is 100; each direct hit generates 10 per unique DoT; Reaping Madness grants 10/20 per turn; Void Shield+ grants 20 first. Void Shield gives 2 Block per 10 Insanity, and active Voidform counts as 100, preserving its 20 base Block at full charge. Shadowy Preparation's design becomes 10 starting Insanity per enemy. This supersedes the numeric units in earlier decisions and test reports; DoT/Madness stacks, costs, damage bonuses, recovery timing, and charge counts are unchanged.


## Barrier reserve on October 26, 2026

- **D065:** Power Word: Barrier remains an Uncommon Skill costing 3, with a 30/40 reserve before modifiers. Barrier is part of total Block, absorbs damage before ordinary Block, and alone persists at turn start. Never restrict ordinary Block gains. The buff counter tracks the remaining reserve; remove it at zero. Dexterity/Frail apply once on casting, never on retention. Recasts refill to the modified card value without stacking or lowering a stronger reserve. Ordinary Block remains independent, including Blur/Barricade/Val'anyr/Calipers retention. The actual Block cap limits the new reserve. This supersedes the Barrier portion of D060 and the October 25 gain-lock design. See [exact rules and validation](barrier-reserve.md).


## Void Vengeance redesign on October 26, 2026

- **D066:** Replace D050's combat Curse conversion with two independent Boss-relic effects: each turn, one Curse can be played for 0 Energy and Exhausts; every actual Curse exhaust grants 2 Haste. External exhausts do not spend the free-play allowance. Preserve ordinary play restrictions and Blue Candle's separate permission and HP cost. Necronomicurse returns as usual, and repeatedly exhausting it grants Haste each time; the Blue Candle combination is deliberately allowed without exclusions or caps. The relic adds no HP cost of its own. Haste retains its standard decay. Permanent decks are unchanged.
