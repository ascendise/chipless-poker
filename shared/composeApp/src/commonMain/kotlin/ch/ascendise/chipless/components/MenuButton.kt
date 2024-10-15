package ch.ascendise.chipless.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.OutlinedButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun MenuButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable () -> Unit) {

    val green = Color(0, 160, 0)
    val buttonColor = if(enabled) green else Color.Gray

    OutlinedButton(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = buttonColor, contentColor = Color.White),
        modifier = modifier,
        border = BorderStroke(2.dp, Color.White),
        shape = RoundedCornerShape(32.dp),
        enabled = enabled
    ) {
        content()
    }

}