package ch.ascendise.chipless.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import chiplesspoker.shared.composeapp.generated.resources.Res
import chiplesspoker.shared.composeapp.generated.resources.chip
import chiplesspoker.shared.composeapp.generated.resources.chip_content_description
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun Chip(
    value: Int,
    modifier: Modifier = Modifier) {

    Box(modifier = modifier) {
        Image(
            painter = painterResource(Res.drawable.chip),
            contentDescription = stringResource(Res.string.chip_content_description),
            contentScale = ContentScale.Inside
        )
        Text(
            modifier = Modifier
                .align(Alignment.Center),
            text = "$value",
            overflow = TextOverflow.Visible,
            softWrap = false,
            color = Color.Black)
    }
}