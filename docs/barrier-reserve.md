# Power Word: Barrier reserve — October 26, 2026

Uncommon Skill, **3 energy**, **30 Barrier / 40 upgraded**. The card reads: “Refresh your Barrier to 30.” The Barrier keyword explains its persistence, consumption order, and refresh behavior. Existing WoW ability artwork is unchanged.

Barrier is a tracked portion of the normal Block total. Its buff shows how much of that persistent reserve remains. Incoming damage spends Barrier **before** ordinary Block. Ordinary Block gains always work and never refill the reserve. At the beginning of the next turn, ordinary Block expires normally while remaining Barrier stays; this is retention, not another Block grant.

| Event | Barrier | Ordinary Block | Displayed total |
|---|---:|---:|---:|
| Cast Barrier | 30 | 0 | 30 |
| Gain 8 ordinary Block | 30 | 8 | 38 |
| Take a 12-damage hit | 18 | 8 | 26 |
| Begin the next turn | 18 | 0 | 18 |

The reserve is intended to be spent to buy setup/damage turns. It is not a buff to protect, and a nearly depleted reserve never restricts your defenses.

## Exact rules

- Dexterity, Frail, and ordinary card Block calculations apply once when casting. Retention does not apply them again and does not trigger effects that respond to gaining Block.
- Recasts refresh to the modified card value, preserving ordinary Block. With 12 reserve and 8 ordinary Block, a base recast adds 18 and leaves 30 reserve plus 8 ordinary Block. A weaker recast never reduces a stronger remaining reserve. Recasts are playable; they do not stack full reserves.
- The reserve can increase only by Block actually added by the cast. At the 999 Block cap, excess grant is lost; ordinary Block is never converted to persistent Barrier. Details records only the actual increase, including on refresh.
- At zero reserve, remove the buff even if ordinary Block remains. Pending removal checks the power's identity and amount so it cannot erase a refreshed reserve.
- Block-ignoring HP loss does not consume Barrier. Explicit Block removal does consume it; removed Block cannot return on the following turn.
- Independent Blur, Barricade, and Val'anyr retain ordinary Block using their own rules. Spending Barrier does not break Val'anyr while other Block remains. Calipers removes up to 15 ordinary Block at turn start, never the protected reserve.
- Barrier no longer impersonates Barricade, retains the hand, disables recasts, or prevents any Block gain.

The implementation observes the engine's Block-loss method for reserve spending and distinguishes the specific turn-start expiration calls in `GameActionManager`. It retains Block in place; it does not remove everything and re-grant the reserve. This preserves Block-gain hooks and accurate Details totals.

## Focused validation

```powershell
./scripts/build.ps1
./scripts/playtest.ps1 -SkipBuild -BarrierOnly -Width 1920 -Height 1080 -Windowed
```

This mode uses an isolated controlled encounter, not a full-run simulation. It covers reserve-first damage, partial/exact depletion, HP loss, nearly depleted reserves with Shield/Atonement, end-turn Shadow Weaving, real turn boundaries, non-stacking recasts, upgraded/weaker recasts, Dexterity/Frail, the Block cap, zero-value casts, explicit removal, independent retention, Calipers, and Details totals. It exports card and combat renders to `.smoketest/card-review/`.

The older Defensive Rework suite now delegates its Barrier section to this focused suite. Details and Setup Balance assertions were updated to permit ordinary Block gains. Other historical balance assertions in older suites are not acceptance checks for this change.


Validation completed October 26: **22 unit tests**, **53 focused in-game checks**, and card-catalog schema validation passed. Base/upgraded card renders and the combat view were inspected; the combat capture shows 26 total Block with an 18-point Barrier counter. A Block-cap fixture was corrected to grant its initial Block through the engine instead of assigning the field, keeping StSLib's internal Block accounting consistent. No full-run simulation was run.
