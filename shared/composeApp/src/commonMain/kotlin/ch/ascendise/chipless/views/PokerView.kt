package ch.ascendise.chipless.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun PokerView() {
    MaterialTheme {
        Row(
            modifier = Modifier.fillMaxWidth().padding(10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                Text("Player1")
                Text("Player2")
                Text("Player3")
                Text("Player4")
            }
            Column {
                Button({}) {
                    Text("Call")
                }
                Button({}) {
                    Text("Raise")
                }
                Button({}) {
                    Text("Check")
                }
                Button({}) {
                    Text("Fold")
                }
            }
        }
    }
}