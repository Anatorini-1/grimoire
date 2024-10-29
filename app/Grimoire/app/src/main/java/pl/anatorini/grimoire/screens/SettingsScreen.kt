package pl.anatorini.grimoire.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.anatorini.grimoire.state.Settings
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
@Composable
fun SettingsScreen(modifier: Modifier = Modifier, settings: Settings, setSettings: (Settings) -> Unit) {
    var serverAddress by remember { mutableStateOf(settings.serverAddress)}
    var port by remember { mutableStateOf(settings.port)}

    fun save(){
        setSettings(Settings(serverAddress,port))
    }
    Column(modifier = modifier.fillMaxSize()) {
        Row(modifier = Modifier.fillMaxWidth()) {
            TextField(label = { Text(text = "Server Address")}, value = serverAddress, onValueChange = {
                serverAddress = it
            })
        }
        Row(modifier = Modifier.fillMaxWidth()) {

            TextField(label = { Text(text = "Server Port: ")},value = port, onValueChange = {
                port = it
            })
        }
    }
}


@Composable
@Preview
fun SettingsScreenPreview() {
    val settings by remember {
        mutableStateOf(Settings())
    }
    SettingsScreen(modifier = Modifier, settings = settings, setSettings = {s -> })
}