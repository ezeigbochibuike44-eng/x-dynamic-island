package com.xdynamicisland.app.ui

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.xdynamicisland.app.R
import com.xdynamicisland.app.XApp
import com.xdynamicisland.app.util.DeviceInfo

/**
 * Stage 1 home screen. It proves the project builds, settings load, and the phone's
 * real screen details can be read. The dashboard and wizards replace it in later stages.
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val infoText = findViewById<TextView>(R.id.infoText)
        // Wait until the window is attached so display cutout info is available.
        window.decorView.post { infoText.text = buildInfo() }
    }

    private fun buildInfo(): String {
        val screen = DeviceInfo.read(this)
        val saved = (application as XApp).settings.load()
        val cutout = if (screen.cutoutRects.isEmpty()) {
            getString(R.string.cutout_none)
        } else {
            screen.cutoutRects.joinToString { it.toShortString() }
        }
        return getString(
            R.string.info_template,
            screen.model,
            screen.sdkInt,
            screen.widthPx,
            screen.heightPx,
            screen.densityDpi,
            screen.density,
            screen.widthDp,
            screen.heightDp,
            screen.statusBarPx,
            screen.statusBarDp,
            cutout,
            saved.widthDp,
            saved.heightDp
        )
    }
}
