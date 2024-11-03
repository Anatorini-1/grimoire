package pl.anatorini.grimoire

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.navigation.MasterRoutes
import pl.anatorini.grimoire.state.Auth
import pl.anatorini.grimoire.state.Settings
import pl.anatorini.grimoire.ui.components.screens.MainScreen

@Composable
@Preview
fun App() {
    val navController = rememberNavController()
    var settings by remember { mutableStateOf(Settings()) }
    var auth by remember { mutableStateOf(Auth()) }

    NavHost(navController = navController, startDestination = MasterRoutes.MAIN.name) {
        composable(MasterRoutes.MAIN.name) {
            MainScreen(
                modifier = Modifier,
                auth = auth,
                masterNavController = navController,
                settings = settings,
                setSettings = { s -> settings = s }
            )
        }
    }

}