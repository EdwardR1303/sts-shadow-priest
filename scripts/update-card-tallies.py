"""Refresh design tallies from cards.md headings, including draft cards."""
import argparse
import re
from pathlib import Path

RARITIES = ("Basic", "Common", "Uncommon", "Rare", "Special", "Curse")
TYPES = ("Attacks", "Skills", "Powers")
START = "<!-- card-tallies:start -->"
END = "<!-- card-tallies:end -->"


def refresh(text):
    counts = {r: [0, 0, 0] for r in RARITIES}
    headings, names = {}, set()
    rarity = kind = None
    fenced = False
    source_lines = text.splitlines(keepends=True)
    for index, line in enumerate(source_lines):
        if line.startswith("```"):
            fenced = not fenced
        if fenced:
            continue
        if line.startswith("## "):
            heading = re.sub(r" \(\d+/\d+/\d+\)$", "", line[3:].strip())
            rarity = heading if heading in counts else None
            kind = None
            if rarity:
                headings[index] = rarity
        elif line.startswith("### "):
            heading = line[4:].strip()
            kind = TYPES.index(heading) if heading in TYPES else None
        elif line.startswith("#### ") and rarity:
            if kind is None:
                raise ValueError("Card has no Attack/Skill/Power category: " + line)
            name = line[5:].strip().rstrip("?").strip().casefold()
            if name in names:
                raise ValueError("Duplicate card heading: " + line)
            names.add(name)
            counts[rarity][kind] += 1
    if not headings:
        raise ValueError("No rarity heading found")
    for index, rarity in headings.items():
        ending = "\n" if source_lines[index].endswith("\n") else ""
        source_lines[index] = "## " + rarity + " (" + "/".join(map(str, counts[rarity])) + ")" + ending
    text = "".join(source_lines)
    block = "\n".join([START, "X/Y/Z = **Attacks/Skills/Powers**. Counts beside each rarity heading include drafts and unreviewed designs in this file; upgrades and repeated starting copies do not count separately.", END])
    if START in text:
        if END not in text:
            raise ValueError("Missing tally end marker")
        return re.sub(re.escape(START) + r".*?" + re.escape(END), lambda _: block, text, count=1, flags=re.S)
    first = re.search(r"^## ", text, re.M)
    if first is None:
        raise ValueError("No rarity heading found")
    return text[:first.start()] + block + "\n\n" + text[first.start():]


if __name__ == "__main__":
    parser = argparse.ArgumentParser(description=__doc__)
    parser.add_argument("path", nargs="?", type=Path, default=Path(__file__).resolve().parents[1] / "cards.md")
    parser.add_argument("--check", action="store_true", help="Fail if tallies are stale; do not edit")
    args = parser.parse_args()
    before = args.path.read_text(encoding="utf-8")
    after = refresh(before)
    if args.check:
        if before != after:
            raise SystemExit("Card tallies are stale; run scripts/update-card-tallies.py")
        print("Card tallies match the current roster.")
    elif before != after:
        args.path.write_text(after, encoding="utf-8")
        print("Updated card tallies in " + str(args.path))
    else:
        print("Card tallies are already current.")
