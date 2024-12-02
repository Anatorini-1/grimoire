package pl.anatorini.grimoire.ui.components.forms

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import pl.anatorini.grimoire.models.Alignment
import pl.anatorini.grimoire.models.AlignmentForeignField
import pl.anatorini.grimoire.models.Background
import pl.anatorini.grimoire.models.BackgroundForeignField
import pl.anatorini.grimoire.models.CharacterClass
import pl.anatorini.grimoire.models.CharacterInfo
import pl.anatorini.grimoire.models.ClassForeignField
import pl.anatorini.grimoire.models.NamedModel
import pl.anatorini.grimoire.models.PostCharacter
import pl.anatorini.grimoire.models.Race
import pl.anatorini.grimoire.models.RaceForeignField
import pl.anatorini.grimoire.services.HttpService
import pl.anatorini.grimoire.ui.theme.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CharacterCreationForm(modifier: Modifier = Modifier, closeFunc: () -> Unit) {

    val context = LocalContext.current

    var name by remember {
        mutableStateOf("")
    }
    var classname by remember { mutableStateOf(ClassForeignField("")) }
    var background by remember { mutableStateOf(BackgroundForeignField("")) }
    var alignment by remember { mutableStateOf(AlignmentForeignField("")) }
    var race by remember { mutableStateOf(RaceForeignField("")) }
    var info: CharacterInfo

    var age by remember { mutableStateOf("10") }
    var height by remember { mutableStateOf("165") }
    var weight by remember { mutableStateOf("80") }
    var eyes by remember { mutableStateOf("Brown") }
    var skin by remember { mutableStateOf("Light") }
    var hair by remember { mutableStateOf("Bald") }
    var alliesAndOrgs by remember { mutableStateOf("No friends") }
    var appearance by remember { mutableStateOf("Ugly as hell") }
    var backstory by remember { mutableStateOf("My character backstory") }
    var treasure by remember { mutableStateOf("Poor") }
    var additionalFeaturesAndTraits by remember { mutableStateOf("No additional traits") }

    var classNames: List<CharacterClass> by remember { mutableStateOf(emptyList()) }
    var backgrounds: List<Background> by remember { mutableStateOf(emptyList()) }
    var alignments: List<Alignment> by remember { mutableStateOf(emptyList()) }
    var races: List<Race> by remember { mutableStateOf(emptyList()) }

    var expanded1 by remember { mutableStateOf(false) }
    var expanded2 by remember { mutableStateOf(false) }
    var expanded3 by remember { mutableStateOf(false) }
    var expanded4 by remember { mutableStateOf(false) }

    var selected1 by remember { mutableStateOf("") }
    var selected2 by remember { mutableStateOf("") }
    var selected3 by remember { mutableStateOf("") }
    var selected4 by remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(key1 = "") {
        classNames = classname.getValues()
        backgrounds = background.getValues()
        alignments = alignment.getValues()
        races = race.getValues()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .verticalScroll(scrollState)
            .padding(5.dp)

    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(3.dp)
                .clip(RoundedCornerShape(10.dp))
                .border(
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(10.dp),
                    width = 2.dp
                ),

            horizontalArrangement = Arrangement.Center

        ) {
            Text(
                text = "New Character",
                fontSize = MaterialTheme.typography.headlineSmall.fontSize,
                fontStyle = MaterialTheme.typography.headlineLarge.fontStyle
            )

        }

        OutlinedTextField(value = name,
            onValueChange = { name = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(5.dp),
            colors = TextFieldDefaults.outlinedTextFieldColors(),
            label = { Text(text = "Name") }

        )
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(onClick = {
                val characterInfo = CharacterInfo(
                    age = age.toIntOrNull() ?: 0,
                    height = height.toIntOrNull() ?: 0,
                    weight = weight.toIntOrNull() ?: 0,
                    eyes = eyes,
                    skin = skin,
                    hair = hair,
                    allies_and_orgs = alliesAndOrgs,
                    appearance = appearance,
                    backstory = backstory,
                    treasure = treasure,
                    additionalFeaturesAndTraits =  additionalFeaturesAndTraits
                )

                val postCharacter = PostCharacter(
                    name = name,
                    classname = classname.url,
                    background = background.url,
                    alignment = alignment.url,
                    race = race.url,
                    info = characterInfo
                )
                scope.launch {
                    try{
                        HttpService.postCharacter(postCharacter)
                            Toast.makeText(
                                context,
                                "Created",
                                Toast.LENGTH_LONG
                            ).show()
                            closeFunc()
                    }
                    catch (e:Exception){
                        Log.e("CharacterCreation", e.message?:"No message")
                        Toast.makeText(
                            context,
                            "Failed",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    }

            }) {
                Text(text = "Create")
            }
        }
        Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(onClick = {closeFunc()}) {
                Text(text = "Cancel")
            }
        }
        ForeignSelect(
            items = classNames,
            setItem = { item ->
                classname = ClassForeignField(item.url)
            },
            label = "Class"
        )
        ForeignSelect(
            items = races,
            setItem = { item ->
                race = RaceForeignField(item.url)
            },
            label = "Race"
        )
        ForeignSelect(
            items = alignments,
            setItem = { item ->
                alignment = AlignmentForeignField(item.url)
            },
            label = "Alignment"
        )
        ForeignSelect(
            items = backgrounds,
            setItem = { item ->
                background = BackgroundForeignField(item.url)
            },
            label = "Background"
        )
        OutlinedTextField(
            value = age,
            onValueChange = { age = it },
            label = { Text("Age") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = height,
            onValueChange = { height = it },
            label = { Text("Height (cm)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = weight,
            onValueChange = { weight = it },
            label = { Text("Weight (kg)") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = eyes,
            onValueChange = { eyes = it },
            label = { Text("Eyes") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = skin,
            onValueChange = { skin = it },
            label = { Text("Skin") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = hair,
            onValueChange = { hair = it },
            label = { Text("Hair") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = alliesAndOrgs,
            onValueChange = { alliesAndOrgs = it },
            label = { Text("Allies and Organizations") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = appearance,
            onValueChange = { appearance = it },
            label = { Text("Appearance") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = backstory,
            onValueChange = { backstory = it },
            label = { Text("Backstory") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = treasure,
            onValueChange = { treasure = it },
            label = { Text("Treasure") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )

        OutlinedTextField(
            value = additionalFeaturesAndTraits,
            onValueChange = { additionalFeaturesAndTraits = it },
            label = { Text("Additional Features & Traits") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 8.dp)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : NamedModel> ForeignSelect(
    modifier: Modifier = Modifier,
    items: List<T>,
    crossinline setItem: (T) -> Unit,
    label: String
) {
    var expanded1 by remember { mutableStateOf(false) }
    var selected1 by remember { mutableStateOf("") }

    ExposedDropdownMenuBox(
        modifier = Modifier
            .fillMaxWidth()
            .clip(
                RoundedCornerShape(10.dp)
            ),
        expanded = expanded1,
        onExpandedChange = {
            expanded1 = !expanded1
        }
    ) {
        TextField(
            value = selected1 ?: "",
            onValueChange = {
                selected1 = it

            },
            shape = RoundedCornerShape(10.dp),
            readOnly = true,
            label = {
                Text(text = label)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded1
                )
            },
            modifier = Modifier
                .menuAnchor()
                .padding(3.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.background,
                focusedTextColor = MaterialTheme.colorScheme.onBackground,
                unfocusedContainerColor = MaterialTheme.colorScheme.background,
                unfocusedTextColor = MaterialTheme.colorScheme.onBackground,

                ),

            )

        ExposedDropdownMenu(
            expanded = expanded1,
            onDismissRequest = { expanded1 = false }
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = { Text(text = item.name) },
                    onClick = {
                        expanded1 = false
                        selected1 = item.name
                        setItem(item)
                    }
                )
            }
        }
    }
}


@Composable
@Preview
fun CharacterCreationFormPreview() {
    AppTheme {

        CharacterCreationForm(closeFunc = {})
    }
}


@Composable
fun ForeignFieldSelect() {

}