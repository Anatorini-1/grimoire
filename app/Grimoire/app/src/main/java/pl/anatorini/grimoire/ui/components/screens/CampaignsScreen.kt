package pl.anatorini.grimoire.ui.components.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.components.archive.modelRenderers.CampaignRenderer
import pl.anatorini.grimoire.ui.components.screens.archive.ArchiveModelScreen
import pl.anatorini.grimoire.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CampaignsScreen(modifier: Modifier = Modifier, navController: NavController) {
    ArchiveModelScreen(getter = HttpService.getCampaigns, navController = navController, render = {CampaignRenderer(
        instance = it, navController = navController
    )})
}

@Composable
@Preview
fun CampaignsScreenPreview() {
    AppTheme {
        CampaignsScreen(
            Modifier
                .fillMaxSize()
                .background(Color.White)
            , rememberNavController()
        )
    }
}
