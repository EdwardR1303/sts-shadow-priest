"""Prepare Steam's manifest and preserve the item ID for subsequent updates."""
import json
import sys
from pathlib import Path

repo = Path(__file__).resolve().parent.parent
tracked = repo / 'workshop/config.json'
staged = repo / '.local/workshop/config.json'
settings = json.loads(tracked.read_text(encoding='utf-8-sig'))
prior = json.loads(staged.read_text(encoding='utf-8-sig')) if staged.exists() else {}
item_id = prior.get('steamPublishedID') or settings.get('steamPublishedID')
if (prior.get('steamPublishedID') and settings.get('steamPublishedID')
        and prior['steamPublishedID'] != settings['steamPublishedID']):
    raise SystemExit('Workshop IDs disagree; resolve before uploading.')
if item_id:
    settings['steamPublishedID'] = item_id
if sys.argv[1] == 'prepare':
    settings['description'] = (repo / 'workshop/description.txt').read_text(encoding='utf-8')
    staged.parent.mkdir(parents=True, exist_ok=True)
    staged.write_text(json.dumps(settings, indent=2, ensure_ascii=False) + '\n', encoding='utf-8')
elif sys.argv[1] == 'persist':
    if not item_id:
        raise SystemExit('Uploader did not return a Workshop item ID.')
    tracked.write_text(json.dumps(settings, indent=2, ensure_ascii=False) + '\n', encoding='utf-8')
    print('Workshop listing: https://steamcommunity.com/sharedfiles/filedetails/?id=' + str(item_id))
else:
    raise SystemExit('Use prepare or persist.')
