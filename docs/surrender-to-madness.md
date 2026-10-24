# Surrender to Madness

An implemented Rare Skill that turns a temporary burst into a commitment for the rest of combat. The class now supplies 24 reward cards: ten Commons, nine Uncommons, and five Rares.

| Property | Base | Upgrade |
| --- | --- | --- |
| Cost | 3 energy | 2 energy |
| Type / rarity | Skill / Rare | Unchanged |
| Benefit | Enter Voidform for the rest of combat | Unchanged |
| Penalty | Take double damage from enemy Attacks | Unchanged |
| Exhaust | Yes | Yes |

## Voidform lifecycle

Surrender enters immediately when its queued effect resolves, without requiring ten Insanity or a ready activation card. It can override the ordinary recovery restriction. If already in ordinary Voidform, it extends that same Power; the damage bonus and discount do not stack and this is not another entry into Voidform.

The existing benefits remain: Attack cards cost one less, with a floor of zero, and deal 50% more damage. X costs are unchanged. Because Surrender is a Skill, ordinary Voidform does not discount it. Its permanent state and its damage penalty both last only for the current combat.

Surrender clears stored Insanity, invalidates any ready activation charge, and prevents further Insanity gain or activation-card delivery while the form remains active. It does not end at turn boundaries or enter the normal recovery phase. Additional Surrender copies are unplayable after the first resolves, even if the penalty is subsequently removed. Queued duplicate effects cannot apply the bargain twice.

Combat cleanup clears this state and restores the ordinary charge/expiry/recovery lifecycle. The card also supplies its own lasting Voidform benefit and cost discount without the starter relic, supporting starter swaps and externally acquired copies. With the starter equipped, only its existing cost provider supplies the discount.

## Preventing or removing the penalty

The damage penalty is a separate, non-turn-based debuff, independent of the Voidform buff and permanent-state flag.

- Artifact already present can block its initial application, consuming one charge.
- Artifact acquired afterward does not remove the existing penalty.
- Orange Pellets can cleanse the penalty while preserving lasting Voidform.
- Preventing or cleansing the penalty does not permit another Surrender play.

This deliberately follows normal debuff prevention/cleansing rules, replacing the earlier proposal to make the drawback inseparable from the benefit.

## Damage and defenses

The debuff doubles normal incoming Attack damage before Block through the engine's damage calculation, so enemy intent previews include it. For example, a ten-damage Attack becomes twenty, then seven Block reduces the HP loss to thirteen. Each hit of a multihit Attack is modified separately. Other ordinary modifiers such as Vulnerable combine normally.

HP-loss and non-Attack damage are unchanged. Intangible still caps Attack damage, Fade reduces the resulting HP loss, and Desperate Prayer can heal a doubled hit afterward if the player survives. Surrender does not change DoT damage or add a new ticking effect.

## Relic design compatibility

The non-starter relics in `relics.md` are still designs, not implemented mechanics. When Idol of Yogg-Saron is implemented, Surrender from an inactive state should count as one entry and grant its energy; extending an already active Voidform must not grant another entry reward. This interaction is not claimed as tested in the current build.

Evaluate exposed and cleansed builds separately during full runs. High-impact combinations to assess include upfront entry energy, sustained Psychic Link/Embrace support, and playing Surrender only when the current hand can end combat immediately. Correctness tests do not establish A20 Heart balance.

## Artwork and validation

The card and penalty Power use the archived WoW Surrender to Madness icon, `achievement_boss_generalvezax_01`, verified in the [ability's archived infobox](https://warcraft.wiki.gg/wiki/Surrender_to_Madness). Current tooltip endpoints for the removed spell returned no entity, so the historical reference was used. The unchanged source image is displayed through the shared card frame and credited in [Warcraft artwork](../licenses/Warcraft-artwork.txt).

See [playtest results](playtest-results.md) for completed unit and actual-game checks, including Artifact, real Orange Pellets activation, damage calculations, and turn transitions.
