param(
    [switch]$SkipBuild, [switch]$Debug, [switch]$SmokeTest, [switch]$CardBatchOnly, [switch]$LatestBatchOnly,
    [ValidateRange(640, 7680)][int]$Width = 3840,
    [ValidateRange(480, 4320)][int]$Height = 2160,
    [switch]$Windowed
)
$ErrorActionPreference = 'Stop'
if ($LatestBatchOnly) { $CardBatchOnly = $true }
if ($CardBatchOnly) { $SmokeTest = $true }
$repo = Split-Path $PSScriptRoot -Parent
if (-not $SkipBuild) { & "$PSScriptRoot/build.ps1" }
$config = Get-Content "$repo/.local/config.json" -Raw | ConvertFrom-Json
$run = if ($SmokeTest) { "$repo/.smoketest" } else { "$repo/.playtest" }
New-Item -ItemType Directory -Force -Path $run, "$run/mods" | Out-Null
$game = "$($config.steamApps)/common/SlayTheSpire"
if (-not (Test-Path "$run/desktop-1.0.jar")) { Copy-Item -LiteralPath "$game/desktop-1.0.jar" -Destination "$run/desktop-1.0.jar" }
foreach ($pair in @(@('1605833019','BaseMod.jar'), @('1609158507','StSLib.jar'))) {
    Copy-Item -LiteralPath "$($config.steamApps)/workshop/content/646570/$($pair[0])/$($pair[1])" -Destination "$run/mods/$($pair[1])" -Force
}
Copy-Item -LiteralPath "$repo/target/shadowpriest.jar" -Destination "$run/mods/shadowpriest.jar" -Force
$borderless = (-not $Windowed).ToString().ToLowerInvariant()
[IO.File]::WriteAllText("$run/info.displayconfig", "$Width`n$Height`n60`nfalse`n$borderless`ntrue`n")
# Saves and preferences are relative to this isolated working directory.
$arguments = @('-Xmx1G', '-Dshadowpriest.playtest=true')
if ($Debug) { $arguments += '-agentlib:jdwp=transport=dt_socket,server=y,suspend=n,address=5005' }
if ($SmokeTest) { $arguments += '-Dshadowpriest.smoketest=true' }
if ($CardBatchOnly) { $arguments += '-Dshadowpriest.cardbatchonly=true' }
if ($LatestBatchOnly) { $arguments += '-Dshadowpriest.latestbatchonly=true' }
if ($SmokeTest) { [IO.File]::WriteAllText("$run/smoke-results.txt", "RUNNING`n") }
$mts = "$($config.steamApps)/workshop/content/646570/1605060445/ModTheSpire.jar"
$arguments += @('-jar', ('"' + $mts + '"'), '--mods', 'basemod,stslib,shadowpriest', '--skip-intro')
$process = Start-Process -FilePath "$game/jre/bin/java.exe" -ArgumentList $arguments -WorkingDirectory $run -WindowStyle Hidden -RedirectStandardOutput "$run/console.log" -RedirectStandardError "$run/error.log" -PassThru
$pidFile = if ($SmokeTest) { 'smoketest.pid' } else { 'playtest.pid' }
[IO.File]::WriteAllText("$repo/.local/$pidFile", [string]$process.Id)
Write-Output "Display: $Width x $Height; borderless: $borderless"
Write-Output "Playtest started (PID $($process.Id)). Separate saves: $run"
Write-Output "Logs: $run/console.log and $run/error.log"
