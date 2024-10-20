package ch.ascendise.chipless.views

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ch.ascendise.chipless.components.MenuButton
import chiplesspoker.shared.composeapp.generated.resources.Res
import chiplesspoker.shared.composeapp.generated.resources.create_new_game_button
import chiplesspoker.shared.composeapp.generated.resources.settings_button
import chiplesspoker.shared.composeapp.generated.resources.load_existing_game_button
import chiplesspoker.shared.composeapp.generated.resources.chipless_poker
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun StartScreenView(viewModel: StartMenuViewModel) {
    Column(
        modifier = Modifier.padding(horizontal = 40.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        Row(modifier = Modifier.padding(vertical = 80.dp)) {
            Text(stringResource(Res.string.chipless_poker))
        }
        Row {
            Column {
                MenuButton({ viewModel.createNewGame() },
                    modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(Res.string.create_new_game_button))
                }
                MenuButton({ viewModel.loadExistingGames() },
                    modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(Res.string.load_existing_game_button))
                }
                MenuButton({ viewModel.editSettings() },
                    modifier = Modifier.fillMaxWidth()) {
                    Text(stringResource(Res.string.settings_button))
                }

            }
        }
    }
}