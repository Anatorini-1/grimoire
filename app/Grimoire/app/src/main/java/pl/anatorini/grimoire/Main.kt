package pl.anatorini.grimoire

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.components.scaffold.BottomBar
import pl.anatorini.grimoire.components.scaffold.TopBar
import pl.anatorini.grimoire.navigation.NavDrawerItem
import pl.anatorini.grimoire.navigation.Routes
import pl.anatorini.grimoire.screens.HomeScreen
import pl.anatorini.grimoire.screens.SettingsScreen
import pl.anatorini.grimoire.state.Settings
import pl.anatorini.grimoire.ui.theme.Theme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue

@Composable
@Preview
fun Main() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentRoute = navController.currentBackStackEntryAsState()
    var settings by remember { mutableStateOf(Settings()) }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                NavDrawerItem(
                    navController = navController,
                    route = Routes.HOME,
                    label = "Home",
                    icon = Icons.Filled.Home,
                    currentRoute = currentRoute.value,
                    drawerState = drawerState
                )
                NavDrawerItem(
                    navController = navController,
                    route = Routes.SETTINGS,
                    label = "Settings",
                    icon = Icons.Filled.Settings,
                    currentRoute = currentRoute.value,
                    drawerState = drawerState
                )
            }
        }) {

        Theme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                topBar = { TopBar(navController, drawerState) },
                bottomBar = { BottomBar(navController) },
                floatingActionButton = {}
            ) { innerPadding ->
                NavHost(
                    navController = navController,
                    startDestination = Routes.SETTINGS.name,
                    modifier = Modifier.padding(innerPadding)
                ) {
                    composable(route = Routes.HOME.name) {
                        HomeScreen()
                    }
                    composable(route = Routes.SETTINGS.name) {
                        SettingsScreen(modifier = Modifier, settings = settings, setSettings = {s -> settings = s})
                    }

                }

                //HttpClient(Modifier.padding(innerPadding))
            }
        }
    }

}