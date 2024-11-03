package pl.anatorini.grimoire

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun Test() {
    Button(
        colors = ButtonDefaults.buttonColors(
           contentColor = MaterialTheme.colorScheme.primary
        ),
        onClick = { /*TODO*/ }) {
        Text(text = "Piwo")
    }
}