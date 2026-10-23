param([string]$SteamApps)
$ErrorActionPreference = 'Stop'
$ProgressPreference = 'SilentlyContinue'
$repo = Split-Path $PSScriptRoot -Parent
$toolsRoot = Join-Path $env:LOCALAPPDATA 'sts-shadow-priest-tools'
New-Item -ItemType Directory -Force -Path $toolsRoot, "$repo/.local", "$repo/.mvn", "$repo/.vscode" | Out-Null

if (-not $SteamApps) {
    $steam = (Get-ItemProperty HKCU:/Software/Valve/Steam -ErrorAction Stop).SteamPath
    $libraries = @($steam)
    $vdf = Get-Content (Join-Path $steam 'steamapps/libraryfolders.vdf') -Raw
    foreach ($match in [regex]::Matches($vdf, '"path"\s+"([^"]+)"')) { $libraries += $match.Groups[1].Value.Replace('\\','\') }
    foreach ($library in $libraries) {
        $candidate = Join-Path $library 'steamapps'
        if (Test-Path (Join-Path $candidate 'common/SlayTheSpire/desktop-1.0.jar')) { $SteamApps = $candidate; break }
    }
}
if (-not $SteamApps) { throw 'Game not found. Run setup.ps1 -SteamApps <Steam library/steamapps>.' }
$SteamApps = (Resolve-Path -LiteralPath $SteamApps).Path.Replace('\','/')
foreach ($relative in @('common/SlayTheSpire/desktop-1.0.jar', 'workshop/content/646570/1605060445/ModTheSpire.jar', 'workshop/content/646570/1605833019/BaseMod.jar', 'workshop/content/646570/1609158507/StSLib.jar')) {
    if (-not (Test-Path -LiteralPath "$SteamApps/$relative")) { throw "Missing $relative. Install the game and subscribe to ModTheSpire, BaseMod, and StSLib in Steam Workshop." }
}

$jdk = Get-ChildItem -LiteralPath $toolsRoot -Directory -Filter 'jdk8*' | Sort-Object Name -Descending | Select-Object -First 1
if (-not $jdk) {
    $asset = (Invoke-RestMethod 'https://api.adoptium.net/v3/assets/latest/8/hotspot?architecture=x64&image_type=jdk&os=windows&vendor=eclipse')[0]
    $package = $asset.binary.package
    $zip = Join-Path $toolsRoot $package.name
    Invoke-WebRequest -UseBasicParsing $package.link -OutFile $zip
    if ((Get-FileHash $zip -Algorithm SHA256).Hash.ToLower() -ne $package.checksum) { throw 'JDK checksum mismatch.' }
    Expand-Archive -LiteralPath $zip -DestinationPath $toolsRoot -Force
    $jdk = Get-ChildItem -LiteralPath $toolsRoot -Directory -Filter 'jdk8*' | Sort-Object Name -Descending | Select-Object -First 1
}
$maven = Join-Path $toolsRoot 'apache-maven-3.9.11'
if (-not (Test-Path "$maven/bin/mvn.cmd")) {
    $url = 'https://repo.maven.apache.org/maven2/org/apache/maven/apache-maven/3.9.11/apache-maven-3.9.11-bin.zip'
    $zip = Join-Path $toolsRoot 'apache-maven-3.9.11-bin.zip'
    Invoke-WebRequest -UseBasicParsing $url -OutFile $zip
    $expected = (Invoke-WebRequest -UseBasicParsing ($url + '.sha512')).Content.Trim()
    if ((Get-FileHash $zip -Algorithm SHA512).Hash.ToLower() -ne $expected.ToLower()) { throw 'Maven checksum mismatch.' }
    Expand-Archive -LiteralPath $zip -DestinationPath $toolsRoot -Force
}
$config = @{steamApps=$SteamApps; javaHome=$jdk.FullName; mavenHome=$maven}
[IO.File]::WriteAllText("$repo/.local/config.json", ($config | ConvertTo-Json))
[IO.File]::WriteAllText("$repo/.mvn/maven.config", "-Dsteam.path=$SteamApps")
$settings = @{
    'java.configuration.runtimes' = @(@{name='JavaSE-1.8'; path=$jdk.FullName; default=$true})
    'maven.executable.path' = "$maven/bin/mvn.cmd"
    'maven.terminal.useJavaHome' = $true
}
if (-not (Test-Path "$repo/.vscode/settings.json")) {
    [IO.File]::WriteAllText("$repo/.vscode/settings.json", ($settings | ConvertTo-Json -Depth 5))
}
Write-Output "Ready. Game: $SteamApps/common/SlayTheSpire"
Write-Output "Java: $($jdk.FullName)"
Write-Output 'Next: ./scripts/build.ps1'
