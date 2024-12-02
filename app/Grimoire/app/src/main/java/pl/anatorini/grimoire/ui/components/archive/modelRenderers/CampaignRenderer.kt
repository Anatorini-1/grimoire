package pl.anatorini.grimoire.ui.components.archive.modelRenderers

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
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
import pl.anatorini.grimoire.models.Character
import pl.anatorini.grimoire.models.CharacterForeignField
import pl.anatorini.grimoire.models.Player
import pl.anatorini.grimoire.navigation.CampaignRoute
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.components.forms.ForeignSelect
import pl.anatorini.grimoire.ui.theme.AppTheme


@Composable
fun CampaignRenderer(
    modifier: Modifier = Modifier,
    instance: Campaign,
    navController: NavController
) {
    var expanded by remember { mutableStateOf(false) }
    val scope = rememberCoroutineScope()
    var selected_character by remember { mutableStateOf("") }

    var characters by remember { mutableStateOf<List<Character>>(emptyList()) }
    var rowKey by remember {
        mutableStateOf("init")
    }
    val context = LocalContext.current
    var reactiveInstance by remember {
        mutableStateOf(
            Campaign(
                players = instance.players,
                sessions = instance.sessions,
                url = instance.url,
                dm = instance.dm,
                name = instance.name,
                accepted = instance.accepted
            )
        )
    }
    LaunchedEffect(key1 = "") {
        rowKey = reactiveInstance.toString()
        characters = CharacterForeignField("").getValues()
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
                    if (reactiveInstance.accepted || (reactiveInstance.dm?.name
                            ?: "0") == (HttpService.user?.username ?: "-1")
                    )
                        navController.navigate(CampaignRoute(instance.url))
                    else {
                        expanded = !expanded
                    }
                }
                .padding(10.dp)
        ) {
            key(rowKey) {
                Text(text = reactiveInstance.name, color = MaterialTheme.colorScheme.onPrimary)
                if ((reactiveInstance.dm?.name ?: "0") == (HttpService.user?.username ?: "-1")) {
                    Icon(
                        Icons.Filled.Star,
                        contentDescription = "Star",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                } else if (!reactiveInstance.accepted) {
                    Icon(
                        Icons.Filled.MailOutline,
                        contentDescription = "Accept",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }
        }
        if (expanded) {
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
                    ForeignSelect(items = characters, setItem = {
                        selected_character = it.url
                    }, label = "Character")
                    Button(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(10.dp),
                        onClick = {
                            scope.launch {
                                HttpService.acceptCampaignInvite(
                                    reactiveInstance,
                                    selected_character
                                )
                                Toast.makeText(
                                    context,
                                    "Joined a campaign!",
                                    Toast.LENGTH_LONG
                                ).show()
                                navController.navigate(CampaignRoute(instance.url))
                            }
                        }) {
                        Text(text = "Join")
                    }
                }
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
            dm = Player(url = "http://localhost:8000/users/1/", name = "Test", email = ""),
            players = emptyList()
        )
        CampaignRenderer(
            modifier = Modifier.fillMaxWidth(),
            instance = campaign,
            navController = rememberNavController()
        )
    }
}
