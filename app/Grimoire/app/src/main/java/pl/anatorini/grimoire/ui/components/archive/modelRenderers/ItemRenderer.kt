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
import pl.anatorini.grimoire.models.Item
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun ItemRenderer(modifier: Modifier = Modifier, item: Item) {
    var detail by remember { mutableStateOf(false) }
    var count by remember { mutableStateOf(0) }
    Column(
        modifier = modifier
            .fillMaxWidth()
            .border(width = 2.dp, color = MaterialTheme.colorScheme.primary)
            .padding(10.dp)
            .clickable { detail = !detail; count++ }
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
                    text = count.toString()
                )
                Text(
                    color = MaterialTheme.colorScheme.primary,
                    fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                    fontFamily = MaterialTheme.typography.bodyLarge.fontFamily,
                    fontStyle = MaterialTheme.typography.bodyLarge.fontStyle,
                    text = item.name
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
                    text = "${item.weight} lbs"
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
                    text = if (item.weapon) "w" else "na"
                )
            }
        }
        if (detail) {
            Row(modifier = Modifier.padding(10.dp)) {
                (if(item.description == null || item.description == "") "No description" else item.description)?.let {
                    Text(
                        color = MaterialTheme.colorScheme.scrim,
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                        text = it
                    )
                }

            }
            Row(modifier = Modifier.padding(10.dp)) {
                Column {
                    Text(
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        text = "Value: "
                    )
                }
                Column {
                    Text(text = "${item.value}gp")
                }
            }
        }
    }
}

@Composable
@Preview
fun ItemRendererPreview() {
    val item = Item(
        url = "",
        name = "Dupa",
        weight = 1f,
        value = 1f,
        weapon = false,
        attackBonus = 0,
        damage = "",
        description = "",
    )
    val item2 = Item(
        url = "",
        name = "Dupa2",
        weight = 1f,
        value = 1f,
        weapon = true,
        attackBonus = 4,
        damage = "1d5",
        description = "Dupa z dupy",
    )
    AppTheme {
        Column {
            ItemRenderer(modifier = Modifier.background(Color.White), item)
            ItemRenderer(modifier = Modifier.background(Color.White), item)
            ItemRenderer(modifier = Modifier.background(Color.White), item)
            ItemRenderer(modifier = Modifier.background(Color.White), item2)
        }
    }
}








