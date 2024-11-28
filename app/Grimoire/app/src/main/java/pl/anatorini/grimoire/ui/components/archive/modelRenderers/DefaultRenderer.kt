package pl.anatorini.grimoire.ui.components.archive.modelRenderers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import pl.anatorini.grimoire.models.NamedModel


@Composable
fun DefaultRenderer(modifier: Modifier = Modifier, item: NamedModel) {
    Row(modifier.fillMaxWidth().padding(10.dp).clip(RoundedCornerShape(5.dp)).background(MaterialTheme.colorScheme.primary).padding(10.dp)) {
        Text(text = item.name, color=MaterialTheme.colorScheme.onPrimary)
    }
}

@Composable
@Preview
fun DefaultRendererPreview() {
    DefaultRenderer(item = object : NamedModel {
        override var name = "Default";
        override var url = ""

    })
}
