package pl.anatorini.grimoire.ui.components.scaffold

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun ActionButton(
) {
    FloatingActionButton(
        onClick = { /*TODO*/ },
        containerColor = MaterialTheme.colorScheme.primary
    ) {
        Icon(imageVector = Icons.Filled.Add, contentDescription = "Action button")
    }
}

@Composable
@Preview
fun ActionButtonPreview() {
    AppTheme {
        ActionButton()
    }
}