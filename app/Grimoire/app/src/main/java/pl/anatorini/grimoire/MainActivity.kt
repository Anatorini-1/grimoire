package pl.anatorini.grimoire

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.NavController
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.theme.AppTheme

class MainActivity : ComponentActivity() {
    lateinit var httpService: HttpService
    lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                App()
            }
        }
    }
}
