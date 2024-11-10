package ch.ascendise.chipless.components

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import chiplesspoker.shared.composeapp.generated.resources.Res
import chiplesspoker.shared.composeapp.generated.resources.pokertable_content_description
import chiplesspoker.shared.composeapp.generated.resources.table
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PokerTable(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(Res.drawable.table),
        contentDescription = stringResource(Res.string.pokertable_content_description)
    )
}