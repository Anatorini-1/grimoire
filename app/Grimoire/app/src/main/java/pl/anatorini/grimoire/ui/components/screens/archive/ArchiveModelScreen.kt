package pl.anatorini.grimoire.ui.components.screens.archive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.models.Model
import pl.anatorini.grimoire.models.PaginatedResponse
import pl.anatorini.grimoire.navigation.Routes
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.components.archive.PaginatedContentView
import pl.anatorini.grimoire.ui.components.archive.modelRenderers.DefaultRenderer
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun <T : Model> ArchiveModelScreen(
    modifier: Modifier = Modifier,
    getter: suspend (String?) -> PaginatedResponse<T>,
    label: @Composable() () -> Unit = { },
    render: @Composable() (T) -> Unit = { item -> DefaultRenderer(item = item) },
    creationForm: @Composable() (closeFunc: () -> Unit) -> Unit = {},
    navController: NavController
) {
    var showForm by remember { mutableStateOf(false) }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if(HttpService.user != null){
                    showForm = true;
                }
                else{
                    navController.navigate(Routes.AUTH.name)
                }
            }) {
                Icon(imageVector = Icons.Filled.Add, contentDescription = "add")
            }
        }
    ) { innerPadding ->
        if (showForm)
            creationForm({ showForm = false })
        else
            PaginatedContentView(
                modifier = Modifier.padding(innerPadding),
                getter = getter,
                label = label,
                render = render
            )
    }
}

@Composable
@Preview
fun ArchiveModelScreenPreview() {
    data class MockModel(
        override val name: String,
        override val url: String = ""
    ) : Model

    val getter: (String?) -> PaginatedResponse<MockModel> = {
        PaginatedResponse<MockModel>(
            next = null,
            previous = null,
            count = 1,
            results = arrayListOf(MockModel("dupa"))
        )
    }
    AppTheme {
        Column(
            Modifier
                .fillMaxWidth()
                .background(Color.White)
        ) {
            Text(text = "Dupa")
            ArchiveModelScreen(getter = getter, modifier = Modifier, label = {}, navController = rememberNavController())
        }
    }
}
