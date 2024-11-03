package pl.anatorini.grimoire.ui.components.screens.settings

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@Composable
fun SomeOtherSettingsScreen(modifier: Modifier = Modifier, navController: NavController) {
    Text(text = "Some other screen")
}

@Composable
@Preview
fun SomeOtherSettingsScreenPreview() {
    SomeOtherSettingsScreen(navController = rememberNavController())
}
