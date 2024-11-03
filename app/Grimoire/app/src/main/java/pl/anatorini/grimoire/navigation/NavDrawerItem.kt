package pl.anatorini.grimoire.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pl.anatorini.grimoire.ui.theme.AppTheme


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
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.primary,
            selectedIconColor = MaterialTheme.colorScheme.onPrimary,
            selectedTextColor = MaterialTheme.colorScheme.onPrimary,
            unselectedIconColor = MaterialTheme.colorScheme.primary,
            unselectedTextColor = MaterialTheme.colorScheme.primary,
        ),
        icon = {
            Icon(icon, contentDescription = "Nav Home", modifier = Modifier.height(16.dp))
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

@Composable
@Preview
fun Preview() {
    AppTheme {
        NavDrawerItem(
            navController = rememberNavController(),
            route = Routes.HOME,
            icon = Icons.Filled.Add,
            label = "Settings",
            currentRoute = rememberNavController().currentBackStackEntry,
            drawerState = DrawerState(DrawerValue.Open)
        )
    }
}
