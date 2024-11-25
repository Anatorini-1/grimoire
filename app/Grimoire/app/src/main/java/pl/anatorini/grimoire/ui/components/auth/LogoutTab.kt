package pl.anatorini.grimoire.ui.components.auth

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Brands
import compose.icons.fontawesomeicons.brands.Google
import kotlinx.coroutines.launch
import pl.anatorini.grimoire.models.User
import pl.anatorini.grimoire.navigation.Routes
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.components.screens.auth.auth_tabs
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun LogoutTab(modifier: Modifier = Modifier, navController: NavHostController) {
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Icon(imageVector = Icons.Filled.AccountCircle, contentDescription = "", modifier=Modifier.size(160.dp))
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text("Logged in as ${HttpService.user?.username}", fontWeight = FontWeight.Bold)
                }
            }
        }
        Column(Modifier.fillMaxWidth(), verticalArrangement = Arrangement.spacedBy(30.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(0.dp), horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    shape = RoundedCornerShape(5.dp),
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.primary,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
                    onClick = {
                        scope.launch{
                            HttpService.logout()
                            navController.navigate(Routes.HOME.name)
                            Toast.makeText(
                                context,
                                "Logged out",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }) {
                            Text("Logout")
                }
            }
        }
    }
}

@Composable
@Preview
fun LogoutTabPreview() {
    HttpService.user = User("Anatorini","token")
    AppTheme {
        LogoutTab(
            modifier = Modifier.background(Color.White),
            navController = rememberNavController()
        )
    }
}
