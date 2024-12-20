package pl.anatorini.grimoire.ui.components.scaffold

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun BottomBar(navController: NavController) {
    BottomAppBar(
        actions = {
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { /*TODO*/ },
                shape = CircleShape,
                containerColor = MaterialTheme.colorScheme.tertiary
            ) {
                Icon(Icons.Filled.Add, contentDescription = "")

            }
        },

        )
}


@Composable
@Preview
fun BottomBarPreview() {
    val navController = rememberNavController()
    AppTheme {
        BottomBar(navController)
    }
}