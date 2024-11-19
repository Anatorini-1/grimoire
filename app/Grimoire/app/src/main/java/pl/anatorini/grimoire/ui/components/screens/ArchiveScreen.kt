package pl.anatorini.grimoire.ui.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.navigation.Routes
import pl.anatorini.grimoire.ui.components.archive.ArchiveLink
import pl.anatorini.grimoire.ui.components.scaffold.TopBar
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun ArchiveScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    Column(modifier = modifier.fillMaxWidth()) {
        ArchiveLink(
            name = "Classes",
            route = Routes.ARCHIVE_CLASSES.name,
            navController = navController,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "archive classes",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
        ArchiveLink(
            name = "Spells",
            route = Routes.ARCHIVE_SPELLS.name,
            navController = navController,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "archive classes",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
        ArchiveLink(
            name = "Races",
            route = Routes.ARCHIVE_RACES.name,
            navController = navController,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "archive classes",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
        ArchiveLink(
            name = "Alignments",
            route = Routes.ARCHIVE_ALIGNMENTS.name,
            navController = navController,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "archive classes",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
        ArchiveLink(
            name = "Backgrounds",
            route = Routes.ARCHIVE_BACKGROUNDS.name,
            navController = navController,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "archive classes",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
        ArchiveLink(
            name = "Items",
            route = Routes.ARCHIVE_ITEMS.name,
            navController = navController,
            icon = {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = "archive classes",
                    tint = MaterialTheme.colorScheme.onPrimaryContainer
                )
            }
        )
    }

}


@Composable
@Preview
fun ArchiveScreenPreview() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    AppTheme {
        Scaffold(topBar = { TopBar(navController = navController, drawerState = drawerState) }) {
            ArchiveScreen(modifier = Modifier.padding(it), navController = navController)
        }
    }
}
