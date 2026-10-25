$ErrorActionPreference = 'Stop'
$repo = Split-Path $PSScriptRoot -Parent
$config = Get-Content "$repo/.local/config.json" -Raw | ConvertFrom-Json
$desktop = [Environment]::GetFolderPath('Desktop')
$shortcutPath = Join-Path $desktop 'Shadow Priest Playtest.lnk'
$shell = New-Object -ComObject WScript.Shell
try {
    $shortcut = $shell.CreateShortcut($shortcutPath)
    $shortcut.TargetPath = "$env:SystemRoot\System32\WindowsPowerShell\v1.0\powershell.exe"
    $shortcut.Arguments = '-NoProfile -ExecutionPolicy Bypass -WindowStyle Hidden -File "' + (Join-Path $PSScriptRoot 'launch-playtest.ps1') + '"'
    $shortcut.WorkingDirectory = $repo
    $shortcut.IconLocation = "$($config.steamApps)/common/SlayTheSpire/SlayTheSpire.exe,0"
    $shortcut.Description = 'Build and play the latest Shadow Priest mod in 4K borderless, with separate saves.'
    $shortcut.WindowStyle = 7
    $shortcut.Save()
    Write-Output "Created $shortcutPath"
} finally {
    if ($shortcut) { [void][Runtime.InteropServices.Marshal]::FinalReleaseComObject($shortcut) }
    [void][Runtime.InteropServices.Marshal]::FinalReleaseComObject($shell)
}
