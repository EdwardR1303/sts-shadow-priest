# Playtest followup ? October 27, 2026

## Changes

- Vampiric Embrace costs 2 Energy in both versions; healing remains 1/2 per Touch tick that removes enemy HP.
- Void Entropy reads "Enter Voidform. Draw 2/3 cards." Recharge prevents Insanity gain, not direct entry. Existing entry/recovery behavior is unchanged.
- Singularity preserves its formula and shows a parenthetical bonus-damage value while targeting. The initial hit still has its own damage number. The preview includes current stacks, Mastery, relevant Attack modifiers, and Searing Nightmare stacks from the initial hit when not blocked by Artifact. It excludes Strength from the bonus as before. Changing/clearing targets and resetting the card clear stale values.
- Void Torrent shows total damage and hit count in parentheses, with effective X from current Energy, Haste, and Chemical X. Each escalating hit is calculated and rounded separately. Preview and resolution share the same damage helper. Void Shield shows total Block while preserving the base Block term in its formula.
- Mental Decay creates **Mind Decay**, a custom generated-only Curse: Unplayable, Retain, take 2 blockable damage at end of turn. It has its own ID, name, Curse frame, and original artwork, and appears in Mental Decay's card preview. It is not registered in random reward/event Curse pools and does not change the 75 collectible-card total.

## Retention fix

The old RetainedDecay subclass inherited vanilla Decay's special end-turn autoplay. Its cleanup could discard the card before normal retention. The earlier test used an effectively empty draw pile and could pass by immediately redrawing the discarded Curse. That test now has a populated draw pile.

Mind Decay schedules a normal THORNS damage action directly from its end-turn hook. It does not play, discard, exhaust, or redraw itself. Block absorbs the damage; ordinary damage-taken hooks apply to actual HP loss. The action is attributed to Mind Decay in Details. Blue Candle/Void Vengeance can deliberately play and exhaust the Curse normally, and external exhaust effects still recognize it as a Curse. Copies and full-hand fallback preserve Retain.

## Validation

- Incremental Maven `verify`: **28 unit tests passed**.
- `scripts/playtest.ps1 -SkipBuild -BalanceFollowupOnly -Windowed -Width 1920 -Height 1080`: **51 focused real-game checks passed** in the isolated smoke-test profile.
- Covers Embrace payment/healing, wording, custom Curse identity/copying, consecutive retained turns with enough draw cards to prevent reshuffling, blockable damage, no fake play/autoplay flags, damage-taken Insanity, Blue Candle, Void Vengeance, external exhaust, and full-hand discard/redraw.
- Preview checks cover Singularity's two-hit damage, Mastery/Strength/Weak/Vulnerable/Voidform, first-hit Searing Nightmare, upgrades, no stacks, reset, and negative Mastery. Torrent checks cover zero Energy with Haste/Chemical X, rounding per hit, actual damage, zero hits, and upgrades. Shield's actual Block matches its displayed total.
- Nine game-rendered card images were visually reviewed under `.smoketest/card-review/followup-*.png`.

No broad run simulation was performed. Previews are damage before Block and reflect current combat state; they do not simulate every reactive enemy/relic effect or future modifier change between hits. A lethal first Singularity hit ends the attack before its bonus, as before.

## Artwork

Mind Decay's portrait was created with the built-in imagegen tool. Final asset: `src/main/resources/shadowpriest/images/cards/icons/mind_decay.png`. The exact prompt and tool are recorded in [mind-decay-art-prompt.json](mind-decay-art-prompt.json).
