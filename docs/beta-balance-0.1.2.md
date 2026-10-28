# Beta balance pass — 0.1.2

Accepted October 28, 2026. The card pool remains 75 collectible cards: four Basic, 28 Common, 26 Uncommon, 14 Rare, and three Legendary. No rarity changes.

## Haste

Haste adds to an explicit repetition count once per card resolution. Existing X-cost effects continue using effective X, with no second addition when that X controls repetitions or Protected Channel. Chemical X remains specific to X-cost cards.

Fixed-repeat Shadow Priest cards: Mind Flay, Penance (damage and healing), and Shadow Word: Death+ (including backlash per hit). Base-game compatibility: Dagger Spray, Sword Boomerang, Pummel, Twin Strike, Riddle With Holes, Bouncing Flask, Rip and Tear, Flying Sleeves, Ragnarok, Tantrum, Eviscerate, Glass Knife, Dualcast, Omniscience, and Expunger. Their secondary effects still happen once. Existing base-game X-card support is retained. Other mods' bespoke repetition implementations need explicit integration.

Do not infer repetitions from queued actions. For-each calculations, ordinary card-draw amounts, Halo duration, recurring triggers, and Dragonwrath replay counts do not gain Haste. Heavy Blade's Strength multiplier is not a repeated effect. Display modified repetition counts and effective X through green dynamic variables.

Power Infusion prevents the next two end-of-turn Haste decays. Recasting adds Haste and refreshes the protection to two; it never extends the counter beyond two. All Haste sources share the protected pool. Decay resumes at the third end-of-turn boundary.

## Damage and defense

- Singularity consumes existing DoTs and makes one hit for `stacks × max(0, 3/5 + Mastery)`. No initial hit, Strength scaling, or pre-consumption Searing Nightmare application. No stacks means no hit. Other normal Attack modifiers apply. The resulting hit can reapply Madness.
- Catharsis accumulates 3 damage per actual HP lost, or 5 while that individual copy is in hand. Healing leaves accumulated damage intact. Exhaust remains; the upgrade grants Retain. Equivalent temporary copies inherit their current counter, but no value is stored in the permanent deck.
- Tithe Evasion stacks. During the player phase, including start/end effects, it reduces remaining HP loss after ordinary Block consumption. Enemy-phase damage is unaffected. Fully prevented HP loss grants neither Insanity nor Catharsis damage.
- Void Volley remains Uncommon. Each copy in hand when Voidform is entered plays once for free after entry effects, including Yogg's energy. It uses then-current energy plus effective-X bonuses, preserves energy, counts as an Attack play, and discards normally. Extending existing Voidform does not trigger it.
- Void Bolt and Void Volley: 10/13 damage. Encroaching Shadows: 6/9 Block. Halo: 4/6 per pulse. Searing Nightmare: 2 Madness per hit.
- Protected Channel ignores Dexterity only for its triggered grants. Other Block modifiers still apply; the initial six Block benefits from Dexterity.
- Mass Dispel+ remains two energy and also removes player debuffs. Petrifying Scream+ remains two energy and stuns all enemies; Exhaust remains.
- Surrender's double-damage penalty is a buff for combat, unaffected by Artifact and debuff cleansing. Permanent Voidform and its price remain together.

## Previews

Calculated parenthetical outputs cover Singularity, Catharsis, Void Shield, Void Torrent, Mind Blast, Shadowy Insight, Mind Soothe, Malady of the Mind, Madness Overwhelming, Void Tendrils, and Shadow Weaving. X cards show current effective X. Target-dependent previews update on hover; prospective end-turn effects are labelled as based on the current board. Attack previews are damage calculations, not guarantees of HP loss through every prevention effect.

## Verification

Use incremental Maven `verify` and `scripts/playtest.ps1 -SkipBuild -BetaBalanceOnly` for focused integration checks. This suite exercises the real patched game and is separate from live Steam saves. Full-run balance simulations are intentionally omitted; sustained balance still needs player runs.

Historical batch documents and their scenario-specific test expectations describe earlier designs. This document, cards.md, keywords.md, the catalog, and the 0.1.2 focused suite define this revision.

Verified October 28, 2026: 57 focused real-engine assertions passed, including base-game Haste compatibility, green repetition counts, post-Block Tithe prevention, independent Catharsis counters, two skipped decay boundaries, Surrender/Artifact/cleanse interactions, free Volley and Yogg entry energy, and upgraded Stun. Rendered Volley+, Catharsis+, Mind Flay and Dagger Spray were visually reviewed.
