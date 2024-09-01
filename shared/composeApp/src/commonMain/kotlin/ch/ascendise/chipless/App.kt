import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.*
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview
import ch.ascendise.chipless.views.PokerView

@Composable
@Preview
fun App() {
    MaterialTheme {
        PokerView()
    }
}