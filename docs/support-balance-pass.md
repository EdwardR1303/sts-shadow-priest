# Support balance pass — October 25, 2026

## Haste and Power Infusion

All Haste loses half its stacks at the end of each player turn, rounding the loss up. Equivalently, remaining Haste is rounded down: 5 → 2 → 1 → 0, or 2 → 1 → 0. Combine stacks from every source before decay. Plays do not consume Haste; effective X, zero-energy use, Chemical X, and Protected Channel retain their existing rules. Chemical X itself does not decay. The Haste icon remains yellow Dexterity.

Power Infusion still grants 2 Haste, costs 1 energy (0 upgraded), and Exhausts. Its text now says “Gain 2 Haste. Exhaust.” The keyword and buff tooltip explain decay. It does not gain Retain.

## Xal'atath

Roll only at combat start, four times (five upgraded), with +2 Strength, Dexterity, or Mastery per roll. Each stat has equal probability; repeated outcomes are allowed. Bonuses last for combat. Haste is excluded. There is no turn-by-turn reroll or accumulation.

## Val'anyr

The original combined power becomes two visible buffs:

- **Barricade (Val'anyr):** uses the base-game Barricade icon and retains Block until exact depletion. The buff then disappears permanently for that combat. Independent Barricade and Blur remain.
- **Guardian Angel:** uses the [WoW Guardian Spirit icon](https://www.wowhead.com/spell=47788/guardian-spirit). The next lethal damage leaves 1 HP, including HP loss. The buff is consumed immediately and its icon is removed. Subsequent hits receive no protection. Damage reduction resolves before the rescue check.

Starting Block remains 5 (10 upgraded). Breaking the ward does not spend Guardian Angel, and using Guardian Angel does not inherently remove the ward. Weapon activation still happens before the opening hand without counting as a played Power.

## Verification

The focused `-SupportOnly` game mode covers Haste decay through actual turn boundaries, zero-energy X cards, Chemical X/Protected Channel, all three Xal'atath outcomes, upgraded stat totals and persistence, separate Val'anyr buffs, independent retention, and rescue after damage reduction. Full-run balance remains a human playtesting question.

Result: build passed with 14 unit tests; the final isolated SupportOnly run passed all 31 checks. Reviewed the updated Power Infusion/Xal'atath card renders and the combat capture showing separate Barricade and Guardian Angel icons. The broad simulation suite was not run.
