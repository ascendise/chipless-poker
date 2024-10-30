package ch.ascendise.chipless.views.game

import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.platform.LocalViewConfiguration
import androidx.compose.ui.platform.LocalWindowInfo
import androidx.compose.ui.platform.ViewConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.test.*
import androidx.compose.ui.unit.LayoutDirection
import ch.ascendise.chipless.Display
import ch.ascendise.chipless.getDisplay
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class GameViewTests {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun shouldForceLandscapeOrientation() = runComposeUiTest {
        //Arrange
        var display: Display? = null
        //Act
        setContent {
            display = getDisplay()
            GameView()
        }
        //Assert
        assertNotNull(display)
        assertEquals(Display.Orientation.Landscape, display?.orientation)

    }
}