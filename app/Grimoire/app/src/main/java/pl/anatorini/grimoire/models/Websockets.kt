package pl.anatorini.grimoire.models

import io.ktor.websocket.Frame
import kotlinx.serialization.Serializable

@Serializable
data class WebsocketCommand(
    val type: String,
    val payload: String,
)

@Serializable
data class WebSocketMessage(
    val type: String,
    val payload: String
)

interface WebSocketHandler {
    val onMessage: suspend (Frame) -> Unit
    val onConnected: suspend () -> Unit
    val onDisconnected: suspend () -> Unit
    val onError: suspend () -> Unit
}