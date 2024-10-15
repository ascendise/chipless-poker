package ch.ascendise.chipless.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun FormTextField(
    value: String = "",
    onValueChange: (String) -> Unit = {},
    caption: String = "",
    modifier: Modifier = Modifier
) {

    TextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(caption) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color(61, 61, 61),
            textColor = Color.White
        ),
        shape = RoundedCornerShape(16.dp),
        modifier = modifier
    )
}