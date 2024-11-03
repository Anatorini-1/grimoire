package pl.anatorini.grimoire.ui.components.screens.settings

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.state.Settings
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun EditServerAddressScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    settings: Settings,
    setSettings: (address: String, port: Int) -> Unit
) {
    var serverAddress by remember { mutableStateOf(settings.serverAddress) }
    var serverPort by remember { mutableIntStateOf(settings.port) }
    var addressError by remember { mutableStateOf("") }
    var portError by remember { mutableStateOf("") }
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp),


        ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Address") },
            value = serverAddress, onValueChange = { serverAddress = it })
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Port") },
            isError = portError != "",
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            value = serverPort.toString(), onValueChange = {
                try {
                    serverPort = it.toInt()
                } catch (e: NumberFormatException) {
                    portError = "Port must be a number"
                }

            })
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            if (portError != "")
                Text(text = "Error: $portError", color = MaterialTheme.colorScheme.error)
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Button(onClick = {
                setSettings(serverAddress, serverPort)
                navController.popBackStack()
            }) {
                Text("Save")
            }
        }
    }
}

@Composable
@Preview
fun EditServerAddressScreenPreview() {
    AppTheme {
        EditServerAddressScreen(
            modifier = Modifier.background(Color.White),
            navController = rememberNavController(),
            settings = Settings(),
            setSettings = { server, port -> }
        )
    }
}
