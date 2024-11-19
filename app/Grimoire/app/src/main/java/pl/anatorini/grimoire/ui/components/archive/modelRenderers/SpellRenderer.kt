package pl.anatorini.grimoire.ui.components.archive.modelRenderers

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.anatorini.grimoire.models.Spell
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun SpellRenderer(modifier: Modifier = Modifier, spell: Spell) {
    var detail by remember { mutableStateOf(false) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = MaterialTheme.colorScheme.primary)
            .padding(10.dp)
            .clickable { detail = !detail }
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(3f)
            ) {
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                    fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    text = spell.name
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                    fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    color = MaterialTheme.colorScheme.tertiary,
                    text = "${spell.level} lvl"
                )
            }
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                Text(
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                    fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    color = MaterialTheme.colorScheme.tertiary,
                    text = spell.school
                )
            }
        }
        if (detail) {
            Row(modifier = Modifier.padding(10.dp)) {
                Text(
                    color = MaterialTheme.colorScheme.scrim,
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                    text = spell.description
                )

            }
            Row(modifier = Modifier.padding(10.dp)) {
                Column {
                    Text(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        text = "Range: "
                    )
                }
                Column {
                    Text(text = spell.range)
                }
            }
            Row(modifier = Modifier.padding(10.dp)) {
                Column {
                    Text(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        text = "Ritual: "
                    )
                }
                Column {
                    Text(text = if (spell.ritual) "Yes" else "No")
                }
            }
            Row(modifier = Modifier.padding(10.dp)) {
                Column {
                    Text(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        text = "Duration: "
                    )
                }
                Column {
                    Text(text = spell.duration)
                }
            }
        }
    }
}

@Composable
@Preview
fun SpellRendererPreview() {
    val spell = Spell(
        url = "",
        name = "Fireball",
        level = 4,
        ritual = false,
        description = "A bright streak flashes from your pointing finger to a point you choose within range then blossoms with a low roar into an explosion of flame. Each creature in a 20-foot radius must make a Dexterity saving throw. A target takes 8d6 fire damage on a failed save, or half as much damage on a successful one. The fire spreads around corners. It ignites flammable objects in the area that aren’t being worn or carried.",
        range = "150ft",
        school = "Evocation",
        duration = "Instantaneous",
    )
    AppTheme {
        SpellRenderer(modifier = Modifier.background(Color.White), spell = spell)
    }
}
