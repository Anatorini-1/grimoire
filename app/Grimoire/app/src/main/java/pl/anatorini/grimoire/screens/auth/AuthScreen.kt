package pl.anatorini.grimoire.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.components.auth.LoginTab
import pl.anatorini.grimoire.components.auth.RegisterTab
import pl.anatorini.grimoire.ui.theme.AppTheme


enum class auth_tabs {
    LOGIN,
    REGISTER
}

@Composable
fun AuthScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val tab by remember { mutableStateOf(auth_tabs.LOGIN) }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(0.dp)
    ) {
        NavHost(navController = navController, startDestination = auth_tabs.LOGIN.name) {
            composable(auth_tabs.LOGIN.name) { LoginTab(navController = navController) }
            composable(auth_tabs.REGISTER.name) { RegisterTab(navController = navController) }
        }
    }
}

@Composable
@Preview
fun AuthScreenPreview() {
    AppTheme {
        AuthScreen(modifier = Modifier.background(Color.White))
    }
}
