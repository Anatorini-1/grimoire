package pl.anatorini.grimoire.ui.components.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import compose.icons.FontAwesomeIcons
import compose.icons.fontawesomeicons.Solid
import compose.icons.fontawesomeicons.solid.Atlas
import compose.icons.fontawesomeicons.solid.BookReader
import compose.icons.fontawesomeicons.solid.Users
import pl.anatorini.grimoire.R
import pl.anatorini.grimoire.navigation.Routes
import pl.anatorini.grimoire.ui.components.common.FullWidthButton
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun HomeScreen(modifier: Modifier = Modifier, navController: NavHostController) {

    val buttonModifier = Modifier.fillMaxWidth()
    val buttonShape = RoundedCornerShape(size = 5.dp)

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), verticalArrangement = Arrangement.Top
    ) {

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Image(
                painter = painterResource(id = R.drawable.grimoire),
                contentDescription = "Grimoire app logo",
                modifier = Modifier.clip(RoundedCornerShape(size = 10.dp))
            )
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(vertical = 20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            FullWidthButton(
                modifier = Modifier,
                label = { Text(text = "Characters") },
                icon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Users,
                        contentDescription = "",
                        modifier = Modifier.height(20.dp)
                    )
                },
                onClick = {
                    navController.navigate(Routes.CHARACTERS.name)
                }
            )
            FullWidthButton(
                modifier = Modifier,
                label = { Text(text = "Campaigns") },
                icon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.BookReader,
                        contentDescription = "",
                        modifier = Modifier.height(20.dp)
                    )
                },
                onClick = {
                    navController.navigate(Routes.CAMPAIGNS.name)
                }
            )
            FullWidthButton(
                modifier = Modifier,
                label = { Text(text = "Knowleage Archive") },
                icon = {
                    Icon(
                        imageVector = FontAwesomeIcons.Solid.Atlas,
                        contentDescription = "",
                        modifier = Modifier.height(20.dp)
                    )
                },
                onClick = {
                    navController.navigate(Routes.ARCHIVE.name)
                }
            )
        }
    }
}

@Composable
@Preview
fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            modifier = Modifier.background(Color.White),
            navController = rememberNavController()
        )
    }
}