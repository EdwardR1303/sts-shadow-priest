# 0.1.3 local release candidate

This candidate includes the local observation fixes, expanded Details metrics, low-pick card revisions, and the five accepted Curse/setup revisions. It is not yet published to Steam Workshop. The changelog and Workshop change note are prepared; publication follows local run feedback.

## Final card revisions

- Blood Magic: 15/20 damage, 3/5 Touch and Pain, one combat-only Pain Curse in discard.
- Mind Trauma: 6/9 damage and Madness, with the existing additional 10 Insanity.
- Purify Disease: exhaust one Curse from hand if available; visibly choose when multiple are present. No Curse is required. Both versions Exhaust; the upgrade also grants Artifact.
- Mental Fortitude: +2 Dexterity/+2 Mastery and one Doubt in hand, replacing the Strength loss. Costs 2/1; repeatable.
- Malady of the Mind: costs 0, doubles/triples Madness, and adds Mind Decay to discard even when multiplication has no effect. Exhaust.

These supersede the Mental Fortitude and Purify Disease entries in the earlier low-pick implementation report. Malady's earlier decision to leave it unchanged is also superseded. Dark Void's flagged draft is not included in this candidate.

## Verification

Use `scripts/playtest.ps1 -SkipBuild -LowPickOnly -Windowed -Width 1600 -Height 900` after building. The focused fixture now includes the five revisions, Curse selection, Artifact interactions, Void Vengeance, Dark Reinforcement, hand overflow, generated Curse identity, and permanent-deck isolation. This is not a full-run simulation.

Completed: 35 unit tests and 94 focused real-engine assertions passed. The card catalog validates against its schema. Reviewed the game-rendered upgraded cards for text fit. No full-run simulation was run. Results are preserved locally in `.local/release-0.1.3-focused-results.txt`.

Release gate: play a local run with the combined changes and review balance before uploading. The normal desktop playtest shortcut builds and launches the local candidate in the isolated playtest profile. Workshop subscribers remain on 0.1.2 until publication.
