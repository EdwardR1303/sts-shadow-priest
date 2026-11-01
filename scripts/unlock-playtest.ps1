# Unlock gameplay progression only in this repository's isolated test profile.
# Close the local playtest first. Never reads or writes Steam's preferences.
$ErrorActionPreference = 'Stop'
$repo = Split-Path $PSScriptRoot -Parent
$prefs = Join-Path $repo '.playtest/preferences'
if (-not (Test-Path -LiteralPath $prefs)) { throw 'Launch the local playtest once before unlocking it.' }
$running = Get-CimInstance Win32_Process -Filter "Name = 'java.exe'" |
    Where-Object { $_.CommandLine -like '*-Dshadowpriest.playtest=true*' -and $_.CommandLine -notlike '*-Dshadowpriest.smoketest=true*' }
if ($running) { throw 'Close the local playtest before editing its progression.' }
$slot = '0'
if (Test-Path "$prefs/STSSaveSlots") { $slot = (Get-Content "$prefs/STSSaveSlots" -Raw | ConvertFrom-Json).DEFAULT_SLOT }
if ($slot -notin @('0','1','2')) { throw "Unexpected save slot: $slot" }
$prefix = if ($slot -eq '0') { '' } else { "$($slot)_" }
$backup = Join-Path $repo ".local/progression-backups/$(Get-Date -Format 'yyyyMMdd-HHmmss-fffffff')"
New-Item -ItemType Directory -Path $backup -Force | Out-Null
Copy-Item -LiteralPath $prefs -Destination $backup -Recurse
function Update-Prefs([string]$name, [hashtable]$changes) {
    $path = Join-Path $prefs "$prefix$name"
    $data = @{}
    if (Test-Path -LiteralPath $path) {
        (Get-Content -LiteralPath $path -Raw | ConvertFrom-Json).PSObject.Properties |
            ForEach-Object { $data[$_.Name] = $_.Value }
    }
    foreach ($key in $changes.Keys) { $data[$key] = [string]$changes[$key] }
    [IO.File]::WriteAllText($path, ($data | ConvertTo-Json -Depth 20), (New-Object Text.UTF8Encoding $false))
}
# Settings.setFinalActAvailability reads these exact player flags.
Update-Prefs 'STSPlayer' @{ IRONCLAD_WIN='true'; THE_SILENT_WIN='true'; DEFECT_WIN='true' }
# UnlockTracker.refresh: 36 cards, 24 relics, three additional base characters.
$cards = @('Havoc','Sentinel','Exhume','Wild Strike','Evolve','Immolate','Heavy Blade','Spot Weakness','Limit Break',
    'Concentrate','Setup','Grand Finale','Cloak And Dagger','Accuracy','Storm of Steel','Bane','Catalyst','Corpse Explosion',
    'Rebound','Undo','Echo Form','Turbo','Sunder','Meteor Strike','Hyperbeam','Recycle','Core Surge',
    'Prostrate','Blasphemy','Devotion','ForeignInfluence','Alpha','MentalFortress','SpiritShield','Wish','Wireheading')
$relics = @('Omamori','Prayer Wheel','Shovel','Art of War','The Courier',"Pandora's Box",'Blue Candle','Dead Branch','Singing Bowl',
    'Du-Vu Doll','Smiling Mask','Tiny Chest','Cables','DataDisk','Emotion Chip','Runic Capacitor','Turnip','Symbiotic Virus',
    'Akabeko','Yang','CeramicFish','StrikeDummy','TeardropLocket','CloakClasp')
$unlocks = @{}; $seenCards = @{}; $seenRelics = @{}
foreach ($id in ($cards + $relics + @('The Silent','Defect','Watcher'))) { $unlocks[$id] = '2' }
foreach ($id in $cards) { $seenCards[$id] = '1' }
foreach ($id in $relics) { $seenRelics[$id] = '1' }
Update-Prefs 'STSUnlocks' $unlocks
Update-Prefs 'STSSeenCards' $seenCards
Update-Prefs 'STSSeenRelics' $seenRelics
$levels = @{}
foreach ($character in @('IRONCLAD','THE_SILENT','DEFECT','WATCHER','SHADOW_PRIEST')) { $levels["$($character)UnlockLevel"] = '5' }
Update-Prefs 'STSUnlockProgress' $levels
foreach ($stat in @('STSDataVagabond','STSDataTheSilent','STSDataDefect','STSDataWatcher','SHADOW_PRIEST')) {
    $path = Join-Path $prefs "$prefix$stat"
    $wins = 0
    if (Test-Path -LiteralPath $path) { $wins = [int](Get-Content -LiteralPath $path -Raw | ConvertFrom-Json).WIN_COUNT }
    Update-Prefs $stat @{ ASCENSION_LEVEL='20'; WIN_COUNT=[Math]::Max(1,$wins) }
}
# Local beta-art access and Custom Mode; no Steam achievement API is called.
Update-Prefs 'STSAchievements' @{ RUBY='true'; EMERALD='true'; SAPPHIRE='true'; AMETHYST='true';
    RUBY_PLUS='true'; EMERALD_PLUS='true'; SAPPHIRE_PLUS='true'; AMETHYST_PLUS='true'; ASCEND_0='true' }
Write-Output "Unlocked Heart route, all card/relic unlock bundles, characters, beta art, and A20 in local slot $slot."
Write-Output 'Collect the three keys normally during a new run. Existing saves are preserved.'
Write-Output "Preference backup: $backup/preferences"
