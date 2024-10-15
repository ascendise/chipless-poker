package ch.ascendise.chipless

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import chiplesspoker.shared.composeapp.generated.resources.Oswald_Bold
import chiplesspoker.shared.composeapp.generated.resources.Oswald_Regular
import chiplesspoker.shared.composeapp.generated.resources.Oswald_SemiBold
import chiplesspoker.shared.composeapp.generated.resources.Res
import org.jetbrains.compose.resources.Font

@Composable
internal fun PokerTheme(modifier: Modifier = Modifier, content: @Composable () -> Unit) {
    MaterialTheme(
        typography = Typography(
            h1 = Typography().h1.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            h2 = Typography().h2.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            h3 = Typography().h3.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            h4 = Typography().h4.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            h5 = Typography().h5.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            h6 = Typography().h6.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            subtitle1 = Typography().subtitle1.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            subtitle2 = Typography().subtitle2.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            body1 = Typography().body1.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            body2 = Typography().body2.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            caption = Typography().caption.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
            button = Typography().button.copy(fontFamily = FontFamily(Font(Res.font.Oswald_SemiBold))),
            overline = Typography().overline.copy(fontFamily = FontFamily(Font(Res.font.Oswald_Regular))),
        ),
        colors = MaterialTheme.colors.copy(
            primary = Color(0, 160, 0),
        )
    ) {
        Surface(
            modifier = modifier,
            color = Color(35, 35, 35),
        ) {
            content()
        }
    }
}