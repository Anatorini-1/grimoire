package pl.anatorini.grimoire.screens

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


@Composable
fun CharactersScreen(modifier: Modifier = Modifier) {
    Text(text = "CharactersScreen")
}

@Composable
@Preview
fun CharactersScreenPreview() {
    CharactersScreen()
}
