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
data class Alignment(
    override var name: String = "",
    override var url: String = ""
) : Model


@Serializable
data class PostAlignment(
    override var name: String = "",
) : PostModel

@Serializable(with = AlignmentForeignFieldSerializer::class)
data class AlignmentForeignField(
    override var url: String,
    override var value: Alignment? = null,
    override val getValues: suspend () -> List<Alignment> = {
        var paginatedResponse = HttpService.getAlignments(null)
        val items = ArrayList<Alignment>()
        items.addAll(paginatedResponse.results)
        while (paginatedResponse.next != null) {
            paginatedResponse = HttpService.getAlignments(paginatedResponse.next)
            items.addAll(paginatedResponse.results)
        }
        items
    }
) : ForeignField<Alignment>

@OptIn(ExperimentalSerializationApi::class)
@Serializer(forClass = AlignmentForeignField::class)
object AlignmentForeignFieldSerializer : KSerializer<AlignmentForeignField> {

    override val descriptor: SerialDescriptor = buildClassSerialDescriptor("ForeignField") {
        element<String>("url")
    }

    override fun serialize(encoder: Encoder, value: AlignmentForeignField) {
        encoder.encodeStructure(descriptor) {
            encodeStringElement(descriptor, 0, value.url)
        }
    }

    override fun deserialize(decoder: Decoder): AlignmentForeignField {
        val url = decoder.decodeString()
        return AlignmentForeignField(url = url ?: "")
    }
}

