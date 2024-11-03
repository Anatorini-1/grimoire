package pl.anatorini.grimoire.components.settings

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
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.anatorini.grimoire.ui.theme.AppTheme

@Composable
fun SettingsField(
    modifier: Modifier = Modifier,
    primaryText: String,
    secondaryText: String = "",
    onClick: () -> Unit,
    icon: ImageVector? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Max)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column(
            modifier = modifier
                .padding(horizontal = 30.dp)
                .padding(vertical = 10.dp)
                .fillMaxHeight()
        ) {
            Text(
                text = primaryText,
                modifier = Modifier.padding(vertical = 5.dp),
                fontStyle = MaterialTheme.typography.labelLarge.fontStyle,
                fontSize = MaterialTheme.typography.labelLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = secondaryText,
                fontStyle = MaterialTheme.typography.labelMedium.fontStyle,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                color = MaterialTheme.colorScheme.tertiary
            )
        }
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(10.dp),
            verticalArrangement = Arrangement.Center,
        ) {
            icon?.let {
                Icon(
                    it,
                    contentDescription = "IconInfo", modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colorScheme.tertiary
                )
            }
        }
    }
}

@Composable
@Preview
fun SettingsFieldPreview() {
    AppTheme {
        SettingsField(
            modifier = Modifier.background(color = MaterialTheme.colorScheme.background),
            primaryText = "Settings field",
            secondaryText = "Secondary text",
            onClick = {},
            icon = Icons.Filled.Settings
        )
    }
}
