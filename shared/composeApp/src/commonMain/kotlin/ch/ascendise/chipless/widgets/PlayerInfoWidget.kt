package ch.ascendise.chipless.widgets

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import ch.ascendise.chipless.components.FormTextField
import ch.ascendise.chipless.views.gamemanagement.CreatePlayerModel
import chiplesspoker.shared.composeapp.generated.resources.Res
import chiplesspoker.shared.composeapp.generated.resources.player_name
import org.jetbrains.compose.resources.stringResource

@Composable
fun PlayerInfoWidget(
    player: CreatePlayerModel,
    modifier: Modifier = Modifier,
    onPlayerEdit: (CreatePlayerModel) -> Unit = {},
    isDealer: Boolean = false,
    setAsDealer: () -> Unit = {}) {

    var state by remember {  mutableStateOf(player) }
    Box(modifier = modifier) {
        FormTextField(
            value = state.name,
            onValueChange = { state = state.copy(name = it) },
            caption = stringResource(Res.string.player_name),
            modifier = Modifier.fillMaxWidth()
                .onFocusChanged { if (!it.isFocused) onPlayerEdit(state) }
        )
        Row(modifier = Modifier.align(Alignment.CenterEnd),
            verticalAlignment = Alignment.CenterVertically) {
            if(isDealer)
                Text(text = "(Dealer)")
            RadioButton(
                selected = isDealer,
                modifier = Modifier.testTag("setAsDealer(${player.name})"),
                onClick = { setAsDealer() })
        }
    }
}