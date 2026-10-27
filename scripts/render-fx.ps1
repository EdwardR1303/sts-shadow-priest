param([switch]$SkipBuild, [ValidateSet('0.5','1','2')][string]$Scale = '0.5')
$ErrorActionPreference = 'Stop'
$repo = Split-Path -Parent $PSScriptRoot
$cfg = Get-Content (Join-Path $repo '.local/config.json') | ConvertFrom-Json
$env:JAVA_HOME = $cfg.javaHome
Push-Location $repo
try {
    if (!$SkipBuild) {
        & "$($cfg.mavenHome)/bin/mvn.cmd" --batch-mode --no-transfer-progress test-compile
        if ($LASTEXITCODE -ne 0) { throw 'FX review compilation failed' }
    }
    $game = Join-Path $cfg.steamApps 'common/SlayTheSpire/desktop-1.0.jar'
    & "$($cfg.javaHome)/bin/java.exe" -cp "target/test-classes;target/classes;$game" shadowpriest.vfx.FxRenderReview $Scale
    if ($LASTEXITCODE -ne 0) { throw 'Offscreen FX review failed' }
} finally { Pop-Location }
