package pl.anatorini.grimoire.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(modifier=modifier.fillMaxSize(), verticalArrangement = Arrangement.Top) {
        Row(modifier=Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "HomeScreen")
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    HomeScreen()
}