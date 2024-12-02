package pl.anatorini.grimoire.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import io.ktor.client.utils.HttpRequestIsReadyForSending
import pl.anatorini.grimoire.models.Alignment
import pl.anatorini.grimoire.models.Background
import pl.anatorini.grimoire.models.CharacterDetail
import pl.anatorini.grimoire.models.CharacterInfo
import pl.anatorini.grimoire.models.ClassResponse
import pl.anatorini.grimoire.models.Race
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.components.common.Center
import pl.anatorini.grimoire.ui.components.common.Loader
import pl.anatorini.grimoire.ui.theme.AppTheme


@Composable
fun CharacterDetailsScreen(
    modifier: Modifier = Modifier,
    url: String? = null,
    instance: CharacterDetail? = null,
    navController: NavController
) {
    var characterDetail by remember { mutableStateOf(instance) }
    var loaded by remember { mutableStateOf(instance != null) }
    LaunchedEffect(key1 = url) {
        url?.let {
            if (characterDetail == null) {
                HttpService.getCharacterDetails(url)?.let {
                    characterDetail = it
                    loaded = true
                }
            }
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp)
    ) {
        if (!loaded) {
            Center {
                Loader(size = 250.dp, strokeWidth = 30.dp)
            }
        } else {
            characterDetail?.let { characterDetail ->
                // Character Name
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                        .clip(
                            RoundedCornerShape(10.dp)
                        )
                        .background(MaterialTheme.colorScheme.primary)
                        .padding(10.dp), horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${characterDetail?.name}",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
                Surface(
                    tonalElevation = 5.dp, shape = RoundedCornerShape(10.dp), modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {

                        Text(text = "Class: ${characterDetail?.classname?.name} Level 1")
                        Text(text = "Background: ${characterDetail.background.name}")
                        Text(text = "Alignment: ${characterDetail.alignment.name}")
                        Text(text = "Race: ${characterDetail.race.name}")
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))


                Surface(
                    tonalElevation = 5.dp, shape = RoundedCornerShape(10.dp), modifier = Modifier
                        .fillMaxWidth()
                        .padding(5.dp)
                ) {

                    Column(
                        modifier = Modifier.padding(10.dp)
                    ) {

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "Experience: ${characterDetail.experience}")
                            Text(text = "Death Saves: ${characterDetail.deathSaveSuccess} / ${characterDetail.deathSaveFailure}")
                        }
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))

                // Background and Alignment
                Spacer(modifier = Modifier.height(8.dp))

                Surface(
                    tonalElevation = 5.dp, shape = RoundedCornerShape(10.dp), modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)
                ) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .padding(10.dp)) {

                        Text(text = "Age: ${characterDetail.info.age}")
                        Text(text = "Height: ${characterDetail.info.height} cm")
                        Text(text = "Weight: ${characterDetail.info.weight} kg")
                        Text(text = "Eyes: ${characterDetail.info.eyes}")
                        Text(text = "Skin: ${characterDetail.info.skin}")
                        Text(text = "Hair: ${characterDetail.info.hair}")
                        Text(text = "Allies & Organizations: ${characterDetail.info.allies_and_orgs}")
                        Text(text = "Appearance: ${characterDetail.info.appearance}")
                        Text(text = "Backstory: ${characterDetail.info.backstory}")
                        Text(text = "Treasure: ${characterDetail.info.treasure}")
                        Text(text = "Additional Features & Traits: ${characterDetail.info.additionalFeaturesAndTraits}")
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))
                Button(modifier= Modifier
                    .padding(10.dp)
                    .fillMaxWidth(), shape = RoundedCornerShape(10.dp),onClick = { /*TODO*/ }) {
                    Row(modifier=Modifier.height(IntrinsicSize.Max).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier=modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                            Text(text = "Inventory", color = MaterialTheme.colorScheme.onPrimary)
                        }
                        Column(modifier=modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                            Icon(Icons.Filled.List, contentDescription = "Items", tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
                Button(modifier= Modifier
                    .padding(10.dp)
                    .fillMaxWidth(), shape = RoundedCornerShape(10.dp),onClick = { /*TODO*/ }) {
                    Row(modifier=Modifier.height(IntrinsicSize.Max).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier=modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                            Text(text = "Spells", color = MaterialTheme.colorScheme.onPrimary)
                        }
                        Column(modifier=modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                            Icon(Icons.Filled.Star, contentDescription = "Items", tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
                Button(modifier= Modifier
                    .padding(10.dp)
                    .fillMaxWidth(), shape = RoundedCornerShape(10.dp),onClick = { /*TODO*/ }) {
                    Row(modifier=Modifier.height(IntrinsicSize.Max).fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                        Column(modifier=modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                            Text(text = "Notes", color = MaterialTheme.colorScheme.onPrimary)
                        }
                        Column(modifier=modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                            Icon(Icons.Filled.Edit, contentDescription = "Items", tint = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }

            }
        }
    }
}


@Composable
@Preview
fun CharacterDetailsScreenPreview() {
    val demoCharacter = CharacterDetail(
        url = "http://localhost:8000/characters/1/",
        player = "http://localhost:8000/users/1/",
        name = "Zbigniew",
        classname = ClassResponse(
            url = "http://localhost:8000/classes/1/",
            name = "Wizard",
            spellcastingAbility = "http://localhost:8000/statistics/4/"
        ),
        caster_info = null,
        experience = 0,
        info = CharacterInfo(
            age = 6,
            height = 12,
            weight = 12,
            eyes = "Brown",
            skin = "Light",
            hair = "Long brown",
            allies_and_orgs = "no friends",
            appearance = "Appearance desc",
            backstory = "Backstory",
            treasure = "No treasure",
            additionalFeaturesAndTraits = "no data"
        ),
        background = Background(
            url = "http://localhost:8000/backgrounds/1/",
            name = "Soldier"
        ),
        alignment = Alignment(
            url = "http://localhost:8000/alignments/1/",
            name = "Lawful Good"
        ),
        race = Race(
            url = "http://localhost:8000/races/1/",
            name = "Human"
        ),
        deathSaveSuccess = 0,
        deathSaveFailure = 0,
        temporaryHitpoint = 0
    )
    AppTheme {
        CharacterDetailsScreen(instance = demoCharacter, navController = rememberNavController())
    }
}
