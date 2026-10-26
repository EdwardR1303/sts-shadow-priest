# Legendary cards

Status: implemented for Dragonwrath, Xal'atath, and Val'anyr. See [card designs](../cards.md) and [sixth-batch rules](card-batch-06.md).

## Acquisition

- Only Act 1 and Act 2 boss card rewards can offer Legendary cards.
- Roll once per eligible reward screen, with probability `max(5, Ascension level)%`: 5% at A0 through A5, rising to 20% at A20.
- On success, replace one Rare option with a randomly selected eligible Legendary. Keep the reward's normal number of options and normal choice limit. Extra card options do not produce extra Legendary rolls.
- If no eligible Legendary remains, leave the ordinary reward unchanged.
- Legendary cards do not enter ordinary combat rewards, shops, or random transformation pools.
- While the permanent deck contains any Legendary, no Legendary is eligible for a reward. Keep ordinary Rare options instead.

## Unique acquisition

- The permanent deck may contain only one Legendary card total, regardless of identity. Owning one blocks acquisition of every other Legendary, as well as additional copies of itself.
- Each named Legendary can still be acquired only once per run. Removing or transforming the owned Legendary frees the deck's single Legendary slot, but previously acquired identities remain excluded for that run. This is a one-at-a-time deck limit, not a one-acquisition-total run limit.
- Once added to the permanent deck, that Legendary is excluded from all later rewards for the run. Track acquisition history, so removing or transforming it does not restore eligibility.
- Upgraded and unupgraded versions share the same identity for uniqueness.
- Dolly's Mirror and other permanent-deck duplication effects, including the Duplicator event, cannot select Legendary cards. Do not let the player select one and then silently waste the effect.
- Enforce both the one-Legendary deck limit and identity history at permanent acquisition as well as in selection screens, so another acquisition or permanent-copy source cannot bypass them. Recheck eligibility when obtaining a previously generated reward.
- Persist acquisition history across save/load. Merely seeing or skipping a Legendary does not mark it acquired.

## Combat-copy boundary

The owned Legendary equips automatically once per combat before the opening hand. Its effects do not count as playing a Power, spend no energy, and leave no drawable copy in the combat deck. It remains visible in the permanent deck and can be upgraded. Temporary copies are unplayable; forced plays cannot equip another weapon.

Dragonwrath rolls once per ordinary Attack played, repeats its full effects for free with the original X, and cannot recursively trigger from purge-on-use repeats. Repeat plays still trigger ordinary card-play hooks. If the original target has died, the repeat selects another living enemy if possible.

Val'anyr's Block retention breaks permanently for the combat at exact depletion. Its independent rescue leaves one HP after a lethal damage event, including HP loss, and supplies no protection from the next hit. Other sources of Block retention are preserved.

## Presentation and validation

- Use a distinctive orange Legendary presentation and a clear explanation: only one Legendary in your deck, and permanent duplication is prohibited.
- Verify the boss reward probability, one-roll rule, empty eligible pool, all-Legendary exclusion while one is owned, acquisition-time enforcement, removal reopening the slot without clearing identity history, upgraded identity, save/load persistence, and permanent duplication restrictions.
- Design intent: make the chosen Legendary a defining run reward and support stronger individual effects through mutual exclusivity. Rarity and exclusivity allow power headroom but do not establish balance by themselves; validate actual cards during playtesting.
- Runtime uses a custom rarity, orange banner/frame, item portraits, and transparent renders of the actual WoW weapon models floating beside the character. These are animated sprites, not live 3D models.
- Frozen Egg upgrades a Legendary reward under the ordinary Power reward rule. The displayed short names fit the card frame; full weapon names remain in the design catalog.
- Save data persists acquired identities through BaseMod CustomSavable. Game checks exercise its JSON roundtrip; complete quit/resume progression remains a manual playtest item.

## Actual combat screenshots

The focused test now starts combat with each upgraded Legendary in the permanent deck, verifies both its effect and equipped identity, draws the opening hand, and pauses for a visible preview. Screenshots are captured after normal combat rendering, rather than the separate portrait-preview framebuffer. The unusual health and deck values are controlled test fixtures.

- [Dragonwrath in combat](images/legendary/Dragonwrath.png)
- [Xal'atath in combat](images/legendary/Xalatath.png)
- [Val'anyr in combat](images/legendary/Valanyr.png)

Earlier mechanic-isolation tests deliberately installed Powers or called the individual effect directly, bypassing the equipped-weapon field. They were insufficient to demonstrate live sprite visibility; the normal combat-start checks now cover all three weapons.


## Painted card portraits

The inventory-icon card portraits were replaced with full weapon illustrations in the [second presentation pass](art-pass-02.md). The actual model-render sprites beside the character remain separate from these painted card scenes.

## Xal'atath stat rolls

Each of the four rolls (five upgraded) independently grants 2 Strength, Dexterity, Mastery, or Haste with equal 25% probabilities. All gains last for combat. An all-Haste result grants 8 Haste, or 10 upgraded. Power Infusion adds temporary Haste to the same visible total; turn end removes only the temporary portion.

The Haste addition passed 238 in-game checks across the equipment and keyword suites, including all-Haste rolls, both temporary/permanent application orders, turn-end expiry, and zero-energy X casts. The build also passes 12 unit tests.
