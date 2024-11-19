package pl.anatorini.grimoire.ui.components.screens

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
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
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Atlas
import compose.icons.fontawesomeicons.solid.BookReader
import compose.icons.fontawesomeicons.solid.Users
import kotlinx.coroutines.launch
import pl.anatorini.grimoire.models.Alignment
import pl.anatorini.grimoire.models.Background
import pl.anatorini.grimoire.models.CharacterClass
import pl.anatorini.grimoire.models.Item
import pl.anatorini.grimoire.models.Race
import pl.anatorini.grimoire.models.Spell
import pl.anatorini.grimoire.navigation.Routes
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.state.Auth
import pl.anatorini.grimoire.state.Settings
import pl.anatorini.grimoire.ui.components.archive.ModelCreationForm
import pl.anatorini.grimoire.ui.components.archive.modelRenderers.DefaultRenderer
import pl.anatorini.grimoire.ui.components.archive.modelRenderers.ItemRenderer
import pl.anatorini.grimoire.ui.components.archive.modelRenderers.SpellRenderer
import pl.anatorini.grimoire.ui.components.navigation.NavDrawerItem
import pl.anatorini.grimoire.ui.components.scaffold.TopBar
import pl.anatorini.grimoire.ui.components.screens.archive.ArchiveModelScreen
import pl.anatorini.grimoire.ui.components.screens.auth.AuthScreen
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun MainScreen(
    modifier: Modifier = Modifier,
    auth: Auth,
    settings: Settings,
    setSettings: (Settings) -> Unit,
    masterNavController: NavHostController
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val currentRoute = navController.currentBackStackEntryAsState()
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                Column(
                    modifier = Modifier.fillMaxHeight(),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
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
                            route = Routes.CHARACTERS,
                            label = "Characters",
                            icon = FontAwesomeIcons.Solid.Users,
                            currentRoute = currentRoute.value,
                            drawerState = drawerState
                        )

                        NavDrawerItem(
                            navController = navController,
                            route = Routes.CAMPAIGNS,
                            label = "Campaigns",
                            icon = FontAwesomeIcons.Solid.BookReader,
                            currentRoute = currentRoute.value,
                            drawerState = drawerState
                        )

                        NavDrawerItem(
                            navController = navController,
                            route = Routes.ARCHIVE,
                            label = "Knowledge Archive",
                            icon = FontAwesomeIcons.Solid.Atlas,
                            currentRoute = currentRoute.value,
                            drawerState = drawerState
                        )
                    }
                    Column {
                        NavDrawerItem(
                            navController = navController,
                            route = Routes.SETTINGS,
                            label = "Settings",
                            icon = Icons.Filled.Settings,
                            currentRoute = currentRoute.value,
                            drawerState = drawerState
                        )
                    }

                }
            }
        }) {

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = { TopBar(navController, drawerState) },
            bottomBar = {},
            floatingActionButton = { }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Routes.HOME.name,
                modifier = Modifier.padding(innerPadding)
            ) {
                composable(route = Routes.HOME.name) {
                    HomeScreen(navController = navController)
                }
                composable(route = Routes.SETTINGS.name) {
                    SettingsScreen(
                        modifier = Modifier,
                        settings = settings,
                        setSettings = { s -> setSettings(s) })
                }
                composable(route = Routes.AUTH.name) {
                    AuthScreen()
                }
                composable(route = Routes.CAMPAIGNS.name) {
                    CampaignsScreen()
                }
                composable(route = Routes.CHARACTERS.name) {
                    CharactersScreen()
                }
                composable(route = Routes.ARCHIVE.name) {
                    ArchiveScreen(navController = navController)
                }
                composable(route = Routes.ARCHIVE_CLASSES.name) {
                    ArchiveModelScreen(
                        modifier = Modifier,
                        render = { characterClass -> DefaultRenderer(item = characterClass) },
                        label = {},
                        getter = HttpService.getClasses,
                        creationForm = { closeFunc ->
                            ModelCreationForm<CharacterClass>(
                                cancel = { closeFunc() },
                                save = { it ->
                                    Log.println(Log.INFO, "", it.toString())
                                    scope.launch {
                                        HttpService.postClass(it)?.let {
                                            Toast.makeText(
                                                context,
                                                "Created!",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        }
                                    }
                                })
                        }
                    )
                }
                composable(route = Routes.ARCHIVE_SPELLS.name) {
                    ArchiveModelScreen(
                        modifier = Modifier,
                        render = { spell -> SpellRenderer(spell = spell) },
                        label = {},
                        getter = HttpService.getSpells,

                        creationForm = { closeFunc ->
                            ModelCreationForm<Spell>(
                                cancel = { closeFunc() },
                                save = { it -> Log.println(Log.INFO, "", it.toString()) })
                        }
                    )
                }
                composable(route = Routes.ARCHIVE_RACES.name) {
                    ArchiveModelScreen(
                        modifier = Modifier,
                        render = { race -> DefaultRenderer(item = race) },
                        label = {},
                        getter = HttpService.getRaces,

                        creationForm = { closeFunc ->
                            ModelCreationForm<Race>(
                                cancel = { closeFunc() },
                                save = { it -> Log.println(Log.INFO, "", it.toString()) })
                        }
                    )
                }
                composable(route = Routes.ARCHIVE_ALIGNMENTS.name) {
                    ArchiveModelScreen(
                        modifier = Modifier,
                        render = { alignment -> DefaultRenderer(item = alignment) },
                        label = {},
                        getter = HttpService.getAlignments,

                        creationForm = { closeFunc ->
                            ModelCreationForm<Alignment>(
                                cancel = { closeFunc() },
                                save = { it -> Log.println(Log.INFO, "", it.toString()) })
                        }
                    )
                }
                composable(route = Routes.ARCHIVE_BACKGROUNDS.name) {
                    ArchiveModelScreen(
                        modifier = Modifier,
                        render = { spell -> DefaultRenderer(item = spell) },
                        label = {},
                        getter = HttpService.getBackgrounds,
                        creationForm = { closeFunc ->
                            ModelCreationForm<Background>(
                                cancel = { closeFunc() },
                                save = { it -> Log.println(Log.INFO, "", it.toString()) })
                        }
                    )
                }
                composable(route = Routes.ARCHIVE_ITEMS.name) {
                    ArchiveModelScreen(
                        modifier = Modifier,
                        render = { item -> ItemRenderer(item = item) },
                        label = {},
                        getter = HttpService.getItems,
                        creationForm = { closeFunc ->
                            ModelCreationForm<Item>(
                                cancel = { closeFunc() },
                                save = { it -> Log.println(Log.INFO, "", it.toString()) })
                        }
                    )
                }

            }

            //HttpClient(Modifier.padding(innerPadding))
        }
    }
}


@Composable
@Preview
fun MainPreview() {
    AppTheme {
        MainScreen(
            auth = Auth(),
            settings = Settings(),
            setSettings = {},
            masterNavController = rememberNavController()
        )
    }
}
