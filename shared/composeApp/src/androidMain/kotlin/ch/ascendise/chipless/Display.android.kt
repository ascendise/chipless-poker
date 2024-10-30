package ch.ascendise.chipless

import android.app.Activity
import android.content.pm.ActivityInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
actual fun getDisplay(): Display
    = AndroidDisplay(LocalContext.current as Activity)

class AndroidDisplay(private val context: Activity): Display {
    override var orientation: Display.Orientation
        get() = when(context.requestedOrientation) {
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT -> Display.Orientation.Portrait
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE -> Display.Orientation.Landscape
            else -> throw UnsupportedOperationException("Was not able to map orientation!")
        }
        set(value) {
            context.requestedOrientation = when(value) {
                Display.Orientation.Portrait -> ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
                Display.Orientation.Landscape -> ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            }
        }
}