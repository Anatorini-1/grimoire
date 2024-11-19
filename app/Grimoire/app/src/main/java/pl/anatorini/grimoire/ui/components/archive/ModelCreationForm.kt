package pl.anatorini.grimoire.ui.components.archive

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
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.anatorini.grimoire.models.Item
import pl.anatorini.grimoire.models.Model
import pl.anatorini.grimoire.ui.theme.AppTheme
import kotlin.reflect.full.memberProperties


@Composable
inline fun <reified T : Model> ModelCreationForm(
    modifier: Modifier = Modifier,
    cancel: () -> Unit,
    save: (arg: T) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 10.dp)
    ) {
        for (prop in T::class.memberProperties) {
            if (prop.name == "url") continue
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                when (prop.returnType.classifier) {
                    String::class -> {
                        var x by remember { mutableStateOf("") }
                        TextField(
                            value = x, onValueChange = {
                                x = it
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
                                x = it.toFloatOrNull() ?: x
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
                                x = it.toIntOrNull() ?: x
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
                                    onCheckedChange = { x = it })
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
                onClick = { /*TODO*/ }) {
                Text(text = "Save")
            }
        }
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            OutlinedButton(onClick = { /*TODO*/ }) {
                Text(text = "Cancel")
            }
        }
    }
}

@Composable
@Preview
fun ModelCreationFormPreview() {
    AppTheme {
        ModelCreationForm<Item>(Modifier.background(Color.White), cancel = {}, save = {})
    }
}
