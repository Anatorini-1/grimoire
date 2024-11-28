package pl.anatorini.grimoire.ui.components.archive.modelRenderers

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pl.anatorini.grimoire.models.Campaign
import pl.anatorini.grimoire.models.CampaignPlayer
import pl.anatorini.grimoire.models.CampaignPlayerForeignField
import pl.anatorini.grimoire.models.PlayerForeignField
import pl.anatorini.grimoire.models.Session
import pl.anatorini.grimoire.navigation.SessionRoute
import pl.anatorini.grimoire.ui.theme.AppTheme


@Composable
fun CampaignRenderer(
    modifier: Modifier = Modifier,
    instance: Campaign,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var reactiveInstance by remember {
        mutableStateOf(
            Campaign(
                players = instance.players,
                sessions = instance.sessions,
                url = instance.url,
                dm = instance.dm,
                name = instance.name
            )
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(5.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    scope.launch {
                        for (player in reactiveInstance.players) {
                            player.value = player.getValue()
                            player.value!!.player.value = player.value!!.player.getValue()
                            Log.d("", player.value.toString())
                        }
                        for (session in reactiveInstance.sessions) {
                            session.value = session.getValue()
                            Log.d("", session.value.toString())
                        }
                        expanded = !expanded
                    }
                }
                .padding(10.dp)
        ) {

            Text(text = reactiveInstance.name, color = MaterialTheme.colorScheme.onPrimary)
        }
        if (expanded) {
            LaunchedEffect(key1 = "") {
                for (player in reactiveInstance.players) {
                    player.value = player.getValue()
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
            )
            {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(MaterialTheme.colorScheme.secondary)
                        .padding(10.dp)
                ) {
                    Text(text = "Players")
                    for (player in reactiveInstance.players) {
                        player.value?.let { PlayerRow(instance = it) }
                    }
                    Text(text = "Sessions")
                    for (session in reactiveInstance.sessions) {
                        session.value?.let {
                            SessionRow(
                                instance = session.value!!,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun PlayerRow(instance: CampaignPlayer) {
    var reactiveInstance by remember {
        mutableStateOf(
            CampaignPlayer(
                url = instance.url,
                player = instance.player,
                character = instance.character,
                accepted = instance.accepted,
                campaign = instance.campaign
            )
        )
    }
    LaunchedEffect(key1 = instance.url) {
        reactiveInstance.player.value = reactiveInstance.player.getValue()
    }
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = reactiveInstance.player.value?.name ?: "Could not GET",
            color = MaterialTheme.colorScheme.onPrimary
        )
        Icon(
            Icons.Filled.Person,
            contentDescription = "",
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}


@Composable
fun SessionRow(instance: Session, navController: NavController) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (instance.active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary)
            .clickable {
                navController.navigate(SessionRoute(instance.url))
            }
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween

    ) {
        Column {
            Row(
                Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Text(
                    text = instance.url,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Icon(
                    Icons.Filled.DateRange,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}


@Composable
@Preview
fun SessionRowPreview() {
    AppTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        )
        {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(10.dp)
            ) {
                SessionRow(
                    instance = Session(url = "Dupa", active = true),
                    navController = rememberNavController()
                )
                SessionRow(
                    instance = Session(url = "Dupa2", active = false),
                    navController = rememberNavController()
                )
                SessionRow(instance = Session(url = ""), navController = rememberNavController())
                SessionRow(instance = Session(url = ""), navController = rememberNavController())
            }
        }
    }
}

@Composable
@Preview
fun PLayerRowPreview() {

    AppTheme {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(10.dp))
        )
        {

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.secondary)
                    .padding(10.dp)
            ) {
                PlayerRow(instance = CampaignPlayer(url = ""))
                PlayerRow(instance = CampaignPlayer(url = ""))
                PlayerRow(instance = CampaignPlayer(url = ""))
                PlayerRow(instance = CampaignPlayer(url = ""))
                PlayerRow(instance = CampaignPlayer(url = ""))
            }
        }
    }
}

@Composable
@Preview
fun CampaignRendererPreview() {
    AppTheme {
        val campaign: Campaign = Campaign(
            name = "Campaign",
            url = "http://localhost:8000/campaigns/1/",
            dm = PlayerForeignField("http://localhost:8000/users/1/"),
            players = listOf(
                CampaignPlayerForeignField("http://localhost:8000/campaignPlayers/1/"),
            )
        )
        CampaignRenderer(
            modifier = Modifier.fillMaxWidth(),
            instance = campaign,
            navController = rememberNavController()
        )
    }
}
