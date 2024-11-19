package pl.anatorini.grimoire.ui.components.archive.modelRenderers

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import pl.anatorini.grimoire.models.Model


@Composable
fun DefaultRenderer(modifier: Modifier = Modifier, item: Model) {
    Row(modifier.fillMaxWidth()) {
        Text(text = item.name)
    }
}

@Composable
@Preview
fun DefaultRendererPreview() {
    DefaultRenderer(item = object : Model {
        override val name = "Default"
    })
}
