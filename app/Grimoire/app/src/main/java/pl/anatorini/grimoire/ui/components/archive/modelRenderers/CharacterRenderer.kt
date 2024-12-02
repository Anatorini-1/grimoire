package pl.anatorini.grimoire.ui.components.archive.modelRenderers

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.models.Character
import pl.anatorini.grimoire.navigation.CharacterRoute
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun CharacterRenderer(
    modifier: Modifier = Modifier,
    instance: Character,
    navController: NavController
) {
    Row(horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .height(intrinsicSize = IntrinsicSize.Max)
            .clip(RoundedCornerShape(15.dp))
            .background(MaterialTheme.colorScheme.primary)
            .padding(10.dp)
            .clickable {
                navController.navigate(CharacterRoute(instance.url))
            }

    ) {
        Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
            Text(instance.name, color = MaterialTheme.colorScheme.onPrimary)
        }
        IconButton(
            onClick = {}, colors = IconButtonDefaults.iconButtonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary
            )
        ) {

            Icon(Icons.Filled.Person, contentDescription = "")
        }
    }
}


@Composable
@Preview
fun CharacterRendererPreview() {
    val c = Character(name = "Anatorini")
    AppTheme {
        CharacterRenderer(
            instance = c,
            modifier = Modifier.fillMaxWidth(),
            navController = rememberNavController()
        )
    }
}