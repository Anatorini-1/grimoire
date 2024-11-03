package pl.anatorini.grimoire.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.components.settings.SettingsField
import pl.anatorini.grimoire.navigation.SettingsRoutes
import pl.anatorini.grimoire.state.Settings
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun SettingsList(
    modifier: Modifier = Modifier,
    settings: Settings,
    setSettings: (Settings) -> Unit,
    navController: NavController
) {

    var serverAddress by remember { mutableStateOf(settings.serverAddress) }
    var port by remember { mutableStateOf(settings.port) }

    fun save() {
        setSettings(Settings(serverAddress, port))
    }
    Column(modifier = modifier.fillMaxSize()) {
        SettingsField(
            primaryText = "Server Address",
            secondaryText = "Set the address of the backend server",
            onClick = {
                navController.navigate(SettingsRoutes.SERVER.name)
            },
            icon = Icons.Filled.Build
        )
        SettingsField(
            primaryText = "Some other setting",
            secondaryText = "Placeholder field",
            onClick = {
                navController.navigate(SettingsRoutes.OTHER.name)
            }
        )
    }
}

@Composable
@Preview
fun SettingsListPreview() {
    val settings by remember {
        mutableStateOf(Settings())
    }
    AppTheme {
        SettingsList(
            Modifier.background(Color.White),
            settings = settings, setSettings = {},
            navController = rememberNavController()
        )
    }
}
