# X Dynamic Island

An iPhone-style Dynamic Island with a Liquid Glass look for Android, tuned for the Infinix S4 (Android 9, waterdrop notch).

Status: Stage 1 of 8 (project foundation). The overlay arrives in Stage 2.
Privacy: no ads, no analytics, no internet permission. Settings stay on the phone.

## Put it on GitHub from Termux

Run these one line at a time.

```bash
pkg update && pkg install -y git unzip
```
Installs git (version control) and unzip.

```bash
termux-setup-storage
```
Lets Termux read your Downloads folder. Tap Allow.

```bash
cd ~ && unzip ~/storage/downloads/XDynamicIsland.zip
```
Unpacks the project into `~/XDynamicIsland`.

```bash
cd ~/XDynamicIsland
git init -b main
git config user.name "Chibuike"
git config user.email "ezeigbochibuike44@gmail.com"
git add .
git commit -m "Stage 1: project foundation"
```
Creates a repository and saves the first snapshot.

Create an empty repository named `x-dynamic-island` on github.com (no README), then:

```bash
git remote add origin https://github.com/ezeigbochibuike44-eng/x-dynamic-island.git
git push -u origin main
```
When asked for a password, paste a GitHub personal access token (github.com, Settings, Developer settings, Personal access tokens, with "repo" permission). Your normal password will not work.

## Get the APK

1. Open the repository on GitHub, tap the Actions tab.
2. Open the latest "Build debug APK" run. It takes a few minutes.
3. When it shows a green tick, scroll to Artifacts and download `x-dynamic-island-debug-apk`.
4. Unzip it, tap `app-debug.apk`, allow installing from this source, and install.

## If the build fails

Open the failed run, tap the red step, and copy the last 30 lines of the log to Claude.

## Project layout

See `ARCHITECTURE.md`.
