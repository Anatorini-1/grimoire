package pl.anatorini.grimoire.components.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.screens.auth.auth_tabs
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun RegisterTab(modifier: Modifier = Modifier, navController: NavHostController) {
    var login by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
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
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text(text = "Username") },
                    value = login,
                    onValueChange = { login = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("E-Mail Address") },
                    value = password,
                    onValueChange = { password = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Password") },
                    value = password,
                    onValueChange = { password = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Repeat password") },
                    value = password,
                    onValueChange = { password = it })
            }
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(size = 5.dp),
                    onClick = { /*TODO*/ }) {
                    Text(text = "Register")
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
                    border = BorderStroke(width = 1.dp, color = MaterialTheme.colorScheme.primary),
                    onClick = {
                        navController.navigate(auth_tabs.LOGIN.name)
                    }) {
                    Text(text = "Log-in instead")
                }
            }
        }
    }
}

@Composable
@Preview
fun RegisterTabPreview() {
    AppTheme {
        RegisterTab(
            modifier = Modifier.background(Color.White),
            navController = rememberNavController()
        )
    }
}
