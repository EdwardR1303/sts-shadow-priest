param([switch]$Deploy)
$ErrorActionPreference = 'Stop'
$repo = Split-Path $PSScriptRoot -Parent
if (-not (Test-Path "$repo/.local/config.json")) { & "$PSScriptRoot/setup.ps1" }
$config = Get-Content "$repo/.local/config.json" -Raw | ConvertFrom-Json
$oldJava = $env:JAVA_HOME
try {
    $env:JAVA_HOME = $config.javaHome
    Push-Location $repo
    try {
        # Incremental builds avoid deleting/recreating the OneDrive-synced target tree.
        & "$($config.mavenHome)/bin/mvn.cmd" --batch-mode --no-transfer-progress verify
        if ($LASTEXITCODE -ne 0) { throw "Build failed ($LASTEXITCODE)." }
    } finally { Pop-Location }
    if ($Deploy) {
        $mods = "$($config.steamApps)/common/SlayTheSpire/mods"
        New-Item -ItemType Directory -Force -Path $mods | Out-Null
        Copy-Item -LiteralPath "$repo/target/shadowpriest.jar" -Destination "$mods/shadowpriest.jar" -Force
        Write-Output "Installed $mods/shadowpriest.jar"
    }
} finally { $env:JAVA_HOME = $oldJava }
