package pl.anatorini.grimoire.ui.components.archive

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.anatorini.grimoire.models.ForeignField
import pl.anatorini.grimoire.models.NamedModel
import pl.anatorini.grimoire.models.Spell
import pl.anatorini.grimoire.ui.theme.AppTheme
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.full.memberProperties


fun setProperty(instance: Any, propName: String, value: Any) {
    val property = instance::class.memberProperties
        .firstOrNull { it.name == propName } as KMutableProperty1<Any, Any?>
    property.set(instance, value)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
inline fun <reified T : NamedModel> ModelCreationForm(
    modifier: Modifier = Modifier,
    crossinline cancel: () -> Unit,
    crossinline save: (arg: T) -> Unit
) {
    var modelInstance by remember {
        mutableStateOf(
            T::class.java.getDeclaredConstructor().newInstance()
        )
    }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        for (prop in T::class.memberProperties) {
            if (prop.name == "url") continue
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                when (val classifier = prop.returnType.classifier) {
                    String::class -> {
                        var x by remember { mutableStateOf("") }
                        TextField(
                            value = x, onValueChange = {
                                x = it
                                setProperty(modelInstance, prop.name, it);

                            },
                            label = { Text(text = prop.name) },
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedContainerColor = MaterialTheme.colorScheme.surface
                            ), modifier = Modifier
                                .padding(vertical = 10.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .border(
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(5.dp)
                                )

                        )
                    }

                    Float::class -> {
                        var x by remember { mutableStateOf<Float?>(null) }
                        TextField(
                            value = x?.toString() ?: "", onValueChange = {
                                it.toFloatOrNull()?.let { f ->
                                    x = f
                                    setProperty(modelInstance, prop.name, f);
                                }
                            },
                            label = { Text(text = prop.name) },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Decimal),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedContainerColor = MaterialTheme.colorScheme.surface
                            ), modifier = Modifier
                                .padding(vertical = 10.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .border(
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(5.dp)
                                )

                        )
                    }

                    Int::class -> {
                        var x by remember { mutableStateOf<Int?>(null) }
                        TextField(
                            value = x?.toString() ?: "", onValueChange = {
                                it.toIntOrNull()?.let { int ->
                                    x = int
                                    setProperty(modelInstance, prop.name, int);
                                }
                            },
                            label = { Text(text = prop.name) },
                            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                            colors = TextFieldDefaults.colors(
                                unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                focusedContainerColor = MaterialTheme.colorScheme.surface
                            ), modifier = Modifier
                                .padding(vertical = 10.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(5.dp))
                                .border(
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(5.dp)
                                )

                        )
                    }

                    Boolean::class -> {
                        var x by remember { mutableStateOf<Boolean>(false) }
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.primary,
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .padding(5.dp)
                                .height(intrinsicSize = IntrinsicSize.Max),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    text = prop.name.replaceFirstChar { it.uppercaseChar() },
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )
                            }
                            Column(
                                modifier = Modifier.fillMaxHeight(),
                                verticalArrangement = Arrangement.Center
                            ) {
                                Switch(
                                    colors = SwitchDefaults.colors(
                                        uncheckedTrackColor = MaterialTheme.colorScheme.surface,
                                        uncheckedThumbColor = MaterialTheme.colorScheme.primary,
                                        uncheckedBorderColor = MaterialTheme.colorScheme.primary,
                                        checkedTrackColor = MaterialTheme.colorScheme.primaryContainer,
                                        checkedThumbColor = MaterialTheme.colorScheme.surface,
                                        checkedBorderColor = MaterialTheme.colorScheme.surface,
                                        checkedIconColor = MaterialTheme.colorScheme.primary
                                    ),
                                    thumbContent = if (x) {
                                        {
                                            Icon(
                                                imageVector = Icons.Filled.Check,
                                                contentDescription = null,
                                                modifier = Modifier.size(SwitchDefaults.IconSize),
                                            )
                                        }
                                    } else {
                                        null
                                    },
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    checked = x,
                                    onCheckedChange = {
                                        x = it
                                        setProperty(modelInstance, prop.name, it);
                                    })
                            }
                        }
                    }

                    is KClass<*> -> {
                        when {
                            ForeignField::class.java.isAssignableFrom(classifier.java) -> {
                                var x by remember { mutableStateOf<String>("") }
                                val scope = rememberCoroutineScope()
                                val context = LocalContext.current
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(intrinsicSize = IntrinsicSize.Max),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    (prop.get(modelInstance) as? ForeignField<*>)?.let { field ->
                                        var coffeeDrinks by remember {
                                            mutableStateOf<List<NamedModel>>(
                                                emptyList()
                                            )
                                        }
                                        var expanded by remember { mutableStateOf(false) }
                                        var selectedText by remember { mutableStateOf<String?>(null) }
                                        LaunchedEffect(key1 = "") {
                                            coffeeDrinks = field.getValues()
                                            if (coffeeDrinks.size > 0)
                                                selectedText = coffeeDrinks[0].name
                                            setProperty(field, "url", coffeeDrinks[0].url)
                                        }
                                        Surface(
                                            modifier = Modifier
                                                .fillMaxWidth(),
                                            tonalElevation = 20.dp
                                        ) {
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
                                                        x = it

                                                    },
                                                    readOnly = true,
                                                    trailingIcon = {
                                                        ExposedDropdownMenuDefaults.TrailingIcon(
                                                            expanded = expanded
                                                        )
                                                    },
                                                    modifier = Modifier
                                                        .menuAnchor()
                                                        .fillMaxWidth(),
                                                    colors = TextFieldDefaults.colors(
                                                        unfocusedContainerColor = Color.Transparent
                                                    ),

                                                    )

                                                ExposedDropdownMenu(
                                                    expanded = expanded,
                                                    onDismissRequest = { expanded = false }
                                                ) {
                                                    coffeeDrinks.forEach { item ->
                                                        DropdownMenuItem(
                                                            text = { Text(text = item.name) },
                                                            onClick = {
                                                                setProperty(field, "url", item.url)
                                                                selectedText = item.name
                                                                expanded = false
                                                                Toast.makeText(
                                                                    context,
                                                                    item.name,
                                                                    Toast.LENGTH_SHORT
                                                                ).show()
                                                            }
                                                        )
                                                    }
                                                }
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }

                    else -> {
                        Text(text = "Fuck knows ${prop.returnType.classifier}")
                    }
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Button(
                onClick = {
                    Log.println(
                        Log.INFO,
                        "ModelCreationForm",
                        "Saving model ${T::class::simpleName} instance ${modelInstance.toString()}"
                    )
                    save(modelInstance)
                    cancel()
                }) {
                Text(text = "Save")
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(onClick = { cancel() }) {
                Text(text = "Cancel")
            }
        }
    }
}

@Composable
@Preview
fun ModelCreationFormPreview() {
    AppTheme {
        ModelCreationForm<Spell>(Modifier.background(Color.White), cancel = {}, save = {})
    }
}
