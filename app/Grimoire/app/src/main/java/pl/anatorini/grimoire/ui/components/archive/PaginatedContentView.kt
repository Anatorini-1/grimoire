package pl.anatorini.grimoire.ui.components.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import pl.anatorini.grimoire.ui.theme.AppTheme


data class GetterReturnType<T>(val items: ArrayList<T>, val next: Int?)

@Composable
fun <T> PaginatedContentView(
    modifier: Modifier = Modifier,
    contentPath: String,
    getter: (page: Int?) -> GetterReturnType<T>,
    label: @Composable() () -> Unit,
    render: @Composable() (T) -> Unit,
) {
    var search by remember { mutableStateOf("") }
    var getterReturn by remember { mutableStateOf(getter(0)) }
    var items by remember { mutableStateOf(getterReturn.items) }
    var next by remember { mutableStateOf(getterReturn.next) }
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    label()
                }
                Row(modifier = Modifier.fillMaxWidth()) {
                    OutlinedTextField(
                        label = { Text(text = "Search...") },
                        value = search,
                        onValueChange = { search = it },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Spacer(modifier = Modifier.height(10.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .verticalScroll(scrollState),
                    verticalArrangement = Arrangement.spacedBy(10.dp)

                ) {
                    for (item in items) {
                        render(item)
                    }
                    if (next != -1) {
                        Button(onClick = {
                            val res = getter(next)
                            items.addAll(res.items)
                            next = res.next
                        }, modifier = Modifier.fillMaxWidth()) {
                            Text(text = "Load more...")
                        }
                    }
                }
            }
        }

    }
}

@Composable
@Preview
fun PaginatedContentViewPreview() {
    AppTheme {
        PaginatedContentView<String>(
            modifier = Modifier.background(Color.White),
            contentPath = "",
            getter = { page ->
                var nums: ArrayList<String> = arrayListOf()
                for (i in (page ?: 0)..(page ?: 0) + 20) {
                    nums.add("$i")
                }
                GetterReturnType(nums, (page ?: 0) + 21)
            },
            render = { str ->
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .border(
                            color = MaterialTheme.colorScheme.primary,
                            width = 3.dp,
                            shape = RoundedCornerShape(size = 5.dp)
                        )
                ) {
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(text = str)
                        Text(text = "Lorem")

                    }
                }
            },
            label = { Text(text = "Placeholder") }
        )
    }
}
