package ch.ascendise.chipless.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import ch.ascendise.chipless.colorutils.random
import ch.ascendise.chipless.components.Chip
import ch.ascendise.chipless.components.PlayerIcon

@Composable
fun Player(
    name: String,
    balance: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.random()) {
    Box(modifier = modifier) {
        PlayerIcon(color = color)
        Chip(balance, Modifier
            .offset(y = (-16).dp)
            .align(Alignment.TopCenter)
            .size(32.dp))
        Text(
            text = name,
            modifier = Modifier.align(Alignment.Center),
            fontWeight = FontWeight.Bold,
            overflow = TextOverflow.Visible,
            softWrap = false,
            color = Color.White,
        )
    }
}