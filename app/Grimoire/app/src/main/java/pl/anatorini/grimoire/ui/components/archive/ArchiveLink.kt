package pl.anatorini.grimoire.ui.components.archive

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import pl.anatorini.grimoire.ui.components.scaffold.TopBar
import pl.anatorini.grimoire.ui.components.screens.ArchiveScreen
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun ArchiveLink(
    modifier: Modifier = Modifier,
    name: String,
    route: String,
    icon: @Composable () -> Unit = {},
    navController: NavController
) {
    Surface(
        modifier = modifier.padding(5.dp),
        tonalElevation = 10.dp,
        shape = RoundedCornerShape(6.dp)
    ) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(10.dp)
                .clickable { navController.navigate(route) },


            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Text(
                text = name,
                fontWeight = FontWeight.Bold,
                fontSize = MaterialTheme.typography.bodyMedium.fontSize
            )
            Box(modifier = Modifier.height(MaterialTheme.typography.bodyMedium.lineHeight.value.dp)) {
                icon()
            }
        }
    }
}

@Composable
@Preview
fun ArchiveLinkPreview() {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    AppTheme {
        Scaffold(topBar = { TopBar(navController = navController, drawerState = drawerState) }) {
            ArchiveScreen(modifier = Modifier.padding(it), navController = navController)
        }
    }
}
