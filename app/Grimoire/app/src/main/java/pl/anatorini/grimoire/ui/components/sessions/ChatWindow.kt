package pl.anatorini.grimoire.ui.components.sessions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.anatorini.grimoire.models.CampaignMessage
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun ChatWindow(
    modifier: Modifier = Modifier,
    sendMessage: (String) -> Unit,
    messages: List<CampaignMessage>
) {
    var message by remember { mutableStateOf("") }
    var scope = rememberCoroutineScope()
    LaunchedEffect(key1 = messages.toString()) {
        for (m in messages) {

        }
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(3.dp)
            .border(
                width = 3.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp)), verticalArrangement = Arrangement.SpaceBetween
    )
    {
        Row {
            Column {
                for (m in messages) {
                    MessageRow(
                        msg = m.message,
                        user = m.sender?.player?.name ?: "unknown",
                        date = m.created_at
                    )
                }
            }
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Max)
                .padding(5.dp)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,

                ) {
                OutlinedTextField(
                    value = message, onValueChange = { message = it },
                    label = { Text(text = "Message") },
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.background,
                        focusedContainerColor = MaterialTheme.colorScheme.background,

                        ),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier
                    .padding(10.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = {
                        sendMessage(message)
                        message = ""
                    }, colors = ButtonDefaults.buttonColors(
                        contentColor = MaterialTheme.colorScheme.onPrimary,
                        containerColor = MaterialTheme.colorScheme.primary
                    ),
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier.fillMaxHeight()

                ) {
                    Icon(Icons.Filled.Send, contentDescription = "Send")
                }
            }

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessageRow(msg: String, user: String, date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(5.dp)
    ) {
        OutlinedTextField(
            value = msg,
            colors = OutlinedTextFieldDefaults.colors(
                disabledBorderColor = MaterialTheme.colorScheme.primary,
                disabledTextColor = MaterialTheme.colorScheme.primary,
            ),
            modifier = Modifier.fillMaxWidth(),
            onValueChange = {},
            enabled = false,
            label = { Text(text = "$user : $date") })
    }
}

@Preview
@Composable
fun MessageRowPreview() {
    AppTheme {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            MessageRow("Dupa", "Anatorini", "Now")
            MessageRow("Dupa", "Anatorini", "Now")
            MessageRow("Dupa", "Anatorini", "Now")
            MessageRow("Dupa", "Anatorini", "Now")
        }
    }
}

@Composable
@Preview
fun ChatWindowPreview() {
    AppTheme {
        ChatWindow(sendMessage = {}, messages = emptyList())
    }
}
