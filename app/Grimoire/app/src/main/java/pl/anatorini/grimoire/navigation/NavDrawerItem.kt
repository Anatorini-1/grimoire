package pl.anatorini.grimoire.navigation

import androidx.compose.material3.DrawerState
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun NavDrawerItem(
    navController: NavController,
    route: Routes,
    icon: ImageVector,
    label: String,
    currentRoute: NavBackStackEntry?,
    drawerState: DrawerState
) {
    val scope = rememberCoroutineScope()
    NavigationDrawerItem(
        icon = {
            Icon(icon, contentDescription = "Nav Home")
        },
        label = {
            Text(text = label)
        },

        selected = currentRoute?.destination?.route == route.name,
        onClick = {
            navController.navigate(route.name)
            scope.launch {
                drawerState.close()
            }
        }
    )
}
