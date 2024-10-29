package pl.anatorini.grimoire.httpService

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.runBlocking
import java.net.ConnectException

@Composable
@Preview
fun HttpClient(modifier: Modifier = Modifier) {
    var url by remember { mutableStateOf("http://localhost:8000/piwo/") }
    Row(
        modifier = modifier
            .fillMaxHeight()
            .fillMaxWidth()
    ) {

        Column(modifier = Modifier.fillMaxWidth(), verticalArrangement = Arrangement.Center) {
            TextField(value = url, modifier = Modifier.fillMaxWidth(), onValueChange = {
                url = it
            })
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                Button(onClick = {
                    Log.println(Log.INFO, "[HTTP]", "Requesting $url")
                    val client = io.ktor.client.HttpClient(CIO)
                    runBlocking {
                        try {
                            val res = client.get(url)
                            Log.println(Log.INFO, "[HTTP]", res.bodyAsText())
                        } catch (err: ConnectException) {
                            Log.println(Log.ERROR, "[HTTP]", err.message.orEmpty())
                        }
                    }
                }) {
                    Text(
                        text = "GET"
                    )
                }
            }
        }

    }
}