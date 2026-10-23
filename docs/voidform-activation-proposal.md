# Voidform activation card proposal

Status: under discussion, October 23, 2026. This would replace automatic activation at 10 Insanity. The accepted Voidform damage bonus, cost reduction, and end-of-turn expiry would remain the same.

## Owner proposal

When Insanity reaches 10, receive a new card that costs 0 energy, has Retain, and reads "Enter Voidform."

This lets the player earn the transformation during one turn and choose when to use it. Reaching the threshold with the final attack no longer forces the entire burst window to expire before a useful hand arrives.

## Suggested card

Working name: **Enter Voidform**.

| Property | Proposed value |
| --- | --- |
| Energy cost | 0, from the owner's proposal |
| Retain | Yes, from the owner's proposal |
| Effect | Enter Voidform, from the owner's proposal |
| Card type | Skill, assistant recommendation |
| Exhaust | Yes, assistant recommendation |
| Acquisition | Generated at 10 Insanity; not part of the permanent starting deck |

Suggested text with the recommended Exhaust addition:

> Retain. Enter Voidform. Exhaust.

Exhaust would prevent the used card from entering the ordinary draw/discard cycle. It does not by itself settle copying or recovery behavior, so activation should also have an explicit charge rule.

## Recommended charge lifecycle

These are assistant recommendations for discussion, not accepted rules:

1. Build Insanity from 0 to 10 through the agreed direct-damage mechanic.
2. At 10, mark one Voidform activation ready and add the activation card to hand when space allows.
3. Cap Insanity at 10 while ready. Further attacks do not create more activation cards or bank another charge.
4. Retain the card until the player chooses to play it. Playing it consumes the ready activation, enters Voidform, and exhausts the card.
5. Do not generate Insanity or new activation cards while Voidform is active.
6. At turn end, Voidform ends and Insanity resets to zero, as already confirmed. The next charge can be built afterward.
7. Combat end clears the meter, generated card, and any pending activation.

This model separates building the resource, holding a ready activation, and using the burst. Requiring a ready activation would prevent a copied or recovered card from granting a free additional Voidform without earning a new charge.

## Tradeoffs and remaining details

Choosing the activation turn makes the same bonus more powerful because the player can wait for a hand with several attacks. A retained card also occupies hand space, so waiting has a cost. Keep the initial threshold of 10 for testing and observe how often waiting improves the burst.

The main implementation details to resolve are full-hand delivery, discard or loss of an unused activation card, and copying or recovering the card. An earned charge should not disappear merely because the hand was full when the threshold was reached.

The intended feel is a combat ability that becomes ready through attacks and waits for the player's input. No claim is made that this mechanic is unprecedented among existing mods.
