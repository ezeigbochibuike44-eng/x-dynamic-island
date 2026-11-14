package com.xdynamicisland.app.data

import android.content.Context

/**
 * Saves and loads [IslandSettings] with SharedPreferences.
 * Stored privately on the phone only; nothing is sent anywhere.
 */
class SettingsRepository(context: Context) {

    private val prefs = context.applicationContext
        .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun load(): IslandSettings {
        val d = IslandSettings()
        return IslandSettings(
            enabled = prefs.getBoolean(KEY_ENABLED, d.enabled),
            widthDp = prefs.getInt(KEY_WIDTH, d.widthDp),
            heightDp = prefs.getInt(KEY_HEIGHT, d.heightDp),
            topOffsetDp = prefs.getInt(KEY_TOP_OFFSET, d.topOffsetDp),
            horizontalOffsetDp = prefs.getInt(KEY_HORIZONTAL_OFFSET, d.horizontalOffsetDp),
            cornerRadiusDp = prefs.getInt(KEY_CORNER_RADIUS, d.cornerRadiusDp)
        ).coerced()
    }

    fun save(settings: IslandSettings) {
        val s = settings.coerced()
        prefs.edit()
            .putBoolean(KEY_ENABLED, s.enabled)
            .putInt(KEY_WIDTH, s.widthDp)
            .putInt(KEY_HEIGHT, s.heightDp)
            .putInt(KEY_TOP_OFFSET, s.topOffsetDp)
            .putInt(KEY_HORIZONTAL_OFFSET, s.horizontalOffsetDp)
            .putInt(KEY_CORNER_RADIUS, s.cornerRadiusDp)
            .apply()
    }

    /** Wipes everything saved, so [load] returns the defaults again. */
    fun reset() {
        prefs.edit().clear().apply()
    }

    private companion object {
        const val PREFS_NAME = "xdi_settings"
        const val KEY_ENABLED = "enabled"
        const val KEY_WIDTH = "width_dp"
        const val KEY_HEIGHT = "height_dp"
        const val KEY_TOP_OFFSET = "top_offset_dp"
        const val KEY_HORIZONTAL_OFFSET = "horizontal_offset_dp"
        const val KEY_CORNER_RADIUS = "corner_radius_dp"
    }
}
