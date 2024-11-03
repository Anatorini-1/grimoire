package pl.anatorini.grimoire.screens

import androidx.compose.foundation.background
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.navigation.SettingsRoutes
import pl.anatorini.grimoire.screens.settings.EditServerAddressScreen
import pl.anatorini.grimoire.screens.settings.SettingsList
import pl.anatorini.grimoire.screens.settings.SomeOtherSettingsScreen
import pl.anatorini.grimoire.state.Settings
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun SettingsScreen(
    modifier: Modifier = Modifier,
    settings: Settings,
    setSettings: (Settings) -> Unit
) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = SettingsRoutes.HOME.name,
        modifier = modifier
    ) {
        composable(route = SettingsRoutes.HOME.name) {
            SettingsList(settings = settings, setSettings = {}, navController = navController)
        }
        composable(route = SettingsRoutes.SERVER.name) {
            EditServerAddressScreen(navController = navController, settings = settings, setSettings = {
                server,port -> setSettings(Settings(server,port))
            })

        }
        composable(route = SettingsRoutes.OTHER.name) {
            SomeOtherSettingsScreen(navController = navController)
        }
    }
}


@Composable
@Preview
fun SettingsScreenPreview() {
    val settings by remember {
        mutableStateOf(Settings())
    }
    AppTheme {
        SettingsScreen(
            modifier = Modifier.background(Color.White),
            settings = settings, setSettings = { s -> })
    }
}