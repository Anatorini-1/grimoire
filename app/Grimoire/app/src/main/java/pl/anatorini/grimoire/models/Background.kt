package pl.anatorini.grimoire.models

import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.Serializer
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.descriptors.buildClassSerialDescriptor
import kotlinx.serialization.descriptors.element
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.encoding.encodeStructure
import pl.anatorini.grimoire.services.HttpService

@Serializable
data class Background(
    override var name: String = "",
    override var url: String = ""
) : Model

@Serializable
data class PostBackground(
    override var name: String = "",
): PostModel

@Serializable(with = BackgroundForeignFieldSerializer::class)
data class BackgroundForeignField(
    override var url: String,
    override var value: Background? = null,
    override val getValues: suspend () -> List<Background> = {
        var paginatedResponse = HttpService.getBackgrounds(null)
        val items = ArrayList<Background>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getBackgrounds(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    }
) : ForeignField<Background>


@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = BackgroundForeignField::class)
object BackgroundForeignFieldSerializer : KSerializer<BackgroundForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: BackgroundForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): BackgroundForeignField {
        val url = decoder.decodeString()
        return BackgroundForeignField(url = url ?: "")
    }
}
