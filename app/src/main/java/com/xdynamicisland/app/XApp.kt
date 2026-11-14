package com.xdynamicisland.app

import android.app.Application
import com.xdynamicisland.app.data.SettingsRepository

/** Application class: holds the single shared settings repository. */
class XApp : Application() {
    val settings: SettingsRepository by lazy { SettingsRepository(this) }
}
