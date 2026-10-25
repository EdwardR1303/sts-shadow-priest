# Desktop entry point: rebuild the current source, then launch the isolated 4K game.
$ErrorActionPreference = 'Stop'
$repo = Split-Path $PSScriptRoot -Parent
$launchMutex = [System.Threading.Mutex]::new($false, 'Local\ShadowPriestPlaytestLauncher')
$ownsMutex = $false
$transcribing = $false
try {
    try { $ownsMutex = $launchMutex.WaitOne(0) }
    catch [System.Threading.AbandonedMutexException] { $ownsMutex = $true }
    if (-not $ownsMutex) { return }
    $logDirectory = Join-Path $repo '.local/launcher-logs'
    New-Item -ItemType Directory -Force -Path $logDirectory | Out-Null
    $launchLog = Join-Path $logDirectory "$(Get-Date -Format 'yyyyMMdd-HHmmss-fffffff').log"
    Start-Transcript -Path $launchLog | Out-Null
    $transcribing = $true
    & "$PSScriptRoot/playtest.ps1"
    $gameProcessId = [int](Get-Content "$repo/.local/playtest.pid" -Raw).Trim()
    Start-Sleep -Seconds 8
    if (-not (Get-Process -Id $gameProcessId -ErrorAction SilentlyContinue)) {
        throw "The game exited during startup. See $repo/.playtest/console.log and error.log."
    }
} catch {
    $details = "Shadow Priest could not launch.`n`n$($_.Exception.Message)`n`nLauncher logs: $repo/.local/launcher-logs"
    Write-Output $details
    Add-Type -AssemblyName System.Windows.Forms
    [System.Windows.Forms.MessageBox]::Show($details, 'Shadow Priest Playtest', 'OK', 'Error') | Out-Null
    exit 1
} finally {
    if ($transcribing) { Stop-Transcript | Out-Null }
    if ($ownsMutex) { $launchMutex.ReleaseMutex() }
    $launchMutex.Dispose()
}
