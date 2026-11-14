# X Dynamic Island: architecture

Native Android app (Kotlin, XML layouts, no Compose, almost no libraries) so it stays light on a 3 GB RAM phone.
Package `com.xdynamicisland.app`, minSdk 28 (Android 9), targetSdk 33, compileSdk 34.

## The big idea

One foreground service owns the island. Everything else feeds it events.

```
Android system events                 Island service (Stage 2+)
---------------------                 -------------------------
Battery broadcast  ----\
MediaSession listener  -+--> IslandController --> IslandView (WindowManager overlay)
NotificationListener  --+         |                    |
Phone state            -+         |                    +-- compact pill / expanded panel
Timer (clock-based)   --/         |
                                  +-- reads SettingsRepository (SharedPreferences)
```

- No polling. Every source is an event (broadcast, callback, listener). The timer stores an end time and computes the remaining time from the clock, so nothing runs continuously.
- The overlay is a single `WindowManager` view of type `TYPE_APPLICATION_OVERLAY`, sized and placed from the saved settings (dp converted to px for the screen density).
- The overlay window is only as big as the island, so the rest of the screen stays touchable.
- A foreground service is needed on Android 9 to survive background limits. Android will show one persistent notification for it. That cannot be hidden.

## Packages (under `app/src/main/java/com/xdynamicisland/app/`)

| Package | Purpose | Stage |
|---|---|---|
| `XApp.kt` | Application class, shared settings repository | 1 (done) |
| `data/` | `IslandSettings`, `SettingsRepository` | 1 (done), extended in 5 |
| `util/` | `DeviceInfo` (screen size, density, cutout, status bar) | 1 (done) |
| `ui/` | Splash, home, calibration wizard, settings screens | 1 started, 2 to 5 |
| `overlay/` | `IslandService`, `IslandView`, window placement | 2 |
| `anim/` | Open, close and state-transition animators | 3 |
| `features/` | Music, charging, notifications, calls, timer, connectivity sources | 4 |

Packages are created when real code goes in, so there are no empty placeholder folders.

## Permissions

| Permission | Why | How the user grants it |
|---|---|---|
| `SYSTEM_ALERT_WINDOW` | Draw the island over other apps | Settings, Display over other apps (wizard opens the screen) |
| `FOREGROUND_SERVICE` | Keep the island alive on Android 9 | Automatic |
| `VIBRATE` | Haptic feedback | Automatic |
| `POST_NOTIFICATIONS` | Android 13+ only, harmless on Android 9 | Prompt on Android 13+ |
| Notification access (Stage 4) | Read notifications and media sessions | Settings, Notification access (wizard opens the screen) |
| Phone state (Stage 4) | Incoming-call display | Runtime prompt, optional |

No Accessibility Service. No internet permission. No analytics.

## Honest limits on Android 9

- Answer and reject buttons for calls are only possible where Android lets a third-party app do it (notification actions). They are not guaranteed for every call.
- Music control works for apps that publish a standard MediaSession. Others will show nothing rather than a fake player.
- Blur behind the island is limited on Android 9, so the glass look is built from translucency, borders and highlights. Real blur is used only where supported.
- Display cutout details may not be exposed by this phone's firmware. That is why calibration is manual-first.

## Build

GitHub Actions builds the debug APK on every push (`.github/workflows/build.yml`). Building locally in Termux is not practical because the Android SDK tools do not run there reliably.
