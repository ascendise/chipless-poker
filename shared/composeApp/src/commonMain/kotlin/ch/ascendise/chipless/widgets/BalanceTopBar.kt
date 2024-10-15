package ch.ascendise.chipless.widgets

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.em

@Composable
fun BalanceTopBar(
    balance: Int = 100,
    onBalanceChange: (Int) -> Unit = {}
) {
    val fontSize = 6.em
    TopAppBar(
        title = {
            Text("Player Balance: ", color = Color.White, fontSize = fontSize)
            TextField(
                value = balance.toString(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = { s: String ->
                    if(s.isEmpty()){
                        onBalanceChange(0)
                    } else {
                        onBalanceChange(s.toInt())
                    }
                },
                textStyle = TextStyle.Default.copy(fontWeight = FontWeight.Bold, fontSize = fontSize)
            )
    })
}

