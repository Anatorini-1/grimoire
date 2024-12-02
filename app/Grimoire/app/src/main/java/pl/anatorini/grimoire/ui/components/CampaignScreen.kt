package pl.anatorini.grimoire.ui.components

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.launch
import pl.anatorini.grimoire.models.Campaign
import pl.anatorini.grimoire.models.CampaignPlayer
import pl.anatorini.grimoire.models.Player
import pl.anatorini.grimoire.models.PlayerForeignField
import pl.anatorini.grimoire.models.Session
import pl.anatorini.grimoire.navigation.SessionRoute
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.components.common.Center
import pl.anatorini.grimoire.ui.components.common.Loader
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun CampaignScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    url: String,
    initial: Campaign? = null
) {
    var view by remember { mutableStateOf("Players") }
    var playersLoaded by remember { mutableStateOf(true) }
    var sessionsLoaded by remember { mutableStateOf(false) }
    var showInviteForm by remember { mutableStateOf(false) }
    var playersKey by remember { mutableStateOf("") }
    var instance by remember { mutableStateOf(initial ?: Campaign(url = url)) }
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = url) {
        instance = HttpService.getModelInstance(instance.url)
        playersKey = instance.players.toString()
        playersLoaded = true
        for (session in instance.sessions) {
        }
        sessionsLoaded = true
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            for (viewKey in listOf("Players", "Sessions", "Materials")) {
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
            when (view) {
                "Players" -> {
                    if (playersLoaded) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            key(playersKey) {

                                for (player in instance.players) {
                                    PlayerRow(instance = player)
                                }
                            }

                            if ((instance.dm?.name ?: "0") == (HttpService.user?.username
                                    ?: "-1")
                            ) {

                                Button(
                                    onClick = {
                                        showInviteForm = !showInviteForm
                                    }, modifier = Modifier
                                        .padding(3.dp)
                                        .fillMaxWidth(), shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary
                                    )

                                ) {
                                    Text(text = "Invite player")
                                }
                            }
                            if (showInviteForm) {

                                InviteForm(instance = instance, addPlayer = {
                                    val l: MutableList<CampaignPlayer> = mutableListOf()
                                    for (player in instance.players) {
                                        l.add(player)
                                    }
                                    l.add(
                                        it
                                    )
                                    instance = Campaign(
                                        url = instance.url,
                                        dm = instance.dm,
                                        name = instance.name,
                                        sessions = instance.sessions,
                                        players = l
                                    )
                                    instance.players = l
                                    playersKey = instance.players.toString()
                                })
                            }
                        }
                    } else {
                        Center {
                            Loader(size = 250.dp, strokeWidth = 30.dp)
                        }
                    }
                }

                "Sessions" -> {
                    if (sessionsLoaded) {
                        Column(modifier = Modifier.fillMaxWidth()) {

                            for (session in instance.sessions) {
                                session?.let {
                                    SessionRow(
                                        navController = navController,
                                        instance = it
                                    )
                                }
                            }

                            if ((instance.dm?.name ?: "0") == (HttpService.user?.username
                                    ?: "-1")
                            ) {
                                Button(
                                    onClick = {
                                        scope.launch {
                                            HttpService.startCampaignSession(instance)
                                        }
                                    }, modifier = Modifier
                                        .padding(3.dp)
                                        .fillMaxWidth(), shape = RoundedCornerShape(10.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = MaterialTheme.colorScheme.tertiary
                                    )

                                ) {
                                    Text(text = "New session")
                                }
                            }
                        }
                    } else {

                        Center {
                            Loader(size = 250.dp, strokeWidth = 30.dp)
                        }

                    }
                }

                "Materials" -> {

                }

                else -> {}
            }
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InviteForm(instance: Campaign, addPlayer: (CampaignPlayer) -> Unit) {
    val scope = rememberCoroutineScope()
    var player by remember {
        mutableStateOf<PlayerForeignField>(PlayerForeignField(""))
    }
    val context = LocalContext.current
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    var players by remember {
        mutableStateOf<List<Player>>(
            emptyList()
        )
    }
    LaunchedEffect(key1 = "") {
        players = player.getValues()
    }
    Row(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.fillMaxWidth()) {

            ExposedDropdownMenuBox(
                modifier = Modifier.fillMaxWidth(),
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                TextField(
                    value = selectedText ?: "",
                    onValueChange = {
                        player.url = it

                    },
                    readOnly = true,
                    label = { Text(text = "Player") },
                    trailingIcon = {
                        ExposedDropdownMenuDefaults.TrailingIcon(
                            expanded = expanded
                        )
                    },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        unfocusedContainerColor = MaterialTheme.colorScheme.secondary,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSecondary,
                        focusedContainerColor = MaterialTheme.colorScheme.secondary,
                        focusedTextColor = MaterialTheme.colorScheme.onSecondary,
                    ),

                    )

                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    players.forEach { item ->
                        DropdownMenuItem(
                            text = { Text(text = item.name) },
                            onClick = {
                                player.url = item.url
                                selectedText = item.name
                                expanded = false
                            }
                        )
                    }
                }
            }

            Row {
                OutlinedButton(
                    onClick = {
                        scope.launch {
                            val new_player = HttpService.createcampaignInvite(instance, player.url)
                            if (new_player != null) {
                                Toast.makeText(
                                    context,
                                    "Invited!",
                                    Toast.LENGTH_LONG
                                ).show()
                                addPlayer(new_player)
                            } else {
                                Toast.makeText(
                                    context,
                                    "Unauthorized!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.scrim,
                        contentColor = MaterialTheme.colorScheme.onPrimary
                    ),
                    border = null,
                    modifier = Modifier
                        .padding(3.dp)
                        .weight(1f), shape = RoundedCornerShape(10.dp)
                ) {
                    Text(text = "Invite")
                }
            }
        }
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
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(3.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(if (reactiveInstance.accepted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.tertiary)
            .padding(10.dp), horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = reactiveInstance.player?.name ?: "Could not GET",
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

@Preview
fun CampaignScreenPreview() {
    AppTheme {
        CampaignScreen(modifier = Modifier, navController = rememberNavController(), url = "")
    }
}
