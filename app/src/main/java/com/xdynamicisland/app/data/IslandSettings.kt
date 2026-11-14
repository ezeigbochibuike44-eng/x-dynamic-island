package com.xdynamicisland.app.data

/**
 * Island geometry and on/off state. All sizes are in dp so they scale with screen density.
 * Defaults are a starting point for the Infinix S4 waterdrop notch; the calibration
 * wizard (Stage 2) lets the user fine-tune them on the real device.
 */
data class IslandSettings(
    val enabled: Boolean = false,
    val widthDp: Int = DEFAULT_WIDTH_DP,
    val heightDp: Int = DEFAULT_HEIGHT_DP,
    val topOffsetDp: Int = DEFAULT_TOP_OFFSET_DP,
    val horizontalOffsetDp: Int = DEFAULT_HORIZONTAL_OFFSET_DP,
    val cornerRadiusDp: Int = DEFAULT_CORNER_RADIUS_DP
) {
    /** Returns a copy with every value forced into a safe range. */
    fun coerced(): IslandSettings = copy(
        widthDp = widthDp.coerceIn(MIN_WIDTH_DP, MAX_WIDTH_DP),
        heightDp = heightDp.coerceIn(MIN_HEIGHT_DP, MAX_HEIGHT_DP),
        topOffsetDp = topOffsetDp.coerceIn(MIN_TOP_OFFSET_DP, MAX_TOP_OFFSET_DP),
        horizontalOffsetDp = horizontalOffsetDp.coerceIn(-MAX_HORIZONTAL_OFFSET_DP, MAX_HORIZONTAL_OFFSET_DP),
        cornerRadiusDp = cornerRadiusDp.coerceIn(MIN_CORNER_RADIUS_DP, MAX_CORNER_RADIUS_DP)
    )

    companion object {
        const val DEFAULT_WIDTH_DP = 120
        const val DEFAULT_HEIGHT_DP = 30
        const val DEFAULT_TOP_OFFSET_DP = 0
        const val DEFAULT_HORIZONTAL_OFFSET_DP = 0
        const val DEFAULT_CORNER_RADIUS_DP = 15

        const val MIN_WIDTH_DP = 60
        const val MAX_WIDTH_DP = 300
        const val MIN_HEIGHT_DP = 20
        const val MAX_HEIGHT_DP = 80
        const val MIN_TOP_OFFSET_DP = 0
        const val MAX_TOP_OFFSET_DP = 60
        const val MAX_HORIZONTAL_OFFSET_DP = 150
        const val MIN_CORNER_RADIUS_DP = 0
        const val MAX_CORNER_RADIUS_DP = 40
    }
}
