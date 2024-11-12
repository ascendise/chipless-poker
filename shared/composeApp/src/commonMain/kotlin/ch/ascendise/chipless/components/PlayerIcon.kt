package ch.ascendise.chipless.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Face
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import ch.ascendise.chipless.colorutils.random

@Composable
fun PlayerIcon(
    modifier: Modifier = Modifier,
    color: Color = Color.random()) {
    Icon(
        imageVector = Icons.Filled.Face,
        contentDescription = "Circle representing player",
        modifier = modifier
            .background(color, CircleShape)
            .fillMaxSize())
}