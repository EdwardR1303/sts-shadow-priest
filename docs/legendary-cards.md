# Legendary cards

Status: acquisition and permanent-deck uniqueness rules agreed; not implemented. Individual Legendary cards remain to be designed.

## Acquisition

- Only Act 1 and Act 2 boss card rewards can offer Legendary cards.
- Roll once per eligible reward screen, with probability `max(5, Ascension level)%`: 5% at A0 through A5, rising to 20% at A20.
- On success, replace one Rare option with a randomly selected eligible Legendary. Keep the reward's normal number of options and normal choice limit. Extra card options do not produce extra Legendary rolls.
- If no eligible Legendary remains, leave the ordinary reward unchanged.
- Legendary cards do not enter ordinary combat rewards, shops, or random transformation pools.

## Unique acquisition

- Each named Legendary can be acquired only once per run. Different Legendary cards may coexist in the same deck.
- Once added to the permanent deck, that Legendary is excluded from all later rewards for the run. Track acquisition history, so removing or transforming it does not restore eligibility.
- Upgraded and unupgraded versions share the same identity for uniqueness.
- Dolly's Mirror and other permanent-deck duplication effects, including the Duplicator event, cannot select Legendary cards. Do not let the player select one and then silently waste the effect.
- Enforce the restriction at permanent acquisition as well as in selection screens, so another permanent-copy source cannot bypass it.
- Persist acquisition history across save/load. Merely seeing or skipping a Legendary does not mark it acquired.

## Combat-copy boundary

Not yet decided: whether effects that create temporary combat copies, or replay a card without adding a permanent copy, should work on Legendaries. The agreed permanent-deck restriction does not settle this. Resolve before implementing those interactions; ordinary combat deck initialization and card previews must remain functional.

## Presentation and validation

- Use a distinctive orange Legendary presentation and a clear explanation of the uniqueness restriction.
- Verify the boss reward probability, one-roll rule, empty eligible pool, acquired-card exclusions, upgraded identity, save/load persistence, and permanent duplication restrictions.
- This is a design specification, not a claim that the runtime already supports Legendary rarity.
