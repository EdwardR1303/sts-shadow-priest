param(
    [switch]$SkipBuild, [switch]$Debug, [switch]$SmokeTest, [switch]$CardBatchOnly, [switch]$LatestBatchOnly, [switch]$ReworkOnly, [switch]$DefensiveReworkOnly, [switch]$SupportOnly, [switch]$RelicOnly, [switch]$RelicArtOnly, [switch]$SetupOnly, [switch]$DetailsOnly, [switch]$BarrierOnly,
    [ValidateRange(640, 7680)][int]$Width = 3840,
    [ValidateRange(480, 4320)][int]$Height = 2160,
    [switch]$Windowed
)
$ErrorActionPreference = 'Stop'
if ($RelicArtOnly) { $RelicOnly = $true }
if ($SetupOnly -or $DetailsOnly -or $BarrierOnly) { $SmokeTest = $true }
if ($ReworkOnly -or $DefensiveReworkOnly -or $SupportOnly -or $RelicOnly) { $SmokeTest = $true }
if ($LatestBatchOnly) { $CardBatchOnly = $true }
if ($CardBatchOnly) { $SmokeTest = $true }
$repo = Split-Path $PSScriptRoot -Parent
# Prevent a second normal instance from sharing saves or replacing live logs.
if (-not $SmokeTest -and (Test-Path "$repo/.local/playtest.pid")) {
    $existingId = 0
    if ([int]::TryParse((Get-Content "$repo/.local/playtest.pid" -Raw).Trim(), [ref]$existingId)) {
        $existing = Get-CimInstance Win32_Process -Filter "ProcessId = $existingId" -ErrorAction SilentlyContinue
        if ($existing -and $existing.Name -eq 'java.exe' -and
            $existing.CommandLine -like '*-Dshadowpriest.playtest=true*' -and
            $existing.CommandLine -notlike '*-Dshadowpriest.smoketest=true*') {
            Write-Output "Shadow Priest playtest is already running (PID $existingId)."
            return
        }
    }
}
if (-not $SkipBuild) { & "$PSScriptRoot/build.ps1" }
$config = Get-Content "$repo/.local/config.json" -Raw | ConvertFrom-Json
$run = if ($SmokeTest) { "$repo/.smoketest" } else { "$repo/.playtest" }
New-Item -ItemType Directory -Force -Path $run, "$run/mods" | Out-Null
if (-not $SmokeTest) {
    $oldLogs = @('console.log', 'error.log') | ForEach-Object { "$run/$_" } | Where-Object { Test-Path -LiteralPath $_ }
    if ($oldLogs) {
        $archive = "$run/logs/$(Get-Date -Format 'yyyyMMdd-HHmmss-fffffff')"
        New-Item -ItemType Directory -Force -Path $archive | Out-Null
        Copy-Item -LiteralPath $oldLogs -Destination $archive
    }
}
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
if ($ReworkOnly) { $arguments += '-Dshadowpriest.reworkonly=true' }
if ($DefensiveReworkOnly) { $arguments += '-Dshadowpriest.defensivereworkonly=true' }
if ($SupportOnly) { $arguments += '-Dshadowpriest.supportonly=true' }
if ($RelicOnly) { $arguments += '-Dshadowpriest.reliconly=true' }
if ($RelicArtOnly) { $arguments += '-Dshadowpriest.relicartonly=true' }
if ($BarrierOnly) { $arguments += '-Dshadowpriest.barrieronly=true' }
if ($DetailsOnly) { $arguments += '-Dshadowpriest.detailsonly=true' }
if ($SetupOnly) { $arguments += '-Dshadowpriest.setuponly=true' }
if ($SmokeTest) { [IO.File]::WriteAllText("$run/smoke-results.txt", "RUNNING`n") }
$mts = "$($config.steamApps)/workshop/content/646570/1605060445/ModTheSpire.jar"
$arguments += @('-jar', ('"' + $mts + '"'), '--mods', 'basemod,stslib,shadowpriest', '--skip-intro')
$process = Start-Process -FilePath "$game/jre/bin/java.exe" -ArgumentList $arguments -WorkingDirectory $run -WindowStyle Hidden -RedirectStandardOutput "$run/console.log" -RedirectStandardError "$run/error.log" -PassThru
$pidFile = if ($SmokeTest) { 'smoketest.pid' } else { 'playtest.pid' }
[IO.File]::WriteAllText("$repo/.local/$pidFile", [string]$process.Id)
Write-Output "Display: $Width x $Height; borderless: $borderless"
Write-Output "Playtest started (PID $($process.Id)). Separate saves: $run"
Write-Output "Logs: $run/console.log and $run/error.log"
