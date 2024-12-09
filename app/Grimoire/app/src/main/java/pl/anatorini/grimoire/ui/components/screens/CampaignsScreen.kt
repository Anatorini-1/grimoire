package pl.anatorini.grimoire.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pl.anatorini.grimoire.models.Campaign
import pl.anatorini.grimoire.models.NamedModel
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.components.archive.ModelCreationForm
import pl.anatorini.grimoire.ui.components.archive.modelRenderers.CampaignRenderer
import pl.anatorini.grimoire.ui.components.screens.archive.ArchiveModelScreen
import pl.anatorini.grimoire.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignsScreen(modifier: Modifier = Modifier, navController: NavController) {

    data class demo(
        override var name: String,
        override var url: String,
        var description: String,
        var rules: String,
    ) : NamedModel

    val scope = rememberCoroutineScope()
    ArchiveModelScreen(
        getter = HttpService.getCampaigns,
        navController = navController,
        creationForm = {
            ModelCreationForm<Campaign>(
                cancel = { it() }) {
                scope.launch { HttpService.postCampaign(it) }
            }
        },
        render = {
            CampaignRenderer(
                instance = it, navController = navController,
            )
        })
}

@Composable
@Preview
fun CampaignsScreenPreview() {
    AppTheme {
        CampaignsScreen(
            Modifier
                .fillMaxSize()
                .background(Color.White), rememberNavController()
        )
    }
}
