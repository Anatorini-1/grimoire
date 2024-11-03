package pl.anatorini.grimoire.ui.components.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.ui.components.common.Center
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun ArchiveScreen(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    val buttonModifier = Modifier.fillMaxWidth()
    val buttonShape = RoundedCornerShape(size = 5.dp)
    Column(modifier = modifier.fillMaxSize()) {
//        Row {
//            Text(
//                fontStyle = MaterialTheme.typography.headlineMedium.fontStyle,
//                fontSize = MaterialTheme.typography.headlineMedium.fontSize,
//                fontWeight = FontWeight.Bold,
//                text = "Knowledge Archive"
//            )
//        }
        Box(
            modifier = Modifier
                .height(3.dp)
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary)
        ) {
        }
        Column(modifier = Modifier.fillMaxSize()) {
            Row(modifier = Modifier.weight(1f)) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        shape = RoundedCornerShape(size = 20.dp),
                        onClick = { /*TODO*/ }) {
                        Center {
                            Text(text = "Classes")
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(size = 20.dp),
                        onClick = { /*TODO*/ }) {
                        Center {
                            Text(text = "Spells")
                        }
                    }
                }
            }
            Row(modifier = Modifier.weight(1f)) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(size = 20.dp),
                        onClick = { /*TODO*/ }) {
                        Center {
                            Text(text = "Races")
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        shape = RoundedCornerShape(size = 20.dp),
                        onClick = { /*TODO*/ }) {
                        Center {
                            Text(text = "Alignments")
                        }
                    }
                }
            }
            Row(modifier = Modifier.weight(1f)) {

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,
                            contentColor = MaterialTheme.colorScheme.onSecondary
                        ),
                        shape = RoundedCornerShape(size = 20.dp),
                        onClick = { /*TODO*/ }) {
                        Center {
                            Text(text = "Backgrounds")
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(10.dp)
                ) {
                    Button(
                        modifier = Modifier.fillMaxSize(),
                        shape = RoundedCornerShape(size = 20.dp),
                        onClick = { /*TODO*/ }) {

                        Center {
                            Text(text = "Items")
                        }
                    }
                }
            }
        }
    }
}


@Composable
@Preview
fun ArchiveScreenPreview() {
    AppTheme {
        ArchiveScreen(
            modifier = Modifier.background(Color.White)
        )
    }
}
