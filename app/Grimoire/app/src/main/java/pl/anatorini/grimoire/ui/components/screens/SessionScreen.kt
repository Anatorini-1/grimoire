package pl.anatorini.grimoire.ui.components.screens

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.websocket.Frame
import io.ktor.websocket.readText
import kotlinx.coroutines.launch
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import okio.ByteString.Companion.toByteString
import pl.anatorini.grimoire.models.CampaignForeignField
import pl.anatorini.grimoire.models.CampaignMessage
import pl.anatorini.grimoire.models.CampaignMessageForeignField
import pl.anatorini.grimoire.models.Session
import pl.anatorini.grimoire.models.SessionConnectedPlayer
import pl.anatorini.grimoire.models.WebSocketHandler
import pl.anatorini.grimoire.models.WebSocketMessage
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.components.common.Center
import pl.anatorini.grimoire.ui.components.common.Loader
import pl.anatorini.grimoire.ui.components.sessions.ChatWindow
import pl.anatorini.grimoire.ui.theme.AppTheme
import java.nio.charset.Charset

@Composable
fun SessionScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    url: String,
    initial: Session? = null
) {
    var instance by remember { mutableStateOf(initial ?: Session(url)) }
    var messages = remember { mutableStateListOf<CampaignMessage>() }
    var connected_players = remember { mutableStateListOf<SessionConnectedPlayer>() }

    var view by remember { mutableStateOf("Players") }
    val scope = rememberCoroutineScope()
    var client = HttpClient {
        install(WebSockets)
    }


    var loaded by remember {
        mutableStateOf(initial != null)
    }
    LaunchedEffect(key1 = url) {
        instance = HttpService.getModelInstance(url)
        HttpService.getSessionMessages(instance)?.let {
            for (message in it) {
                messages.add(message)
            }
        }
        launch {
            instance.token?.let {
                HttpService.wssConnect(token = it, handler = object : WebSocketHandler {
                    override val onMessage: suspend (Frame) -> Unit = {
                        when (it) {
                            is Frame.Binary -> {
                                Log.d(
                                    "WSS",
                                    it.data.toByteString().string(Charset.defaultCharset())
                                )
                            }

                            is Frame.Text -> {
                                Log.d("WSS", it.readText())
                                val command: WebSocketMessage =
                                    Json.decodeFromString(it.readText())
                                when (command.type) {
                                    "chat_message" -> {
                                        Log.d("WSS", "Chat message $${command.payload}")
                                        val m: CampaignMessage =
                                            Json.decodeFromString(command.payload)
                                        messages.add(m)
                                    }

                                    "player_connected" -> {
                                        Log.d("WSS", "Player Connected  $${command.payload}")
                                        val m: String = command.payload
                                        Log.d("WSS", m)
                                    }

                                    "player_disconnected" -> {
                                        Log.d("WSS", "Player disconnected $${command.payload}")
                                        val m: String = command.payload
                                        Log.d("WSS", m)
                                    }

                                    else -> {

                                        Log.e("WSS", "Unsupported message")
                                        Log.e("WSS", command.type)
                                        Log.e("WSS", command.payload)
                                    }

                                }
                            }

                            is Frame.Close -> {

                                Log.d("WSS", "Close")
                            }

                            else -> {
                                Log.e("WSS", "Unsupported frame type")
                            }
                        }
                    }
                    override val onDisconnected: suspend () -> Unit = {

                        Log.e("WSS", "Disconnected")
                    }
                    override val onConnected: suspend () -> Unit = {
                        HttpService.getSessionConnectedPlayers(instance)?.let {
                            for (player in it) {
                                connected_players.add(player)
                            }
                        }
                    }
                    override val onError: suspend () -> Unit = {}
                })
            }
        }

        loaded = true

    }
    if (loaded) {
        Column(
            modifier = modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        MaterialTheme.colorScheme.primary
                    )
                    .padding(10.dp)
                    .height(IntrinsicSize.Max)
            ) {
                Center {

                    Text(text = "Session", color = MaterialTheme.colorScheme.onPrimary)
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                for (viewKey in listOf("Players", "Chat", "Combat")) {
                    var modifier: Modifier = Modifier
                        .weight(1f)
                        .padding(3.dp)
                    var colors: ButtonColors
                    if (view != viewKey) {
                        modifier = modifier
                            .border(
                                width = 3.dp,
                                color = MaterialTheme.colorScheme.primary,
                                shape = RoundedCornerShape(10.dp)
                            )
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onSecondary,
                            containerColor = MaterialTheme.colorScheme.secondary
                        )

                    } else {
                        modifier = modifier
                        colors = ButtonDefaults.buttonColors(
                            contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                            containerColor = MaterialTheme.colorScheme.primary
                        )
                    }
                    Button(modifier = modifier,
                        colors = colors,
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            view = viewKey
                        }) {
                        Text(text = viewKey)
                    }
                }
            }
            Row {
                Text(text = "Messages: ${messages.size}")
            }

            Row {
                Text(text = "Players: ${connected_players.size}")
            }
            when (view) {
                "Players" -> {
                    Column {

                        connected_players.forEach { player ->
                            Log.d("Dupa", Json.encodeToString(player.player))
                            Row {
                                Text(text = player.player.player?.name ?: "Failed to get")
                            }
                        }
                    }
                }

                "Chat" -> {
                    ChatWindow(sendMessage = {
                        scope.launch {
                            HttpService.sendChatMessage(instance.url, it)
                        }
                    }, messages = messages)
                }

                "Combat" -> {}
                else -> {}
            }
//            Text(text = instance.toString())
        }
    } else {
        Center {
            Loader(size = 250.dp, strokeWidth = 30.dp)
        }
    }
}

@Composable
@Preview
fun SessionScreenPreview() {
    AppTheme {
        SessionScreen(
            modifier = Modifier.background(MaterialTheme.colorScheme.background),
            navController = rememberNavController(), url = "", initial = Session(
                url = "",
                active = true,
                campaign = CampaignForeignField(""),
                messages = listOf(
                    CampaignMessageForeignField(""),
                    CampaignMessageForeignField(""),
                    CampaignMessageForeignField(""),
                )
            )
        )
    }
}
