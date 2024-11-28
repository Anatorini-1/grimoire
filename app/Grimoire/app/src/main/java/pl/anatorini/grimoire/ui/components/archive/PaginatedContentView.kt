package pl.anatorini.grimoire.ui.components.archive

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import pl.anatorini.grimoire.models.NamedModel
import pl.anatorini.grimoire.models.PaginatedResponse
import pl.anatorini.grimoire.ui.components.archive.modelRenderers.DefaultRenderer
import pl.anatorini.grimoire.ui.components.common.Center
import pl.anatorini.grimoire.ui.components.common.Loader
import pl.anatorini.grimoire.ui.theme.AppTheme


@Composable
fun <T : NamedModel> PaginatedContentView(
    modifier: Modifier = Modifier,
    getter: suspend (String?) -> PaginatedResponse<T>,
    label: @Composable() () -> Unit = { },
    render: @Composable() (T) -> Unit = { item -> DefaultRenderer(item = item) }
) {
    var search by remember { mutableStateOf("") }
    val pages: SnapshotStateList<PaginatedResponse<T>> = remember { mutableStateListOf() }
    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()
    var isError by remember { mutableStateOf(false) }
    val safeGetter: suspend (String?) -> Unit = {
        try {
            pages.add(getter(it))
        } catch (e: Exception) {
            Log.println(Log.ERROR, "PaginatedContentView", "Failed to load content")
            Log.println(Log.ERROR, "PaginatedContentView", e.message.orEmpty())
            isError = true
        }
    }

    if (pages.size == 0) {
        LaunchedEffect(key1 = label) {
            safeGetter(null)
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(10.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        if (isError) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .defaultMinSize(minHeight = 50.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(MaterialTheme.colorScheme.errorContainer)
                        .padding(10.dp)
                ) {
                    Column(Modifier.fillMaxWidth()) {
                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(intrinsicSize = IntrinsicSize.Max)
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Info,
                                    contentDescription = "error info",
                                    tint = MaterialTheme.colorScheme.onErrorContainer,
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                            }
                            Column(
                                verticalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxHeight()
                            ) {
                                Text(
                                    text = "Failed to load data!",
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                        Row(Modifier.fillMaxWidth(), Arrangement.Center) {
                            Button(
                                onClick = {
                                    scope.launch {
                                        isError = false
                                        if (pages.size == 0)
                                            safeGetter(null)
                                        else
                                            safeGetter(pages.last().next)
                                    }
                                }, colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.onErrorContainer,
                                    contentColor = MaterialTheme.colorScheme.errorContainer
                                ),
                                modifier = Modifier.padding(vertical = 20.dp)
                            ) {
                                Text(text = "Try Again")
                            }
                        }
                    }

                }
            }
        } else {
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
                        for (page in pages) {
                            for (item in page.results) {
                                render(item)
                            }
                        }
                        if (!pages.isEmpty() && pages.last().next != null) {
                            Button(onClick = {
                                runBlocking {
                                    safeGetter(pages.last().next)
                                }
                            }, modifier = Modifier.fillMaxWidth()) {
                                Text(text = "Load more...")
                            }
                        }
                    }
                }
            }
        }
        if (pages.isEmpty() && !isError) {
            Center {
                Loader(size = 250.dp, strokeWidth = 30.dp)
            }
        }
    }

}

@Composable
@Preview
fun PaginatedContentViewPreview() {
    data class Tmp(
        override val name: String,
        override val url: String = ""
    ) : NamedModel
    AppTheme {
        PaginatedContentView<Tmp>(
            modifier = Modifier.background(Color.White),
            getter = { page ->
                delay(10000)
                val nums: ArrayList<Tmp> = arrayListOf()
                for (i in (page?.toInt() ?: 0)..(page?.toInt() ?: 0) + 20) {
                    nums.add(Tmp(i.toString()))
                }
                val res: PaginatedResponse<Tmp> = PaginatedResponse<Tmp>(
                    previous = null,
                    next = ((page?.toInt()?.plus(20)).toString()),
                    count = 20,
                    results = nums
                )
                res
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
                        Text(text = str.name)
                        Text(text = "Lorem")

                    }
                }
            },
            label = { Text(text = "Placeholder") }
        )
    }
}






