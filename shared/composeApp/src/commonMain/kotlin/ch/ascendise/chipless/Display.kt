package ch.ascendise.chipless

import androidx.compose.runtime.Composable

interface Display {

    var orientation: Orientation

    enum class Orientation {
        Landscape,
        Portrait
    }
}


@Composable
expect fun getDisplay(): Display