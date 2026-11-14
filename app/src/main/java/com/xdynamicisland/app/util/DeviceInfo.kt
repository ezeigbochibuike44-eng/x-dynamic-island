package com.xdynamicisland.app.util

import android.app.Activity
import android.graphics.Rect
import android.os.Build
import android.util.DisplayMetrics

/** What Android reports about the screen. Used by the calibration wizard in Stage 2. */
data class ScreenInfo(
    val model: String,
    val sdkInt: Int,
    val widthPx: Int,
    val heightPx: Int,
    val densityDpi: Int,
    val density: Float,
    val statusBarPx: Int,
    val cutoutRects: List<Rect>
) {
    val widthDp: Float get() = widthPx / density
    val heightDp: Float get() = heightPx / density
    val statusBarDp: Float get() = statusBarPx / density
}

object DeviceInfo {

    /**
     * Reads screen details. Call after the window is attached (for example from
     * decorView.post { }) so display cutout information is available.
     */
    fun read(activity: Activity): ScreenInfo {
        val metrics = DisplayMetrics()
        @Suppress("DEPRECATION")
        activity.windowManager.defaultDisplay.getRealMetrics(metrics)

        val res = activity.resources
        val statusId = res.getIdentifier("status_bar_height", "dimen", "android")
        val statusPx = if (statusId > 0) res.getDimensionPixelSize(statusId) else 0

        val cutouts: List<Rect> = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            activity.window.decorView.rootWindowInsets?.displayCutout?.boundingRects
                ?: emptyList()
        } else {
            emptyList()
        }

        return ScreenInfo(
            model = "${Build.MANUFACTURER} ${Build.MODEL}",
            sdkInt = Build.VERSION.SDK_INT,
            widthPx = metrics.widthPixels,
            heightPx = metrics.heightPixels,
            densityDpi = metrics.densityDpi,
            density = metrics.density,
            statusBarPx = statusPx,
            cutoutRects = cutouts
        )
    }
}
