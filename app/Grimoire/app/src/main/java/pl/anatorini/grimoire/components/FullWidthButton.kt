package pl.anatorini.grimoire.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun FullWidthButton(
    modifier: Modifier = Modifier,
    icon: @Composable() () -> Unit,
    label: @Composable() () -> Unit,
    onClick: () -> Unit
) {
    val buttonModifier = Modifier.fillMaxWidth()
    val buttonShape = RoundedCornerShape(size = 5.dp)

    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
        Button(
            modifier = buttonModifier,
            shape = buttonShape,
            onClick = { onClick() }) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(IntrinsicSize.Max),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    label()

                }
                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        icon()
                    }
                }
            }
        }
    }
}

@Composable
@Preview
fun FullWidthButtonPreview() {
    FullWidthButton(
        modifier = Modifier.background(Color.White),
        label = { Text(text = "Characters") },
        onClick = {},
        icon = { Icon(imageVector = Icons.Filled.Menu, contentDescription = "") }
    )
}
