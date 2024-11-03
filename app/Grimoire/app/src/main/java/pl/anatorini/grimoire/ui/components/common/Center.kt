package pl.anatorini.grimoire.ui.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Composable
fun Center(modifier: Modifier = Modifier, content: @Composable() () -> Unit) {
    Column(modifier = modifier.fillMaxSize(), verticalArrangement = Arrangement.Center) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            content()
        }
    }
}

@Composable
@Preview
fun CenterPreview() {
    Center() {}
}
