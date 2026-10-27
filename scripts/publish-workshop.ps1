param([switch]$Upload)
$ErrorActionPreference = 'Stop'
$repo = Split-Path $PSScriptRoot -Parent
$local = Get-Content "$repo/.local/config.json" -Raw | ConvertFrom-Json
$workspace = "$repo/.local/workshop"
$oldJava = $env:JAVA_HOME
try {
    $env:JAVA_HOME = $local.javaHome
    Push-Location $repo
    try {
        & "$($local.mavenHome)/bin/mvn.cmd" --batch-mode --no-transfer-progress verify
        if ($LASTEXITCODE -ne 0) { throw 'Release build failed.' }
    } finally { Pop-Location }
} finally { $env:JAVA_HOME = $oldJava }
New-Item -ItemType Directory -Force -Path "$workspace/content" | Out-Null
$unexpected = @(Get-ChildItem -LiteralPath "$workspace/content" -Force | Where-Object { $_.Name -ne 'shadowpriest.jar' -or $_.PSIsContainer })
if ($unexpected.Count) { throw 'Unexpected files in Workshop content; inspect before publishing.' }
Copy-Item -LiteralPath "$repo/target/shadowpriest.jar" -Destination "$workspace/content/shadowpriest.jar" -Force
Copy-Item -LiteralPath "$repo/workshop/image.jpg" -Destination "$workspace/image.jpg" -Force
& python "$PSScriptRoot/workshop-manifest.py" prepare
if ($LASTEXITCODE -ne 0) { throw 'Could not prepare Workshop manifest.' }
$settings = Get-Content "$workspace/config.json" -Raw -Encoding UTF8 | ConvertFrom-Json
Get-FileHash -LiteralPath "$workspace/content/shadowpriest.jar" -Algorithm SHA256
Write-Output "Prepared $($settings.title), visibility: $($settings.visibility)."
if (-not $Upload) { Write-Output 'Preparation only. Use -Upload to publish to Steam Workshop.'; return }
$game = "$($local.steamApps)/common/SlayTheSpire"
Push-Location $game
try {
    & "$($local.javaHome)/bin/java.exe" '-Djava.awt.headless=true' -jar "$game/mod-uploader.jar" upload -w $workspace
    if ($LASTEXITCODE -ne 0) { throw 'Workshop upload failed. The workspace and its item ID were preserved.' }
} finally { Pop-Location }
& python "$PSScriptRoot/workshop-manifest.py" persist
if ($LASTEXITCODE -ne 0) { throw 'Could not persist Workshop item ID.' }
